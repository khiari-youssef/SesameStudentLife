package com.youapps.onlybeans.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import com.youapps.onlybeans.contracts.UseCaseContract
import com.youapps.onlybeans.contracts.UseCaseContractReadOnly
import com.youapps.onlybeans.domain.entities.SesameLoginInterface
import com.youapps.onlybeans.domain.entities.SesameUser
import com.youapps.onlybeans.domain.usecases.OBUserGetProfileUseCase
import com.youapps.onlybeans.domain.usecases.OBUserLoginUseCase
import com.youapps.onlybeans.domain.usecases.OBUserLogoutUseCase


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