import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.components.SesameFontFamilies
import tn.sesame.designsystem.onBackgroundShadedLightMode

@Composable
fun PlaceholderText(
    text : String,
    fontSize : TextUnit = 14.sp,
    align : TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        style = TextStyle(
            color = onBackgroundShadedLightMode,
            fontSize = fontSize,
            fontFamily = SesameFontFamilies.MainMediumFontFamily,
            textAlign = align
        )
    )
}