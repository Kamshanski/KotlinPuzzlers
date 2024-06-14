package coroutines.coroutines_exceptions.solutions

import coroutines.coroutines_exceptions.utils.AsyncException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import utils.Solution

fun Solution.doTopLevelAsyncWorkThrowsException(
	scope: CoroutineScope,
	doWork: () -> Int,
	onError: (Throwable) -> Unit,
	onSuccess: (Int) -> Unit,
) {
	val intDeferred = scope.async { doWork() }

	scope.launch {
		try {
			val number = intDeferred.await()
			onSuccess(number)
		} catch (e: AsyncException) {
			onError(e)
		}
	}
}