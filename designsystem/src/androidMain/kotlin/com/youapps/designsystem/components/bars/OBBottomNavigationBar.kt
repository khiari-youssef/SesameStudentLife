package com.youapps.designsystem.components.bars

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.core.graphics.toColor
import com.youapps.designsystem.R


data class OBBottomNavigationBarItem(
 val selectedStateIcon  : Int,
 val unSelectedStateIcon  : Int,
 val badgeContent : Int  = 0
)
@JvmInline
@Stable
value class OBBottomNavigationBarDefaults(
   val items : List<OBBottomNavigationBarItem>
){
    companion object{
        val DEFAULT : OBBottomNavigationBarDefaults = OBBottomNavigationBarDefaults(emptyList())
    }
}

@Composable
fun OBBottomNavigationBar(
    selectedItemIndex : Int,
    modifier: Modifier = Modifier,
    properties : OBBottomNavigationBarDefaults,
    onItemSelected : (index : Int)->Unit
) {
    val allowedItems = properties.items.take(5)
    val unSelectedBottomNavigationColor =  LocalContext.current.getColor(
        R.color.screenBackgroundColor
    ).toColor().let {
        Color(it.red(),it.green(),it.blue(),it.alpha())
    }

    val selectedNavigationBarItemColor = if (isSystemInDarkTheme()) Color(0xFF150d0d)  else Color(0xFFCFC1C1)
    NavigationBar(
        modifier = modifier,
        contentColor = unSelectedBottomNavigationColor
    )  {

        allowedItems.forEachIndexed { index, item ->
            val state = animateIntAsState(
                targetValue = item.badgeContent
            )
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor =  if (selectedItemIndex == index)
                        selectedNavigationBarItemColor
                    else unSelectedBottomNavigationColor
                ),
                modifier = Modifier
                    .background(
                        if (selectedItemIndex == index)
                           selectedNavigationBarItemColor
                        else unSelectedBottomNavigationColor
                    ),
                icon = {
                    if (item.badgeContent > 0) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    contentColor = Color(0xFFD51E1E),
                                    content = {
                                        Text(
                                            modifier = Modifier
                                                .wrapContentSize(),
                                            text = state.value.toString(),
                                            color = Color.White
                                        )
                                    }
                                )
                            }
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(item.run {
                                    if (selectedItemIndex == index) {
                                        selectedStateIcon
                                    } else unSelectedStateIcon
                                }),
                                "",
                                tint = if (isSystemInDarkTheme()) Color(0xFFd1c6c6) else MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(item.run {
                                if (selectedItemIndex == index) {
                                    selectedStateIcon
                                } else unSelectedStateIcon
                            }),
                            "",
                            tint = if (isSystemInDarkTheme()) Color(0xFFd1c6c6) else MaterialTheme.colorScheme.primary
                        )
                    }

                },
                selected = selectedItemIndex == index,
                onClick = { onItemSelected(index) }
            )

        }
    }
}