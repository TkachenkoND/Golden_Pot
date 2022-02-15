package com.dassol.kanolio.app

import android.app.Application
import com.dassol.kanolio.di.appModule
import com.dassol.kanolio.di.dbModule
import com.onesignal.OneSignal
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@App)
            modules(listOf(
                dbModule,
                appModule
            ))
        }

        OneSignal.initWithContext(applicationContext)
        OneSignal.setAppId("b35b5e05-323b-4288-95f8-87fd22e9f8d5")  //THIS FIELD IN EVERY PROJECT IS UNIQUE !!!
    }
}