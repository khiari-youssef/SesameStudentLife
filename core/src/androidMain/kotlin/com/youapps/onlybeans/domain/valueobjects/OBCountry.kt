package com.youapps.onlybeans.domain.valueobjects



data class OBCountry(
    val name : String,
    val code : String,
    val flagUrl : String?=null,
    val cities : List<String>
)