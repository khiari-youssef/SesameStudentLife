import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.BrickRed
import com.youapps.designsystem.OBFontFamilies
import com.youapps.designsystem.RoseEbony
import com.youapps.designsystem.components.loading.SesameCircularProgressBar


enum class OBButtonTheme{
    ContainedPrimary,ContainedSecondary ,OutlinedPrimary, OutlinedSecondary
}

enum class OBButtonSize{
    Small,Medium
}


@Composable
fun OBButton(
    modifier: Modifier = Modifier,
    text : String,
    theme : OBButtonTheme,
    size : OBButtonSize = OBButtonSize.Medium,
    isEnabled : Boolean = true,
    isLoading : Boolean = false,
    paddingValues: PaddingValues = if (size == OBButtonSize.Small ) {
        PaddingValues(
            horizontal = 12.dp,
            vertical = 4.dp
        )
    } else PaddingValues(
        horizontal = 12.dp,
        vertical = 8.dp
    ),
    fontSize : TextUnit = 16.sp,
    heightRangeDP : IntRange =  25..44,
    onClick: ()->Unit
) {
   Button(
        modifier = modifier
            .heightIn(heightRangeDP.first.dp,heightRangeDP.last.dp),
        enabled = isEnabled or isLoading,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
          containerColor = when (theme){
              OBButtonTheme.ContainedSecondary-> BrickRed
              OBButtonTheme.ContainedPrimary -> RoseEbony
              else -> Color.Unspecified
          },
        ),
       border =when (theme){
           OBButtonTheme.ContainedSecondary-> BorderStroke(width = 1.dp, color =  BrickRed)
           OBButtonTheme.ContainedPrimary -> BorderStroke(width = 1.dp, color = RoseEbony )
           else -> null
       },
       contentPadding = paddingValues,
        onClick = onClick
    ) {
       Crossfade(
           modifier = Modifier.height(20.dp),
           targetState = isLoading,
           label = "ButtonLoading",
           animationSpec = spring()
       ) {
           if (it){
               SesameCircularProgressBar(
                   modifier = Modifier.semantics {
                       contentDescription = "SesameButtonLoadingCircularProgressBar"
                   }.size(20.dp),
                   strokeWidth = 2.dp,
                   color = Color.White
               )
           } else {
               Text(
                   text = text,
                   style = TextStyle(
                       color = Color.White,
                       fontFamily = OBFontFamilies.MainMediumFontFamily,
                       fontSize = fontSize
                   )
               )
           }
       }


    }
}