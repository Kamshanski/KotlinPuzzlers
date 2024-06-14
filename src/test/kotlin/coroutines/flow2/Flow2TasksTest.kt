package coroutines.flow2

import coroutines.flow2.util.MessagesRepositoryImpl
import coroutines.flow2.utils.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

open class Flow2TasksTest {

	open val flowProblems: IFlow2Tasks = Flow2Tasks()

	@Test
	fun `GIVEN strings without exception EXPECT STARTED in the beginning, COMPLETED in the end`() = runTest {
		val strings = listOf("Hi", "Hello", "Ciao", "Buongiorno")
		val expected = listOf("STARTED") + strings + "COMPLETED"
		val stringsFlow = flow {
			for (it in strings) {
				emit(it)
			}
		}

		val actual = flowProblems
			.trackFlowStartAndFinish(stringsFlow)
			.toList()

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN strings with exception EXPECT STARTED in the beginning, ERROR in the end`() = runTest {
		val strings = listOf("Hi", "Hello", "Ciao", "Buongiorno")
		val expected = listOf("STARTED") + strings + "ERROR"
		val stringsFlow = flow {
			for (it in strings) {
				emit(it)
			}

			throw ArithmeticException(":(")
		}

		val actual = flowProblems
			.trackFlowStartAndFinish(stringsFlow)
			.toList()

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN strings and ints EXPECT combinations of latest string and sum of all emitted ints`() = runTest {
		val names = Channel<String>()
		val increments = Channel<Int>()

		val flow = flowProblems
			.stringsWithIncrements(names.consumeAsFlow(), increments.consumeAsFlow())

		launch {
			names.send("A")
			increments.send(2)
			increments.send(3)
			names.send("B")
			names.send("C")
			names.send("D")
			increments.send(2)
			increments.send(3)

			names.close()
			increments.close()
		}
		val actual = flow.toList()

		assertEquals(
			listOf(
				"A_2",
				"A_5",
				"B_5",
				"C_5",
				"D_5",
				"D_7",
				"D_10",
			),
			actual
		)
	}

	@Test
	fun `GIVEN longs with delay and long to string transformation function with delay EXPECT list of strings with fast transformation`() =
		runTest {

			val numbers = List(10) { Random.nextLong() }.distinct()
			val mappigns = numbers.associateWith { Random.nextLong().toString() }

			val emitDelay = 100L
			val mappingDelay = 300L
			val coroutinesTimeError = 90L
			val timeout = (emitDelay + numbers.size * mappingDelay + coroutinesTimeError).milliseconds

			suspend fun stringTransform(item: Long): String {
				delay(mappingDelay)
				return mappigns[item]!!
			}

			val actual = withTimeout(timeout) {
				flowProblems.efficientStringComputation(
					numbers.asFlow().onEach { delay(emitDelay) },
					::stringTransform,
				)
			}

			assertEquals(mappigns.values.toList(), actual)
		}

	@Test
	fun `GIVEN students data EXPECT filtered students models`() = runTest {
		val students = listOf(
			listOf("Anna", "19", "Architecture"),
			listOf("Dima", "21y.o.", "IT"),
			listOf("Luca", null, "Agriculture"),
			listOf(null, "24", "Medicine"),
			listOf("Aristotle", "2330", "Geometry"),
			listOf("Nikolai", "22", "Calculus"),
			listOf("Li", "年20岁", "Geometry"),
			listOf("Euclid", "2300", "Geometry"),
		)
			.flatten()
			.asFlow()
		val expected = listOf(
			Student(name = "Anna", age = 19, major = "Architecture"),
			Student(name = "Dima", age = null, major = "IT"),
			Student(name = "Luca", age = null, major = "Agriculture"),
			Student(name = "Aristotle", age = 2330, major = "Geometry"),
			Student(name = "Nikolai", age = 22, major = "Calculus"),
		)

		val actual = flowProblems
			.buildStudents(students)
			.toList()

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN counter commands without limit exceeding EXPECT counter values without exception`() = runTest {
		val flow = flowOf(
			CounterAction.INCREMENT,
			CounterAction.INCREMENT,
			CounterAction.CLEAR,
			CounterAction.INCREMENT,
			CounterAction.CLEAR,
			CounterAction.CLEAR,
			CounterAction.INCREMENT,
		)

		val actual = flowProblems.countTo3(flow).toList()

		val expected = listOf(1, 2, 0, 1, 0, 1)
		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN counter commands with limit exceeding EXPECT exception`() = runTest {
		val flow = flowOf(
			CounterAction.INCREMENT,
			CounterAction.INCREMENT,
			CounterAction.INCREMENT,
		)

		assertThrows<CounterException> {
			flowProblems.countTo3(flow).collect()
		}
	}

	@Test
	fun `GIVEN flows emitting ints or exceptions EXPECT all ints and last exception`() = runTest {
		val lastException = Flow2Exception("last")
		val exceptions = mutableListOf(
			Flow2Exception("1"),
			Flow2Exception("2"),
			lastException,
		)

		val lock = Any()
		fun getException(): Flow2Exception =
			synchronized(lock) {
				exceptions.removeFirst()
			}

		val list = listOf(
			flow {
				emit(10)
				delay(100)

				emit(11)
				delay(100)

				throw getException()
			},
			flowOf(20, 21),
			flow {
				delay(20)

				emit(30)

				throw getException()
			},
			flow {
				emit(40)
				delay(300)

				throw getException()
			},
		)
		val expectedItems = listOf(10, 11, 20, 21, 30, 40).sorted()
		val expected = expectedItems to lastException

		val actual = withTimeout(320 + 30) {    // 320 ms of delay + 30 ms for coroutine working
			flowProblems.collectIntsAndException(list)
		}

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN flows emitting ints EXPECT all ints and no exception`() = runTest {
		val list = listOf(
			flow {
				emit(10)
				delay(100)

				emit(11)
				delay(100)
			},
			flowOf(20, 21),
			flow {
				delay(20)

				emit(30)
			},
			flow {
				emit(40)
				delay(300)
			},
		)
		val expectedItems = listOf(10, 11, 20, 21, 30, 40).sorted()
		val expected: Pair<List<Int>, Flow2Exception?> = expectedItems to null

		val actual = withTimeout(320 + 30) {    // 320 ms of delay + 30 ms for coroutine working
			flowProblems.collectIntsAndException(list)
		}

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN all progress flows works ok EXPECT correct summation`() = runTest {
		val channel1 = Channel<Int>()
		val channel2 = Channel<Int>()
		val channel3 = Channel<Int>()
		val channel4 = Channel<Int>()
		val progresses = listOf(channel1, channel2, channel3, channel4)

		val actions = listOf(
			channel1 to 10,  // 2
			channel2 to 20,  // 7
			channel3 to 20,  // 12
			channel1 to 20,  // 15
			channel4 to 10,  // 27
			channel2 to 59,  // 35
			channel2 to 60,  //     Skip emit. Just same value
			channel3 to 50,  // 40
			channel4 to 30,  // 35
			channel4 to 40,  // 42
			channel3 to -1,  // 40
			channel1 to 80,  // 60
			channel4 to 90,  // 76
			channel2 to 100, // 90
			channel4 to 100, // 93
			channel1 to 100, // 100
		)
		val expected = listOf(0, 2, 7, 12, 15, 17, 27, 35, 40, 42, 40, 60, 76, 90, 93, 100)

		val emitJob = launch(start = CoroutineStart.LAZY) {
			progresses.forEach {
				it.send(0)
			}

			for ((channel, progress) in actions) {
				delay(10)
				channel.send(progress)

				if (progress == 100 || progress < 0) {
					channel.close()
				}
			}
		}

		val flows = progresses.map { it.consumeAsFlow() }
		val actual = coroutineScope {
			flowProblems
				.loadingProgress(flows)
				.onStart { emitJob.start() }
				.toList()
		}

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN all progress flows works ok, but returns -1 EXPECT -1`() = runTest {
		val channel1 = Channel<Int>()
		val channel2 = Channel<Int>()
		val channel3 = Channel<Int>()
		val channel4 = Channel<Int>()
		val progresses = listOf(channel1, channel2, channel3, channel4)

		val actions = listOf(
			channel4 to -1,  // 0
			channel3 to -1,  //    Same value skip
			channel2 to -1,  //    Same value skip
			channel1 to -1,  // -1
		)
		val expected = listOf(0, -1)

		val emitJob = launch(start = CoroutineStart.LAZY) {
			progresses.forEach {
				it.send(0)
			}

			for ((channel, progress) in actions) {
				delay(10)
				channel.send(progress)

				if (progress == 100 || progress < 0) {
					channel.close()
				}
			}
		}

		val flows = progresses.map { it.consumeAsFlow() }
		val actual = coroutineScope {
			flowProblems
				.loadingProgress(flows)
				.onStart { emitJob.start() }
				.toList()
		}

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN all progress flows works wrong EXPECT correct summation`() = runTest {
		val channel1 = Channel<Int>()
		val channel2 = Channel<Int>()
		val channel3 = Channel<Int>()
		val channel4 = Channel<Int>()
		val progresses = listOf(channel1, channel2, channel3, channel4)

		val actions = listOf(
			channel1 to 10,  // 2
			channel2 to 20,  // 7
			channel3 to 20,  // 12
			channel1 to 20,  // 15
			channel4 to 10,  // 27
			channel2 to 59,  // 35
			channel2 to 60,  //     Skip emit. Just same value
			channel3 to 50,  // 40
			channel4 to 30,  // 35
			channel4 to 40,  // 42
			channel3 to -1,  // 40
			channel1 to 80,  // 60
			channel4 to 90,  // 76
			channel2 to 100, // 90
			channel3 to 21,  //     Channel previously sent -1, ignore its new value
			channel4 to 100, // 93
			channel3 to 44,  //     Channel previously sent -1, ignore its new value
			channel1 to 100, // 100
		)
		val expected = listOf(0, 2, 7, 12, 15, 17, 27, 35, 40, 42, 40, 60, 76, 90, 93, 100)

		val emitJob = launch(start = CoroutineStart.LAZY) {
			progresses.forEach {
				it.send(0)
			}

			for ((channel, progress) in actions) {
				delay(10)
				channel.send(progress)
			}

			progresses.forEach { it.cancel() }
		}

		val flows = progresses.map { it.consumeAsFlow() }
		val actual = coroutineScope {
			flowProblems
				.loadingBrokenProgress(flows)
				.onStart { emitJob.start() }
				.toList()
		}
		emitJob.cancel()

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN all progress flows works wrong, but returns -1 EXPECT -1`() = runTest {
		val channel1 = Channel<Int>()
		val channel2 = Channel<Int>()
		val channel3 = Channel<Int>()
		val channel4 = Channel<Int>()
		val progresses = listOf(channel1, channel2, channel3, channel4)

		val actions = listOf(
			channel4 to -1,  // 0
			channel3 to -1,  //    Same value skip
			channel2 to -1,  //    Same value skip
			channel1 to -1,  // -1
			channel3 to 2,   // Flow must be closed
			channel4 to 3,   // Flow must be closed
			channel2 to 4,   // Flow must be closed
			channel1 to 5,   // Flow must be closed
		)
		val expected = listOf(0, -1)

		val emitJob = launch(start = CoroutineStart.LAZY) {
			progresses.forEach {
				it.send(0)
				it.invokeOnClose {
					println("Closed((")
				}
			}

			for ((channel, progress) in actions) {
				delay(10)
				channel.send(progress)
			}

			progresses.forEach { it.cancel() }
		}

		val flows = progresses.map { it.receiveAsFlow() }
		val actual = coroutineScope {
			flowProblems
				.loadingBrokenProgress(flows)
				.onStart { emitJob.start() }
				.toList()
		}
		emitJob.cancel()

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN repository produces data with dot EXPECT strings`() = runTest {
		val items = listOf("Hello", "!", "My", "name", "is", "potato", ".", "I`m", "from", "Thailand")
		val expected = listOf("Hello", "!", "My", "name", "is", "potato", ".")
		val repository = MessagesRepositoryImpl(this) { callback ->
			for (data in items) {
				delay(10)
				callback.onNewMessage(data)
			}
			callback.onFinished(null)
		}

		val actual = flowProblems
			.collectMessagesFromRepository(repository)
			.toList()

		assertEquals(expected, actual)
		assertEquals(
			false,
			repository.isCallbackSet(),
			"Don't forget to unsubscribe when receiving messages is finished"
		)
	}

	@Test
	fun `GIVEN repository produces data without dot EXPECT strings`() = runTest {
		val items = listOf("Hello", "!", "My", "name", "is", "potato", "and", "I`m", "from", "Thailand")
		val repository = MessagesRepositoryImpl(this) { callback ->
			for (data in items) {
				delay(10)
				callback.onNewMessage(data)
			}
			callback.onFinished(null)
		}

		val actual = flowProblems
			.collectMessagesFromRepository(repository)
			.toList()

		assertEquals(items, actual)
		assertEquals(
			false,
			repository.isCallbackSet(),
			"Don't forget to unsubscribe when receiving messages is finished"
		)
	}

	@Test
	fun `GIVEN repository produces data without exceptions EXPECT strings with error string`() = runTest {
		val items = listOf("Hello", "!", "My", "name", "is", "potato", ".", "I`m", "from", "Thailand")
		val expected = items.take(3) + "." + "ERROR"
		val repository = MessagesRepositoryImpl(this) { callback ->
			for (data in items.take(3)) {
				delay(10)
				callback.onNewMessage(data)
			}
			callback.onFinished(IOException(":("))
		}

		val actual = flowProblems
			.collectMessagesFromRepository(repository)
			.toList()

		assertEquals(expected, actual)
		assertEquals(
			false,
			repository.isCallbackSet(),
			"Don't forget to unsubscribe when receiving messages is finished"
		)
	}
}