package tn.sesame.spm.domain.entities

sealed class SesameProjectNotification(
    val id : String,
    val senderID : String,
    val senderImage : String,
    val senderFullName : String,
    val projectRef : String
){

    override fun equals(other: Any?): Boolean = other is SesameProjectNotification && other.id == id
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + senderID.hashCode()
        result = 31 * result + senderImage.hashCode()
        result = 31 * result + senderFullName.hashCode()
        result = 31 * result + projectRef.hashCode()
        return result
    }

    class SesameProjectRequestNotification(
         senderID : String,
         senderImage : String,
         senderFullName : String,
         projectRef : String,
        val requestType : String
    ) : SesameProjectNotification(
        senderID,senderImage,senderImage, senderFullName, projectRef
    ){
        companion object{
            const val ACTION_INVITATION_REQUEST : String = "a_invitation_req"
            const val ACTION_JOIN_REQUEST : String = "a_join_req"
            const val ACTION_SUPERVISION_REQUEST : String = "a_supervision_req"
        }
    }
     class SesameProjectResponseNotification(
         senderID : String,
         senderImage : String,
         senderFullName : String,
         projectRef : String,
        val action : String,
        val isAccepted : Boolean
    ): SesameProjectNotification(
        senderID,senderImage,senderImage, senderFullName, projectRef
    ) {
        companion object{
            const val RESPONSE_INVITATION : String = "response_invitation"
            const val RESPONSE_JOIN : String = "response_join"
            const val RESPONSE_SUPERVISION : String = "response_supervision"
        }
    }
     class SesameProjectInfoNotification(
         senderID : String,
         senderImage : String,
         senderFullName : String,
         projectRef : String,
        val infoType : String
    ): SesameProjectNotification(
         senderID,senderImage,senderImage, senderFullName, projectRef
     ){
        companion object{
            const val ACTION_ASSIGNMENT : String = "a_assignment"
        }
    }
}