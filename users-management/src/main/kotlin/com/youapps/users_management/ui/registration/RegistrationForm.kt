package com.youapps.users_management.ui.registration

import OBButtonContainedNeutral
import OBTextField
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.youapps.designsystem.components.PageSection
import com.youapps.designsystem.components.images.OBCircleImageXXL
import com.youapps.users_management.R
import com.youapps.designsystem.R as ds
import com.youapps.designsystem.components.images.OBCoverPhoto
import com.youapps.onlybeans.domain.entities.users.OBUserProfile


@Composable
fun RegistrationForm(
    modifier: Modifier = Modifier,
    screenState: OBRegistrationStateHolder,
    onProfilePictureClicked: ()-> Unit,
    onCoverPictureClicked: ()-> Unit,
    onStatusChanged: (status : String)-> Unit,
    onProfileDescriptionChanged: (profile : String)-> Unit
) {
    Box(
        modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp
                )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.Top)
        ) {
            ProfileHeader(
                modifier = Modifier.fillMaxWidth(),
                profilePictureUri = screenState.profilePicture.value,
                coverPictureUri = screenState.coverPicture.value,
                onProfilePictureClicked = onProfilePictureClicked,
                onCoverPictureClicked = onCoverPictureClicked
            )
            PageSection(
                modifier = Modifier.fillMaxWidth(),
                sectionTitle = stringResource(R.string.profile_general),
            ) {
                OBTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = screenState.firstName.value ?: "",
                    label = "First Name",
                    placeholder = "",
                    isEnabled = false,
                    isError = false,
                    isReadOnly = true,
                    onTextChanged = {

                    }
                )
            }
        }


    }

}



@Composable
private fun ProfileHeader(
    modifier: Modifier = Modifier,
    profilePictureUri : String?,
    coverPictureUri : String?,
    onProfilePictureClicked: ()-> Unit,
    onCoverPictureClicked: ()-> Unit,
) {
    ConstraintLayout(
        modifier = modifier
    ){
        val (coverImageRef,profileAvatarPicture,hint)= createRefs()
        OBCoverPhoto(
            modifier = Modifier
                .clickable(onClick = onCoverPictureClicked)
                .height(
                    height = 128.dp
                ).fillMaxWidth()
                .constrainAs(coverImageRef){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            url = coverPictureUri
        )
        OBCircleImageXXL(
            modifier = Modifier
                .clickable(onClick = onProfilePictureClicked).constrainAs(profileAvatarPicture){
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(coverImageRef.bottom,(-72).dp)
            } ,
            uri =profilePictureUri ?: "",
            placeholderRes = ds.drawable.ic_profile_placeholder_male,
            errorRes = ds.drawable.ic_profile_placeholder_male,
        )
        Text(
            modifier = Modifier
                .constrainAs(hint){
                    top.linkTo(profileAvatarPicture.bottom,16.dp)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
            text = stringResource(R.string.profile_edit_hint),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}