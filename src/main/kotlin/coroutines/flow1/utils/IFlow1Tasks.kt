package coroutines.flow1.utils

import kotlinx.coroutines.flow.Flow

interface IFlow1Tasks {

	suspend fun countryNameInCapitals(country: Flow<Country>): Flow<String>

	suspend fun listOnly3rdAnd4thCountry(countries: Flow<Country>): Flow<Country>

	suspend fun countCountriesWithLargePopulation(countries: Flow<Country>): Int

	suspend fun isAllCountriesPopulationMoreThanOrEqualsToSixtyMillion(countries: Flow<Country>): Boolean

	suspend fun listPopulationMoreThanSixtyMillionWithTimeoutFallbackToEmpty(countries: Flow<Country>): Flow<Country>

	suspend fun getCurrencyOfACountryOrNone(countryName: String, countries: Flow<Country>): String

	suspend fun sumPopulationOfCountries(countries: Flow<Country>): Long

	suspend fun sumPopulationOfMultipleCountries(countryFlow1: Flow<Country>, countryFlow2: Flow<Country>): Long
}