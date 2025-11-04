import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.dialogs.ImageViewerDialog
import com.youapps.designsystem.components.lists.CarouselState
import com.youapps.designsystem.components.lists.OBCarousel
import com.youapps.designsystem.components.loading.shimmerEffect
import com.youapps.designsystem.components.text.OBParagraphMode
import com.youapps.designsystem.components.text.OBParagraphText
import com.youapps.users_management.R
import com.youapps.users_management.ui.profile.ProfileScreenState
import com.youapps.users_management.ui.profile.UserProfilePreview
import com.youapps.users_management.ui.profile.UserProfilePreviewLoader
import com.youapps.designsystem.R as ds


@Composable
fun ProfileScreen(
modifier: Modifier = Modifier,
screenState: ProfileScreenState,
onRefreshProfile :  ()->Unit,
onPullToRefreshProfile :  ()->Unit,
onLogOutClicked :  ()->Unit
) {
    val isLargeScreen = LocalConfiguration.current.run {
        (orientation == Configuration.ORIENTATION_LANDSCAPE) or (this.screenWidthDp >= 600)
    }
    val imageViewerContent : MutableState<String?> = remember {
        mutableStateOf(null)
    }
    val ptrState = rememberPullToRefreshState()

    ImageViewerDialog(
        imageUrl = imageViewerContent.value ?: "" ,
        isVisible = imageViewerContent.value != null,
        onDismissRequest = {
            imageViewerContent.value = null
        }
    )
    PullToRefreshBox(
        isRefreshing = screenState is ProfileScreenState.Loading && screenState.withPullToRefresh,
        onRefresh = onPullToRefreshProfile,
        state = ptrState,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = screenState is ProfileScreenState.Loading && screenState.withPullToRefresh,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = ptrState
            )
        },
        contentAlignment = Alignment.TopCenter
    ){

            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
            ) {

                when (screenState) {
                    is ProfileScreenState.Error -> {
                        ErrorModal(
                            title = stringResource(R.string.profile_data_error),
                            details = "",
                            onRetryAction = onRefreshProfile
                        )
                    }
                    is ProfileScreenState.Loading -> {
                        UserProfilePreviewLoader(
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    is ProfileScreenState.Loaded -> {
                        UserProfilePreview(
                            modifier = Modifier
                                .fillMaxWidth(),
                            oBUserProfile = screenState.profile.profilePreView,
                            actionButtonText = stringResource(com.youapps.users_management.R.string.edit_profile),
                            onProfileActionClicked = {

                            }
                        )
                    }

                }
                OBParagraphText(
                    modifier = Modifier
                        .shimmerEffect(screenState is ProfileScreenState.Loading)
                        .padding(
                            horizontal = 16.dp
                        )
                        .fillMaxWidth(),
                    text = if (screenState is ProfileScreenState.Loaded) screenState.profile.profileDescription else "",
                    placeholderRes = ds.string.description_placeholder,
                    expandMode = OBParagraphMode.Expandable(
                        expandActionText = stringResource(ds.string.expand_to_read_more),
                        collapseActionText =  stringResource(ds.string.collapse_to_read_less),
                        textStyle = SpanStyle(color = MaterialTheme.colorScheme.secondary)
                    )
                )

                OBCarousel(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp
                        )
                        .fillMaxWidth()
                        .height(206.dp),
                    state =  when (screenState) {
                        is ProfileScreenState.Error ->  CarouselState.Loaded(images = emptyList())
                        is ProfileScreenState.Loading ->  CarouselState.Loading
                        is ProfileScreenState.Loaded -> CarouselState.Loaded(
                            images = screenState.profile.myCoffeeSpace?.gallery ?: emptyList()
                        )
                    },
                    itemSpacing = 8.dp,
                    preferredItemWidth = 320.dp,
                    onItemClicked = { url->
                        imageViewerContent.value = url
                    }
                )
            }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 16.dp,
                    bottom = 16.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colorScheme.primary,
                onClick = onLogOutClicked
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ds.drawable.logout),
                    contentDescription = stringResource(R.string.profile_logout),
                    tint = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            }
        }
    }

}
