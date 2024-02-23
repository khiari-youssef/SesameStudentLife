plugins {
    id("sesame.android.application")
    id("kotlin-parcelize")
}

android.run {
    namespace = "tn.sesame.spm.android"
    defaultConfig.applicationId = "tn.sesame.spm.android"
}

dependencies {
    api(projects.shared)
    implementation(projects.designsystem)
    implementation(projects.usersManagement)
    implementation(libs.bundles.composelibs)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlin.coroutines)
    implementation(libs.androidx.splashscreen)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.jetpack.viewmodel.core)

}