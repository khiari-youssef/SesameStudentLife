package com.youapps.onlybeans.domain.valueobjects


sealed interface OBAuthInterface{

    data class OBTokenLogin(
        val value : String
    ) : OBAuthInterface

    data class OBCredentialsLogin(
        val password : String,
        val email : String
    ) : OBAuthInterface
}