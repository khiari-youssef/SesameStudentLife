package com.youapps.onlybeans.di

import android.content.Context
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module

actual val datastoreModule : Module = module {
    factory(dataStorePathTag) {
        get<Context>().filesDir.resolve(dataStoreFileName).absolutePath.toPath()
    }
}