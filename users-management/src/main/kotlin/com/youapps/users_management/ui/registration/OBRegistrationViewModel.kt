package com.youapps.users_management.ui.registration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youapps.onlybeans.data.repositories.users.OBUsersRepositoryInterface
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.exception.DomainErrorType
import com.youapps.users_management.ui.profile.ProfileScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class OBRegistrationViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val usersRepository : OBUsersRepositoryInterface
)  : ViewModel() {

    private val _profileState = MutableStateFlow<OBRegistrationScreenState>(OBRegistrationScreenState.Loading)
    val profileState : StateFlow<OBRegistrationScreenState> = _profileState

    init {
        fetchMyProfile()
    }


    private fun fetchMyProfile() {
        viewModelScope.launch {
            runCatching {
                return@runCatching usersRepository.getCurrentUserData()!!
            }.onFailure {
                _profileState.update {
                    OBRegistrationScreenState.Error(errorType = DomainErrorType.Undefined)
                }
            }.onSuccess { data->
                updateProfilePicture(uri = data.profilePicture)
                updateCoverPicture(uri = data.coverPicture)
                updateProfileDescription(text = data.profileDescription)
                updateStatus(status = data.status)
                _profileState.update {
                    OBRegistrationScreenState.Success(userProfile = data)
                }
            }
        }
    }

    fun updateProfilePicture(uri : String) {
       viewModelScope.launch {
           withContext(Dispatchers.IO){
               savedStateHandle[PROFILE_PICTURE_KEY] = uri
           }
       }
    }

    fun updateCoverPicture(uri : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                savedStateHandle[COVER_PICTURE_KEY] = uri
            }
        }
    }

    fun updateProfileDescription(text : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                savedStateHandle[PROFILE_DESC_KEY] = text
            }
        }
    }

    fun updateStatus(status : String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                savedStateHandle[PROFILE_STATUS_KEY] = status
            }
        }
    }

     fun getProfilePicture() :  Flow<String?> = savedStateHandle.getStateFlow(key = PROFILE_PICTURE_KEY,null)

     fun getCoverPicture() : Flow<String?> =  savedStateHandle.getStateFlow(key = COVER_PICTURE_KEY,null)

     fun getProfileDescription() : Flow<String?> = savedStateHandle.getStateFlow(key = PROFILE_DESC_KEY,null)

     fun getProfileStatus() : Flow<String?> = savedStateHandle.getStateFlow(key = PROFILE_STATUS_KEY,null)

    fun getFistName() : Flow<String?> = _profileState.map {
        if (it is OBRegistrationScreenState.Success) {
            it.userProfile.firstName
        } else null
    }

    fun getLastName() : Flow<String?> = _profileState.map {
        if (it is OBRegistrationScreenState.Success) {
            it.userProfile.secondName
        } else null
    }

    fun getEmail() : Flow<String?> = _profileState.map {
        if (it is OBRegistrationScreenState.Success) {
            it.userProfile.email
        } else null
    }



    companion object{
        private const val PROFILE_PICTURE_KEY = "profile_picture"
        private const val COVER_PICTURE_KEY = "cover_picture"
        private const val PROFILE_DESC_KEY = "profile_desc"
        private const val PROFILE_STATUS_KEY = "profile_status"
    }


}