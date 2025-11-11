package com.youapps.users_management.ui.registration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.exception.DomainErrorType


sealed interface OBRegistrationScreenState  {



    @Stable
    data object Loading : OBRegistrationScreenState

    @Stable
    data class Error(val errorType : DomainErrorType = DomainErrorType.Undefined) : OBRegistrationScreenState

    @Stable
    data class Success(val userProfile : OBUserProfile) : OBRegistrationScreenState
}

data class OBRegistrationStateHolder(
    val profilePicture : State<String?>,
    val coverPicture : State<String?>,
    val profileDescription : State<String?>,
    val profileStatus : State<String?>,
    val firstName : State<String?>,
    val lastName : State<String?>,
    val email : State<String?>
){
    companion object{

        @Composable
        fun rememberOBRegistrationState(
             profilePicture : State<String?>,
             coverPicture : State<String?>,
             profileDescription : State<String?>,
             profileStatus : State<String?>,
              firstName : State<String?>,
              lastName : State<String?>,
              email : State<String?>
        ) : OBRegistrationStateHolder = remember(profilePicture,coverPicture,profileDescription,profileStatus,firstName,lastName,email) {
            OBRegistrationStateHolder(
                profilePicture,coverPicture,profileDescription,profileStatus,firstName,lastName,email
            )
        }

    }
}