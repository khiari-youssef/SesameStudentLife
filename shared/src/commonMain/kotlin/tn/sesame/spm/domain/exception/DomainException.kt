package tn.sesame.spm.domain.exception



enum class DomainErrorType{
    InvalidCredentials,
    AccountLocked,
    Undefined,
    IllegalEntityAttributes,
    Unauthorized
}
 class DomainException(
    override val cause: Throwable?=null,
    override val message: String?=null,
     val errorType : DomainErrorType
) : Exception()