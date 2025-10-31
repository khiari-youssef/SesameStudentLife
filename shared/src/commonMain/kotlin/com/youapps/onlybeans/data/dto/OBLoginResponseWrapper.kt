package com.youapps.onlybeans.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class OBLoginResponseWrapper(
  @SerialName("token")  val token : String,
  @SerialName("data")  val data : OBUserProfileDTO
)