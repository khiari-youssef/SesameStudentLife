package tn.sesame.spm.domain.entities

enum class SesamePermissionState{
    GRANTED,DENIED,REQ_AUTH
}

class SesameRole(
    val id : String?=null,
    val permissions : List<SesamePermission>? = listOf()
){
    companion object{
        const val NONE : String = "0x00"
        const val STUDENT : String = "0x01"
        const val TEACHER : String = "0x01"
    }

}

data class SesamePermission(
    val id : String,
    val description : String,
    val state : SesamePermissionState
)