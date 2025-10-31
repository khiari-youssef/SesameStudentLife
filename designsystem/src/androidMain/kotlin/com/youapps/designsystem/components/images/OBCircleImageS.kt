import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun OBCircleImageS(
    uri : Any,
    placeholderRes : Int,
    errorRes : Int
) {
    OBCircleImage(
        uri = uri,
        placeholderRes = placeholderRes,
        errorRes = errorRes,
        size = 16.dp
    )
}

