package com.youapps.users_management.ui.settings.privacypolicy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.DetailsScreenTemplate
import com.youapps.users_management.R

@Composable
fun PrivacyPolicyScreen(
    onBackPressed : ()->Unit
) {
    DetailsScreenTemplate(
        modifier = Modifier,
        title = stringResource(id = R.string.profile_policy),
        onBackPressed = onBackPressed
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement
                .spacedBy(12.dp, Alignment.CenterVertically)
        ) {

        }
    }
}