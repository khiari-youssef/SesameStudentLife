plugins {
    id("sesame.android.application")
}

android {
    namespace = "tn.sesame.android_services"
    defaultConfig.applicationId = "tn.sesame.android_services"
    defaultConfig.proguardFile("proguard-rules.pro")
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility  = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
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
    implementation(libs.image.labeling.custom)
        implementation( libs.androidx.camera.core)
        implementation( libs.androidx.camera.camera2)
        implementation (libs.androidx.camera.lifecycle)
        implementation (libs.androidx.camera.video)
        implementation (libs.androidx.camera.view)
        implementation (libs.androidx.camera.extensions)
    implementation(libs.play.services.mlkit.barcode.scanning)
    implementation(libs.barcode.scanning)

}