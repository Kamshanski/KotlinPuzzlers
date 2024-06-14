package coroutines.coroutines1

import coroutines.coroutines1.utils.ICoroutines1Tasks
import coroutines.coroutines1.utils.NoDataTransformedException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.spy
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.io.IOException
import java.net.ConnectException
import java.util.concurrent.atomic.AtomicInteger
import kotlin.test.Test
import kotlin.test.assertEquals

open class Coroutines1TasksTest {

	private companion object {

		private val STRINGS_TO_INTS = List(20) { it }
			.associateBy { it.toString() }
	}

	open val asyncProblems: ICoroutines1Tasks = Coroutines1Tasks()

	@Test
	fun `GIVEN lambda that does not throw exceptions WHEN call loadWithRetryOrNull EXPECT string`() = runTest {
		val expected = "Result"
		suspend fun loadData(): String {
			return expected
		}

		val actual = asyncProblems.loadWithRetryOrNull(::loadData)

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN lambda that throws IOException 2 times WHEN call loadWithRetryOrNull EXPECT string`() = runTest {
		val expected = "Result"
		var counter = 0
		suspend fun loadData(): String {
			counter++
			if (counter in 1..2) {
				throw IOException("Some exception")
			}
			return expected
		}

		val actual = asyncProblems.loadWithRetryOrNull(::loadData)

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN lambda that throws subtype of IOException 2 times WHEN call loadWithRetryOrNull EXPECT string`() =
		runTest {
		val expected = "Result"
		var counter = 0
		suspend fun loadData(): String {
			counter++
			if (counter in 1..2) {
				throw ConnectException("Some exception")
			}
			return expected
		}

			val actual = asyncProblems.loadWithRetryOrNull(::loadData)

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN lambda that always throws IOException WHEN call loadWithRetryOrNull EXPECT string`() = runTest {
		suspend fun loadData(): String {
			throw IOException("Some exception")
		}
		val loadData = spy(::loadData)

		val actual = asyncProblems.loadWithRetryOrNull(loadData)

		assertEquals(null, actual)
		verify(loadData, times(3)).invoke()
	}

	@Test
	fun `GIVEN lambda that throws other exception WHEN call loadWithRetryOrNull EXPECT string`() = runTest {
		suspend fun loadData(): String {
			throw ArithmeticException("Some exception")
		}

		val loadData = spy(::loadData)

		assertThrows<ArithmeticException> {
			asyncProblems.loadWithRetryOrNull(loadData)
		}
		verify(loadData, times(1)).invoke()
	}

	@Test
	fun `GIVEN lambda that does not throw exceptions WHEN call loadWithRetryOrThrow EXPECT string`() = runTest {
		val expected = "Result"
		suspend fun loadData(): String {
			return expected
		}

		val actual = asyncProblems.loadWithRetryOrThrow(::loadData)

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN lambda that throws IOException 2 times WHEN call loadWithRetryOrThrow EXPECT string`() = runTest {
		val expected = "Result"
		var counter = 0
		suspend fun loadData(): String {
			counter++
			if (counter in 1..2) {
				throw IOException("Some exception")
			}
			return expected
		}

		val actual = asyncProblems.loadWithRetryOrThrow(::loadData)

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN lambda that throws subtype of IOException 2 times WHEN call loadWithRetryOrThrow EXPECT string`() =
		runTest {
			val expected = "Result"
			var counter = 0
			suspend fun loadData(): String {
				counter++
				if (counter in 1..2) {
					throw ConnectException("Some exception")
				}
				return expected
			}

			val actual = asyncProblems.loadWithRetryOrThrow(::loadData)

			assertEquals(expected, actual)
		}

	@Test
	fun `GIVEN lambda that always throws IOException WHEN call loadWithRetryOrThrow EXPECT IOException`() = runTest {
		suspend fun loadData(): String {
			throw IOException("Some exception")
		}

		val loadData = spy(::loadData)

		assertThrows<IOException> {
			asyncProblems.loadWithRetryOrThrow(loadData)
		}
		verify(loadData, times(3)).invoke()
	}

	@Test
	fun `GIVEN lambda that throws other exception WHEN call loadWithRetryOrThrow EXPECT string`() = runTest {
		suspend fun loadData(): String {
			throw ArithmeticException("Some exception")
		}
		val loadData = spy(::loadData)

		assertThrows<ArithmeticException> {
			asyncProblems.loadWithRetryOrThrow(loadData)
		}
		verify(loadData, times(1)).invoke()
	}

	@Test
	fun `GIVEN strings and lambdas EXPECT list of longs, all lambdas run sequentially`() = runTest {
		class TestData(val string: String, val int: Int, val double: Double, val long: Long)

		val data = listOf(
			TestData("10", 12, 110.0, 101L),
			TestData("20", 23, 220.0, 202L),
			TestData("30", 37, 730.0, 304L),
			TestData("40", 49, 440.0, 405L),
			TestData("50", 53, 850.0, 507L),
			TestData("60", 66, 460.0, 609L),
			TestData("70", 77, 370.0, 702L),
		)
		val firstStateCounter = AtomicInteger(0)
		val secondStateCounter = AtomicInteger(0)
		val expected = data.map { it.long }.sorted()

		suspend fun transform1(q: String): Int {
			val item = data.first { it.string == q }
			delay(item.string.toLong())

			firstStateCounter.incrementAndGet()

			return item.int
		}

		suspend fun transform2(q: Int): Double {
			val counter = firstStateCounter.get()
			if (counter < data.size) throw IllegalStateException("Cannot execute transform2 before all data transformed with transform1. transfrom1 executed $counter times")

			val item = data.first { it.int == q }
			delay(item.string.toLong())

			secondStateCounter.incrementAndGet()

			return item.double
		}

		suspend fun transform3(q: Double): Long {
			val counter = secondStateCounter.get()
			if (counter < data.size) throw IllegalStateException("Cannot execute transform3 before all data transformed with transform2. transfrom2 executed $counter times")

			val item = data.first { it.double == q }
			delay(item.string.toLong())
			return item.long
		}

		val actual = withTimeout(210 + 50) { // 210 ms delay for all computations + 50 ms for other code executions
			asyncProblems
				.processAllStagesSequentially(data.map { it.string }, ::transform1, ::transform2, ::transform3)
		}

		assertEquals(expected, actual.sorted())
	}

	@Test
	fun `GIVEN query and lambdas EXPECT first loaded result`() = runTest {
		val firstResult = "result first"

		val finishedCount = AtomicInteger(0)
		suspend fun load(delayTime: Long, result: String): String {
			delay(delayTime)
			finishedCount.incrementAndGet()
			return result
		}

		val load1: suspend (String) -> String = { load(300, "result 1") }
		val load2: suspend (String) -> String = { load(200, "result 2") }
		val load3: suspend (String) -> String = { load(100, firstResult) }

		val actual =
			withTimeout(100 + 30) { // 100 ms delay for fastest lambda execution + 30 ms for other code executions
				asyncProblems
					.loadManyReturnFirst(firstResult, load1, load2, load3)
			}

		assertEquals(finishedCount.get(), 1, "Rest of request were not finished")
		assertEquals(firstResult, actual)
	}

	@Test
	fun `GIVEN strings and lambda that does not throws exception EXPECT successfully transformed ints`() = runTest {
		suspend fun transform(str: String): Int {
			val int = STRINGS_TO_INTS[str]!!
			delay(int * 10L)

			return int
		}

		val actual = withTimeout(200 + 50) { // 200 ms delay for all computations + 50 ms for other code executions
			asyncProblems
				.processStringsWithExceptions(STRINGS_TO_INTS.keys.toList(), ::transform)
		}

		assertEquals(STRINGS_TO_INTS.values.sorted(), actual.sorted())
	}

	@Test
	fun `GIVEN strings and lambda that throws exception to some strings EXPECT successfully transformed ints`() =
		runTest {
			val exceptionStrings = STRINGS_TO_INTS.keys
				.shuffled()
				.take(5)
				.toSet()
			val expected = (STRINGS_TO_INTS.keys - exceptionStrings)
				.map { STRINGS_TO_INTS[it]!! }
				.sorted()

			suspend fun transform(str: String): Int {
				val int = STRINGS_TO_INTS[str]!!
				delay(int * 10L)

				if (str in exceptionStrings) {
					throw RuntimeException("Test exception")
				}

				return int
			}

			val actual = withTimeout(200 + 50) { // 200 ms delay for all computations + 50 ms for other code executions
				asyncProblems
					.processStringsWithExceptions(STRINGS_TO_INTS.keys.toList(), ::transform)
			}

			assertEquals(expected.sorted(), actual.sorted())
		}

	@Test
	fun `GIVEN strings and lambda that always throws exception EXPECT no data transformed exception`() = runTest {
		suspend fun transform(str: String): Int {
			val int = STRINGS_TO_INTS[str]!!
			delay(int * 10L)

			throw RuntimeException("Test exception")
		}

		assertThrows<NoDataTransformedException> {
			withTimeout(200 + 50) { // 200 ms delay for all computations + 50 ms for other code executions
				asyncProblems
					.processStringsWithExceptions(STRINGS_TO_INTS.keys.toList(), ::transform)
			}
		}
	}

	@Test
	fun `GIVEN strings and long computation lambda EXPECT ints, lambda were never running 4 times simultaneously`() =
		runBlocking {
			val stringsToInts = STRINGS_TO_INTS

			val atomicInt = AtomicInteger(4)

			suspend fun longComputation(str: String): Int {
				val counter = atomicInt.decrementAndGet()
				if (counter < 0) {
					throw IllegalStateException("Cannot perform more than 4 computaions")
				}

				delay(30)
				val int = stringsToInts[str]!!

				atomicInt.incrementAndGet()

				return int
			}

			val actual = withTimeout(150 + 50) { // 150 ms delay for all computations + 50 ms for other code executions
				asyncProblems
					.processStringsWithLimitOfFourSimultaneousComputations(
						stringsToInts.keys.toList(),
						::longComputation
					)
			}

			assertEquals(stringsToInts.values.sorted(), actual.sorted())
		}
}