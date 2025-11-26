package com.youapps.onlybeans.di

import com.youapps.onlybeans.data.dataSources.AppMetaDataSource
import com.youapps.onlybeans.data.repositories.AppMetaDataAPI
import com.youapps.onlybeans.platform.OBLocationService
import org.koin.dsl.module

val  platformServicesModule = module {

    single {
        OBLocationService(get())
    }

}