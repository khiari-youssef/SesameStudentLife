package tn.sesame.spm.domain.exception



enum class DomainErrorType{
    InvalidCredentials,AuthenticationFailed,InvalidSesameEmailAddress,Undefined,IllegalEntityAttributes
}
 class DomainException(
    override val cause: Throwable?,
    override val message: String?,
     val errorType : DomainErrorType
) : Exception()