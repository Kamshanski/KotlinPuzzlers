package coroutines.coroutines1.solution

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import utils.Solution

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
 * Hint / Подсказка: [coroutines.hints.CoroutinesProblems1Hint_processAllStagesSequentially]
 */
suspend fun Solution.processAllStagesSequentially(
	strings: List<String>,
	transform1: suspend (String) -> Int,
	transform2: suspend (Int) -> Double,
	transform3: suspend (Double) -> Long,
): List<Long> =
	coroutineScope {
		strings
			.map { async { transform1(it) } }
			.map { it.await() }
			.map { async { transform2(it) } }
			.map { it.await() }
			.map { async { transform3(it) } }
			.map { it.await() }
	}