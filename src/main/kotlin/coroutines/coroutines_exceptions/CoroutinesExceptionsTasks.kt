package coroutines.coroutines_exceptions

import coroutines.coroutines_exceptions.utils.AsyncException
import coroutines.coroutines_exceptions.utils.ICoroutinesExceptionsTasks
import kotlinx.coroutines.*


class CoroutinesExceptionsTasks : ICoroutinesExceptionsTasks {

	/**
	 * The [function] can throw an exception or return a number.
	 * If the function returned a number, return the number.
	 * If a function throws an [Exception] or its descendant, catch that exception and return it.
	 * If a function throws an error that is not inherited from [Exception], do not catch this error, throw it further.
	 * Don't forget about [CancellationException]
	 *
	 * Функция [function] может выбросить исключение или вернуть число.
	 * Если функция вернула число, верни число.
	 * Если функция выбросила исключение [Exception] или его потомка, поймай это исключение и верни её.
	 * Если функция выбросила ошибку, которая не потомок [Exception], не лови эту ошибку, пробрось её дальше.
	 * Не забудь про [CancellationException]
	 *
	 * Hint / Подсказка: [coroutines.coroutines_exceptions.hints.tryNotToFail]
	 */
	override suspend fun tryNotToFail(function: suspend () -> Int): Any /* Any = Int or Exception */ {
		TODO()
	}

	/**
	 * This code consists of two coroutines launched via [launch].
	 * The inner coroutine invokes [doWork], which throws an [AsyncException].
	 * It is expected that the exception in the inner coroutine should be caught and passed to the [onError] callback.
	 *
	 * But the code below doesn't work that way.
	 * Rewrite the code so that both coroutines complete successfully and so that an error from the internal coroutine is caught and passed to the onError callback.
	 * Don't use CoroutineExceptionHandler
	 *
	 * The solution cannot be verified by tests, so check yourself by looking at the solution: [coroutines.coroutines_exceptions.solutions.doWorkThrowsExceptionWithTryCatch]
	 *
	 * Этот код состоит из двух корутин, запускаемых через [launch].
	 * Внутренняя корутина запускает [doWork], который выбрасывает исключение [AsyncException].
	 * Ожидается, что исключение во внутренней корутине должно отлавливаться и передаваться в колбэк [onError].
	 *
	 * Но код ниже так не работает.
	 * Перепиши код так, чтобы данные передавались в колбэки как ожидается.
	 * Не используй CoroutineExceptionHandler
	 *
	 * Решение невозможно проверить тестами, поэтому проверь себя, посмотрев решение: [coroutines.coroutines_exceptions.solutions.doWorkThrowsExceptionWithTryCatch]
	 *
	 * Hint / Подсказка: [coroutines.coroutines_exceptions.hints.doWorkThrowsExceptionWithTryCatch]
	 */
	override fun doWorkThrowsExceptionWithTryCatch(
		scope: CoroutineScope,
		doWork: () -> Unit,
		onError: (Throwable) -> Unit
	) {
		scope.launch {
			try {
				launch {
					doWork()
				}
			} catch (exception: AsyncException) {
				onError(exception)
			}
		}
	}

	/**
	 * This code consists of two coroutines launched via [launch].
	 * The inner coroutine invokes [doWork], which throws an [AsyncException].
	 * It is expected that the exception in the inner coroutine should be caught and passed to the [onError] callback.
	 *
	 * But the code below doesn't work that way.
	 * Rewrite the code so that exception is passed to callbacks as expected.
	 * Don't use try/catch.
	 *
	 * The solution cannot be verified by tests, so check yourself by looking at the solution: [coroutines.coroutines_exceptions.solutions.doWorkThrowsExceptionWithCEH]
	 *
	 * Этот код состоит из двух корутин, запускаемых через [launch].
	 * Внутренняя корутина запускает [doWork], который выбрасывает исключение [AsyncException].
	 * Ожидается, что исключение во внутренней корутине должно отлавливаться и передаваться в колбэк [onError].
	 *
	 * Но код ниже так не работает.
	 * Перепиши код так, чтобы данные передавались в колбэки как ожидается.
	 * Не используй try/catch.
	 *
	 * Решение невозможно проверить тестами, поэтому проверь себя, посмотрев решение: [coroutines.coroutines_exceptions.solutions.doWorkThrowsExceptionWithCEH]
	 *
	 * Hint / Подсказка: [coroutines.coroutines_exceptions.hints.doWorkThrowsExceptionWithCEH]
	 */
	override fun doWorkThrowsExceptionWithCEH(
		scope: CoroutineScope,
		doWork: () -> Unit,
		onError: (Throwable) -> Unit
	) {
		scope.launch {
			launch(
				CoroutineExceptionHandler { _, ex ->
					onError(ex)
				}
			) {
				doWork()
			}
		}
	}

	/**
	 * This code consists of two coroutines launched via [launch] and [async].
	 * Coroutine [async] invokes [doWork], which either returns a number or throws an [AsyncException].
	 * The number is expected to be passed to the [onSuccess] callback, and the exception should be caught and passed to the [onError] callback.
	 *
	 * But the code below doesn't work that way.
	 * Rewrite the code so that data is passed to callbacks as expected.
	 * Do not change the order or location of the coroutines launches.
	 *
	 * The solution cannot be verified by tests, so check yourself by looking at the solution: [coroutines.coroutines_exceptions.solutions.doTopLevelAsyncWorkThrowsException]
	 *
	 * Этот код состоит из двух корутин, запускаемых через [launch] и [async].
	 * Корутина [async] выполняет [doWork], который либо возвращает число, либо выбрасывает исключение [AsyncException].
	 * Ожидается, что число будет передавать в колбэк [onSuccess], а исключение должно отлавливаться и передаваться в колбэк [onError].
	 *
	 * Но код ниже так не работает.
	 * Перепиши код так, чтобы данные передавались в колбэки как ожидается.
	 * Не меняй порядок или места запуска корутин.
	 *
	 * Решение невозможно проверить тестами, поэтому проверь себя, посмотрев решение: [coroutines.coroutines_exceptions.solutions.doTopLevelAsyncWorkThrowsException]
	 *
	 * Hint / Подсказка: [coroutines.coroutines_exceptions.hints.doTopLevelAsyncWorkThrowsException]
	 */
	override fun doTopLevelAsyncWorkThrowsException(
		scope: CoroutineScope,
		doWork: () -> Int,
		onError: (Throwable) -> Unit,
		onSuccess: (Int) -> Unit,
	) {
		val intDeferred = scope.async { doWork() }

		scope.launch {
			// TODO: Here send number to onSuccess or error to onError.
			// TODO: Тут передайте число в onSuccess или ошибку в onError
			intDeferred.await()
		}
	}

	/**
	 * This code consists of two coroutines launched via [launch] and [async].
	 * The inner [async] coroutine invokes [doWork], which throws [AsyncException].
	 * The number is expected to be passed to the [onSuccess] callback, and the exception should be caught and passed to the [onError] callback.
	 *
	 * But the code below doesn't work that way.
	 * Rewrite the code so that data is passed to callbacks as expected.
	 * Do not change the order or location of the coroutines launches.
	 *
	 * The solution cannot be verified by tests, so check yourself by looking at the solution: [coroutines.coroutines_exceptions.solutions.doChildAsyncWorkThrowsException]
	 *
	 * Этот код состоит из двух корутин, запускаемых через [launch] и [async].
	 * Внутренняя корутина [async] запускает [doWork], который выбрасывает исключение [AsyncException].
	 * Ожидается, что число будет передавать в колбэк [onSuccess], а исключение должно отлавливаться и передаваться в колбэк [onError].
	 *
	 * Но код ниже так не работает.
	 * Перепиши код так, чтобы данные передавались в колбэки как ожидается.
	 * Не меняй порядок или места запуска корутин.
	 *
	 * Решение невозможно проверить тестами, поэтому проверь себя, посмотрев решение: [coroutines.coroutines_exceptions.solutions.doChildAsyncWorkThrowsException]
	 *
	 * Hint / Подсказка: [coroutines.coroutines_exceptions.hints.doChildAsyncWorkThrowsException]
	 */
	override fun doChildAsyncWorkThrowsException(
		scope: CoroutineScope,
		doWork: () -> Int,
		onError: (Throwable) -> Unit,
		onSuccess: (Int) -> Unit,
	) {
		scope.launch {
			val intDeferred = async { doWork() }

			intDeferred.await()
		}
	}

	/**
	 * Lambdas [doWork1] and [doWork2] can throw [AsyncException] or return a number.
	 * You need to execute lambdas in parallel and return a pair of [AsyncException] or [Int] values.
	 * The first value in Pair is the result of [doWork1]. The second value is the result of [doWork2].
	 * Example: if [doWork1] returned AsyncException and [doWork2] returned 4, the function should return Pair(AsyncException, 5)
	 * If at least one lambda throws an error that is not [AsyncException] or its descendant, end the function with this error.
	 *
	 * Лямбды [doWork1] and [doWork2] могут выбросить [AsyncException] или вернуть число.
	 * Нужно параллельно выполнить лябмды и вернуть пару значений [AsyncException] или [Int].
	 * Первое значение в Pair - резульат [doWork1]. Второе значение - результат [doWork2].
	 * Пример 1: если [doWork1] вернула ArithmeticException, а [doWork2] вернула 4, функция должна вернуть Pair(ArithmeticException, 5)
	 * Пример 2: если [doWork1] была отменена, а [doWork2] вернула 4, функция должна вернуть Pair(null, 5)
	 * Если хотя бы одна лямбда выбросила ошибку, которая не [AsyncException] или её потомок, заверши функию с этой ошибкой.
	 *
	 * Hint / Подсказка: [coroutines.coroutines_exceptions.hints.tryNotToFailInParallel]
	 */
	override suspend fun tryNotToFailInParallel(
		doWork1: suspend () -> Int,
		doWork2: suspend () -> Int,
	): Pair<Any, Any> /* Any = Int or AsyncException */ {
		TODO()
	}
}