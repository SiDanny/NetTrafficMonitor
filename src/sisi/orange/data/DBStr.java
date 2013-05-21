package sisi.orange.data;

import android.net.Uri;

public class DBStr {
	public static final String DBNAME = "siisdb";          
	public static final String TNAME = "type_nettraffic"; 
	public static final String LIST_TNAME = "listnettraffic";
	public static final String RECORDER_TNAME = "recorder_data"; 
	/** 2013-5-4*/
	public static final int VERSION = 8;               
	public static final String TID = "tid";   
	public static final String DAY = "day";    
	public static final String AUTOHORITY = "sisi.orange"; 
	public static final int ITEM = 1;         
	public static final int ITEM_ID = 2;          
	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.sisi.login";       
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.sisi.login";              
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTOHORITY + "/"+TNAME); 
	public static final Uri LIST_URI = Uri.parse("content://" + AUTOHORITY + "/"+LIST_TNAME);
	public static final String UPNUM = "upNumber";
	public static final String DOWNNUM = "downNumber";
	public static final String ISCUSTOM = "isCustom";
	public static final String UID = "uid";
	public static final String TNAME_INTERNET = "table.name.internet";
	public static final String LABEL = "label";
	public static final String PACKAGE = "packageName";
	public static final String WIFI = "wifi";
	public static final String MOBILE = "mobile";
	public static final String ICON = "icon";
	
}
