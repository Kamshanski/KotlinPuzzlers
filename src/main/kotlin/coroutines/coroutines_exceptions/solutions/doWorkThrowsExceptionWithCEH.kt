package coroutines.coroutines_exceptions.solutions

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import utils.Solution

fun Solution.doWorkThrowsExceptionWithCEH(
	scope: CoroutineScope,
	doWork: () -> Unit,
	onError: (Throwable) -> Unit
) {
	scope.launch(
		CoroutineExceptionHandler { _, ex ->
			onError(ex)
		}
	) {
		launch {
			doWork()
		}
	}
}