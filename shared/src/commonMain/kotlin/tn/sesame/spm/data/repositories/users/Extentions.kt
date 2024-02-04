package tn.sesame.spm.data.repositories.users

import tn.sesame.spm.data.exceptions.CustomHttpException
import tn.sesame.spm.data.exceptions.HttpErrorType
import tn.sesame.spm.domain.exception.DomainErrorType
import tn.sesame.spm.domain.exception.DomainException

 internal suspend fun  <T> UsersRepositoryInterface.toDomainAuthenticationError(withCredentials : Boolean = true,authOperation : suspend ()->T) : T{
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