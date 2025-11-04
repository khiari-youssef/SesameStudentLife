package com.youapps.designsystem.components.dialogs

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.youapps.designsystem.components.images.OBCoverPhoto


@Composable
fun ImageViewerDialog(
    imageUrl : String,
    isVisible : Boolean,
    onDismissRequest: ()-> Unit
) {
    if (isVisible) {
        Dialog(
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            ),
            onDismissRequest = onDismissRequest
        ){
            OBCoverPhoto(
                modifier = Modifier.wrapContentSize(),
                url = imageUrl
            )
        }
    }
}