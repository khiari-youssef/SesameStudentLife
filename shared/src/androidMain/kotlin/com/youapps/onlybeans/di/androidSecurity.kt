package com.youapps.onlybeans.di

import org.koin.dsl.module
import com.youapps.onlybeans.security.BiometricAuthService

val androidSecurityModule = module {
    factory {
        BiometricAuthService(get())
    }
}