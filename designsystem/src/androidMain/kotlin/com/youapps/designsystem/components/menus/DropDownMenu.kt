package com.youapps.designsystem.components.menus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.R


@Immutable
data class DropDownMenuItemData(
    val label : String,
    val icon : Int?=null,
)

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
                                Icon(
                                    imageVector = ImageVector.vectorResource(item.icon),
                                    contentDescription = stringResource(R.string.content_description_drop_down_item,item.label),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    )
                }
            }
        }
    }

}