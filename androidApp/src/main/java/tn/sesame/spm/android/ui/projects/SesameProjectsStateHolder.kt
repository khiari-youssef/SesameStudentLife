package tn.sesame.spm.android.ui.projects

import ProjectList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember

data class SesameProjectsStateHolder(
    val currentSearchQuery : MutableState<String>,
    val currentProjects : MutableState<ProjectList>
){
    companion object{
        @Composable
        fun rememberSesameProjectsState(
            currentSearchQuery : MutableState<String>,
            currentProjects : MutableState<ProjectList>
        ) : SesameProjectsStateHolder = remember(currentSearchQuery,currentProjects) {
            SesameProjectsStateHolder(
                currentSearchQuery, currentProjects
            )
        }
    }
}