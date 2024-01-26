package tn.sesame.spm.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tn.sesame.spm.data.dataSources.UserPreferencesStore
import tn.sesame.spm.data.dataSources.UsersLocalDAO
import tn.sesame.spm.data.dataSources.UsersRemoteDAO

internal val dataStorePathTag = named("datastorepath")

internal val dataSourcesModule = module {
    includes(networkModule)
    includes(datastoreModule)
    includes(databaseModule)
    factory<UsersLocalDAO> {
        UsersLocalDAO(get())
    }
    factory {
        UsersRemoteDAO(get(RestClientImplTag))
    }
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
}