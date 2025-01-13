# Настройка для работы с KMP

В этом гайде я постараюсь простыми словами рассказать, как и что запускать

## Project Structure
Поскольку проект на KMP, то и структура у него будет соответствующая

- **shared**: Это модуль, в котором лежит переиспользуемый код на обе платформы
- **composeApp**: Это android-модуль проекта, его мы запускаем в android studio при работе с эмулятором
- **iosApp**: Такой же платформенный модуль, но для iOS

## Есть пререквизиты
Если хотите запустить iOS версию, потребуется macOS. Для android версии подойдёт любая ОС


### Android требования
- [Android Studio](https://developer.android.com/studio)
Нужно будет скачать студию и создать эмулятор (любой из предложенных, я выбираю medium phone и версию android 35)

### iOS требования (Mac Only)
- [Android Studio](https://developer.android.com/studio)
- [Xcode](https://developer.apple.com/xcode/)
- [JDL](https://www.oracle.com/java/technologies/downloads/?er=221886)
- Kotlin Mutliplatform plugin. (В студии шестерня -> настройки -> Plugins)
- [Brew](https://brew.sh/)

После этого в терминале нужно будет установить kdoctor. Эта утилита покажет, каких элементов не хватет, если такие будут. Пишем: 
```bash
    brew install kdoctor
```
Потом вызываем его в том же терминале
```bash
    kdoctor
```

## Подготовка проекта
Скачиваем его через гит или можно через склонировать командой 
```bash
    git clone https://github.com/LockBox-for-business/mobile.git
```

## Запускаем проект

### Android
1. Нужно будет подождать пока сконфигурируется студия (это может быть реально долгий процесс, но обычно минут 5-10 хватает)

2. Вверху нажимаем зелёный треугольничек, снова ждём. По окончании откроется эмулятор, с ним монжо взаимодействовать

### iOS (Mac Only)
1. Открываем iOS Проект:
   ```bash
   open iosApp/iosApp.xcworkspace
   ```

2. Запускаем iOS прилу. Выбираем симулятор и нажимаем Run в Xcode
### Android требования
- [Android Studio](https://developer.android.com/studio)
Нужно будет скачать студию и создать эмулятор (любой из предложенных, я выбираю medium phone и версию android 35)

### iOS требования (Mac Only)
- [Android Studio](https://developer.android.com/studio)
- [Xcode](https://developer.apple.com/xcode/)
- [JDK](https://www.oracle.com/java/technologies/downloads/?er=221886)
- Kotlin Mutliplatform plugin. (В студии шестерня -> настройки -> Plugins)
- [Brew](https://brew.sh/)

После этого в терминале нужно будет установить kdoctor. Эта утилита покажет, каких элементов не хватет, если такие будут. Пишем: 
```bash
    brew install kdoctor
```
Потом вызываем его в том же терминале
```bash
    kdoctor
```

## Подготовка проекта
Скачиваем его через гит или можно через склонировать командой 
```bash
    git clone https://github.com/LockBox-for-business/mobile.git
```

## Запускаем проект

### Android
1. Нужно будет подождать пока сконфигурируется студия (это может быть реально долгий процесс, но обычно минут 5-10 хватает)

2. Вверху нажимаем зелёный треугольничек, снова ждём. По окончании откроется эмулятор, с ним можно взаимодействовать

### iOS (Mac Only)
1. Открываем iOS Проект:
   ```bash
   open iosApp/iosApp.xcworkspace
   ```

2. Запускаем iOS прилу. Выбираем симулятор и нажимаем Run в Xcode