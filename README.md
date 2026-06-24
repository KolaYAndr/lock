# Lockbox

Кроссплатформенное приложение для безопасного хранения данных (часть экосистемы **Homesharing**),
построенное на **Kotlin Multiplatform** и **Compose Multiplatform**. Цели сборки: Android, iOS и
Desktop (JVM).

> Подробное описание архитектуры, модулей и правил разработки — в [`Agents.md`](AGENTS.md).

---

## 🗂 Структура проекта
```text
- androidApp   # Android-приложение (точка входа, Android-специфичный Koin)
- desktopApp   # Desktop-приложение (JVM), хостит общий Compose UI
- iosApp       # iOS-приложение (SwiftUI), требуется macOS
- shared       # Общая бизнес-логика: data, domain, networking, DI
- sharedUI     # Общий Compose UI: экраны, ViewModel, навигация, тема, UI-kit
```

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
```bash
open iosApp/iosApp.xcodeproj
```
Выберите симулятор → нажмите ▶️.

---

## 🔧 Примечания
- Версии зависимостей централизованы в [`gradle/libs.versions.toml`](gradle/libs.versions.toml).
- Первая сборка может занять до 15 минут из-за загрузки зависимостей.
- Для iOS обязательно выполните `kdoctor` для проверки зависимостей.
