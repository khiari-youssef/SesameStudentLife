package tn.sesame.spm.ui.login

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject
import tn.sesame.designsystem.R
import tn.sesame.spm.android.ui.login.LoginScreen
import tn.sesame.spm.android.ui.login.LoginState
import tn.sesame.spm.android.ui.login.LoginUIStateHolder
import tn.sesame.spm.domain.exception.DomainErrorType

class LoginScreenTest : KoinTest {

private val instrumentationContext: Context by inject()

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


@Test
fun testLoginScreenWhenIdleThenLoginWithInvalidCredentials() {
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
                viewModelLoginMockState.value = LoginState.Error(
                    errorType = DomainErrorType.InvalidCredentials
                )
            }
        )
    }
    composeLoginTestRule.run {
        onNodeWithContentDescription("SesameButtonLoadingCircularProgressBar")
            .assertDoesNotExist()
        onNodeWithContentDescription("LoginButton").performClick()
        val expectedToastMessage = instrumentationContext
            .resources
            .getString(R.string.error_toast_invalid_credentials)
        onNodeWithContentDescription("ToastTextContent").run {
            assertExists()
           assertTextContains(expectedToastMessage)
        }
    }
}


@Test
fun testLoginScreenWhenIdleThenLoginWithLockedAccount() {
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
                viewModelLoginMockState.value = LoginState.Error(
                    errorType = DomainErrorType.AccountLocked
                )
            }
        )
    }
    composeLoginTestRule.run {
        onNodeWithContentDescription("LoginEmailTextField")
            .performTextInput("lockedaccount@sesame.com.tn")
        onNodeWithContentDescription("LoginPasswordTextField")
            .performTextInput("sesame1234")
        onNodeWithContentDescription("SesameButtonLoadingCircularProgressBar")
            .assertDoesNotExist()
        onNodeWithContentDescription("LoginButton").performClick()
        val expectedToastMessage = instrumentationContext
            .resources
            .getString(R.string.error_toast_locked)
        onNodeWithContentDescription("ToastTextContent").run {
            assertExists()
           assertTextContains(expectedToastMessage)
        }
    }
}
@Test
fun testLoginScreenWhenIdleThenLoginANDUndefinedErrorOccurs() {
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
                viewModelLoginMockState.value = LoginState.Error(
                    errorType = DomainErrorType.Undefined
                )
            }
        )
    }
    composeLoginTestRule.run {
        onNodeWithContentDescription("LoginEmailTextField")
            .performTextInput("lockedaccount@sesame.com.tn")
        onNodeWithContentDescription("LoginPasswordTextField")
            .performTextInput("sesame1234")
        onNodeWithContentDescription("SesameButtonLoadingCircularProgressBar")
            .assertDoesNotExist()
        onNodeWithContentDescription("LoginButton").performClick()
        val expectedToastMessage = instrumentationContext
            .resources
            .getString(R.string.error_toast_unknown)
        onNodeWithContentDescription("ToastTextContent").run {
            assertExists()
           assertTextContains(expectedToastMessage)
        }
    }
}



}