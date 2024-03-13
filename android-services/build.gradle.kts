plugins {
    id("sesame.android.application")
}

android {
    namespace = "tn.sesame.android_services"
    defaultConfig.applicationId = "tn.sesame.android_services"
    defaultConfig.proguardFile("proguard-rules.pro")
}


dependencies {
    api(projects.shared)
    api(projects.designsystem)
    implementation(projects.designsystem)
    implementation(projects.usersManagement)
    implementation(projects.projectsManagement)
    implementation(libs.bundles.composelibs)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlin.coroutines)
    implementation(libs.androidx.splashscreen)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.jetpack.viewmodel.core)
    implementation(libs.play.services.mlkit.document.scanner)
}