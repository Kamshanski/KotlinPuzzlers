package coroutines.flow2.hints

interface countTo3 {
	/**
	 * Consider [kotlinx.coroutines.flow.runningFold]. Its accumulation value can be counter value.
	 * CounterAction from flow will change this accumulation value
	 * Notice that runningFold emits its initial value.
	 * To skip emitting value that is the same as previous value try [kotlinx.coroutines.flow.distinctUntilChanged]
	 */
}