package tn.sesame.spm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class SesameLoginResponseWrapper(
  @SerialName("token")  val token : SesameAuthToken,
  @SerialName("user_profile")  val data : SesameUserDTO
)