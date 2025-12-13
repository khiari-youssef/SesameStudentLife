package com.youapps.onlybeans.di

import com.youapps.onlybeans.data.repositories.users.OBUsersRepository
import com.youapps.onlybeans.data.repositories.users.OBUsersRepositoryInterface
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

 val UsersRepositoryTag = named("OBUsersRepository")
val repositoriesModule = module {
    includes(dataSourcesModule)
    factory<OBUsersRepositoryInterface>(UsersRepositoryTag) {
        OBUsersRepository(get(), get(), get())
    }
    includes(sharedRepositories)
}

expect val sharedRepositories : Module