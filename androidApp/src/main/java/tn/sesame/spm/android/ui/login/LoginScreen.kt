package tn.sesame.spm.android.ui.login

import AppBrand
import SesameEmailTextField
import SesamePasswordTextField
import SesameTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tn.sesame.spm.android.R

@Composable
fun LoginScreen(
loginUIStateHolder: LoginUIStateHolder,
modifier: Modifier = Modifier
) {
Column {
    AppBrand(
        modifier = Modifier
    )
}

}


@Composable
fun LoginForm(
    email : String = "",
    password : String = "",
    onEmailChanged : (email : String) -> Unit ={},
    onPasswordChanged : (password : String)->Unit={}
) {
 Column(
     modifier = Modifier
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