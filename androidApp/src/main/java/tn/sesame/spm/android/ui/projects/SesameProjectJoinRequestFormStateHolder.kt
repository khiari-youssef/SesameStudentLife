package tn.sesame.spm.android.ui.projects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import tn.sesame.spm.domain.entities.SesameUser
import java.lang.Exception

@Immutable
@JvmInline
value class SesameProjectActors(
    val list : List<SesameUser>
)

@Immutable
@JvmInline
value class SesameProjectTechnologies(
    val list : List<String>
)

@Immutable
@JvmInline
value class SelectedIndexes(
    val list : List<Int>
)

sealed interface SesameProjectActorsListState{
    data object Loading : SesameProjectActorsListState

    data class Error(val exception: Exception?=null) : SesameProjectActorsListState

    data class Success(val actors : SesameProjectActors) : SesameProjectActorsListState
}

data class SesameProjectJoinRequestSupervisorSelectionStateHolder(
    val availableSuperVisors : State<SesameProjectActorsListState>,
    val selectedSupervisorIndex : MutableState<Int?>
) {
    companion object{
        @Composable
        fun rememberSesameProjectJoinRequestFormState(
             availableSuperVisors : State<SesameProjectActorsListState>,
             selectedSupervisorIndex : MutableState<Int?>
        ) : SesameProjectJoinRequestSupervisorSelectionStateHolder = remember(
            availableSuperVisors,selectedSupervisorIndex
        ){
            SesameProjectJoinRequestSupervisorSelectionStateHolder(
                availableSuperVisors, selectedSupervisorIndex
            )
        }
    }
}