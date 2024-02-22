plugins {
    id("com.android.test")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "tn.sesame.androidTestApp"
    targetProjectPath = ":androidApp"
    compileSdk = extensions.getByType<VersionCatalogsExtension>().named("libs").findVersion("compileSdk").get().toString().toInt()
    defaultConfig.minSdk = extensions.getByType<VersionCatalogsExtension>().named("libs").findVersion("minSdk").get().toString().toInt()
    defaultConfig.testApplicationId = "tn.sesame.spm.android.test"
    defaultConfig.testInstrumentationRunner = "tn.sesame.spm.test.configuration.ApplicationTestRunner"

    testOptions{
        reportDir = "$projectDir/test-reports"
        resultsDir = "$projectDir/test-results"
    }
    buildTypes {
       debug {
         isDebuggable = true
       }
    }
    packaging {
        resources.excludes += "META-INF/*"
    }

    buildFeatures{
        compose = true
        buildConfig = true
    }
    composeOptions{
        kotlinCompilerExtensionVersion = extensions.getByType<VersionCatalogsExtension>().named("libs").findVersion("compose-compiler").get().toString()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

dependencies{
    implementation(projects.designsystem)
    implementation(projects.usersManagement)
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
    implementation(libs.androidx.test.runner)
    implementation(libs.androidx.test.rules)
    implementation(libs.compose.test.junit)
    debugImplementation(libs.compose.test.rule)
    implementation(libs.koin.test)
    implementation(libs.kotlin.test.mockito)
    implementation(libs.koin.test.android)
    implementation(libs.junit)
    implementation(libs.androidx.test.ext.junit)
    implementation(libs.espresso.core)
}
