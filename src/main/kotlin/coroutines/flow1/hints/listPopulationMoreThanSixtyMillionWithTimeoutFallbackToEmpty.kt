package coroutines.flow1.hints

interface listPopulationMoreThanSixtyMillionWithTimeoutFallbackToEmpty {
	/**
	 * [kotlinx.coroutines.flow.timeout] operator sets timeout to flow to emit new item. If timeout is exceeded, [kotlinx.coroutines.TimeoutCancellationException] is thrown
	 * You can catch with exception with [kotlinx.coroutines.flow.catch]
	 */
}