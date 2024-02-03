package tn.sesame.spm.android.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import tn.sesame.spm.domain.exception.DomainErrorType

sealed interface LoginState {
  data object Idle : LoginState
  data object Loading : LoginState
  data class Error(val errorType : DomainErrorType) : LoginState
  data class Success(val user : Any) :LoginState
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