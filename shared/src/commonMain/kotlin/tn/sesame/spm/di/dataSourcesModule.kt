package tn.sesame.spm.di

import org.koin.dsl.module
import tn.sesame.spm.data.dataSources.UsersLocalDAO
import tn.sesame.spm.data.dataSources.UsersRemoteDAO

internal val dataSourcesModule = module {
    includes(networkModule)
    includes(databaseModule)
    factory<UsersLocalDAO> {
        UsersLocalDAO(get())
    }
    factory {
        UsersRemoteDAO(get(RestClientImplTag))
    }
}