import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.DuskBlue
import tn.sesame.designsystem.ErrorColor
import tn.sesame.designsystem.NiceBlue
import tn.sesame.designsystem.components.SesameFontFamilies


enum class SesameButtonVariants{
    PrimarySoft,PrimaryHard,PrimaryAlert
}
@Composable
fun SesameButton(
    modifier: Modifier,
    text : String,
    variant : SesameButtonVariants,
    isEnabled : Boolean,
    isLoading : Boolean,
    onClick: ()->Unit
) {
   Button(
        modifier = modifier,
        enabled = isEnabled,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
          containerColor = when (variant){
              SesameButtonVariants.PrimaryAlert -> ErrorColor
              SesameButtonVariants.PrimaryHard -> DuskBlue
              SesameButtonVariants.PrimarySoft -> NiceBlue
          }
        ),
       contentPadding = PaddingValues(
           horizontal = 24.dp,
           vertical = 12.dp
       ),
        onClick = onClick
    ) {
       Crossfade(
           targetState = isLoading,
           label = "ButtonLoading",
           animationSpec = spring()
       ) {
           if (it){
               CircularProgressIndicator(
                   modifier = Modifier.size(16.dp),
                   strokeWidth = 1.dp,
                   color = Color.White
               )
           } else {
               Text(
                   text = text,
                   style = TextStyle(
                       color = Color.White,
                       fontFamily = SesameFontFamilies.MainMediumFontFamily,
                       fontSize = 16.sp
                   )
               )
           }
       }


    }
}