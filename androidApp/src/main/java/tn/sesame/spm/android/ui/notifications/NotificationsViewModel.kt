package tn.sesame.spm.android.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tn.sesame.spm.domain.entities.SesameProjectNotification

class NotificationsViewModel : ViewModel() {


    private val _latestNotificationsState : MutableStateFlow<NotificationsListState> = MutableStateFlow(NotificationsListState.NotificationsLoading())
    val latestNotificationsState : StateFlow<NotificationsListState> = _latestNotificationsState

    init {
        getLastNotifications()
    }

    val list1 = List(10){
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
    val list2 = List(2){
            SesameProjectNotification.SesameProjectInfoNotification(
                "idk2$it",
                "",
                "Mohsen",
                "#PPE${it*10}",
                SesameProjectNotification.SesameProjectInfoNotification.ACTION_ASSIGNMENT
            )

    }
fun getLastNotifications(isRefresh : Boolean = false) {
viewModelScope.launch {
    _latestNotificationsState.update {currentState->
        NotificationsListState.NotificationsLoading(isRefresh)
    }
    delay(2000)
    _latestNotificationsState.update {currentState->
       NotificationsListState.Success(list1)
    }
}
}

}