package tn.sesame.spm.android.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import tn.sesame.spm.data.repositories.users.UsersRepositoryInterface


class SettingsViewModel(
    private val repositoryInterface: UsersRepositoryInterface
) : ViewModel() {

    fun setAutoLoginEnabled(isEnabled : Boolean){
        viewModelScope.launch {
            repositoryInterface.setAutoLoginEnabled(isEnabled)
        }
    }

    fun getAutoLoginEnabled() : Flow<Boolean> = repositoryInterface
        .isAutoLoginEnabled()
        .map { it ?: false }
        .flowOn(Dispatchers.Main)
}