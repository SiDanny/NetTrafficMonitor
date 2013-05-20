package sisi.orange.list;

import android.telephony.TelephonyManager;

public class NetworkType {
	public static String getType(int flag){
		String str="";
		switch (flag) {
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			str = "HSDPA";
			break;
		case TelephonyManager.NETWORK_TYPE_HSPA:
			str = "HSPA";
			break;		
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			str = "HSUPA";
			break;	
		case TelephonyManager.NETWORK_TYPE_IDEN:
			str = "IDEN";
			break;	
		default:
			str = "";
			break;
		}
		return str;
	}
}
