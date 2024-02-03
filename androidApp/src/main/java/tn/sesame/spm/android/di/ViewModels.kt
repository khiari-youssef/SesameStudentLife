package tn.sesame.spm.android.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tn.sesame.spm.android.ui.login.LoginViewModel
import tn.sesame.spm.android.ui.main.MainActivityViewModel
import tn.sesame.spm.android.ui.notifications.NotificationsViewModel
import tn.sesame.spm.android.ui.profile.MyProfileViewModel
import tn.sesame.spm.android.ui.projects.ProjectsViewModel
import tn.sesame.spm.android.ui.settings.SettingsViewModel
import tn.sesame.spm.di.UsersRepositoryTag
import tn.sesame.spm.di.domainModule
import tn.sesame.spm.di.repositoriesModule

val viewModelsModule = module {
    includes(domainModule)
    includes(repositoriesModule)
    viewModel {
        NotificationsViewModel()
    }
    viewModel {
        MyProfileViewModel()
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        ProjectsViewModel()
    }
    viewModel {
        SettingsViewModel(get(UsersRepositoryTag))
    }
    viewModel {
        MainActivityViewModel(get(UsersRepositoryTag))
    }
}