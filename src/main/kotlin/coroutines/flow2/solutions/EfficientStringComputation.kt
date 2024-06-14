package coroutines.flow2.solutions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import utils.Solution

suspend fun Solution.efficientStringComputation(items: Flow<Long>, mapper: suspend (Long) -> String): List<String> {
	return items
		.buffer()
		.map(mapper)
		.toList()
}