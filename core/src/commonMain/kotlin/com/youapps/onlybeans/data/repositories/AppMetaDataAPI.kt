package com.youapps.onlybeans.data.repositories

import com.youapps.onlybeans.data.dataSources.OBCountry
import kotlinx.coroutines.flow.Flow


interface AppMetaDataAPI {
    suspend fun initAppData()
     fun getCountriesList(limit: Int,offset: Int): Flow<List<OBCountry>>
     suspend fun getCountryByCode(countryCode : String) : OBCountry?
}
internal expect class AppMetaDataAPIImpl : AppMetaDataAPI {

 override suspend fun initAppData()
 override  fun getCountriesList(limit: Int,offset: Int) : Flow<List<OBCountry>>
 override suspend fun getCountryByCode(countryCode: String): OBCountry?

}