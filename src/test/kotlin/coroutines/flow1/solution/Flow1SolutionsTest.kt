package coroutines.flow1.solution

import coroutines.flow1.Flow1TasksTest
import coroutines.flow1.solutions.*
import coroutines.flow1.utils.Country
import coroutines.flow1.utils.IFlow1Tasks
import kotlinx.coroutines.flow.Flow
import utils.Solution

class Flow1SolutionsTest : Flow1TasksTest() {

	override val flowProblems: IFlow1Tasks = Solutions()
}

private class Solutions : IFlow1Tasks {
	override suspend fun countryNameInCapitals(country: Flow<Country>): Flow<String> =
		Solution.countryNameInCapitals(country)

	override suspend fun listOnly3rdAnd4thCountry(countries: Flow<Country>): Flow<Country> =
		Solution.listOnly3rdAnd4thCountry(countries)

	override suspend fun countCountriesWithLargePopulation(countries: Flow<Country>): Int =
		Solution.countCountriesWithLargePopulation(countries)

	override suspend fun isAllCountriesPopulationMoreThanOrEqualsToSixtyMillion(countries: Flow<Country>): Boolean =
		Solution.isAllCountriesPopulationMoreThanOrEqualsToSixtyMillion(countries)

	override suspend fun listPopulationMoreThanSixtyMillionWithTimeoutFallbackToEmpty(countries: Flow<Country>): Flow<Country> =
		Solution.listPopulationMoreThanSixtyMillionWithTimeoutFallbackToEmpty(countries)

	override suspend fun getCurrencyOfACountryOrNone(countryName: String, countries: Flow<Country>): String =
		Solution.getCurrencyOfACountryOrNone(countryName, countries)

	override suspend fun sumPopulationOfCountries(countries: Flow<Country>): Long =
		Solution.sumPopulationOfCountries(countries)

	override suspend fun sumPopulationOfMultipleCountries(
		countryFlow1: Flow<Country>,
		countryFlow2: Flow<Country>
	): Long =
		Solution.sumPopulationOfMultipleCountries(countryFlow1, countryFlow2)

}