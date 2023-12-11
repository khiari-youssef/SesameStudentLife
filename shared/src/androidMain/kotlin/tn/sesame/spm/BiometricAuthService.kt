package com.example.androidutils.systemServices.security.deviceAuthentication


import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.crypto.Cipher
import javax.crypto.SecretKey


class BiometricAuthService(
    private val context : Context
) {


private val keyGuardManager : KeyguardManager by lazy {
    context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
}

private val biometricManager =  BiometricManager.from(context)

private val biometricLauncherService : BiometricLauncherService by lazy {
    BiometricLauncher(context)
}




fun checkBiometricCapabilitiesState() : SupportedDeviceAuthenticationMethods {
   return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q){
        if(keyGuardManager.isDeviceSecure) SupportedDeviceAuthenticationMethods.Available(biometricLauncherService)
        else SupportedDeviceAuthenticationMethods.Unavailable
    } else {
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> SupportedDeviceAuthenticationMethods.Available(biometricLauncherService)

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> SupportedDeviceAuthenticationMethods.NoHardware

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> SupportedDeviceAuthenticationMethods.HardwareUnavailable

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> SupportedDeviceAuthenticationMethods.Unavailable

            else -> SupportedDeviceAuthenticationMethods.Undefined
        }
    }
}



    private class BiometricLauncher(
        private val context: Context
    ) : BiometricLauncherService,BiometricPrompt.AuthenticationCallback(){

        private var authenticationActionListener : ((BiometricLauncherService.DeviceAuthenticationState)->Unit)?=null


        private fun createBiometricPromptRequest() : BiometricPrompt.PromptInfo
                = BiometricPrompt.PromptInfo.Builder().apply {
            setTitle("Biometric login for my app")
            setSubtitle("Log in using your biometric credential")
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q){
                setNegativeButtonText("cancel")
                setAllowedAuthenticators(BIOMETRIC_WEAK)
            } else {
                setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            }
        }.build()

        @RequiresApi(Build.VERSION_CODES.P)
        override fun launch(activity: FragmentActivity) {
                val promptRequest = createBiometricPromptRequest()
                val biometricPrompt = BiometricPrompt(
                    activity,
                    context.mainExecutor,
                    this
                )
                CoroutineScope(Dispatchers.Unconfined).launch {
                    withContext(Dispatchers.Main){
                        biometricPrompt.authenticate(
                            promptRequest
                        )
                    }
                }
        }

        @RequiresApi(Build.VERSION_CODES.R)
        override fun launchAndEncrypt(
            activity: FragmentActivity,
            secretKey: SecretKey
        ) {

                val promptRequest = createBiometricPromptRequest()
                val biometricPrompt = BiometricPrompt(
                    activity,
                    context.mainExecutor,
                    this
                )
                CoroutineScope(Dispatchers.Unconfined).launch {
                    val cipher = Cipher.getInstance(
                        KeyProperties.KEY_ALGORITHM_AES + "/"
                                + KeyProperties.BLOCK_MODE_CBC + "/"
                                + KeyProperties.ENCRYPTION_PADDING_PKCS7
                    )
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey)
                    withContext(Dispatchers.Main){
                        biometricPrompt.authenticate(
                            promptRequest,
                            BiometricPrompt.CryptoObject(cipher)
                        )
                    }
            }

        }

        override fun setOnAuthenticationActionListener(callback: (BiometricLauncherService.DeviceAuthenticationState) -> Unit) {
            authenticationActionListener = callback
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            authenticationActionListener?.let { callback->
                callback(BiometricLauncherService.DeviceAuthenticationState.Failed)
            }
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            authenticationActionListener?.let { callback->
                callback(BiometricLauncherService.DeviceAuthenticationState.Success(
                    result.authenticationType,
                    result.cryptoObject?.cipher?.doFinal()
                ))
            }

        }

        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            authenticationActionListener?.let { callback->
                callback(BiometricLauncherService.DeviceAuthenticationState.Error(
                    errorCode
                ))
            }
        }
    }

}