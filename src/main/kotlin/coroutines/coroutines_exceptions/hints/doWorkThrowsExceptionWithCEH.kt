package coroutines.coroutines_exceptions.hints

interface doWorkThrowsExceptionWithCEH {
	/**
	 * [kotlinx.coroutines.CoroutineExceptionHandler] only works with scope or top-level coroutines.
	 *
	 * From documentation:
	 * CoroutineExceptionHandler is a last-resort mechanism for global "catch all" behavior.
	 * You cannot recover from the exception in the CoroutineExceptionHandler.
	 * The coroutine had already completed with the corresponding exception when the handler is called.
	 * Normally, the handler is used to log the exception, show some kind of error message, terminate, and/ or restart the application.
	 * If you need to handle exception in a specific part of the code, it is recommended to use try/catch around the corresponding code inside your coroutine.
	 * This way you can prevent completion of the coroutine with the exception (exception is now caught), retry the operation, and/ or take other arbitrary actions.
	 *
	 * [kotlinx.coroutines.CoroutineExceptionHandler] работает только со скоупом или верхнеуровневыми корутинами.
	 *
	 * Из документации:
	 * CoroutineExceptionHandler это последнее средство для глобального перехвата всех исключений.
	 * Нельзя восстановится после обработки исключения в CoroutineExceptionHandler.
	 * Корутина на момент вызова CEH уже завершилась с соответствующим исключением.
	 * Обычно, CEH используется для логгирования исключений или рестартов приложения.
	 * Если вам нужно обработать исключение в конкретной части кода, рекомендуется использовать try/catch вокруго этого участка кода.
	 * Таким образом вы предотвратите остановку корутины с выброшенной ошибкой. Вы можете повторить какую-то операцию, обработать ошибку или выполнить другую логику.
	 */
}