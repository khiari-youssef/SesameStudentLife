package com.youapps.onlybeans.domain.entities.products


abstract  class OBCoffeeSpace(
    val spaceId : String,
    val userEmail : String,
    val description : String,
    val gallery : List<String>
)


class OBHomeCoffeeBar(
    val coffeeGear : List<OBProductListItem>,
    val coffeeBeans : List<OBProductListItem>,
    spaceId : String,
    userEmail : String,
    description : String,
    gallery : List<String>
) : OBCoffeeSpace(spaceId, userEmail, description, gallery)

class OBCoffeeShop(

    spaceId : String,
    userEmail : String,
    description : String,
    gallery : List<String>
) : OBCoffeeSpace(spaceId, userEmail, description, gallery) {

}

class OBCoffeeCompany(

    spaceId : String,
    userEmail : String,
    description : String,
    gallery : List<String>
) : OBCoffeeSpace(spaceId, userEmail, description, gallery) {

}

class OBCoffeeFarm(

    spaceId : String,
    userEmail : String,
    description : String,
    gallery : List<String>
) : OBCoffeeSpace(spaceId, userEmail, description, gallery) {

}