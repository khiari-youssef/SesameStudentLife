

import com.android.build.api.dsl.ApplicationExtension
import configureKotlinAndroid
import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.io.FileInputStream
import java.util.Properties

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val keystoreProperties = Properties()
        runCatching {
            val keystorePropertiesFile = target.file("keystore.properties")
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))
        }.onFailure { th->
            th.printStackTrace()
        }
        with(target) {
            with(pluginManager){
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
                packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
                packaging.resources.excludes += "/META-INF/versions/9/previous-compilation-data.bin"
                defaultConfig.versionCode = 1
                defaultConfig.versionName = "1.0"
                buildTypes {
                    debug {
                        isDebuggable = true
                        isMinifyEnabled = false
                        signingConfigs {
                            create("signingConfig") {
                                keyAlias = keystoreProperties["keyAlias"].toString()
                                keyPassword = keystoreProperties["keyPassword"].toString()
                                storeFile = file(keystoreProperties["storeFile"].toString())
                                storePassword = keystoreProperties["storePassword"].toString()
                            }
                        }
                    }
                    release {
                        isMinifyEnabled = true
                        isShrinkResources = true
                    }
                }
            }

        }
    }

}