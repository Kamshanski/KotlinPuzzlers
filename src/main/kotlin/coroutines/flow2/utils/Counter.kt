package coroutines.flow2.utils

enum class CounterAction {
	INCREMENT,
	CLEAR,
}

class CounterException : RuntimeException()