package tn.sesame.designsystem.components.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import tn.sesame.designsystem.Alabaster
import tn.sesame.designsystem.AliceBlue
import tn.sesame.designsystem.Charcoal2
import tn.sesame.designsystem.LightGreyBlue


data class SesameBottomNavigationBarItem(
 val selectedStateIcon  : Int,
 val unSelectedStateIcon  : Int
)
@JvmInline
@Stable
value class SesameBottomNavigationBarDefaults(
   val items : List<SesameBottomNavigationBarItem>
){
    companion object{
        @Composable
        fun getDefaultConfiguration() = SesameBottomNavigationBarDefaults(
            listOf(
                SesameBottomNavigationBarItem(
                    selectedStateIcon = tn.sesame.designsystem.R.drawable.ic_calendar,
                    unSelectedStateIcon = tn.sesame.designsystem.R.drawable.ic_calendar_outlined
                ),
                SesameBottomNavigationBarItem(
                    selectedStateIcon = tn.sesame.designsystem.R.drawable.ic_project,
                    unSelectedStateIcon = tn.sesame.designsystem.R.drawable.ic_project_outlined
                ),
                SesameBottomNavigationBarItem(
                    selectedStateIcon = tn.sesame.designsystem.R.drawable.ic_notifications,
                    unSelectedStateIcon = tn.sesame.designsystem.R.drawable.ic_notifications_outlined
                ),
                SesameBottomNavigationBarItem(
                    selectedStateIcon = tn.sesame.designsystem.R.drawable.ic_profile,
                    unSelectedStateIcon = tn.sesame.designsystem.R.drawable.ic_profile_outlined
                )
            )
        )
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
    val unSelectedBottomNavigationColor = if (isSystemInDarkTheme()) Charcoal2 else Alabaster
    val selectedNavigationBarItemColor = if (isSystemInDarkTheme()) Charcoal2.copy(
        green = Charcoal2.green +0.04f,
        blue = Charcoal2.blue+0.05f
    ) else AliceBlue
    BottomNavigation(
        modifier = modifier,
        backgroundColor = unSelectedBottomNavigationColor
    )  {
        allowedItems.forEachIndexed { index, item ->
            BottomNavigationItem(
                modifier = Modifier
                    .background(
                        if (selectedItemIndex == index)
                           selectedNavigationBarItemColor
                        else unSelectedBottomNavigationColor
                    ),
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.run {
                            if (selectedItemIndex == index) {
                                selectedStateIcon
                            } else unSelectedStateIcon
                        }),
                        "",
                        tint = if (isSystemInDarkTheme()) LightGreyBlue else MaterialTheme.colorScheme.secondary
                    )
                },
                selected = selectedItemIndex == index,
                onClick = { onItemSelected(index) }
            )

        }
    }
}