package com.youapps.designsystem.components.popups

import DualButtonPopup
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.youapps.designsystem.R

@Composable
fun LogoutPopup(
    isShown : Boolean,
   onConfirmAppExit : ()->Unit,
   onCancelled : ()->Unit
) {
    DualButtonPopup(
        isShown =isShown,
        title = stringResource(id = R.string.app_logout_popup_title),
        onDismissRequest = onCancelled,
        onNegativeButtonClicked = onCancelled,
        onPositiveButtonClicked =onConfirmAppExit,
        positiveButtonText = stringResource(id = R.string.yes),
        negativeButtonText = stringResource(id = R.string.no)
    )
}