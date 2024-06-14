package coroutines.flow2.solutions

import coroutines.flow2.utils.Flow2Exception
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import utils.Solution
import java.util.concurrent.atomic.AtomicReference

suspend fun Solution.collectIntsAndException(list: List<Flow<Int>>): Pair<List<Int>, Flow2Exception?> {
	val lastException = AtomicReference<Flow2Exception?>(null)

	val items = coroutineScope {
		list
			.map { flow ->
				async {
					val items = mutableListOf<Int>()
					flow
						.onEach { items.add(it) }
						.catch { ex -> if (ex !is Flow2Exception) throw ex else lastException.set(ex) }
						.collect()
					return@async items
				}
			}
			.flatMap { it.await() }
	}

	return items to lastException.get()
}