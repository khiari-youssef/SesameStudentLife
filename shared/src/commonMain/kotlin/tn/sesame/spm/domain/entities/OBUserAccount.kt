package tn.sesame.spm.domain.entities

class OBUserAccount(
    val id : String,
    val email : String,
    val password : String,
    val phone : String,
    val userProfile : OBUserProfile
)