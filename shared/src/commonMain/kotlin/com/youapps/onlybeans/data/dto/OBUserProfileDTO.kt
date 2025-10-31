package com.youapps.onlybeans.data.dto

import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.entities.users.OBUserProfileOverView
import com.youapps.onlybeans.domain.valueobjects.decodeToUserSex
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
internal data class OBUserProfileDTO(
    @SerialName("email")   val email : String,
    @SerialName("firstName")   val firstName : String,
    @SerialName("secondName") val secondName : String,
    @SerialName("status")  val status : String,
    @SerialName("nationality") val nationality : String,
    @SerialName("address") val address : String,
    @SerialName("sex") val sex : String?,
    @SerialName("phone") val phone : String?,
    @SerialName("profileDescription") val profileDescription : String,
    @SerialName("profilePicture")  val profilePicture : String,
    @Serializable(with = CoffeeSpaceSerialize::class)
    @SerialName("myCoffeeSpace")  val myCoffeeSpace : CoffeeSpace?
) {
    fun toDomainModel() : OBUserProfile = OBUserProfile(
        email = email,
        firstName = firstName,
        secondName = secondName,
        status = status,
        nationality = nationality,
        address = address,
        profileDescription = profileDescription,
        profilePicture = profilePicture,
        myCoffeeSpace = myCoffeeSpace?.toDomainModel(),
        sex = sex?.decodeToUserSex(),
        phone = phone
    )
}


@Serializable
 internal data class OBUserProfileOverViewDTO(
    @SerialName("id")   val id : String,
    @SerialName("fullName") val fullName : String,
    @SerialName("status") val status : String,
    @SerialName("profilePicture") val profilePicture : String
) {
     fun toDomainModel() : OBUserProfileOverView = OBUserProfileOverView(
         id = id,
         fullName = fullName,
         status = status,
         profilePicture = profilePicture
     )
 }