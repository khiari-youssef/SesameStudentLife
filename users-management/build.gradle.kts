plugins {
    id("sesame.android.feature")
    alias(libs.plugins.compose.compiler)
}

android.run {
    namespace = "tn.sesame.users_management"
    defaultConfig.setConsumerProguardFiles(
       listOf("consumer-rules.pro")
    )

}

dependencies {
    implementation(projects.shared)
    implementation(projects.designsystem)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlin.coroutines)
    implementation(libs.androidx.splashscreen)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.bundles.composelibs)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.jetpack.viewmodel.core)
}