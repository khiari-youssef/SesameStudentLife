package tn.sesame.users_management.ui.login


import AppVersion
import LoginAnimation
import SesameButton
import SesameButtonVariants
import SesameEmailTextField
import SesamePasswordTextField
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import tn.sesame.designsystem.R
import tn.sesame.designsystem.components.AppBrand
import tn.sesame.designsystem.components.popups.SesameToastDefaults
import tn.sesame.designsystem.components.popups.SesameToastPopup
import tn.sesame.spm.domain.exception.DomainErrorType
import tn.sesame.users_management.BuildConfig

typealias ToastState = Pair<Int,String>

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginUIStateHolder: LoginUIStateHolder,
    onEmailChanged: (email: String) -> Unit,
    onPasswordChanged: (password: String) -> Unit,
    onSetIdleState : ()->Unit,
    onLoginClicked : ()->Unit

) {
   val isLargeScreen  = LocalConfiguration.current.run {
       (orientation == Configuration.ORIENTATION_LANDSCAPE) or (this.screenWidthDp >= 600)
   }
    val toastState : MutableState<ToastState?> = remember {
        mutableStateOf(null)
    }
    val localContext = LocalContext.current
    LaunchedEffect(
        key1 =loginUIStateHolder.loginRequestResult.value,
        block ={
        val state = loginUIStateHolder.loginRequestResult.value
        if (state is LoginState.Error) {
            toastState.value =  (R.drawable.ic_alert to when(state.errorType){
                DomainErrorType.AccountLocked -> {
                    localContext.getString(R.string.error_toast_locked)
                }
                DomainErrorType.Unauthorized ->{
                    localContext.getString(R.string.error_toast_unauthorized)
                }
                DomainErrorType.InvalidCredentials ->{
                    localContext.getString(R.string.error_toast_invalid_credentials)
                }
                else -> localContext.getString(R.string.error_toast_unknown)
            })
            onSetIdleState()
        }

    } )
ConstraintLayout(
    modifier = modifier.padding(
        horizontal = if (isLargeScreen) 12.dp else 20.dp
    ),
    constraintSet = if (isLargeScreen) LoginScreenConfigurationLandscape else LoginScreenConfigurationPortrait
) {
    AppBrand(
        modifier = Modifier
            .layoutId("loginAppBrand")
    )
    LoginAnimation(
        modifier = Modifier
            .size(if (isLargeScreen) 125.dp else 200.dp)
            .layoutId("loginAnim")
    )
    LoginForm(
        modifier = Modifier
            .layoutId("loginForm"),
        email = loginUIStateHolder.loginEmail.value,
        password = loginUIStateHolder.loginPassword.value,
        onEmailChanged = onEmailChanged,
        onPasswordChanged = onPasswordChanged
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .layoutId("loginButton"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterHorizontally)
    ) {
       SesameButton(
           modifier = Modifier
               .semantics {
                   contentDescription = "LoginButton"
               }
               .wrapContentHeight()
               .fillMaxWidth(0.9f),
           text = stringResource(id = R.string.login),
           variant = SesameButtonVariants.PrimarySoft,
           isEnabled = true,
           isLoading = loginUIStateHolder.loginRequestResult.value is LoginState.Loading,
           onClick = onLoginClicked
       )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .layoutId("loginFooter"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.Start)
    ) {
        AppVersion(version = "1.0.0")
    }
    SesameToastPopup(
        modifier = Modifier
            .padding(
                horizontal = 16.dp
            )
            .fillMaxWidth()
            .layoutId("toast"),
        isShown = toastState.value != null,
        message = toastState.value?.second ?: "",
        iconResID = toastState.value?.first ?: R.drawable.ic_alert,
        sesameToastDefaults = SesameToastDefaults.getAlertToastStyle(),
        onDismissRequest = {
            toastState.value = null
        }
    )
}

}


@Composable
fun LoginForm(
    modifier: Modifier,
    email : String = "",
    password : String = "",
    onEmailChanged : (email : String) -> Unit ={},
    onPasswordChanged : (password : String)->Unit={}
) {
 Column(
     modifier = modifier
         .fillMaxWidth()
         .wrapContentHeight(),
     horizontalAlignment = Alignment.CenterHorizontally,
     verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
 ) {
     SesameEmailTextField(
         modifier = Modifier.semantics {
            contentDescription = "LoginEmailTextField"
         },
         text = email,
         isEnabled = true,
         isError = false,
         rightIconRes = R.drawable.ic_clear,
         onRightIconResClicked ={
             onEmailChanged("")
         },
         onEmailChanged = onEmailChanged
     )
     SesamePasswordTextField(
         modifier = Modifier.semantics {
             contentDescription = "LoginPasswordTextField"
         },
         password = password,
         label = stringResource(id = R.string.password_label) ,
         placeholder =stringResource(id = R.string.password_placeholder),
         isEnabled = true,
         isError = false,
         onPasswordChanged = onPasswordChanged
     )
 }
}