package com.youapps.onlybeans.data.exceptions


enum class HttpErrorType {
    UnauthorizedAccess,InternalServerError,BadRequest,NotFound,Undefined,Locked
}

 data class CustomHttpException(override val message: String?=null, override val cause: Throwable?=null,val errorType : HttpErrorType) : Exception()