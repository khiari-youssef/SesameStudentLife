plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-parcelize")
    id("sesame.android.application")
}

android.run {
    namespace = "tn.sesame.spm.android"
    defaultConfig.applicationId = "tn.sesame.spm.android"
    defaultConfig.versionCode = 1
    defaultConfig.versionName = "1.0"
    defaultConfig.testInstrumentationRunner = "tn.sesame.spm.ApplicationTestRunner"
}

dependencies {
    implementation(projects.shared)
    implementation(projects.designsystem)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.foundation)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.animation.core)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlin.coroutines)
    implementation(libs.compose.animation.graphics)
    implementation(libs.compose.constraintlayout)
    implementation(libs.androidx.splashscreen)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.coil)
    implementation(libs.compose.navigation)
    implementation(libs.compose.material)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.jetpack.credentialmanager)
    implementation(libs.jetpack.credentialManagerPlayServices)
    implementation(libs.jetpack.viewmodel.core)
    implementation(libs.jetpack.viewmodel.compose)
    implementation(libs.jetpack.viewmodel.composeutils)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.compose.test.junit)
    debugImplementation(libs.compose.test.rule)
    testImplementation(libs.koin.test)
    androidTestImplementation(libs.kotlin.test.mockito)
    androidTestImplementation(libs.koin.test.android)
}