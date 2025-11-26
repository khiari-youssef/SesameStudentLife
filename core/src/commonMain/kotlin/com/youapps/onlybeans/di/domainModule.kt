package com.youapps.onlybeans.di

import com.youapps.onlybeans.contracts.UseCaseContract
import com.youapps.onlybeans.contracts.UseCaseContractReadOnly
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.usecases.OBUserLoginUseCase
import com.youapps.onlybeans.domain.usecases.OBUserLogoutUseCase
import com.youapps.onlybeans.domain.valueobjects.OBAuthInterface
import org.koin.core.qualifier.named
import org.koin.dsl.module


val OBUserLoginUseCaseTag = named("OBUserLoginUseCase")
val OBUserLogoutUseCaseTag = named("OBUserLogoutUseCase")




val domainModule = module {
   includes(repositoriesModule)

   factory<UseCaseContract< OBAuthInterface,OBUserProfile>>(OBUserLoginUseCaseTag) {
       OBUserLoginUseCase(
           get(UsersRepositoryTag)
       )
   }

   factory<UseCaseContractReadOnly<Boolean>>(OBUserLogoutUseCaseTag) {
       OBUserLogoutUseCase(
           get(UsersRepositoryTag)
       )
   }

}