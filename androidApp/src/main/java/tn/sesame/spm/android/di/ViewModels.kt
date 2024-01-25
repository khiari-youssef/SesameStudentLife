package tn.sesame.spm.android.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tn.sesame.spm.android.ui.login.LoginViewModel
import tn.sesame.spm.android.ui.notifications.NotificationsViewModel
import tn.sesame.spm.android.ui.profile.MyProfileViewModel
import tn.sesame.spm.android.ui.projects.ProjectsViewModel
import tn.sesame.spm.di.domainModule

val viewModelsModule = module {
    includes(domainModule)
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
}