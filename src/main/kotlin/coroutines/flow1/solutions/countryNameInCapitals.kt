package coroutines.flow1.solutions

import coroutines.flow1.utils.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import utils.Solution

suspend fun Solution.countryNameInCapitals(country: Flow<Country>): Flow<String> {
	return country
		.map { it.name.uppercase() }
}