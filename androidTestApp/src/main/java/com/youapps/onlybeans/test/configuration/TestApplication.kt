package com.youapps.onlybeans.test.configuration

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.youapps.onlybeans.android.di.viewModelsModule
import com.youapps.onlybeans.di.androidSecurityModule
import com.youapps.onlybeans.test.di.testModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(
                InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
            )
            modules(listOf(testModule,androidSecurityModule, viewModelsModule))
        }
    }
}