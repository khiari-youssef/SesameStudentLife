package com.youapps.users_management.ui.registration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import com.youapps.onlybeans.domain.entities.users.OBLocation
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.exception.DomainErrorType
import com.youapps.onlybeans.domain.services.InputRuleType


sealed interface InputRuleCheckState{

    data object Initial : InputRuleCheckState

    @Immutable
    data class Invalid(
        val input : String?,
        val brokenRule : InputRuleType,
    ) : InputRuleCheckState

    @Immutable
    data class Valid(
        val input : String
    ) : InputRuleCheckState

}



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
    val profileDescription : State<InputRuleCheckState>,
    val profileStatus : State<InputRuleCheckState>,
    val firstName : State<String?>,
    val lastName : State<String?>,
    val email : State<String?>,
    val country : State<InputRuleCheckState>,
    val city : State<InputRuleCheckState>,
    val location : State<OBLocation?>,
    val phone : State<InputRuleCheckState>
){

    @Composable
    fun isFormReadyToSubmitState() : State<Boolean> = remember {
        derivedStateOf {
            profileDescription.value is InputRuleCheckState.Valid &&
            profileStatus.value is InputRuleCheckState.Valid &&
            country.value is InputRuleCheckState.Valid &&
            city.value is InputRuleCheckState.Valid &&
            phone.value is InputRuleCheckState.Valid
        }
    }
    companion object{

        @Composable
        fun rememberOBRegistrationState(
             profilePicture : State<String?>,
             coverPicture : State<String?>,
             profileDescription : State<InputRuleCheckState>,
             profileStatus : State<InputRuleCheckState>,
              firstName : State<String?>,
              lastName : State<String?>,
              email : State<String?>,
              country : State<InputRuleCheckState>,
              city : State<InputRuleCheckState>,
              location : State<OBLocation?>,
              phone : State<InputRuleCheckState>
        ) : OBRegistrationStateHolder = remember(profilePicture,coverPicture,profileDescription,profileStatus,firstName,lastName,email,country,city,location) {
            OBRegistrationStateHolder(
                profilePicture,coverPicture,profileDescription,profileStatus,firstName,lastName,email,country,city,location,phone
            )
        }

    }
}