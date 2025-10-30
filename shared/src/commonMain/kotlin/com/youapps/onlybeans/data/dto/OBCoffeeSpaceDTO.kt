package com.youapps.onlybeans.data.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class OBHomeCoffeeBar(
    @SerialName("spaceId")  val spaceId : String,
    @SerialName("userEmail") val userEmail : String,
    @SerialName("description") val description : String,
    @SerialName("gallery") val gallery : List<String>,
    @SerialName("coffeeGear")  val coffeeGear : List<OBProductListItemDTO>,
    @SerialName("coffeeBeans")  val coffeeBeans : List<OBProductListItemDTO>
)


@Serializable
internal data class OBCoffeeShop(
    @SerialName("spaceId")  val spaceId : String,
    @SerialName("userEmail") val userEmail : String,
    @SerialName("description") val description : String,
    @SerialName("gallery") val gallery : List<String>
)

@Serializable
internal data class OBCoffeeCompany(
    @SerialName("spaceId")  val spaceId : String,
    @SerialName("userEmail") val userEmail : String,
    @SerialName("description") val description : String,
    @SerialName("gallery") val gallery : List<String>
)

@Serializable
internal data class OBCoffeeFarm(
    @SerialName("spaceId")  val spaceId : String,
    @SerialName("userEmail") val userEmail : String,
    @SerialName("description") val description : String,
    @SerialName("gallery") val gallery : List<String>
)