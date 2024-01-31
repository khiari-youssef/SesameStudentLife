package tn.sesame.spm.data

import tn.sesame.spm.data.dto.SesameClassDTO
import tn.sesame.spm.data.dto.SesameUserDTO
import tn.sesame.spm.data.dto.UserRolesDTO
import tn.sesame.spm.domain.entities.SesameClass
import tn.sesame.spm.domain.entities.SesamePermission
import tn.sesame.spm.domain.entities.SesamePermissionState
import tn.sesame.spm.domain.entities.SesameRole
import tn.sesame.spm.domain.entities.SesameStudent
import tn.sesame.spm.domain.entities.SesameTeacher
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.entities.SesameUserSex


internal fun SesameUserDTO.toDomainModel() : SesameUser? = runCatching {
    when(role?.id) {
        "teacher_role" -> {
            SesameTeacher(
                registrationID = registrationID,
                firstName = firstName,
                lastName  = lastName ?: "",
                email  = email,
                sex = if (sex == "h") SesameUserSex.Male else SesameUserSex.Female,
                profilePicture = profilePicture,
                portfolioId = portfolioId,
                assignedClasses = assignedClasses?.mapNotNull {
                    it.toDomainModel()
                }!!,
                profBackground = profBackground ?: ""
            )
        }
        "student_role" -> {
            SesameStudent(
                registrationID = registrationID,
                firstName = firstName,
                lastName  = lastName ?: "",
                email  = email,
                sex = if (sex == "h") SesameUserSex.Male else SesameUserSex.Female,
                profilePicture = profilePicture,
                portfolioId = portfolioId,
                job = job ?: "",
                sesameClass = sesameClass?.toDomainModel() ?: throw NullPointerException()
            )
        }
        else -> SesameUser(
            registrationID = registrationID,
            firstName = firstName,
            lastName  = lastName ?: "",
            email  = email,
            sex = if (sex == "h") SesameUserSex.Male else SesameUserSex.Female,
            profilePicture = profilePicture
        )
    }
}.getOrNull()

internal fun SesameClassDTO.toDomainModel() : SesameClass? = runCatching {
    SesameClass(
        id = id!!,
        name = name!!,
        level = level!!,
        group = group!!
    )
}.getOrNull()

internal fun UserRolesDTO.toDomainModel() : SesameRole? = runCatching {
    SesameRole(
        id = id,
        permissions = permissions.map {
            SesamePermission(
                id = it.id,
                description = it.description ,
                state = when (it.state) {
                    "granted" -> SesamePermissionState.GRANTED,
                    "granted_with_auth" -> SesamePermissionState.REQ_AUTH
                    else -> SesamePermissionState.DENIED
                }
            )
        }
    )
}.getOrNull()