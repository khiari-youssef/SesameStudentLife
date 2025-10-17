package tn.sesame.designsystem.components.bars

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Badge
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.core.graphics.toColor
import tn.sesame.designsystem.Alabaster
import tn.sesame.designsystem.AliceBlue
import tn.sesame.designsystem.Charcoal2
import tn.sesame.designsystem.LightGreyBlue
import tn.sesame.designsystem.R


data class SesameBottomNavigationBarItem(
 val selectedStateIcon  : Int,
 val unSelectedStateIcon  : Int,
 val badgeContent : Int  = 0
)
@JvmInline
@Stable
value class SesameBottomNavigationBarDefaults(
   val items : List<SesameBottomNavigationBarItem>
){
    companion object{
        val DEFAULT : SesameBottomNavigationBarDefaults = SesameBottomNavigationBarDefaults(emptyList())
    }
}

@Composable
fun SesameBottomNavigationBar(
  selectedItemIndex : Int,
  modifier: Modifier = Modifier,
  properties : SesameBottomNavigationBarDefaults,
  onItemSelected : (index : Int)->Unit
) {
    val allowedItems = properties.items.take(5)
    val unSelectedBottomNavigationColor =  LocalContext.current.getColor(
        R.color.screenBackgroundColor
    ).toColor().let {
        Color(it.red(),it.green(),it.blue(),it.alpha())
    }

    val selectedNavigationBarItemColor = if (isSystemInDarkTheme()) Color(0xFF150d0d)  else Color(0xFFCFC1C1)
    BottomNavigation(
        modifier = modifier,
        backgroundColor = unSelectedBottomNavigationColor
    )  {

        allowedItems.forEachIndexed { index, item ->
            val state = animateIntAsState(
                targetValue = item.badgeContent
            )
            BottomNavigationItem(
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
                                    backgroundColor = Color(0xFFD51E1E),
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