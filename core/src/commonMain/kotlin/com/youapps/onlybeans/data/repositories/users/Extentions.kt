package com.youapps.onlybeans.data.repositories.users

import com.youapps.onlybeans.data.exceptions.CustomHttpException
import com.youapps.onlybeans.data.exceptions.HttpErrorType
import com.youapps.onlybeans.domain.exception.DomainErrorType
import com.youapps.onlybeans.domain.exception.DomainException

 internal suspend fun  <T> OBUsersRepositoryInterface.toDomainAuthenticationError(withCredentials : Boolean = true, authOperation : suspend ()->T) : T{
    return try {
        authOperation()
    } catch (th : Throwable){
        th.printStackTrace()
        throw if (th is CustomHttpException){
            if (th.errorType == HttpErrorType.UnauthorizedAccess){
                DomainException(errorType = if (withCredentials) DomainErrorType.InvalidCredentials else DomainErrorType.Unauthorized)
            } else if (th.errorType == HttpErrorType.Locked){
                DomainException(errorType = DomainErrorType.AccountLocked)
            } else DomainException(errorType = DomainErrorType.Undefined)
        } else DomainException(errorType = DomainErrorType.Undefined)
    }
}