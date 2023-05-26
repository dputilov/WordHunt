package com.dms.wordhunt.useCase.services


import com.dms.wordhunt.classes.ExchangeItem
import com.dms.wordhunt.classes.ExchangeItemStatus
import com.dms.wordhunt.classes.ExchangeItemType
import com.dms.wordhunt.manager.credit.ApiExerciseService
import io.reactivex.Observable
import javax.inject.Inject

interface LoadExerciseUseCaseInterface {
    fun loadExercise(): Observable<List<ExchangeItem>>
}

/**
 * Exchange all use case
 */
class LoadExerciseUseCase @Inject constructor (private val apiExerciseService: ApiExerciseService) : LoadExerciseUseCaseInterface
{

    override fun loadExercise(): Observable<List<ExchangeItem>> {
        return apiExerciseService.loadExercise()
            .flatMap { dataList ->
                val newExchangeItemList = mutableListOf<ExchangeItem>()

                val item = ExchangeItem(
                    title = "Кредит",
                    objectName = dataList.string(),
                    type = ExchangeItemType.Credit,
                    status = ExchangeItemStatus.New,
//                    item = dataList.toString(),
//                    baseItem = dataList.toString(),
                )

                newExchangeItemList.add(item)

                Observable.just(newExchangeItemList)
            }
    }

}
