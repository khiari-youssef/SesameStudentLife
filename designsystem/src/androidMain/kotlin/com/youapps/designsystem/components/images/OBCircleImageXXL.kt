package com.youapps.designsystem.components.images

import OBCircleImage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OBCircleImageXXL(
    modifier: Modifier = Modifier,
    uri : Any,
    placeholderRes : Int,
    errorRes : Int,
) {
    OBCircleImage(
        modifier = modifier,
        uri = uri,
        placeholderRes = placeholderRes,
        errorRes = errorRes,
        size = 144.dp
    )
}

