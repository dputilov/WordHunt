package com.dms.wordhunt.useCase.services


import com.dms.wordhunt.classes.ExchangeItem
import com.dms.wordhunt.manager.credit.ApiCreditService
import io.reactivex.Observable
import io.reactivex.functions.Function
import javax.inject.Inject

/**
 * Exchange all use case
 */
class ExchangeLoadAllUseCase @Inject constructor (
    private val apiCreditService: ApiCreditService,
    private val creditUseCase: ExchangeCreditUseCaseInterface = ExchangeCreditUseCase(apiCreditService),
{

    fun loadAll(): Observable<List<ExchangeItem>> {
        val functionList: MutableList<Observable<List<ExchangeItem>>> = mutableListOf()

        functionList.add(creditUseCase.loadCredits())
        functionList.add(creditUseCase.loadCreditPayments())
        functionList.add(creditUseCase.loadCreditGraphic())
        functionList.add(flatUseCase.loadFlats())
        functionList.add(flatUseCase.loadFlatPayments())

        val convertToExchangeItemListFunction = Function<Array<Any>, List<ExchangeItem>> { sourceList ->
            val exchangeList = mutableListOf<ExchangeItem>()
            sourceList.forEach { resultList ->
                if (resultList is List<*>) {
                    exchangeList.addAll(resultList as List<ExchangeItem>)
                }
            }
            exchangeList
        }

        return Observable.zip(functionList, convertToExchangeItemListFunction)
    }

}
