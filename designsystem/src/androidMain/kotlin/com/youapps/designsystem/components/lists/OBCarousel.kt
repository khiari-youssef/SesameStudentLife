package com.youapps.designsystem.components.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.images.OBCoverPhoto


@Immutable
data class CarouselData(
    val images: List<String>,
    val contentDescription: String?=null
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OBCarousel(
  modifier: Modifier,
  data : CarouselData,
  contentPadding: PaddingValues = PaddingValues(0.dp),
  itemSpacing: Dp = 8.dp,
  preferredItemWidth: Dp,
  onItemClicked : ((String)-> Unit)?=null
) {
    val carouselState = rememberCarouselState { data.images.size }

    HorizontalMultiBrowseCarousel(
        state = carouselState,
        modifier = modifier,
        preferredItemWidth = preferredItemWidth,
        itemSpacing = itemSpacing,
        contentPadding = contentPadding
    ) { index ->
        OBCoverPhoto(
            modifier = Modifier
                .clickable(enabled = onItemClicked != null){
                    onItemClicked?.invoke(data.images[index])
                }
                .height(206.dp),
            url = data.images[index]
        )
    }
}