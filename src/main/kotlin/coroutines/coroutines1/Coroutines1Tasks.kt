package coroutines.coroutines1

import coroutines.coroutines1.utils.ICoroutines1Tasks
import coroutines.coroutines1.utils.NoDataTransformedException

class Coroutines1Tasks : ICoroutines1Tasks {

	/**
	 * Given lambda [loadData]. Lambda can return a string or throw an exception.
	 * If the lambda throws [java.io.IOException] or its descendants, repeat the request 2 more times.
	 * If in all 3 attempts the lambda only throw [java.io.IOException], return null.
	 * If a lambda throws any error other than [java.io.IOException] or its descendants, throw that error.
	 *
	 * Дана лямбда [loadData]. При вызове лямбды может вернуться строка или выброситься исключение.
	 * Если лямбда выбросила исключение [java.io.IOException] или её наследников необходимо повторить запрос ещё 2 раза.
	 * Если за все 3 попытки лямбда только выбросила [java.io.IOException], необходимо вернуть null.
	 * Если лямбда выбросила любую другую ошибку кроме [java.io.IOException] или её наследников, эта функция должна тоже выбросить эту ошибку.
	 *
	 * Hint / Подсказка: [coroutines.coroutines1.hints.loadWithRetryOrNull]
	 */
	override suspend fun loadWithRetryOrNull(loadData: suspend () -> String): String? {
		TODO()
	}

	/**
	 * Given lambda [loadData]. Lambda can return a string or throw an exception.
	 * If the lambda throws [java.io.IOException] or its descendants, repeat the request 2 more times.
	 * If in all 3 attempts the lambda only throw [java.io.IOException], throw last exception.
	 * If a lambda throws any error other than [java.io.IOException] or its descendants, throw that error.
	 *
	 * Дана лямбда [loadData]. При вызове лямбды может вернуться строка или выброситься исключение.
	 * Если лямбда выбросила исключение [java.io.IOException] или её наследников необходимо повторить запрос ещё 2 раза.
	 * Если за все 3 попытки лямбда только выбросила [java.io.IOException], необходимо выброить последнее исключение.
	 * Если лямбда выбросила любую другую ошибку кроме [java.io.IOException] или её наследников, эта функция должна тоже выбросить эту ошибку.
	 *
	 * Hint / Подсказка: [coroutines.coroutines1.hints.loadWithRetryOrThrow]
	 */
	override suspend fun loadWithRetryOrThrow(loadData: suspend () -> String): String {
		TODO()
	}

	/**
	 * Given a list of [strings]. Each string must be transformed sequentially with [transform1], [transform2], [transform3].
	 * Each of the lambdas requires some time to complete.
	 * You cannot run [transform2] before [transform1] has completed transforming all rows. Similarly, you cannot run [transform3] until all [transform2] are completed
	 * The test is limited in execution time, so the code must run as quickly as possible.
	 *
	 * Дан список строк [strings]. Каждую строку нужно преобразовать последловательно с помощью [transform1], [transform2], [transform3].
	 * Каждая из лямюд требует некоторого времени на выполнение.
	 * Нельзя запускать [transform2] до того, как завершится преобразование всех строк с помощью [transform1]. Аналогично нельзя запускать [transform3] до завершения всех [transform2]
	 * Тест ограничен по времени выполнения, поэтому код должен выполняться как можно быстрее.
	 *
	 * Hint / Подсказка: [coroutines.coroutines1.hints.processAllStagesSequentially]
	 */
	override suspend fun processAllStagesSequentially(
		strings: List<String>,
		transform1: suspend (String) -> Int,
		transform2: suspend (Int) -> Double,
		transform3: suspend (Double) -> Long,
	): List<Long> {
		TODO()
	}

	/**
	 * Given a [query] and several lambdas with which you can load data by [query]. Each lambda runs for some time.
	 * Return the result that was loaded first. Also cancel all other requests that have not yet completed.
	 *
	 * Дан запрос [query] и несколько лямбд, с помощью которых можно загрузить данные по [query]. Каждая лямбда выполняется какое-то время.
	 * Необходимо вернуть результат, загруженный раньше всех. Также нужно отменить все остальные запросы, которые ещё не завершились.
	 *
	 * Hint / Подсказка: [coroutines.coroutines1.hints.loadManyReturnFirst]
	 */
	override suspend fun loadManyReturnFirst(
		query: String,
		loadFromBing: suspend (String) -> String,
		loanFromYandex: suspend (String) -> String,
		loadFromGoogle: suspend (String) -> String,
	): String {
		TODO()
	}

	/**
	 * Given a list of [strings]. Each string needs to be transformed with [transform] lambda. [transform] takes some time to complete.
	 * [transform] may return an integer or an error.
	 * Return a list of all numbers that are converted without error.
	 * If all numbers are converted with an error, throw the [NoDataTransformedException] error.
	 * The test is limited in execution time, so the code must run as quickly as possible.
	 *
	 * Дан список строк [strings]. Каждую строку нужно преобразовать с помощью [transform]. [transform] требует некоторого времени на выполнение.
	 * [transform] может вернуть число или ошибку.
	 * Необходимо вернуть список всех чисел, которые получилось преобразовать без ошибки.
	 * Если все числа были преобразованы с ошибой, нужно выбросить ошибку [NoDataTransformedException].
	 * Тест ограничен по времени выполнения, поэтому код должен выполняться как можно быстрее.
	 *
	 * Hint / Подсказка: [coroutines.coroutines1.hints.processStringsWithExceptions]
	 */
	override suspend fun processStringsWithExceptions(
		strings: List<String>,
		transform: suspend (String) -> Int
	): List<Int> {
		TODO()
	}

	/**
	 * Given a list of strings [rawData]. Each line of it must be converted using the [longComputation] lambda. Lambda takes some time to process string.
	 * Also, it is forbidden for lambdas to be executed 5 times simultaneously. You must wait until the 4th call completes to make a new lambda call.
	 * You should convert [rawData] into a list of numbers as quickly as possible using [longComputation].
	 *
	 * Дан список строк [rawData]. Каждую строку нужно преобразовать с помощью лябмды [longComputation]. Работа лямбды затрачивает некоторое время.
	 * Также нельзя, чтобы лямбда одновременно выполнялись 5 раз. Необходимо дождаться окончания выполнения четвёртого вызова, чтобы сделать новый вызов лямбды.
	 * Нужно максимально быстро преобразовать [rawData] во список чисел с помощью [longComputation].
	 *
	 * Hint / Подсказка: [coroutines.coroutines1.hints.processStringsWithLimitOfFourSimultaneousComputations]
	 */
	override suspend fun processStringsWithLimitOfFourSimultaneousComputations(
		rawData: List<String>,
		longComputation: suspend (String) -> Int
	): List<Int> {
		TODO()
	}
}