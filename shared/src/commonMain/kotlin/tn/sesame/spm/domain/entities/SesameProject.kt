package tn.sesame.spm.domain.entities

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import tn.sesame.spm.domain.formatDMY
import tn.sesame.spm.domain.formatHHMM

enum class SesameProjectJoinRequestState{
    ACCEPTED,REJECTED,WAITING_APPROVAL
}

class SesameProjectSupervisor(
    val id : String,
    val email : String,
    val fullName : String,
    val photo : String
)

class SesameProjectCollaborator(
    val id : String,
    val email : String,
    val fullName : String,
    val photo : String,
    val joinStatus : SesameProjectJoinRequestState
)


enum class ProjectType{
    PFE,PDS,PPE
}

data class SesameProject(
    val id : String,
    val type : ProjectType,
    val description : String,
    val supervisor : SesameProjectSupervisor,
    val collaboratorsToJoin : List<SesameProjectCollaborator>,
    val maxCollaborators : Int,
    val duration : ClosedRange<LocalDateTime>,
    val creationDate : LocalDateTime,
    val presentationDate : LocalDateTime,
    val keywords : List<String>,
    val techStack : List<String>
) {

    val joinedCollaborators : List<SesameProjectCollaborator> = collaboratorsToJoin
        .filter { state->
            state.joinStatus == SesameProjectJoinRequestState.ACCEPTED
        }

    val displayCreationDate : String = creationDate.date.formatDMY()
    val displayStartDate : String = duration.start.date.formatDMY()
    val displayEndDate : String = duration.endInclusive.date.formatDMY()
    val displayPresentationDate : String = presentationDate.date.formatDMY()
    val displayCreationTime : String = creationDate.time.formatHHMM()


 fun isFullOfCollaborators() : Boolean = joinedCollaborators.size <= maxCollaborators
 fun isActive() : Boolean
 = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) in duration

 fun isOver() : Boolean = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) > duration.endInclusive


}