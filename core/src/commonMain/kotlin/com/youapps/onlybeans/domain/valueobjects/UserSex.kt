package com.youapps.onlybeans.domain.valueobjects

import io.ktor.util.toUpperCasePreservingASCIIRules

enum class UserSex{
    Male,Female
}



fun String.decodeToUserSex() : UserSex? {
    return when(this.toUpperCasePreservingASCIIRules()) {
        "M" -> UserSex.Male
        "F" -> UserSex.Female
        else -> runCatching { UserSex.valueOf(this) }.getOrNull()
    }
}