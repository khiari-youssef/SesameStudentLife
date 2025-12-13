package com.youapps.designsystem.components.text

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.TonedDark

@Composable
fun OBTextArea(
    modifier: Modifier = Modifier,
    label : String,
    text : String?=null,
    isEnabled: Boolean = true,
    maxChars : Int = 255,
    isRequired : Boolean = true,
    errorMessage : String?=null,
    onTextChanged : (String)->Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth(),
            text =   buildAnnotatedString {
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
            ),
            textAlign = TextAlign.Start
        )
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .requiredHeightIn(max = 120.dp)
                .fillMaxHeight(),
            onValueChange = onTextChanged,
            value = text ?: "",
            enabled = isEnabled,
            shape = RoundedCornerShape(8.dp),
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = if (isSystemInDarkTheme()) TonedDark else Color.White,
                unfocusedContainerColor = if (isSystemInDarkTheme()) TonedDark else Color.White,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTrailingIconColor = Color.Unspecified,
                disabledContainerColor = if (isSystemInDarkTheme()) Color(0xFF333333) else Color(0xFFD9D9D9),
                disabledTextColor = Color(0xFFB3B3B3),
                unfocusedTextColor =  MaterialTheme.colorScheme.onSurface,
                focusedTextColor =  MaterialTheme.colorScheme.onSurface
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Unspecified,
                showKeyboardOnFocus = true
            ),
            isError = errorMessage != null,
            supportingText = errorMessage?.run {
                {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
           Text(
               modifier = Modifier.fillMaxWidth(),
               text = "${text?.length ?: 0}/$maxChars",
               textAlign = TextAlign.End,
               style = MaterialTheme.typography.labelSmall.copy(
                   color = Color(0xFF757575)
               )
           )
        }
    }



}