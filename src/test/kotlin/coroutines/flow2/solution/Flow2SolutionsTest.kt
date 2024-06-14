package coroutines.flow2.solution

import coroutines.flow2.Flow2TasksTest
import coroutines.flow2.solutions.*
import coroutines.flow2.utils.*
import kotlinx.coroutines.flow.Flow
import utils.Solution

class Flow2SolutionsTest : Flow2TasksTest() {

	override val flowProblems: IFlow2Tasks = Solutions()
}

private class Solutions : IFlow2Tasks {

	override suspend fun trackFlowStartAndFinish(flow: Flow<String>): Flow<String> =
		Solution.trackFlowStartAndFinish(flow)

	override suspend fun stringsWithIncrements(strings: Flow<String>, increments: Flow<Int>): Flow<String> =
		Solution.stringsWithIncrements(strings, increments)

	override suspend fun efficientStringComputation(items: Flow<Long>, mapper: suspend (Long) -> String): List<String> =
		Solution.efficientStringComputation(items, mapper)

	override suspend fun buildStudents(data: Flow<String?>): Flow<Student> =
		Solution.buildStudents(data)

	override suspend fun countTo3(actions: Flow<CounterAction>): Flow<Int> =
		Solution.countTo3(actions)

	override suspend fun collectIntsAndException(list: List<Flow<Int>>): Pair<List<Int>, Flow2Exception?> =
		Solution.collectIntsAndException(list)

	override suspend fun loadingProgress(progressList: List<Flow<Int>>): Flow<Int> =
		Solution.loadingProgress(progressList)

	override suspend fun loadingBrokenProgress(progressList: List<Flow<Int>>): Flow<Int> =
		Solution.loadingBrokenProgress(progressList)

	override suspend fun collectMessagesFromRepository(repositories: MessagesRepository): Flow<String> =
		Solution.collectMessagesFromRepository(repositories)
}