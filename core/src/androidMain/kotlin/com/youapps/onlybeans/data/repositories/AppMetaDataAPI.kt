package com.youapps.onlybeans.data.repositories

import android.content.Context
import com.youapps.onlybeans.R
import com.youapps.onlybeans.data.dataSources.AppMetaDataSource
import com.youapps.onlybeans.data.dataSources.OBCountry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext




 internal actual class AppMetaDataAPIImpl(
    private val applicationContext: Context,
    private val dataSource : AppMetaDataSource
) : AppMetaDataAPI {

     actual override suspend fun initAppData() {
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

     actual override fun getCountriesList(limit: Int,offset: Int) : Flow<List<OBCountry>> = flow{
        val countries = dataSource.getCountriesList(limit = limit, offset = offset)
        emit(countries)
    }

     actual override suspend fun getCountryByCode(countryCode: String): OBCountry? =dataSource.getCountryByCode(countryCode = countryCode)
 }