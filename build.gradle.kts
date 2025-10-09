plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("org.jetbrains.dokka") version "1.9.10"

}



tasks.dokkaHtmlMultiModule {
    moduleName.set("Sesame Student Life")
}