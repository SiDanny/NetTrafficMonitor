lint.xml�ļ�Ϊ���ӹ����ļ�
proguard.cfg�ļ�Ϊandroid�����ļ�



-injars  androidtest.jar��jar�����ڵ�ַ�� 
-outjars  out�������ַ��
-libraryjars    'D:\android-sdk-windows\platforms\android-9\android.jar' �����õĿ��jar�����ڽ���injars��ָ����jar�ࡿ
 
-optimizationpasses 5
-dontusemixedcaseclassnames ������ʱ�����������ɫɫ������ ��
-dontskipnonpubliclibraryclasses ��ָ����ȥ���Էǹ����Ŀ��ࡣ ��
-dontpreverify ����ԤУ�顿
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/* ���Ż���
-keep public class * extends android.app.Activity�����������л�������ԭ����
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public abstract interface com.asqw.android.Listener{
public protected <methods>;  �����з��������л�����
}
-keep public class com.asqw.android{
public void Start(java.lang.String); ���Ը÷��������л�����
}
-keepclasseswithmembernames class * { ������ָ���������ĳ�Ա�����ƣ��������ָ�������Ա��ϯ����ѹ������֮�󣩡�
native <methods>;
}
-keepclasseswithmembers class * { ������ָ���������ĳ�Ա��������������ָ����������Ա��Ҫ���ڡ���
public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {������ָ����ĳ�Ա����������ܵ��������ǻᱣ���ĸ��� ��
public void *(android.view.View);
}
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {������ָ�������ļ�����ĳ�Ա��
public static final android.os.Parcelable$Creator *;
}

�ο���http://blog.csdn.net/vrix/article/details/7100841

���������jar��֮�󳣳��ֵļ����쳣��
proguard returned with error code 1.See console
���1��
Proguard returned with error code 1. See console 
Error: C:/Documents (ϵͳ�Ҳ���ָ���ļ�) 
������������Ϊ���������̷ŵ��������ϣ��������Ŀ¼��C:/Documents and Settings/Administrator/���棬���������пո񣬶�proguard���з������ʱ���ǲ������пո��
���������ȷ·���������õĻ���ֱ��ɾ��proguard�ͺ���
ע�⣺SDK�ͳ���·����ò�Ҫ�пո��
���2��
Proguard returned with error code 1. See console 
�쳣��
java.lang.ArrayIndexOutOfBoundsException
����취����proguard.cfg�е�"-dontpreverify"�ĳɡ�-dontoptimize��
�ο����£�http://groups.google.com/group/android-developers/browse_thread/thread/eca3b0f5ce6ad00f

�Ұ���Ŀ�����ɵ�proguard�ļ��У���ʱ�ļ����ǿյģ�ɾ����Ȼ��������������Ŀ����OK �ˡ�
 
���3��
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
 
�׳��������쳣��ԭ���ǵ�����jar������·�����ԣ�û���ҵ������Ҫ���Ի�����jar����