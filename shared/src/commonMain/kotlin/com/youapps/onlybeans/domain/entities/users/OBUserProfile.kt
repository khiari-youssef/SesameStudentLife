package com.youapps.onlybeans.domain.entities.users
import com.youapps.onlybeans.domain.entities.products.OBCoffeeSpace
import com.youapps.onlybeans.domain.valueobjects.UserSex


class OBUserProfile(
      val email : String,
      val firstName : String,
     val secondName : String?,
    val status : String,
    val address : OBAddress?=null,
   val phone : String?,
   val sex : UserSex?,
    val nationality : String?,
    val profileDescription : String,
     val profilePicture : String,
     val coverPicture : String,
      val keywords: List<String>?=null,
     val myCoffeeSpace : OBCoffeeSpace?
) {
    val fullName = "$firstName $secondName"

    val profilePreView : OBUserProfilePreView = OBUserProfilePreView(
        id = email,
        fullName = fullName,
        status = status,
        profilePicture = profilePicture,
        coverPicture = coverPicture,
        address = address
    )
}



 class OBUserProfilePreView(
      val id : String,
     val fullName : String,
     val status : String,
     val profilePicture : String,
    val coverPicture : String,
    val address: OBAddress?=null
)
 data class OBAddress(
      val country : String,
      val city : String?=null,
      val line : String?=null,
      val postalCode : String?=null,
      val location : OBLocation?=null
)

data class OBLocation(
    val longitude : Double,
    val latitude : Double
) {
    fun toDMS() : String = TODO()

    override fun toString(): String = "$latitude:$longitude"

    companion object{

        fun fromString(input: String) : OBLocation? = runCatching{
            val decodedString = input.split(":").map {
                it.toDoubleOrNull()
            }
            OBLocation(
                latitude = decodedString[0]!!,
                longitude = decodedString[1]!!
            )
        }.getOrNull()
    }
}