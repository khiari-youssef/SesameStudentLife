package tn.sesame.users_management.ui.profile

import SesameCircleImageXL
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.components.buttons.SesameIconButtonVariant
import tn.sesame.designsystem.onBackgroundShadedDarkMode
import tn.sesame.designsystem.onBackgroundShadedLightMode
import tn.sesame.spm.domain.entities.SesameStudent
import tn.sesame.spm.domain.entities.SesameTeacher
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.entities.SesameUserSex
import tn.sesame.users_management.R

@Composable
fun UserProfileDetails(
    modifier: Modifier = Modifier,
    onViewMyBadgeClicked : ()->Unit,
    onViewMyProfileClicked: ()->Unit,
    sesameUser : SesameUser
) {
    Column(
        modifier = modifier
    ){
        Row(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(
                24.dp,
                Alignment.CenterHorizontally
            )
        ) {
            val placeholder = if (sesameUser.sex == SesameUserSex.Male) tn.sesame.designsystem.R.drawable.ic_profile_placeholder_male else tn.sesame.designsystem.R.drawable.ic_profile_placeholder_female
            SesameCircleImageXL(
                uri = sesameUser.profilePicture,
                placeholderRes = placeholder ,
                errorRes = placeholder
            )
            Column(
                modifier = Modifier
                    .wrapContentSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement
                    .spacedBy(8.dp,Alignment.CenterVertically)
            ) {
                    Text(
                        text = sesameUser.getFullName(),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = SesameFontFamilies.MainBoldFontFamily,
                            fontWeight = FontWeight(700),
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    )
                Text(
                    text = when (sesameUser){
                      is SesameStudent-> stringResource(id = R.string.profile_student)
                      is SesameTeacher -> stringResource(id = R.string.profile_teacher)
                      else -> stringResource(id = R.string.profile_user)
                     },
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = SesameFontFamilies.MainMediumFontFamily,
                        fontWeight = FontWeight(500),
                        color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                    )
                )
                if (sesameUser is SesameStudent){
                    Text(
                        text = sesameUser.sesameClass.getDisplayName(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = SesameFontFamilies.MainMediumFontFamily,
                            fontWeight = FontWeight(500),
                            color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                        )
                    )
                } else if (sesameUser is SesameTeacher){
                    Text(
                        text = sesameUser.profBackground,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = SesameFontFamilies.MainMediumFontFamily,
                            fontWeight = FontWeight(500),
                            color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                        )
                    )
                }
                Text(
                    text = sesameUser.email,
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
                .padding(
                    vertical = 8.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                16.dp,Alignment.CenterHorizontally
            )
        ) {
            SesameIconButtonVariant(
                modifier = Modifier.wrapContentSize(),
                onClick = onViewMyBadgeClicked,
                iconResID = tn.sesame.designsystem.R.drawable.ic_badge,
                text = stringResource(id = R.string.view_badge)
            )
            SesameIconButtonVariant(
                modifier = Modifier.wrapContentSize(),
                onClick = onViewMyProfileClicked,
                iconResID = tn.sesame.designsystem.R.drawable.ic_password_revealed,
                text = stringResource(id = R.string.view_profile)
            )
        }
    }
}