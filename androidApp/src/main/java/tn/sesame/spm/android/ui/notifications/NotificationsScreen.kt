import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import tn.sesame.spm.android.ui.notifications.NotificationsListState
import tn.sesame.spm.domain.entities.SesameProjectNotification
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    notificationsListState : NotificationsListState
) {
 val listState = rememberLazyListState()
LazyColumn(
    modifier = modifier
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
        is NotificationsListState.Loading->{
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