import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *,*>,
) {
    commonExtension.apply {
        compileSdk =  libs.findVersion("compileSdk").get().toString().toInt()
        defaultConfig {
            minSdk = libs.findVersion("minSdk").get().toString().toInt()
        }
        buildFeatures{
            compose = true
            buildConfig = true
        }
        composeOptions{
            kotlinCompilerExtensionVersion =  libs.findVersion("compose-compiler").get().toString()
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }

    }


}
