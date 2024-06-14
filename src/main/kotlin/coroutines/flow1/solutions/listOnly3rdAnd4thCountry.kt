package coroutines.flow1.solutions

import coroutines.flow1.utils.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.take
import utils.Solution

suspend fun Solution.listOnly3rdAnd4thCountry(countries: Flow<Country>): Flow<Country> {
	return countries
		.drop(2)
		.take(2)
}