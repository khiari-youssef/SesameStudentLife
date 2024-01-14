import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import tn.sesame.spm.android.ui.notifications.NotificationsListState
import tn.sesame.spm.domain.entities.SesameProjectNotification

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    notificationsListState : NotificationsListState,
    onRefreshNotifications: ()->Unit
) {
    val isPullRefreshing = remember {
        derivedStateOf {
            notificationsListState is NotificationsListState.Success && notificationsListState.isRefreshingMoreUpwards
        }
    }
 val listState = rememberLazyListState()
    val refreshState = rememberPullRefreshState(
        refreshing = isPullRefreshing.value,
        onRefresh = onRefreshNotifications
    )
   Box(
       modifier = Modifier
           .pullRefresh(
               state = refreshState
           )
           .fillMaxWidth()
           .wrapContentHeight(),
       contentAlignment = Alignment.TopCenter
   ){
       PullRefreshIndicator(
           refreshing = isPullRefreshing.value,
           state = refreshState,
           modifier = Modifier
               .zIndex(4f),
           backgroundColor = Color.White,
       )
       LazyColumn(
           modifier = modifier
               .padding(
                   top = 8.dp
               )
               .background(
                   color = MaterialTheme.colorScheme.surfaceVariant
               ),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.spacedBy(
               4.dp,Alignment.CenterVertically
           ),
           state = listState
       ){
           when (notificationsListState){
               is NotificationsListState.NotificationsLoading->{
                       items(15) {
                           NotificationItem(
                               modifier = Modifier
                                   .height(60.dp)
                                   .fillMaxWidth(),
                               sesameProjectNotification = null
                           )
                           Divider(
                               modifier = Modifier
                                   .padding(
                                       horizontal = 12.dp
                                   )
                                   .fillMaxWidth(),
                               thickness = 0.5f.dp
                           )
                       }
               }
               is NotificationsListState.Error->{

               }
               is NotificationsListState.Success->{
                   items(
                       items = notificationsListState.notificationsList,
                       key = {
                           it.id
                       }
                   ) { notification->

                       when (notification){
                           is SesameProjectNotification.SesameProjectRequestNotification->{
                               NotificationRequestItem(
                                   modifier = Modifier
                                       .fillMaxWidth(),
                                   sesameProjectNotification = notification
                               )
                           }
                           is SesameProjectNotification.SesameProjectInfoNotification->{
                               NotificationItem(
                                   modifier = Modifier
                                       .fillMaxWidth(),
                                   sesameProjectNotification = notification
                               )
                           }
                           is SesameProjectNotification.SesameProjectResponseNotification->{
                               NotificationResponseItem(
                                   modifier = Modifier
                                       .fillMaxWidth(),
                                   sesameProjectNotification = notification
                               )
                           }
                       }
                       Divider(
                           modifier = Modifier
                               .padding(
                                   horizontal = 12.dp
                               )
                               .fillMaxWidth(),
                           thickness = 0.5f.dp
                       )
                   }
               }
           }
       }
   }

}