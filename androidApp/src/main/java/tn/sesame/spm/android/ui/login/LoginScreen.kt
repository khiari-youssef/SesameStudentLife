package tn.sesame.spm.android.ui.login


import AppVersion
import LoginAnimation
import SesameButton
import SesameButtonVariants
import SesameEmailTextField
import SesamePasswordTextField
import SesameTextField
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import tn.sesame.designsystem.components.AppBrand
import tn.sesame.spm.android.BuildConfig


@Composable
fun LoginScreen(
modifier: Modifier = Modifier,
loginUIStateHolder: LoginUIStateHolder,
onEmailChanged: (email: String) -> Unit,
onPasswordChanged: (password: String) -> Unit,
onLoginClicked : ()->Unit
) {
   val isLargeScreen  = LocalConfiguration.current.run {
       (orientation == Configuration.ORIENTATION_LANDSCAPE) or (this.screenWidthDp >= 600)
   }
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
               .wrapContentHeight()
               .fillMaxWidth(0.9f),
           text = stringResource(id = tn.sesame.designsystem.R.string.login),
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
        AppVersion(version = BuildConfig.VERSION_NAME)
    }
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
         text = email,
         isEnabled = true,
         isError = false,
         rightIconRes = tn.sesame.designsystem.R.drawable.ic_clear,
         onRightIconResClicked ={
             onEmailChanged("")
         },
         onEmailChanged = onEmailChanged
     )
     SesamePasswordTextField(
         password = password,
         label = stringResource(id = tn.sesame.designsystem.R.string.password_label) ,
         placeholder =stringResource(id = tn.sesame.designsystem.R.string.password_placeholder),
         isEnabled = true,
         isError = false,
         onPasswordChanged = onPasswordChanged
     )
 }
}