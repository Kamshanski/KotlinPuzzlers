package coroutines.coroutines_exceptions.utils

import kotlinx.coroutines.CoroutineScope

interface ICoroutinesExceptionsTasks {

	suspend fun tryNotToFail(function: suspend () -> Int): Any

	fun doWorkThrowsExceptionWithTryCatch(
		scope: CoroutineScope,
		doWork: () -> Unit,
		onError: (Throwable) -> Unit,
	)

	fun doWorkThrowsExceptionWithCEH(
		scope: CoroutineScope,
		doWork: () -> Unit,
		onError: (Throwable) -> Unit,
	)

	fun doTopLevelAsyncWorkThrowsException(
		scope: CoroutineScope,
		doWork: () -> Int,
		onError: (Throwable) -> Unit,
		onSuccess: (Int) -> Unit,
	)

	fun doChildAsyncWorkThrowsException(
		scope: CoroutineScope,
		doWork: () -> Int,
		onError: (Throwable) -> Unit,
		onSuccess: (Int) -> Unit,
	)

	suspend fun tryNotToFailInParallel(
		doWork1: suspend () -> Int,
		doWork2: suspend () -> Int,
	): Pair<Any, Any>
}