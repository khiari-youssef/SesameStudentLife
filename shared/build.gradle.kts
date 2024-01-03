plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinXSer)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
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
        }
        iosMain.dependencies {

        }
    }
}

android {
    namespace = "tn.sesame.spm"
    compileSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

sqldelight {
    databases {
        create("SesameWorksLifeDatabase") {
            packageName.set("tn.sesame.spmdatabase")
            generateAsync.set(true)
        }
    }
}
