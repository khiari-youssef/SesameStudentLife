package tn.sesame.spm.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import tn.sesame.spm.data.repositories.users.UsersRepository
import tn.sesame.spm.data.repositories.users.UsersRepositoryInterface
internal val UsersRepositoryTag = named("UsersRepository")
val repositoriesModule = module {
    includes(dataSourcesModule)
    factory< UsersRepositoryInterface>(UsersRepositoryTag) {
        UsersRepository(get(),get())
    }
}