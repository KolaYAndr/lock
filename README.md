# Руководство по настройке KMP-проекта

Простой пошаговый гайд для запуска проекта на Kotlin Multiplatform.

---

## 🗂 Структура проекта
```text
- shared          # Общий кроссплатформенный код
- composeApp      # Android-приложение
- iosApp          # iOS-приложение (требуется macOS)
```

---

## ⚙️ Требования

### Android
1. [Android Studio](https://developer.android.com/studio)
2. Эмулятор (создаётся в Android Studio)

### iOS
1. [Xcode 14+](https://developer.apple.com/xcode/)
2. [JDK 17+](https://www.oracle.com/java/technologies/downloads/)
3. [Homebrew](https://brew.sh/)
4. Плагин Kotlin Multiplatform в Android Studio:  
   `Settings → Plugins → Kotlin Multiplatform Mobile`

#### Проверка окружения для iOS
```bash
brew install kdoctor && kdoctor
```

---

## 🛠 Подготовка
```bash
git clone https://github.com/LockBox-for-business/mobile.git
cd mobile
```

---

## ▶️ Запуск

### Android
1. Откройте `composeApp` в Android Studio
2. Дождитесь завершения синхронизации Gradle (5-10 мин)
3. Запустите эмулятор → нажмите ▶️ в панели инструментов

### iOS (только macOS)
1. Откройте проект в Xcode:
```bash
open iosApp/iosApp.xcworkspace
```

2. Выберите симулятор → нажмите ▶️

---

## 🔧 Примечания
- Для iOS обязательно выполните `kdoctor` для проверки зависимостей
- Первый запуск может занять до 15 минут из-за загрузки зависимостей
