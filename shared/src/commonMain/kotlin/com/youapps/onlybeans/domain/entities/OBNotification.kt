package com.youapps.onlybeans.domain.entities

sealed class OBNotification(
    val id : String,
    val senderID : String,
    val senderImage : String,
    val senderFullName : String,
    val projectRef : String
){

    override fun equals(other: Any?): Boolean = other is OBNotification && other.id == id
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + senderID.hashCode()
        result = 31 * result + senderImage.hashCode()
        result = 31 * result + senderFullName.hashCode()
        result = 31 * result + projectRef.hashCode()
        return result
    }

    class OBRequestNotification(
         senderID : String,
         senderImage : String,
         senderFullName : String,
         projectRef : String,
        val requestType : String
    ) : OBNotification(
        senderID,senderImage,senderImage, senderFullName, projectRef
    ){
        companion object{
            const val ACTION_INVITATION_REQUEST : String = "a_invitation_req"
            const val ACTION_JOIN_REQUEST : String = "a_join_req"
            const val ACTION_SUPERVISION_REQUEST : String = "a_supervision_req"
        }
    }
     class OBResponseNotification(
         senderID : String,
         senderImage : String,
         senderFullName : String,
         projectRef : String,
        val action : String,
        val isAccepted : Boolean
    ): OBNotification(
        senderID,senderImage,senderImage, senderFullName, projectRef
    ) {
        companion object{
            const val RESPONSE_INVITATION : String = "response_invitation"
            const val RESPONSE_JOIN : String = "response_join"
            const val RESPONSE_SUPERVISION : String = "response_supervision"
        }
    }
     class OBInfoNotification(
         senderID : String,
         senderImage : String,
         senderFullName : String,
         projectRef : String,
        val infoType : String
    ): OBNotification(
         senderID,senderImage,senderImage, senderFullName, projectRef
     ){
        companion object{
            const val ACTION_ASSIGNMENT : String = "a_assignment"
        }
    }
}