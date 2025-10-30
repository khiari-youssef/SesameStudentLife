package com.youapps.onlybeans.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class OBUserAccountDTO(
    @SerialName("id") val id : String,
    @SerialName("email")val email : String,
    @SerialName("password")val password : String,
    @SerialName("phone")val phone : String,
    @SerialName("userProfile") val userProfile : OBUserProfileDTO
)