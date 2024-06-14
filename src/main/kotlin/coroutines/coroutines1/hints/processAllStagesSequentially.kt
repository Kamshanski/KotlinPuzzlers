package coroutines.coroutines1.hints

interface processAllStagesSequentially {
	/**
	 * First, using async, transform the list using transform1. Wait until all transformations with transform1 are completed.
	 * Next, run and wait for transformations with transform2 and transform3 in the same way
	 *
	 * Сначала с помощью async преобразуй список с помощью transform1. Дожись окончания всех преобразований с transform1.
	 * Далее аналогично запусти и дождись преобразований с transform2 и transform3
	 */
}