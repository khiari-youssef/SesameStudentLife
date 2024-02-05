import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import tn.sesame.designsystem.DuskBlue
import tn.sesame.designsystem.ErrorColor
import tn.sesame.designsystem.NiceBlue
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.components.loading.SesameCircularProgressBar


enum class SesameButtonVariants{
    PrimarySoft,PrimaryHard,PrimaryAlert
}
@Composable
fun SesameButton(
    modifier: Modifier = Modifier,
    text : String,
    variant : SesameButtonVariants,
    isEnabled : Boolean = true,
    isLoading : Boolean = false,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = 20.dp,
        vertical = 12.dp
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
          containerColor = when (variant){
              SesameButtonVariants.PrimaryAlert -> ErrorColor
              SesameButtonVariants.PrimaryHard -> DuskBlue
              SesameButtonVariants.PrimarySoft -> NiceBlue
          }
        ),
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
                       fontFamily = SesameFontFamilies.MainMediumFontFamily,
                       fontSize = fontSize
                   )
               )
           }
       }


    }
}