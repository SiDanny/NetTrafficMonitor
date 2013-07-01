package sisi.orange.list;
                           
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import sisi.orange.view.R;
import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

/**
* retrieve phone info
*
* 
*/
public class PhoneInfo {
        private static final String FILE_MEMORY = "/proc/meminfo";
        private static final String FILE_CPU = "/proc/cpuinfo";
        public String mIMEI;
        public String mPhoneType;
        public String mSysVersion;
        public String mNetWorkCountryIso;
        public String mNetWorkOperator;
        public String mNetWorkOperatorName;
        public String mNetWorkType;
        public boolean mIsOnLine;
        public String mConnectTypeName;
        public String mMemInfo;
        public String mCupInfo;
        public String mProductName;
        public String mModelName;
        public String mManufacturerName;
		private String mDisplay;

        /**
         * private constructor
         */
        private PhoneInfo() {

        }

        /**
         * get imei
         * 
         * @return
         */
        public static String getIMEI(Context context) {
        	TelephonyManager manager = (TelephonyManager) context
        			.getSystemService(Activity.TELEPHONY_SERVICE);
        	// check if has the permission
        	if (PackageManager.PERMISSION_GRANTED == context.getPackageManager()
        			.checkPermission(Manifest.permission.READ_PHONE_STATE,
        					context.getPackageName())) {
        		return manager.getDeviceId();
        	} else {
        		return null;
        	}
        }

        /**
         * get phone type,like :GSM CDMA
         * 
         * @param context
         * @return
         */
        public static int getPhoneType(Context context) {
                TelephonyManager manager = (TelephonyManager) context
                                .getSystemService(Activity.TELEPHONY_SERVICE);
                return manager.getPhoneType();
        }

        /**
         * get phone sys version
         * 
         * @return
         */
        public static String getSysVersion() {
                return Build.VERSION.RELEASE;
        }

        /**
         * Returns the ISO country code equivalent of the current registered
         * operator's MCC (Mobile Country Code).
         * 
         * @param context
         * @return
         */
        public static String getNetWorkCountryIso(Context context) {
                TelephonyManager manager = (TelephonyManager) context
                                .getSystemService(Activity.TELEPHONY_SERVICE);
                return manager.getNetworkCountryIso();
        }

        /**
         * Returns the numeric name (MCC+MNC) of current registered operator.may not
         * work on CDMA phone
         * 
         * @param context
         * @return
         */
        public static String getNetWorkOperator(Context context) {
                TelephonyManager manager = (TelephonyManager) context
                                .getSystemService(Activity.TELEPHONY_SERVICE);
                return manager.getNetworkOperator();
        }

        /**
         * Returns the alphabetic name of current registered operator.may not work
         * on CDMA phone
         * 
         * @param context
         * @return
         */
        public static String getNetWorkOperatorName(Context context) {
                TelephonyManager manager = (TelephonyManager) context
                                .getSystemService(Activity.TELEPHONY_SERVICE);
                return manager.getNetworkOperatorName();
        }

        /**
         * get type of current network
         * 
         * @param context
         * @return
         */
        public static int getNetworkType(Context context) {
                TelephonyManager manager = (TelephonyManager) context
                                .getSystemService(Activity.TELEPHONY_SERVICE);
                return manager.getNetworkType();
        }

        /**
         * is webservice aviliable
         * 
         * @param context
         * @return
         */
        public static boolean isOnline(Context context) {
                ConnectivityManager manager = (ConnectivityManager) context
                                .getSystemService(Activity.CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                        return true;
                }
                return false;
        }

        /**
         * get current data connection type name ,like ,Mobile��WIFI��OFFLINE
         * 
         * @param context
         * @return
         */
        public static String getConnectTypeName(Context context) {
                if (!isOnline(context)) {
                        return "OFFLINE";
                }
                ConnectivityManager manager = (ConnectivityManager) context
                                .getSystemService(Activity.CONNECTIVITY_SERVICE);
                NetworkInfo info = manager.getActiveNetworkInfo();
                if (info != null) {
                        return info.getTypeName();
                } else {
                        return "OFFLINE";
                }
        }

        /**
         * get free memory of phone, in M
         * 
         * @param context
         * @return
         */
        public static long getFreeMem(Context context) {
                ActivityManager manager = (ActivityManager) context
                                .getSystemService(Activity.ACTIVITY_SERVICE);
                MemoryInfo info = new MemoryInfo();
                manager.getMemoryInfo(info);
                long free = info.availMem / 1024 / 1024;
                return free;
        }

        /**
         * get total memory of phone , in M
         * 
         * @param context
         * @return
         */
        public static long getTotalMem(Context context) {
                try {
                        FileReader fr = new FileReader(FILE_MEMORY);
                        BufferedReader br = new BufferedReader(fr);
                        String text = br.readLine();
                        String[] array = text.split("\\s+");
                        return Long.valueOf(array[1]) / 1024;
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return -1;
        }

        public static String getCpuInfo() {
                try {
                        FileReader fr = new FileReader(FILE_CPU);
                        BufferedReader br = new BufferedReader(fr);
                        String text = br.readLine();
                        String[] array = text.split(":\\s+", 2);
                        return array[1];
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return null;
        }

        /**
         * get product name of phone
         * 
         * @return
         */
        public static String getProductName() {
                return Build.PRODUCT;
        }

        /**
         * get model of phone
         * 
         * @return
         */
        public static String getModelName() {
                return Build.MODEL;
        }

        /**
         * get Manufacturer Name of phone
         * 
         * @return
         */
        public static String getManufacturerName() {
                return Build.MANUFACTURER;
        }

        public static PhoneInfo getPhoneInfo(Context context) {
                PhoneInfo result = new PhoneInfo();
                String unknow = context.getResources().getString(R.string.unknowned);
                String str="";
                result.mIMEI = context.getResources().getString(R.string.IMIE)+": "+getIMEI(context);
                switch (getPhoneType(context)) {
				case TelephonyManager.PHONE_TYPE_CDMA:
					str = "CDMA";
					break;
				case TelephonyManager.PHONE_TYPE_GSM:
					str = "GSM";
					break;					
				default:
					str = unknow;
					break;
				}
                result.mPhoneType = context.getResources().getString(R.string.PhoneType)+": "+str;
                result.mSysVersion = context.getResources().getString(R.string.SysVersion)
                		+": "+getSysVersion();
                result.mNetWorkCountryIso = context.getResources().getString(R.string.NetWorkCountryIso)
                		+": "+getNetWorkCountryIso(context);
                result.mNetWorkOperatorName = context.getResources().getString(R.string.NetWorkOperator)
                		+": "+getNetWorkOperatorName(context);
                int flag = getNetworkType(context);
                switch (flag) {
				case TelephonyManager.NETWORK_TYPE_CDMA:
					str = "CDMA";
					break;
				case TelephonyManager.NETWORK_TYPE_EDGE:
					str = "EDGE";
					break;		
				case TelephonyManager.NETWORK_TYPE_UMTS:
					str = "UMTS";
					break;	
				case TelephonyManager.NETWORK_TYPE_GPRS:
					str = "GPRS";
					break;	
				default:
					str = unknow;
					break;
				}
                if(str.equals(unknow)&&Build.VERSION.SDK_INT>7){
                	str = NetworkType.getType(flag);
                	if(str.equals(""))
                		str = unknow;
                }
                result.mNetWorkType = context.getResources().getString(R.string.NetWorkType)+": "+str;
                result.mConnectTypeName = context.getResources().getString(R.string.ConnectTypeName)
                		+": "+getConnectTypeName(context);
                result.mMemInfo = context.getResources().getString(R.string.FreeMem)+
                		": "+getFreeMem(context)+"MB/"+getTotalMem(context)+"MB";
                result.mCupInfo = context.getResources().getString(R.string.CpuInfo)+": "+getCpuInfo();
                result.mModelName = context.getResources().getString(R.string.ModelName)+": "+getModelName();
                result.mDisplay = getDisplayMetrics(context);
                return result;
        }
        
        public static String getDisplayMetrics(Context cx) {
    		String str = "";
    		DisplayMetrics dm = new DisplayMetrics();
    		dm = cx.getApplicationContext().getResources().getDisplayMetrics();
    		int screenWidth = dm.widthPixels;
    		int screenHeight = dm.heightPixels;
    		float xdpi = dm.xdpi;
    		float ydpi = dm.ydpi;
    		str += cx.getResources().getString(R.string.dis0) +":"+ screenWidth+"*"+screenHeight + "\n";
    		str += cx.getResources().getString(R.string.dis1) +String.format(":%.1f*", xdpi)+
    				String.format("%.1f",ydpi);
    		return str;
    	}


        @Override
        public String toString() {
                StringBuilder builder = new StringBuilder();
                builder.append(mModelName+"\n");
                builder.append(mCupInfo+"\n");
                builder.append(mIMEI+"\n");               
                builder.append(mMemInfo+"\n");
                builder.append(mNetWorkOperatorName+"\n");
                builder.append(mPhoneType+"\n");
                builder.append(mNetWorkType+"\n"); 
                builder.append(mDisplay+"\n");
                builder.append(mNetWorkCountryIso+"\n");                
                builder.append(mConnectTypeName+"\n"); 
                builder.append(mSysVersion+"\n");
                
                return builder.toString();
        }
        
}