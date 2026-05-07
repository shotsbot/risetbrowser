# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-verbose

# Optimization
-optimizationpasses 5
-dontusemixedcaseclassnames

# Remove Logging
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# Android Support Library
-dontwarn android.support.**
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }

# AndroidX
-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# WebView
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}

# OkHttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }

# GSON
-keep class com.google.gson.** { *; }
-keep interface com.google.gson.** { *; }

# Applications
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Callbacks
-keep class * implements android.content.ActivityLifecycleCallbacks { *; }

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

# Preserve custom application classes
-keep class com.risetbrowser.android.** { *; }
-keep interface com.risetbrowser.android.** { *; }

# Keep all resources
-keepresourcexmls res/drawable/*.xml

# Enumeration
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable classes
-keep class * implements java.io.Serializable { *; }

# Retrofit & RxJava
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-dontwarn rx.**
-keepclasseswithmembernames class rx.** { *; }

# Glide
-dontwarn com.bumptech.glide.**
-keep class com.bumptech.glide.** { *; }
