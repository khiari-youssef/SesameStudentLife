import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.youapps.designsystem.R

@Composable
fun OBEmailTextField(
    modifier : Modifier = Modifier,
    text: String,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    rightIconRes : Int?=null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onRightIconResClicked : (()->Unit)?=null,
    onEmailChanged: (text: String) -> Unit
) {
   OBTextField(
       modifier = modifier,
       text = text,
       label = stringResource(id = R.string.email_address_label) ,
       placeholder = stringResource(id = R.string.email_address_placeholder)  ,
       isEnabled = isEnabled,
       keyboardActions= keyboardActions,
       isError = isError ,
       rightIconRes =rightIconRes,
       onRightIconResClicked = onRightIconResClicked,
       onTextChanged =  onEmailChanged
   )

}