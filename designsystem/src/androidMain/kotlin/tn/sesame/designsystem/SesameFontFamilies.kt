package tn.sesame.designsystem

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import tn.sesame.designsystem.R

data object SesameFontFamilies {
    val LogoFontRegular = FontFamily(Font(R.font.brand_font))
    val LogoFontBold = FontFamily(Font(R.font.brand_font))
    val MainRegularFontFamily = FontFamily(Font(R.font.roboto_regular))
    val MainMediumFontFamily = FontFamily(Font(R.font.roboto_medium))
    val MainBoldFontFamily = FontFamily(Font(R.font.roboto_bold))
    val MainItalicFontFamily = FontFamily(Font(R.font.roboto_italic))
}