package coroutines.coroutines_exceptions.solutions

import coroutines.coroutines_exceptions.utils.AsyncException
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import utils.Solution

suspend fun Solution.tryNotToFailInParallel(
	doWork1: suspend () -> Int,
	doWork2: suspend () -> Int,
): Pair<Any, Any> {
	return supervisorScope {
		val intDeferred1 = async { doWork1() }
		val intDeferred2 = async { doWork2() }

		val int1 = try {
			intDeferred1.await()
		} catch (ex: AsyncException) {
			ex
		}

		val int2 = try {
			intDeferred2.await()
		} catch (ex: AsyncException) {
			ex
		}

		return@supervisorScope int1 to int2
	}
}