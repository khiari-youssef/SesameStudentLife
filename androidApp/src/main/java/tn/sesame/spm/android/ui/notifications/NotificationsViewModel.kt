package tn.sesame.spm.android.ui.notifications

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tn.sesame.spm.domain.entities.SesameProjectNotification

class NotificationsViewModel : ViewModel() {

fun getLastNotifications() : Flow<NotificationsListState> = flow{
delay(1000)
 emit(NotificationsListState.Success(
     notificationsList = List(10){
         if (it.mod(2) == 0){
             SesameProjectNotification.SesameProjectRequestNotification(
                 "id$it",
                 "",
                 "Ahmed",
                 "#PPE${it*10}",
                 ""
             )
         } else {
             SesameProjectNotification.SesameProjectInfoNotification(
                 "id$it",
                 "",
                 "Yassine",
                 "#PDS${it*10}",
                 SesameProjectNotification.SesameProjectInfoNotification.ACTION_ASSIGNMENT
             )
         }
     }
 ))
}

}