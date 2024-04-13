package tn.sesame.designsystem.components.menus


import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.LightGreyBlue
import tn.sesame.designsystem.SesameFontFamilies

@Stable
data class MenuOption(
    val id : String,
    val iconRes : Int,
    val label : String
)

@Stable
@JvmInline
value class MenuOptions(
    val options : List<MenuOption>
)



@Composable
fun OptionsListMenu(
    modifier: Modifier = Modifier,
    menuOptions : MenuOptions,
    onOptionClicked : (optionIndex : Int)->Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val itemColor = if (isSystemInDarkTheme()) LightGreyBlue else MaterialTheme.colorScheme.secondary
        menuOptions.options.forEachIndexed { index, option->
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable {
                        onOptionClicked(index)
                    },
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(option.iconRes) ,
                    contentDescription = "",
                    tint = itemColor
                )
                Text(
                    text = option.label,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = SesameFontFamilies.MainMediumFontFamily,
                        fontWeight = FontWeight(500),
                        color = itemColor,
                    )
                )
            }
        }
    }
}