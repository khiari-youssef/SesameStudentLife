package tn.sesame.spm.android.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import tn.sesame.spm.android.ui.main.MainActivityViewModel
import tn.sesame.spm.android.ui.notifications.NotificationsViewModel
import tn.sesame.spm.di.OBUserGetProfileUseCaseTag
import tn.sesame.spm.di.OBUserLoginUseCaseTag
import tn.sesame.spm.di.OBUserLogoutUseCaseTag
import tn.sesame.spm.di.UsersRepositoryTag
import tn.sesame.spm.di.androidSecurityModule
import tn.sesame.spm.di.domainModule
import tn.sesame.spm.di.repositoriesModule
import tn.sesame.users_management.ui.login.LoginViewModel
import tn.sesame.users_management.ui.profile.MyProfileViewModel
import tn.sesame.users_management.ui.settings.SettingsViewModel

val viewModelsModule = module {
    includes(domainModule)
    includes(androidSecurityModule)
    includes(repositoriesModule)
    viewModel {
        NotificationsViewModel()
    }
    viewModel {
        MyProfileViewModel(get(OBUserGetProfileUseCaseTag),get(UsersRepositoryTag),get(
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
            get()
        )
    }
}