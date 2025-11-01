package com.youapps.users_management.ui.profile

import OBButton
import OBButtonSize
import OBCircleImageXL
import OBButtonTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.youapps.designsystem.R
import com.youapps.designsystem.components.images.OBCoverPhoto
import com.youapps.onlybeans.domain.entities.users.OBUserProfilePreView


@Composable
fun UserProfilePreview(
    modifier: Modifier = Modifier,
   // onProfileActionClicked : ()->Unit,
    oBUserProfile : OBUserProfilePreView
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (coverImageRef,profileAvatarPicture,detailSection)= createRefs()
        OBCoverPhoto(
            modifier = Modifier
                .height(
                    height = 128.dp
                ).fillMaxWidth()
                .constrainAs(coverImageRef){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            url = oBUserProfile.coverPicture
        )
        OBCircleImageXL(
            modifier = Modifier.constrainAs(profileAvatarPicture){
                start.linkTo(parent.start,16.dp)
                top.linkTo(coverImageRef.bottom,(-36).dp)
            } ,
            uri = oBUserProfile.profilePicture,
            placeholderRes = R.drawable.ic_profile_placeholder_male,
            errorRes = R.drawable.ic_profile_placeholder_male
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.constrainAs(detailSection){
                start.linkTo(parent.start,16.dp)
                end.linkTo(parent.end,16.dp)
                width = Dimension.fillToConstraints
                top.linkTo(profileAvatarPicture.bottom,8.dp)
            }
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = oBUserProfile.fullName,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    ),
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
                Text(
                    text = oBUserProfile.status,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF5E5E5E)
                    ),
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
                oBUserProfile.address?.run {
                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start)
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(height = 12.dp, width = 10.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_location_pin),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "$country, ${city ?: ""}, ${line ?: ""}, ${postalCode ?: ""}",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFFA55233)
                            ),
                            textAlign = TextAlign.Start,
                            maxLines = 1
                        )
                    }
                }

            }
            OBButton(
                text = "Edit profile",
                size = OBButtonSize.Small,
                theme = OBButtonTheme.ContainedSecondary,
                onClick = {

                }
            )

        }

    }
}