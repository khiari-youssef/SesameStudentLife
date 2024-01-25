import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp


@Composable
fun SesameCircleImageM(
    uri : Any,
    placeholderRes : Int,
    errorRes : Int
) {
    SesameCircleImage(
        uri = uri,
        placeholderRes = placeholderRes,
        errorRes = errorRes,
        size = 24.dp
    )
}

