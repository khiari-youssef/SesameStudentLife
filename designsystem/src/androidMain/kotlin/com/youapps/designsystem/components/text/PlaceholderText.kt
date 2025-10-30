package com.youapps.designsystem.components.text

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.OBFontFamilies
import com.youapps.designsystem.onBackgroundShadedDarkMode
import com.youapps.designsystem.onBackgroundShadedLightMode

@Composable
fun PlaceholderText(
    modifier: Modifier = Modifier,
    text : String,
    fontSize : TextUnit = 14.sp,
    align : TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        style = TextStyle(
            color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
            fontSize = fontSize,
            fontFamily = OBFontFamilies.MainMediumFontFamily,
            textAlign = align
        )
    )
}