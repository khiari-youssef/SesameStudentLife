package com.youapps.onlybeans.di

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.youapps.onlybeans.OnlyBeansDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseModule : Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(OnlyBeansDatabase.Schema.synchronous(), get(), DATABASE_FILE_NAME)
    }
    single<ColumnAdapter<Long,ULong >> {
        object :ColumnAdapter<Long, ULong>{
            override fun decode(databaseValue: ULong): Long = databaseValue.toLong()

            override fun encode(value: Long): ULong = value.toULong()
        }
    }
    single<OnlyBeansDatabase>{
        OnlyBeansDatabase(
            get<SqlDriver>()
        )
    }
}