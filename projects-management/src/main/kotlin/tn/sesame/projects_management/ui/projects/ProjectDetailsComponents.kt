package tn.sesame.projects_management.ui.projects

import SesameButton
import SesameButtonVariants
import SesameCircleImageL
import SesameCircleImageS
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import tn.sesame.designsystem.ErrorColor
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.SuccessColor
import tn.sesame.designsystem.components.text.PlaceholderText
import tn.sesame.designsystem.onBackgroundShadedDarkMode
import tn.sesame.designsystem.onBackgroundShadedLightMode
import tn.sesame.projects_management.R
import tn.sesame.spm.domain.entities.SesameProjectMember
import tn.sesame.spm.domain.entities.SesameStudent
import tn.sesame.spm.domain.entities.SesameTeacher
import tn.sesame.spm.domain.entities.SesameUserSex


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectKeywords(
 modifier: Modifier = Modifier,
 keywords : List<String> ,
 fontSize : TextUnit = 10.sp,
 textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier =modifier.basicMarquee(),
        text = keywords.joinToString(" ") {
            "#$it"
        },
        style = TextStyle(
            fontSize = fontSize,
            fontFamily = SesameFontFamilies.MainRegularFontFamily,
            fontWeight = FontWeight(400),
            color = if (isSystemInDarkTheme()) Color(0xFFCACACA) else Color(0xFF696969),
            textAlign =textAlign
        )
    )
}


@Composable
fun ProjectCreationDate(
 modifier: Modifier = Modifier,
 date : String,
 time : String,
 fontSize : TextUnit = 10.sp,
 textAlign : TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.project_creation_date,date,time),
        style = TextStyle(
            fontSize = fontSize,
            fontFamily = SesameFontFamilies.MainRegularFontFamily,
            fontWeight = FontWeight(400),
            color = if (isSystemInDarkTheme()) Color(0xFFCACACA) else Color(0xFF696969),
            textAlign = textAlign
        )
    )
}


@Composable
fun ProjectHumanResourceDetailItem(
    modifier: Modifier = Modifier,
    sesameSupervisor : SesameProjectMember
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            8.dp,Alignment.Start
        )
    ) {
        val placeholderRes = if (sesameSupervisor.sex == SesameUserSex.Male) {
            tn.sesame.designsystem.R.drawable.ic_profile_placeholder_male
        } else tn.sesame.designsystem.R.drawable.ic_profile_placeholder_female
        SesameCircleImageL(
            uri = sesameSupervisor.photo,
            placeholderRes = placeholderRes ,
            errorRes = placeholderRes
        )
        Text(
            text = sesameSupervisor.fullName,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = SesameFontFamilies.MainMediumFontFamily,
                fontWeight = FontWeight(500),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Start
            )
        )
    }
}

@Composable
fun ProjectSupervisorDetailItem(
    modifier: Modifier = Modifier,
    sesameSupervisor: SesameTeacher?,
    onClicked : ()->Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(
            8.dp,Alignment.CenterVertically
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = "${stringResource(id = R.string.project_supervisor)} :",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = SesameFontFamilies.MainMediumFontFamily,
                fontWeight = FontWeight(500),
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Start
            )
        )
        sesameSupervisor?.getFullName()?.takeUnless { it.isBlank() }?.run {
            Row(
                modifier = Modifier
                    .clickable(onClick = onClicked)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp,Alignment.Start
                )
            ) {
                val placeholderRes = if (sesameSupervisor.sex == SesameUserSex.Male) {
                    tn.sesame.designsystem.R.drawable.ic_profile_placeholder_male
                } else tn.sesame.designsystem.R.drawable.ic_profile_placeholder_female
                SesameCircleImageL(
                    uri = sesameSupervisor.profilePicture,
                    placeholderRes = placeholderRes ,
                    errorRes = placeholderRes
                )
                Text(
                    text = this@run,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = SesameFontFamilies.MainMediumFontFamily,
                        fontWeight = FontWeight(500),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                )
            }
        } ?: run {
            PlaceholderText(
                modifier = Modifier
                    .padding(
                        vertical = 12.dp
                    )
                    .fillMaxWidth(),
                text = stringResource(id = R.string.project_supervisor_unassigned),
                align = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }

}
@Composable
fun ProjectSupervisorListItem(
    modifier: Modifier = Modifier,
    sesameSupervisor: SesameTeacher?
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(
            8.dp,Alignment.CenterVertically
        )
    ) {
        Text(
            text = "${stringResource(id = R.string.project_supervisor)} :",
            style = TextStyle(
                fontSize = 10.sp,
                fontFamily = SesameFontFamilies.MainMediumFontFamily,
                fontWeight = FontWeight(500),
                color = MaterialTheme.colorScheme.onBackground,
            )
        )
        sesameSupervisor?.getFullName()?.takeUnless { it.isBlank() }?.run {
           Row(
               modifier = Modifier
                   .wrapContentSize(),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.spacedBy(
                   8.dp,Alignment.Start
               )
           ) {
               val placeholderRes = if (sesameSupervisor.sex == SesameUserSex.Male) {
                   tn.sesame.designsystem.R.drawable.ic_profile_placeholder_male
               } else tn.sesame.designsystem.R.drawable.ic_profile_placeholder_female
             SesameCircleImageS(
                 uri = sesameSupervisor.profilePicture,
                 placeholderRes = placeholderRes ,
                 errorRes = placeholderRes
             )
               Text(
                   text = this@run,
                   style = TextStyle(
                       fontSize = 10.sp,
                       fontFamily = SesameFontFamilies.MainMediumFontFamily,
                       fontWeight = FontWeight(500),
                       color = MaterialTheme.colorScheme.onBackground,
                   )
               )
           }
        } ?: run {
            Text(
                text = stringResource(id = R.string.project_supervisor_unassigned),
                style = TextStyle(
                    fontSize = 10.sp,
                    fontFamily = SesameFontFamilies.MainMediumFontFamily,
                    fontWeight = FontWeight(500),
                    color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                )
            )
        }
    }
}

@Composable
fun ProjectCollaboratorsDetailItem(
    modifier: Modifier = Modifier,
    maxCollaborators : Int,
    collaborators : List<SesameStudent>?,
    onCollaboratorClicked : (collaborator : SesameStudent)->Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(
            8.dp,Alignment.CenterVertically
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = buildAnnotatedString {
                this.append("${stringResource(id = R.string.project_collaborators)} : ")
                withStyle(SpanStyle(color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode )){
                    append("(${collaborators?.size?.coerceIn(0,maxCollaborators)}/$maxCollaborators)")
                }
            },
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = SesameFontFamilies.MainMediumFontFamily,
                fontWeight = FontWeight(500),
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Start
            )
        )
        collaborators?.takeUnless { it.isEmpty() }?.run {
            forEach { collaborator->
                Row(
                    modifier = Modifier
                        .clickable {
                            onCollaboratorClicked(collaborator)
                        }
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,Alignment.Start
                    )
                ) {
                    val placeholderRes = if (collaborator.sex == SesameUserSex.Male) {
                        tn.sesame.designsystem.R.drawable.ic_profile_placeholder_male
                    } else tn.sesame.designsystem.R.drawable.ic_profile_placeholder_female
                    SesameCircleImageL(
                        uri = collaborator.profilePicture,
                        placeholderRes = placeholderRes,
                        errorRes = placeholderRes
                    )
                    Text(
                        text = collaborator.getFullName(),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontFamily = SesameFontFamilies.MainMediumFontFamily,
                            fontWeight = FontWeight(500),
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Start
                        )
                    )
                }
            }

        } ?: run {
            PlaceholderText(
                modifier = Modifier
                    .padding(
                        vertical = 12.dp
                    )
                    .fillMaxWidth(),
                text = stringResource(id = R.string.project_no_collaborators),
                align = TextAlign.Center,
                fontSize = 14.sp
            )
        }
    }
}
@Composable
fun ProjectCollaboratorsPreviewListItem(
    modifier: Modifier = Modifier,
    maxCollaborators : Int,
    collaborators : List<SesameStudent>?
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(
            8.dp,Alignment.CenterVertically
        )
    ) {
            Text(
                text = buildAnnotatedString {
                    this.append("${stringResource(id = R.string.project_collaborators)} : ")
                              withStyle(SpanStyle(color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode )){
                                  append("(${collaborators?.size?.coerceIn(0,maxCollaborators)}/$maxCollaborators)")
                              }
                },
                style = TextStyle(
                    fontSize = 10.sp,
                    fontFamily = SesameFontFamilies.MainMediumFontFamily,
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.onBackground,
                )
            )
        collaborators?.takeUnless { it.isEmpty() }?.run {
            Row(
                modifier = Modifier
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp,Alignment.Start
                )
            ) {
                forEach { collaborator->
                    val placeholderRes = if (collaborator.sex == SesameUserSex.Male) {
                        tn.sesame.designsystem.R.drawable.ic_profile_placeholder_male
                    } else tn.sesame.designsystem.R.drawable.ic_profile_placeholder_female
                    SesameCircleImageS(
                        uri = collaborator.profilePicture,
                        placeholderRes = placeholderRes,
                        errorRes = placeholderRes
                    )
                }
            }
        } ?: run {
            Text(
                text = stringResource(id = R.string.project_no_collaborators),
                style = TextStyle(
                    fontSize = 10.sp,
                    fontFamily = SesameFontFamilies.MainMediumFontFamily,
                    fontWeight = FontWeight(500),
                    color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                )
            )
        }
    }
}


@Composable
fun ProjectDurationListItem(
    startDate : String,
    endDate : String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
    ) {
       Icon(
           imageVector = ImageVector.vectorResource(tn.sesame.designsystem.R.drawable.ic_calendar_outlined),
           contentDescription =  "",
           tint = MaterialTheme.colorScheme.secondary
       )
        Text(
            text = stringResource(id = R.string.project_duration,startDate,endDate),
            style = TextStyle(
                fontSize = 10.sp,
                fontFamily = SesameFontFamilies.MainMediumFontFamily,
                fontWeight = FontWeight(500),
                color = MaterialTheme.colorScheme.onBackground,
            )
        )
    }
}

@Composable
fun ProjectItemListFooter(
    status: ProjectJoinRequestStatus,
    iamMember : Boolean,
    onViewDetails : ()->Unit,
    onJoinRequest : ()->Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        val (statusRef,detailRef,actionRef) = createRefs()
        val verticalGuidline  = createGuidelineFromAbsoluteLeft(0.6f)
        if (iamMember){
            ProjectStatus(
                modifier = Modifier
                    .constrainAs(statusRef){
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(verticalGuidline)
                        width = Dimension.fillToConstraints
                    },
                status = status
            )
        }
        SesameButton(
            modifier = Modifier.constrainAs(detailRef){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            },
            text = stringResource(id = R.string.project_button_details),
            fontSize = 12.sp,
            paddingValues = PaddingValues(
                6.dp,6.dp
            ),
            heightRangeDP = 16..32 ,
            variant = SesameButtonVariants.PrimarySoft,
            isEnabled = true,
            isLoading =  false,
            onClick = onViewDetails
        )
        SesameButton(
            modifier = Modifier.constrainAs(actionRef){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(detailRef.start,12.dp)
            },
            text = stringResource(id = R.string.project_button_join),
            fontSize = 12.sp,
            paddingValues = PaddingValues(
                6.dp,6.dp
            ),
            heightRangeDP = 16..32 ,
            variant = SesameButtonVariants.PrimaryHard,
            isEnabled = true,
            isLoading =  false,
            onClick = onJoinRequest
        )
    }
}

enum class ProjectJoinRequestStatus{
    WAITING_FOR_APPROVAL,
    REJECTED,
    JOINED,
    IDLE
}
@Composable
fun ProjectStatus(
modifier: Modifier  = Modifier,
status: ProjectJoinRequestStatus = ProjectJoinRequestStatus.IDLE
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.Start)
    ) {
        when(status){
            ProjectJoinRequestStatus.IDLE ->{

            }
            ProjectJoinRequestStatus.JOINED ->{
                Icon(
                    imageVector = ImageVector.vectorResource(tn.sesame.designsystem.R.drawable.success) ,
                    contentDescription = "",
                    tint = SuccessColor
                )
                Text(
                    text = stringResource(id = R.string.project_status_joined),
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = SesameFontFamilies.MainMediumFontFamily),
                    fontWeight = FontWeight(500),
                    color = SuccessColor,
                    textAlign = TextAlign.Start,
                )
            }
            ProjectJoinRequestStatus.WAITING_FOR_APPROVAL ->{
                CircularProgressIndicator(
                    color = Color(0xFFD98D0F),
                    modifier = Modifier.size(12.dp),
                    strokeWidth = 0.5f.dp
                )
                Text(
                    text = stringResource(id = R.string.project_status_waiting_for_approval),
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = SesameFontFamilies.MainMediumFontFamily),
                    fontWeight = FontWeight(500),
                    color = Color(0xFFD98D0F),
                    textAlign = TextAlign.Start,
                )
            }
            ProjectJoinRequestStatus.REJECTED ->{
                Icon(
                    imageVector = ImageVector.vectorResource(tn.sesame.designsystem.R.drawable.ic_clear) ,
                    contentDescription = "",
                    tint = ErrorColor
                )
                Text(
                    text = stringResource(id = R.string.project_status_rejected),
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = SesameFontFamilies.MainMediumFontFamily),
                    fontWeight = FontWeight(500),
                    color = ErrorColor,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}