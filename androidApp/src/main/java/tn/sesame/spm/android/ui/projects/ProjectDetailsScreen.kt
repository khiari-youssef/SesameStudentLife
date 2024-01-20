import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import tn.sesame.designsystem.components.DetailsScreenTemplate
import tn.sesame.spm.android.R
import tn.sesame.spm.domain.entities.ProjectType
import tn.sesame.spm.domain.entities.SesameProject
import tn.sesame.spm.domain.entities.SesameProjectCollaborator
import tn.sesame.spm.domain.entities.SesameProjectJoinRequestState
import tn.sesame.spm.domain.entities.SesameProjectSupervisor
import tn.sesame.spm.domain.entities.SesameUserSex


@Preview
@Composable
fun ProjectDetailsScreen(
    modifier: Modifier = Modifier,
    project : SesameProject =
        SesameProject(
        "idp",
        ProjectType.PFE,
        "",
            SesameProjectSupervisor(
                "",
                "",
                "Supervisor",
                "",
                SesameUserSex.Female
            ),
List(5){
    SesameProjectCollaborator(
        id = "id$it",
        email = "email$it@mail.com",
        fullName = "name$it",
        photo ="",
        sex = SesameUserSex.Male,
        joinStatus = SesameProjectJoinRequestState.ACCEPTED
    )
},
        5,
        LocalDateTime(dayOfMonth = 1, month = Month.MARCH, year = 2024, hour = 23, minute = 23)..LocalDateTime(dayOfMonth = 1, month = Month.SEPTEMBER, year = 2024, hour = 23, minute = 23),
        LocalDateTime(dayOfMonth = 23, month = Month.FEBRUARY, year = 2024, hour = 8, minute = 23),
        LocalDateTime(dayOfMonth = 23, month = Month.DECEMBER, year = 2024, hour = 8, minute = 23),
        listOf(),
        listOf()
    )
) {
    DetailsScreenTemplate(
        modifier = modifier,
        title = stringResource(id = R.string.project_details_title_graduation_internship),
        onBackPressed = {

        }
    ){
        
    }
}