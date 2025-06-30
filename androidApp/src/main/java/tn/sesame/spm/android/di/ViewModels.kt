package tn.sesame.spm.android.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tn.sesame.spm.android.ui.main.MainActivityViewModel
import tn.sesame.spm.android.ui.notifications.NotificationsViewModel
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
        MyProfileViewModel(get())
    }
    viewModel {
        LoginViewModel(get())
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