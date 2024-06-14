package coroutines.flow1.solutions

import coroutines.flow1.utils.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import utils.Solution

suspend fun Solution.getCurrencyOfACountryOrNone(countryName: String, countries: Flow<Country>): String {
	return countries
		.firstOrNull { it.name == countryName }
		?.currency
		?: "None"
}