import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.onBackgroundShadedDarkMode
import tn.sesame.designsystem.onBackgroundShadedLightMode
import tn.sesame.spm.android.R

@Preview
@Composable
fun ProfileScreen(
modifier: Modifier = Modifier
) {
    val menuOptions = MenuOptions(listOf(
        MenuOption(
            iconRes = tn.sesame.designsystem.R.drawable.ic_project_outlined,
            label = "My projects"
        ),
        MenuOption(
            iconRes = tn.sesame.designsystem.R.drawable.ic_policy ,
            label = "Policy"
        ),
        MenuOption(
            iconRes = tn.sesame.designsystem.R.drawable.ic_settings ,
            label = "Settings"
        )
    ))
 Column(
     modifier = modifier
         .padding(16.dp),
     horizontalAlignment = Alignment.CenterHorizontally,
     verticalArrangement = Arrangement.spacedBy(
         12.dp,
         Alignment.Top
     )
 ) {
   Row(
       modifier = Modifier
           .fillMaxWidth()
           .wrapContentHeight(),
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.spacedBy(
           24.dp,
       Alignment.CenterHorizontally
       )
   ) {
       SesameCircleImageXL(
           uri = "",
           placeholderRes = tn.sesame.designsystem.R.drawable.profile_placeholder ,
           errorRes = tn.sesame.designsystem.R.drawable.profile_placeholder
       )
       Column(
           modifier = Modifier
               .fillMaxWidth()
               .wrapContentHeight(),
       ) {
           Text(
               text = "Ahmed",
               style = TextStyle(
                   fontSize = 24.sp,
                   fontFamily = SesameFontFamilies.MainBoldFontFamily,
                   fontWeight = FontWeight(700),
                   color = MaterialTheme.colorScheme.onBackground,
               )
           )
           Text(
               text = "Student",
               style = TextStyle(
                   fontSize = 16.sp,
                   fontFamily = SesameFontFamilies.MainMediumFontFamily,
                   fontWeight = FontWeight(500),
                   color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
               )
           )
           Text(
               text = "INGTA-3C",
               style = TextStyle(
                   fontSize = 16.sp,
                   fontFamily = SesameFontFamilies.MainMediumFontFamily,
                   fontWeight = FontWeight(500),
                   color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
               )
           )
           Text(
               text = "Software engineer",
               style = TextStyle(
                   fontSize = 16.sp,
                   fontFamily = SesameFontFamilies.MainMediumFontFamily,
                   fontWeight = FontWeight(500),
                   color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
               )
           )
       }
   }
     Row(
         modifier = Modifier
             .padding(8.dp)
             .fillMaxWidth()
             .wrapContentHeight(),
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.Start
     ) {
         val annotatedString = buildAnnotatedString {
             append("${stringResource(id = R.string.profile_email)} :  ")
             withStyle(
                 SpanStyle(
                     color = MaterialTheme.colorScheme.primary,
                     textDecoration = TextDecoration.Underline
                 )
             ){
                 pushStringAnnotation(tag = "emailTag", annotation = "ahmed@sesame.com.tn")
                 append("ahmed@sesame.com.tn")
             }
         }
         ClickableText(
             text =  annotatedString,
             style = TextStyle(
                 fontSize = 16.sp,
                 fontFamily = SesameFontFamilies.MainMediumFontFamily,
                 fontWeight = FontWeight(500),
                 color = MaterialTheme.colorScheme.onBackground,
             ),
             onClick = {
                 annotatedString.getStringAnnotations(it,it).find {
                     it.tag == "emailTag"
                 }?.run {

                 }
             }
         )
     }
     Divider(
         modifier = Modifier
             .fillMaxWidth(0.95f)
     )
     ConstraintLayout(
         modifier = Modifier
             .fillMaxSize()
     ) {
         val (buttonRef,menuRef) = createRefs()
         OptionsListMenu(
             modifier = Modifier.constrainAs(menuRef){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
             },
             menuOptions = menuOptions ,
             onOptionClicked = {option->

             }
         )
         Button(
             modifier = Modifier.constrainAs(buttonRef){
                 end.linkTo(parent.end)
                 bottom.linkTo(parent.bottom)
             },
             contentPadding = PaddingValues(12.dp),
             shape = MaterialTheme.shapes.medium,
             colors = ButtonDefaults.buttonColors(
                 containerColor = Color(0xFFF3F3F3)
             ),
             onClick = { }
         ) {
             Icon(
                 imageVector = ImageVector.vectorResource(tn.sesame.designsystem.R.drawable.logout) ,
                 contentDescription = "",
                 tint = Color.Unspecified
             )
             Spacer(modifier = Modifier.width(8.dp))
             Text(
                 text = stringResource(id = R.string.profile_logout),
                 style = TextStyle(
                     fontSize = 16.sp,
                     fontFamily = SesameFontFamilies.MainMediumFontFamily,
                     fontWeight = FontWeight(500),
                     color = Color.Black,
                 )
             )
         }
     }

     
 }
}