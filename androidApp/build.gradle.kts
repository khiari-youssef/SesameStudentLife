plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-parcelize")
}

android {
    namespace = "tn.sesame.spm.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "tn.sesame.spm.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "tn.sesame.spm.ApplicationTestRunner"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/versions/9/previous-compilation-data.bin"
        }
    }
    buildTypes {
        getByName("debug"){
            isDebuggable = true
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        reportDir = "$rootDir/test-reports"
        resultsDir = "$rootDir/test-results"
        managedDevices {
               localDevices{
                   create("Android13Pixel7ProDevice"){
                       device = "Pixel 7 Pro"
                       // ATDs currently support only API level 30.
                       apiLevel = 33
                       // You can also specify "google-atd" if you require Google Play Services.
                       systemImageSource = "aosp-atd"
                   }
               }
        }
    }

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