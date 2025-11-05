package com.youapps.onlybeans.android.base

data object NavigationRoutingData {
    const val EXIT_APP_ROUTE = "exit_app"
    const val LOGIN = "login_screen"
    const val NAVIGATION_NOT_FOUND = "navigation_not_found_screen"
    const val SETTINGS = "settings_screen"
    const val PRIVACY_POLICY_SCREEN = "PrivacyPolicyScreen_screen"

    const val EDIT_PROFILE_SCREEN = "edit_screen_profile"

    const val VIEW_SCREEN_PRODUCT = "registration_screen"

    const val REGISTRATION_SCREEN = "registration_screen"

    data object Home{
          val ROOT = toString()
        const val NETWORK = "network_screen"
        const val PROFILE = "profile_screen"
        const val NOTIFICATIONS = "notifications_screen"
        const val MARKETPLACE = "news_screen"

        fun mapRouteToIndex(route : String) : Int = when (route){
            NETWORK  -> 0
            PROFILE -> 3
            NOTIFICATIONS -> 2
            MARKETPLACE -> 1
            else -> throw  IllegalStateException()
        }

        fun mapIndexToRoute(index : Int) : String= when (index){
            0-> NETWORK
            1 -> MARKETPLACE
            2 -> NOTIFICATIONS
            3 -> PROFILE
           else -> throw IndexOutOfBoundsException()
        }
    }



}