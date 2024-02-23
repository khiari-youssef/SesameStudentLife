import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.components.DetailsScreenTemplate
import tn.sesame.designsystem.components.loading.shimmerEffect
import tn.sesame.projects_management.R
import tn.sesame.projects_management.ui.projects.joinProcedure.SesameProjectActorsListState
import tn.sesame.projects_management.ui.projects.joinProcedure.SesameProjectJoinRequestCollaboratorsSelectionStateHolder
import tn.sesame.spm.domain.entities.SesameUserSex

@Composable
fun ProjectTeammatesSelectionScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    uiState: SesameProjectJoinRequestCollaboratorsSelectionStateHolder,
    onItemSelectedStateChanged : (index : Int,isSelected : Boolean)->Unit,
    onNextStepButtonClicked : ()->Unit
) {
    DetailsScreenTemplate(
        modifier = modifier,
        title = stringResource(id = R.string.project_details_title_graduation_internship),
        onBackPressed = onBackPressed
    ) {
        val listState = rememberLazyListState()
        Box(
            modifier = Modifier
                .padding(
                    16.dp
                )
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.project_form_choose_your_supervisor),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = SesameFontFamilies.MainMediumFontFamily,
                        fontWeight = FontWeight(500),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start
                    )
                )
            }
            when (val state = uiState.availableSuperVisors.value) {
                is SesameProjectActorsListState.Loading -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(
                            8.dp, Alignment.CenterVertically
                        ),
                        state = listState,
                        content = {
                            items(15) {
                                Box(
                                    modifier = Modifier
                                        .shimmerEffect(
                                            true,
                                            MaterialTheme.shapes.medium
                                        )
                                        .fillMaxWidth()
                                        .height(60.dp)
                                )
                            }
                        })
                }

                is SesameProjectActorsListState.Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {

                    }
                }

                is SesameProjectActorsListState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(
                            8.dp, Alignment.CenterVertically
                        ),
                        state = listState,
                        content = {
                            itemsIndexed(state.actors.list) { index, actor ->

                                SesameCheckableUserProfile(
                                    modifier = Modifier,
                                    fullName = actor.getFullName(),
                                    avatarURI = actor.profilePicture,
                                    placeholderResID = if (actor.sex == SesameUserSex.Male) {
                                        tn.sesame.designsystem.R.drawable.ic_profile_placeholder_male
                                    } else tn.sesame.designsystem.R.drawable.ic_profile_placeholder_female,
                                    isSelected = index in uiState.collaboratorsSelectionStateArray ,
                                    onItemSelectedStateChanged = {
                                        onItemSelectedStateChanged(
                                            index,it
                                        )
                                    }
                                )
                            }
                        })
                }
            }

            SesameButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                text = stringResource(id = tn.sesame.designsystem.R.string.next_step),
                variant = SesameButtonVariants.PrimaryHard,
                onClick = onNextStepButtonClicked
            )
        }
    }
    }