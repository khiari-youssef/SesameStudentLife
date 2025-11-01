import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OBCircleImageL(
    modifier: Modifier = Modifier,
    uri : Any,
    placeholderRes : Int,
    errorRes : Int
) {
    OBCircleImage(
        modifier = modifier,
        uri = uri,
        placeholderRes = placeholderRes,
        errorRes = errorRes,
        size = 48.dp
    )
}

