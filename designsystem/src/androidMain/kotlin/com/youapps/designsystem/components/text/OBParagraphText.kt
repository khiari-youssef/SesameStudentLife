package com.youapps.designsystem.components.text

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.OBFontFamilies
import java.util.Locale

sealed interface OBParagraphMode{
    data class Expandable(
        val expandActionText : String,
        val collapseActionText : String,
        val textStyle : SpanStyle
    ) : OBParagraphMode
    object None : OBParagraphMode
}

@Composable
fun  OBParagraphText(
    modifier: Modifier = Modifier,
    text : String,
    placeholderRes : Int,
    maxLines : Int = 3,
    expandMode : OBParagraphMode = OBParagraphMode.None,
) {


    val isExpandable = remember {
        mutableStateOf(expandMode is OBParagraphMode.Expandable)
    }
    val isExpanded = remember {
        mutableStateOf(false)
    }

    if (text.isBlank()) {
        PlaceholderText(
            modifier = modifier,
            text = stringResource(id = placeholderRes)
        )
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top)
        ){
            Text(
                text = buildAnnotatedString {
                    append(text.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    })
                },
                maxLines = if (expandMode is OBParagraphMode.None) maxLines else if (isExpanded.value) Int.MAX_VALUE else maxLines,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.animateContentSize(
                    animationSpec = tween(
                        500,
                        easing = LinearOutSlowInEasing
                    )
                ),
                onTextLayout = { textLayout ->
                    if (isExpanded.value) {
                        isExpandable.value = textLayout.lineCount > maxLines
                    } else {
                        isExpandable.value = textLayout.hasVisualOverflow
                    }
                },
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 20.sp,
                    fontFamily = OBFontFamilies.MainRegularFontFamily,
                    fontWeight = FontWeight(400),
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = 0.52.sp,
                    lineBreak = LineBreak.Paragraph,
                )
            )
            if (expandMode is OBParagraphMode.Expandable) {
                Text(
                    text = buildAnnotatedString {
                        withLink(
                            link = LinkAnnotation.Clickable(
                                tag = "ExpandCollapseActionText",
                                linkInteractionListener = {
                                    isExpanded.value = !isExpanded.value
                                },
                                styles = TextLinkStyles(
                                    style = expandMode.textStyle
                                )
                            ),
                        ) {
                            append(
                                if (isExpanded.value)
                                    expandMode.collapseActionText
                                else
                                    expandMode.expandActionText
                            )

                        }
                    },
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        fontFamily = OBFontFamilies.MainRegularFontFamily,
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 0.52.sp,
                        lineBreak = LineBreak.Paragraph,
                    )
                )
            }
        }
    }

}