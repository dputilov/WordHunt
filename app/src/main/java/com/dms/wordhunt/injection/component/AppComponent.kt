package com.dms.wordhunt.injection.component

import androidx.lifecycle.AndroidViewModel
import com.dms.wordhunt.diagram.CreditDiagramViewModel
import com.dms.wordhunt.exchange.ExchangeViewModel
import com.dms.wordhunt.flat.FlatViewModel
import com.dms.wordhunt.flat.mainPage.FlatMainViewModel
import com.dms.wordhunt.flat.paymentListPage.FlatPaymentListViewModel
import com.dms.wordhunt.graphic.GraphicListViewModel
import com.dms.wordhunt.injection.module.AppModule
import com.dms.wordhunt.injection.module.NetworkModule
import com.dms.wordhunt.injection.module.ServiceRemoteModule
import com.dms.wordhunt.injection.module.UseCaseModule
import com.dms.wordhunt.main_window.creditPage.CreditListViewModel
import com.dms.wordhunt.main_window.flatPage.FlatListViewModel
import com.dms.wordhunt.main_window.taskPage.TaskViewModel
import com.dms.wordhunt.sideMenu.SideMenuViewModel
import dagger.Component
import javax.inject.Singleton


object AndroidViewModelInjector{
    fun inject(androidViewModel: AndroidViewModel, appComponent: AppComponent, daggerInjectConstructorParameter: Any? = null){
        when (androidViewModel){
            is SideMenuViewModel -> appComponent.inject(androidViewModel)
            is GraphicListViewModel -> appComponent.inject(androidViewModel)
            is FlatViewModel -> appComponent.inject(androidViewModel)
            is FlatListViewModel -> appComponent.inject(androidViewModel)
            is TaskViewModel -> appComponent.inject(androidViewModel)
            is FlatPaymentListViewModel -> appComponent.inject(androidViewModel)
            is CreditDiagramViewModel -> appComponent.inject(androidViewModel)
            is ExchangeViewModel -> appComponent.inject(androidViewModel)
            is CreditListViewModel -> appComponent.inject(androidViewModel)
            is FlatMainViewModel -> appComponent.inject(androidViewModel)
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

    fun inject(target: SideMenuViewModel)

    fun inject(target: GraphicListViewModel)

    fun inject(target: FlatViewModel)

    fun inject(target: FlatListViewModel)

    fun inject(target: FlatMainViewModel)

    fun inject(target: TaskViewModel)

    fun inject(target: FlatPaymentListViewModel)

    fun inject(target: CreditDiagramViewModel)

    fun inject(target: ExchangeViewModel)

    fun inject(target: CreditListViewModel)

}