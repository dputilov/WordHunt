package com.dms.wordhunt.injection.module

import android.content.Context
import com.dms.wordhunt.internet.services.credit.ApiCreditService
import com.dms.wordhunt.internet.services.flat.ApiFlatService
import com.dms.wordhunt.useCase.services.ExchangeApplyUseCase
import com.dms.wordhunt.useCase.services.ExchangeCreditUseCase
import com.dms.wordhunt.useCase.services.ExchangeFlatUseCase
import com.dms.wordhunt.useCase.services.ExchangeLoadAllUseCase
import dagger.Module
import dagger.Provides

/**
 * Module which provides all required dependencies of Use Cases
 */
@Module
class UseCaseModule {

    @Provides
    fun provideExchangeCreditUseCase(apiCreditService : ApiCreditService): ExchangeCreditUseCase = ExchangeCreditUseCase(apiCreditService)

    @Provides
    fun provideExchangeFlatUseCase(apiFlatService : ApiFlatService): ExchangeFlatUseCase = ExchangeFlatUseCase(apiFlatService)

    @Provides
    fun provideExchangeApplyUseCase(context : Context): ExchangeApplyUseCase = ExchangeApplyUseCase(context)

    @Provides
    fun provideExchangeLoadAllUseCase(apiCreditService : ApiCreditService, apiFlatService : ApiFlatService): ExchangeLoadAllUseCase = ExchangeLoadAllUseCase(apiCreditService, apiFlatService)

}
