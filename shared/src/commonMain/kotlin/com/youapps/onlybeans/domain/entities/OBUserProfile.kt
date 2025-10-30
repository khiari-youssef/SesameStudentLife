package com.youapps.onlybeans.domain.entities

class OBUserProfile(
    val email : String,
    val firstName : String,
    val secondName : String,
    val status : String,
    val nationality : String,
    val address : String,
    val profileDescription : String,
    val profilePicture : String,
    val myCoffeeSpace : OBCoffeeSpace
)


class OBUserProfileOverView(
    val id : String,
    val fullName : String,
    val status : String,
    val profilePicture : String,
)