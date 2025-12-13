package com.youapps.onlybeans.android.ui.notifications

import OBCircleImageM
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.OBFontFamilies
import com.youapps.designsystem.R
import com.youapps.designsystem.SuccessColor
import com.youapps.designsystem.components.loading.shimmerEffect
import com.youapps.onlybeans.domain.entities.OBNotification


@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    oBNotification: OBNotification?,
    onProjectReferenceClicked : (ref: String)->Unit,
    builder: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = modifier
            .defaultMinSize(
                minHeight = 56.dp
            )
            .shimmerEffect(oBNotification == null)
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
            oBNotification?.run {
                withStyle(
                    SpanStyle(
                        fontFamily = OBFontFamilies.MainBoldFontFamily,
                        fontWeight = FontWeight.W700
                    )
                ) {
                    append(oBNotification.senderFullName)
                }
                append(" sent this notification")
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = "SignUpActionText",
                        styles = TextLinkStyles( SpanStyle(
                            fontFamily = OBFontFamilies.MainBoldFontFamily,
                            fontWeight = FontWeight.W700,
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline
                        )),
                        linkInteractionListener = {
                            onProjectReferenceClicked(oBNotification.projectRef)
                        },
                    )
                ){
                    append(oBNotification.projectRef)
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
            OBCircleImageM(
                uri = oBNotification?.senderImage ?: "",
                placeholderRes = R.drawable.profile_placeholder,
                errorRes = R.drawable.profile_placeholder
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = notificationContent,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = OBFontFamilies.MainRegularFontFamily,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.onBackground,
                )
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
    onProjectReferenceClicked : (ref : String)->Unit,
    oBNotification: OBNotification.OBRequestNotification?
) {
    NotificationItem(
        modifier = modifier,
        oBNotification = oBNotification,
        onProjectReferenceClicked = onProjectReferenceClicked,
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
                        fontFamily = OBFontFamilies.MainBoldFontFamily,
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
                        fontFamily = OBFontFamilies.MainBoldFontFamily,
                        fontWeight = FontWeight(700),
                        color = MaterialTheme.colorScheme.error,
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
    onProjectReferenceClicked : (ref : String)->Unit,
    OBNotification: OBNotification.OBResponseNotification?
) {
    NotificationItem(
        modifier = modifier,
        oBNotification = OBNotification,
        onProjectReferenceClicked = onProjectReferenceClicked,
        builder = OBNotification?.run {
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
                        tint = if (isAccepted) SuccessColor else MaterialTheme.colorScheme.error
                    )
                    Text(
                        modifier = Modifier
                            .clickable {

                            }
                            .padding(8.dp)
                            .wrapContentSize(),
                        text = stringResource(
                            id = if (OBNotification.isAccepted) R.string.accepted else R.string.rejected
                        ),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = OBFontFamilies.MainBoldFontFamily,
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
