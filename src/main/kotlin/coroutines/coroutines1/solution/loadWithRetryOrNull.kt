package coroutines.coroutines1.solution

import utils.Solution
import java.io.IOException

suspend fun Solution.loadWithRetryOrNull(loadData: suspend () -> String): String? {
	repeat(3) {
		try {
			val string = loadData()
			return string
		} catch (_: IOException) {
			// Ignore
		}
	}
	return null
}