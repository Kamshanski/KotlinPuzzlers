package coroutines.flow2.solutions

import coroutines.flow2.utils.Student
import kotlinx.coroutines.flow.*
import utils.Solution

fun Solution.buildStudents(data: Flow<String?>): Flow<Student> {
	val existingMajors = mutableSetOf<String>()
	return data
		.chunked(3)
		.map {
			Student(
				it.getOrNull(0),
				it.getOrNull(1)?.toIntOrNull(),
				it.get(2)!!,
			)
		}
		.filter { it.name != null && it.major !in existingMajors }
		.onEach { existingMajors.add(it.major) }
}