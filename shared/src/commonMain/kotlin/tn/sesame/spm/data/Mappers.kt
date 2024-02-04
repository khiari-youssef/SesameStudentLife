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

internal fun SesameClass.toDTOString() : String = "$id:$name:$level:$group"
internal fun String.toSesameClass() : SesameClass? = split(":").runCatching {
    SesameClass(
        id = get(0),
        name = get(1),
        level = get(2),
        group = get(3)
    )
}.getOrNull()

internal fun List<SesameClass>.toDTOString() : String = joinToString(",") {
    it.toDTOString()
}

internal fun String.toSesameClasses() : List<SesameClass?> = split(",").map {
    it.toSesameClass()
}

internal fun SesameUserSex.toDTOString() = when (this){
    SesameUserSex.Male -> "m"
    SesameUserSex.Female -> "f"
}
internal fun String.toEnumSex() : SesameUserSex= when (this){
    "f" -> SesameUserSex.Female
    else -> SesameUserSex.Male
}

internal fun SesameUserDTO.toDomainModel() : SesameUser? = runCatching {
    when(role?.id) {
        "teacher_role" -> {
            SesameTeacher(
                registrationID = registrationID,
                firstName = firstName,
                lastName  = lastName ?: "",
                email  = email,
                sex = sex.toEnumSex(),
                profilePicture = profilePicture,
                portfolioId = portfolioId,
                assignedClasses = assignedClasses?.mapNotNull {
                    it.toDomainModel()
                }!!,
                profBackground = profBackground ?: "",
                role = role.toDomainModel() ?: SesameRole.getDefaultRoleForID(SesameRole.TEACHER)
            )
        }
        "student_role" -> {
            SesameStudent(
                registrationID = registrationID,
                firstName = firstName,
                lastName  = lastName ?: "",
                email  = email,
                sex = sex.toEnumSex(),
                profilePicture = profilePicture,
                portfolioId = portfolioId,
                job = job ?: "",
                sesameClass = sesameClass?.toDomainModel() ?: throw NullPointerException(),
                role = role.toDomainModel() ?: SesameRole.getDefaultRoleForID(SesameRole.STUDENT)
            )
        }
        else -> SesameUser(
            registrationID = registrationID,
            firstName = firstName,
            lastName  = lastName ?: "",
            email  = email,
            sex = sex.toEnumSex(),
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
                    "granted" -> SesamePermissionState.GRANTED
                    "granted_with_auth" -> SesamePermissionState.REQ_AUTH
                    else -> SesamePermissionState.DENIED
                }
            )
        }
    )
}.getOrNull()