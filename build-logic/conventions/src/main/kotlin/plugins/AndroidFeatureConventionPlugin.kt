package plugins

import libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("wallpaper.android.library")
                apply("wallpaper.android.hilt")
            }

            dependencies {
                add("implementation", project(":domain"))
                add("testImplementation", kotlin("test"))
                add("androidTestImplementation", kotlin("test"))
                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
            }
        }
    }
}