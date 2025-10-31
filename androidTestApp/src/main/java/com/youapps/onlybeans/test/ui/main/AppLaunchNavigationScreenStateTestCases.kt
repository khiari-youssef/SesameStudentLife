package com.youapps.onlybeans.test.ui.main


import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.youapps.designsystem.OBTheme
import com.youapps.onlybeans.android.R
import com.youapps.onlybeans.android.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.test.KoinTest
import org.koin.test.inject


@RunWith(JUnit4::class)
class AppLaunchNavigationScreenStateTestCases : KoinTest{

    private val instrumentationContext: Context by inject()


    @get:Rule
    val composeMainActivityTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun testMainNavigationWhenLoginScreenShouldBeTheStartDestination() {
        composeMainActivityTestRule.activity.run {
            setContent {
                    OBTheme {

                    }
            }
        }
        composeMainActivityTestRule
            .onNodeWithContentDescription("LoginButton")
            .assertExists()
    }


    @Test
    fun testMainNavigationWhenLoginScreenIsSkippedForAutologin() {
        composeMainActivityTestRule.activity.run {
           setContent {
               OBTheme {

               }
            }
        }
        composeMainActivityTestRule
            .onNodeWithContentDescription("LoginButton")
            .assertDoesNotExist()
        composeMainActivityTestRule
            .onNodeWithContentDescription("HomeScreen")
            .assertExists()

    }


    @Test
    fun testMainActivityScreenWhenAnUndefinedBiometricAuthErrorOccurs() {
        composeMainActivityTestRule.activity.run {
             setContent {
                   OBTheme {

               }
           }
        }
        composeMainActivityTestRule.onNodeWithContentDescription(
            "InfoPopup"
        ).run {
            assertExists()
            val titleText = instrumentationContext
                .resources.getString(R.string.error_biometric_temporararely_unavailable_title)
            val subTitleText = instrumentationContext
                .resources.getString(R.string.error_biometric_temporararely_unavailable_message)
            hasAnyChild(
                hasText(titleText) and hasText(subTitleText)
            )
        }

    }

    @Test
    fun testMainActivityScreenWhenANoHardwareBiometricAuthErrorOccurs() {
        composeMainActivityTestRule.activity.run {
            setContent {
                OBTheme {


                }
            }
            composeMainActivityTestRule.onNodeWithContentDescription(
                "InfoPopup"
            ).run {
                assertExists()
                val titleText = instrumentationContext
                    .resources.getString(R.string.error_unsupported_biometric_features_title)
                val subTitleText = instrumentationContext
                    .resources.getString(R.string.error_unsupported_biometric_features_message)
                hasAnyChild(
                    hasText(titleText) and hasText(subTitleText)
                )
            }

        }
    }


    @Test
    fun testMainActivityScreenWhenHardwareUnavailableBiometricAuthErrorOccurs() {
        composeMainActivityTestRule.activity.run {
            setContent{
           OBTheme {


               }
           }
        }
        composeMainActivityTestRule.onNodeWithContentDescription(
            "InfoPopup"
        ).run {

        }

    }

    @Test
    fun testMainActivityScreenWhenUnavailableBiometricAuthErrorOccurs() {
        composeMainActivityTestRule.activity.run {
            setContent {
                OBTheme {

                }
            }
           }
        composeMainActivityTestRule.onNodeWithContentDescription(
            "InfoPopup"
        ).run {


        }
    }


}