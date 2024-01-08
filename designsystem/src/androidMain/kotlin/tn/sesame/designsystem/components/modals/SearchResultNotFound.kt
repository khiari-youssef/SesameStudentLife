import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import tn.sesame.designsystem.R
import tn.sesame.designsystem.SesameFontFamilies

@Composable
fun SearchResultNotFound(
    modifier: Modifier = Modifier,
    message : String = stringResource(id = R.string.result_not_found),
) {
   Column(
       modifier = modifier,
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.spacedBy(
           16.dp
       )
   ) {
       Text(
           modifier = Modifier
               .fillMaxWidth(),
           text = message,
           style = TextStyle(
               fontSize = 24.sp,
               fontFamily = SesameFontFamilies.MainMediumFontFamily,
               fontWeight = FontWeight(500),
               color = MaterialTheme.colorScheme.tertiary,
               textAlign = TextAlign.Center,
           )
       )
       val composition by rememberLottieComposition(
           spec = LottieCompositionSpec.RawRes(R.raw.search_not_found),
       )
       val progress by animateLottieCompositionAsState(composition)
       LottieAnimation(
           modifier = Modifier,
           composition = composition,
           progress = {progress}
       )
   }
}