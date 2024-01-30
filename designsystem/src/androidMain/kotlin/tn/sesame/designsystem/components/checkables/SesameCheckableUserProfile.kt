import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import tn.sesame.designsystem.R
import tn.sesame.designsystem.SesameFontFamilies

@Composable
fun SesameCheckableUserProfile(
    modifier: Modifier = Modifier,
    fullName : String,
    avatarURI : String,
    placeholderResID : Int,
    isSelected : Boolean,
    isEnabled : Boolean = true,
    onItemSelectedStateChanged : (isSelected : Boolean)->Unit
) {
 Card(
     modifier = modifier,
     colors = CardDefaults
         .cardColors(
             containerColor = Color(0xFFD9D9D9)
         ),
     shape = MaterialTheme.shapes.medium
 ) {
  ConstraintLayout(
      modifier = Modifier
          .padding(8.dp)
          .fillMaxWidth()
          .wrapContentHeight()
  ) {
     val (profileImgRef,nameRef,checkboxRef) = createRefs()
    Box(
        modifier = Modifier.constrainAs(profileImgRef){
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
    ) {
        SesameCircleImageL(
            uri = avatarURI,
            placeholderRes = placeholderResID ,
            errorRes = placeholderResID
        )
    }
      Text(
          modifier = Modifier.constrainAs(nameRef){
              start.linkTo(profileImgRef.end,16.dp)
              top.linkTo(parent.top)
              bottom.linkTo(parent.bottom)
              end.linkTo(checkboxRef.end,16.dp)
              width = Dimension.fillToConstraints
          },
          text = fullName,
          style = TextStyle(
              fontSize = 18.sp,
              fontFamily = SesameFontFamilies.MainMediumFontFamily,
              fontWeight = FontWeight(500),
              color = MaterialTheme.colorScheme.onBackground,
              textAlign = TextAlign.Start
          )
      )
      Checkbox(
          modifier = Modifier.constrainAs(checkboxRef){
              top.linkTo(parent.top)
              bottom.linkTo(parent.bottom)
              end.linkTo(parent.end,8.dp)
          },
          checked = isSelected,
          onCheckedChange = onItemSelectedStateChanged,
          colors = CheckboxDefaults.colors(
              checkedColor = MaterialTheme.colorScheme.secondary
          )
      )
  }
 }
}