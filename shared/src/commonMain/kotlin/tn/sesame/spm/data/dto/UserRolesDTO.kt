package tn.sesame.spm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RolePermission(
    @SerialName("id")   val id : String,
    @SerialName("description")  val description : String,
    @SerialName("state")  val state : String
)

@Serializable
internal data class UserRolesDTO(
  @SerialName("id")  val id : String,
  @SerialName("permissions")  val permissions : List<RolePermission>
)