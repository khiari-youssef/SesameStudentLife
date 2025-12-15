package com.youapps.onlybeans.android.ui.main

import SettingsScreen
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.youapps.designsystem.components.bars.OBBottomNavigationBarDefaults
import com.youapps.designsystem.components.dialogs.ImageViewerDialog
import com.youapps.designsystem.components.dialogs.NavigationNotFoundModal
import com.youapps.designsystem.components.lists.CarouselState
import com.youapps.designsystem.components.popups.AppExitPopup
import com.youapps.designsystem.navigateBack
import com.youapps.onlybeans.R
import com.youapps.onlybeans.android.base.NavigationRoutingData
import com.youapps.onlybeans.android.ui.home.HomeScreen
import com.youapps.onlybeans.domain.valueobjects.UserSex
import com.youapps.users_management.ui.login.LoginScreen
import com.youapps.users_management.ui.login.LoginState
import com.youapps.users_management.ui.login.LoginUIStateHolder
import com.youapps.users_management.ui.login.LoginViewModel
import com.youapps.users_management.ui.registration.InputRuleCheckState
import com.youapps.users_management.ui.registration.OBRegistrationScreen
import com.youapps.users_management.ui.registration.OBRegistrationStateHolder
import com.youapps.users_management.ui.registration.OBRegistrationViewModel
import com.youapps.users_management.ui.settings.AppSettingsStateHolder
import com.youapps.users_management.ui.settings.SettingsViewModel
import com.youapps.users_management.ui.settings.privacypolicy.PrivacyPolicyScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.net.toUri


@Composable
fun MainActivity.MainNavigation(
    modifier: Modifier = Modifier,
    rootNavController : NavHostController,
    homeDestinations : State<OBBottomNavigationBarDefaults>,
    autoLoginState : LoginState
) {
    val isAppExistPopupShown = remember {
        mutableStateOf(false)
    }
    AppExitPopup(
        isShown =isAppExistPopupShown.value,
        onConfirmAppExit = {
            this@MainNavigation.finishAffinity()
        },
        onCancelled = {
            isAppExistPopupShown.value = false
        }
    )
    if (autoLoginState is LoginState.Loading){
        return
    }
    NavHost(
        modifier = modifier,
        route = "MainGraph",
        startDestination = if (autoLoginState is LoginState.Success) "MainNavigation" else NavigationRoutingData.LOGIN,
        navController = rootNavController,
        builder = {
            composable(
                route = NavigationRoutingData.LOGIN
            ){
                val viewModel : LoginViewModel = koinViewModel()
                val loginUIState = LoginUIStateHolder.rememberLoginUIState(
                    loginEmail = rememberSaveable {
                        mutableStateOf("")
                    },
                    loginPassword = rememberSaveable {
                        mutableStateOf("")
                    },
                    loginRequestResult = viewModel.loginResultState.collectAsStateWithLifecycle()
                )
                LaunchedEffect(key1 = loginUIState.loginRequestResult.value, block = {
                    if (loginUIState.loginRequestResult.value is LoginState.Success){
                        rootNavController.navigate("MainNavigation")
                    }
                } )

                BackHandler {
                    isAppExistPopupShown.value = true
                }
                LoginScreen(
                    modifier = Modifier
                        .semantics {
                            contentDescription = "LoginScreen"
                        }
                        .fillMaxSize(),
                    loginUIStateHolder = loginUIState,
                    onEmailChanged = { email ->
                        loginUIState.loginEmail.value = email
                    },
                    onPasswordChanged ={ password ->
                        loginUIState.loginPassword.value = password
                    },
                    onSetIdleState = {
                        viewModel.setLoginIdleState()
                    },
                    onLoginClicked = {
                        viewModel.loginWithEmailAndPassword(
                            loginUIState.loginEmail.value,
                            loginUIState.loginPassword.value
                        )

                    },
                    onSignUpClicked = {
                        rootNavController.navigate(NavigationRoutingData.REGISTRATION_SCREEN)
                    }
                )

            }
            composable(
                route = "MainNavigation"
            ){ _->

                HomeScreen(
                    homeDestinations = homeDestinations.value,
                    onHomeExit = {destination->
                        when (destination){
                            NavigationRoutingData.EXIT_APP_ROUTE->{
                                isAppExistPopupShown.value = true
                            }
                            NavigationRoutingData.LOGIN->{
                                rootNavController.navigate(
                                    destination,
                                    NavOptions.Builder()
                                        .setPopUpTo(NavigationRoutingData.LOGIN,true)
                                        .build()
                                )
                            }
                            else ->{
                                rootNavController.navigate(destination)
                            }
                        }
                    }
                )
                BackHandler {
                    isAppExistPopupShown.value = true
                }
            }
            composable(
                route = NavigationRoutingData.NAVIGATION_NOT_FOUND
            ){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    NavigationNotFoundModal(
                        modifier = Modifier
                    )
                }
            }
            composable(
                route = NavigationRoutingData.EDIT_PROFILE_SCREEN
            ){

                val viewModel : OBRegistrationViewModel by viewModel<OBRegistrationViewModel>()

                val screenState : OBRegistrationStateHolder = OBRegistrationStateHolder.rememberOBRegistrationState(
                    profileDescription = viewModel.getProfileDescription().collectAsStateWithLifecycle(initialValue =  InputRuleCheckState.Initial),
                    profilePicture = viewModel.getProfilePicture().collectAsStateWithLifecycle(initialValue = null),
                    profileStatus = viewModel.getProfileStatus().collectAsStateWithLifecycle(initialValue =  InputRuleCheckState.Initial),
                    coverPicture = viewModel.getCoverPicture().collectAsStateWithLifecycle(initialValue = null),
                    firstName = viewModel.getFistName().collectAsStateWithLifecycle(initialValue = null),
                    lastName= viewModel.getLastName().collectAsStateWithLifecycle(initialValue = null),
                    userSex = viewModel.getUserSex().collectAsStateWithLifecycle(initialValue = null),
                    email = viewModel.getEmail().collectAsStateWithLifecycle(initialValue = null),
                    country = viewModel.getCountry().collectAsStateWithLifecycle(initialValue = InputRuleCheckState.Initial),
                    city = viewModel.getCity().collectAsStateWithLifecycle(initialValue =  InputRuleCheckState.Initial),
                    location = viewModel.getLocation().collectAsStateWithLifecycle(initialValue = null),
                    phone = viewModel.getPhone().collectAsStateWithLifecycle(initialValue =  InputRuleCheckState.Initial),
                    countriesListData = viewModel.getCountriesList().collectAsStateWithLifecycle(initialValue = null),
                    citiesListData =viewModel.getCitiesList().collectAsStateWithLifecycle(initialValue = null),
                    coffeeSpaceCarouselState = viewModel.getCoffeeSpaceCarouselImages().collectAsStateWithLifecycle(initialValue = CarouselState.Loaded(emptyList())),
                    countryCodesDropDownMenuData = viewModel.countryCodesDropDownMenuDataStateFlow.collectAsStateWithLifecycle(initialValue = null),
                    selectedCountryCode = viewModel.getSelectedPhonePrefix().collectAsStateWithLifecycle(initialValue = null),
                    link = viewModel.getProfileLink().collectAsStateWithLifecycle(initialValue = InputRuleCheckState.Initial),
                    keywords = viewModel.getProfileKeywordsList().collectAsStateWithLifecycle(initialValue = null)
                )
                val currentContext = LocalContext.current

                val coverPicturePicker  = rememberLauncherForActivityResult(
                    ActivityResultContracts.PickVisualMedia()
                ) { uri ->
                    if (uri != null) {
                        viewModel.updateCoverPicture(uri = uri.toString())
                    } else {
                        Toast.makeText(currentContext,currentContext.getString(R.string.image_picker_error_message) , Toast.LENGTH_SHORT).show()
                    }
                }
                val profilerPicturePicker  = rememberLauncherForActivityResult(
                    ActivityResultContracts.PickVisualMedia()
                ) { uri ->
                    if (uri != null) {
                        viewModel.updateProfilePicture(uri = uri.toString())
                    } else {
                        Toast.makeText(currentContext,currentContext.getString(R.string.image_picker_error_message) , Toast.LENGTH_SHORT).show()
                    }
                }
                val galleryPicturePicker  = rememberLauncherForActivityResult(
                    ActivityResultContracts.PickMultipleVisualMedia()
                ) { uris ->
                    uris.takeIf { it.isNotEmpty() }?.run {
                         val newImages = uris.map { it.toString() }
                        val currentImages = screenState.coffeeSpaceCarouselState.value.images
                            viewModel.updateCoffeeSpaceCarouselImages(
                                coffeeSpaceImages = currentImages + newImages
                            )
                    } ?: run {
                        Toast.makeText(currentContext,currentContext.getString(R.string.image_picker_error_message) , Toast.LENGTH_SHORT).show()
                    }
                }

                val imageViewerContent : MutableState<String?> = remember {
                    mutableStateOf(null)
                }
                ImageViewerDialog(
                    imageUrl = imageViewerContent.value ?: "" ,
                    isVisible = imageViewerContent.value != null,
                    onDismissRequest = {
                        imageViewerContent.value = null
                    }
                )

                OBRegistrationScreen(
                    screenUpdateState = screenState,
                    onSexChecked = { checkedItemIndex ->
                            when(checkedItemIndex){
                                0 -> viewModel.onSexChecked(UserSex.Male)
                                1 -> viewModel.onSexChecked(UserSex.Female)
                           }
                    },
                    onCoverPictureClicked = {
                        coverPicturePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly), options = ActivityOptionsCompat
                            .makeBasic()
                        )
                    },
                    onProfilePictureClicked = {
                        profilerPicturePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly), options = ActivityOptionsCompat
                            .makeBasic()
                        )
                    },
                    onProfileDescriptionChanged = viewModel::updateProfileDescription,
                    onStatusChanged = viewModel::updateStatus,
                    onCountrySelected = viewModel::updateCountry,
                    onCitySelected = viewModel::updateCity,
                    onGalleryItemDeleted = viewModel::deleteCoffeeSpaceCarouselImage,
                    onGalleryItemClicked = { url->
                        imageViewerContent.value = url
                    },
                    onGalleryItemAdd = {
                        val allowedImagesToAdd = 5 - screenState.coffeeSpaceCarouselState.value.images.size
                        if (allowedImagesToAdd > 0){
                            galleryPicturePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly, maxItems = allowedImagesToAdd), options = ActivityOptionsCompat.makeBasic())
                        } else {
                            Toast.makeText(currentContext,"you can't add more",Toast.LENGTH_LONG).show()
                        }
                    },
                    onExit = {
                        rootNavController.popBackStack()
                    },
                    onPhoneNumberChanged = viewModel::updatePhoneNumber,
                    onCountryCodeChanged = viewModel::setSelectedCountryPrefix,
                    onRequestDropDownRefresh = {
                        viewModel.loadCountriesMenuNextPage(
                            offset = screenState.countryCodesDropDownMenuData.value?.items?.size ?: 0
                        )
                    },
                    onDropDownDismissed = {
                        viewModel.loadCountriesMenuNextPage(
                            offset = 0,
                            withRefresh = true
                        )
                    },
                    onLinkChanged = {link->
                        viewModel.setProfileLink(link)
                    },
                    onValidLinkClicked = { link->
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, link.toUri())
                        startActivity(browserIntent)
                    },
                    onKeywordAdded = { keyword->
                        viewModel.addNewProfileKeyword(keyword)
                    },
                    onKeyWordDeleted = {keyword->
                        viewModel.deleteProfileKeyword(keyword)
                    }
                )

            }
            composable(
                route = NavigationRoutingData.VIEW_SCREEN_PRODUCT
            ){

            }
            composable(
                route = NavigationRoutingData.REGISTRATION_SCREEN
            ){
                //OBRegistrationScreen()
            }
            composable(
                route = NavigationRoutingData.PRIVACY_POLICY_SCREEN
            ){
                PrivacyPolicyScreen(
                    onBackPressed = {
                        rootNavController.navigateBack()
                    }
                )
            }
            composable(
                route = NavigationRoutingData.SETTINGS
            ){
                val viewModel : SettingsViewModel = koinViewModel()
                val uiState = AppSettingsStateHolder
                    .rememberAppSettingsState(
                        isAutoLoginEnabled = remember {  mutableStateOf(false) }
                    )
                SettingsScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    uiState = uiState,
                    onItemSelectedStateChanged ={

                    },
                    onBackPressed = {
                        rootNavController.navigateBack()
                    }
                )
            }

        }
    )

}