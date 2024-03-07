import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import tn.sesame.designsystem.components.DetailsScreenTemplate
import tn.sesame.projects_management.R

@Composable
fun ProjectDocumentsDepositScreen(
    modifier: Modifier = Modifier,
    onBackPressed : ()->Unit
) {
    DetailsScreenTemplate(
        modifier = modifier,
        title = stringResource(id = R.string.project_details_title_graduation_internship),
        onBackPressed = onBackPressed
    ){

    }
}