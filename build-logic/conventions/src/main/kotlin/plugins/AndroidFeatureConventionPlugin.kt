
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
                packaging.resources.excludes += "/META-INF/versions/9/previous-compilation-data.bin"
                buildTypes {
                    getByName("debug"){
                        isMinifyEnabled = false
                    }
                    getByName("release") {
                        isMinifyEnabled = true
                    }
                }
            }
            dependencies {
               // add("implementation", project(":domain"))
                add("testImplementation", kotlin("test"))
                add("androidTestImplementation", kotlin("test"))
//                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
            }
        }
    }
}