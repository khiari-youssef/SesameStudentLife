package tn.sesame.spm.domain.entities

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

enum class SesameProjectJoinRequestState{
    ACCEPTED,REJECTED,WAITING_APPROVAL
}

class SesameSupervisor(
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

class SesameProject(
    val id : String,
    val type : String,
    val description : String,
    val supervisor : SesameSupervisor,
    val collaboratorsToJoin : List<SesameProjectCollaborator>,
    val maxCollaborators : Int,
    val duration : ClosedRange<LocalDateTime>,
    val creationDate : LocalDateTime,
    val presentationDate : LocalDateTime,
    val keywords : List<String>,
    val techStack : List<String>
){

    val joinedCollaborators : List<SesameProjectCollaborator> = collaboratorsToJoin
        .filter { state->
            state.joinStatus == SesameProjectJoinRequestState.ACCEPTED
        }

    val displayCreationDate : String = creationDate.date.formatDMY()
    val displayStartDate : String = duration.start.date.formatDMY()
    val displayEndDate : String = duration.endInclusive.date.formatDMY()
    val displayPresentationDate : String = presentationDate.date.formatDMY()
    val displayCreationTime : String = creationDate.time.formatHHMM()


 fun isFullOfCollaborators() : Boolean = joinedCollaborators.size < maxCollaborators
 fun isActive() : Boolean
 = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) in duration

 fun isOver() : Boolean = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()) > duration.endInclusive


fun LocalDate.formatDMY() : String {
    val displayDay = if (dayOfMonth > 9) "$dayOfMonth" else "0$dayOfMonth"
    val displayMonth = if (month.ordinal > 9) "$month" else "0$month"
    return "$displayDay/$displayMonth/$year"
}

fun LocalTime.formatHHMM() : String {
        val hours = if(hour == 0) "00" else if (hour > 9) "$hour" else "0$hour"
        val minutes = if (minute == 0) "00" else if (minute > 9) "$minute" else "0$minute"
        return "${hours}h:${minutes}m"
}

}