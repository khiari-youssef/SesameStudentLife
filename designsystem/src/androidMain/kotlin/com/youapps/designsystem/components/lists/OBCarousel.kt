package com.youapps.designsystem.components.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.images.OBCoverPhoto
import com.youapps.designsystem.components.loading.shimmerEffect


sealed interface CarouselState{
    @Immutable
    object Loading: CarouselState

    @Immutable
    data class Loaded(val images: List<String>): CarouselState
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OBCarousel(
    modifier: Modifier,
    state : CarouselState,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    itemSpacing: Dp = 8.dp,
    preferredItemWidth: Dp,
    onItemClicked : ((String)-> Unit)?=null
) {
    val carouselState = rememberCarouselState {
      when(state){
          CarouselState.Loading -> 3
          is CarouselState.Loaded -> state.images.size
      }
    }

    HorizontalMultiBrowseCarousel(
        state = carouselState,
        modifier = modifier,
        preferredItemWidth = preferredItemWidth,
        itemSpacing = itemSpacing,
        contentPadding = contentPadding
    ) { index ->
        OBCoverPhoto(
            modifier = Modifier
                .shimmerEffect(state is CarouselState.Loading)
                .clickable(enabled = onItemClicked != null && state is CarouselState.Loaded){
                    if (state is CarouselState.Loaded) {
                        onItemClicked?.invoke(state.images[index])
                    }
                }
                .height(206.dp),
            url = if (state is CarouselState.Loaded)  state.images[index] else ""
        )
    }
}