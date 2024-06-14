package coroutines.flow2.utils

data class Flow2Exception(override val message: String, override val cause: Throwable? = null) :
	RuntimeException(message, cause)