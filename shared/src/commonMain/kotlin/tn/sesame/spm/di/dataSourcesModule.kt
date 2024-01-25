package tn.sesame.spm.di

import org.koin.dsl.module
import tn.sesame.spm.data.dataSources.UsersLocalDAO

internal val dataSourcesModule = module {
    includes(databaseModule)
    factory<UsersLocalDAO> {
        UsersLocalDAO(get())
    }
}