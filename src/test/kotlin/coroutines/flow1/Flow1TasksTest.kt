package coroutines.flow1

import coroutines.flow1.utils.Country
import coroutines.flow1.utils.IFlow1Tasks
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

open class Flow1TasksTest {

	companion object {

		val COUNTRY = Country("Nigeria", "NGN", 223804632)

		val COUNTRIES = listOf(
			COUNTRY,
			Country("Ethiopia", "ETB", 126527060),
			Country("DRC", "CDF", 93867828),
			Country("Egypt", "EGP", 104930833),
			Country("South Africa", "ZAR", 60144495),
			Country("Tanzania", "TZS", 64631543),
			Country("Kenya", "KES", 58021971),
			Country("Uganda", "UGX", 54632334),
		)

		val COUNTRIES_WITH_POPULATION_GTE_SIXTY_MILLION =
			COUNTRIES.filter { it.population >= 60_000_000 }

		val SUM_POPULATION_OF_ALL_COUNTRIES =
			COUNTRIES.sumOf { it.population }
	}

	open val flowProblems: IFlow1Tasks = Flow1Tasks()

	@Test
	fun `GIVEN countries EXPECT capitalized names`() = runTest {
		val expected = COUNTRY.name.uppercase()

		val actual = flowProblems
			.countryNameInCapitals(flowOf(COUNTRY))
			.single()

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN countries EXPECT amount of countries with population gte 1 million`() = runTest {
		val expected = COUNTRIES.size

		val actual = flowProblems
			.countCountriesWithLargePopulation(COUNTRIES.asFlow())

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN countries EXPECT only 3rd and 4th countries`() = runTest {
		val expected = listOf(
			COUNTRIES[2],
			COUNTRIES[3],
		)

		val actual = flowProblems
			.listOnly3rdAnd4thCountry(COUNTRIES.asFlow())
			.toList()

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN countries with population gte 1 million EXPECT true`() = runTest {
		val actual = flowProblems
			.isAllCountriesPopulationMoreThanOrEqualsToSixtyMillion(COUNTRIES_WITH_POPULATION_GTE_SIXTY_MILLION.asFlow())

		assertEquals(true, actual)
	}

	@Test
	fun `GIVEN countries with population less than 1 million EXPECT false`() = runTest {
		val actual = flowProblems
			.isAllCountriesPopulationMoreThanOrEqualsToSixtyMillion(COUNTRIES.asFlow())

		assertEquals(false, actual)
	}

	@Test
	fun `GIVEN countries with population gte EXPECT correct countries list`() = runTest {
		val expected = COUNTRIES_WITH_POPULATION_GTE_SIXTY_MILLION
		val countries = flow {
			COUNTRIES.forEach {
				emit(it)
				delay(20)
			}
		}

		val actual = flowProblems
			.listPopulationMoreThanSixtyMillionWithTimeoutFallbackToEmpty(countries)
			.toList()

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN countries with population less than 60 million EXPECT correct countries list`() = runTest {
		val countries = flow {
			COUNTRIES_WITH_POPULATION_GTE_SIXTY_MILLION.forEach {
				emit(it)
				delay(2000)
			}
		}
		val expected = listOf(COUNTRIES.first())

		val actual = flowProblems
			.listPopulationMoreThanSixtyMillionWithTimeoutFallbackToEmpty(countries)
			.toList()

		assertEquals(expected, actual)
	}

	@Test
	fun `GIVEN list of countries and country name from list EXPECT country currency`() = runTest {
		val country = COUNTRIES.random()

		val actual = flowProblems
			.getCurrencyOfACountryOrNone(country.name, COUNTRIES.asFlow())

		assertEquals(country.currency, actual)
	}

	@Test
	fun `GIVEN list of countries and country name not from list EXPECT none`() = runTest {
		val countryRequested = "Canada"
		val actual = flowProblems
			.getCurrencyOfACountryOrNone(countryRequested, COUNTRIES.asFlow())

		assertEquals("None", actual)
	}

	@Test
	fun `GIVEN countries EXPECT populations sum`() = runTest {
		val actual = flowProblems
			.sumPopulationOfCountries(COUNTRIES.asFlow())

		assertEquals(SUM_POPULATION_OF_ALL_COUNTRIES, actual)
	}

	@Test
	fun `GIVEN two countries flow EXPECT populations sum`() = runTest {
		val half1 = COUNTRIES.take(COUNTRIES.size / 2)
		val half2 = COUNTRIES.drop(COUNTRIES.size / 2)

		val actual = flowProblems
			.sumPopulationOfMultipleCountries(half1.asFlow(), half2.asFlow())

		assertEquals(SUM_POPULATION_OF_ALL_COUNTRIES, actual)
	}
}