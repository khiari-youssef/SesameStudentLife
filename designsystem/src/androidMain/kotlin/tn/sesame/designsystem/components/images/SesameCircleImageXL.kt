import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SesameCircleImageXL(
    uri : Any,
    placeholderRes : Int,
    errorRes : Int
) {
    val currentContext = LocalContext.current
 AsyncImage(
     modifier = Modifier
         .requiredSize(96.dp),
     model = ImageRequest
     .Builder(currentContext)
     .data(uri)
     .placeholder(placeholderRes)
     .error(errorRes)
     .build(),
     contentDescription = ""
 )
}

