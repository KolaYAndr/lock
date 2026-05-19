import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.impl.VariantOutputImpl
import org.gradle.api.JavaVersion.VERSION_21

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.composeCompiler)
}

configure<ApplicationExtension> {
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    namespace = "org.homesharing.lockbox"

    defaultConfig {
        applicationId = "org.homesharing.lockbox"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = VERSION_21
        targetCompatibility = VERSION_21
    }
    buildFeatures {
        compose = true
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

androidComponents {
    onVariants { variant ->
        val buildType = variant.buildType
        variant.outputs
            .map { it as VariantOutputImpl }
            .forEach { output ->
                output.outputFileName = "Lockbox-$buildType.apk"
            }
    }
}

dependencies {
    implementation(projects.sharedUI)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.koin.android)
}
