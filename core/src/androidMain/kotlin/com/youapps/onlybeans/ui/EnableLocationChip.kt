package com.youapps.onlybeans.ui

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity.RESULT_CANCELED
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.component1
import androidx.activity.result.component2
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.youapps.onlybeans.R
import com.youapps.onlybeans.platform.OBLocationService
import org.koin.compose.koinInject
import com.youapps.designsystem.R as ds

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EnableLocationChip(
    modifier: Modifier = Modifier,
    onLocationEnabled : ()-> Unit
) {
    val locationService : OBLocationService = koinInject<OBLocationService>()
    val localContext = LocalContext.current
    val locationPermissions =  arrayOf(
        ACCESS_COARSE_LOCATION,
        ACCESS_COARSE_LOCATION
    )
    val locationSettingsLauncher : ActivityResultLauncher<Intent> = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { (resultCode, _) ->
        if (resultCode == RESULT_CANCELED && locationService.isLocationEnabled()){
                onLocationEnabled()
        } else {
            Toast.makeText(localContext, localContext.getString(R.string.random_error), Toast.LENGTH_SHORT)
        }
    }

    val enabledPermissions : List<Boolean> = locationPermissions.map { permission->
        localContext.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result->
         if (result.values.any { it }){
             locationSettingsLauncher.launch(locationService.getRedirectionToLocationSettingsIntent(localContext))
         } else {
             Toast.makeText(localContext, localContext.getString(R.string.permission_required), Toast.LENGTH_SHORT).show()
         }
    }





    Chip(
        colors = ChipDefaults.chipColors(
            backgroundColor = MaterialTheme.colorScheme.primary,
            disabledBackgroundColor = MaterialTheme.colorScheme.primary.copy(
                alpha = 0.5f
            )
        ),
        modifier = modifier
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            )
            .wrapContentSize(),
        onClick = {
           if (enabledPermissions.none()){
               permissionLauncher.launch(locationPermissions)
           } else {
               locationSettingsLauncher.launch(locationService.getRedirectionToLocationSettingsIntent(localContext))
           }
        },
        content = {
            Text(
                text = stringResource(R.string.enable_location_service),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Color.White
                ),
                textAlign = TextAlign.Start
            )
        },
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .padding(
                        start = 4.dp
                    )
                    .size(
                        height = 14.dp,
                        width = 10.dp
                    ),
                imageVector = ImageVector.vectorResource(ds.drawable.ic_location_pin) ,
                contentDescription = stringResource(R.string.enable_location_service),
                tint = Color.White
            )
        }
    )





}