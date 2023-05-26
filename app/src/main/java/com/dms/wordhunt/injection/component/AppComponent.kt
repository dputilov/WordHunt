package com.dms.wordhunt.injection.component

import androidx.lifecycle.AndroidViewModel
import com.dms.wordhunt.exchange.ExchangeViewModel
import com.dms.wordhunt.injection.module.AppModule
import com.dms.wordhunt.injection.module.NetworkModule
import com.dms.wordhunt.injection.module.ServiceRemoteModule
import com.dms.wordhunt.injection.module.UseCaseModule

import dagger.Component
import javax.inject.Singleton


object AndroidViewModelInjector{
    fun inject(androidViewModel: AndroidViewModel, appComponent: AppComponent, daggerInjectConstructorParameter: Any? = null){
        when (androidViewModel){
            is ExchangeViewModel -> appComponent.inject(androidViewModel)
        }
    }
}

/**
 * Application Component providing inject() methods for the instances where we are supposed to inject data.
 */
@Singleton
@Component(modules = [
    AppModule::class,
    UseCaseModule::class,
    ServiceRemoteModule::class,
    NetworkModule::class])
interface AppComponent {

    fun inject(target: ExchangeViewModel)

}