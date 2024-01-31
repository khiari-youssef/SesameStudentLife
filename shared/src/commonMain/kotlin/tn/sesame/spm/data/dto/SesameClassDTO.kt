package tn.sesame.spm.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SesameClassDTO(
 @SerialName("id")   val id : String?=null,
 @SerialName("name")   val name :  String?=null,
 @SerialName("level")  val level :  String?=null,
 @SerialName("group")    val group :  String?=null
)