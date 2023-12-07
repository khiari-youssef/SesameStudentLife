package tn.sesame.designsystem.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import tn.sesame.designsystem.R


@Composable
fun NavigationBarScreenTemplate(
    modifier : Modifier = Modifier,
    onExitNavigation : (route : String)->Unit,
    content :@Composable (modifier : Modifier)->Unit
) {
   content(modifier)
}

@Composable
fun DetailsScreenTemplate(
    title : String,
    modifier : Modifier = Modifier,
    onBackPressed : ()->Unit,
    content :@Composable ()->Unit
) {
    BackHandler(onBack = onBackPressed)
    Column(
        modifier = modifier
            .imePadding()
            .systemBarsPadding()
    ){
       ConstraintLayout(
           modifier = Modifier
               .fillMaxWidth()
       ) {
           val (icon,text) = createRefs()
           Icon(
               modifier = Modifier
                   .clickable(onClick = onBackPressed)
                   .constrainAs(icon) {
                       start.linkTo(parent.start, 12.dp)
                       top.linkTo(parent.top, 8.dp)
                       bottom.linkTo(parent.bottom, 8.dp)
                   }
                   .size(24.dp),
               imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
               contentDescription = "",
               tint = MaterialTheme.colorScheme.primary
           )
           Text(
               modifier = Modifier.constrainAs(text){
                   start.linkTo(parent.start,48.dp)
                   top.linkTo(parent.top,8.dp)
                   bottom.linkTo(parent.bottom,8.dp)
                   end.linkTo(parent.end,48.dp)
               },
               text = title
           )
       }
       content()
    }
}

@Composable
fun PopUpScreenTemplate() {

}

@Composable
fun FormScreenTemplate() {

}



