package coroutines.coroutines_exceptions.utils

data class AsyncException(override val message: String, override val cause: Throwable? = null) :
	RuntimeException(message, cause)