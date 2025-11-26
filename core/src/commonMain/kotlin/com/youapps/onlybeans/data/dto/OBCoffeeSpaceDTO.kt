package com.youapps.onlybeans.data.dto
import com.youapps.onlybeans.domain.entities.products.OBCoffeeCompany
import com.youapps.onlybeans.domain.entities.products.OBCoffeeFarm
import com.youapps.onlybeans.domain.entities.products.OBCoffeeShop
import com.youapps.onlybeans.domain.entities.products.OBCoffeeSpace
import com.youapps.onlybeans.domain.entities.products.OBHomeCoffeeBar
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json


internal data class OBCoffeeSpaceDTO(
    val id: String,
    val data : Any
) {
    fun toDomainModel() : OBCoffeeSpace = when(data) {
        is OBHomeCoffeeBarDTO -> data.toDomainModel()
        is OBCoffeeShopDTO -> data.toDomainModel()
        is OBCoffeeCompanyDTO -> data.toDomainModel()
        is OBCoffeeFarmDTO -> data.toDomainModel()
        else -> throw IllegalStateException("no mapping found")
    }
}

internal const val OBHomeCoffeeBarID : String = "ob_home_coffee_bar"
internal const val OBCoffeeShopID : String = "ob_coffee_shop"
internal const val OBCoffeeCompanyID : String = "ob_coffee_company"
internal const val OBCoffeeFarmID : String = "ob_coffee_farm"

internal  class  CoffeeSpaceSerialize() : KSerializer<OBCoffeeSpaceDTO> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("CoffeeSpace", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: OBCoffeeSpaceDTO
    ) {
        when(value.id) {
            OBHomeCoffeeBarID -> {
                if (value.data is OBHomeCoffeeBarDTO) {
                    encoder.encodeSerializableValue(OBHomeCoffeeBarDTO.serializer(), value.data)
                } else throw SerializationException(message = "The schema associated with the ID $OBHomeCoffeeBarID is not valid")
            }
            OBCoffeeShopID -> {
                if (value.data is OBCoffeeShopDTO) {
                    encoder.encodeSerializableValue(OBCoffeeShopDTO.serializer(), value.data)
                } else throw SerializationException(message = "The schema associated with the ID $OBCoffeeShopID is not valid")
            }
            OBCoffeeCompanyID -> {
                if (value.data is OBCoffeeCompanyDTO) {
                    encoder.encodeSerializableValue(OBCoffeeCompanyDTO.serializer(), value.data)
                } else throw SerializationException(message = "The schema associated with the ID $OBCoffeeCompanyID is not valid")
            }
            OBCoffeeFarmID -> {
                if (value.data is OBCoffeeFarmDTO) {
                    encoder.encodeSerializableValue(OBCoffeeFarmDTO.serializer(), value.data)
                } else throw SerializationException(message = "The schema associated with the ID $OBCoffeeFarmID is not valid")
            }
            else ->  throw SerializationException(message = "no such id !")
        }
    }

    override fun deserialize(decoder: Decoder): OBCoffeeSpaceDTO {
       val decodedString = decoder.decodeString()
        val coffeeSpace : OBCoffeeSpaceDTO= Json.decodeFromString<OBCoffeeSpaceDTO>(decodedString)
        return coffeeSpace
    }


}

@Serializable
internal data class OBHomeCoffeeBarDTO(
    @SerialName("spaceId")  val spaceId : String,
    @SerialName("userEmail") val userEmail : String,
    @SerialName("description") val description : String,
    @SerialName("gallery") val gallery : List<String>,
    @SerialName("coffeeGear")  val coffeeGear : List<OBProductListItemDTO>,
    @SerialName("coffeeBeans")  val coffeeBeans : List<OBProductListItemDTO>
){
    fun toDomainModel() : OBHomeCoffeeBar {
        return OBHomeCoffeeBar(
            spaceId = this.spaceId,
            userEmail = this.userEmail,
            description = this.description,
            gallery = this.gallery,
            coffeeGear = this.coffeeGear.map { it.toDomain() },
            coffeeBeans = this.coffeeBeans.map { it.toDomain() }
        )
    }

}


@Serializable
internal data class OBCoffeeShopDTO(
    @SerialName("spaceId")  val spaceId : String,
    @SerialName("userEmail") val userEmail : String,
    @SerialName("description") val description : String,
    @SerialName("gallery") val gallery : List<String>,
    @SerialName("coffeeGear")  val coffeeGear : List<OBProductListItemDTO>,
    @SerialName("coffeeBeans")  val coffeeBeans : List<OBProductListItemDTO>
) {
    fun toDomainModel() : OBCoffeeShop = OBCoffeeShop(
        spaceId = this.spaceId,
        userEmail = this.userEmail,
        description = this.description,
        gallery = this.gallery,
        coffeeGear = this.coffeeGear.map { it.toDomain() },
        coffeeBeans = this.coffeeBeans.map { it.toDomain() }
    )

}

@Serializable
internal data class OBCoffeeCompanyDTO(
    @SerialName("spaceId")  val spaceId : String,
    @SerialName("userEmail") val userEmail : String,
    @SerialName("description") val description : String,
    @SerialName("gallery") val gallery : List<String>,
    @SerialName("coffeeGear")  val coffeeGear : List<OBProductListItemDTO>,
    @SerialName("coffeeBeans")  val coffeeBeans : List<OBProductListItemDTO>
) {
    fun toDomainModel() : OBCoffeeCompany = OBCoffeeCompany(
        spaceId = this.spaceId,
        userEmail = this.userEmail,
        description = this.description,
        gallery = this.gallery,
        coffeeGear = this.coffeeGear.map { it.toDomain() },
        coffeeBeans = this.coffeeBeans.map { it.toDomain() }
    )
}

@Serializable
internal data class OBCoffeeFarmDTO(
    @SerialName("spaceId")  val spaceId : String,
    @SerialName("userEmail") val userEmail : String,
    @SerialName("description") val description : String,
    @SerialName("gallery") val gallery : List<String>,
    @SerialName("coffeeGear")  val coffeeGear : List<OBProductListItemDTO>,
    @SerialName("coffeeBeans")  val coffeeBeans : List<OBProductListItemDTO>
) {
    fun toDomainModel() : OBCoffeeFarm = OBCoffeeFarm(
        spaceId = this.spaceId,
        userEmail = this.userEmail,
        description = this.description,
        gallery = this.gallery,
        coffeeGear = this.coffeeGear.map { it.toDomain() },
        coffeeBeans = this.coffeeBeans.map { it.toDomain() }
    )
}