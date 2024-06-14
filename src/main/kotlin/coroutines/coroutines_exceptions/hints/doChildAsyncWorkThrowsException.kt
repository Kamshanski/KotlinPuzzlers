package coroutines.coroutines_exceptions.hints

interface doChildAsyncWorkThrowsException {
	/**
	 * Child coroutines launched with async according to structured concurrency throw an exception to the parent.
	 * Use ordinary Koltin try/catch blocks or CoroutineExceptionHandle
	 *
	 * Дочерние корутины, запускаемые с async согласно structured concurency пробрасывают ошибку родителю.
	 * Используй обычную конструкцию Koltin'а try/catch или CoroutineExceptionHandle
	 */
}