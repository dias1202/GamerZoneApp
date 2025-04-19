# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn java.lang.invoke.StringConcatFactory

# Menjaga sealed class Resource dan subclass-nya
-keep class com.dicoding.core.data.Resource { *; }
-keep class com.dicoding.core.data.Resource$* { *; }

# Menjaga semua model domain
-keep class com.dicoding.core.domain.model.** { *; }

# Jaga semua field dan anotasi di model domain
-keepclassmembers class com.dicoding.core.domain.model.** {
    <fields>;
    <methods>;
}
-keepattributes *Annotation*

# Menjaga repository dan use case
-keep class com.dicoding.core.domain.repository.** { *; }
-keep class com.dicoding.core.domain.usecase.** { *; }

# Menjaga module dependency injection (Koin)
-keep class com.dicoding.core.di.** { *; }

# Menjaga utility class
-keep class com.dicoding.core.util.** { *; }

-keep class com.dicoding.core.data.source.remote.response.** { *; }
-keepclassmembers class com.dicoding.core.data.source.remote.response.** {
    public <init>(...);
}

# Keep entity classes
-keep class androidx.room.** { *; }
-keep class com.dicoding.core.data.source.local.entity.** { *; }
-keep class com.dicoding.core.data.source.local.converter.** { *; }

# Keep annotations for Room
-keepattributes *Annotation*

# Keep type converters
-keepclassmembers class * {
    @androidx.room.TypeConverter <methods>;
}

# Hilt
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }

# Jaga agar fragment/activity tidak diubah saat obfuscation
-keep class com.dicoding.gamerzoneapp.favorite.** { *; }

