import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import tn.sesame.designsystem.R


@Composable
fun SesamePasswordTextField(
    password : String,
    label : String,
    placeholder : String,
    isEnabled : Boolean,
    isError : Boolean,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    onPasswordChanged : (text : String)->Unit
) {
    val isPasswordRevealed = remember {
        mutableStateOf(false)
    }
    val visualTransformation = remember {
        derivedStateOf {
            if (isPasswordRevealed.value){
                VisualTransformation.None
            } else PasswordVisualTransformation()
        }
    }
    SesameTextField(
        text = password,
        label = label ,
        placeholder = placeholder,
        isEnabled = isEnabled,
        isReadOnly = false ,
        isError = isError,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation.value ,
        rightIconRes = if (isPasswordRevealed.value) R.drawable.ic_password_revealed else R.drawable.ic_password_hidden,
        onRightIconResClicked = remember {
            {
                isPasswordRevealed.value = isPasswordRevealed.value.not()
            }
        },
        onTextChanged = onPasswordChanged
    )
}