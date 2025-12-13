package com.youapps.onlybeans.data.repositories

import com.youapps.onlybeans.data.dataSources.OBCountry
import kotlinx.coroutines.flow.Flow


actual class AppMetaDataAPIImpl : AppMetaDataAPI {

    actual override suspend fun initAppData() {
        TODO("iOs Implementation")
    }

    actual override  fun getCountriesList(limit: Int,offset: Int) : Flow<List<OBCountry>> {
        TODO("iOs Implementation")
    }

    actual override suspend fun getCountryByCode(countryCode: String): OBCountry? {
        TODO("Not yet implemented")
    }
}