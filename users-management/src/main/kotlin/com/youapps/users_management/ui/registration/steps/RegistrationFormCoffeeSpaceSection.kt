package com.youapps.users_management.ui.registration.steps

import OBButtonContainedNeutral
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.PageSection
import com.youapps.designsystem.components.lists.OBCarouselEditable
import com.youapps.designsystem.components.menus.OBKeywordsListInput
import com.youapps.designsystem.components.text.PlaceholderText
import com.youapps.users_management.R
import com.youapps.designsystem.R as ds
import com.youapps.users_management.ui.registration.OBRegistrationStateHolder


@Composable
fun RegistrationFormCoffeeSpaceSection(
    modifier: Modifier = Modifier,
    screenState: OBRegistrationStateHolder,
    onGalleryItemAdd: ()-> Unit,
    onGalleryItemClicked : ((String)-> Unit)?=null,
    onGalleryItemDeleted: (Int)-> Unit,
    onKeywordAdded: (String)-> Unit,
    onKeyWordDeleted: (String)-> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.Top)
    ) {

            PageSection(
                modifier = Modifier.fillMaxWidth(),
                sectionTitle = stringResource(R.string.profile_gallery),
            ) {
                OBCarouselEditable(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(206.dp),
                    state = screenState.coffeeSpaceCarouselState.value,
                    itemSpacing = 8.dp,
                    preferredItemWidth = 320.dp,
                    onItemClicked = onGalleryItemClicked,
                    onItemDeleted = onGalleryItemDeleted,
                    onGalleryItemAdd = onGalleryItemAdd
                )
            }

        OBKeywordsListInput(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            keywords = screenState.keywords.value,
            label = stringResource(ds.string.keywords),
            onKeywordAdded = onKeywordAdded,
            onKeyWordDeleted = onKeyWordDeleted
        )
    }


}