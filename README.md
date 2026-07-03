# Lockbox

Кроссплатформенное приложение для безопасного хранения данных (часть экосистемы **Homesharing**),
построенное на **Kotlin Multiplatform** и **Compose Multiplatform**. Цели сборки: Android, iOS и
Desktop (JVM).

> Подробное описание архитектуры, модулей и правил разработки — в [`Agents.md`](AGENTS.md).

---

## 🗂 Структура проекта
```text
- androidApp     # Android-приложение (точка входа, Android-специфичный Koin)
- desktopApp     # Desktop-приложение (JVM), хостит общий Compose UI
- iosApp         # iOS-приложение (SwiftUI), хостит собственный UI поверх shared; требуется macOS
- iosComposeApp  # iOS-приложение (Compose Multiplatform), переиспользует sharedUI; требуется macOS
- shared         # Общая бизнес-логика: data, domain, networking, DI
- sharedUI       # Общий Compose UI: экраны, ViewModel, навигация, тема, UI-kit (Android/Desktop/iOS)
```

> Есть два iOS-приложения: `iosApp` — нативный SwiftUI-интерфейс поверх `shared`, а
> `iosComposeApp` — тот же Compose-UI из `sharedUI`, что и на Android/Desktop.

---

## ⚙️ Требования

### Общие
- [JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- [Android Studio](https://developer.android.com/studio) (рекомендуется)

### Android
- Эмулятор или физическое устройство (Android 8.0+, `minSdk` 26)

### iOS (только macOS)
- [Xcode 14+](https://developer.apple.com/xcode/)
- [Homebrew](https://brew.sh/)
- Плагин Kotlin Multiplatform в Android Studio:
  `Settings → Plugins → Kotlin Multiplatform`

Проверка iOS-окружения:
```bash
brew install kdoctor && kdoctor
```

---

## 🛠 Подготовка
```bash
git clone git@github.com:LockBox-for-business/mobile.git
cd mobile
```
Путь к Android SDK задаётся в `local.properties`.

---

## ▶️ Запуск и сборка

### Android
```bash
./gradlew :androidApp:assembleDebug   # debug APK
./gradlew :androidApp:installDebug    # установить на устройство/эмулятор
```
Или откройте проект в Android Studio, выберите конфигурацию `androidApp` и нажмите ▶️.

### Desktop (JVM)
```bash
./gradlew :desktopApp:run
```

### iOS (только macOS)
Нативное SwiftUI-приложение:
```bash
open iosApp/iosApp.xcodeproj
```
Compose-приложение (переиспользует `sharedUI`):
```bash
open iosComposeApp/iosComposeApp.xcodeproj
```
Выберите симулятор → нажмите ▶️.

> ⚠️ `shared`/`sharedUI` объявляют только `iosSimulatorArm64` (без `iosX64`), поэтому на симуляторе
> собирайте под **arm64** (Apple Silicon). Сборка Compose-приложения из CLI:
> ```bash
> xcodebuild -project iosComposeApp/iosComposeApp.xcodeproj -scheme iosComposeApp \
>   -configuration Debug -destination 'id=<uuid arm64-симулятора>' build
> ```
> На симуляторе bundle id получает суффикс `TEAM_ID` (напр. `org.homesharing.lockbox.compose123`).
> `TEAM_ID` задаётся в `iosComposeApp/Configuration/Config.xcconfig` (по умолчанию placeholder `123`).

---

## 🔧 Примечания
- Версии зависимостей централизованы в [`gradle/libs.versions.toml`](gradle/libs.versions.toml).
- Первая сборка может занять до 15 минут из-за загрузки зависимостей.
- Для iOS обязательно выполните `kdoctor` для проверки зависимостей.
