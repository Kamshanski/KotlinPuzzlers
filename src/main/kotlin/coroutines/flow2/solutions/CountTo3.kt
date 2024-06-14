package coroutines.flow2.solutions

import coroutines.flow2.utils.CounterAction
import coroutines.flow2.utils.CounterException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.runningFold
import utils.Solution

suspend fun Solution.countTo3(actions: Flow<CounterAction>): Flow<Int> =
	actions
		.runningFold(0) { acc, value ->
			val newAcc = when (value) {
				CounterAction.INCREMENT -> acc + 1
				CounterAction.CLEAR -> 0
			}
			if (newAcc >= 3) {
				throw CounterException()
			}
			return@runningFold newAcc
		}
		.drop(1)
		.distinctUntilChanged()