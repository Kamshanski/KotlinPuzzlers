package coroutines.flow2.solutions

import kotlinx.coroutines.flow.*
import utils.Solution

suspend fun Solution.loadingProgress(progressList: List<Flow<Int>>): Flow<Int> {
	return progressList
		.mapIndexed { flowIndex, flow ->
			flow.map { progress -> flowIndex to progress }
		}
		.merge()
		.runningFold(MutableList(progressList.size) { 0 }) { progresses, (flowIndex, progress) ->
			progresses[flowIndex] = progress
			progresses
		}
		.map { progresses ->
			val validProgresses = progresses.filter { it >= 0 }
			if (validProgresses.isNotEmpty()) {
				validProgresses.sum() / validProgresses.count()
			} else {
				-1
			}
		}
		.distinctUntilChanged()
}