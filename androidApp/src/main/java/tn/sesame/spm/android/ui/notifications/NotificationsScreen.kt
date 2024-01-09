import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tn.sesame.spm.domain.entities.SesameProjectNotification

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier
) {
 val listState = rememberLazyListState()
LazyColumn(
    modifier = modifier
        .background(
            color = MaterialTheme.colorScheme.surfaceVariant
        ),
    state = listState
){
    items(20) {
        NotificationItem(
         sesameProjectNotification = SesameProjectNotification.SesameProjectRequestNotification(
             "id",
             "aa",
             "Sender",
             "aaa",
             ""
         )
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

}