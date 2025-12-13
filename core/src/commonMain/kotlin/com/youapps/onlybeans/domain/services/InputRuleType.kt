package com.youapps.onlybeans.domain.services

enum class InputRuleType{
    REQUIRED,
    EMAIL_FORMAT,
    PHONE_FORMAT,
    LINK_FORMAT,
    PASSWORD_POLICY,
    LETTERS_ONLY,
    NUMBERS_ONLY,
    DATE_FORMAT,
    MIN_LENGTH,
    MAX_LENGTH
}