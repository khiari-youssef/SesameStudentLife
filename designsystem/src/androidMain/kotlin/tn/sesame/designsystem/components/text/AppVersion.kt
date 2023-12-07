import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.R
import tn.sesame.designsystem.components.SesameFontFamilies

@Composable
fun AppVersion(
    version : String
) {
  Text(
      style = TextStyle(
         color = MaterialTheme.colorScheme.primary,
          fontSize = 10.sp,
          fontFamily = SesameFontFamilies.MainMediumFontFamily
      ),
      modifier = Modifier
          .wrapContentSize(),
      maxLines = 1,
      text = stringResource(id = R.string.version_placeholder,version),
      textAlign = TextAlign.Start
  )
}