package tn.sesame.spm.domain.entities

import tn.sesame.spm.domain.entities.SesameRole.Companion.NONE
import tn.sesame.spm.domain.entities.SesameRole.Companion.getDefaultRoleForID

enum class SesameUserSex{
    Male,Female
}

sealed interface SesameLoginInterface{

    data class SesameTokenLogin(
        val value : String
    ) : SesameLoginInterface

    data class SesameCredentialsLogin(
        val password : String,
        val email : String
    ) : SesameLoginInterface
}

data class SesameUserAccount(
    val token : String,
    val role_id : String,
    val email : String
)
data class SesameClass(
 val id : String,
 val name : String,
 val level : String,
 val group : String
){
    fun getDisplayName() : String = "$name$level-$group"
}

open class SesameUser(
 val registrationID : String,
 val firstName : String,
 val lastName : String,
 val email : String,
 val sex : SesameUserSex,
 val profilePicture : String,
 val role : SesameRole = SesameRole(id=NONE)
){

  val isSesameUser : Boolean = email.endsWith("@sesame.com.tn")
   fun getFullName() : String = "$firstName $lastName"


    open val canViewProjects : SesamePermissionState = SesamePermissionState.DENIED
    open val canCreateProjects : SesamePermissionState = SesamePermissionState.DENIED
    open val canJoinProjects : SesamePermissionState = SesamePermissionState.DENIED
    open val canRemoveProjects : SesamePermissionState = SesamePermissionState.DENIED
    open val canEditProjects : SesamePermissionState = SesamePermissionState.DENIED
    open val canAssignMembersToProject : SesamePermissionState = SesamePermissionState.DENIED
    open val canUnassignMembersFromProject : SesamePermissionState = SesamePermissionState.DENIED
    open val canManageUsers : SesamePermissionState = SesamePermissionState.DENIED
    open val canViewProfiles : SesamePermissionState = SesamePermissionState.DENIED
    open val canLogin : SesamePermissionState = SesamePermissionState.DENIED
    open val canManageRequest : SesamePermissionState = SesamePermissionState.DENIED

}


 class SesameStudent(
   registrationID : String,
   firstName : String,
   lastName : String,
   email : String,
   sex : SesameUserSex,
   profilePicture : String,
  val portfolioId : String?=null,
  val job : String?=null,
  val sesameClass : SesameClass,
   role : SesameRole=  getDefaultRoleForID(SesameRole.STUDENT)
 ) : SesameUser(registrationID, firstName, lastName, email,sex, profilePicture,role){

     override val canViewProjects : SesamePermissionState = SesamePermissionState.GRANTED
     override val canJoinProjects : SesamePermissionState = SesamePermissionState.REQ_AUTH
     override val canViewProfiles : SesamePermissionState = SesamePermissionState.GRANTED
     override val canLogin : SesamePermissionState = SesamePermissionState.GRANTED
 }

class SesameTeacher(
    registrationID : String,
    firstName : String,
    lastName : String,
    email : String,
    sex : SesameUserSex,
    profilePicture : String,
    val portfolioId : String?=null,
    val profBackground : String,
    val assignedClasses : List<SesameClass>,
    role : SesameRole = getDefaultRoleForID(SesameRole.TEACHER),
): SesameUser(registrationID, firstName, lastName, email,sex, profilePicture,role){
    override val canViewProjects : SesamePermissionState = SesamePermissionState.GRANTED
    override val canCreateProjects : SesamePermissionState = SesamePermissionState.GRANTED
    override val canViewProfiles : SesamePermissionState = SesamePermissionState.GRANTED
    override val canLogin : SesamePermissionState = SesamePermissionState.GRANTED
}