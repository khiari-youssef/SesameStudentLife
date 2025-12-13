package com.youapps.onlybeans.ui

import OBButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.youapps.designsystem.R
import com.youapps.designsystem.components.loading.shimmerEffect
import com.youapps.onlybeans.domain.entities.products.OBProductListItem

@Immutable
data class ProductOverViewListData(
    val items : List<OBProductListItem>
)

@Composable
fun ProductOverViewList(
    modifier: Modifier = Modifier,
    data: ProductOverViewListData,
    sectionTitle : String,
    maxRows : Int = 4,
    onItemClick : (OBProductListItem)->Unit,
    onSeeAllClick : ()->Unit
){
    data.items.takeIf { it.isNotEmpty() }?.take(maxRows)?.let { listData->
        Column (
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = sectionTitle,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
                List(listData.size){ index->
                    val item : OBProductListItem? = listData[index]
                    item?.run {
                        ProductOverViewItem(
                            imagePreviewUrl = item.productImagePreview,
                            imagePreviewContentDescription = stringResource(com.youapps.onlybeans.R.string.content_description_product_image_preview,item.productName),
                            title = item.productName,
                            description = item.productDescription,
                            actionIcon = R.drawable.ic_arrow_right,
                            actionIconContentDescription = stringResource(com.youapps.onlybeans.R.string.content_description_product_image_preview,item.productName),
                            onActionClick = {
                                onItemClick(item)
                            }
                        )
                    }
                }
                if (data.items.size > maxRows) {
                    Row(
                      modifier = Modifier.fillMaxWidth(),
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.End
                    ) {
                        OBButton(
                            backgroundColor = Color(0xFF3A3A3A),
                            text = stringResource(com.youapps.onlybeans.R.string.see_all_button),
                            onClick = onSeeAllClick
                        )

                    }
                }


        }
    }
}




@Composable
fun ProductOverViewItem(
    modifier: Modifier = Modifier,
    imagePreviewUrl : String,
    imagePreviewContentDescription : String,
    title : String,
    description : String,
    actionIcon : Int?=null,
    actionIconContentDescription : String,
    onActionClick: (()-> Unit)?=null
) {
    Card(
        modifier = modifier,
        shape = RectangleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    )  {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 4.dp
                )
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
        ){
            val isImageLoading = remember {
                mutableStateOf(true)
            }
            AsyncImage(
                modifier = modifier
                    .size(56.dp)
                    .shimmerEffect(isImageLoading.value),
                contentScale = ContentScale.FillWidth,
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(imagePreviewUrl)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .crossfade(true)
                    .build(),
                onState = {state->
                    isImageLoading.value = state is AsyncImagePainter.State.Loading
                },
                contentDescription = imagePreviewContentDescription
            )
            Column(
                modifier = Modifier
                    .weight(if (actionIcon != null) 0.9f else 1f)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF49454F),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            actionIcon?.run {
                Icon(
                    modifier = Modifier
                        .weight(0.1f)
                        .padding(8.dp)
                        .apply{
                            onActionClick?.run {
                                then(Modifier.clickable(onClick = onActionClick))
                            } ?: this
                        },
                    imageVector = ImageVector.vectorResource(id = actionIcon),
                    contentDescription = actionIconContentDescription
                )
            }
        }
    }
}