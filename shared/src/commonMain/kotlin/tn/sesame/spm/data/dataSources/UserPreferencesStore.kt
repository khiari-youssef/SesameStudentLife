package tn.sesame.spm.data.dataSources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserPreferencesStore(
   private val preferences: DataStore<Preferences>
) {


    companion object{
        private val IS_AUTOLOGIN_ENABLED_KEY : Preferences.Key<Boolean> = booleanPreferencesKey("IS_AUTOLOGIN_ENABLED_KEY")
    }

fun isAutoLoginEnabled() : Flow<Boolean?> = preferences
.data.map { prefs->
prefs[IS_AUTOLOGIN_ENABLED_KEY]
}.flowOn(Dispatchers.IO)


suspend fun setAutoLoginEnabled(isEnabled : Boolean) = withContext(Dispatchers.IO){
    preferences.edit { mutablePrefs->
        mutablePrefs[IS_AUTOLOGIN_ENABLED_KEY] = isEnabled
    }
}

}