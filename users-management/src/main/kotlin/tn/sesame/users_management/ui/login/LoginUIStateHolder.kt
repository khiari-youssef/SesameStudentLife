package tn.sesame.users_management.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.exception.DomainErrorType

sealed interface LoginState {

    @Stable
  data object Idle : LoginState


    @Stable
  data object Loading : LoginState

  @Stable
  data class Error(val errorType : DomainErrorType) : LoginState

    @Stable
  data class Success(val user : SesameUser) : LoginState
}

data class LoginUIStateHolder(
  val loginEmail : MutableState<String>,
  val loginPassword : MutableState<String>,
  val loginRequestResult : State<LoginState>
){
    companion object{
        @Composable
        fun rememberLoginUIState(
             loginEmail : MutableState<String>,
             loginPassword : MutableState<String>,
             loginRequestResult : State<LoginState>
        ) : LoginUIStateHolder = remember(loginEmail, loginPassword, loginRequestResult) {
            LoginUIStateHolder(
              loginEmail, loginPassword, loginRequestResult
            )
        }
    }
}