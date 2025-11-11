package com.youapps.designsystem.components.images

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
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
    AsyncImage(
        modifier = modifier
            .shimmerEffect(isLoading.value),
        contentScale = ContentScale.FillWidth,
        model = ImageRequest
            .Builder(LocalContext.current)
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