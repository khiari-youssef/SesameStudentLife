package tn.sesame.designsystem.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import tn.sesame.designsystem.R


@Composable
fun NavigationBarScreenTemplate(
    modifier : Modifier = Modifier,
    onExitNavigation : ()->Unit,
    content :@Composable (modifier : Modifier)->Unit
) {
   content(modifier)
    BackHandler(onBack = onExitNavigation)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsScreenTemplate(
    modifier : Modifier = Modifier,
    title : String,
    isTitleBarVisible : Boolean = true,
    onBackPressed : ()->Unit,
    content :@Composable ()->Unit
) {
        BackHandler(onBack = onBackPressed )
    Column(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .imePadding()
            .systemBarsPadding()
    ){
       ConstraintLayout(
           modifier = Modifier
               .fillMaxWidth()
       ) {
           val (icon,text) = createRefs()
           val iconModifier =
               Modifier
                   .clickable(onClick = onBackPressed)
           Icon(
               modifier = iconModifier
                   .constrainAs(icon) {
                       start.linkTo(parent.start, 12.dp)
                       top.linkTo(parent.top,12.dp)
                       bottom.linkTo(parent.bottom,12.dp)
                   }
                   .size(24.dp),
               imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
               contentDescription = "",
               tint = MaterialTheme.colorScheme.primary
           )
           Text(
               modifier = Modifier
                   .basicMarquee()
                   .constrainAs(text){
                   start.linkTo(parent.start,48.dp)
                   top.linkTo(parent.top,12.dp)
                   bottom.linkTo(parent.bottom,12.dp)
                   end.linkTo(parent.end,48.dp)
               },
               text = title,
               style = TextStyle(
                       fontSize = 18.sp,
                       fontFamily = FontFamily(Font(R.font.roboto_medium)),
                       fontWeight = FontWeight(500),
                       color = MaterialTheme.colorScheme.secondary,
                       textAlign = TextAlign.Center,
                   )

           )
       }
       content()
    }
}





