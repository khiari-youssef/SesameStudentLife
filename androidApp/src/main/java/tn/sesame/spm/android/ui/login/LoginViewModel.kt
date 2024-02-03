package tn.sesame.spm.android.ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tn.sesame.spm.domain.exception.DomainErrorType
import tn.sesame.spm.domain.usecases.SesameUsersUsecase

class LoginViewModel(
    private val sesameUsersUsecase: SesameUsersUsecase
) : ViewModel() {

    private val loginResultMutableState : MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    val loginResultState : StateFlow<LoginState> = loginResultMutableState

    fun loginWithEmailAndPassword(email : String,password : String){
        viewModelScope.launch {
            loginResultMutableState.value = LoginState.Loading
            delay(1000)
            loginResultMutableState.value = LoginState.Success("")
        }
    }

    fun checkIfAutoLoginIsEnabled() : Flow<Boolean> = sesameUsersUsecase
        .checkIfAutoLoginIsEnabled()

}