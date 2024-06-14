package coroutines.coroutines1.solution

import coroutines.coroutines1.utils.NoDataTransformedException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import utils.Solution

suspend fun Solution.processStringsWithExceptions(
	strings: List<String>,
	transform: suspend (String) -> Int
): List<Int> {
	val ints = coroutineScope {
		strings
			.map {
				async {
					try {
						transform(it)
					} catch (ex: CancellationException) {
						throw ex
					} catch (ex: Exception) {
						null
					}
				}
			}
			.mapNotNull { it.await() }
	}

	if (strings.isNotEmpty() && ints.isEmpty()) {
		throw NoDataTransformedException()
	}

	return ints
}