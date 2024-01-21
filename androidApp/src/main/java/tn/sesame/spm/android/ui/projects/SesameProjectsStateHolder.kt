package tn.sesame.spm.android.ui.projects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.remember

data class SesameProjectsStateHolder(
    val currentSearchQuery : MutableState<String>,
    val projectsState : State<SesameProjectsState>
){
    companion object{
        @Composable
        fun rememberSesameProjectsState(
            currentSearchQuery : MutableState<String>,
            currentProjects : State<SesameProjectsState>
        ) : SesameProjectsStateHolder = remember(currentSearchQuery,currentProjects) {
            SesameProjectsStateHolder(
                currentSearchQuery, currentProjects
            )
        }
    }
}