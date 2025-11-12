package com.youapps.designsystem.components.templates

import OBButton
import OBButtonContainedNeutral
import OBButtonContainedSecondary
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.R as ds

@Composable
fun  OBFormPage(
    modifier: Modifier = Modifier,
    onNextStep: (() -> Unit)?=null,
    onPreviousStep: (()-> Unit)?=null,
    onSaveChanges : (()-> Unit)?=null,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        content()
        onPreviousStep?.run {
            Box(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        top = 8.dp
                    )
                    .fillMaxSize(),
               contentAlignment = Alignment.Center
            ){
                Icon(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.primaryContainer , shape = CircleShape)
                        .clip(CircleShape)
                        .padding(8.dp)
                        .clickable(onClick = onPreviousStep)
                        .align(Alignment.TopStart),
                    imageVector = ImageVector.vectorResource(ds.drawable.ic_back),
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = ""
                )
            }

        }
        Row(
         modifier = Modifier
             .padding(
                 bottom = 16.dp,
             )
             .padding(
                 horizontal = 16.dp
             )
             .align(Alignment.BottomCenter)
             .fillMaxWidth(),
         verticalAlignment = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            onSaveChanges?.run {
                OBButtonContainedSecondary(
                    modifier = Modifier
                        .weight(if (onNextStep != null) 0.5f else 1f)
                        .fillMaxWidth(),
                    text = stringResource(ds.string.profile_save),
                    onClick = onSaveChanges
                )
            }
            onNextStep?.run {
                OBButton(
                    modifier = Modifier
                        .weight(if (onSaveChanges != null) 0.5f else 1f)
                        .fillMaxWidth(),
                    text = stringResource(ds.string.next_step),
                    backgroundColor = Color.Black,
                    fontColor = Color.White,
                    onClick = onNextStep
                )
            }
        }
    }
}