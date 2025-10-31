package com.youapps.onlybeans.data.dto

import com.youapps.onlybeans.domain.entities.users.OBUserAccount
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class OBUserAccountDTO(
    @SerialName("id") val id : String,
    @SerialName("email")val email : String,
    @SerialName("phone")val phone : String,
    @SerialName("userProfile") val userProfile : OBUserProfileDTO
) {


    fun toDomainModel() : OBUserAccount = OBUserAccount(
        id= id,
        email = email,
        phone = phone,
        userProfile = userProfile.toDomainModel()
    )
}