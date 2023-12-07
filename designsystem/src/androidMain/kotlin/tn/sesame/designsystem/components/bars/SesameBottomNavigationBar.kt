package tn.sesame.designsystem.components.bars

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import tn.sesame.designsystem.AliceBlue


data class SesameBottomNavigationBarItem(
 val selectedStateIcon  : Int,
 val unSelectedStateIcon  : Int
)
@JvmInline
@Stable
value class SesameBottomNavigationBarDefaults(
   val items : List<SesameBottomNavigationBarItem>
)

@Composable
fun SesameBottomNavigationBar(
  selectedItemIndex : Int,
  modifier: Modifier = Modifier,
  properties : SesameBottomNavigationBarDefaults,
  onItemSelected : (index : Int)->Unit
) {
    val allowedItems = properties.items.take(5)
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colorScheme.surface
    )  {
        allowedItems.forEachIndexed { index, item ->
            BottomNavigationItem(
                modifier = Modifier
                    .background(
                        if (selectedItemIndex == index) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
                    ),
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.run {
                            if (selectedItemIndex == index) {
                                selectedStateIcon
                            } else unSelectedStateIcon
                        }),
                        "",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                },
                selected = selectedItemIndex == index,
                onClick = { onItemSelected(index) }
            )

        }
    }

}