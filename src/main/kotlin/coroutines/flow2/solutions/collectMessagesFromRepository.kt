package coroutines.flow2.solutions

import coroutines.flow2.utils.MessagesRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import utils.Solution

private const val TERMINATE_MESSAGE = "."
private const val ERROR_MESSAGE = "ERROR"

fun Solution.collectMessagesFromRepository(messagesRepository: MessagesRepository): Flow<String> {
	return callbackFlow {
		val callback = object : MessagesRepository.Callback {
			override fun onNewMessage(message: String) {
				trySendBlocking(message)
				if (message == TERMINATE_MESSAGE) {
					channel.close()
				}
			}

			override fun onFinished(cause: Exception?) {
				channel.close(cause)
			}
		}
		messagesRepository.subscribeToMessages(callback)

		awaitClose { messagesRepository.unsubscribeFromMessages(callback) }
	}
		.catch {
			emit(TERMINATE_MESSAGE)
			emit(ERROR_MESSAGE)
		}
}