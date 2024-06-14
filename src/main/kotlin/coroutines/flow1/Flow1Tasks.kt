package coroutines.flow1

import coroutines.flow1.utils.Country
import coroutines.flow1.utils.IFlow1Tasks
import kotlinx.coroutines.flow.Flow

class Flow1Tasks : IFlow1Tasks {

	/**
	 * Return flow of capitalized countries names
	 *
	 * Верни флоу названий стран в заглавными буквами буквами
	 *
	 * Hint / Подсказка: [coroutines.flow1.hints.countryNameInCapitals]
	 */
	override suspend fun countryNameInCapitals(country: Flow<Country>): Flow<String> {
		TODO()
	}

	/**
	 * Return only 3rd and 4th countries from a flow
	 *
	 * Верни только 3ю и 4ю страны из флоу
	 *
	 * Hint / Подсказка: [coroutines.flow1.hints.listOnly3rdAnd4thCountry]
	 */
	override suspend fun listOnly3rdAnd4thCountry(countries: Flow<Country>): Flow<Country> {
		TODO()
	}

	/**
	 * Count countries that population is >= 6 million people
	 *
	 * Посчитай количество стран с населением >= 1 миллионов
	 *
	 * Hint / Подсказка: [coroutines.flow1.hints.countCountriesWithLargePopulation]
	 */
	override suspend fun countCountriesWithLargePopulation(countries: Flow<Country>): Int {
		TODO()
	}

	/**
	 * Verify if all countries in a flow have population >= 1 million people
	 * Return false if flow is empty
	 *
	 * Проверь, все ли страны во флоу имеют население >= 6 миллионов
	 * Если флоу пустое, верни false
	 *
	 * Hint / Подсказка: [coroutines.flow1.hints.isAllCountriesPopulationMoreThanOrEqualsToSixtyMillion]
	 */
	override suspend fun isAllCountriesPopulationMoreThanOrEqualsToSixtyMillion(countries: Flow<Country>): Boolean {
		TODO()
	}

	/**
	 * Return a flow of countries that has population >= 1 million.
	 * Countries flow emits countries with random delay. Stop collecting countries if no country is received in 1 second since last country.
	 *
	 * Вернуть поток стран с населением >= 6 миллиона.
	 * Флоу стран шлёт страны со случайной задержкой. Прекрати сбор стран, если ни одна страна не была получена в течение 1 секунды с момента последней страны.
	 *
	 * Hint / Подсказка: [coroutines.flow1.hints.listPopulationMoreThanSixtyMillionWithTimeoutFallbackToEmpty]
	 */
	override suspend fun listPopulationMoreThanSixtyMillionWithTimeoutFallbackToEmpty(countries: Flow<Country>): Flow<Country> {
		TODO()
	}

	/**
	 * In the flow, find the country with the given name. Get its currency.
	 * If the country was not found, then return "None"
	 *
	 * Во флоу найди страну с заданным именем. Достань её валюту.
	 * Если страна не была найдена, то верни "None"
	 *
	 * Hint / Подсказка: [coroutines.flow1.hints.getCurrencyOfACountryOrNone]
	 */
	override suspend fun getCurrencyOfACountryOrNone(countryName: String, countries: Flow<Country>): String {
		TODO()
	}

	/**
	 * Sum population of all countries
	 *
	 * Просуммируй население всех стран
	 *
	 * Hint / Подсказка: [coroutines.flow1.hints.sumPopulationOfCountries]
	 */
	override suspend fun sumPopulationOfCountries(countries: Flow<Country>): Long {
		TODO()
	}

	/**
	 * Sum population of all countries. Try not to collect first flow and then second flow
	 *
	 * Просуммируй население всех стран. Попробуй не собирать сначала первое флоу, а потом второе
	 *
	 * Hint / Подсказка: [coroutines.flow1.hints.sumPopulationOfMultipleCountries]
	 */
	override suspend fun sumPopulationOfMultipleCountries(
		countryFlow1: Flow<Country>,
		countryFlow2: Flow<Country>
	): Long {
		TODO()
	}
}