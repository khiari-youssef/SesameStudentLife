package com.youapps.onlybeans.di

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
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

