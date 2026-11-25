import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
            export(projects.core.model)
            export(projects.feature.auth.api)
            export(projects.feature.account.api)
            export(projects.feature.flat.api)
            export(projects.feature.invitation.api)
            export(projects.feature.contracts.api)
        }
    }

    jvm()

    android {
        namespace = "org.homesharing.lockbox.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
        androidResources {
            enable = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.model)
            api(projects.core.security)
            api(projects.core.network)
            api(projects.feature.auth.api)
            api(projects.feature.auth.impl)
            api(projects.feature.account.api)
            api(projects.feature.account.impl)
            api(projects.feature.flat.api)
            api(projects.feature.flat.impl)
            api(projects.feature.invitation.api)
            api(projects.feature.invitation.impl)
            api(projects.feature.contracts.api)
            api(projects.feature.contracts.impl)
            api(libs.koin.core)
        }
    }
}
