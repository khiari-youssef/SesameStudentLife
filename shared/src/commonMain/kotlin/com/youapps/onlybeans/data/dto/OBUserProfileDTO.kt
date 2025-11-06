package com.youapps.onlybeans.data.dto

import com.youapps.onlybeans.domain.entities.users.OBAddress
import com.youapps.onlybeans.domain.entities.users.OBLocation
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.entities.users.OBUserProfilePreView
import com.youapps.onlybeans.domain.valueobjects.decodeToUserSex
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
internal data class OBUserProfileDTO(
    @SerialName("email")   val email : String,
    @SerialName("firstName")   val firstName : String,
    @SerialName("secondName") val secondName : String,
    @SerialName("status")  val status : String,
    @SerialName("nationality") val nationality : String,
    @SerialName("address") val address : OBAddressDTO,
    @SerialName("sex") val sex : String?,
    @SerialName("phone") val phone : String?,
    @SerialName("profileDescription") val profileDescription : String,
    @SerialName("profilePicture")  val profilePicture : String,
    @SerialName("coverPicture")  val coverPicture : String,
    @Serializable(with = CoffeeSpaceSerialize::class)
    @SerialName("myCoffeeSpace")  val myCoffeeSpace : OBCoffeeSpaceDTO?,
    @SerialName("keywords") val keywords: List<String>?=null,
) {
    fun toDomainModel() : OBUserProfile = OBUserProfile(
        email = email,
        firstName = firstName,
        secondName = secondName,
        status = status,
        nationality = nationality,
        address = address.toDomainModel(),
        profileDescription = profileDescription,
        profilePicture = profilePicture,
        myCoffeeSpace = myCoffeeSpace?.toDomainModel(),
        sex = sex?.decodeToUserSex(),
        coverPicture = coverPicture,
        phone = phone,
        keywords = keywords,
    )
}


@Serializable
 internal data class OBUserProfileOverViewDTO(
    @SerialName("id")   val id : String,
    @SerialName("fullName") val fullName : String,
    @SerialName("status") val status : String,
    @SerialName("profilePicture") val profilePicture : String,
    @SerialName("coverPicture") val coverPicture : String,
    @SerialName("address") val address: OBAddressDTO?=null
) {
     fun toDomainModel() : OBUserProfilePreView = OBUserProfilePreView(
         id = id,
         fullName = fullName,
         status = status,
         profilePicture = profilePicture,
         coverPicture = coverPicture,
         address = address?.toDomainModel()
     )
 }

@Serializable
data class OBAddressDTO(
    @SerialName("country")  val country : String,
    @SerialName("city")  val city : String?=null,
    @SerialName("line") val line : String?=null,
    @SerialName("postalCode") val postalCode : String?=null,
    @SerialName("location") val location : OBLocationDTO?=null
) {
    fun toDomainModel() :OBAddress = OBAddress(
        country = country,
        city = city,
        line = line,
        postalCode = postalCode,
        location = location?.toDomainModel(),
    )

     fun toJson(): String = Json.encodeToString(this)

     companion object{
         fun fromJson(json : String): OBAddressDTO? = runCatching {
             return@runCatching  Json.decodeFromString<OBAddressDTO?>(json)
         }.getOrNull()
     }
}

@Serializable
data class OBLocationDTO(
    @SerialName("longitude")  val longitude : Double,
    @SerialName("latitude") val latitude : Double
) {
    fun toDomainModel() : OBLocation =OBLocation(
        longitude = longitude,
        latitude = latitude
    )
}