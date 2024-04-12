package tn.sesame.spm.android.base

data object NavigationRoutingData {
    const val ExitAppRoute = "exit_app"
    const val Login = "login_screen"
    const val MyProjects = "my_projects_screen"
    const val NavigationNotFound = "navigation_not_found_screen"
    const val Settings = "settings_screen"
    const val PrivacyPolicyScreen = "PrivacyPolicyScreen_screen"

    data object Home{
          val ROOT = toString()
        const val Calendar = "calendar_screen"
        const val Profile = "profile_screen"
        const val Notifications = "notifications_screen"
        const val Projects = "projects_screen"
        const val News = "news_screen"

        fun mapRouteToIndex(route : String) : Int = when (route){
            News  -> 0
            Profile -> 3
            Notifications -> 2
            Calendar -> 1
            else -> throw  IllegalStateException()
        }

        fun mapIndexToRoute(index : Int) : String= when (index){
            0-> News
            1 -> Calendar
            2 -> Notifications
            3 -> Profile
           else -> throw IndexOutOfBoundsException()
        }
    }

    data object ProjectJoinProcedure{
        const val ProjectDetailsScreen = "project_details_screen"
        const val SupervisorSelectionScreen : String = "SupervisorSelectionScreen"
        const val TeammatesSelectionScreen : String = "TeammatesSelectionScreen"
        const val TechnologiesSelectionScreen : String = "TechnologiesSelectionScreen"
        const val ProjectDocumentsDepositScreen : String = "ProjectDocumentsDepositScreen"
        const val JoinRequestResultScreen : String = "JoinRequestResultScreen"
    }

}