package tn.sesame.spm.domain.entities

enum class SesamePermissionState{
    GRANTED,DENIED,REQ_AUTH
}

class SesameRole(
    val id : String,
    val permissions : List<SesamePermission>? = listOf()
){
    companion object{
        const val NONE : String = "none_role"
        const val STUDENT : String = "student_role"
        const val TEACHER : String = "teacher_role"

        fun getDefaultRoleForID(id : String) : SesameRole = SesameRole(id)
    }

}

data class SesamePermission(
    val id : String,
    val description : String,
    val state : SesamePermissionState
)