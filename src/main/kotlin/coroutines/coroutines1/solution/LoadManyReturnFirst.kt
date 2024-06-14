package coroutines.coroutines1.solution

import kotlinx.coroutines.async
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.selects.select
import utils.Solution

suspend fun Solution.loadManyReturnFirst(
	query: String,
	loadFromBing: suspend (String) -> String,
	loanFromYandex: suspend (String) -> String,
	loadFromGoogle: suspend (String) -> String,
): String =
	coroutineScope {
		select {
			async { loadFromBing(query) }.onAwait { it }
			async { loanFromYandex(query) }.onAwait { it }
			async { loadFromGoogle(query) }.onAwait { it }
		}
			.also { coroutineContext.cancelChildren() }
	}