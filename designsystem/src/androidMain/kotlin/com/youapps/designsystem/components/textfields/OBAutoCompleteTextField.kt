package com.youapps.designsystem.components.textfields

import OBTextField
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.youapps.designsystem.components.menus.DropDownMenuData
import com.youapps.designsystem.components.menus.OBDropDownMenu


@Composable
fun OBAutoCompleteTextField(
    modifier: Modifier = Modifier,
    label : String,
    placeholder : String?=null,
    text : String?=null,
    data : DropDownMenuData
) {
    val isExpanded = remember{
        mutableStateOf(false)
    }

    val filteredData = remember {
        mutableStateOf(data)
    }

    val currentText = remember {
        mutableStateOf(text ?: "")
    }

    OBTextField(
        modifier = modifier,
        text = currentText.value,
        label = label,
        placeholder = placeholder ?: "",
        onTextChanged = { text->
            filteredData.value = filteredData.value.copy(
                items = data.items.filter {
                    it.label.contains(text)
                }
            )
            currentText.value = text
            isExpanded.value = filteredData.value.items.isNotEmpty()
        }
    )
    OBDropDownMenu(
        modifier = Modifier.fillMaxWidth(),
        isExpanded = isExpanded.value,
        onExpandedChange = {
            isExpanded.value = it
        },
        onClick = {
            currentText.value = it.label
            isExpanded.value = false
        },
        data = filteredData.value
    )
}