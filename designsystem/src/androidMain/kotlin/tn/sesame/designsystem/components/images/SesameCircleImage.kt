import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import tn.sesame.designsystem.components.animations.shimmerEffect

@Composable
fun SesameCircleImage(
    modifier: Modifier = Modifier,
    uri : Any,
    placeholderRes : Int,
    errorRes : Int
) {
    val currentContext = LocalContext.current
    val isLoading = remember {
        mutableStateOf(true)
    }
 AsyncImage(
     modifier = modifier
         .shimmerEffect(isLoading.value),
     model = ImageRequest
     .Builder(currentContext)
     .data(uri)
     .placeholder(placeholderRes)
     .error(errorRes)
     .build(),
     onState = {state->
       isLoading.value = state is AsyncImagePainter.State.Loading
     },
     contentDescription = ""
 )
}

