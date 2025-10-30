package com.youapps.onlybeans.data.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias CoffeeSpaceType = Pair<String, Any>

internal const val OBHomeCoffeeBarID : String = "OBHomeCoffeeBar"
internal const val OBCoffeeShopID : String = "OBCoffeeShop"
internal const val OBCoffeeCompanyID : String = "OBCoffeeCompany"
internal const val OBCoffeeFarmID : String = "OBCoffeeFarm"

@Serializable
internal  class OBUserProfileDTO(
    @SerialName("email")   val email : String,
    @SerialName("firstName")   val firstName : String,
    @SerialName("secondName") val secondName : String,
    @SerialName("status")  val status : String,
    @SerialName("nationality") val nationality : String,
    @SerialName("address") val address : String,
    @SerialName("profileDescription") val profileDescription : String,
    @SerialName("profilePicture")  val profilePicture : String,
    @SerialName("myCoffeeSpace")  val myCoffeeSpace : CoffeeSpaceType
)


@Serializable
 class OBUserProfileOverViewDTO(
    @SerialName("id")   val id : String,
    @SerialName("fullName") val fullName : String,
    @SerialName("status") val status : String,
    @SerialName("profilePicture") val profilePicture : String
)