package tn.sesame.spm.data.exceptions


enum class HttpErrorType {
    UnauthorizedAccess,InternalServerError,BadRequest,NotFound,Undefined
}

 data class CustomHttpException(override val message: String?, override val cause: Throwable?,val errorType : HttpErrorType) : Exception()