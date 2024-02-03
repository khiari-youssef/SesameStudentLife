package tn.sesame.spm.data.dataSources

import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import tn.sesame.spm.data.dto.RolePermission
import tn.sesame.spm.data.dto.SesameClassDTO
import tn.sesame.spm.data.dto.SesameLoginResponseWrapper
import tn.sesame.spm.data.dto.SesameUserDTO
import tn.sesame.spm.data.dto.UserRolesDTO

internal class UsersRemoteDAO(
    private val restClient : HttpClient
) {

    private val roles : List<UserRolesDTO> = listOf(
        UserRolesDTO(
            id =  "student_role",
            permissions = listOf(
                RolePermission(
                id = "project_read",
                description = "Allows/disallows read access to projects",
                state = "granted"
                  ),
                RolePermission(
                    id = "project_join",
                    description = "Allows/disallows requesting to join projects",
                    state = "granted_with_auth"
                ),
                RolePermission(
                    id = "profiles_view",
                    description = "Allows/disallows viewing profiles",
                    state = "granted"
                ),
                RolePermission(
                    id = "account_login",
                    description = "Allows/disallows account login/logout",
                    state = "granted"
                )
            )
        ),
        UserRolesDTO(
            id =  "teacher_role",
            permissions = listOf(
                RolePermission(
                    id = "project_read",
                    description = "Allows/disallows read access to projects",
                    state = "granted"
                ),
                RolePermission(
                    id = "project_write",
                    description = "Allows/disallows write access to projects which includes creation,modification_removal",
                    state = "granted_with_auth"
                ),
                RolePermission(
                    id = "project_requests_manage",
                    description = "Allows/disallows approving or disapproving projects join requests",
                    state = "granted_with_auth"
                ),
                RolePermission(
                    id = "profiles_view",
                    description = "Allows/disallows viewing profiles",
                    state = "granted"
                ),
                RolePermission(
                    id = "account_login",
                    description = "Allows/disallows account login/logout",
                    state = "granted"
                )
            )
        ),
        UserRolesDTO(
            id =  "admin_role",
            permissions = listOf()
        ),
        UserRolesDTO(
            id =  "undefined_role",
            permissions = listOf()
        )
    )

    private val sesameClasses= listOf(
        SesameClassDTO(
            id = "ingta4-a",
            name = "ingta",
            level = "4",
            group = "a"
        ),
        SesameClassDTO(
            id = "ingta4-b",
            name = "ingta",
            level = "4",
            group = "b"
        ),
        SesameClassDTO(
            id = "ingta4-c",
            name = "ingta",
            level = "4",
            group = "c"
        )
    )

    private val credentialsLogins : Map<String,String> = mapOf(
        "youssef.khiari@sesame.com.tn" to "007007",
        "amira@sesame.com.tn" to "1111"
    )

    private val tokenLogins : Map<String,String> = mapOf(
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InlvdXNzZWYua2hpYXJpQHNlc2FtZS5jb20udG4iLCJwYXNzd29yZCI6IjAwNzAwNyJ9.6a4KlE6CaP6NUyA1zDhPgHzQ7irJS5Y3MNw-RCEqzSM" to "youssef.khiari@sesame.com.tn",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFtaXJhQHNlc2FtZS5jb20udG4iLCJwYXNzd29yZCI6IjExMTEifQ.tTN0z-dKcWXRtzheCKDSCv6zn5yjwNdvKGDoqddeTXI" to "amira@sesame.com.tn"
    )

    private val users : List<SesameUserDTO> = listOf(
        SesameUserDTO(
            registrationID = "9c057fe2d493527a6f08a405f32387e96f569472",
            firstName = "Youssef",
            lastName = "Khiari",
            email = "youssef.khiari@sesame.com.tn",
            sex = "h",
            profilePicture = "https://scontent.ftun1-2.fna.fbcdn.net/v/t39.30808-6/221326371_3098397677063019_1217079727753827358_n.jpg?_nc_cat=106&ccb=1-7&_nc_sid=efb6e6&_nc_ohc=hcnmyhzRLXgAX-4at3u&_nc_ht=scontent.ftun1-2.fna&oh=00_AfA1eolX_v17fV3X6z2dEUfjmOZGPjRCbgvgjF2TVkazpw&oe=65B9E918",
            portfolioId = "",
            job = "",
            sesameClass = sesameClasses.first(),
            role = roles.find {
                it.id == "student_role"
            }
        ),
        SesameUserDTO(
            registrationID = "6d0266d23367f7d25d767aeb5bb6a01ed1f04fa7",
            firstName = "Amira",
            lastName = "",
            email = "amira@sesame.com.tn",
            sex = "f",
            profilePicture = "https://img.freepik.com/free-photo/portrait-beautiful-young-businesswoman-glasses-gray-background_1142-51193.jpg",
            profBackground = "BI manager",
            assignedClasses = sesameClasses,
            role = roles.find {
                it.id == "teacher_role"
            }
        ),
        SesameUserDTO(
            registrationID = "",
            firstName = "None",
            lastName = "None",
            email = "",
            sex = "",
            profilePicture = "",
            portfolioId = "",
            job = "",
            profBackground = "",
            assignedClasses = listOf()
        )
    )


    suspend fun fetchEmailAndPasswordLoginAPI(
        email : String,password : String
    ) : SesameLoginResponseWrapper = withContext(Dispatchers.IO){
         delay(1000)
       val userExists = credentialsLogins.entries.any { (dbemail,dbpassword)->
            dbemail == email && dbpassword == password
        }
        if (userExists) {
           val profile = users.first {
                it.email == email
            }
            val generateToken : String = tokenLogins.entries.find {
                it.value == email
            }?.key ?: "gigep52dhh5"
            SesameLoginResponseWrapper(
                token = generateToken,
                data = profile
            )
        } else throw Exception()
    }

    suspend fun fetchTokenLoginAPI(
       token : String
    ) : SesameLoginResponseWrapper  = withContext(Dispatchers.IO) {
        tokenLogins[token]?.let { email ->
            val profile = users.first {
                it.email == email
            }
            SesameLoginResponseWrapper(
                token = token,
                data = profile
            )
        } ?: throw Exception()
    }

}