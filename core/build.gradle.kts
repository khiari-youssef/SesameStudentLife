plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinXSer)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget {
        compilations.all {

        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "core"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlin.coroutines)
            implementation(libs.kotlin.ktor.core)
            implementation(libs.kotlin.ktor.content)
            implementation(libs.kotlin.ktor.json)
            implementation(libs.kotlin.ktor.logging)
            implementation(libs.koin.core)
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.adapter)
            implementation(libs.androidx.annotation)
            implementation(libs.androidx.collection)
            implementation(libs.androidx.datastore.preferences.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.koin.test)
        }
        androidMain.dependencies {
            implementation(libs.kotlin.ktor.android)
            implementation(libs.koin.android)
            implementation(libs.sqldelight.driver.android)
            implementation(libs.androidx.biometric.ktx)
            implementation(libs.androidx.security.crypto)
            implementation(libs.androidx.security.identity.credential)
            implementation(libs.androidx.security.app.authenticator)
            implementation(libs.jetpack.viewmodel.core)
            implementation(libs.bundles.composelibs)
            implementation(projects.designsystem)
        }
        iosMain.dependencies {
            implementation(libs.stately.common)
            implementation(libs.sqldelight.driver.native)
        }
    }
}

android {
    namespace = "com.youapps.onlybeans"
    compileSdk = 36

    defaultConfig {
        minSdk = 26
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/versions/9/previous-compilation-data.bin"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        getByName("debug"){
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = true
        }
    }
}

sqldelight {
    databases {
        create("OnlyBeansDatabase") {
            packageName.set("com.youapps.onlybeans")
            generateAsync.set(true)
        }
    }
}

tasks.register("testClasses")