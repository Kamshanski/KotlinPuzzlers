package coroutines.coroutines_exceptions.hints

interface doTopLevelAsyncWorkThrowsException {
	/**
	 * Top-level deferred throws exception thrown during coroutine execution when await() called.
	 * Use ordinary Koltin try/catch blocks
	 *
	 * Верхнеуровневые deferred выбрасывают ошибку, которая произошла вов время выполнения корутины, при вызове await().
	 * Используй обычную конструкцию Koltin'а try/catch
	 */
}