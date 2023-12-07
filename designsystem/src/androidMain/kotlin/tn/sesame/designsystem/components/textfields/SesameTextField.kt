import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.PlaceHolderColor
import tn.sesame.designsystem.components.SesameFontFamilies

@Composable
fun SesameTextField(
    text : String,
    label : String,
    placeholder : String,
    isEnabled : Boolean,
    isReadOnly : Boolean,
    isError : Boolean,
    onTextChanged : (text : String)->Unit
) {
TextField(
value = text,
label = {
  Text(
      text = label,
      style = TextStyle(
          color = PlaceHolderColor,
          fontSize = 12.sp,
          fontFamily = SesameFontFamilies.MainMediumFontFamily,
          textAlign = TextAlign.Start
      )
  )
},
placeholder ={
    PlaceholderText(
        text = placeholder,
        fontSize = 14.sp
    )
},
enabled = isEnabled,
readOnly = isReadOnly,
 isError = isError,
singleLine = true,
onValueChange = onTextChanged
)

}