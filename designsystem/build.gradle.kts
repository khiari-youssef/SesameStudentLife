plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
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
            baseName = "designsystem"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies{
            implementation(libs.bundles.composelibs)
            implementation(libs.androidx.splashscreen)
        }
    }
}

android {
    namespace = "tn.sesame.designsystem"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    dependencies{
        debugImplementation(libs.compose.ui.tooling)
    }
}

tasks.register("testClasses")
