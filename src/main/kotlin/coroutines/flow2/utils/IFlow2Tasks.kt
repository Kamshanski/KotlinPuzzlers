package coroutines.flow2.utils

import kotlinx.coroutines.flow.Flow

interface IFlow2Tasks {

	suspend fun trackFlowStartAndFinish(flow: Flow<String>): Flow<String>

	suspend fun stringsWithIncrements(strings: Flow<String>, increments: Flow<Int>): Flow<String>

	suspend fun efficientStringComputation(items: Flow<Long>, mapper: suspend (Long) -> String): List<String>

	suspend fun buildStudents(data: Flow<String?>): Flow<Student>

	suspend fun countTo3(actions: Flow<CounterAction>): Flow<Int>

	suspend fun collectIntsAndException(list: List<Flow<Int>>): Pair<List<Int>, Flow2Exception?>

	suspend fun loadingProgress(progressList: List<Flow<Int>>): Flow<Int>

	suspend fun loadingBrokenProgress(progressList: List<Flow<Int>>): Flow<Int>

	suspend fun collectMessagesFromRepository(repositories: MessagesRepository): Flow<String>
}