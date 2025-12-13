package com.youapps.designsystem.components.menus

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.youapps.designsystem.R

sealed interface ImageMediaType{
    data class Resource(val resId : Int,val size : DpSize?=null) : ImageMediaType
    data class Url(val url : String,val size : DpSize?=null) : ImageMediaType {
        override fun toString(): String = url
    }
}

@Immutable
data class DropDownMenuItemData(
    val label : String,
    val icon : ImageMediaType?=null,
)


sealed interface DropDownMenuDataState {
    data object Loading : DropDownMenuDataState
    data object Error : DropDownMenuDataState

    @Immutable
    data class Success(val items : List<DropDownMenuItemData>) : DropDownMenuDataState
}
@Immutable
data class DropDownMenuData(
    val items : List<DropDownMenuItemData>
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OBDropDownMenu(
    modifier: Modifier = Modifier,
    data : DropDownMenuData,
    isExpanded : Boolean,
    scrollState: ScrollState = rememberScrollState(),
    onExpandedChange : (Boolean)-> Unit,
    onClick : (DropDownMenuItemData) -> Unit
) {
    if( data.items.isNotEmpty()) {
        ExposedDropdownMenuBox(
            modifier = modifier,
            expanded = isExpanded,
            onExpandedChange = onExpandedChange,
        ) {
            ExposedDropdownMenu(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                scrollState = scrollState,
                expanded = isExpanded,
                onDismissRequest = {
                    onExpandedChange(false)
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                data.items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                modifier = Modifier,
                                text = item.label,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                textAlign = TextAlign.Start
                            )
                        },
                        onClick = {
                            onClick(item)
                        },
                        leadingIcon =item.icon?.run {
                            {
                                when (item.icon){
                                    is ImageMediaType.Resource ->Icon(
                                        modifier = Modifier.size(item.icon.size ?: DpSize(
                                            width = 25.dp,
                                            height = 30.dp)),
                                        imageVector = ImageVector.vectorResource(item.icon.resId),
                                        contentDescription = stringResource(R.string.content_description_drop_down_item,item.label),
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                    is ImageMediaType.Url ->  AsyncImage(
                                        modifier = Modifier.size(item.icon.size ?: DpSize(
                                            width = 25.dp,
                                            height = 30.dp)),
                                        model = ImageRequest.Builder(LocalContext.current)
                                            .data(item.icon.url)
                                            .diskCachePolicy(CachePolicy.ENABLED)
                                            .memoryCachePolicy(CachePolicy.ENABLED)
                                            .build() ,
                                        contentDescription = stringResource(R.string.content_description_drop_down_item,item.label),
                                        )

                                }

                            }
                        }
                    )
                }
            }
        }
    }

}