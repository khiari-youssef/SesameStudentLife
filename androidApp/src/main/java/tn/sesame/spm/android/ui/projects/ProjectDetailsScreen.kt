import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.components.DetailsScreenTemplate
import tn.sesame.designsystem.components.text.DateText
import tn.sesame.designsystem.components.text.PlaceholderText
import tn.sesame.designsystem.components.text.SesameParagraphText
import tn.sesame.spm.android.R
import tn.sesame.spm.android.ui.projects.ProjectCollaboratorsDetailItem
import tn.sesame.spm.android.ui.projects.ProjectCreationDate
import tn.sesame.spm.android.ui.projects.ProjectKeywords
import tn.sesame.spm.android.ui.projects.ProjectSupervisorDetailItem
import tn.sesame.spm.domain.entities.SesameProject
import tn.sesame.spm.domain.entities.SesameProjectMember
import tn.sesame.spm.domain.entities.SesameUser


@Composable
fun ProjectDetailsScreen(
    modifier: Modifier = Modifier,
    project : SesameProject,
    onShowMember : (member : SesameUser)->Unit
) {
    DetailsScreenTemplate(
        modifier = modifier,
        title = stringResource(id = R.string.project_details_title_graduation_internship)
    ){
        val verticalScrollState = rememberScrollState()
       ConstraintLayout(
           modifier = Modifier
               .verticalScroll(verticalScrollState)
               .padding(
                   horizontal = 12.dp,
                   vertical = 12.dp
               )
               .fillMaxWidth()
               .wrapContentHeight()
       ) {
          val (descRef,datesRef,techstackRef,supervisorRef,collaboratorsRef,footerRef) = createRefs()

           val descriptionModifier = Modifier
               .constrainAs(descRef) {
                   top.linkTo(parent.top)
                   start.linkTo(parent.start)
                   end.linkTo(parent.end)
                   width = Dimension.fillToConstraints
               }
               .animateContentSize()
               .fillMaxWidth()
               .wrapContentHeight()
           SesameParagraphText(
               modifier = descriptionModifier,
               paragraph = project.description,
               placeholderRes = R.string.project_details_no_description
           )
           ProjectDatesSection(
               modifier = Modifier
                   .constrainAs(datesRef) {
                       top.linkTo(descRef.bottom, 24.dp)
                       start.linkTo(parent.start)
                       end.linkTo(parent.end)
                       width = Dimension.fillToConstraints
                   },
               startDate = project.displayStartDate,
               endDate =  project.displayEndDate,
               presentationDate = project.displayPresentationDate
           )
           TechStackMatrix(
               modifier = Modifier.constrainAs(techstackRef) {
                   top.linkTo(datesRef.bottom, 24.dp)
                   start.linkTo(parent.start)
                   end.linkTo(parent.end)
                   width = Dimension.fillToConstraints
               },
               title = stringResource(id =R.string.project_tech_stack_title ),
               techStackList = project.techStack,
               placeholderResID = R.string.project_tech_stack_placeholder
           )
           ProjectSupervisorDetailItem(
               modifier = Modifier.constrainAs(supervisorRef) {
                   top.linkTo(techstackRef.bottom, 24.dp)
                   start.linkTo(parent.start)
                   end.linkTo(parent.end)
                   width = Dimension.fillToConstraints
               },
               sesameSupervisor = project.supervisor,
               onClicked = {
                   onShowMember(project.supervisor)
               }
           )
           ProjectCollaboratorsDetailItem(
               modifier = Modifier.constrainAs(collaboratorsRef) {
                   top.linkTo(supervisorRef.bottom, 24.dp)
                   start.linkTo(parent.start)
                   end.linkTo(parent.end)
                   width = Dimension.fillToConstraints
               },
              project.maxCollaborators,
              project.joinedCollaborators,
               onCollaboratorClicked = { student->
                   onShowMember(student)
               }
           )
           Column(
               modifier = Modifier.constrainAs(footerRef){
                   top.linkTo(collaboratorsRef.bottom, 24.dp)
                   start.linkTo(parent.start)
                   end.linkTo(parent.end)
                   bottom.linkTo(parent.bottom,24.dp)
                   width = Dimension.fillToConstraints
               },
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.spacedBy(
                   8.dp,Alignment.CenterVertically
               )
           ) {
               ProjectKeywords(
                   modifier = Modifier
                       .wrapContentHeight()
                       .fillMaxWidth(),
                   keywords = project.keywords,
                   textAlign = TextAlign.Center,
                   fontSize = 16.sp
               )
               ProjectCreationDate(
                   modifier = Modifier
                       .wrapContentHeight()
                       .fillMaxWidth(),
                   date = project.displayCreationDate,
                   time = project.displayCreationTime,
                   fontSize = 16.sp,
                   textAlign = TextAlign.Center
               )
           }
       }
    }
}


@Composable
fun ProjectDatesSection(
    modifier: Modifier = Modifier,
    startDate: String,
    endDate: String,
    presentationDate: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(
            12.dp,Alignment.CenterVertically
        )
    ) {
        DateText(
            modifier = Modifier
                .wrapContentSize(),
            label = stringResource(id = R.string.project_start_date),
            value =  startDate,
            valuePlaceholderResID = R.string.project_date_unavailable
        )
        DateText(
            modifier = Modifier
                .wrapContentSize(),
            label = stringResource(id = R.string.project_end_date),
            value = endDate,
            valuePlaceholderResID = R.string.project_date_unavailable
        )
        DateText(
            modifier = Modifier
                .wrapContentSize(),
            label = stringResource(id = R.string.project_presentation_date),
            value =  presentationDate,
            valuePlaceholderResID = R.string.project_date_to_be_assigned
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TechStackMatrix(
    modifier: Modifier = Modifier,
    title : String?=null,
    placeholderResID : Int,
    techStackList : List<String>
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            16.dp,Alignment.CenterVertically
        )
    ) {
        title?.takeIf { it.isNotBlank() }?.let { safeTitle->
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = safeTitle,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = SesameFontFamilies.MainMediumFontFamily,
                    fontWeight = FontWeight(500),
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Start
                )
            )
        }

        if (techStackList.isEmpty()) {
            PlaceholderText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = placeholderResID),
                align = TextAlign.Center
            )
        } else {
            FlowRow(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                maxItemsInEachRow = 4,
                verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterVertically),
                horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterHorizontally)
            ) {
                techStackList.forEach { tech ->
                    tech.takeUnless { it.isBlank() }?.run {
                        Card(
                            modifier = Modifier
                                .wrapContentHeight(),
                            shape = RoundedCornerShape(size = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor =  Color(0xFFEFEFEF)
                            )
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp)
                                    .wrapContentHeight(),
                                text = tech,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    fontFamily = SesameFontFamilies.MainBoldFontFamily,
                                    fontWeight = FontWeight(700),
                                    color = Color(0xFF284C8E),
                                    textAlign = TextAlign.Center,
                                    letterSpacing = 0.5.sp,
                                )
                            )
                        }
                    }

                }
            }
        }

    }
}


