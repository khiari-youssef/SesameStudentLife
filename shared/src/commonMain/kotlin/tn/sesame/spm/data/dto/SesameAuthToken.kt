package tn.sesame.spm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SesameAuthToken(
  @SerialName("jwt")  val value : String,
  @SerialName("expiration")  val expirationTimeStamp : Long
)