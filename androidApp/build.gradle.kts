plugins {
    id("sesame.android.application")
    id("kotlin-parcelize")
    alias(libs.plugins.google.playservices)
}

android.run {
    namespace = "tn.sesame.spm.android"
    defaultConfig.applicationId = "tn.sesame.spm.android"
    defaultConfig.proguardFile("proguard-rules.pro")
}

dependencies {
    api(projects.shared)
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
    implementation(platform(libs.firebase.bom))
}