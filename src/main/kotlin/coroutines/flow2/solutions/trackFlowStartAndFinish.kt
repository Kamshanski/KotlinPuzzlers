package coroutines.flow2.solutions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import utils.Solution

suspend fun Solution.trackFlowStartAndFinish(flow: Flow<String>): Flow<String> {
	return flow
		.onStart { emit("STARTED") }
		.onCompletion { ex -> if (ex == null) emit("COMPLETED") }
		.catch { emit("ERROR") }
}