import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier
) {
 val listState = rememberLazyListState()
LazyColumn(
    modifier = modifier,
    state = listState
){
    items(20) {
        NotificationItem()
    }
}

}