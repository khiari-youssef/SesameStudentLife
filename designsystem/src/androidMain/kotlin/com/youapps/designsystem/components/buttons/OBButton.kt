import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.BrickRed
import com.youapps.designsystem.OBFontFamilies
import com.youapps.designsystem.RoseEbony
import com.youapps.designsystem.components.loading.OBCircularProgressBar




enum class OBButtonSize{
    Small,Medium
}


@Composable
fun OBButtonContainedPrimary(
    modifier: Modifier = Modifier,
    text : String,
    size : OBButtonSize = OBButtonSize.Medium,
    isEnabled : Boolean = true,
    isLoading : Boolean = false,
    icon: Int?=null,
    onClick: ()->Unit
) {
    OBButton(
        modifier= modifier,
        text = text,
        isLoading = isLoading,
        isEnabled = isEnabled,
        backgroundColor = RoseEbony ,
        size = size ,
        onClick = onClick,
        icon = icon
    )
}

@Composable
fun OBButtonContainedNeutral(
    modifier: Modifier = Modifier,
    text: String,
    size: OBButtonSize = OBButtonSize.Medium,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    icon: Int? =null,
    onClick: () -> Unit
) {
    OBButton(
        modifier= modifier,
        text = text,
        isLoading = isLoading,
        isEnabled = isEnabled,
        backgroundColor = Color(0xFFb3b3b3) ,
        size = size ,
        onClick = onClick,
        fontColor = Color.Black,
        icon = icon
    )
}

@Composable
fun OBButtonContainedSecondary(
    modifier: Modifier = Modifier,
    text : String,
    size : OBButtonSize = OBButtonSize.Medium,
    isEnabled : Boolean = true,
    isLoading : Boolean = false,
    icon : Int?=null,
    onClick: ()->Unit
) {
    OBButton(
        modifier= modifier,
        text = text,
        isLoading = isLoading,
        isEnabled = isEnabled,
        backgroundColor = BrickRed ,
        size = size ,
        icon = icon,
        onClick = onClick
    )
}


@Composable
fun OBButton(
    modifier: Modifier = Modifier,
    text : String,
    icon : Int?=null,
    iconDescription : String?=null,
    backgroundColor : Color,
    border : BorderStroke?=null,
    size : OBButtonSize = OBButtonSize.Medium,
    isEnabled : Boolean = true,
    isLoading : Boolean = false,
    paddingValues: PaddingValues = if (size == OBButtonSize.Small ) {
        PaddingValues(
            horizontal = 12.dp,
            vertical = 4.dp
        )
    } else PaddingValues(
        horizontal = 12.dp,
        vertical = 8.dp
    ),
    fontSize : TextUnit = 16.sp,
    fontColor : Color = Color.White,
    heightRangeDP : IntRange =  25..44,
    onClick: ()->Unit
) {
   Button(
        modifier = modifier
            .heightIn(heightRangeDP.first.dp,heightRangeDP.last.dp),
        enabled = isEnabled or isLoading,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
          containerColor = backgroundColor,
        ),
       border =border,

       contentPadding = paddingValues,
        onClick = onClick
    ) {
       Crossfade(
           modifier = Modifier.height(20.dp),
           targetState = isLoading,
           label = "ButtonLoading",
           animationSpec = spring()
       ) {
           if (it){
               OBCircularProgressBar(
                   modifier = Modifier
                       .semantics {
                           contentDescription = "OBButtonLoadingCircularProgressBar"
                       }
                       .size(20.dp),
                   strokeWidth = 2.dp,
                   color = Color.White
               )
           } else {
               icon?.run {
                   Row(
                       modifier = Modifier.wrapContentSize(),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.spacedBy(2.dp,Alignment.CenterHorizontally)
                   ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = icon) ,
                            contentDescription = iconDescription,
                            tint = fontColor,
                            modifier = Modifier.size(20.dp)
                        )
                       Text(
                           text = text,
                           style = TextStyle(
                               color = fontColor,
                               fontFamily = OBFontFamilies.MainMediumFontFamily,
                               fontSize = fontSize
                           )
                       )
                   }
               } ?:  Text(
                   text = text,
                   style = TextStyle(
                       color = fontColor,
                       fontFamily = OBFontFamilies.MainMediumFontFamily,
                       fontSize = fontSize
                   )
               )

           }
       }


    }
}