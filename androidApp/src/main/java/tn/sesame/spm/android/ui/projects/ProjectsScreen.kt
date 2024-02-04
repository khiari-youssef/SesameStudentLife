import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tn.sesame.designsystem.components.loading.shimmerEffect
import tn.sesame.designsystem.components.textfields.SesameSearchField
import tn.sesame.spm.android.ui.projects.SesameProjectsState
import tn.sesame.spm.android.ui.projects.SesameProjectsStateHolder
import tn.sesame.spm.domain.entities.SesameProject


@Stable
@JvmInline
value class ProjectList(
    val projects : List<SesameProject>
)

@Composable
fun ProjectsScreen(
    modifier: Modifier = Modifier,
    uiState : SesameProjectsStateHolder,
    onSearchQueryChanged : (query : String)->Unit,
    onViewDetails : (sesameProjectID: String)->Unit,
    onJoinRequest: (projectID : String)->Unit
) {
Column(
    modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(
        8.dp,Alignment.CenterVertically
    )
) {
    SesameSearchField(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        query = uiState.currentSearchQuery.value,
        placeholderRes = tn.sesame.spm.android.R.string.projects_search,
        onSearchQueryChanged = onSearchQueryChanged
    )
    when(val state = uiState.projectsState.value) {
        is SesameProjectsState.Loading -> {
            LazyColumn(
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(
                        vertical = 8.dp
                    )
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
                    .padding(
                        vertical = 8.dp
                    )
                    .fillMaxSize(),
                projectList = ProjectList(state.projects),
                onViewDetails = onViewDetails,
                onJoinRequest = onJoinRequest
            )
        }
    }

}

}


@Composable
fun ColumnScope.ProjectsList(
    modifier: Modifier = Modifier,
    projectList : ProjectList,
    onViewDetails : (sesameProjectID: String)->Unit,
    onJoinRequest: ((projectID : String)->Unit)?=null
) {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp,Alignment.Top),
        content = {
            if (projectList.projects.isEmpty()){
                item(key = "empty"){
                    SearchResultNotFound(
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            } else {
                items(
                    items = projectList.projects,
                    key = {project->
                        project.id
                    }
                ){ project->
                  ProjectListItem(
                      sesameProject = project,
                      myID = "youssef-id",
                      onJoinRequest = {
                        onJoinRequest?.let {cb->
                            cb(project.id)
                      }
                      },
                      onViewDetails = {
                         onViewDetails(project.id)
                      }
                  )
                }
            }
        }
    )
}