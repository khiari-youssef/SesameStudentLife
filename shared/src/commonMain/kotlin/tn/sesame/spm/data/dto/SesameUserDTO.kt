package tn.sesame.spm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SesameUserDTO(
  @SerialName("registrationID")  val registrationID : String,
  @SerialName("firstName")   val firstName : String,
  @SerialName("lastName")   val lastName : String?=null,
  @SerialName("email")   val email : String,
  @SerialName("sex")   val sex : String,
  @SerialName("profilePicture")   val profilePicture : String,
  @SerialName("portfolioId") val portfolioId : String?=null,
  @SerialName("job") val job : String?=null,
  @SerialName("sesameClass") val sesameClass : SesameClassDTO?=null,
  @SerialName("profBackground") val profBackground : String?=null,
  @SerialName("assignedClasses") val assignedClasses : List<SesameClassDTO>?=null,
  @SerialName("role") val role : UserRolesDTO?=null
)