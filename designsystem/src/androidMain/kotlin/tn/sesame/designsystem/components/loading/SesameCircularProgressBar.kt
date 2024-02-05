package tn.sesame.designsystem.components.loading

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun SesameCircularProgressBar(
    modifier: Modifier = Modifier,
    color : Color,
    strokeWidth : Dp = 1.dp
) {
    CircularProgressIndicator(
        modifier = modifier,
        strokeWidth = strokeWidth,
        color = color
    )
}