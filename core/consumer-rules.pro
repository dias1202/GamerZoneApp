#############################################
#           SQLCipher Configuration         #
#############################################
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }


#############################################
#             Gson Configuration            #
#############################################

# Pertahankan signature type untuk generics
-keepattributes Signature

# Pertahankan anotasi
-keepattributes *Annotation*

# Gson core classes
-keep class com.google.gson.** { *; }
-keep class com.google.gson.examples.android.model.** { <fields>; }

# TypeAdapter dan interface terkait
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Pertahankan field yang menggunakan @SerializedName
-keep class * {
    @com.google.gson.annotations.SerializedName <fields>;
}


#############################################
#           Retrofit Configuration          #
#############################################

-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Pertahankan method interface Retrofit
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore warning yang tidak perlu
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-dontwarn kotlinx.**


#############################################
#            Glide Configuration            #
#############################################

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
    <init>(...);
}

-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
    *** rewind();
}


#############################################
#            RxJava Configuration           #
#############################################

# Uncomment jika menggunakan RxJava
# -dontwarn java.util.concurrent.Flow*


#############################################
#          Room Database Configuration      #
#############################################

# Entity dan Database
-keep @androidx.room.Entity class * { *; }
-keep class * extends androidx.room.Database
-keep class * extends androidx.room.Dao { *; }

# Keep Room database and DAO
-keep class * extends androidx.room.RoomDatabase { *; }
-keep @androidx.room.Database class * { *; }
-keep @androidx.room.Dao class * { *; }
-keep interface * implements androidx.room.RoomDatabase { *; }
-keep interface * implements androidx.room.Dao { *; }

# TypeConverter
-keep class * implements androidx.room.TypeConverter
-keepclassmembers class * {
    @androidx.room.TypeConverter *;
}

# SQLite
-keep class * implements android.database.sqlite.SQLiteOpenHelper { *; }

#############################################
#           Annotations & Metadata          #
#############################################

-keepattributes RuntimeVisibleAnnotations, AnnotationDefault

-keepclassmembers class * {
    @kotlin.Metadata *;
    @com.google.gson.annotations.** *;
}


#############################################
#             Parcelable Support            #
#############################################

-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

# Untuk Parcelize Kotlin
-keepclassmembers class com.dicoding.core.domain.model.** {
    ** writeToParcel(android.os.Parcel, int);
    ** createFromParcel(android.os.Parcel);
}

# Hilt
-keep class dagger.hilt.** { *; }
-keepclassmembers class * {
    @dagger.hilt.android.* <methods>;
}
-dontwarn dagger.hilt.**

-keep class com.dicoding.core.data.source.remote.response.** { *; }
-keepclassmembers class com.dicoding.core.data.source.remote.response.** {
    public <init>(...);
}

-keep class * extends androidx.lifecycle.ViewModel
