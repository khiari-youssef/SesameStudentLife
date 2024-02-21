
plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies{
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin{
   plugins {
       register("androidApplication") {
           id = "sesame.android.application"
           implementationClass = "AndroidApplicationConventionPlugin"
       }
       register("androidFeature") {
           id = "sesame.android.feature"
           implementationClass = "AndroidFeatureConventionPlugin"
       }
   }
}
