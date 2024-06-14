package coroutines.coroutines_exceptions.hints

interface tryNotToFailInParallel {
	/**
	 * To execute async inside suspend function, use [kotlinx.coroutines.coroutineScope] or [kotlinx.coroutines.supervisorScope]
	 * Use ordinary Koltin try/catch blocks
	 *
	 * Чтобы запустить async внутри suspend функции, используй [kotlinx.coroutines.coroutineScope] или [kotlinx.coroutines.supervisorScope]
	 * Используй обычную конструкцию Koltin'а try/catch
	 */
}