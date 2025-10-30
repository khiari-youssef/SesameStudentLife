package com.youapps.onlybeans.data.dto

import com.youapps.onlybeans.domain.entities.OBUserProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class OBUserAccountDTO(
    @SerialName("id")  val id : String,
    @SerialName("email") val email : String,
    @SerialName("password")val password : String,
    @SerialName("phone") val phone : String,
    @SerialName("userProfile") val userProfile : OBUserProfile
)