import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.components.buttons.SesameIconButtonVariant
import tn.sesame.designsystem.components.menus.MenuOption
import tn.sesame.designsystem.components.menus.MenuOptions
import tn.sesame.designsystem.components.menus.OptionsListMenu
import tn.sesame.spm.domain.entities.SesameStudent
import tn.sesame.spm.domain.entities.SesameTeacher
import tn.sesame.users_management.ui.profile.UserProfileDetails
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.users_management.R


@Composable
fun ProfileScreen(
modifier: Modifier = Modifier,
menuOptions : MenuOptions,
sesameUser: SesameUser,
onMenuItemClicked : (optionIndex : Int)->Unit,
onLogOutClicked :  ()->Unit
) {
    val isLargeScreen  = LocalConfiguration.current.run {
        (orientation == Configuration.ORIENTATION_LANDSCAPE) or (this.screenWidthDp >= 600)
    }
    if (isLargeScreen){
       Row(
           modifier = modifier
               .fillMaxSize()
               .padding(16.dp),
           verticalAlignment = Alignment.Top,
           horizontalArrangement = Arrangement.Center
       ){
           UserProfileDetails(
               sesameUser = sesameUser,
               modifier = Modifier
                   .weight(0.5f),
               onViewMyBadgeClicked = {

               },
               onViewMyProfileClicked = {

               }
           )
           ProfileMenu(
               modifier = Modifier
                   .weight(0.5f)
                   .fillMaxSize(),
               menuOptions = menuOptions,
               onMenuItemClicked = onMenuItemClicked,
               onLogOutClicked = onLogOutClicked
           )

       }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                12.dp,
                Alignment.Top
            )
        ) {
            UserProfileDetails(
                sesameUser = sesameUser,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onViewMyBadgeClicked = {

                },
                onViewMyProfileClicked = {

                }
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
            )
            ProfileMenu(
              modifier = Modifier
                    .fillMaxSize(),
                menuOptions = menuOptions,
                onMenuItemClicked = onMenuItemClicked,
                onLogOutClicked = onLogOutClicked
            )
        }
    }

}




@Composable
fun ProfileMenu(
    modifier: Modifier = Modifier,
    menuOptions : MenuOptions,
    onMenuItemClicked: (optionIndex : Int) -> Unit,
    onLogOutClicked: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (buttonRef,menuRef) = createRefs()
        OptionsListMenu(
            modifier = Modifier.constrainAs(menuRef){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            },
            menuOptions = menuOptions ,
            onOptionClicked = onMenuItemClicked
        )
        SesameIconButtonVariant(
            modifier = Modifier.constrainAs(buttonRef){
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            },
            onClick = onLogOutClicked,
            iconResID = tn.sesame.designsystem.R.drawable.logout,
            text = stringResource(id = R.string.profile_logout)
        )
    }
}