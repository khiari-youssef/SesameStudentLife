package tn.sesame.spm.android.ui.projects

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.toLocalDateTime
import tn.sesame.spm.domain.entities.ProjectType
import tn.sesame.spm.domain.entities.SesameClass
import tn.sesame.spm.domain.entities.SesameProject
import tn.sesame.spm.domain.entities.SesameProjectJoinRequestState
import tn.sesame.spm.domain.entities.SesameStudent
import tn.sesame.spm.domain.entities.SesameTeacher
import tn.sesame.spm.domain.entities.SesameUserSex


sealed interface SesameProjectsState{

    @Stable
    data object Loading : SesameProjectsState

    @Stable
    data class Error(val errorCode : Int) : SesameProjectsState

    @Stable
    data class Success(val projects : List<SesameProject>) : SesameProjectsState
}


class ProjectsViewModel : ViewModel() {

    private val allProjects = List(12){
        SesameProject(
            id ="fakeid-$it",
            type = ProjectType.PDS,
            description = "Design and build48$it a website for sesame students to ease the access to the services of sesame as well the daily routine of students and staff",
            supervisor = SesameTeacher(
                registrationID = "blabla-id",
                firstName = "mblala@gmail.com",
                lastName = "Monsieur blabla",
                profilePicture = "",
                email = "prof@gmail.com",
                profBackground = "",
                sex = SesameUserSex.Male,
                assignedClasses = listOf(SesameClass("ingta4c","ingt","4","c"))
            ),
            collaboratorsToJoin = if (it == 6) {
                buildMap{
                    repeat(5){
                        SesameStudent(
                            registrationID = "id$it",
                            email = "email$it@mail.com",
                            firstName = "firstname$it",
                            lastName = "",
                            profilePicture = "",
                            portfolioId = "",
                            sex = SesameUserSex.Male,
                            sesameClass = SesameClass("ingta4c","ingt","4","c")
                        ) to (if (it <3) SesameProjectJoinRequestState.ACCEPTED else SesameProjectJoinRequestState.WAITING_APPROVAL)
                    }
                }
            } else {
                buildMap{
                    repeat(5){
                        SesameStudent(
                            registrationID = "id$it",
                            email = "email$it@gmail.com",
                            firstName = "firstname$it",
                            lastName = "",
                            profilePicture = "",
                            portfolioId = "",
                            sex = SesameUserSex.Male,
                            sesameClass = SesameClass("ingta4c","ingt","4","c")
                        ) to (if (it <3) SesameProjectJoinRequestState.ACCEPTED else SesameProjectJoinRequestState.WAITING_APPROVAL)
                    }
                }
            },
            maxCollaborators = 5,
            duration = "2024-03-01T08:30:00".toLocalDateTime().."2024-08-15T08:30:00".toLocalDateTime(),
            creationDate = "2023-11-01T20:30:00".toLocalDateTime(),
            presentationDate = "2024-07-10T08:30:00".toLocalDateTime(),
            keywords = listOf("WebDev","UI","Backend"),
            techStack = listOf(
                "Angular",
                "NodeJS",
                "MySql"
            )
        )
    }

private val currentProjectsMutableState : MutableStateFlow<SesameProjectsState> = MutableStateFlow(SesameProjectsState.Loading)
 val currentProjectsState : StateFlow<SesameProjectsState> = currentProjectsMutableState

fun refreshProjects(userID : String?=null,keywordsFilter : String?=null) {
    currentProjectsMutableState.update {
        SesameProjectsState.Loading
    }
    viewModelScope.launch {
        delay(1000)
        val filteredProjects = if (userID.isNullOrBlank()) {
            keywordsFilter?.takeIf { it.isNotBlank() }?.let { keyword->
                allProjects.filter {
                    it.description.contains(keyword)
                }
            } ?: allProjects
        } else listOf()
        currentProjectsMutableState.update {
            SesameProjectsState.Success(filteredProjects)
        }
    }
}


fun getProjectById(project : String) : Flow<SesameProject?> = flow {
    emit(
        SesameProject(
            "idp",
            ProjectType.PFE,
            "lorepsum ".repeat(20),
            SesameTeacher(
                registrationID = "",
                firstName = "Supervisor",
                lastName = "",
                email = "teacher@sesame.com.tn",
                sex = SesameUserSex.Male,
                profilePicture = "",
                profBackground = "Software design engineer",
                assignedClasses = listOf(SesameClass("ingta4c","ingt","4","c"))
            ),
            buildMap{
                repeat(5){
                    SesameStudent(
                        registrationID = "id$it",
                        email = "email$it@mail.com",
                        firstName = "firstname$it",
                        lastName = "",
                        profilePicture = "",
                        portfolioId = "",
                        sex = SesameUserSex.Male,
                        sesameClass = SesameClass("ingta4c","ingt","4","c")
                    ) to  SesameProjectJoinRequestState.ACCEPTED
                }
            },
            5,
            LocalDateTime(dayOfMonth = 1, month = Month.MARCH, year = 2024, hour = 23, minute = 23)..LocalDateTime(dayOfMonth = 1, month = Month.SEPTEMBER, year = 2024, hour = 23, minute = 23),
            LocalDateTime(dayOfMonth = 23, month = Month.FEBRUARY, year = 2024, hour = 8, minute = 23),
            LocalDateTime(dayOfMonth = 23, month = Month.DECEMBER, year = 2024, hour = 8, minute = 23),
            listOf("Bigdata","analytics","hadoop"),
            listOf("hadoop","kafka","cassandra","rabbitmq","nodejs","postgresSql","AWS")
        )
    )
}



}