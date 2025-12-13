package com.youapps.designsystem.components.textfields

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.youapps.designsystem.OBFontFamilies
import com.youapps.designsystem.R
import com.youapps.designsystem.TonedDark
import com.youapps.designsystem.components.menus.DropDownMenuData
import com.youapps.designsystem.components.menus.DropDownMenuItemData
import com.youapps.designsystem.components.menus.ImageMediaType
import com.youapps.designsystem.components.menus.OBDropDownMenu
import com.youapps.designsystem.components.text.PlaceholderText
import com.youapps.designsystem.onBackgroundShadedDarkMode
import com.youapps.designsystem.onBackgroundShadedLightMode


@Composable
fun OBPhoneInput(
    modifier: Modifier = Modifier,
    phoneNumber: String,
    selectedCountryCode: State<DropDownMenuItemData?>,
    countryCodesDropDownMenuData: DropDownMenuData,
    isRequired: Boolean = false,
    isEnabled: Boolean = true,
    errorMessage : String?=null,
    dropDownScrollState: ScrollState = rememberScrollState(),
    onDropDownDismissed: ()-> Unit,
    onPhoneNumberChanged: (number : String) -> Unit,
    onCountryCodeChanged: (DropDownMenuItemData) -> Unit,
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = buildAnnotatedString {
                append("${stringResource(R.string.phone_number_label)}:")
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
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
        ){
            Box(
                modifier = Modifier.weight(0.3f),
                contentAlignment = Alignment.Center
            ) {
                AssistChip(
                    modifier = Modifier
                        .height(
                            height = 56.dp
                        ).fillMaxWidth(),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (isSystemInDarkTheme()) TonedDark else Color.White,
                        labelColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = MaterialTheme.shapes.small ,
                    label = {
                        Text(
                            text = selectedCountryCode.value?.label ?: "",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Normal,
                                fontFamily = OBFontFamilies.MainRegularFontFamily,
                                letterSpacing = 1.sp,
                                lineHeight = 24.sp
                            )
                        )
                    },
                    onClick = {
                        isExpanded.value = true
                    },
                    leadingIcon = {
                        selectedCountryCode.value?.icon?.run {
                            val label : String = selectedCountryCode.value?.label ?: ""
                            when (this){
                                is ImageMediaType.Resource ->Icon(
                                    modifier = Modifier.size(this.size ?: DpSize(
                                        width = 25.dp,
                                        height = 30.dp)),
                                    imageVector = ImageVector.vectorResource(this.resId),
                                    contentDescription = stringResource(R.string.content_description_drop_down_item,label),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                                is ImageMediaType.Url ->  AsyncImage(
                                    modifier = Modifier.size(this.size ?: DpSize(
                                        width = 25.dp,
                                        height = 30.dp)),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(this.url)
                                        .diskCachePolicy(CachePolicy.ENABLED)
                                        .memoryCachePolicy(CachePolicy.ENABLED)
                                        .build() ,
                                    contentDescription = stringResource(R.string.content_description_drop_down_item,label),
                                )
                            }
                        }

                    }
                )
                OBDropDownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    scrollState = dropDownScrollState,
                    isExpanded = isExpanded.value,
                    onExpandedChange = {
                        isExpanded.value = it
                        if (!it) {
                            onDropDownDismissed()
                        }
                    },
                    onClick = { data->
                        onCountryCodeChanged(data)
                        isExpanded.value = false
                        onDropDownDismissed()
                    },
                    data = countryCodesDropDownMenuData
                )
            }
            OutlinedTextField(
                modifier = Modifier.weight(0.7f),
                shape = MaterialTheme.shapes.small,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Normal,
                    fontFamily = OBFontFamilies.MainRegularFontFamily,
                    letterSpacing = 1.sp,
                    lineHeight = 24.sp
                ),
                value = phoneNumber,
                keyboardActions= KeyboardActions.Default,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                placeholder = {
                    PlaceholderText(
                        text = stringResource(R.string.phone_number_placeholder),
                        fontSize = 14.sp
                    )
                },
                enabled = isEnabled,
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
                onValueChange = onPhoneNumberChanged
            )
        }
        AnimatedVisibility(visible = errorMessage != null) {
            errorMessage?.run {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

    }

}