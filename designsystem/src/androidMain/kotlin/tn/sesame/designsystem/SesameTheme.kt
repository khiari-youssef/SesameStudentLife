package tn.sesame.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.components.SesameFontFamilies

@Composable
fun SesameTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = NiceBlue,
            secondary = DuskBlue,
            tertiary = BlueHosta,
            surfaceVariant = Charcoal2,
            surface = Alabaster,
            primaryContainer = TonedDark,
            background = Dark
        )
    } else {
        lightColorScheme(
            primary = NiceBlue,
            secondary = DuskBlue,
            tertiary = BlueHosta,
            surfaceVariant = AliceBlue,
            surface = Alabaster,
            primaryContainer = ShadedWhite
        )
    }
    val typography = Typography(
        bodyMedium = TextStyle(
            fontFamily = SesameFontFamilies.MainRegularFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        extraSmall = RoundedCornerShape(2.dp),
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(12.dp),
        extraLarge = RoundedCornerShape(16.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )

}
