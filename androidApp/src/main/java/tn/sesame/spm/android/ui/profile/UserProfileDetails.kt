package tn.sesame.spm.android.ui.profile

import SesameCircleImageXL
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.R
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.onBackgroundShadedDarkMode
import tn.sesame.designsystem.onBackgroundShadedLightMode
import tn.sesame.spm.domain.entities.SesameStudent
import tn.sesame.spm.domain.entities.SesameTeacher
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.entities.SesameUserSex

@Composable
fun UserProfileDetails(
    modifier: Modifier = Modifier,
    sesameUser : SesameUser,
    showSex : Boolean = true,
    onProfileEmailClicked : ((email : String)->Unit)?=null
) {
    Column(modifier = modifier){
        Row(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                24.dp,
                Alignment.CenterHorizontally
            )
        ) {
            val placeholder = if (sesameUser.sex == SesameUserSex.Male) R.drawable.ic_profile_placeholder_male else R.drawable.ic_profile_placeholder_female
            SesameCircleImageXL(
                uri = sesameUser.profilePicture,
                placeholderRes = placeholder ,
                errorRes = placeholder
            )
            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,Alignment.Start
                    )
                ) {
                    if (showSex) {
                        Icon(
                            imageVector = ImageVector.vectorResource(when (sesameUser.sex) {
                                SesameUserSex.Female -> R.drawable.ic_sex_female
                                SesameUserSex.Male -> R.drawable.ic_sex_male
                            }) ,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                    Text(
                        text = sesameUser.getFullName(),
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = SesameFontFamilies.MainBoldFontFamily,
                            fontWeight = FontWeight(700),
                            color = MaterialTheme.colorScheme.onBackground,
                        )
                    )
                }
                Text(
                    text = when (sesameUser){
                      is SesameStudent-> stringResource(id = tn.sesame.spm.android.R.string.profile_student)
                      is SesameTeacher -> stringResource(id = tn.sesame.spm.android.R.string.profile_teacher)
                      else -> stringResource(id = tn.sesame.spm.android.R.string.profile_user)
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
                    sesameUser.job?.takeIf { it.isNotBlank() }?.run {
                        Text(
                            text = this,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = SesameFontFamilies.MainMediumFontFamily,
                                fontWeight = FontWeight(500),
                                color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                            )
                        )
                    }
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

            }
        }
        Row(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            val annotatedString = buildAnnotatedString {
                append("${stringResource(id = tn.sesame.spm.android.R.string.profile_email)} :  ")
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ){
                    pushStringAnnotation(tag = "emailTag", annotation = sesameUser.email)
                    append(sesameUser.email)
                }
            }
            onProfileEmailClicked?.run {
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
                            onProfileEmailClicked(this.item)
                        }
                    }
                )
            } ?: run {
                Text(
                    text =  annotatedString,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = SesameFontFamilies.MainMediumFontFamily,
                        fontWeight = FontWeight(500),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                )
            }

        }
    }
}