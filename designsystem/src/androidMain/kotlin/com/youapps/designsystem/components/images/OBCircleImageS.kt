import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OBCircleImageS(
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
        size = 16.dp
    )
}

