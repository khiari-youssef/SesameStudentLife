import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import tn.sesame.designsystem.R

@Composable
fun SesameEmailTextField(
    text: String,
    isEnabled: Boolean,
    isReadOnly: Boolean = false,
    isError: Boolean,
    rightIconRes : Int?=null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onRightIconResClicked : (()->Unit)?=null,
    onEmailChanged: (text: String) -> Unit
) {
   SesameTextField(
       text = text,
       label = stringResource(id = R.string.email_address_label) ,
       placeholder = stringResource(id = R.string.email_address_placeholder)  ,
       isEnabled = isEnabled,
       isReadOnly = isReadOnly,
       keyboardActions= keyboardActions,
       isError = isError ,
       rightIconRes =rightIconRes,
       onRightIconResClicked = onRightIconResClicked,
       onTextChanged =  onEmailChanged
   )

}