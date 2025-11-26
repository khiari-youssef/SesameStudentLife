plugins {
    id("sesame.android.feature")
    alias(libs.plugins.compose.compiler)
}

android.run {
    namespace = "com.youapps.users_management"
    defaultConfig.setConsumerProguardFiles(
       listOf("consumer-rules.pro")
    )

}

dependencies {
    implementation(projects.core)
    implementation(projects.designsystem)
    //implementation(projects.androidServices)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlin.coroutines)
    implementation(libs.androidx.splashscreen)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.bundles.composelibs)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.jetpack.viewmodel.core)
}