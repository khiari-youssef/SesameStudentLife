package com.youapps.onlybeans.di

import com.youapps.onlybeans.data.repositories.AppMetaDataAPI
import com.youapps.onlybeans.data.repositories.AppMetaDataAPIImpl
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module


val AppMetaDataAPITag = named("AppMetaDataAPIImplTag")
actual val sharedRepositories: Module = module {
    single<AppMetaDataAPI>(AppMetaDataAPITag) {
        AppMetaDataAPIImpl(
            get(),
            get()
        )
    }
}