import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun OBCircleImageL(
    uri : Any,
    placeholderRes : Int,
    errorRes : Int
) {
    OBCircleImage(
        uri = uri,
        placeholderRes = placeholderRes,
        errorRes = errorRes,
        size = 48.dp
    )
}

