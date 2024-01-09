import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.ErrorColor
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.SuccessColor
import tn.sesame.spm.android.R
import tn.sesame.spm.domain.entities.SesameProjectNotification



@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    sesameProjectNotification : SesameProjectNotification
) {
 Column(
     modifier = modifier
         .background(
             color = MaterialTheme.colorScheme.surfaceVariant
         )
         .padding(12.dp),
     horizontalAlignment = Alignment.Start,
     verticalArrangement = Arrangement.spacedBy(
         8.dp,Alignment.CenterVertically
     )
 ) {
     val notificationContent = buildAnnotatedString {
         withStyle(SpanStyle(
             fontFamily = SesameFontFamilies.MainBoldFontFamily,
             fontWeight = FontWeight.W700
         )){
             append(sesameProjectNotification.senderFullName)
         }
         append(" sent this notification")
         withStyle(SpanStyle(
             fontFamily = SesameFontFamilies.MainBoldFontFamily,
             fontWeight = FontWeight.W700,
             color = MaterialTheme.colorScheme.primary,
             textDecoration = TextDecoration.Underline
         )){
             append(" #IP01")
         }
     }
     Row(
       modifier = Modifier,
      verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.spacedBy(
             8.dp,Alignment.CenterHorizontally
         )
     ) {
       SesameCircleImageM(
           uri = sesameProjectNotification.senderImage,
           placeholderRes = tn.sesame.designsystem.R.drawable.profile_placeholder ,
           errorRes = tn.sesame.designsystem.R.drawable.profile_placeholder
       )
       ClickableText(
           modifier = Modifier
               .fillMaxWidth(),
             text = notificationContent,
             style = TextStyle(
                 fontSize = 14.sp,
                 fontFamily = SesameFontFamilies.MainRegularFontFamily,
                 fontWeight = FontWeight(400),
                 color = MaterialTheme.colorScheme.onBackground,
             ),
           onClick = { offset->

           }
        )
     }
     when (sesameProjectNotification){
         is SesameProjectNotification.SesameProjectInfoNotification->{

         }
         is SesameProjectNotification.SesameProjectRequestNotification->{
             Row(
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(
                         end = 8.dp
                     ),
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.spacedBy(
                     16.dp,Alignment.End
                 )
             ) {
                 Text(
                     modifier = Modifier
                         .clickable{

                         }
                         .padding(8.dp)
                         .wrapContentSize(),
                     text = "Accept",
                     style = TextStyle(
                         fontSize = 16.sp,
                         fontFamily = SesameFontFamilies.MainBoldFontFamily,
                         fontWeight = FontWeight(700),
                         color = SuccessColor,
                         textAlign = TextAlign.End
                     )
                 )
                 Text(
                     modifier = Modifier
                         .clickable {

                         }
                         .padding(8.dp)
                         .wrapContentSize(),
                     text = "Deny",
                     style = TextStyle(
                         fontSize = 16.sp,
                         fontFamily = SesameFontFamilies.MainBoldFontFamily,
                         fontWeight = FontWeight(700),
                         color = ErrorColor,
                         textAlign = TextAlign.End
                     )
                 )
             }
         }
         is SesameProjectNotification.SesameProjectResponseNotification->{
             Row(
                 modifier = Modifier
                     .padding(
                         end = 8.dp
                     ),
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.spacedBy(
                     8.dp,Alignment.End
                 )
             ) {

             }
         }
     }

 }
}