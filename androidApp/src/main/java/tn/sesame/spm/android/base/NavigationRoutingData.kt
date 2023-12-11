package tn.sesame.spm.android.base

data object NavigationRoutingData {
    const val ExitAppRoute = "exit_app"
    const val Login = "login_screen"
    data object Home{
          val ROOT = toString()
        const val Calendar = "calendar_screen"
        const val Profile = "profile_screen"
        const val Notifications = "notifications_screen"
        const val Projects = "projects_screen"

        fun mapRouteToIndex(route : String) : Int = when (route){
            Calendar -> 0
            Profile -> 3
            Notifications -> 2
            Projects -> 1
            else -> throw  IllegalStateException()
        }

        fun mapIndexToRoute(index : Int) : String= when (index){
            0-> Calendar
            1 -> Projects
            2 -> Notifications
            3 -> Profile
           else -> throw IndexOutOfBoundsException()
        }
    }

}