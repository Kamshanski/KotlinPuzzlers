package coroutines.coroutines_exceptions.solutions

import kotlinx.coroutines.CancellationException
import utils.Solution

suspend fun Solution.tryNotToFail(function: suspend () -> Int): Any {
	return try {
		function()
	} catch (ce: CancellationException) {
		throw ce
	} catch (ex: Exception) {
		ex
	}
}