package com.youapps.onlybeans.di

import com.youapps.onlybeans.security.BiometricAuthService
import org.koin.dsl.module

val androidSecurityModule = module {
    factory {
        BiometricAuthService(get())
    }
}