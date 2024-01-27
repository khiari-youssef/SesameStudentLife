package tn.sesame.designsystem.components.popups

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import tn.sesame.designsystem.R
import tn.sesame.designsystem.SesameFontFamilies

@Stable
data class SesameToastDefaults(
  val backgroundColor: Color,
  val textStyle : TextStyle = TextStyle(
      fontSize = 14.sp,
      fontFamily = SesameFontFamilies.MainMediumFontFamily,
      fontWeight = FontWeight(500),
      color = Color.Black,
      textAlign = TextAlign.Center
  ),
  val iconsTint : Color
)

@Composable
fun SesameToast(
    modifier: Modifier = Modifier,
    message : String,
    iconResID : Int,
    sesameToastDefaults : SesameToastDefaults,
    onDismissRequest : ()->Unit
) {
 Surface(
     modifier = modifier,
     color = sesameToastDefaults.backgroundColor,
     shape = MaterialTheme.shapes.medium
 ) {
     ConstraintLayout(
         modifier = Modifier
             .zIndex(4f)
             .padding(
                 8.dp
             )
             .fillMaxWidth()
             .wrapContentHeight()
     ) {
         val (iconRef,textRef,closeIconRef) = createRefs()
          Icon(
              modifier = Modifier
                  .constrainAs(iconRef) {
                      start.linkTo(parent.start)
                      top.linkTo(parent.top)
                      bottom.linkTo(parent.bottom)
                  }
                  .requiredSize(20.dp),
              imageVector = ImageVector.vectorResource(
                  iconResID
              ),
              contentDescription = null,
              tint = sesameToastDefaults.iconsTint
          )
         Text(
             modifier = Modifier.constrainAs(textRef){
                 start.linkTo(iconRef.end,8.dp)
                 end.linkTo(closeIconRef.start,8.dp)
                 width  = Dimension.fillToConstraints
                 top.linkTo(parent.top)
                 bottom.linkTo(parent.bottom)
             },
             text = message,
             style = sesameToastDefaults.textStyle
         )
         Icon(
             modifier = Modifier
                 .constrainAs(closeIconRef) {
                     end.linkTo(parent.end)
                     top.linkTo(parent.top)
                     bottom.linkTo(parent.bottom)
                 }
                 .clickable(onClick = onDismissRequest)
                 .requiredSize(20.dp),
             imageVector = ImageVector.vectorResource(
                 R.drawable.ic_clear
             ),
             contentDescription = null,
             tint = sesameToastDefaults.iconsTint
         )
     }
 }
}


@Composable
fun SesameToastPopup(
    modifier: Modifier = Modifier,
    isShown : Boolean,
    message : String,
    iconResID : Int = R.drawable.ic_alert,
    sesameToastDefaults :SesameToastDefaults,
    onDismissRequest : ()->Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = isShown,
        enter = slideInVertically(tween(durationMillis = 300, easing = EaseOut)) {
            it
        },
        exit = slideOutVertically(tween(durationMillis = 300, easing = EaseIn)){
           it*2
        },
        label = "ToastVisibilityAnimation"
    ) {
        SesameToast(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            message,
            iconResID,
            sesameToastDefaults = sesameToastDefaults,
            onDismissRequest
        )
    }
}