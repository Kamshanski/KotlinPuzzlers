package coroutines.flow2.util

import coroutines.flow2.utils.MessagesRepository
import coroutines.flow2.utils.MessagesRepository.Callback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MessagesRepositoryImpl(
	private val scope: CoroutineScope,
	private val performLoading: suspend (Callback) -> Unit,
) : MessagesRepository {
	private var callback: Callback? = null
	private var job: Job? = null

	override fun subscribeToMessages(callback: Callback) {
		this.callback = callback
		if (this.job != null)
			throw IllegalStateException("Another loading is running now")

		startLoading(callback)
	}

	override fun unsubscribeFromMessages(callback: Callback) {
		this.callback = null
		job?.cancel()
	}

	private fun startLoading(callback: Callback) {
		job = scope.launch {
			performLoading(callback)
		}
	}

	fun isCallbackSet(): Boolean =
		callback != null
}