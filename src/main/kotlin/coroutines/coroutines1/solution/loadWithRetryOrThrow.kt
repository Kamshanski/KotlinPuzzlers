package coroutines.coroutines1.solution

import utils.Solution
import java.io.IOException

suspend fun Solution.loadWithRetryOrThrow(loadData: suspend () -> String): String {
	repeat(2) {
		try {
			return loadData()
		} catch (ex: IOException) {
			// Ignore
		}
	}
	return loadData()
}