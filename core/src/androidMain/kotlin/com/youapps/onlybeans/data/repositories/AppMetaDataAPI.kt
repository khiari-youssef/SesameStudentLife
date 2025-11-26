package com.youapps.onlybeans.data.repositories

import android.content.Context
import com.youapps.onlybeans.R
import com.youapps.onlybeans.data.dataSources.AppMetaDataSource
import com.youapps.onlybeans.data.dataSources.OBCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext


 class AppMetaDataAPI(
    private val applicationContext: Context,
    private val dataSource : AppMetaDataSource
){

     suspend fun initAppData() {
        val countryCodes: List<OBCountry> = withContext(Dispatchers.Default) {
            applicationContext.resources.getStringArray(R.array.country_codes_to_prefixes_and_names)
                .map {
                    it.split("|").let { (code, prefix,name) ->
                        OBCountry(
                            countryFlag = applicationContext.getString(R.string.countries_api_url,code),
                            countryCode = code,
                            phonePrefix = prefix,
                            countryName = name
                        )
                    }
                }
        }
        withContext(Dispatchers.IO){
            dataSource.setCountriesList(countryCodes)
        }
    }

     suspend fun getCountriesList() : Flow<List<OBCountry>> = flow{
        val countries = dataSource.getCountriesList()
        emit(countries)
    }
}