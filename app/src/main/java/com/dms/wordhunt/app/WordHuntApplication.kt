package com.dms.wordhunt.app

import androidx.multidex.MultiDexApplication
import com.dms.wordhunt.injection.component.AppComponent
import com.dms.wordhunt.injection.component.DaggerAppComponent
import com.dms.wordhunt.injection.module.AppModule
import com.dms.wordhunt.manager.SettingsManager

class WordHuntApplication: MultiDexApplication() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initDependencies()

        initManagers()

    }

    private fun initDependencies() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    private fun initManagers() {
        SettingsManager.instance.initManager(this)
        //DatabaseManager.instance.initManager(this)
    }

}