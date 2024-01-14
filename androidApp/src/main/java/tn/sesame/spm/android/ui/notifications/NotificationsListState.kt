package tn.sesame.spm.android.ui.notifications

import androidx.compose.runtime.Stable
import tn.sesame.spm.domain.entities.SesameProjectNotification



sealed interface NotificationsListState{

    @Stable
    data object NotificationsLoading  : NotificationsListState

    @Stable
    data class Error(val code : Int) : NotificationsListState

    @Stable
    data class Success(val notificationsList: List<SesameProjectNotification>,val isRefreshingMoreUpwards : Boolean = false,val isRefreshingMoreDownwards : Boolean = false) : NotificationsListState
}