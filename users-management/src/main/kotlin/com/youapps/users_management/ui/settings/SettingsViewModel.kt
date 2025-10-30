package com.youapps.users_management.ui.settings

import androidx.lifecycle.ViewModel
import com.youapps.onlybeans.data.repositories.users.OBUsersRepositoryInterface


class SettingsViewModel(
    private val repositoryInterface: OBUsersRepositoryInterface
) : ViewModel()