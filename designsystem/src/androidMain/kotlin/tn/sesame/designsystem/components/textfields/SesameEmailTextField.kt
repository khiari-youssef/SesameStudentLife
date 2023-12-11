import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.R
import tn.sesame.designsystem.components.SesameFontFamilies
import tn.sesame.designsystem.onBackgroundShadedLightMode

@Composable
fun SesameEmailTextField(
    text: String,
    isEnabled: Boolean,
    isReadOnly: Boolean = false,
    isError: Boolean,
    rightIconRes : Int?=null,
    onRightIconResClicked : (()->Unit)?=null,
    onEmailChanged: (text: String) -> Unit
) {
   SesameTextField(
       text = text,
       label = stringResource(id = R.string.email_address_label) ,
       placeholder = stringResource(id = R.string.email_address_placeholder)  ,
       isEnabled = isEnabled,
       isReadOnly = isReadOnly,
       isError = isError ,
       rightIconRes =rightIconRes,
       onRightIconResClicked = onRightIconResClicked,
       onTextChanged =  onEmailChanged
   )

}