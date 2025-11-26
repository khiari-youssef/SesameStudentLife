pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}




dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "OnlyBeans"
include(":androidApp")
include(":core")
include(":designsystem")
include(":users-management")
include(":androidTestApp")
include(":search-module")
include(":android-services")
