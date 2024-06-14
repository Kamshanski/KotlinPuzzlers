package coroutines.coroutines_exceptions.hints

interface doWorkThrowsExceptionWithTryCatch {
	/**
	 * Try/catch does not catch exception that happens inside another coroutine.
	 * Try to move try/catch inside coroutine to catch exception from doWork.
	 *
	 * Also, never forget to throw [kotlinx.coroutines.CancellationException] if you caught one.
	 * It's necessary for structured concurrency that is implemented in coroutines.
	 * It's recommended to catch only specific exception, e.g. IOException, ArithmeticException etc. Also, avoid catching Throwable, Exception or RuntimeException.
	 * Notice that [kotlinx.coroutines.CancellationException] extends [IllegalStateException].
	 *
	 * Try/catch не перехватывает исключение, которое происходит внутри другой корутины.
	 *
	 * Попробуйте переместить try/catch внутрь корутины, чтобы перехватить исключение из doWork.
	 *
	 * Кроме того, никогда не забывайте выбрасывать [kotlinx.coroutines.CancellationException], если отловили её.
	 * Это необходимо для структурированного параллелизма (Structured Concurrency), реализованного в корутнах.
	 * Рекомендуется перехватывать только определенные исключения, например. IOException, ArithmeticException и т.д.
	 * Также избегайте перехвата Throwable, Exception или RuntimeException.
	 * Имейте в виду, что [kotlinx.coroutines.CancellationException] расширяет [IllegalStateException].
	 */
}