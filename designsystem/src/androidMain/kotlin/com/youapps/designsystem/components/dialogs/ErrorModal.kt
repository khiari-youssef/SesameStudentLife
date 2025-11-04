import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.OBFontFamilies
import com.youapps.designsystem.R
import com.youapps.designsystem.onBackgroundShadedDarkMode
import com.youapps.designsystem.onBackgroundShadedLightMode



@Composable
fun ErrorModal(
    modifier: Modifier = Modifier,
    title : String,
    details : String,
    imgRes : Int?=null,
    onRetryAction : ()->Unit
) {
   Card(
       modifier = modifier
           .fillMaxWidth()
           .wrapContentHeight(),
       colors = CardDefaults.cardColors(
           containerColor = MaterialTheme.colorScheme.background
       ),
       shape = MaterialTheme.shapes.large
   ) {
       Column(
           modifier = Modifier
               .padding(24.dp)
               .fillMaxWidth()
               .wrapContentHeight(),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.spacedBy(
               24.dp,Alignment.CenterVertically
           )
       ) {
          Column(
              modifier = Modifier
                  .fillMaxWidth()
                  .wrapContentHeight(),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(
                  16.dp,Alignment.CenterVertically
              )
          ) {
              Text(
                  modifier = Modifier
                      .fillMaxWidth()
                      .wrapContentHeight(),
                  text = title,
                  style = TextStyle(
                      fontSize = 20.sp,
                      fontFamily = OBFontFamilies.MainBoldFontFamily,
                      fontWeight = FontWeight(700),
                      color = MaterialTheme.colorScheme.onBackground,
                      textAlign = TextAlign.Center
                  )
              )
              if (details.isNotBlank()){
                  Text(
                      modifier = Modifier
                          .fillMaxWidth()
                          .wrapContentHeight(),
                      text = details,
                      style = TextStyle(
                          fontSize = 16.sp,
                          fontFamily = OBFontFamilies.MainMediumFontFamily,
                          fontWeight = FontWeight(500),
                          color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                          textAlign = TextAlign.Center
                      ),
                      maxLines = 3,
                      overflow = TextOverflow.Ellipsis
                  )
              }
          }
           imgRes?.run {
               Image(
                   imageVector = ImageVector.vectorResource(imgRes) ,
                   contentDescription = "",
               )
           }
           OBButtonContainedSecondary(
               text = stringResource(id = R.string.retry),
               isEnabled = true,
               isLoading = false,
               onClick = onRetryAction
           )
       }
   }
}

