package com.youapps.onlybeans.domain.exception



enum class DomainErrorType{
    InvalidCredentials,
    AccountLocked,
    Undefined,
    IllegalEntityAttributes,
    Unauthorized,
    NotFound
}
 class DomainException(
    override val cause: Throwable?=null,
    override val message: String?=null,
     val errorType : DomainErrorType
) : Exception()