package com.youapps.designsystem.components.checkables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.DisabledOnSurfaceColorDarkMode
import com.youapps.designsystem.DisabledOnSurfaceColorLightMode
import com.youapps.designsystem.ShadedWhite
import com.youapps.designsystem.TonedDark

@Stable
data class OBRadioGroupData(
    val items : List<String>,
    val checkedItemIndex : Int,
    val disabledItemsIndexes : List<Int> = emptyList()
)



@Composable
fun OBRadioButton(
    modifier: Modifier = Modifier,
    isChecked : Boolean,
    isCheckable : Boolean = true,
    label : String,
    onClick : (Boolean)-> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
    ) {
        val disabledTextColor = if (isSystemInDarkTheme()) DisabledOnSurfaceColorDarkMode else DisabledOnSurfaceColorLightMode
        RadioButton(
            modifier = modifier,
            enabled = isCheckable,
            selected = isChecked,
            onClick = {
                onClick(isChecked.not())
            },
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                disabledSelectedColor = disabledTextColor,
                disabledUnselectedColor = disabledTextColor
            )
        )
        Text(
            text = label,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.labelMedium,
            color = if(isCheckable) MaterialTheme.colorScheme.onSurface else disabledTextColor
        )
    }
}



@Composable
fun OBRadioGroup(
    modifier: Modifier = Modifier,
    data: OBRadioGroupData,
    onSexChecked: (checkedItemIndex : Int)-> Unit
) {

    data.items.takeIf {
        it.isNotEmpty()
    }?.run {
        FlowRow(
            modifier = modifier,
            itemVerticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            verticalArrangement = Arrangement.SpaceBetween,
            maxItemsInEachRow = 2
        ){
            data.items.forEachIndexed { index,item ->
                OBRadioButton(
                    modifier = Modifier.wrapContentSize(),
                    label = item,
                    isChecked = index == data.checkedItemIndex,
                    isCheckable = data.disabledItemsIndexes.contains(index).not(),
                    onClick = { isChecked->
                        if (isChecked){
                            onSexChecked(index)
                        }
                    }
                )
            }
        }
    }

}


