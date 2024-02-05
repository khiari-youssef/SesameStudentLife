import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.LightGreyBlue
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.components.text.PlaceholderText
import tn.sesame.designsystem.onBackgroundShadedDarkMode
import tn.sesame.designsystem.onBackgroundShadedLightMode

@Composable
fun SesameTextField(
    modifier: Modifier =Modifier,
    text: String,
    label: String,
    placeholder: String,
    isEnabled: Boolean,
    isReadOnly: Boolean,
    isError: Boolean,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation : VisualTransformation = VisualTransformation.None,
    leftIconRes : Int?=null,
    rightIconRes : Int?=null,
    onLeftIconResClicked : (()->Unit)?=null,
    onRightIconResClicked : (()->Unit)?=null,
    onTextChanged: (text: String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        textStyle = TextStyle(
            fontSize = 14.sp,
            fontStyle = FontStyle.Normal,
            fontFamily = SesameFontFamilies.MainRegularFontFamily,
            letterSpacing = 1.sp,
            lineHeight = 24.sp
        ),
        value = text,
        keyboardActions= keyboardActions,
        label = {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = SesameFontFamilies.MainMediumFontFamily,
                    textAlign = TextAlign.Start
                )
            )
        },
        placeholder = {
            PlaceholderText(
                text = placeholder,
                fontSize = 14.sp
            )
        },
        enabled = isEnabled,
        readOnly = isReadOnly,
        isError = isError,
        singleLine = true,
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
                    tint = if (isSystemInDarkTheme()) LightGreyBlue else MaterialTheme.colorScheme.secondary
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
                    tint = if (isSystemInDarkTheme()) LightGreyBlue else MaterialTheme.colorScheme.secondary
                )
            }
        },
        visualTransformation = visualTransformation,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Unspecified,
            cursorColor = if (isSystemInDarkTheme()) LightGreyBlue else MaterialTheme.colorScheme.secondary,
            focusedLabelColor = if (isSystemInDarkTheme()) LightGreyBlue else MaterialTheme.colorScheme.secondary,
            focusedTrailingIconColor = Color.Unspecified,
            unfocusedContainerColor = Color.Unspecified,
            focusedBorderColor = if (isSystemInDarkTheme()) LightGreyBlue else MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode
        ),
        onValueChange = onTextChanged
    )

}