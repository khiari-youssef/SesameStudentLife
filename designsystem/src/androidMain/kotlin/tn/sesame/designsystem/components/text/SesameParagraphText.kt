package tn.sesame.designsystem.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.SesameFontFamilies
import java.util.Locale

@Composable
fun  SesameParagraphText(
    modifier: Modifier = Modifier,
    paragraph : String,
    placeholderRes : Int
) {
    if (paragraph.isBlank()) {
        PlaceholderText(
            modifier = modifier,
            text = stringResource(id = placeholderRes)
        )
    } else {
        Text(
            modifier = modifier,
            text = paragraph.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            },
            style = TextStyle(
                fontSize = 13.sp,
                lineHeight = 20.sp,
                fontFamily = SesameFontFamilies.MainRegularFontFamily,
                fontWeight = FontWeight(400),
                color = MaterialTheme.colorScheme.onBackground,
                letterSpacing = 0.52.sp,
                lineBreak = LineBreak.Paragraph,
            )
        )
    }

}