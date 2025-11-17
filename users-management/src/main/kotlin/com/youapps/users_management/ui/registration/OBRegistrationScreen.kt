package com.youapps.users_management.ui.registration
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.templates.OBFormPage
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OBRegistrationScreen(
    modifier: Modifier = Modifier,
    screenUpdateState : OBRegistrationStateHolder,
    onProfilePictureClicked: ()-> Unit,
    onCoverPictureClicked: ()-> Unit,
    onStatusChanged: (status : String)-> Unit,
    onProfileDescriptionChanged: (profile : String)-> Unit,
    onExit : ()-> Unit
) {
    val pagerState = rememberPagerState(pageCount = {
        3
    })

    val pagerCoroutineScope = rememberCoroutineScope()

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        userScrollEnabled = false,
        pageContent = {step->
            val scrollState = rememberScrollState()

            OBFormPage(
                modifier = Modifier,
                canSubmitChanges = screenUpdateState.isFormReadyToSubmitState().value,
                content = {
                    when(pagerState.currentPage) {
                        0 -> RegistrationFormGeneralSection(
                            modifier = Modifier
                                .verticalScroll(scrollState),
                            screenState = screenUpdateState,
                            onStatusChanged = onStatusChanged,
                            onProfilePictureClicked = onProfilePictureClicked,
                            onCoverPictureClicked = onCoverPictureClicked,
                            onProfileDescriptionChanged = onProfileDescriptionChanged
                        )
                        1 -> {

                        }
                        2->{

                        }
                    }

                },
                onNextStep = takeUnless { step == pagerState.pageCount-1 }?.let {
                    {
                        if (pagerState.canScrollForward){
                            pagerCoroutineScope.launch {
                                pagerState.scrollToPage(pagerState.currentPage+1)
                            }
                        }
                    }
                },
                onPreviousStep = {
                    if (pagerState.canScrollBackward){
                        pagerCoroutineScope.launch {
                            pagerState.scrollToPage(pagerState.currentPage-1)
                        }
                    } else {
                        onExit()
                    }
                },
                onSaveChanges = {

                }
            )
        }
    )



}

