package tn.sesame.spm.domain.exception



enum class DomainErrorType{
    InvalidCredentials,
    AccountLocked,
    AuthenticationFailed,
    Undefined,
    IllegalEntityAttributes,
    Unauthorized
}
 class DomainException(
    override val cause: Throwable?,
    override val message: String?,
     val errorType : DomainErrorType
) : Exception()