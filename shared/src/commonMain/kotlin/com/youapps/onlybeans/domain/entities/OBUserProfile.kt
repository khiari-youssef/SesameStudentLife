package com.youapps.onlybeans.domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias CoffeeSpaceType = Pair<String, Any>

@Serializable
internal data class OBUserProfile(
    @SerialName("email")  val email : String,
    @SerialName("firstName")  val firstName : String,
    @SerialName("secondName") val secondName : String,
    @SerialName("status")val status : String,
    @SerialName("nationality")val nationality : String,
    @SerialName("address")val address : String,
    @SerialName("profileDescription")val profileDescription : String,
    @SerialName("profilePicture") val profilePicture : String,
    @SerialName("myCoffeeSpace") val myCoffeeSpace : CoffeeSpaceType
)


@Serializable
internal data class OBUserProfileOverView(
    @SerialName("id")  val id : String,
    @SerialName("fullName") val fullName : String,
    @SerialName("status") val status : String,
    @SerialName("profilePicture") val profilePicture : String,
)