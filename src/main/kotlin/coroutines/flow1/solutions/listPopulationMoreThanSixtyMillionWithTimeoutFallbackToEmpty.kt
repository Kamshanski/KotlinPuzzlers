package coroutines.flow1.solutions

import coroutines.flow1.utils.Country
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.timeout
import utils.Solution
import kotlin.time.Duration.Companion.milliseconds

suspend fun Solution.listPopulationMoreThanSixtyMillionWithTimeoutFallbackToEmpty(countries: Flow<Country>): Flow<Country> {
	return countries
		.timeout(1000.milliseconds)
		.catch { ex ->
			if (ex !is TimeoutCancellationException) throw ex
		}
		.filter { it.population >= 60_000_000 }
}