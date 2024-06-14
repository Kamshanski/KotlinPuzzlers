package coroutines.flow2

import coroutines.flow2.utils.*
import kotlinx.coroutines.flow.Flow

class Flow2Tasks : IFlow2Tasks {

	/**
	 * Given flow of strings. Return flow that emits "STARTED" at start.
	 * Then if flow completes successfully, emit "COMPLETED".
	 * If flow completes with exception, emit "ERROR".
	 *
	 * Given flow of strings. Return flow that emits "STARTED" at start.
	 * Then if flow completes successfully, emit "COMPLETED".
	 * If flow completes with exception, emit "ERROR".
	 *
	 * Hint / Подсказка: [coroutines.flow2.hints.trackFlowStartAndFinish]
	 * Solution / Решение: [coroutines.flow2.solutions.trackFlowStartAndFinish]
	 */
	override suspend fun trackFlowStartAndFinish(flow: Flow<String>): Flow<String> {
		TODO()
	}

	/**
	 * A flow of strings and a flow of numbers are given.
	 * It is necessary to return a flow that will merge the last line from the first flow with the sum of all numbers that came from the second flow.
	 *
	 * Дан флоу строк и флоу чисел.
	 * Необходимо вернуть поток, который будет склеивать последнюю строку из первого потока с суммой всех числе пришедших из второго потока.
	 *
	 * Example / Пример:
	 * strings:    	[ 	-> A	-> B	-> 		-> 		-> C	-> D	]
	 * increments: 	[ 0 -> 		-> 		-> 2	-> 3	-> 		-> 		]
	 * output:		[ 	-> A_0	-> B_0	-> B_2	-> B_5	-> C_5	-> D_10	]
	 *
	 * Hint / Подсказка: [coroutines.flow2.hints.stringsWithIncrements]
	 * Solution / Решение: [coroutines.flow2.solutions.stringsWithIncrements]
	 */
	override suspend fun stringsWithIncrements(strings: Flow<String>, increments: Flow<Int>): Flow<String> {
		TODO()
	}

	/**
	 * Дан флоу, который выдаёт числа каждые 100 мс. Есть функиця, которая преобразует это число в строку за 300 мс.
	 * Необходимо преобразовать все числа максимально быстрым способом. Т.е. если всего эмиттер выпустит всего 10 чисел, то эта функция должна отработать
	 * не более чем за 3_100 мс + небольшое время ошибки на работу кода корутин, а не за 4_000 мс.
	 *
	 * Given a flow that produces numbers every 100 ms. There is a function that converts this number into a string in 300 ms.
	 * It is necessary to convert all numbers as quickly as possible. Those, if the emitter produces only 10 numbers, then this function should work
	 * in no more than 3_100 ms + a small time for the coroutine internal code work, but not in 4_000 ms.
	 *
	 * Hint / Подсказка: [coroutines.flow2.hints.efficientStringComputation]
	 * Solution / Решение: [coroutines.flow2.solutions.efficientStringComputation]
	 */
	override suspend fun efficientStringComputation(items: Flow<Long>, mapper: suspend (Long) -> String): List<String> {
		TODO()
	}

	/**
	 * Given the flow of strings. The flow gives out the name, age and major one by ony. [Student] class describes this structure.
	 * Name and age may be null.
	 * This order never changes. It`s guaranteed that for each person each field value or null will be returned.
	 * In [Student] class age filed is [Int]. You must cast string to number.
	 * But the age field can be non-numeric. For example, "23y.o.". Such cases are valid. Just pass null to [Student] object.
	 * Students' major may be repeated. The final flow should only contain the first student with such major.
	 * Repeated ages and names are allowed.
	 * Students whose names are null should not be in the final flow.
	 *
	 * Return the flow of people Flow<[Student]> collected from the original data flow.
	 *
	 * Дано флоу строк. Флоу поочереди выдаёт имя, возраст и специальность. Класс [Student] описывает эту структуру.
	 * Имя и возраст могут быть null.
	 * Этот порядок никогда не меняется. Гарантированно, что для каждого человека придёт каждое значение поля или null.
	 * В классе [Student] возраст это [Int]. Нужно преобразовать строку в число.
	 * Поле age может быть нечисловым. Например, "23y.o.". Такие случаи допускаются, и в модель [Student] запиши null.
	 * Специальность студентов может повторяться. В итоговом флоу должен быть только первый студент с такой специальностью.
	 * Повторяющийся возраст и имена разрешены.
	 * Студенты, у которых имена null, не должны быть в итоговом флоу.
	 *
	 * Необходимо вернуть флоу людей Flow<[Student]>, собранных из изначального потока данных.
	 *
	 * Example / Пример:
	 *  data  : "Sam", "22", "IT",
	 *          "Anna", null, "Biotechnology",
	 *          "Ramis", "19 years", "Documentation Studies",
	 *          "Michael", "21 years", "IT",
	 *  humans: Human("Sam", 23, "IT"),
	 *          Human("Anna", null, "Architecture"),
	 *          Human("Ramis", null, "Documentation Studies"),
	 *          // Michael is not returned, because his major is the same as Sam`s major.
	 *
	 * Hint / Подсказка: [coroutines.flow2.hints.buildStudents]
	 * Solution / Решение: [coroutines.flow2.solutions.buildStudents]
	 */
	override suspend fun buildStudents(data: Flow<String?>): Flow<Student> {
		TODO()
	}

	/**
	 * Flow<IntAction> эммитит 2 команды счётчику: INCREMENT и CLEAR.
	 * Необходимо вернуть флоу Flow<Int>, которое мапит команды в значения счётчика.
	 * Счётчик работает по следующим правилам:
	 * 	Начальное значение счётчика - 0, но отсылать начальное занчение во флоу не нужно.
	 * 	Если приходит коменда INCREMENT, то нужно увеличить счетчик на единицу и отослать новое значение.
	 * 	Если счетчик становится == 3, то выбрасываем исключение CounterException
	 * 	Если приходит команда CLEAR, то нужно обнулить счетчик и отослать 0 во флоу.
	 * 	Флоу не должно выдавать повторяющиеся значения счётчика. Т.е. неправильно, если флоу подрят выдаст значения 0 и 0.
	 *
	 * Flow<IntAction> emits 2 commands to the counter: INCREMENT and CLEAR.
	 * Return the flow Flow<Int>, which maps commands to counter values.
	 * The counter operates according to the following rules:
	 *  The initial value of the counter is 0. but this initial value should not be emitted to result flow.
	 *  If the INCREMENT command arrives, then increase the counter by one and emit a new counter value.
	 *  If the counter becomes == 3, then throw a CounterException
	 *  If the CLEAR command arrives, then reset the counter and emit 0.
	 *  The flow should not produce duplicate counter values. It’s wrong if the flow emits values two zeros one by one.
	 *
	 * Solution / Решение: [coroutines.flow2.solutions.countTo3]
	 * Hint / Подсказка: [coroutines.flow2.hints.countTo3]
	 */
	override suspend fun countTo3(actions: Flow<CounterAction>): Flow<Int> {
		TODO()
	}

	/**
	 * Дано несколько флоу. Каждый из них может вернуть несколько чисел или выбросить ошибку [coroutines.flow2.utils.Flow2Exception].
	 * Верни все числа, полученные из флоу (в любом порядке), а также самую последнюю ошибку выброшенную или null, если ни один из потоков не вернул ошибку.
	 * Порядок чисел не важен.
	 * При этом время выполнения функции должно быть меньше, чем сумма всремени жизни всех флоу.
	 *
	 * Several flows are given. Each of them can return multiple numbers or throw a [coroutines.flow2.utils.Flow2Exception].
	 * Return all numbers received from the flow (in any order), plus the most recently thrown error, or null if none of the flows returned an error.
	 * Order of numbers in list is not important.
	 * The execution time of the function should be less than the sum of the lifetime of all flows.
	 *
	 * Hint / Подсказка: [coroutines.flow2.hints.collectIntsAndException]
	 * Solution / Решение: [coroutines.flow2.solutions.collectIntsAndException]
	 */
	override suspend fun collectIntsAndException(list: List<Flow<Int>>): Pair<List<Int>, Flow2Exception?> {
		TODO()
	}

	/**
	 * Given is a list of flows of numbers. Each flow gives the progress of some process.
	 * Each flow emits the current progress as a percentage: 0..100. Initial value is 0 for all flows.
	 * Also, the flow can emit -1, which means a process error. Flow which emits -1 completes after that.
	 *
	 * Return a flow that displays the overall progress. When dividing, discard the fractional part.
	 * E.g. if there are 2 flows, the first gives progress of 21, and the second gives 30, then the total progress is (21 + 30)/2 = 51/2 = 25.5 => 25.
	 * If any of the flows returned -1, that flows are no longer need to be taken into account in the calculation.
	 * E.g. if the progress of the first flow is 20, the progress of the second flow is -1, then the total progress is 20.
	 * If all flows end with -1, send -1 to result flow and stop it.
	 * Also do not repeat several same progress values in a row.
	 *
	 * Есть спискок flow чисел. Каждый флоу отдаёт прогресс какого-то процесса.
	 * Каждое флоу эмитит текущий прогресс в процентах: 0..100. Начальное значение каждого флоу 0
	 * Также флоу может заэмитить -1, что значит ошибку процесса. Флоу, которое заэмитило -1, завершается после этого.
	 *
	 * Требуется вернуть флоу, которое отображает общий прогресс. При делении дробную часть отбрасывать.
	 * Т.е. если есть 2 флоу, у первого прогресс 21, а у второго - 30, то общий прогресс (21 + 30)/2 = 51/2 = 25.5 => 25.
	 * Если какое-то из флоу вернуло -1, то его не нужно больше учитывать в подсчёте.
	 * Т.е. если прогресс флоу 1 - 20, прогресс флоу 2 - -1, то общий прогресс - 20.
	 * Если все флоу завершатся с -1, отправь во флоу -1 и заверши его.
	 * Также, не повторяй несколько одинаковых значений итогого прогресса подряд.
	 *
	 * Hint / Подсказка: [coroutines.flow2.hints.loadingProgress]
	 * Solution / Решение: [coroutines.flow2.solutions.loadingProgress]
	 */
	override suspend fun loadingProgress(progressList: List<Flow<Int>>): Flow<Int> {
		TODO()
	}

	/**
	 * The task and conditions are the same as the previous task [coroutines.flow2.Flow2Tasks.loadingProgress].
	 * The only change is that now each progress flow does not complete if it emits -1.
	 * After emitting -1 flow may still send incorrect values for some time.
	 * These incorrect values should be ignored and not taken into account in the calculation of overall progress.
	 *
	 * Задание и условия такие же как предыдущем задании [coroutines.flow2.Flow2Tasks.loadingProgress].
	 * Единственное изменение - теперь каждое флоу прогресса не завершается, если оно отправило -1.
	 * После отправики -1 flow может ещё некоторое время отсылать неверные значения.
	 * Эти неверные значения нужно игнорировать и не учитывать в подсчёте общего прогресса.
	 *
	 * Hint / Подсказка: [coroutines.flow2.hints.loadingBrokenProgress]
	 * Solution / Решение: [coroutines.flow2.solutions.loadingBrokenProgress]
	 */
	override suspend fun loadingBrokenProgress(progressList: List<Flow<Int>>): Flow<Int> {
		TODO()
	}

	/**
	 * Дан репозиторий для чтения сообщений:
	 * * [MessagesRepository.subscribeToMessages] - позволяет подписаться на сообщения. Метод принимает [MessagesRepository.Callback].
	 * * [MessagesRepository.unsubscribeFromMessages] - позволяет отписаться от получения сообщений.
	 *
	 * [MessagesRepository.Callback] имеет следующие методы (см. KDoc этого интерфейса):
	 * * [MessagesRepository.Callback.onNewMessage] - вызывается, когда приходит новое сообщение.
	 * * [MessagesRepository.Callback.onFinished] - вызывается, когда поток сообщений заканчивается из-за окончания потока или ошибки
	 *
	 * Нужно написать функцию, которая возвращает флоу сообщений из репозитория.
	 * Сообщения, которые приходят в [MessagesRepository.Callback.onNewMessage] нужно отправлять во флоу.
	 * Если пришло сообщение из одной точки ".", нужно прекратить получение сообщений и завершить флоу.
	 * Если был вызван [MessagesRepository.Callback.onFinished] без ошибки, то необходимо завершить флоу
	 * Если был вызван [MessagesRepository.Callback.onFinished] с ошибкой, то во флоу необходимо отправить 2 сообщения: "." и "ERROR". После этого завершить флоу завершить флоу.
	 *
	 * Given a repository for reading messages:
	 * * [MessagesRepository.subscribeToMessages] - allows you to subscribe to messages. The method accepts [MessagesRepository.Callback].
	 * * [MessagesRepository.unsubscribeFromMessages] - allows you to unsubscribe from receiving messages.
	 *
	 * [MessagesRepository.Callback] has the following methods (see KDoc of this interface):
	 * * [MessagesRepository.Callback.onNewMessage] - called when a new message arrives.
	 * * [MessagesRepository.Callback.onFinished] - called when the there are no more messages or an exception is thrown
	 *
	 * Write a function that returns a flow of messages from the repository.
	 * Messages that come to [MessagesRepository.Callback.onNewMessage] must be sent to the flow.
	 * If a message of a single dot "." is arrived, you need to stop receiving messages and finish the flow.
	 * If [MessagesRepository.Callback.onFinished] was called without an error, then the flow must be finished
	 * If [MessagesRepository.Callback.onFinished] was called with an error, then 2 messages must be sent to the flow: "." and "ERROR". After that, complete the flow finish the flow.
	 *
	 * Hint / Подсказка: [coroutines.flow2.hints.collectMessagesFromRepository]
	 * Solution / Решение: [coroutines.flow2.solutions.collectMessagesFromRepository]
	 */
	override suspend fun collectMessagesFromRepository(repositories: MessagesRepository): Flow<String> {
		TODO("Not yet implemented")
	}
}
