package tn.sesame.spm.domain.entities

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

sealed class SesameProjectNotification(
    val id : String,
    val senderID : String,
    val senderImage : String,
    val senderFullName : String,
    val projectRef : String,
    val timestamp : Instant
){

     class SesameProjectRequestNotification(
         senderID : String,
         senderImage : String,
         senderFullName : String,
         projectRef : String,
         timestamp : Instant = Clock.System.now(),
        val requestType : String
    ) : SesameProjectNotification(
        senderID,senderImage,senderImage, senderFullName, projectRef,timestamp
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
          timestamp : Instant= Clock.System.now(),
        val action : String,
        val isAccepted : Boolean
    ): SesameProjectNotification(
        senderID,senderImage,senderImage, senderFullName, projectRef,timestamp
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
         val infoType : String,
         timestamp : Instant= Clock.System.now(),
    ): SesameProjectNotification(
         senderID,senderImage,senderImage, senderFullName, projectRef,timestamp
     ){
        companion object{
            const val ACTION_ASSIGNMENT : String = "a_assignment"
        }
    }
}