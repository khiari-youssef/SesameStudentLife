package com.youapps.users_management.ui.registration.steps

import OBEmailTextField
import OBTextField
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.youapps.designsystem.components.PageSection
import com.youapps.designsystem.components.checkables.OBRadioGroup
import com.youapps.designsystem.components.checkables.OBRadioGroupData
import com.youapps.designsystem.components.images.OBCircleImageXXL
import com.youapps.designsystem.components.images.OBCoverPhoto
import com.youapps.designsystem.components.menus.DropDownMenuItemData
import com.youapps.designsystem.components.text.OBTextArea
import com.youapps.designsystem.components.textfields.LinkInputField
import com.youapps.designsystem.components.textfields.OBAutoCompleteTextField
import com.youapps.designsystem.components.textfields.OBPhoneInput
import com.youapps.onlybeans.domain.services.InputRuleType
import com.youapps.onlybeans.domain.valueobjects.UserSex
import com.youapps.onlybeans.ui.EnableLocationChip
import com.youapps.users_management.R
import com.youapps.users_management.ui.registration.InputRuleCheckState
import com.youapps.users_management.ui.registration.OBRegistrationStateHolder
import com.youapps.designsystem.R as ds


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun RegistrationFormGeneralSection(
    modifier: Modifier = Modifier,
    screenState: OBRegistrationStateHolder,
    onSexChecked: (checkedItemIndex: Int)-> Unit,
    onProfilePictureClicked: ()-> Unit,
    onCoverPictureClicked: ()-> Unit,
    onStatusChanged: (status : String)-> Unit,
    onProfileDescriptionChanged: (profile : String)-> Unit,
    onCountrySelected : (String)-> Unit,
    onCitySelected : (String)-> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onCountryCodeChanged: (DropDownMenuItemData) -> Unit,
    onRequestDropDownRefresh: ()-> Unit,
    onDropDownDismissed: ()-> Unit,
    onLinkChanged: (link: String) -> Unit,
    onValidLinkClicked : (link: String) -> Unit
) {
        Column(
            modifier = modifier,
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
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.Top)
            ){
                PageSection(
                    modifier = Modifier.fillMaxWidth(),
                    sectionTitle = stringResource(R.string.profile_general),
                ) {
                    OBTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = screenState.firstName.value ?: "",
                        label = stringResource(R.string.profile_firstName),
                        placeholder = "",
                        isEnabled = false,
                        onTextChanged = {}
                    )
                    OBTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = screenState.lastName.value ?: "",
                        label = stringResource(R.string.profile_lastName),
                        placeholder = "",
                        isEnabled = false,
                        onTextChanged = {}
                    )
                    OBEmailTextField(
                        modifier = Modifier.fillMaxWidth(),
                        isEnabled = false,
                        text = screenState.email.value ?: "",
                        onEmailChanged = {}
                    )
                    OBRadioGroup(
                        modifier = Modifier.fillMaxWidth(),
                        data = OBRadioGroupData(
                            items = listOf(
                                stringResource(R.string.profile_sex_male),
                                stringResource(R.string.profile_sex_female)
                            ),
                            checkedItemIndex = when(screenState.userSex.value){
                                UserSex.Female -> 1
                               UserSex.Male -> 0
                                else -> -1
                            },
                            disabledItemsIndexes = listOf(0,1),
                        ),
                        onSexChecked = onSexChecked
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.profile_cannot_edit),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    OBTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text =  screenState.profileStatus.value.displayContent(),
                        label = stringResource(R.string.profile_status),
                        placeholder = "",
                        onTextChanged = onStatusChanged,
                        isRequired = true,
                        errorMessage = screenState.profileStatus.value.getErrorMessageForErrorType()
                    )

                    OBTextArea(
                        modifier = Modifier.fillMaxWidth(),
                        text = screenState.profileDescription.value.displayContent(),
                        label = stringResource(R.string.profile_description),
                        errorMessage = screenState.profileDescription.value.getErrorMessageForErrorType(),
                        onTextChanged = onProfileDescriptionChanged
                    )
                    Spacer(modifier = Modifier
                        .imePadding()
                    )
                }
                PageSection(
                    modifier = Modifier.fillMaxWidth(),
                    sectionTitle = stringResource(R.string.profile_address),
                ) {

                    OBAutoCompleteTextField(
                        isRequired = true,
                        modifier = Modifier.fillMaxWidth(),
                        text = screenState.country.value.displayContent(),
                        label = stringResource(R.string.profile_country),
                        placeholder = stringResource(R.string.profile_country),
                        data = screenState.countriesListData.value,
                        onValueChanged = onCountrySelected,
                        errorMessage = screenState.country.value.getErrorMessageForErrorType()
                    )
                    OBAutoCompleteTextField(
                        isRequired = true,
                        modifier = Modifier.fillMaxWidth(),
                        text = screenState.city.value.displayContent(),
                        label = stringResource(R.string.profile_city),
                        placeholder = stringResource(R.string.profile_city),
                        data = screenState.citiesListData.value,
                        errorMessage = screenState.city.value.getErrorMessageForErrorType(),
                        onValueChanged = onCitySelected
                    )
                    Text(
                        text = stringResource(R.string.profile_pick_exact_location),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleMedium
                    )
                    val ctx= LocalContext.current
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        EnableLocationChip(
                            onLocationEnabled = {
                                Toast.makeText(ctx, "enabled", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                    screenState.countryCodesDropDownMenuData.value?.let {data ->
                        val dropDownScrollState = rememberScrollState()
                        LaunchedEffect(key1 = dropDownScrollState.canScrollForward) {
                            if (dropDownScrollState.canScrollForward.not()) {
                                 onRequestDropDownRefresh()
                            }
                        }

                        OBPhoneInput(
                            modifier = Modifier.fillMaxWidth(),
                            phoneNumber = screenState.phone.value.displayContent(),
                            errorMessage = screenState.phone.value.getErrorMessageForErrorType(),
                            countryCodesDropDownMenuData = data,
                            onPhoneNumberChanged = onPhoneNumberChanged,
                            onCountryCodeChanged = onCountryCodeChanged,
                            selectedCountryCode = screenState.selectedCountryCode,
                            dropDownScrollState = dropDownScrollState,
                            onDropDownDismissed = onDropDownDismissed
                        )
                    }
                    LinkInputField(
                        modifier = Modifier.fillMaxWidth(),
                        link = screenState.link.value.displayContent(),
                        errorMessage = screenState.link.value.getErrorMessageForErrorType(),
                        onLinkChanged =onLinkChanged,
                        onValidLinkClicked = onValidLinkClicked
                    )
                    Spacer(
                        modifier = Modifier
                            .height(400.dp)
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
                )
                .fillMaxWidth()
                .constrainAs(coverImageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            url = coverPictureUri
        )
        OBCircleImageXXL(
            modifier = Modifier
                .clickable(onClick = onProfilePictureClicked)
                .constrainAs(profileAvatarPicture) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(coverImageRef.bottom, (-72).dp)
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


fun InputRuleCheckState.displayContent(defaultValue : String = "") : String = when(this){
    is InputRuleCheckState.Initial -> defaultValue
    is InputRuleCheckState.Invalid -> input ?: defaultValue
    is InputRuleCheckState.Valid -> input
}

@Composable
fun InputRuleCheckState.getErrorMessageForErrorType(defaultValue : String? = null) : String? = if (this is InputRuleCheckState.Invalid){
    when(brokenRule){
        InputRuleType.REQUIRED -> stringResource(com.youapps.onlybeans.R.string.input_required_error_message)
        InputRuleType.EMAIL_FORMAT ->  stringResource(com.youapps.onlybeans.R.string.input_email_format_error_message)
        InputRuleType.PHONE_FORMAT ->  stringResource(com.youapps.onlybeans.R.string.input_phone_format_error_message)
        InputRuleType.PASSWORD_POLICY ->  stringResource(com.youapps.onlybeans.R.string.input_password_policy_error_message)
        InputRuleType.LETTERS_ONLY ->  stringResource(com.youapps.onlybeans.R.string.input_letters_only_error_message)
        InputRuleType.NUMBERS_ONLY ->  stringResource(com.youapps.onlybeans.R.string.input_digits_only_error_message)
        InputRuleType.DATE_FORMAT ->  stringResource(com.youapps.onlybeans.R.string.input_date_formats_error_message)
        InputRuleType.MIN_LENGTH ->  stringResource(com.youapps.onlybeans.R.string.text_area_min_characters_error_message)
        InputRuleType.MAX_LENGTH ->  stringResource(com.youapps.onlybeans.R.string.text_area_max_characters_error_message)
        InputRuleType.LINK_FORMAT -> stringResource(com.youapps.onlybeans.R.string.input_link_format_error_message)
    }
} else defaultValue