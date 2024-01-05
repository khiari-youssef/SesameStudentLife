package tn.sesame.spm.android.ui.profile

import SesameCircleImageXL
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.R
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.onBackgroundShadedDarkMode
import tn.sesame.designsystem.onBackgroundShadedLightMode

@Composable
fun UserProfileDetails(
    modifier: Modifier = Modifier,
    onProfileEmailClicked : (email : String)->Unit
) {
    Column(modifier = modifier){
        Row(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                24.dp,
                Alignment.CenterHorizontally
            )
        ) {
            SesameCircleImageXL(
                uri = "",
                placeholderRes = R.drawable.profile_placeholder ,
                errorRes = R.drawable.profile_placeholder
            )
            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Text(
                    text = "Ahmed",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = SesameFontFamilies.MainBoldFontFamily,
                        fontWeight = FontWeight(700),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                )
                Text(
                    text = "Student",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = SesameFontFamilies.MainMediumFontFamily,
                        fontWeight = FontWeight(500),
                        color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                    )
                )
                Text(
                    text = "INGTA-3C",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = SesameFontFamilies.MainMediumFontFamily,
                        fontWeight = FontWeight(500),
                        color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                    )
                )
                Text(
                    text = "Software engineer",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = SesameFontFamilies.MainMediumFontFamily,
                        fontWeight = FontWeight(500),
                        color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                    )
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            val annotatedString = buildAnnotatedString {
                append("${stringResource(id = tn.sesame.spm.android.R.string.profile_email)} :  ")
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ){
                    pushStringAnnotation(tag = "emailTag", annotation = "ahmed@sesame.com.tn")
                    append("ahmed@sesame.com.tn")
                }
            }
            ClickableText(
                text =  annotatedString,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = SesameFontFamilies.MainMediumFontFamily,
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                onClick = {
                    annotatedString.getStringAnnotations(it,it).find {
                        it.tag == "emailTag"
                    }?.run {
                        onProfileEmailClicked(this.item)
                    }
                }
            )
        }
    }
}