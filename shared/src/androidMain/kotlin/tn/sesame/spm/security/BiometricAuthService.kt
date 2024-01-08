package tn.sesame.spm.security


import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import javax.crypto.Cipher
import javax.crypto.SecretKey


class BiometricAuthService(
    private val context: Context
) {


    private val keyGuardManager: KeyguardManager by lazy {
        context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    }

    private val biometricManager = BiometricManager.from(context)

    private val biometricLauncherService: BiometricLauncherService by lazy {
        BiometricLauncher(context)
    }

    fun checkBiometricCapabilitiesState(): SupportedDeviceAuthenticationMethods {
        return if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            if (keyGuardManager.isDeviceSecure) SupportedDeviceAuthenticationMethods.Available(
                biometricLauncherService
            )
            else SupportedDeviceAuthenticationMethods.Unavailable
        } else {
            when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
                BiometricManager.BIOMETRIC_SUCCESS -> SupportedDeviceAuthenticationMethods.Available(
                    biometricLauncherService
                )

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> SupportedDeviceAuthenticationMethods.NoHardware

                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> SupportedDeviceAuthenticationMethods.HardwareUnavailable

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> SupportedDeviceAuthenticationMethods.Unavailable

                else -> SupportedDeviceAuthenticationMethods.Undefined
            }
        }
    }



    private class BiometricLauncher(
        private val context: Context
    ) : BiometricLauncherService{

        private val _authenticationResultState: MutableStateFlow<BiometricLauncherService.DeviceAuthenticationState>
                = MutableStateFlow(BiometricLauncherService.DeviceAuthenticationState.Idle)

        private fun createBiometricPromptRequest(
            title: String,
            subtitle: String
        ): BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(title)
            setSubtitle(subtitle)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
                setNegativeButtonText("cancel")
                setAllowedAuthenticators(BIOMETRIC_WEAK)
            } else {
                setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            }
        }.build()


        override fun launch(
            activity: FragmentActivity,
            title: String,
            subtitle: String
        ) : Flow<BiometricLauncherService.DeviceAuthenticationState> = callbackFlow {
            val promptRequest = createBiometricPromptRequest(
                title, subtitle
            )
            val callback = object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    trySend(BiometricLauncherService.DeviceAuthenticationState.Failed)
                }
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    trySend(BiometricLauncherService.DeviceAuthenticationState.Error(
                        errorCode
                    ))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    trySend( BiometricLauncherService.DeviceAuthenticationState.Success(
                        result.authenticationType,
                        result.cryptoObject?.cipher?.doFinal()
                    ))
                }
            }
            val biometricPrompt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                BiometricPrompt(
                    activity,
                    context.mainExecutor,
                    callback
                )
            } else {
                BiometricPrompt(
                    activity,
                    callback
                )
            }
            biometricPrompt.authenticate(
                promptRequest
            )
            awaitClose()
        }


        override fun launch(fragment: Fragment, title: String, subtitle: String): Flow<BiometricLauncherService.DeviceAuthenticationState> = callbackFlow  {
            val promptRequest = createBiometricPromptRequest(
                title, subtitle
            )
            val callback = object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    trySend(BiometricLauncherService.DeviceAuthenticationState.Failed)
                }
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    trySend(BiometricLauncherService.DeviceAuthenticationState.Error(
                        errorCode
                    ))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    trySend( BiometricLauncherService.DeviceAuthenticationState.Success(
                        result.authenticationType,
                        result.cryptoObject?.cipher?.doFinal()
                    ))
                }
            }
            val biometricPrompt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                BiometricPrompt(
                    fragment,
                    context.mainExecutor,
                    callback
                )
            } else {
                BiometricPrompt(
                    fragment,
                    callback
                )
            }
            biometricPrompt.authenticate(
                promptRequest
            )
            awaitClose()
        }

        @RequiresApi(Build.VERSION_CODES.R)
        override fun launchAndEncrypt(
            activity: FragmentActivity,
            secretKey: SecretKey,
            title: String,
            subtitle: String
        ) : Flow<BiometricLauncherService.DeviceAuthenticationState> = callbackFlow {

            val promptRequest = createBiometricPromptRequest(
                title, subtitle
            )
            val callback = object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    trySend(BiometricLauncherService.DeviceAuthenticationState.Failed)
                }
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    trySend(BiometricLauncherService.DeviceAuthenticationState.Error(
                        errorCode
                    ))
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    trySend( BiometricLauncherService.DeviceAuthenticationState.Success(
                        result.authenticationType,
                        result.cryptoObject?.cipher?.doFinal()
                    ))
                }
            }
            val biometricPrompt = BiometricPrompt(
                activity,
                context.mainExecutor,
                callback
            )
                val cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7
                )
                cipher.init(Cipher.ENCRYPT_MODE, secretKey)
                withContext(Dispatchers.Default) {
                    biometricPrompt.authenticate(
                        promptRequest,
                        BiometricPrompt.CryptoObject(cipher)
                    )
                }
            awaitClose()
        }

        override val authenticationResultState: StateFlow<BiometricLauncherService.DeviceAuthenticationState>
            get() = _authenticationResultState


    }

}