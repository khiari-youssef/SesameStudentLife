import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.SesameFontFamilies

@Preview
@Composable
fun SesameCircularLoadingProgress(
    modifier: Modifier = Modifier
) {
Card(
    modifier = modifier
        .wrapContentHeight(),
    colors = CardDefaults
        .cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
    shape = MaterialTheme.shapes.large,
    border = BorderStroke(color = MaterialTheme.colorScheme.secondary, width = 1.dp )
) {
  Row(
      modifier = Modifier
          .padding(8.dp)
          .wrapContentSize(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement
          .spacedBy(12.dp,Alignment.CenterHorizontally)
  ) {
    CircularProgressIndicator(
        modifier = Modifier
            .size(16.dp),
        strokeWidth = 2.dp,
        color = MaterialTheme.colorScheme.secondary
    )
    Text(
        modifier = Modifier
            .animateContentSize(
                animationSpec = tween(800)
            )
            .wrapContentSize(),
        text = "Loading ...",
        style = TextStyle(
            fontSize = 12.sp,
            fontFamily = SesameFontFamilies.MainMediumFontFamily,
            color = MaterialTheme.colorScheme.secondary
        )
    )
  }
}
}