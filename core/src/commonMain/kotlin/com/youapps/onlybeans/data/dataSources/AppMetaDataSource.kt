package com.youapps.onlybeans.data.dataSources

import com.youapps.onlybeans.OnlyBeansDatabase
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext


data class OBCountry(
    val countryCode : String,
    val phonePrefix : String,
    val countryName : String,
    val countryFlag : String
)

class AppMetaDataSource(
    private val onlyBeansDatabase : OnlyBeansDatabase,
    private val restClient : HttpClient
) {


suspend fun getCountriesList() : List<OBCountry> = withContext(Dispatchers.IO){
       onlyBeansDatabase.onlyBeansDatabaseQueries.getCountries().executeAsList().map { db->
        OBCountry(
            countryCode = db.countryCode,
            phonePrefix = db.phonePrefix,
            countryName = db.countryName,
            countryFlag = db.countryFlag
        )
    }
}


suspend fun setCountriesList(list:  List<OBCountry> ) = withContext(Dispatchers.IO){
        onlyBeansDatabase.onlyBeansDatabaseQueries.transactionWithResult{
            return@transactionWithResult list.forEach {
                onlyBeansDatabase.onlyBeansDatabaseQueries.insertCountries(
                    countryCode = it.countryCode,
                    phonePrefix = it.phonePrefix,
                    countryName = it.countryName,
                    countryFlag = it.countryFlag
                )
            }
        }
    }



}