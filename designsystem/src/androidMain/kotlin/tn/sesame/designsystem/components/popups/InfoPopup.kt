import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.onBackgroundShadedDarkMode
import tn.sesame.designsystem.onBackgroundShadedLightMode

@Composable
fun InfoPopup(
    title : String,
    subtitle : String?=null,
    isShown : Boolean,
    buttonText : String,
    onButtonClicked : ()->Unit,
    onDismissRequest : ()->Unit
) {
    if (isShown){
        Dialog(
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(
                        top = 16.dp
                    )
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp
                        )
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        text = title,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontFamily = SesameFontFamilies.MainMediumFontFamily,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    subtitle?.run {
                        Text(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth(),
                            text = subtitle,
                            style = TextStyle(
                                color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                                fontFamily = SesameFontFamilies.MainMediumFontFamily,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        )
                    }

                }
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextButton(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        contentPadding = PaddingValues(8.dp),
                        onClick = onButtonClicked
                    ) {
                        Text(
                            text = buttonText,
                            style = TextStyle(
                                color = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary.copy(
                                    red = 0.2f
                                ) else MaterialTheme.colorScheme.primary,
                                fontFamily = SesameFontFamilies.MainMediumFontFamily,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            ),
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}