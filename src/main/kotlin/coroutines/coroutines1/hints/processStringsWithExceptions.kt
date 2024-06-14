package coroutines.coroutines1.hints

import coroutines.coroutines1.utils.NoDataTransformedException

interface processStringsWithExceptions {
	/**
	 * Use try-catch inside async block to catch any exceptions.
	 * Then if all async returned exceptions, throw [NoDataTransformedException]
	 * Dont forget about CancellationException
	 *
	 * Используй try-catch внутри блока async чтобы поймать ошибки.
	 * Далее если все ошибки вернули, то выброси ошибку [NoDataTransformedException]
	 * Не забудь о CancellationException
	 */
}