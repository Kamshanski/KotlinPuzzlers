package coroutines.coroutines_exceptions.solutions

import coroutines.coroutines_exceptions.utils.AsyncException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import utils.Solution

fun Solution.doWorkThrowsExceptionWithTryCatch(
	scope: CoroutineScope,
	doWork: () -> Unit,
	onError: (Throwable) -> Unit
) {
	scope.launch {
		launch {
			try {
				doWork()
			} catch (exception: AsyncException) {
				onError(exception)
			}
		}
	}
}