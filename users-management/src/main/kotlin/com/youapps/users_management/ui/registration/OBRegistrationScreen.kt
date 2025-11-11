package com.youapps.users_management.ui.registration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun OBRegistrationScreen(
    modifier: Modifier = Modifier,
    screenUpdateState : OBRegistrationStateHolder,
    onProfilePictureClicked: ()-> Unit,
    onCoverPictureClicked: ()-> Unit,
    onStatusChanged: (status : String)-> Unit,
    onProfileDescriptionChanged: (profile : String)-> Unit
) {
    RegistrationForm(
        modifier = modifier,
        screenState = screenUpdateState,
        onStatusChanged = onStatusChanged,
        onProfilePictureClicked = onProfilePictureClicked,
        onCoverPictureClicked = onCoverPictureClicked,
        onProfileDescriptionChanged = onProfileDescriptionChanged
    )
}

