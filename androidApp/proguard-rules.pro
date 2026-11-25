# kotlinx.serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class org.homesharing.lockbox.**$$serializer { *; }
-keepclassmembers class org.homesharing.lockbox.** {
    *** Companion;
}
-keepclasseswithmembers class org.homesharing.lockbox.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Ktor
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**
-keepclassmembers class * implements io.ktor.client.engine.HttpClientEngineContainer { *; }

# Koin
-keep class org.koin.** { *; }
-dontwarn org.koin.**

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-dontwarn kotlinx.coroutines.**
