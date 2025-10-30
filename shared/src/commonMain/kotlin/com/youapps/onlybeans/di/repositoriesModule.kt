package com.youapps.onlybeans.di

import com.youapps.onlybeans.data.repositories.users.UsersRepository
import com.youapps.onlybeans.data.repositories.users.UsersRepositoryInterface
import org.koin.core.qualifier.named
import org.koin.dsl.module
 val UsersRepositoryTag = named("UsersRepository")
val repositoriesModule = module {
    includes(dataSourcesModule)
    factory<UsersRepositoryInterface>(UsersRepositoryTag) {
        UsersRepository(get(), get(), get())
    }
}