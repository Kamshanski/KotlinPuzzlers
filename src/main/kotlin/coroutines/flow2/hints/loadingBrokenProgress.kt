package coroutines.flow2.hints

interface loadingBrokenProgress {
	/**
	 * Hint is the same as in [coroutines.flow2.hints.loadingProgress]
	 *
	 * But consider [kotlinx.coroutines.flow.transformWhile] to stop collecting items when all flows returned -1.
	 */
}