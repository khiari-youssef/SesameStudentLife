package com.youapps.onlybeans.security

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.biometric.BiometricManager


fun getRegistrationBiometricIdentityIntent(): Intent {
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                )
            }
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
            // ACTION_FINGERPRINT_ENROLL is deprecated in API 29, but required for API 28 (P)
            @Suppress("DEPRECATION")
            Intent(Settings.ACTION_FINGERPRINT_ENROLL)
        }
        else -> {
            Intent(Settings.ACTION_SECURITY_SETTINGS)
        }
    }
}
