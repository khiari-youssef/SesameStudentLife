package com.youapps.onlybeans.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class OBProductListItemDTO(
    @SerialName("productID")  val productID : String,
    @SerialName("productName") val productName : String,
    @SerialName("productImagePreview") val productImagePreview : String,
    @SerialName("productDescription") val productDescription : String
)