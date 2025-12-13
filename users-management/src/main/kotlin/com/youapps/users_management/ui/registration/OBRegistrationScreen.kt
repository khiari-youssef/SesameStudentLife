package com.youapps.users_management.ui.registration
import InfoPopup
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.menus.DropDownMenuItemData
import com.youapps.designsystem.components.templates.OBFormPage
import com.youapps.users_management.R
import com.youapps.users_management.ui.registration.steps.RegistrationFormCoffeeSpaceSection
import com.youapps.users_management.ui.registration.steps.RegistrationFormGeneralSection
import kotlinx.coroutines.launch
import com.youapps.designsystem.R as ds


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OBRegistrationScreen(
    modifier: Modifier = Modifier,
    screenUpdateState : OBRegistrationStateHolder,
    onSexChecked: (checkedItemIndex: Int)-> Unit,
    onProfilePictureClicked: ()-> Unit,
    onCoverPictureClicked: ()-> Unit,
    onStatusChanged: (status : String)-> Unit,
    onProfileDescriptionChanged: (profile : String)-> Unit,
    onCountrySelected : (String)-> Unit,
    onCitySelected : (String)-> Unit,
    onGalleryItemAdd: ()-> Unit,
    onGalleryItemClicked : ((String)-> Unit)?=null,
    onGalleryItemDeleted: (Int)-> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onCountryCodeChanged: (DropDownMenuItemData) -> Unit,
    onRequestDropDownRefresh: ()-> Unit,
    onDropDownDismissed: ()-> Unit,
    onLinkChanged: (link: String) -> Unit,
    onValidLinkClicked : (link: String) -> Unit,
    onExit : ()-> Unit
) {
    val pagerState = rememberPagerState(pageCount = {
        3
    })

    val pagerCoroutineScope = rememberCoroutineScope()

    val infoPopupContent : MutableState<String?> = remember {
        mutableStateOf(null)
    }

    InfoPopup(
        title = infoPopupContent.value ?: "",
        onDismissRequest = {
            infoPopupContent.value = null
        },
        isShown = infoPopupContent.value != null,
        buttonText = stringResource(ds.string.ok),
        onButtonClicked = {
            infoPopupContent.value = null
        }
    )
    val missingProfilePicture = stringResource(R.string.profile_missing_profile_picture)
    val missingCoverPicture =stringResource(R.string.profile_missing_cover_picture)


    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        userScrollEnabled = false,
        pageContent = {step->
            OBFormPage(
                modifier = Modifier,
                title = if (step == 1) stringResource(R.string.profile_coffee_space) else null,
                canSubmitChanges = screenUpdateState.isFormReadyToSubmitState().value,
                content = { padding->
                    when(step) {
                        0 -> {
                            val scrollState = rememberScrollState()
                            RegistrationFormGeneralSection(
                                modifier = Modifier
                                    .verticalScroll(scrollState),
                                screenState = screenUpdateState,
                                onSexChecked = onSexChecked,
                                onStatusChanged = onStatusChanged,
                                onProfilePictureClicked = onProfilePictureClicked,
                                onCoverPictureClicked = onCoverPictureClicked,
                                onProfileDescriptionChanged = onProfileDescriptionChanged,
                                onCitySelected = onCitySelected,
                                onCountrySelected = onCountrySelected,
                                onPhoneNumberChanged = onPhoneNumberChanged,
                                onCountryCodeChanged = onCountryCodeChanged,
                                onRequestDropDownRefresh = onRequestDropDownRefresh,
                                onDropDownDismissed = onDropDownDismissed,
                                onLinkChanged = onLinkChanged,
                                onValidLinkClicked = onValidLinkClicked
                            )
                        }
                        1 -> {
                            val scrollState = rememberScrollState()
                            RegistrationFormCoffeeSpaceSection(
                                modifier = Modifier
                                    .padding(padding)
                                    .padding(
                                        horizontal = 12.dp
                                    )
                                    .verticalScroll(scrollState),
                                screenState = screenUpdateState,
                                onGalleryItemAdd = onGalleryItemAdd,
                                onGalleryItemClicked = onGalleryItemClicked,
                                onGalleryItemDeleted = onGalleryItemDeleted
                            )
                        }
                        2->{

                        }
                    }

                },
                onNextStep =
                    {
                        if (pagerState.canScrollForward){
                            pagerCoroutineScope.launch {
                                pagerState.scrollToPage(step+1)
                            }
                        }
                    }
                ,
                onPreviousStep = {
                    if (pagerState.canScrollBackward){
                        pagerCoroutineScope.launch {
                            pagerState.scrollToPage(step-1)
                        }
                    } else {
                        onExit()
                    }
                },
                onSubmit = {
                 if(screenUpdateState.profilePicture.value.isNullOrBlank()) {
                     infoPopupContent.value = missingProfilePicture
                 } else if (screenUpdateState.coverPicture.value.isNullOrBlank()) {
                     infoPopupContent.value = missingCoverPicture
                 } else {

                 }
                }
            )
        }
    )



}

