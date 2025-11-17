package com.youapps.designsystem.components.textfields

import OBTextField
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.youapps.designsystem.components.menus.DropDownMenuData
import com.youapps.designsystem.components.menus.DropDownMenuItemData
import com.youapps.designsystem.components.menus.OBDropDownMenu


@Composable
fun OBAutoCompleteTextField(
    modifier: Modifier = Modifier,
    label : String,
    placeholder : String?=null,
    text : String,
    errorMessage: String? = null,
    isRequired: Boolean = false,
    data : DropDownMenuData?=null,
    onValueChanged : (String)-> Unit,
    customFilter: ((item : DropDownMenuItemData)-> Boolean)?=null
) {
    val isExpanded = remember{
        mutableStateOf(false)
    }

    val filteredData = remember {
        mutableStateOf(data)
    }


    OBTextField(
        modifier = modifier,
        isRequired = isRequired,
        text = text,
        label = label,
        placeholder = placeholder ?: "",
        onTextChanged = { text->
            data?.items?.takeIf { it.isNotEmpty() }?.run {
                filteredData.value = filteredData.value?.copy(
                    items = this.filter(customFilter ?: {
                        it.label.contains(text)
                    })
                )
                isExpanded.value = filteredData.value?.items?.isNotEmpty() ?: false
            }
            onValueChanged(text)
        },
        errorMessage =errorMessage
    )
    filteredData.value?.run {
        OBDropDownMenu(
            modifier = Modifier.fillMaxWidth(),
            isExpanded = isExpanded.value,
            onExpandedChange = {
                isExpanded.value = it
            },
            onClick = {
                onValueChanged(it.label)
                isExpanded.value = false
            },
            data = this
        )
    }

}