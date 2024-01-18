package tn.sesame.spm.android.ui.notifications

import androidx.compose.runtime.Composable
import androidx.compose.runtime.IntState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember

class NotificationScreenStateHolder(
    val notificationsListState : State<NotificationsListState>,
    val currentPage : MutableIntState
){

    companion object{

        @Composable
        fun rememberNotificationScreenState(
            notificationsListState : State<NotificationsListState>,
            currentPage : MutableIntState
        ) : NotificationScreenStateHolder = remember(notificationsListState, currentPage) {
            NotificationScreenStateHolder(
                notificationsListState, currentPage
            )
        }
    }
}