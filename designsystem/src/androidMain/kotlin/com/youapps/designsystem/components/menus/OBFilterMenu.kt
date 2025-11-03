package com.youapps.designsystem.components.menus

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.OBFontFamilies
import com.youapps.designsystem.R

@Immutable
data class OBFilterMenuData(
    val items : List<OBFilterItemData>
)

@Immutable
data class OBFilterItemData(
    val label : String,
    val selectedBackgroundColor : Color,
    val unSelectedBackgroundColor : Color,
    val selectedBorderStroke : BorderStroke?=null,
    val unSelectedBorderStroke : BorderStroke?=null,
    val selectedTextColor : Color,
    val unSelectedTextColor: Color
)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OBFilterItem(
    modifier: Modifier = Modifier,
    data : OBFilterItemData,
    isEnabled : Boolean = true,
    isSelected: Boolean = true,
    onClick: ()-> Unit
) {
    FilterChip(
        modifier = modifier,
        selected = isSelected,
        enabled = isEnabled,
        colors = ChipDefaults.filterChipColors(
            selectedBackgroundColor = data.selectedBackgroundColor,
            backgroundColor = data.unSelectedBackgroundColor,
        ),
        border = if (isSelected) data.selectedBorderStroke else data.unSelectedBorderStroke,
        onClick = onClick
    ) {
        Text(
            data.label,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = OBFontFamilies.MainMediumFontFamily,
                color = if (isSelected) data.selectedTextColor else data.unSelectedTextColor
            ),
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun OBFilterMenu(
    modifier: Modifier = Modifier,
    menu : OBFilterMenuData ,
    selectedItemIndex : Int?=null,
    onItemSelected : (index : Int)-> Unit
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        maxItemsInEachRow = 4,
        maxLines = 3,
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        itemVerticalAlignment = Alignment.CenterVertically
    ) {
        menu.items.forEachIndexed { index, item ->
            OBFilterItem(
                data = item,
                isSelected = selectedItemIndex == index,
                onClick = {
                    onItemSelected(index)
                })
        }
    }
}


internal enum class FilterMenuState{
    Collapsed,Expanded
}


@Composable
fun OBExpandableFilterMenu(
    modifier: Modifier = Modifier,
    menu : OBFilterMenuData,
    actionButtonIconRes : Int,
    actionButtonBackgroundColor : Color,
    actionButtonIconColor : Color,
    selectedItemIndex : Int?=null,
    onItemSelected : (index : Int)-> Unit
) {

    val currentState = remember { MutableTransitionState(FilterMenuState.Collapsed) }
    val transition = rememberTransition(currentState, label = "box state")


    Row(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.Start),
        verticalAlignment = Alignment.Top
    ) {
        FloatingActionButton(
            backgroundColor = actionButtonBackgroundColor,
            onClick = {
                if (transition.currentState == FilterMenuState.Collapsed) {
                    currentState.targetState = FilterMenuState.Expanded
                } else {
                    currentState.targetState = FilterMenuState.Collapsed
                }
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = actionButtonIconRes),
                contentDescription = "",
                tint = actionButtonIconColor
            )
        }
        transition.AnimatedVisibility(
            visible = {
                it == FilterMenuState.Expanded
            },
            enter = expandHorizontally(),
            exit = shrinkHorizontally()
        ) {
            FlowRow(
                modifier = modifier
                    .animateContentSize()
                    .fillMaxWidth(),
                maxItemsInEachRow = 4,
                maxLines = 3,
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                itemVerticalAlignment = Alignment.CenterVertically
            ) {
                menu.items.forEachIndexed { index, item ->
                    OBFilterItem(
                        data = item,
                        isSelected = selectedItemIndex == index,
                        onClick = {
                            onItemSelected(index)
                        })
                }
            }
        }
    }




}