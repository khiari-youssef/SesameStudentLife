package tn.sesame.designsystem

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController


fun NavController.navigateBack() {
   val canGoBack = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
    if (canGoBack) {
        popBackStack()
    }
}