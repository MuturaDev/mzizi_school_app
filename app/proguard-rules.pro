# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Java\javaAndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class daysEntitled to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file daysEntitled.
#-renamesourcefileattribute SourceFile


#### -- Picasso --
 -dontwarn com.squareup.picasso.**

 #### -- OkHttp --

 -dontwarn com.squareup.okhttp.internal.**

 #### -- Apache Commons --

 -dontwarn org.apache.commons.logging.**

 ##This solved taking long to generate signed release apk
   ## and https://discuss.bitrise.io/t/android-uncaught-translation-error-java-util-concurrent-executionexception-java-lang-outofmemoryerror-gc-overhead-limit-exceeded/1051
 -ignorewarnings
 -keep class * {
 public private protected *;
 }

 -dontwarn javax.annotation.GuardedBy

 -keepattributes *Annotation*
 -keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
 }
 -keep enum org.greenrobot.eventbus.ThreadMode { *; }

# And if you use AsyncExecutor:
 -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
 }


# Advanced webview
-keep class * extends android.webkit.WebChromeClient { *; }
-dontwarn im.delight.android.webview.**



