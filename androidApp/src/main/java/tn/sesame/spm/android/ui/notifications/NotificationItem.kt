package tn.sesame.spm.android.ui.notifications

import SesameCircleImageM
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.sesame.designsystem.ErrorColor
import tn.sesame.designsystem.R
import tn.sesame.designsystem.SesameFontFamilies
import tn.sesame.designsystem.SuccessColor
import tn.sesame.designsystem.components.loading.shimmerEffect
import tn.sesame.spm.domain.entities.SesameProjectNotification


@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    sesameProjectNotification: SesameProjectNotification?,
    builder: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier
            .defaultMinSize(
                minHeight = 56.dp
            )
            .shimmerEffect(sesameProjectNotification == null)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            ),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(
            8.dp, Alignment.CenterVertically
        )
    ) {
        val notificationContent = buildAnnotatedString {
            sesameProjectNotification?.run {
                withStyle(
                    SpanStyle(
                        fontFamily = SesameFontFamilies.MainBoldFontFamily,
                        fontWeight = FontWeight.W700
                    )
                ) {
                    append(sesameProjectNotification.senderFullName)
                }
                append(" sent this notification")
                withStyle(
                    SpanStyle(
                        fontFamily = SesameFontFamilies.MainBoldFontFamily,
                        fontWeight = FontWeight.W700,
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(sesameProjectNotification.projectRef)
                }
            } ?: append("")

        }
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(
                8.dp, Alignment.Start
            )
        ) {
            SesameCircleImageM(
                uri = sesameProjectNotification?.senderImage ?: "",
                placeholderRes = tn.sesame.designsystem.R.drawable.profile_placeholder,
                errorRes = tn.sesame.designsystem.R.drawable.profile_placeholder
            )
            ClickableText(
                modifier = Modifier
                    .fillMaxWidth(),
                text = notificationContent,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = SesameFontFamilies.MainRegularFontFamily,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                onClick = { offset ->

                }
            )
        }
        builder?.let { callback ->
            callback()
        }
    }
}

@Composable
fun NotificationRequestItem(
    modifier: Modifier = Modifier,
    sesameProjectNotification: SesameProjectNotification.SesameProjectRequestNotification?
) {
    NotificationItem(
        modifier = modifier,
        sesameProjectNotification = sesameProjectNotification,
        builder = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    16.dp, Alignment.End
                )
            ) {
                Text(
                    modifier = Modifier
                        .clickable {

                        }
                        .padding(8.dp)
                        .wrapContentSize(),
                    text = "Accept",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = SesameFontFamilies.MainBoldFontFamily,
                        fontWeight = FontWeight(700),
                        color = SuccessColor,
                        textAlign = TextAlign.End
                    )
                )
                Text(
                    modifier = Modifier
                        .clickable {

                        }
                        .padding(8.dp)
                        .wrapContentSize(),
                    text = "Deny",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = SesameFontFamilies.MainBoldFontFamily,
                        fontWeight = FontWeight(700),
                        color = ErrorColor,
                        textAlign = TextAlign.End
                    )
                )
            }
        }
    )
}

@Composable
fun NotificationResponseItem(
    modifier: Modifier = Modifier,
    sesameProjectNotification: SesameProjectNotification.SesameProjectResponseNotification?
) {
    NotificationItem(
        modifier = modifier,
        sesameProjectNotification = sesameProjectNotification,
        builder = sesameProjectNotification?.run {
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            end = 8.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        4.dp, Alignment.End
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            id = if (isAccepted) R.drawable.success else R.drawable.ic_clear
                        ),
                        contentDescription = "",
                        tint = if (isAccepted) SuccessColor else ErrorColor
                    )
                    Text(
                        modifier = Modifier
                            .clickable {

                            }
                            .padding(8.dp)
                            .wrapContentSize(),
                        text = stringResource(
                            id = if (sesameProjectNotification.isAccepted) R.string.accepted else R.string.rejected
                        ),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = SesameFontFamilies.MainBoldFontFamily,
                            fontWeight = FontWeight(700),
                            color = SuccessColor,
                            textAlign = TextAlign.End
                        )
                    )
                }
            }
        }
    )
}
