package tn.sesame.designsystem.components.text

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.R
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.onBackgroundShadedDarkMode
import tn.sesame.designsystem.onBackgroundShadedLightMode



@Composable
fun DateText(
    modifier: Modifier = Modifier,
    label : String,
    value : String,
    valuePlaceholderResID : Int
) {
 Row(
     modifier = modifier,
     verticalAlignment = Alignment.CenterVertically,
     horizontalArrangement = Arrangement.spacedBy(
         8.dp,
         Alignment.Start
     )
 ) {
     val valuePlaceHolderResource = stringResource(valuePlaceholderResID)
    Icon(
        modifier = Modifier
            .requiredSize(24.dp),
        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_outlined),
        contentDescription = ""
    )
     Text(
         text = buildAnnotatedString {
          append("$label : ")
             withStyle(SpanStyle(
                 color = MaterialTheme.colorScheme.onBackground
             )) {
                 append(value.ifBlank { valuePlaceHolderResource })
             }
         },
         style = TextStyle(
             fontSize = 14.sp,
             fontFamily = SesameFontFamilies.MainMediumFontFamily,
             fontWeight = FontWeight(500),
             color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
         )
     )
 }
}