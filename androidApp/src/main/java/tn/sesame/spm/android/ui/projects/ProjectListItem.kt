import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.spm.android.ui.projects.ProjectCollaboratorsPreviewListItem
import tn.sesame.spm.android.ui.projects.ProjectCollaboratorsPreviewListItem
import tn.sesame.spm.android.ui.projects.ProjectCreationDate
import tn.sesame.spm.android.ui.projects.ProjectDurationListItem
import tn.sesame.spm.android.ui.projects.ProjectItemListFooter
import tn.sesame.spm.android.ui.projects.ProjectJoinRequestStatus
import tn.sesame.spm.android.ui.projects.ProjectKeywords
import tn.sesame.spm.android.ui.projects.ProjectSupervisorListItem


@Preview(widthDp = 360)
@Composable
fun ProjectListItem() {
    Card(
      modifier = Modifier
          .fillMaxWidth()
          .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.medium
    ) {
      Column(
          modifier = Modifier
              .padding(8.dp)
              .fillMaxWidth()
              .wrapContentHeight(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(
              8.dp,Alignment.CenterVertically
          )
      ) {
          ConstraintLayout(
              modifier = Modifier
                  .fillMaxWidth()
                  .wrapContentHeight()
          ) {
              val (keywords,datetime) = createRefs()
              val verticalChain = createGuidelineFromAbsoluteLeft(0.45f)
              ProjectKeywords(
                  modifier = Modifier.constrainAs(keywords){
                     start.linkTo(parent.start)
                     top.linkTo(parent.top)
                     bottom.linkTo(parent.bottom)
                     end.linkTo(verticalChain,16.dp)
                     width = Dimension.fillToConstraints
                  },
                  keywords = listOf(
                      "Big data","AI","database","anything"
                 ),
              )
              ProjectCreationDate(
                  modifier = Modifier.constrainAs(datetime){
                      start.linkTo(verticalChain)
                      end.linkTo(parent.end)
                      top.linkTo(parent.top)
                      bottom.linkTo(parent.bottom)
                  },
                  date = "15/01/2023",
                  time = "15/01/2023"
              )
          }
          Column(
              modifier = Modifier
                  .fillMaxWidth()
                  .wrapContentHeight(),
              horizontalAlignment = Alignment.Start
          ) {
              Text(
                  text = "Personal project",
                  style = TextStyle(
                      fontSize = 14.sp,
                      fontFamily = SesameFontFamilies.MainMediumFontFamily,
                      fontWeight = FontWeight(500),
                      color = MaterialTheme.colorScheme.secondary
                  ),
                  maxLines = 1
              )
              Text(
                  text = "In the Give Life: Predict Blood Donations project, you will predict whether or not a donor will give blood in a given time window. The dataset used in the project is from a mobile blood donation vehicle in Taiwan, and as part of a blood donation drive, the blood transfusion service center drives to various universities to collect the blood.",
                  style = TextStyle(
                      fontSize = 13.sp,
                      fontFamily = SesameFontFamilies.MainRegularFontFamily,
                      fontWeight = FontWeight(400),
                      color = MaterialTheme.colorScheme.onBackground,
                  ),
                  maxLines = 3,
                  overflow = TextOverflow.Ellipsis
              )
          }

          Row(
              modifier = Modifier
                  .fillMaxWidth()
                  .wrapContentHeight(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Start
          ) {
              ProjectSupervisorListItem(
                  modifier = Modifier
                      .weight(0.5f)
                      .fillMaxWidth()
                      .wrapContentHeight(),
                  profileName = "Avatar",
                  profileURI = ""
              )
              ProjectCollaboratorsPreviewListItem(
                  modifier = Modifier
                      .weight(0.5f)
                      .fillMaxWidth()
                      .wrapContentHeight(),
                  maxCollaborators = 5,
                  listOf()
              )
          }
          ProjectDurationListItem(
              startDate = "15/04/2023",
              endDate = "03/07/2023"
          )
          ProjectItemListFooter(
              status = ProjectJoinRequestStatus.WAITING_FOR_SUPERVISOR_APPROVAL
          )

      }
    }
}