# Project: Lockbox (Homesharing)

## Overview

Lockbox is a multiplatform application for secure data storage, part of the "Homesharing"
ecosystem. It is built with **Kotlin Multiplatform** and **Compose Multiplatform**,
targeting Android, iOS, and Desktop (JVM). The shared Compose UI (`sharedUI`) runs on
Android, Desktop, and iOS; there are two iOS apps — a native SwiftUI one (`iosApp`) and a
Compose one (`iosComposeApp`) that reuses `sharedUI`.

## Project Structure

- `androidApp/`: Android entry point (`LockboxActivity`, `LockboxApp`) and Android-only Koin setup.
- `desktopApp/`: Desktop (JVM) entry point that hosts the shared Compose UI.
- `iosApp/`: Native iOS application (SwiftUI) that consumes the `shared` module via the
  KMP framework (its own hand-written UI surface).
- `iosComposeApp/`: iOS application (Compose Multiplatform) that consumes `sharedUI` via the
  `SharedUI` framework and renders the same Compose UI as Android/Desktop. Thin Swift shell
  (`iOSApp.swift`, `ContentView.swift` wrapping `MainViewControllerKt.MainViewController()`); its
  "Compile Kotlin Framework" build phase runs `:sharedUI:embedAndSignAppleFrameworkForXcode`.
- `core/`: Low-level cross-cutting infrastructure modules (`model`, `security`, `network`).
- `feature/`: Business features decomposed into `:api` and `:impl` submodules (`auth`, `account`, `flat`, `invitation`).
- `shared/`: Umbrella facade module exporting core and feature modules for Xcode (`Shared.framework`), DI aggregation (`SharedAppModules`), and Swift interop (`KoinHelper`).
- `sharedUI/`: Cross-platform Compose UI module — screens, ViewModels, navigation, theme, and the
  reusable UI kit. Targets Android, JVM, and iOS (`iosArm64`/`iosSimulatorArm64`, static `SharedUI`
  framework). Consumed by `androidApp`, `desktopApp`, and `iosComposeApp`; `iosApp` uses its own
  SwiftUI surface instead. `iosMain/MainViewController.kt` is the iOS entry point — it exposes
  `MainViewController()` and starts Koin with `sharedUiModule` in the `ComposeUIViewController`
  `configure` block.

### Feature flows (in `sharedUI/.../presentation`)

- `login/` — authentication entry.
- `flats_flow/` — add/import flat, address, data, rooms, created confirmation.
- `flat_list/` — paginated list of flats (`FlatListScreen` + `FlatListViewModel`).
- `flat_detailed/` — single flat detail view (`FlatDetailedScreen` + `FlatDetailedViewModel`).
- `guest_invitation_flow/` — invitation check, details, check-in setup, guest info, invited state.
- `profile_flow/` — role chooser, profile setup.
- `uikit/` — shared composables (buttons, chips, inputs, pickers, date picker, checkboxes,
  pull-to-refresh, editable/property containers), theme (colors, typography, shapes), animations,
  and modifiers.
- `navigation/` — Navigation3 graphs split into `login/` (unauthenticated) and `authenticated/`.

## Tech Stack

- **Language**: Kotlin 2.4.21 (multiplatform).
- **UI**: Compose Multiplatform 1.12.2 (shared across Android/Desktop/iOS via `sharedUI`) +
  SwiftUI in the native `iosApp`.
- **Navigation**: AndroidX Navigation3 (1.1.1 — pinned, do not bump to 2.10.0) with Jetbrains
  lifecycle-viewmodel-navigation3 (2.11.0).
- **Networking**: Ktor 3.6.0 (OkHttp on Android, Darwin on iOS), `kotlinx.serialization` JSON (1.12.0),
  content negotiation, logging, and auth plugins.
- **Concurrency**: Kotlin Coroutines & Flow 1.11.0 (kotlinx-coroutines-swing on Desktop).
- **DI**: Koin 4.2.2 (`koin-core`, `koin-compose`, `koin-compose-viewmodel`, `koin-android`).
- **Paging**: AndroidX Paging 3.5.3 (used in `FlatPagingSource`).
- **Persistence**: AndroidX DataStore Preferences 1.3.0.
- **Image loading**: Coil 3.6.3 with Ktor3 network fetcher.
- **Dates**: `kotlinx-datetime` 0.8.0 with `kotlin.time` `Clock`/`Instant`. Date logic in
  `commonMain` must use these (not `java.time`/`java.util`) so it compiles for the iOS target.
- **Security**:
    - Android: AES/GCM/NoPadding via Android Keystore (`LockboxCipher`). StrongBox-preferred with
      fallback to standard TEE/SE.
    - iOS: Keychain-backed `TokenStorage` (`Keychain` wraps the Security framework; generic-password
      items with `kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly` — device-only, not synced/backed up).
    - Desktop (JVM): AES/GCM/NoPadding via `LockboxCipher` (256-bit key in a JCEKS keystore file
      under `~/.lockbox/`, restricted to owner-only `600`). Tokens are stored encrypted in
      DataStore Preferences. Not hardware-backed — the key lives on disk, unlike Android/iOS.
- **Build**: Gradle Kotlin DSL with version catalog (`gradle/libs.versions.toml`); AGP 9.5.0, Gradle 9.6.0,
  `minSdk` 26, `compileSdk`/`targetSdk` 37; typesafe project accessors enabled.

## Key Components

- `TokenStorage` (common interface + `android`/`ios`/`jvm` implementations): persists access/refresh
  tokens. Android & desktop store them encrypted (`LockboxCipher`) in DataStore Preferences
  (desktop under `~/.lockbox/`); iOS stores them in the Keychain via the `Keychain` wrapper.
- `LockboxCipher`: AES/GCM encryption. Android backs the key with the Android Keystore
  (StrongBox-preferred); desktop (`LockboxCipher.jvm.kt`) keeps the key in an owner-only JCEKS
  keystore file under `~/.lockbox/`.
- `NetworkClient` (common + platform variants): Ktor client with auth and refresh wiring.
- Repositories (in `data/repository/`): `AuthRepositoryImpl`, `AccountRepositoryImpl`,
  `FlatRepositoryImpl`, fulfilling interfaces in `domain/repository/` (`AuthRepository`,
  `AccountRepository`, `FlatRepository`). `FlatPagingSource` (in `data/repository/paging/`) backs
  the paged flat list.
- Network APIs (in `data/network/api/`): `AccountApi`/`AccountApiImpl`, `FlatApi`/`FlatApiImpl`
  (internal `*Api` interface + `*ApiImpl`), with shared response wrappers `ApiResponse`/`AuthResponse`
  under `api/response/`.
- Koin modules: `NetworkModule`, `RepositoryModule`, `PlatformModule` (expect/actual),
  `SharedAppModules` in `shared`; `ViewModelModule` + `Koin.kt` in `sharedUI`.
- `LockboxAppViewModel` + `LockboxApp` composable: app-level state and routing between login and
  authenticated graphs.

## AI Guidelines

1. **Consistency**: Maintain the KMP layout — domain/data/network logic in `shared/commonMain`,
   UI in `sharedUI/commonMain`. Only drop into platform source sets when you need an `actual`
   implementation or platform API. `sharedUI/commonMain` now compiles for iOS too, so keep it free
   of JVM-only APIs (`java.*`, `System.currentTimeMillis()`); use multiplatform equivalents
   (`kotlin.time`, `kotlinx-datetime`, Compose/coroutines APIs). Note `androidx.compose.material3`
   `CalendarLocale` is an `expect class` (NSLocale on iOS) that can't be constructed in common code —
   format dates via `kotlinx-datetime` instead of `DatePickerFormatter.formatDate(..., locale)`.
2. **DI**: Register new dependencies in the appropriate Koin module (`NetworkModule`,
   `RepositoryModule`, `ViewModelModule`, or a `PlatformModule` actual). Don't instantiate
   collaborators ad-hoc inside ViewModels.
3. **Navigation**: Add new destinations under `presentation/navigation/{login,authenticated}` and
   model routes with the Navigation3 serialization helpers in `NavigationSerialization.kt`. Keep
   `navigation3` pinned at the version in the catalog.
4. **UI kit first**: Reuse composables under `presentation/uikit/composable/` (buttons, inputs,
   chips, pickers, date picker, etc.) and theme tokens from `uikit/theme/` rather than introducing
   new ad-hoc widgets or hard-coded colors/typography.
5. **Security**: `LockboxCipher` has per-platform implementations (Android Keystore, desktop JCEKS).
   On Android, always handle `StrongBoxUnavailableException` and fall back to the standard Keystore
   path. On decryption failure, clear stored tokens rather than surfacing stale/garbage data. Never
   log token material or cipher input/output.
6. **Networking**: Add new endpoints as an internal `*Api` interface under `data/network/api/`
   (with a `*ApiImpl` implementation) and surface them through a repository — don't call Ktor
   directly from ViewModels. Define DTOs under `data/network/dto/<feature>/` and map them into
   `domain/model/<feature>/` via `*DtoToDomain`/`*DomainToDto` mappers.
7. **Strings & resources**: Prefer Compose Multiplatform resources in `sharedUI` for shared UI
   strings; use platform resources (Android `res/`, iOS `Localizable.xcstrings`) only for content
   that lives in those targets.
8. **Testing**: There is currently no test suite in the project (no `commonTest`/`*Test` source
   sets). If you add tests, place shared-logic tests in `shared/src/commonTest` and UI-logic tests
   in `sharedUI/src/commonTest` so they run on every target, and wire up the kotlin-test
   dependencies in the corresponding `build.gradle.kts`.

## Build & Run

- **Android (debug APK)**: `./gradlew :androidApp:assembleDebug`
- **Android (install on device/emulator)**: `./gradlew :androidApp:installDebug`
- **Desktop (JVM)**: `./gradlew :desktopApp:run`
- **iOS (SwiftUI)**: open `iosApp/iosApp.xcodeproj` in Xcode and run.
- **iOS (Compose)**: open `iosComposeApp/iosComposeApp.xcodeproj` in Xcode and run. Only
  `iosSimulatorArm64` is declared, so build against an **arm64** simulator (Apple Silicon), e.g.
  `xcodebuild -project iosComposeApp/iosComposeApp.xcodeproj -scheme iosComposeApp -configuration Debug -destination 'id=<arm64-sim-uuid>' build`.
  A `generic/platform=iOS Simulator` destination fails in `syncComposeResourcesForIos` ("Unknown iOS
  simulator arch: 'x86_64'"). On the simulator the bundle id gets the `TEAM_ID` suffix
  (`org.homesharing.lockbox.compose123`); `TEAM_ID` lives in `iosComposeApp/Configuration/Config.xcconfig`.
- **Compile checks**: `./gradlew :shared:compileKotlinMetadata`, `./gradlew :sharedUI:compileKotlinJvm`,
  `./gradlew :sharedUI:compileAndroidMain`, `./gradlew :sharedUI:linkDebugFrameworkIosSimulatorArm64`.
- Configuration lives in `local.properties` (SDK path) and `gradle.properties`. Dependency
  versions are centralized in `gradle/libs.versions.toml`.
