package com.youapps.users_management.ui.login


import AppTitleLogo
import AppVersion
import OBButtonContainedPrimary
import OBEmailTextField
import SesamePasswordTextField
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.youapps.designsystem.components.popups.SesameToastDefaults
import com.youapps.designsystem.components.popups.SesameToastPopup
import com.youapps.onlybeans.domain.exception.DomainErrorType
import com.youapps.users_management.R
import com.youapps.designsystem.R as DSR

typealias ToastState = Pair<Int,String>

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginUIStateHolder: LoginUIStateHolder,
    onEmailChanged: (email: String) -> Unit,
    onPasswordChanged: (password: String) -> Unit,
    onSetIdleState : ()->Unit,
    onLoginClicked : ()->Unit,
    onSignUpClicked : ()->Unit

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
            toastState.value =  (DSR.drawable.ic_alert to when(state.errorType){
                DomainErrorType.AccountLocked -> {
                    localContext.getString(DSR.string.error_toast_locked)
                }
                DomainErrorType.Unauthorized ->{
                    localContext.getString(DSR.string.error_toast_unauthorized)
                }
                DomainErrorType.InvalidCredentials ->{
                    localContext.getString(DSR.string.error_toast_invalid_credentials)
                }
                else -> localContext.getString(DSR.string.error_toast_unknown)
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

    LoginScreenTop(
        modifier = Modifier
            .layoutId("LoginScreenTop")
    )
    AppTitleLogo(
        modifier = Modifier
            .layoutId("AppTitleLogo")
    )
    LoginForm(
        modifier = Modifier
            .layoutId("loginForm"),
        email = loginUIStateHolder.loginEmail.value,
        password = loginUIStateHolder.loginPassword.value,
        onEmailChanged = onEmailChanged,
        onPasswordChanged = onPasswordChanged
    )
    Text(
        modifier = Modifier.fillMaxWidth().layoutId("signUpAction"),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center,
        text = buildAnnotatedString {
            val context = LocalContext.current
            val text1 = stringResource(R.string.login_no_account)
            val text2 = stringResource(R.string.login_sign_up_action)
            val primaryColor : Color =  MaterialTheme.colorScheme.primary
            append(text1)
            appendLine()
            withLink(
                link = LinkAnnotation.Clickable(
                    tag = "SignUpActionText",
                    styles = TextLinkStyles(SpanStyle(color = primaryColor)),
                    linkInteractionListener = { it->
                        onSignUpClicked()
                    },
                )
            ){
                append(text2)
            }

        }
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .layoutId("loginButton"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterHorizontally)
    ) {
       OBButtonContainedPrimary(
           modifier = Modifier
               .semantics {
                   contentDescription = "LoginButton"
               }
               .wrapContentHeight()
               .fillMaxWidth(0.9f),
           text = stringResource(id = DSR.string.login),
           isEnabled = true,
           isLoading = loginUIStateHolder.loginRequestResult.value is LoginState.Loading,
           onClick = onLoginClicked
       )
    }
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .layoutId("loginFooter"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
    ) {
        LoginScreenBottom(
            modifier = Modifier
                .layoutId("LoginScreenBottom")
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.Start)
        ) {
            AppVersion(version = "1.0.0")
        }
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
        iconResID = toastState.value?.first ?: DSR.drawable.ic_alert,
        sesameToastDefaults = SesameToastDefaults.getAlertToastStyle(),
        onDismissRequest = {
            toastState.value = null
        }
    )
}

}

@Composable
fun LoginScreenTop(modifier : Modifier) {
  Row(
      modifier = modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
  ) {
      Image(
          modifier = Modifier
              .rotate(30f)
              .size(72.dp),
          imageVector = ImageVector.vectorResource(id = DSR.drawable.ic_coffee_dripper),
          contentDescription = ""
      )
      Image(
          modifier = Modifier
              .size(72.dp),
          imageVector = ImageVector.vectorResource(id = DSR.drawable.ic_espresso_machine),
          contentDescription = ""
      )
      Image(
          modifier = Modifier
              .rotate(-30f)
              .size(72.dp),
          imageVector = ImageVector.vectorResource(id = DSR.drawable.ic_coffee_cup),
          contentDescription = ""
      )
  }
}
@Composable
fun LoginScreenBottom(modifier : Modifier) {
  Row(
      modifier = modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
  ) {
      Image(
          modifier = Modifier
              .rotate(30f)
              .size(72.dp),
          imageVector = ImageVector.vectorResource(id = DSR.drawable.ic_coffee_press),
          contentDescription = ""
      )
      Image(
          modifier = Modifier
              .size(72.dp),
          imageVector = ImageVector.vectorResource(id = DSR.drawable.ic_coffee_brunch),
          contentDescription = ""
      )
      Image(
          modifier = Modifier
              .rotate(-30f)
              .size(72.dp),
          imageVector = ImageVector.vectorResource(id = DSR.drawable.ic_coffee_grinder),
          contentDescription = ""
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
     OBEmailTextField(
         modifier = Modifier
             .focusGroup()
             .fillMaxWidth()
             .wrapContentHeight()
             .semantics {
                 contentDescription = "LoginEmailTextField"
             },
         text = email,
         isEnabled = true,
         rightIconRes = DSR.drawable.ic_clear,
         keyboardActions = KeyboardActions.Default,
         onRightIconResClicked ={
             onEmailChanged("")
         },
         onEmailChanged = onEmailChanged
     )
     SesamePasswordTextField(
         modifier = Modifier
             .focusGroup()
             .fillMaxWidth()
             .wrapContentHeight()
             .semantics {
                 contentDescription = "LoginPasswordTextField"
             },
         password = password,
         label = stringResource(id = DSR.string.password_label) ,
         placeholder =stringResource(id = DSR.string.password_placeholder),
         keyboardActions = KeyboardActions.Default,
         isEnabled = true,
         onPasswordChanged = onPasswordChanged
     )
 }
}