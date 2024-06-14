package coroutines.flow1.solutions

import coroutines.flow1.utils.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold
import utils.Solution

suspend fun Solution.sumPopulationOfCountries(countries: Flow<Country>): Long {
	return countries
		.fold(0) { acc, item -> acc + item.population }
}