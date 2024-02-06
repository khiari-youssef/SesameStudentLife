package tn.sesame.designsystem.components.modals

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.R
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.onBackgroundShadedDarkMode
import tn.sesame.designsystem.onBackgroundShadedLightMode

@Preview
@Composable
fun NavigationNotFoundModal(
    modifier: Modifier = Modifier,
    title : String = stringResource(id = R.string.error_destination_not_found_title),
    details : String = stringResource(id = R.string.error_destination_not_found_detail)
) {
    Card(
        modifier = Modifier
            .semantics {
                contentDescription = "NavigationDestinationNotFoundModal"
            }
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                24.dp, Alignment.CenterVertically
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    16.dp, Alignment.CenterVertically
                )
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = SesameFontFamilies.MainBoldFontFamily,
                        fontWeight = FontWeight(700),
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = details,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = SesameFontFamilies.MainMediumFontFamily,
                        fontWeight = FontWeight(500),
                        color = if (isSystemInDarkTheme()) onBackgroundShadedDarkMode else onBackgroundShadedLightMode,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_navigation_not_found) ,
                contentDescription = "",
            )
        }
    }
}