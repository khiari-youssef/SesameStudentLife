package com.youapps.onlybeans.data.dto

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
    @SerialName("profileDescription") val profileDescription : String,
    @SerialName("profilePicture")  val profilePicture : String,
    @Serializable(with = CoffeeSpaceSerialize::class)
    @SerialName("myCoffeeSpace")  val myCoffeeSpace : CoffeeSpace
)


@Serializable
 internal data class OBUserProfileOverViewDTO(
    @SerialName("id")   val id : String,
    @SerialName("fullName") val fullName : String,
    @SerialName("status") val status : String,
    @SerialName("profilePicture") val profilePicture : String
)