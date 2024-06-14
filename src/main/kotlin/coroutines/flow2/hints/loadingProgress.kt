package coroutines.flow2.hints

interface loadingProgress {
	/**
	 * You may collect progresses to some kind of cache.
	 *
	 * There are several ways to collect multiple flow at one time.
	 * First, run async coroutines to collect each flow.
	 * Second, use [kotlinx.coroutines.flow.merge] to make single flow from several.
	 *
	 * [kotlinx.coroutines.flow.distinctUntilChanged] may be used to prevent repeating the same values
	 *
	 * Also consider [kotlinx.coroutines.flow.transformWhile] to stop collecting item
	 */
}