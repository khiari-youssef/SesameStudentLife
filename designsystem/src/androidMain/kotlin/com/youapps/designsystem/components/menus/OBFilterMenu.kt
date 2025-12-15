package com.youapps.designsystem.components.menus

import OBTextField
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.youapps.designsystem.DisabledOnSurfaceColorLightMode
import com.youapps.designsystem.OBFontFamilies
import com.youapps.designsystem.R
import com.youapps.designsystem.components.text.PlaceholderText

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


@Stable
data class KeywordsData(
    val keywords : List<String>
)

@Composable
fun OBKeywordsList(
    modifier: Modifier = Modifier,
    data : KeywordsData?,
    onKeyWordClicked: ((String)-> Unit)?=null,
    onKeyWordDeleted: ((String)-> Unit)?=null
) {
    if (data?.keywords?.isNotEmpty() == true) {
        FlowRow(
            modifier = modifier
                .fillMaxWidth(),
            maxLines = 3,
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            itemVerticalAlignment = Alignment.CenterVertically
        ) {
            val borderShape = RoundedCornerShape(8.dp)

            data.keywords.forEach { item ->
                var isDeleteOptionEnabled by remember(item) {
                    mutableStateOf(false)
                }
                Box(
                    modifier = Modifier
                        .combinedClickable(
                            onClick = {
                                if(isDeleteOptionEnabled) {
                                    isDeleteOptionEnabled = false
                                } else {
                                    onKeyWordClicked?.invoke(item)
                                }
                            },
                            onLongClick = {
                                isDeleteOptionEnabled = true
                            }
                        )
                        .background(color = MaterialTheme.colorScheme.primary, shape = borderShape)
                        .clip(borderShape)
                        .wrapContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .padding(
                                vertical = 4.dp,
                                horizontal = 12.dp
                            )
                            .wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp,Alignment.CenterHorizontally),
                    ) {
                        Text(
                            modifier = Modifier.wrapContentSize(),
                            text = item,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = OBFontFamilies.MainMediumFontFamily,
                                color = Color.White
                            ),
                            textAlign = TextAlign.Center
                        )
                        AnimatedContent(
                            targetState = isDeleteOptionEnabled,
                        ) { isEnabled->
                            if (isEnabled){
                                Row(modifier = Modifier.wrapContentSize(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp,Alignment.CenterHorizontally)
                                ) {
                                    IconButton(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(
                                                color =  MaterialTheme.colorScheme.error,
                                                shape = CircleShape
                                            )
                                            .clip(CircleShape)
                                            .padding(2.dp),
                                        onClick = {
                                            onKeyWordDeleted?.invoke(item)
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            imageVector = ImageVector.vectorResource(R.drawable.ic_remove),
                                            contentDescription = stringResource(R.string.content_description_remove_keyword_button,item),
                                            tint = Color.White
                                        )
                                    }
                                    IconButton(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(
                                                color =  Color.White,
                                                shape = CircleShape
                                            )
                                            .clip(CircleShape)
                                            .padding(2.dp),
                                        onClick = {
                                              isDeleteOptionEnabled = false
                                        }
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(24.dp),
                                            imageVector = ImageVector.vectorResource(R.drawable.ic_cross),
                                            contentDescription = stringResource(R.string.content_description_remove_cancel_keyword_button),
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }


                }
            }
        }
    }

}


@Composable
fun OBKeywordsListInput(
    modifier: Modifier = Modifier,
    label : String,
    keywords: KeywordsData?,
    maxKeywords : Int = 10,
    onKeywordAdded: (String)-> Unit,
    onKeyWordDeleted: (String)-> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterVertically)
    ) {
        val currentInputText =remember {
            mutableStateOf("")
        }
        val isEnabled = (keywords?.keywords?.size ?: 0) < maxKeywords
        ConstraintLayout(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            val (inputLabel,inputField,addButton) = createRefs()
            Text(
                modifier = Modifier.constrainAs(inputLabel){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = if (isEnabled) MaterialTheme.colorScheme.onSurface else Color(0xFFB3B3B3)
                )
            )
            OutlinedTextField(
                modifier = Modifier
                    .constrainAs(inputField){
                        start.linkTo(parent.start)
                        end.linkTo(addButton.start)
                        top.linkTo(inputLabel.bottom,4.dp)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    }
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Normal,
                    fontFamily = OBFontFamilies.MainRegularFontFamily,
                    letterSpacing = 1.sp,
                    lineHeight = 24.sp
                ),
                value = currentInputText.value,
                keyboardActions= KeyboardActions(),
                placeholder = {
                    PlaceholderText(
                        text = label,
                        fontSize = 14.sp
                    )
                },
                onValueChange =  {
                    currentInputText.value = it
                }
            )
            Box(
                modifier = Modifier.constrainAs(addButton){
                    start.linkTo(inputField.end,8.dp)
                    end.linkTo(parent.end)
                    top.linkTo(inputField.top)
                    bottom.linkTo(inputField.bottom)
                },
               contentAlignment = Alignment.Center
            ){
                IconButton(
                    enabled =isEnabled ,
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = if (isEnabled) MaterialTheme.colorScheme.primary else DisabledOnSurfaceColorLightMode,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .padding(2.dp),
                    onClick = {
                        if(currentInputText.value.isBlank().not()){
                            onKeywordAdded(currentInputText.value)
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_add),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }

        }
        OBKeywordsList(
            modifier = Modifier.fillMaxWidth(),
            data = keywords,
            onKeyWordDeleted = onKeyWordDeleted
        )
        AnimatedVisibility(
            visible = isEnabled.not()
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Max 10 keywords",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
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