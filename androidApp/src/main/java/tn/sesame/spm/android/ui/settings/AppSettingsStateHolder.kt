package tn.sesame.spm.android.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember


data class AppSettingsStateHolder(
  val isAutoLoginEnabled : State<Boolean>
){
    companion object{
        @Composable
        fun rememberAppSettingsState(
            isAutoLoginEnabled : State<Boolean>
        ) : AppSettingsStateHolder = remember(isAutoLoginEnabled) {
            AppSettingsStateHolder(isAutoLoginEnabled)
        }
    }
}