import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.R

@Composable
fun AppTitleLogo(
    modifier: Modifier = Modifier
) {
       Row(
           modifier = modifier,
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.Absolute.Center
       ) {
           Image(
               modifier = Modifier
                   .size(32.dp),
               imageVector = ImageVector.vectorResource(id = R.drawable.ic_coffee_bean),
               contentDescription = ""
           )
           Text(
               modifier = modifier,
               textAlign = TextAlign.Center,
               style = TextStyle(
                   color = MaterialTheme.colorScheme.primary,
                   fontFamily = FontFamily(fonts = listOf(Font(R.font.app_title_font))),
                   fontSize = 48.sp
               ),
               text = "nly Beans"
           )
       }



}