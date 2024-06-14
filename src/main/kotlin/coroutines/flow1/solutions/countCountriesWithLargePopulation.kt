package coroutines.flow1.solutions

import coroutines.flow1.utils.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import utils.Solution

suspend fun Solution.countCountriesWithLargePopulation(countries: Flow<Country>): Int {
	return countries
		.filter { it.population > 6_000_000 }
		.count()
}