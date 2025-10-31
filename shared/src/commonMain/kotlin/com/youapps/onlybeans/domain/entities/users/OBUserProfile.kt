package com.youapps.onlybeans.domain.entities.users

import com.youapps.onlybeans.domain.entities.products.OBCoffeeSpace
import com.youapps.onlybeans.domain.valueobjects.UserSex


class OBUserProfile(
      val email : String,
      val firstName : String,
     val secondName : String?,
    val status : String,
   val phone : String?,
   val sex : UserSex?,
    val nationality : String?,
    val address : String,
    val profileDescription : String,
     val profilePicture : String,
     val myCoffeeSpace : OBCoffeeSpace?
) {
    val fullName = "$firstName $secondName"
}



 class OBUserProfileOverView(
      val id : String,
     val fullName : String,
     val status : String,
     val profilePicture : String,
)