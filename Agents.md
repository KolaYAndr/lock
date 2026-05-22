# Project: Lockbox (Home Sharing)

## Overview

Lockbox is a multiplatform application for secure data storage, part of the "Home Sharing"
ecosystem. It is built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**,
targeting Android, iOS, and Desktop (JVM).

## Project Structure

- `androidApp/`: Android entry point (`LockboxActivity`, `LockboxApp`) and Android-only Koin setup.
- `desktopApp/`: Desktop (JVM) entry point that hosts the shared Compose UI.
- `iosApp/`: Native iOS application (SwiftUI) that consumes the `shared` module via the
  KMP framework.
- `shared/`: Pure business logic — data, domain, networking, DI.
    - `commonMain/`: Cross-platform code (Ktor APIs, DTOs, repositories, domain models, paging,
      token storage interfaces, Koin modules).
    - `androidMain/`: Android-specific implementations (`LockboxCipher` with Android Keystore,
      OkHttp engine, Android `PlatformModule`).
    - `iosMain/`: iOS implementations (Darwin Ktor engine, Keychain-backed `TokenStorage`,
      `KoinHelper` for Swift interop).
    - `jvmMain/`: Desktop platform module.
- `sharedUI/`: Cross-platform Compose UI module — screens, ViewModels, navigation, theme, and the
  reusable UI kit. Consumed by `androidApp` and `desktopApp`; iOS uses its own SwiftUI surface.

### Feature flows (in `sharedUI/.../presentation`)

- `login/` — authentication entry.
- `flats_flow/` — add/import flat, address, data, rooms, created confirmation.
- `guest_invitaion_flow/` — invitation check, details, check-in setup, guest info, invited state.
- `profile_flow/` — role chooser, profile setup.
- `uikit/` — shared composables (buttons, chips, inputs, pickers, date picker, checkboxes),
  theme (colors, typography, shapes), animations, and modifiers.
- `navigation/` — Navigation3 graphs split into `login/` (unauthenticated) and `authenticated/`.

## Tech Stack

- **Language**: Kotlin 2.3.21 (multiplatform).
- **UI**: Compose Multiplatform 1.11.0 (shared across Android/Desktop) + SwiftUI on iOS.
- **Navigation**: AndroidX Navigation3 (1.1.1 — pinned, do not bump to 2.10.0) with Jetbrains
  lifecycle-viewmodel-navigation3.
- **Networking**: Ktor 3.5.0 (OkHttp on Android, Darwin on iOS), `kotlinx.serialization` JSON,
  content negotiation, logging, and auth plugins.
- **Concurrency**: Kotlin Coroutines & Flow 1.11.0 (kotlinx-coroutines-swing on Desktop).
- **DI**: Koin 4.2.1 (`koin-core`, `koin-compose`, `koin-compose-viewmodel`, `koin-android`).
- **Paging**: AndroidX Paging 3.5.0 (used in `BoxesPagingSource`, `ContractsPagingSource`).
- **Persistence**: AndroidX DataStore Preferences 1.2.1.
- **Image loading**: Coil 3.4.0 with Ktor3 network fetcher.
- **Dates**: `kotlinx-datetime` 0.8.0.
- **Security**:
    - Android: AES/GCM/NoPadding via Android Keystore (`LockboxCipher`). StrongBox-preferred with
      fallback to standard TEE/SE.
    - iOS: Keychain-backed `TokenStorage`.
- **Build**: Gradle Kotlin DSL with version catalog (`gradle/libs.versions.toml`); AGP 9.2.1,
  `minSdk` 26, `compileSdk`/`targetSdk` 37; typesafe project accessors enabled.

## Key Components

- `LockboxCipher` (Android): AES/GCM encryption via Keystore for sensitive blobs.
- `TokenStorage` (common interface + `android`/`ios` implementations): persists access/refresh
  tokens.
- `NetworkClient` (common + platform variants): Ktor client with auth and refresh wiring.
- Repositories (in `data/repository/`): `AuthRepositoryImpl`, `AccountRepositoryImpl`,
  `BoxesRepositoryImpl`, `DocumentsRepositoryImpl`, fulfilling interfaces in `domain/repository/`.
- Koin modules: `NetworkModule`, `RepositoryModule`, `PlatformModule` (expect/actual),
  `SharedAppModules` in `shared`; `ViewModelModule` + `Koin.kt` in `sharedUI`.
- `LockboxAppViewModel` + `LockboxApp` composable: app-level state and routing between login and
  authenticated graphs.

## AI Guidelines

1. **Consistency**: Maintain the KMP layout — domain/data/network logic in `shared/commonMain`,
   UI in `sharedUI/commonMain`. Only drop into platform source sets when you need an `actual`
   implementation or platform API.
2. **DI**: Register new dependencies in the appropriate Koin module (`NetworkModule`,
   `RepositoryModule`, `ViewModelModule`, or a `PlatformModule` actual). Don't instantiate
   collaborators ad-hoc inside ViewModels.
3. **Navigation**: Add new destinations under `presentation/navigation/{login,authenticated}` and
   model routes with the Navigation3 serialization helpers in `NavigationSerialization.kt`. Keep
   `navigation3` pinned at the version in the catalog.
4. **UI kit first**: Reuse composables under `presentation/uikit/composable/` (buttons, inputs,
   chips, pickers, date picker, etc.) and theme tokens from `uikit/theme/` rather than introducing
   new ad-hoc widgets or hard-coded colors/typography.
5. **Security**: When touching `LockboxCipher`, always handle `StrongBoxUnavailableException` and
   fall back to the standard Keystore path. Don't log token material or cipher input/output.
6. **Networking**: Add new endpoints as interfaces under `data/network/api/` (with a
   `LockBox*Api` implementation) and surface them through a repository — don't call Ktor directly
   from ViewModels. Define DTOs under `data/network/dto/<feature>/` and map them into
   `domain/model/`.
7. **Strings & resources**: Prefer Compose Multiplatform resources in `sharedUI` for shared UI
   strings; use platform resources (Android `res/`, iOS `Localizable.xcstrings`) only for content
   that lives in those targets.
8. **Testing**: Add tests for shared logic in `shared/src/commonTest` (and `sharedUI/src/commonTest`
   for UI logic) so they run on every target.
