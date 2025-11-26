package com.youapps.designsystem.components.lists

import OBButtonContainedPrimary
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.R
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OBCarouselEditable(
    modifier: Modifier,
    state : CarouselState.Loaded,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    itemSpacing: Dp = 8.dp,
    preferredItemWidth: Dp,
    imagePickLimit: Int = 5,
    onGalleryItemAdd: ()-> Unit,
    onItemClicked : ((String)-> Unit)?=null,
    onItemDeleted: (Int)-> Unit
) {
    if (state.images.isNotEmpty()) {

        val carouselState = rememberCarouselState {
            state.images.size
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            HorizontalMultiBrowseCarousel(
                state = carouselState,
                modifier = modifier,
                preferredItemWidth = preferredItemWidth,
                itemSpacing = itemSpacing,
                contentPadding = contentPadding
            ) { index ->
                Box{
                    OBCoverPhoto(
                        modifier = Modifier
                            .clickable(enabled = onItemClicked != null){
                                onItemClicked?.invoke(state.images[index])
                            }
                            .height(206.dp),
                        url =state.images[index]
                    )
                    Icon(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .background(color = MaterialTheme.colorScheme.primaryContainer , shape = CircleShape)
                            .clip(CircleShape)
                            .clickable(onClick = {
                                onItemDeleted(index)
                            })
                        ,
                        imageVector = ImageVector.vectorResource(R.drawable.ic_clear),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(R.string.content_description_delete_button)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OBButtonContainedPrimary(
                    text = "Add more images",
                    isEnabled = state.images.size <= imagePickLimit,
                    onClick = onGalleryItemAdd
                )
                Text(
                    modifier = Modifier.weight(0.1f),
                    text = "${state.images.size}/$imagePickLimit",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}