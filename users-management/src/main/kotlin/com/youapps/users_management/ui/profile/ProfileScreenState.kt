package com.youapps.users_management.ui.profile

import androidx.compose.runtime.Immutable
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.exception.DomainErrorType


sealed interface ProfileScreenState {

    @Immutable
    data class Loading(val withPullToRefresh : Boolean = false) : ProfileScreenState

    @Immutable
    data class Error(val error : DomainErrorType) : ProfileScreenState

    @Immutable
    data class Loaded(val profile: OBUserProfile) : ProfileScreenState
}