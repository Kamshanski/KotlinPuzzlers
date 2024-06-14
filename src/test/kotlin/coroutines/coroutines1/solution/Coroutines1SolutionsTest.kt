package coroutines.coroutines1.solution

import coroutines.coroutines1.Coroutines1TasksTest
import coroutines.coroutines1.utils.ICoroutines1Tasks
import utils.Solution

class Coroutines1SolutionsTest : Coroutines1TasksTest() {

	override val asyncProblems: ICoroutines1Tasks = Solutions()
}

private class Solutions : ICoroutines1Tasks {
	override suspend fun loadWithRetryOrNull(loadData: suspend () -> String): String? =
		Solution.loadWithRetryOrNull(loadData)

	override suspend fun loadWithRetryOrThrow(loadData: suspend () -> String): String =
		Solution.loadWithRetryOrThrow(loadData)

	override suspend fun processAllStagesSequentially(
		strings: List<String>,
		transform1: suspend (String) -> Int,
		transform2: suspend (Int) -> Double,
		transform3: suspend (Double) -> Long
	): List<Long> =
		Solution.processAllStagesSequentially(strings, transform1, transform2, transform3)

	override suspend fun loadManyReturnFirst(
		query: String,
		loadFromBing: suspend (String) -> String,
		loanFromYandex: suspend (String) -> String,
		loadFromGoogle: suspend (String) -> String
	): String =
		Solution.loadManyReturnFirst(query, loadFromBing, loanFromYandex, loadFromGoogle)

	override suspend fun processStringsWithExceptions(
		strings: List<String>,
		transform: suspend (String) -> Int
	): List<Int> =
		Solution.processStringsWithExceptions(strings, transform)

	override suspend fun processStringsWithLimitOfFourSimultaneousComputations(
		rawData: List<String>,
		longComputation: suspend (String) -> Int
	): List<Int> =
		Solution.processStringsWithLimitOfFourSimultaneousComputations(rawData, longComputation)

}


