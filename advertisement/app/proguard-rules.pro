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

# 1.基本指令区
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-ignorewarning
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable

# 2.默认保留区
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

-keep class java.**{*;}

# 项目用到的第三方框架
-keep class okhttp3.**{*;}
-keep class okio.**{*;}
-keep class com.squareup.okhttp3.** { *;}
-keep class com.alibaba.fastjson.** {*;}
-keep class org.springframework.**{*;}
-keep class io.reactivex.** {*;}
-keep class net.sourceforge.** {*;}
-keep class com.bumptech.glide.** {*;}
-keep class org.eclipse.paho.** {*;}

-keep class com.seeta.sdk.** {*;}
-keep class com.seetatech.sdk.** {*;}

-keep class com.jiangdg.usbcamera.**{*;}
-keep class com.serenegiant.usb.**{*;}

# bean
-keep class com.seetatech.ad.data.bean.** {*;}
-keep class com.seetatech.ad.data.http.** {*;}
-keep class com.seetatech.httpserver.data.bean.** {*;}