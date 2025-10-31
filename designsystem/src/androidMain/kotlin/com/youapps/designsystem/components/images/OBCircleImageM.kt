import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@Composable
fun OBCircleImageM(
    uri : Any,
    placeholderRes : Int,
    errorRes : Int
) {
    OBCircleImage(
        uri = uri,
        placeholderRes = placeholderRes,
        errorRes = errorRes,
        size = 24.dp
    )
}

