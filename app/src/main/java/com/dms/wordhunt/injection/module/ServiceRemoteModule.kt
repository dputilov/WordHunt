package com.dms.wordhunt.injection.module

import com.dms.wordhunt.internet.api.Api
import com.dms.wordhunt.manager.credit.ApiExerciseService
import com.dms.wordhunt.manager.credit.ApiExerciseServiceRemote
import dagger.Module
import dagger.Provides

/**
 * Module which provides all required dependencies about Remote services
 */
@Module
class ServiceRemoteModule {

    @Provides
    fun provideApiCreditService(api: Api): ApiExerciseService = ApiExerciseServiceRemote(api)

}
