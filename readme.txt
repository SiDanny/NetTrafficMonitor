lint.xml文件为链接规则文件
proguard.cfg文件为android混淆文件



-injars  androidtest.jar【jar包所在地址】 
-outjars  out【输出地址】
-libraryjars    'D:\android-sdk-windows\platforms\android-9\android.jar' 【引用的库的jar，用于解析injars所指定的jar类】
 
-optimizationpasses 5
-dontusemixedcaseclassnames 【混淆时不会产生形形色色的类名 】
-dontskipnonpubliclibraryclasses 【指定不去忽略非公共的库类。 】
-dontpreverify 【不预校验】
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* 【优化】
-keep public class * extends android.app.Activity　　【不进行混淆保持原样】
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public abstract interface com.asqw.android.Listener{
public protected <methods>;  【所有方法不进行混淆】
}
-keep public class com.asqw.android{
public void Start(java.lang.String); 【对该方法不进行混淆】
}
-keepclasseswithmembernames class * { 【保护指定的类和类的成员的名称，如果所有指定的类成员出席（在压缩步骤之后）】
native <methods>;
}
-keepclasseswithmembers class * { 【保护指定的类和类的成员，但条件是所有指定的类和类成员是要存在。】
public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {【保护指定类的成员，如果此类受到保护他们会保护的更好 】
public void *(android.view.View);
}
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {【保护指定的类文件和类的成员】
public static final android.os.Parcelable$Creator *;
}

参考：http://blog.csdn.net/vrix/article/details/7100841

加入第三方jar包之后常出现的几个异常：
proguard returned with error code 1.See console
情况1：
Proguard returned with error code 1. See console 
Error: C:/Documents (系统找不到指定文件) 
后来发现是因为将整个工程放到了桌面上，而桌面的目录是C:/Documents and Settings/Administrator/桌面，在这里面有空格，而proguard进行发编译的时候是不允许有空格的
如果换了正确路径还不好用的话，直接删除proguard就好了
注意：SDK和程序路径最好不要有空格符
情况2：
Proguard returned with error code 1. See console 
异常：
java.lang.ArrayIndexOutOfBoundsException
解决办法：将proguard.cfg中的"-dontpreverify"改成“-dontoptimize”
参考文章：http://groups.google.com/group/android-developers/browse_thread/thread/eca3b0f5ce6ad00f

我把项目中生成的proguard文件夹（此时文件夹是空的）删掉，然后再重新运行项目，就OK 了。
 
情况3：
[2011-10-21 13:22:32 - ZMKSMarket_Build_v1.0] Proguard returned with error code 1. See console
[2011-10-21 13:22:32 - ZMKSMarket_Build_v1.0] java.io.IOException: Can't read [proguard.ClassPathEntry@106082] (No such file or directory)
[2011-10-21 13:22:32 - ZMKSMarket_Build_v1.0] 
at proguard.InputReader.readInput(InputReader.java:230)
[2011-10-21 13:22:32 - ZMKSMarket_Build_v1.0] 
at proguard.InputReader.readInput(InputReader.java:200)
[2011-10-21 13:22:32 - ZMKSMarket_Build_v1.0] 
at proguard.InputReader.readInput(InputReader.java:178)
[2011-10-21 13:22:32 - ZMKSMarket_Build_v1.0] 
at proguard.InputReader.execute(InputReader.java:100)
[2011-10-21 13:22:32 - ZMKSMarket_Build_v1.0] 
at proguard.ProGuard.readInput(ProGuard.java:195)
[2011-10-21 13:22:32 - ZMKSMarket_Build_v1.0] 
at proguard.ProGuard.execute(ProGuard.java:78)
[2011-10-21 13:22:32 - ZMKSMarket_Build_v1.0] 
at proguard.ProGuard.main(ProGuard.java:499)
 
抛出这样的异常的原因是第三方jar的引用路径不对，没有找到这个需要忽略混淆的jar包。