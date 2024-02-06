package tn.sesame.spm.android.ui.main

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import tn.sesame.spm.android.ui.login.LoginState
import tn.sesame.spm.data.exceptions.CustomHttpException
import tn.sesame.spm.data.exceptions.HttpErrorType
import tn.sesame.spm.data.repositories.users.UsersRepositoryInterface
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.exception.DomainErrorType
import tn.sesame.spm.domain.exception.DomainException
import tn.sesame.spm.security.BiometricAuthService
import tn.sesame.spm.security.SupportedDeviceAuthenticationMethods


class MainActivityViewModel(
    private val usersRepositoryInterface: UsersRepositoryInterface,
    private val bioService : BiometricAuthService
) : ViewModel() {

    private val biometricCapabilitiesMutableState : MutableStateFlow<SupportedDeviceAuthenticationMethods>
            = MutableStateFlow(SupportedDeviceAuthenticationMethods.Waiting)
    val biometricCapabilitiesState : StateFlow<SupportedDeviceAuthenticationMethods> = biometricCapabilitiesMutableState

    private val autoLoginMutableState : MutableStateFlow<LoginState> = MutableStateFlow(
        LoginState.Loading
    )
    val autoLoginState : StateFlow<LoginState>  = autoLoginMutableState

    init {
        checkBiometricCapabilitiesState()
        checkAutoLoginState()
    }


    fun checkBiometricCapabilitiesState(){
        biometricCapabilitiesMutableState.value = bioService.checkBiometricCapabilitiesState()
    }

    private fun checkAutoLoginState() {
        viewModelScope.launch {
            usersRepositoryInterface.isAutoLoginEnabled()
                .map {isAutoLogin->
                    if (isAutoLogin == true){
                        val token = usersRepositoryInterface.getLastUsedLogin()
                        token?.run {
                            try {
                                val updatedUserData : SesameUser = usersRepositoryInterface.loginWithToken(token)
                                LoginState.Success(updatedUserData)
                            } catch (th: Throwable){
                                th.printStackTrace()
                                LoginState.Error(
                                    errorType = if (th is DomainException) th.errorType else DomainErrorType.Undefined
                                )
                            }
                        } ?: run {
                            usersRepositoryInterface.clearUsersFromLocalStorage()
                            LoginState.Idle
                        }
                    } else {
                        usersRepositoryInterface.clearUsersFromLocalStorage()
                        LoginState.Idle
                    }
                }.catch { th->
                    emit(
                        LoginState.Error(
                            errorType =  if (th is CustomHttpException){
                                if (th.errorType ==  HttpErrorType.UnauthorizedAccess) DomainErrorType.Unauthorized else DomainErrorType.Undefined
                            } else  DomainErrorType.Undefined
                        )
                    )
                }.collect(autoLoginMutableState)
        }
    }
}