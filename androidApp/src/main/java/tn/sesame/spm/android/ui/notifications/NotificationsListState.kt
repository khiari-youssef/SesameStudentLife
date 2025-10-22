package tn.sesame.spm.android.ui.notifications

import androidx.compose.runtime.Stable
import tn.sesame.spm.domain.entities.OBNotification



sealed interface NotificationsListState{

    @Stable
    data class NotificationsLoading(val isRefresh : Boolean = false)  : NotificationsListState

    @Stable
    data class Error(val code : Int) : NotificationsListState

    @Stable
    data class Success(val notificationsList: List<OBNotification>, val isRefreshingMore : Boolean = false) : NotificationsListState
}