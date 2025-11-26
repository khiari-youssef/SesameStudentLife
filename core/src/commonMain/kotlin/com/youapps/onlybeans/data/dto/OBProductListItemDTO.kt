package com.youapps.onlybeans.data.dto

import com.youapps.onlybeans.domain.entities.products.OBProductListItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class OBProductListItemDTO(
    @SerialName("productID")  val productID : String,
    @SerialName("productName") val productName : String,
    @SerialName("productImagePreview") val productImagePreview : String,
    @SerialName("productDescription") val productDescription : String
) {
    fun toDomain() : OBProductListItem = OBProductListItem(
        productID = productID,
        productName = productName,
        productImagePreview = productImagePreview,
        productDescription = productDescription
    )
}