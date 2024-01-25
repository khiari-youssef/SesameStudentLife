package tn.sesame.spm.di

import org.koin.dsl.module
import tn.sesame.spm.domain.usecases.SesameUsersUsecase

val domainModule = module {
   includes(repositoriesModule)
   factory {
       SesameUsersUsecase(
           get(UsersRepositoryTag)
       )
   }
}