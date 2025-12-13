package com.youapps.onlybeans.domain.services




object OBFormValidator {

    const val MIN_TEXT_AREA_LENGTH = 10
    const val MAX_TEXT_AREA_LENGTH = 255

    val linkRegExp = Regex("^https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)$")



fun matchesRequiredRule(input : String?) : Boolean = input?.run {
    isNotBlank()
} ?: false


fun matchesMinCharsRule(input : String) : Boolean = input.length >= MIN_TEXT_AREA_LENGTH
fun matchesMaxCharsRule(input : String) : Boolean = input.length <= MAX_TEXT_AREA_LENGTH

fun matchesCharRangeRule(input : String) : Boolean = matchesMaxCharsRule(input) && matchesMinCharsRule(input)


fun matchesOnlyLettersRule(input : String) : Boolean = input.trim().all {
    it == ' ' || it.isLetter()
}

fun matchesOnlyDigitsRule(input : String) : Boolean = input.trim().all {
        it.isDigit()
}


fun matchesLinkRule(input : String) : Boolean = input.trim().matches(linkRegExp)


}