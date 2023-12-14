package tn.sesame.spm.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.biometric.BiometricManager


fun getRegistrationBiometricIdentityIntent() : Intent {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
            putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
        }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
          Intent(Settings.ACTION_FINGERPRINT_ENROLL)
        } else {
            Intent(Settings.ACTION_SECURITY_SETTINGS)
        }
}