plugins {
    id("com.android.test")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "tn.sesame.android.test"
    targetProjectPath = ":androidApp"
    compileSdk = extensions.getByType<VersionCatalogsExtension>().named("libs").findVersion("compileSdk").get().toString().toInt()
    defaultConfig.minSdk = extensions.getByType<VersionCatalogsExtension>().named("libs").findVersion("minSdk").get().toString().toInt()
    defaultConfig.testApplicationId = "tn.sesame.spm.android.test"
    defaultConfig.testInstrumentationRunner = "tn.sesame.spm.test.configuration.ApplicationTestRunner"
    defaultConfig.proguardFile("proguard-rules.pro")

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
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlin.coroutines)
    implementation(libs.androidx.splashscreen)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.jetpack.viewmodel.core)
    debugImplementation(libs.compose.test.rule)
    implementation(libs.bundles.composelibs)
    implementation(libs.bundles.testingLibs)
    implementation("androidx.startup:startup-runtime:1.1.1")
}
