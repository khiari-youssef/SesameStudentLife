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
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        label = {
            Text(
                text = stringResource(id = R.string.email_address_label),
                style = TextStyle(
                    color = onBackgroundShadedLightMode,
                    fontSize = 12.sp,
                    fontFamily = SesameFontFamilies.MainMediumFontFamily,
                    textAlign = TextAlign.Start
                )
            )
        },
        placeholder = {
            PlaceholderText(
                text = stringResource(id = R.string.email_address_placeholder),
                fontSize = 14.sp
            )
        },
        enabled = isEnabled,
        readOnly = isReadOnly,
        isError = isError,
        singleLine = true,
        trailingIcon = rightIconRes?.run{
            {
                Icon(
                    modifier = Modifier.clickable {
                        onRightIconResClicked?.let { callback->
                            callback()
                        }
                    },
                    imageVector = ImageVector.vectorResource(this),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        },
        colors = TextFieldDefaults.colors(
           focusedContainerColor = Color.Unspecified,
           focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            focusedTrailingIconColor = Color.Unspecified,
            unfocusedContainerColor = Color.Unspecified
        ),
        onValueChange = onEmailChanged
    )

}