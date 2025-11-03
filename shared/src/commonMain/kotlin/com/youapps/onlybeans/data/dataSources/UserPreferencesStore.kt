package com.youapps.onlybeans.data.dataSources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserPreferencesStore(
    private val preferences: DataStore<Preferences>
) {


    companion object {
        private val OB_USER_TOKEN: Preferences.Key<String> = stringPreferencesKey("OB_USER_TOKEN")
        private val OB_USER_EMAIL: Preferences.Key<String> = stringPreferencesKey("OB_USER_EMAIL")
    }

    fun getUserEmail(): Flow<String?> = preferences
        .data.map { prefs ->
            prefs[OB_USER_EMAIL]
        }.flowOn(Dispatchers.IO)


    fun getUserToken(): Flow<String?> = preferences
        .data.map { prefs ->
            prefs[OB_USER_TOKEN]
        }.flowOn(Dispatchers.IO)



    suspend fun setUserToken(email : String,token: String) = withContext(Dispatchers.IO) {
        preferences.edit { mutablePrefs ->
            mutablePrefs[OB_USER_EMAIL] = email
            mutablePrefs[OB_USER_TOKEN] = token
        }
    }

    suspend fun clearUserData() {
        withContext(Dispatchers.IO) {
            preferences.edit { mutablePrefs ->
                mutablePrefs.clear()
            }
        }
    }


}