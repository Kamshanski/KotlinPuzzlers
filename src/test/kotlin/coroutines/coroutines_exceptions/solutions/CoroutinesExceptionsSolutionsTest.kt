package coroutines.coroutines_exceptions.solutions

import coroutines.coroutines_exceptions.CoroutinesExceptionsTasksTest
import coroutines.coroutines_exceptions.utils.ICoroutinesExceptionsTasks
import kotlinx.coroutines.CoroutineScope
import utils.Solution

class CoroutinesExceptionsSolutionsTest : CoroutinesExceptionsTasksTest() {

	override val tasks: ICoroutinesExceptionsTasks = Solutions()
}

private class Solutions : ICoroutinesExceptionsTasks {

	override suspend fun tryNotToFail(function: suspend () -> Int): Any =
		Solution.tryNotToFail(function)

	override fun doWorkThrowsExceptionWithTryCatch(
		scope: CoroutineScope,
		doWork: () -> Unit,
		onError: (Throwable) -> Unit,
	) =
		Solution.doWorkThrowsExceptionWithTryCatch(scope, doWork, onError)

	override fun doWorkThrowsExceptionWithCEH(
		scope: CoroutineScope,
		doWork: () -> Unit,
		onError: (Throwable) -> Unit
	) {
		Solution.doWorkThrowsExceptionWithCEH(scope, doWork, onError)
	}

	override fun doTopLevelAsyncWorkThrowsException(
		scope: CoroutineScope,
		doWork: () -> Int,
		onError: (Throwable) -> Unit,
		onSuccess: (Int) -> Unit
	) {
		Solution.doTopLevelAsyncWorkThrowsException(scope, doWork, onError, onSuccess)
	}

	override fun doChildAsyncWorkThrowsException(
		scope: CoroutineScope,
		doWork: () -> Int,
		onError: (Throwable) -> Unit,
		onSuccess: (Int) -> Unit
	) {
		Solution.doChildAsyncWorkThrowsException(scope, doWork, onError, onSuccess)
	}

	override suspend fun tryNotToFailInParallel(
		doWork1: suspend () -> Int,
		doWork2: suspend () -> Int
	): Pair<Any, Any> =
		Solution.tryNotToFailInParallel(doWork1, doWork2)
}