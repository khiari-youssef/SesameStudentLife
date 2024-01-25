import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import tn.sesame.designsystem.components.DetailsScreenTemplate
import tn.sesame.designsystem.components.loading.shimmerEffect
import tn.sesame.designsystem.components.textfields.SesameSearchField
import tn.sesame.spm.android.R
import tn.sesame.spm.android.ui.projects.SesameProjectsState
import tn.sesame.spm.android.ui.projects.SesameProjectsStateHolder


@Composable
fun MyProjectsScreen(
    modifier: Modifier = Modifier,
    uiState : SesameProjectsStateHolder,
    onSearchQueryChanged : (query : String)->Unit,
    onViewDetails : (sesameProjectID: String)->Unit,
    onBackPressed : ()->Unit
) {
    DetailsScreenTemplate(
        modifier = modifier,
        title = stringResource(id = R.string.project_title_myprojects),
        onBackPressed = onBackPressed
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                8.dp,Alignment.CenterVertically
            )
        ) {
            SesameSearchField(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(0.95f),
                query = uiState.currentSearchQuery.value,
                placeholderRes = tn.sesame.spm.android.R.string.projects_search,
                onSearchQueryChanged = onSearchQueryChanged
            )
            when(val state = uiState.projectsState.value) {
                is SesameProjectsState.Loading -> {
                    LazyColumn(
                        modifier = Modifier
                            .systemBarsPadding()
                            .padding(8.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.Top),
                        content = {
                            items(
                                10
                            ) {
                                Box(
                                    modifier = Modifier
                                        .shimmerEffect(true, shape = MaterialTheme.shapes.medium)
                                        .fillMaxWidth()
                                        .height(170.dp)
                                )
                            }
                        })
                }
                is SesameProjectsState.Error -> {

                }
                is SesameProjectsState.Success -> {
                    ProjectsList(
                        modifier = Modifier
                            .systemBarsPadding()
                            .padding(8.dp)
                            .fillMaxSize(),
                        projectList = ProjectList(state.projects),
                        onViewDetails = onViewDetails
                    )
                }
            }

        }
    }

}

