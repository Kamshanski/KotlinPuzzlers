package coroutines.coroutines_exceptions.solutions

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import utils.Solution

fun Solution.doChildAsyncWorkThrowsException(
	scope: CoroutineScope,
	doWork: () -> Int,
	onError: (Throwable) -> Unit,
	onSuccess: (Int) -> Unit,
) {
	scope.launch(
		CoroutineExceptionHandler { _, ex -> onError(ex) }
	) {
		val intDeferred = async { doWork() }

		val number = intDeferred.await()
		onSuccess(number)
	}
}