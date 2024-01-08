package tn.sesame.spm.android.ui.projects

import SesameButton
import SesameCircleImageL
import SesameCircleImageS
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import tn.sesame.designsystem.onBackgroundShadedDarkMode
import tn.sesame.designsystem.onBackgroundShadedLightMode
import tn.sesame.spm.android.R
import tn.sesame.spm.domain.entities.SesameSupervisor


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProjectKeywords(
 modifier: Modifier = Modifier,
 keywords : List<String> ,
 fontSize : TextUnit = 10.sp
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
        )
    )
}


@Composable
fun ProjectCreationDate(
 modifier: Modifier = Modifier,
 date : String,
 time : String,
 fontSize : TextUnit = 10.sp
) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.project_creation_date,date,time),
        style = TextStyle(
            fontSize = fontSize,
            fontFamily = SesameFontFamilies.MainRegularFontFamily,
            fontWeight = FontWeight(400),
            color = if (isSystemInDarkTheme()) Color(0xFFCACACA) else Color(0xFF696969),
        )
    )
}


@Composable
fun ProjectSupervisorListItem(
    modifier: Modifier = Modifier,
    sesameSupervisor: SesameSupervisor?
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
        sesameSupervisor?.fullName?.takeUnless { it.isBlank() }?.run {
           Row(
               modifier = Modifier
                   .wrapContentSize(),
               verticalAlignment = Alignment.CenterVertically,
               horizontalArrangement = Arrangement.spacedBy(
                   8.dp,Alignment.Start
               )
           ) {
             SesameCircleImageS(
                 uri = sesameSupervisor.photo,
                 placeholderRes = tn.sesame.designsystem.R.drawable.profile_placeholder ,
                 errorRes = tn.sesame.designsystem.R.drawable.profile_placeholder
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
fun ProjectCollaboratorsPreviewListItem(
    modifier: Modifier = Modifier,
    maxCollaborators : Int,
   profileURIs : List<String>?
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
                                  append("(${profileURIs?.size?.coerceIn(0,maxCollaborators)}/$maxCollaborators)")
                              }
                },
                style = TextStyle(
                    fontSize = 10.sp,
                    fontFamily = SesameFontFamilies.MainMediumFontFamily,
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.onBackground,
                )
            )
        profileURIs?.takeUnless { it.isEmpty() }?.run {
            Row(
                modifier = Modifier
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp,Alignment.Start
                )
            ) {
                forEach { collaboratorURI->
                    SesameCircleImageS(
                        uri = collaboratorURI,
                        placeholderRes = tn.sesame.designsystem.R.drawable.profile_placeholder ,
                        errorRes = tn.sesame.designsystem.R.drawable.profile_placeholder
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