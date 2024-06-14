package coroutines.flow1.solutions

import coroutines.flow1.utils.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.merge
import utils.Solution

suspend fun Solution.sumPopulationOfMultipleCountries(countryFlow1: Flow<Country>, countryFlow2: Flow<Country>): Long {
	return merge(countryFlow1, countryFlow2)
		.fold(0) { acc, item -> acc + item.population }
}