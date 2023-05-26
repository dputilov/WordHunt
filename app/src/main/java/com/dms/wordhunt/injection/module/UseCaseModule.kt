package com.dms.wordhunt.injection.module

import com.dms.wordhunt.manager.credit.ApiExerciseService
import com.dms.wordhunt.useCase.services.LoadExerciseUseCase
import dagger.Module
import dagger.Provides

/**
 * Module which provides all required dependencies of Use Cases
 */
@Module
class UseCaseModule {

    @Provides
    fun provideExchangeCreditUseCase(apiExerciseService : ApiExerciseService): LoadExerciseUseCase = LoadExerciseUseCase(apiExerciseService)

}
