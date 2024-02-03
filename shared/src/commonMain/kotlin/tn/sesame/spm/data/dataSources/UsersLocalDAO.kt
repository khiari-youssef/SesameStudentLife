package tn.sesame.spm.data.dataSources

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import tn.sesame.spmdatabase.SesameLogin
import tn.sesame.spmdatabase.SesameWorksLifeDatabase

internal class UsersLocalDAO(
    private val sesameWorksLifeDatabase : SesameWorksLifeDatabase
) {


suspend fun saveUserLogin(sesameAuthToken: String,roleID : String?,registrationID : String) {
    sesameWorksLifeDatabase.sesameWorksDatabaseQueries
        .insertNewLogin(
            token = sesameAuthToken,
            role_id = roleID,
            registrationID = registrationID,
            loginTimestamp = null
        )
}

suspend fun getLastUsedLogin() : SesameLogin? = withContext(Dispatchers.IO)  {
    sesameWorksLifeDatabase.sesameWorksDatabaseQueries.selecteMostRecentlySavedLogin().executeAsOneOrNull()
}




}