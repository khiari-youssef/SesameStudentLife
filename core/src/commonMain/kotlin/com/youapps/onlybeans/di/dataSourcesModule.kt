package com.youapps.onlybeans.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.youapps.onlybeans.data.dataSources.AppMetaDataSource
import com.youapps.onlybeans.data.dataSources.UserPreferencesStore
import com.youapps.onlybeans.data.dataSources.UsersLocalDAO
import com.youapps.onlybeans.data.dataSources.UsersRemoteDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val dataStorePathTag = named("datastorepath")

internal val dataSourcesModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.createWithPath(
            corruptionHandler = null,
            migrations = emptyList(),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = {
                get(dataStorePathTag)
            },
        )
    }
    factory {
        UserPreferencesStore(get())
    }
    includes(networkModule)
    includes(datastoreModule)
    includes(databaseModule)
    factory<UsersLocalDAO> {
        UsersLocalDAO(get(),get())
    }
    factory {
        UsersRemoteDAO(get(RestClientImplTag))
    }
    factory {
        AppMetaDataSource(
            get(),
            get(RestClientImplTag)
        )
    }

}