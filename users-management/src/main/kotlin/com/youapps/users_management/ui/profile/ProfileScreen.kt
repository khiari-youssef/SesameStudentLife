import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.R
import com.youapps.designsystem.components.dialogs.ImageViewerDialog
import com.youapps.designsystem.components.lists.CarouselData
import com.youapps.designsystem.components.lists.OBCarousel
import com.youapps.designsystem.components.menus.MenuOptions
import com.youapps.designsystem.components.text.OBParagraphMode
import com.youapps.designsystem.components.text.OBParagraphText
import com.youapps.onlybeans.domain.entities.users.OBAddress
import com.youapps.onlybeans.domain.entities.users.OBLocation
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.entities.users.OBUserProfilePreView
import com.youapps.users_management.ui.profile.UserProfilePreview


@Composable
fun ProfileScreen(
modifier: Modifier = Modifier,
menuOptions : MenuOptions,
oBUserProfile: OBUserProfile,
onMenuItemClicked : (optionIndex : Int)->Unit,
onLogOutClicked :  ()->Unit
) {
    val isLargeScreen = LocalConfiguration.current.run {
        (orientation == Configuration.ORIENTATION_LANDSCAPE) or (this.screenWidthDp >= 600)
    }
    val imageViewerContent : MutableState<String?> = remember {
        mutableStateOf(null)
    }

    ImageViewerDialog(
        imageUrl = imageViewerContent.value ?: "" ,
        isVisible = imageViewerContent.value != null,
        onDismissRequest = {
            imageViewerContent.value = null
        }
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
    ) {
        UserProfilePreview(
            modifier = Modifier
                .fillMaxWidth(),
            oBUserProfile = OBUserProfilePreView(
                id = "",
                fullName = "Youssef Khiari",
                status = "Home coffee barista",
                coverPicture = "https://images.unsplash.com/photo-1601813913455-118810e79277?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1170",
                profilePicture = "https://avatar.iran.liara.run/public",
                address = OBAddress(
                    country = "Tunisia",
                    city = "Tunis",
                    location = OBLocation(
                        12.44,12.554
                    )
                )
            )
        )
        OBParagraphText(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp
                )
                .fillMaxWidth(),
            text = "aaaaaaaa".repeat(12).repeat(4),
            placeholderRes = R.string.description_placeholder,
            expandMode = OBParagraphMode.Expandable(
                expandActionText = "Read more",
                collapseActionText = "Read less",
                textStyle = SpanStyle(color = MaterialTheme.colorScheme.secondary)
            )
        )
        val data = CarouselData(
            images = listOf(
                "https://images.unsplash.com/photo-1572982270699-473dfa34d7e7?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1170",
                "https://images.unsplash.com/photo-1522126039546-182129aa0b93?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1331",
                "https://images.unsplash.com/photo-1610889556528-9a770e32642f?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1315",
                "https://images.unsplash.com/photo-1581068106019-5aa70c6ab424?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1171"
            )
        )
        OBCarousel(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp
                )
                .fillMaxWidth()
                .height(206.dp),
            data = data,
            itemSpacing = 8.dp,
            preferredItemWidth = 320.dp,
            onItemClicked = { url->
                imageViewerContent.value = url
            }
        )


    }
}
