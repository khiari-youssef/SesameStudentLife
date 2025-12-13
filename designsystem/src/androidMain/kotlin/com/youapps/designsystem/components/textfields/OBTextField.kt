import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.OBFontFamilies
import com.youapps.designsystem.TonedDark
import com.youapps.designsystem.components.text.PlaceholderText
import com.youapps.designsystem.onBackgroundShadedDarkMode
import com.youapps.designsystem.onBackgroundShadedLightMode





@Composable
fun OBTextField(
    modifier: Modifier =Modifier,
    text: String,
    label: String,
    placeholder: String,
    isRequired: Boolean = false,
    isEnabled: Boolean = true,
    errorMessage: String? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation : VisualTransformation = VisualTransformation.None,
    leftIconRes : Int?=null,
    rightIconRes : Int?=null,
    onLeftIconResClicked : (()->Unit)?=null,
    onRightIconResClicked : (()->Unit)?=null,
    onTextChanged: (text: String) -> Unit
) {

    Column(
     modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = buildAnnotatedString {
                append("$label:")
                if (isRequired) {
                 withStyle(
                     style = SpanStyle(color = MaterialTheme.colorScheme.error)
                 ){
                     append("*")
                 }
                }
            },
            style = MaterialTheme.typography.bodyMedium.copy(
               color = if (isEnabled) MaterialTheme.colorScheme.onSurface else Color(0xFFB3B3B3)
            )
        )
        OutlinedTextField(
            modifier = modifier,
            shape = MaterialTheme.shapes.small,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontStyle = FontStyle.Normal,
                fontFamily = OBFontFamilies.MainRegularFontFamily,
                letterSpacing = 1.sp,
                lineHeight = 24.sp
            ),
            value = text,
            keyboardActions= keyboardActions,
            placeholder = {
                PlaceholderText(
                    text = placeholder,
                    fontSize = 14.sp
                )
            },
            enabled = isEnabled,
            isError = errorMessage != null,
            singleLine = true,
            supportingText = errorMessage?.run {
                {
                   Text(
                       text = errorMessage,
                       style = MaterialTheme.typography.labelMedium,
                       textAlign = TextAlign.Start,
                   )
                }
            },
            leadingIcon = leftIconRes?.run{
                {
                    Icon(
                        modifier = Modifier.clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = {
                                onLeftIconResClicked?.let { callback->
                                    callback()
                                }
                            }) ,
                        imageVector = ImageVector.vectorResource(this),
                        contentDescription = "",
                        tint =  MaterialTheme.colorScheme.secondary
                    )
                }
            },
            trailingIcon = rightIconRes?.run{
                {
                    Icon(
                        modifier = Modifier.clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            onRightIconResClicked?.let { callback->
                                callback()
                            }
                        },
                        imageVector = ImageVector.vectorResource(this),
                        contentDescription = "",
                        tint =  MaterialTheme.colorScheme.primary
                    )
                }
            },
            visualTransformation = visualTransformation,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = if (isSystemInDarkTheme()) TonedDark else Color.White,
                unfocusedContainerColor = if (isSystemInDarkTheme()) TonedDark else Color.White,
                errorContainerColor = if (isSystemInDarkTheme()) TonedDark else Color.White,
                cursorColor =   MaterialTheme.colorScheme.primary,
                focusedLabelColor =  MaterialTheme.colorScheme.primary,
                focusedTrailingIconColor = Color.Unspecified,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                disabledContainerColor = if (isSystemInDarkTheme()) Color(0xFF333333) else Color(0xFFD9D9D9),
                disabledTextColor = Color(0xFFB3B3B3),
                unfocusedBorderColor = Color(0xFFD9D9D9),
                unfocusedTextColor =  MaterialTheme.colorScheme.onSurface,
                focusedTextColor =  MaterialTheme.colorScheme.onSurface
            ),
            onValueChange = onTextChanged
        )
    }


}