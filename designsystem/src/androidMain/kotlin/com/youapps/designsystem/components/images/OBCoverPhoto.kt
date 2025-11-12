package com.youapps.designsystem.components.images

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.youapps.designsystem.components.loading.shimmerEffect


@Composable
fun OBCoverPhoto(
    modifier: Modifier = Modifier,
    url : String?,
    contentDescription : String?=null
) {
    val isLoading = remember {
        mutableStateOf(true)
    }
    val coverSize : MutableState<IntSize?> = remember {
        mutableStateOf(null)
    }
    AsyncImage(
        modifier = modifier
            .onGloballyPositioned{
                coverSize.value = it.size
            }
            .shimmerEffect(isLoading.value),
        contentScale = ContentScale.FillWidth,
        model = ImageRequest
            .Builder(LocalContext.current)
            .size {
                coverSize.value?.let {
                    Size(
                        width = it.width ,
                        height = it.height
                    )
                } ?: Size.ORIGINAL
            }
            .data(url)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build(),
        onState = {state->
            isLoading.value = state is AsyncImagePainter.State.Loading
        },
        contentDescription = contentDescription
    )
}