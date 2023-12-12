package tn.sesame.spm.di

import org.koin.dsl.module
import tn.sesame.spm.security.BiometricAuthService

val androidSecurityModule = module {
    factory {
        BiometricAuthService(get())
    }
}