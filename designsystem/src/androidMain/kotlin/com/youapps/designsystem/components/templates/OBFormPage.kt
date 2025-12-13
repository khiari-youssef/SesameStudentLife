package com.youapps.designsystem.components.templates

import OBButton
import OBButtonContainedSecondary
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.R as ds

@Composable
fun  OBFormPage(
    modifier: Modifier = Modifier,
    title : String?=null,
    canSubmitChanges : Boolean = true,
    onNextStep: (() -> Unit)?=null,
    onPreviousStep: (()-> Unit)?=null,
    onSubmit : (()-> Unit)?=null,
    content: @Composable (PaddingValues) -> Unit
) {

    BackHandler(enabled = onPreviousStep != null) {
        onPreviousStep?.invoke()
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {

        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        )
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    onPreviousStep?.run {
                        Icon(
                            modifier = Modifier
                                .background(color = MaterialTheme.colorScheme.primaryContainer , shape = CircleShape)
                                .clip(CircleShape)
                                .padding(8.dp)
                                .clickable(onClick = onPreviousStep)
                                .weight(0.1f)
                            ,
                            imageVector = ImageVector.vectorResource(ds.drawable.ic_back),
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = stringResource(ds.string.content_description_back_button)
                        )
                        title?.takeIf { it.isNotBlank() }?.run {
                            Text(
                                modifier = Modifier.weight(0.8f),
                                text = title,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                            Spacer(
                                modifier = Modifier.weight(0.1f)
                            )
                        } ?: run {
                            Spacer(
                                modifier = Modifier.weight(0.9f)
                            )
                        }

                    } ?: run {
                        title?.takeIf { it.isNotBlank() }?.run {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = title,
                                style = MaterialTheme.typography.headlineSmall,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            },
            content = content,
            bottomBar = {
                Row(
                    modifier = Modifier
                        .padding(
                            bottom = 16.dp,
                        )
                        .padding(
                            horizontal = 16.dp
                        )
                        .imePadding()
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                ) {
                    onSubmit?.run {
                        OBButtonContainedSecondary(
                            modifier = Modifier
                                .weight(if (onNextStep != null) 0.5f else 1f)
                                .fillMaxWidth(),
                            text = stringResource(ds.string.save),
                            onClick = onSubmit,
                            isEnabled = canSubmitChanges
                        )
                    }
                    onNextStep?.run {
                        OBButton(
                            modifier = Modifier
                                .weight(if (onSubmit != null) 0.5f else 1f)
                                .fillMaxWidth(),
                            text = stringResource(ds.string.next_step),
                            backgroundColor = Color.Black,
                            fontColor = Color.White,
                            onClick = onNextStep
                        )
                    }
                }
            }
        )
    }
}