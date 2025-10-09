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

rootProject.name = "SesameStudentLife"
include(":androidApp")
include(":shared")
include(":designsystem")
include(":users-management")
include(":androidTestApp")
include(":projects-management")
include(":android-services")
