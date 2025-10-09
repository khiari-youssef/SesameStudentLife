
plugins {
    `kotlin-dsl`
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
       register("androidTest"){
           id = "sesame.android.test"
           implementationClass = "AndroidTestConventionPlugin"
       }
   }
}
