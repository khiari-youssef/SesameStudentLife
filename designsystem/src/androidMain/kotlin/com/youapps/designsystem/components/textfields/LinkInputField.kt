package com.youapps.designsystem.components.textfields

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.LinkColor
import com.youapps.designsystem.OBFontFamilies
import com.youapps.designsystem.R
import com.youapps.designsystem.TonedDark
import com.youapps.designsystem.components.text.PlaceholderText
import com.youapps.designsystem.onBackgroundShadedDarkMode
import com.youapps.designsystem.onBackgroundShadedLightMode


internal class LinkVisualTransformation(
    private val onLinkClicked : (link : String)-> Unit
): VisualTransformation{

    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            text = buildAnnotatedString {
                    withLink(
                        link = LinkAnnotation.Clickable(
                            tag = "LinkVisualTransformation",
                            linkInteractionListener = {
                                onLinkClicked(text.toString())
                            },
                            styles = TextLinkStyles(
                                style = SpanStyle(
                                    background = LinkColor,
                                    fontSize = 14.sp,
                                    fontStyle = FontStyle.Normal,
                                    fontFamily = OBFontFamilies.MainRegularFontFamily,
                                    letterSpacing = 1.sp
                                )
                            )
                        ),
                    ) {
                        append(text = text)
                    }
            } ,
            OffsetMapping.Identity
        )
    }
}

@Composable
fun LinkInputField(
    modifier: Modifier = Modifier,
    link: String,
    isEnabled: Boolean = true,
    isRequired: Boolean = false,
    errorMessage : String?=null,
    onLinkChanged: (link: String) -> Unit,
    onValidLinkClicked : (link: String) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = buildAnnotatedString {
                append("${stringResource(R.string.links_label)}:")
                if (isRequired) {
                    withStyle(
                        style = SpanStyle(color = MaterialTheme.colorScheme.error)
                    ) {
                        append("*")
                    }
                }
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (isEnabled) MaterialTheme.colorScheme.onSurface else Color(0xFFB3B3B3)
            )
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            textStyle = TextStyle(
                fontSize = 14.sp,
                fontStyle = FontStyle.Normal,
                fontFamily = OBFontFamilies.MainRegularFontFamily,
                letterSpacing = 1.sp,
                lineHeight = 24.sp
            ),
            value = link,
            keyboardActions= KeyboardActions.Default,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            placeholder = {
                PlaceholderText(
                    text = stringResource(R.string.links_placeholder),
                    fontSize = 14.sp
                )
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_link),
                    contentDescription = stringResource(R.string.content_description_link_button,link),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            enabled = isEnabled,
            supportingText = errorMessage?.run {
                {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Start,
                    )
                }
            },
            isError = errorMessage != null,
            singleLine = true,
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
            onValueChange = onLinkChanged
        )
    }

}