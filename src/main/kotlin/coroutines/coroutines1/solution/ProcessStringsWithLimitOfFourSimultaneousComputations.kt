package coroutines.coroutines1.solution

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import utils.Solution

suspend fun Solution.processStringsWithLimitOfFourSimultaneousComputations(
	rawData: List<String>,
	longComputation: suspend (String) -> Int
): List<Int> {
	val semaphore = Semaphore(4)

	return coroutineScope {
		rawData
			.map {
				async {
					semaphore.acquire()
					val int = longComputation(it)
					semaphore.release()
					return@async int
				}
			}
			.map { it.await() }
	}
}