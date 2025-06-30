package tn.sesame.designsystem.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.SesameFontFamilies

@Composable
fun SesameIconButtonVariant(
    modifier: Modifier,
    text : String,
    iconResID : Int,
    onClick : ()->Unit
) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(12.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF3F3F3)
        ),
        onClick = onClick
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconResID) ,
            contentDescription = "",
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = SesameFontFamilies.MainMediumFontFamily,
                fontWeight = FontWeight(500),
                color = Color.Black,
            )
        )
    }
}