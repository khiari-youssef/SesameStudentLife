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

open class SesameProjectMember(
    val id : String,
    val email : String,
    val fullName : String,
    val photo : String,
    val sex : SesameUserSex
)

class SesameProjectStudentMember (
     id : String,
     email : String,
     fullName : String,
     photo : String,
    val joinStatus : SesameProjectJoinRequestState,
     sex : SesameUserSex
) : SesameProjectMember(id, email, fullName, photo, sex)


enum class ProjectType{
    PFE,PDS,PPE
}

data class SesameProject(
    val id : String,
    val type : ProjectType,
    val description : String,
    val supervisor : SesameTeacher,
    val collaboratorsToJoin : Map<SesameStudent,SesameProjectJoinRequestState>,
    val maxCollaborators : Int,
    val duration : ClosedRange<LocalDateTime>,
    val creationDate : LocalDateTime,
    val presentationDate : LocalDateTime,
    val keywords : List<String>,
    val techStack : List<String>
) {

    override fun equals(other: Any?): Boolean = other is SesameProject && other.id == id

    val joinedCollaborators : List<SesameStudent> = collaboratorsToJoin
        .filter { (student,requestState)->
            requestState == SesameProjectJoinRequestState.ACCEPTED
        }.keys.toList()

    val displayCreationDate : String = creationDate.date.formatDMY()
    val displayStartDate : String = duration.start.date.formatDMY()
    val displayEndDate : String = duration.endInclusive.date.formatDMY()
    val displayPresentationDate : String = presentationDate.date.formatDMY()
    val displayCreationTime : String = creationDate.time.formatHHMM()


 fun isFullOfCollaborators() : Boolean = joinedCollaborators.size <= maxCollaborators
 fun isActive() : Boolean
 = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) in duration

 fun isOver() : Boolean = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) > duration.endInclusive
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + supervisor.hashCode()
        result = 31 * result + collaboratorsToJoin.hashCode()
        result = 31 * result + maxCollaborators
        result = 31 * result + duration.hashCode()
        result = 31 * result + creationDate.hashCode()
        result = 31 * result + presentationDate.hashCode()
        result = 31 * result + keywords.hashCode()
        result = 31 * result + techStack.hashCode()
        result = 31 * result + joinedCollaborators.hashCode()
        result = 31 * result + displayCreationDate.hashCode()
        result = 31 * result + displayStartDate.hashCode()
        result = 31 * result + displayEndDate.hashCode()
        result = 31 * result + displayPresentationDate.hashCode()
        result = 31 * result + displayCreationTime.hashCode()
        return result
    }


}