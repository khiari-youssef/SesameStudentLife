import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.onBackgroundShadedDarkMode
import tn.sesame.designsystem.onBackgroundShadedLightMode
import tn.sesame.spm.android.R

@Preview
@Composable
fun ProfileScreen(
modifier: Modifier = Modifier
) {
 Column(
     modifier = modifier
         .padding(16.dp),
     horizontalAlignment = Alignment.CenterHorizontally,
     verticalArrangement = Arrangement.spacedBy(
         8.dp,
         Alignment.Top
     )
 ) {
   Row(
       modifier = Modifier
           .fillMaxWidth()
           .wrapContentHeight(),
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.spacedBy(
           8.dp,
       Alignment.CenterHorizontally
       )
   ) {
       SesameCircleImageXL(
           uri = "",
           placeholderRes = tn.sesame.designsystem.R.drawable.profile_placeholder ,
           errorRes = tn.sesame.designsystem.R.drawable.profile_placeholder
       )
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .wrapContentHeight(),
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
             .padding(
                 start = 8.dp
             )
             .fillMaxWidth()
             .wrapContentHeight(),
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.Start
     ) {
         ClickableText(
             text = buildAnnotatedString {
                 append("Email : ")
                 withStyle(
                     SpanStyle(
                         color = MaterialTheme.colorScheme.surfaceVariant,
                         textDecoration = TextDecoration.Underline
                     )
                 ){
                     pushStringAnnotation(tag = "emailTag", annotation = "ahmed@sesame.com.tn")
                     append("ahmed@sesame.com.tn")
                 }
             },
             style = TextStyle(
                 fontSize = 16.sp,
                 fontFamily = SesameFontFamilies.MainMediumFontFamily,
                 fontWeight = FontWeight(500),
                 color = MaterialTheme.colorScheme.onBackground,
             ),
             onClick = {

             }
         )
     }
     Divider(
         modifier = Modifier
             .fillMaxWidth(0.95f)
     )
 }
}