package com.youapps.users_management.ui.registration.steps

import OBButtonContainedNeutral
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.youapps.users_management.R
import com.youapps.users_management.ui.registration.OBRegistrationStateHolder


@Composable
fun RegistrationFormCoffeeSpaceSection(
    modifier: Modifier = Modifier,
    screenState: OBRegistrationStateHolder,
    onGalleryItemAdd: ()-> Unit,
    onGalleryItemClicked : ((String)-> Unit)?=null,
    onGalleryItemDeleted: (Int)-> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp,Alignment.Top)
    ) {
        screenState.coffeeSpaceCarouselState.value.takeIf { it.images.isNotEmpty() }?.run {
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
        } ?: run {
          Column(
              modifier = Modifier.fillMaxWidth(),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(
                  8.dp, Alignment.CenterVertically
              )
          ) {
                Text(
                    text = "No images yet in your gallery !",
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center
                )
              OBButtonContainedNeutral(
                  text = "Add images"
              ) {

              }
          }
        }
    }


}