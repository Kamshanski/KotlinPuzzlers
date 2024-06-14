package coroutines.flow2.utils

interface MessagesRepository {

	fun subscribeToMessages(callback: Callback)

	fun unsubscribeFromMessages(callback: Callback)

	interface Callback {
		/**
		 * Invoked when new [message] is loaded
		 */
		fun onNewMessage(message: String)

		/**
		 * Invoked when no more messages.
		 * [cause] is null, if all messages is sent successfully
		 * [cause] is not null, if exception occured while
		 */
		fun onFinished(cause: Exception?)
	}
}