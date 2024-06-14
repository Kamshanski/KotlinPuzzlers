package coroutines.flow1.solutions

import coroutines.flow1.utils.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold
import utils.Solution

suspend fun Solution.isAllCountriesPopulationMoreThanOrEqualsToSixtyMillion(countries: Flow<Country>): Boolean {
	return countries
		.all { it.population >= 60_000_000 }
}

private suspend fun <T> Flow<T>.all(predicate: (T) -> Boolean): Boolean =
	fold(true) { result, item ->
		if (!predicate(item)) {
			false
		} else {
			result
		}
	}