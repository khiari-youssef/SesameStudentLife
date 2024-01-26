package tn.sesame.spm.di

import org.koin.core.module.Module

internal const val DATABASE_FILE_NAME : String = "AppDatabase.db"

internal expect val databaseModule : Module

