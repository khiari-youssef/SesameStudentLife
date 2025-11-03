package com.youapps.designsystem.components.menus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
    /*
    = OBFilterMenuData(
        items = List(6){
            OBFilterItemData(
                selectedTextColor = Color.White,
                unSelectedTextColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                selectedBackgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                unSelectedBackgroundColor = Color.White,
                unSelectedBorderStroke = BorderStroke(
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                    width = 1.dp
                ),
                label = "option$it"
            )
        }
    ),
     */
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