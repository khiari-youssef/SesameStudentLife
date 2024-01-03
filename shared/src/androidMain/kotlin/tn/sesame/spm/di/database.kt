package tn.sesame.spm.di

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module
import tn.sesame.spmdatabase.SesameWorksLifeDatabase

actual val databaseModule : Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(SesameWorksLifeDatabase.Schema.synchronous(), get(), DATABASE_FILE_NAME)
    }
    single<SesameWorksLifeDatabase>{
        SesameWorksLifeDatabase(get<SqlDriver>())
    }
}