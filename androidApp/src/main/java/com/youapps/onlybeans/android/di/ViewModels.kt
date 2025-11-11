package com.youapps.onlybeans.android.di

import com.youapps.onlybeans.android.ui.main.MainActivityViewModel
import com.youapps.onlybeans.android.ui.notifications.NotificationsViewModel
import com.youapps.onlybeans.di.OBUserLoginUseCaseTag
import com.youapps.onlybeans.di.OBUserLogoutUseCaseTag
import com.youapps.onlybeans.di.UsersRepositoryTag
import com.youapps.onlybeans.di.androidSecurityModule
import com.youapps.onlybeans.di.domainModule
import com.youapps.onlybeans.di.repositoriesModule
import com.youapps.users_management.ui.login.LoginViewModel
import com.youapps.users_management.ui.profile.MyProfileViewModel
import com.youapps.users_management.ui.registration.OBRegistrationViewModel
import com.youapps.users_management.ui.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    includes(domainModule)
    includes(androidSecurityModule)
    includes(repositoriesModule)
    viewModel {
        NotificationsViewModel()
    }
    viewModel {
        MyProfileViewModel(get(UsersRepositoryTag),get(
            OBUserLogoutUseCaseTag
        ))
    }
    viewModel {
        LoginViewModel(get(OBUserLoginUseCaseTag))
    }
    viewModel {
        SettingsViewModel(get(UsersRepositoryTag))
    }
    viewModel {
        MainActivityViewModel(
            get(UsersRepositoryTag),
            get(OBUserLoginUseCaseTag),
            get()
        )
    }
    viewModel {
        OBRegistrationViewModel(get(),get(UsersRepositoryTag))
    }
}