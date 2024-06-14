package coroutines.flow2.solutions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.runningReduce
import utils.Solution

suspend fun Solution.stringsWithIncrements(strings: Flow<String>, increments: Flow<Int>): Flow<String> {
	val prices = increments.runningReduce { acc, increment -> acc + increment }
	return strings.combine(prices) { str, num -> "${str}_${num}" }
}