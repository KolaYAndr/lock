# Project: Lockbox (Home Sharing)

## Overview

Lockbox is a mobile application for secure data storage, part of the "Home Sharing" ecosystem. It is
built using **Kotlin Multiplatform (KMP)**.

## Project Structure

- `androidApp/`: Android-specific application code (Compose, ViewModels).
- `shared/`: Shared business logic, data layer, and domain models.
    - `commonMain/`: Cross-platform logic (Ktor, Kotlinx Serialization, etc.).
    - `androidMain/`: Android-specific implementations (e.g., `LockboxCipher` using Android
      Keystore).
    - `iosMain/`: iOS-specific implementations.
- `iosApp/`: Native iOS application (SwiftUI) that consumes the shared module.

## Tech Stack

- **UI**: Compose Multiplatform (Android) / SwiftUI (iOS).
- **Networking**: Ktor.
- **Concurrency**: Kotlin Coroutines & Flow.
- **Security**:
    - Android: AES/GCM/NoPadding via Android Keystore. Supports StrongBox with fallback to standard
      TEE/SE.
- **Dependency Injection**: Likely Koin (based on `PlatformModule.android.kt` path seen in usages).
- **Build System**: Gradle with Kotlin DSL and Version Catalogs (`gradle/libs.versions.toml`).

## Key Components

- `LockboxCipher`: Handles encryption/decryption of sensitive data (like tokens) using
  platform-specific APIs.
- `TokenStorage`: Manages storage and retrieval of access/refresh tokens.
- `RefreshManager`: Handles token refreshing logic.

## AI Guidelines

1. **Consistency**: Maintain the existing KMP architecture. Place logic in `commonMain` whenever
   possible.
2. **Security**: When dealing with `LockboxCipher`, always handle `StrongBoxUnavailableException`
   for older or non-flagship Android devices.
3. **Strings**: Use multiplatform string resources if available, or platform-specific resources in
   their respective modules.
4. **Testing**: Add tests in `commonTest` for shared logic.
