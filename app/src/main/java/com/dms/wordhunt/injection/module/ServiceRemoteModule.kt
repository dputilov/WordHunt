package com.dms.wordhunt.injection.module

import com.dms.wordhunt.internet.api.Api
import com.dms.wordhunt.internet.services.credit.ApiCreditService
import com.dms.wordhunt.internet.services.credit.ApiCreditServiceRemote
import com.dms.wordhunt.internet.services.flat.ApiFlatService
import com.dms.wordhunt.internet.services.flat.ApiFlatServiceRemote
import dagger.Module
import dagger.Provides

/**
 * Module which provides all required dependencies about Remote services
 */
@Module
class ServiceRemoteModule {

    @Provides
    fun provideApiCreditService(api: Api): ApiCreditService = ApiCreditServiceRemote(api)

    @Provides
    fun provideApiFlatService(api: Api): ApiFlatService = ApiFlatServiceRemote(api)

}
