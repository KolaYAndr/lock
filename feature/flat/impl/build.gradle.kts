import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.kotlinxSerialization)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    )

    jvm()

    android {
        namespace = "org.homesharing.lockbox.feature.flat.impl"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.feature.flat.api)
            implementation(projects.core.model)
            implementation(projects.core.network)
            implementation(libs.paging)
            implementation(libs.bundles.ktor)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.serialization.json)
        }
    }
}
