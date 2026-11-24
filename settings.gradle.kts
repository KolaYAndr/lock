rootProject.name = "Lockbox"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

// Core modules
include(":core:model")
project(":core:model").projectDir = file("core/model")

include(":core:security")
project(":core:security").projectDir = file("core/security")

include(":core:network")
project(":core:network").projectDir = file("core/network")

// Feature modules (api / impl)
include(":feature:auth:api")
project(":feature:auth:api").projectDir = file("feature/auth/api")
include(":feature:auth:impl")
project(":feature:auth:impl").projectDir = file("feature/auth/impl")

include(":feature:account:api")
project(":feature:account:api").projectDir = file("feature/account/api")
include(":feature:account:impl")
project(":feature:account:impl").projectDir = file("feature/account/impl")

include(":feature:flat:api")
project(":feature:flat:api").projectDir = file("feature/flat/api")
include(":feature:flat:impl")
project(":feature:flat:impl").projectDir = file("feature/flat/impl")

include(":feature:invitation:api")
project(":feature:invitation:api").projectDir = file("feature/invitation/api")
include(":feature:invitation:impl")
project(":feature:invitation:impl").projectDir = file("feature/invitation/impl")

include(":androidApp")
include(":shared")
include(":sharedUI")
include(":desktopApp")
