package tn.sesame.spm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tn.sesame.spm.domain.entities.SesamePermission
import tn.sesame.spm.domain.entities.SesamePermissionState

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