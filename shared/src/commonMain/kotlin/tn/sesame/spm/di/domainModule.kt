package tn.sesame.spm.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import tn.sesame.spm.contracts.UseCaseContract
import tn.sesame.spm.contracts.UseCaseContractReadOnly
import tn.sesame.spm.domain.entities.SesameLoginInterface
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.usecases.OBUserGetProfileUseCase
import tn.sesame.spm.domain.usecases.OBUserLoginUseCase
import tn.sesame.spm.domain.usecases.OBUserLogoutUseCase


val OBUserLoginUseCaseTag = named("OBUserLoginUseCase")
val OBUserLogoutUseCaseTag = named("OBUserLogoutUseCase")

val OBUserGetProfileUseCaseTag = named("OBUserGetProfileUseCase")



val domainModule = module {
   includes(repositoriesModule)

   factory<UseCaseContract<SesameLoginInterface,SesameUser>>(OBUserLoginUseCaseTag) {
       OBUserLoginUseCase(
           get(UsersRepositoryTag)
       )
   }

   factory<UseCaseContractReadOnly<Boolean>>(OBUserLogoutUseCaseTag) {
       OBUserLogoutUseCase(
           get(UsersRepositoryTag)
       )
   }

   factory<UseCaseContract<String,SesameUser?>>(OBUserGetProfileUseCaseTag) {
       OBUserGetProfileUseCase(
           get(UsersRepositoryTag)
       )
   }
}