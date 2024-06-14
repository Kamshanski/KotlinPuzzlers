package coroutines.coroutines_exceptions

import coroutines.coroutines_exceptions.utils.AsyncException
import coroutines.coroutines_exceptions.utils.ICoroutinesExceptionsTasks
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.*
import kotlin.test.Test
import kotlin.test.assertEquals

open class CoroutinesExceptionsTasksTest {

	open val tasks: ICoroutinesExceptionsTasks = CoroutinesExceptionsTasks()

	@Test
	fun `GIVEN function returns number EXPECT number`() = runTest {
		val expected = 13
		suspend fun function(): Int {
			return expected
		}

		val actual = tasks.tryNotToFail(::function)

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN function throws exception EXPECT exception is returned`() = runTest {
		val expected = IllegalArgumentException(":(")
		suspend fun function(): Int {
			throw expected
		}

		val actual = tasks.tryNotToFail(::function)

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN function throws error EXPECT error`() = runTest {
		val expected = OutOfMemoryError(":(")
		suspend fun function(): Int {
			throw expected
		}

		assertThrows<OutOfMemoryError> {
			tasks.tryNotToFail(::function)
		}
	}

	@Test
	fun `GIVEN function throws cancellation exception EXPECT cancellation exception `() = runTest {
		val expected =
			CancellationException("Never catch CancellationException. Никогда не ловите CancellationException")

		suspend fun function(): Int {
			throw expected
		}

		assertThrows<CancellationException> {
			tasks.tryNotToFail(::function)
		}
	}

	@Test
	fun `GIVEN work throws AsyncException for try-catch case EXPECT AsyncException in onError callback`() = runTest {
		val expected = AsyncException(":(")
		val onError: (Throwable) -> Unit = mock()
		val doWork: () -> Unit = mock()

		whenever(doWork.invoke()).thenAnswer { throw expected }

		coroutineScope {
			tasks.doWorkThrowsExceptionWithTryCatch(scope = this, doWork, onError)
		}

		verify(doWork).invoke()
		verify(onError).invoke(expected)
	}

	@Test
	fun `GIVEN work throws AsyncException for CEH case EXPECT AsyncException in onError callback`() = runTest {
		val expected = AsyncException(":(")
		val onError: (Throwable) -> Unit = mock()
		val doWork: () -> Unit = mock()

		whenever(doWork.invoke()).thenAnswer { throw expected }

		supervisorScope {
			tasks.doWorkThrowsExceptionWithCEH(scope = this, doWork, onError)
		}

		verify(doWork).invoke()
		verify(onError).invoke(expected)
	}

	@Test
	fun `GIVEN async work throws AsyncException EXPECT AsyncException in onError callback`() = runTest {
		val expected = AsyncException(":(")
		val onError: (Throwable) -> Unit = mock()
		val onSuccess: (Int) -> Unit = mock()
		val doWork: () -> Int = mock()

		whenever(doWork.invoke()).thenAnswer { throw expected }

		supervisorScope {
			tasks.doTopLevelAsyncWorkThrowsException(scope = this, doWork, onError, onSuccess)
		}

		verify(doWork).invoke()
		verify(onError).invoke(expected)
		verify(onSuccess, never()).invoke(any())
	}

	@Test
	fun `GIVEN async work returns a number EXPECT number in onSuccess callback`() = runTest {
		val expected = 12
		val onError: (Throwable) -> Unit = mock()
		val onSuccess: (Int) -> Unit = mock()
		val doWork: () -> Int = mock()

		whenever(doWork.invoke()).thenAnswer { expected }

		supervisorScope {
			tasks.doTopLevelAsyncWorkThrowsException(scope = this, doWork, onError, onSuccess)
		}

		verify(doWork).invoke()
		verify(onSuccess).invoke(expected)
		verify(onError, never()).invoke(any())
	}

	@Test
	fun `GIVEN child async work throws AsyncException EXPECT AsyncException in onError callback`() = runTest {
		val expected = AsyncException(":(")
		val onError: (Throwable) -> Unit = mock()
		val onSuccess: (Int) -> Unit = mock()
		val doWork: () -> Int = mock()

		whenever(doWork.invoke()).thenAnswer { throw expected }

		supervisorScope {
			tasks.doChildAsyncWorkThrowsException(scope = this, doWork, onError, onSuccess)
		}

		verify(doWork).invoke()
		verify(onError).invoke(expected)
		verify(onSuccess, never()).invoke(any())
	}

	@Test
	fun `GIVEN child async work returns a number EXPECT number in onSuccess callback`() = runTest {
		val expected = 12
		val onError: (Throwable) -> Unit = mock()
		val onSuccess: (Int) -> Unit = mock()
		val doWork: () -> Int = mock()

		whenever(doWork.invoke()).thenAnswer { expected }

		supervisorScope {
			tasks.doChildAsyncWorkThrowsException(scope = this, doWork, onError, onSuccess)
		}

		verify(doWork).invoke()
		verify(onSuccess).invoke(expected)
		verify(onError, never()).invoke(any())
	}

	@Test
	fun `GIVEN first lambda returns number, second lambda returns exception EXPECT number and exception`() = runTest {
		val expect = 5 to AsyncException(":(")
		suspend fun doWork1(): Int = expect.first
		suspend fun doWork2(): Int = throw expect.second

		val actual = tasks.tryNotToFailInParallel(::doWork1, ::doWork2)

		assertEquals(expect, actual)
	}

	@Test
	fun `GIVEN first lambda returns exception, second lambda returns number EXPECT exception and number`() = runTest {
		val expect = AsyncException(":(") to 5
		suspend fun doWork1(): Int = throw expect.first
		suspend fun doWork2(): Int = expect.second

		val actual = tasks.tryNotToFailInParallel(::doWork1, ::doWork2)

		assertEquals(expect, actual)
	}

	@Test
	fun `GIVEN both lambdas returns numbers EXPECT numbers`() = runTest {
		val expect = 10 to 23
		suspend fun doWork1(): Int = expect.first
		suspend fun doWork2(): Int = expect.second

		val actual = tasks.tryNotToFailInParallel(::doWork1, ::doWork2)

		assertEquals(expect, actual)
	}

	@Test
	fun `GIVEN both lambdas throws exceptions EXPECT exceptions`() = runTest {
		val expect = AsyncException(":(") to AsyncException(":)")
		suspend fun doWork1(): Int = throw expect.first
		suspend fun doWork2(): Int = throw expect.second

		val actual = tasks.tryNotToFailInParallel(::doWork1, ::doWork2)

		assertEquals(expect, actual)
	}

	@Test
	fun `GIVEN lambda throws unexpected exception EXPECT unexpected exception`() = runTest {
		suspend fun doWork1(): Int = 4
		suspend fun doWork2(): Int = throw ArithmeticException("((")

		assertThrows<ArithmeticException> {
			tasks.tryNotToFailInParallel(::doWork1, ::doWork2)
		}
	}
}