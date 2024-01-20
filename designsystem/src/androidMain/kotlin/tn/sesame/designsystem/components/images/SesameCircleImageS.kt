import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SesameCircleImageS(
    uri : Any,
    placeholderRes : Int,
    errorRes : Int
) {
    SesameCircleImage(
        uri = uri,
        placeholderRes = placeholderRes,
        errorRes = errorRes,
        size = 16.dp
    )
}

