import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import tn.sesame.designsystem.R
import tn.sesame.designsystem.components.loading.shimmerEffect


@Composable
fun SesameCircleImage(
    modifier: Modifier = Modifier,
    uri : Any,
    placeholderRes : Int = R.drawable.profile_placeholder,
    errorRes : Int = R.drawable.profile_placeholder,
    size : Dp,
    borderStroke: BorderStroke = BorderStroke(width = 0.dp, color = Color.Unspecified)
) {
    val currentContext = LocalContext.current
    val isLoading = remember {
        mutableStateOf(true)
    }
 AsyncImage(
     modifier = modifier
         .border(
             border = borderStroke,
             shape = CircleShape
         )
         .background(
             color = Color.Unspecified,
             shape = CircleShape
         )
         .clip(CircleShape)
         .requiredSize(size)
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

