import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun SesameCircleImageXL(
    uri : Any,
    placeholderRes : Int,
    errorRes : Int
) {
    SesameCircleImage(
        uri = uri,
        placeholderRes = placeholderRes,
        errorRes = errorRes,
        size = 96.dp
    )
}

