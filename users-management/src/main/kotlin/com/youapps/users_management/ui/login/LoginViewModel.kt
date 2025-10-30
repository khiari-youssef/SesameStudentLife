package com.youapps.users_management.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.youapps.onlybeans.contracts.UseCaseContract
import com.youapps.onlybeans.domain.entities.SesameLoginInterface
import com.youapps.onlybeans.domain.entities.SesameUser
import com.youapps.onlybeans.domain.exception.DomainErrorType
import com.youapps.onlybeans.domain.exception.DomainException

class LoginViewModel(
    private val oBUserLoginUseCase: UseCaseContract<SesameLoginInterface,SesameUser>
) : ViewModel() {

    private val loginResultMutableState : MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    val loginResultState : StateFlow<LoginState> = loginResultMutableState

    fun loginWithEmailAndPassword(email : String,password : String){
        if (email.isBlank() or password.isBlank()){
            loginResultMutableState.value = LoginState.Error(
                errorType = DomainErrorType.InvalidCredentials
            )
        } else {
            viewModelScope.launch {
                loginResultMutableState.value = LoginState.Loading
                runCatching {
                    return@runCatching oBUserLoginUseCase.execute(
                        SesameLoginInterface.SesameCredentialsLogin(
                            email = email.trim(),
                            password = password.trim()
                        )
                    )
                }.onFailure { th->
                    th.printStackTrace()
                    loginResultMutableState.update {
                        LoginState.Error(
                            errorType = if (th is DomainException) th.errorType else DomainErrorType.Undefined
                        )
                    }
                }.onSuccess { sesameUser->
                    loginResultMutableState.update {
                        LoginState.Success(sesameUser)
                    }
                }
            }
        }
    }


    fun setLoginIdleState() {
        loginResultMutableState.update {
            LoginState.Idle
        }
    }

}