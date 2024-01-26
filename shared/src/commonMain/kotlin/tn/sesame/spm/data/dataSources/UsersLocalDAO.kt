package tn.sesame.spm.data.dataSources

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spmdatabase.SesameLogin
import tn.sesame.spmdatabase.SesameWorksLifeDatabase

internal class UsersLocalDAO(
    private val sesameWorksLifeDatabase : SesameWorksLifeDatabase
) {


suspend fun getLastUsedLogin() : SesameLogin? = withContext(Dispatchers.IO)  {
    sesameWorksLifeDatabase.sesameWorksDatabaseQueries.selecteMostRecentlySavedLogin().executeAsOneOrNull()
}




}