import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.R
import tn.sesame.designsystem.components.SesameFontFamilies


@Preview
@Composable
fun AppBrand(
    modifier: Modifier = Modifier
) {
Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp,Alignment.CenterHorizontally)
) {
    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.brand_logo) ,
        contentDescription = "",
        tint = Color.Unspecified
    )
    Column(
        modifier = Modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = stringResource(id = R.string.app_brand_text_primary),
            style = TextStyle(
                fontSize = 32.sp,
                fontFamily = SesameFontFamilies.LogoFontRegular,
                fontWeight = FontWeight.W700,
                fontStyle = FontStyle.Normal,
                color = Color(0xFF284C8E),
            )
        )
        Text(
            text = stringResource(id = R.string.app_brand_text_secondary),
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = SesameFontFamilies.LogoFontRegular,
                fontWeight = FontWeight.W400,
                fontStyle = FontStyle.Normal,
                color = Color(0xFF62BCC5),
            )
        )
    }
}
}