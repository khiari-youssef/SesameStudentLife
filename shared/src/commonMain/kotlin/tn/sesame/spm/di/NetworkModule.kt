package tn.sesame.spm.di

import org.koin.core.module.Module
import org.koin.core.qualifier.named

internal val RestClientImplTag = named("RestClientImplTag")

internal const val DEFAULT_HOST : String = "localhost"
internal const val DEFAULT_PORT : Int = 7985
internal expect val networkModule : Module