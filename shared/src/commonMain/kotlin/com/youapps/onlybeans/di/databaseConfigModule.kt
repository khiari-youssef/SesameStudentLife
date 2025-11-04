package com.youapps.onlybeans.di

import kotlinx.serialization.json.Json
import org.koin.core.module.Module

internal const val DATABASE_FILE_NAME : String = "AppDatabase.db"


fun List<String>.toDBJsonRow() : String? {
    return    runCatching {
        val dbRow : Map<String, List<String>> = mapOf(
            "row" to this
        )
        Json.encodeToString(dbRow)
    }.getOrNull()
}

fun String.fromDBJsonRow() : List<String>? {
   return  runCatching {
       val dbRow : Map<String,List<String>> = Json.decodeFromString(this)
       return dbRow["row"]
   }.getOrNull()
}

internal expect val databaseModule : Module

