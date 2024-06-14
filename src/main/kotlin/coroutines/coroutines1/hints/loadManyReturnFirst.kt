package coroutines.coroutines1.hints

interface loadManyReturnFirst {
	/**
	 * Use `select` function to select the first response from async blocks.
	 * When `select` function is completed, cancel all coroutines in coroutine context.
	 *
	 * Используй функцию `select`, чтобы выбрать первый ответ от блоков async.
	 * Когда функция `select` завершится, отмени все корутины в coroutine context.
	 */
}