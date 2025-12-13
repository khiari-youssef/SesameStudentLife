package com.youapps.designsystem.components.menus

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.OBFontFamilies

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
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = data.selectedBackgroundColor,
            containerColor = data.unSelectedBackgroundColor,
        ),
        border = if (isSelected) data.selectedBorderStroke else data.unSelectedBorderStroke,
        onClick = onClick,
        label = {
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
    )
}


@Composable
fun OBKeywordsList(
    modifier: Modifier = Modifier,
    keywords : List<String>,
    onKeyWordClicked: (String)-> Unit
) {
    if (keywords.isNotEmpty()) {
        FlowRow(
            modifier = modifier.fillMaxWidth(),
            maxLines = 3,
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            itemVerticalAlignment = Alignment.CenterVertically
        ) {
            val borderShape = RoundedCornerShape(8.dp)
            keywords.forEach { item ->
                Box(
                    modifier = Modifier
                        .clickable {
                            onKeyWordClicked(item)
                        }
                        .background(color = MaterialTheme.colorScheme.primary, shape = borderShape)
                        .clip(borderShape)
                        .wrapContentSize()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = 4.dp,
                                horizontal = 12.dp
                            ),
                        text = item,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            fontFamily = OBFontFamilies.MainMediumFontFamily,
                            color = Color.White
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}

@Composable
fun OBFilterMenu(
    modifier: Modifier = Modifier,
    menu : OBFilterMenuData,
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


@Immutable
data class OBExpandableFilterActionButton(
    val actionButtonIconRes : Int,
    val actionButtonBackgroundColor : Color,
    val actionButtonIconColor : Color,
)


@Composable
fun OBExpandableFilterMenu(
    modifier: Modifier = Modifier,
    menu : OBFilterMenuData,
    oBExpandableFilterActionButton : OBExpandableFilterActionButton,
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
            containerColor = oBExpandableFilterActionButton.actionButtonBackgroundColor,
            onClick = {
                if (transition.currentState == FilterMenuState.Collapsed) {
                    currentState.targetState = FilterMenuState.Expanded
                } else {
                    currentState.targetState = FilterMenuState.Collapsed
                }
            }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = oBExpandableFilterActionButton.actionButtonIconRes),
                contentDescription = "",
                tint = oBExpandableFilterActionButton.actionButtonIconColor
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