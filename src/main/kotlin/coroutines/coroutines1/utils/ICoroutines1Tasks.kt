package coroutines.coroutines1.utils

interface ICoroutines1Tasks {

	suspend fun loadWithRetryOrNull(loadData: suspend () -> String): String?

	suspend fun loadWithRetryOrThrow(loadData: suspend () -> String): String

	suspend fun processAllStagesSequentially(
		strings: List<String>,
		transform1: suspend (String) -> Int,
		transform2: suspend (Int) -> Double,
		transform3: suspend (Double) -> Long,
	): List<Long>

	suspend fun loadManyReturnFirst(
		query: String,
		loadFromBing: suspend (String) -> String,
		loanFromYandex: suspend (String) -> String,
		loadFromGoogle: suspend (String) -> String,
	): String

	suspend fun processStringsWithExceptions(strings: List<String>, transform: suspend (String) -> Int): List<Int>

	suspend fun processStringsWithLimitOfFourSimultaneousComputations(
		rawData: List<String>,
		longComputation: suspend (String) -> Int
	): List<Int>
}