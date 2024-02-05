package ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tn.sesame.spm.android.ui.login.LoginScreen
import tn.sesame.spm.android.ui.login.LoginState
import tn.sesame.spm.android.ui.login.LoginUIStateHolder

class LoginScreenTest {

@get:Rule
val composeLoginTestRule = createComposeRule()

private val viewModelLoginMockState : MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)

@Before
fun init(){
    viewModelLoginMockState.value = LoginState.Idle
}

@Test
fun testLoginScreenWhenIdleThenLoginWithCredentials() {
    composeLoginTestRule.setContent {
        val uiState = LoginUIStateHolder.rememberLoginUIState(
            loginEmail = remember {
                mutableStateOf("")
            },
            loginPassword =  remember {
                mutableStateOf("")
            },
            loginRequestResult = viewModelLoginMockState.collectAsStateWithLifecycle(
                initialValue = LoginState.Idle
            )
        )
        LoginScreen(
            loginUIStateHolder = uiState,
            onEmailChanged = {
                uiState.loginEmail.value = it
            }, onPasswordChanged ={
                uiState.loginPassword.value = it
            },
            onLoginClicked = {
                viewModelLoginMockState.value = LoginState.Loading
            }
        )
    }
    composeLoginTestRule.run {
        onNodeWithContentDescription("LoginEmailTextField")
            .performTextInput("test@sesame.com.tn")
        onNodeWithContentDescription("LoginPasswordTextField")
            .performTextInput("sesame1234")
        onNodeWithContentDescription("SesameButtonLoadingCircularProgressBar")
            .assertDoesNotExist()
        onNodeWithContentDescription("LoginButton").performClick()
        onNodeWithContentDescription("SesameButtonLoadingCircularProgressBar")
            .assertExists()
    }
}



}