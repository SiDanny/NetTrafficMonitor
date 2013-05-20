package sisi.orange.data;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;



public final class Data {    
	private static final String tag = "Data";
	static Bundle b;
	static ContentValues values = new ContentValues();
	public static double[][] getListDateTrafficInfo(Context context) {
		// TODO Auto-generated method stub
		
		Cursor c = context.getContentResolver().query(DBStr.CONTENT_URI, 
				new String[]{DBStr.DAY, DBStr.MOBILE, DBStr.WIFI}, 
				DBStr.DAY+">?", new String[]{"0"}, DBStr.DAY+" ASC");

		double[][] list = new double[c.getColumnCount()][c.getCount()];
		int index=0;
		while (c.moveToNext()) {
			list[0][index] = c.getInt(0);
			list[1][index] = c.getInt(1)/1048576d;
			list[2][index] = c.getInt(2)/1048576d;
			index++;
		}
		c.close();
		//Log.v("data",c.getColumnCount()+" "+c.getCount() /*list[0].length+" "+list[0].length+" "+list[0].length*/);
		return list;
	}
	
	public static void updateTempTraffic(Context context, long temp,
			String category) {
		// TODO Auto-generated method stub
		update(context, -2, (int) temp, category);
	}

	public static void setNotifyUnit(Context context, int unit){
		update(context, -6, unit, "mobile");
	}
	public static int getNotifyUnit(Context context){
		return getFlagData(context, -6, "mobile");
	}
	public static boolean isPreNetAvaiable(Context context, String category){
		return getFlagData(context, -8, category)==1?true:false;
	}
	public static void setNetAvaiable(Context context, boolean netAvaiable, String category){
		update(context, -8, netAvaiable?1:0, category);
	}


	public static int getFlagData(Context context,int flag,String field){
		Cursor c = context.getContentResolver().query(DBStr.CONTENT_URI, 
				new String[]{DBStr.DAY, field}, 
				DBStr.DAY+"=?", new String[]{""+flag}, null);
		int i=-1;
		if(c.getCount()==1){
			c.moveToFirst();
			i=c.getInt(1);
		}
		c.close();
		Log.v(tag,flag+" get "+i+" "+field);
		return i;
	}
	
	/***
	 * 若不存在该记录，则插入
	 * @param flag 0到-5 sum,history,tempTraffic,tempday, notifiTime,NOTIFI_SHOW_FLAG ,
	 * -6~-11 backgroundIndex,isCustom,netAvaiable,notifUnit,line,point,
	 */	
	public static void update(Context context,int flag,int data,String field){
		if(values==null)
			values = new ContentValues();
		else
			values.clear();
		values.put(DBStr.DAY, flag);
		values.put(field, data);
		Cursor c = context.getContentResolver().query(DBStr.CONTENT_URI, new String[]{DBStr.DAY}, 
				DBStr.DAY+"=?", new String[]{""+flag},null);

		try {
			if(c.getCount()==0)
				context.getContentResolver().insert(DBStr.CONTENT_URI, values);
			else
				context.getContentResolver().update(DBStr.CONTENT_URI, values, 
						DBStr.DAY+"=?", new String[]{""+flag});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.close();
		//Log.v("Data ","update rows "+flag+" "+data);
	}
	/***
	 * 月统计信息出错，删除这一个月流量记录
	 * @param day
	 */
	public static void clearDataOverMonth(Context context){
		Log.v("Data ","delete all data");
		ContentValues value = new ContentValues();
		value.put("mobile", 0);
		value.put("wifi", 0);
		context.getContentResolver().update(DBStr.CONTENT_URI, value, DBStr.DAY+"<? AND "+DBStr.DAY+">?", 
				new String[]{32+"","0"});
	}

	public static void updateList(Context context,int uid, int up, int down){
		if(values==null)
			values = new ContentValues();
		else
			values.clear();
		values.put(DBStr.UPNUM,up);
		values.put(DBStr.DOWNNUM,down);
		context.getContentResolver().update(DBStr.LIST_URI, values, DBStr.UID+"=?", new String[]{""+uid});
	}

	public static void zeroapk(Context context){
		/*ContentValues values = new ContentValues();
		if(values==null)
			values = new ContentValues();
		else
			values.clear();
		values.put(DBStr.UPNUM,0);
		values.put(DBStr.DOWNNUM,0);*/
		//context.getContentResolver().update(DBStr.LIST_URI, values , DBStr.UID+">?", new String[]{"0"});
		context.getContentResolver().delete(DBStr.LIST_URI, "1=?", new String[]{"1"});
		//Log.v("data update",rows+" rows");
	}
	/***
	 * 用于每天查询是否有新的联网程序，若有便调用该函数
	 * @param uid
	 */
	public static void insert(Context context,int uid,CharSequence label,
			String pkgName,String versionName) {
		// TODO Auto-generated method stub
		Cursor c=context.getContentResolver().query(DBStr.LIST_URI, new String[]{DBStr.UID}, DBStr.UID+"=?",new String[]{""+uid}, null);
		if(c.getCount()==0){
			if(values==null)
				values = new ContentValues();
			else
				values.clear();
			values.put(DBStr.UID, uid);
			values.put(DBStr.LABEL, label.toString());
			values.put(DBStr.DOWNNUM, 0);
			values.put(DBStr.UPNUM, 0);
			values.put(DBStr.PACKAGE, pkgName);
			//values.put(DBStr.VERSIONNAME, versionName);
			try {
				context.getContentResolver().insert(DBStr.LIST_URI, values);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		c.close();
	}	
	
	public static void update(Context context,String pkgName, String size){
		if(values==null)
			values = new ContentValues();
		else
			values.clear();
		//values.put(DBStr.SIZE,size);
		context.getContentResolver().update(DBStr.LIST_URI, values, DBStr.PACKAGE+" =?", new String[]{pkgName});
	}	
	/***
	 * 获取数据，若第一次使用（数据库空）就初始化数据并写入数据库
	 * @param context
	 * @param field
	 * @return
	 */
	public static ServiceData getServiceData(Context context, String field) {
		// TODO Auto-generated method stub
		ServiceData data ;
		Cursor c = context.getContentResolver().query(DBStr.CONTENT_URI, new String[]{DBStr.DAY,field}, 
				DBStr.DAY+"<? AND "+DBStr.DAY+">?", new String[]{"1","-8"}, DBStr.DAY+" DESC");
		int lenth = c.getCount();
		//Log.v("Data","getData count="+ lenth);
		if(lenth == 0)
		{
			Data.update(context, 0, 104875600, field);
			Data.update(context, -1, 0, field);
			Data.update(context, -2, 0, field);
			Data.update(context, -3, 1, field);
			Data.update(context, -4, 0, field);
			Data.update(context, -5, 0, field);
			Data.update(context, -6, 1, field);
			Data.update(context, -7, 1, field);
			Data.update(context, -9, 1048756, field);
			return new ServiceData(1048756L,0L,0L,1,0L,0,0);
		}
		else{
			data = new ServiceData();
			while (c.moveToNext()) {
				int mData = c.getInt(c.getColumnIndex(field));
				switch (c.getInt(c.getColumnIndex(DBStr.DAY))) {
				case 0:
					
					data.setSum(mData < 10485760?10485760:mData);
					break;
				case -1:
					data.setHistory(mData);
					break;
				case -2:
					data.setTempTraffic(mData);
					break;
				case -3:
					data.setDateDay(mData);
					break;
				case -4:
					data.setNotifyData(mData);
					break;
				case -5:
					data.setNOTIFI_SHOW_FLAG(mData);
					break;
				case -7:
					data.setLine(mData);
					break;
				default:
					break;
				}
			}
			c.close();
			return data;				
		}
	}
	
	public static void setServiceDataByService(Context context, ServiceData data, String field){
		Data.update(context, -1, data.getHistory(), field);
		Data.update(context, -2, data.getTempTraffic(), field);
		Data.update(context, -3, data.getDateDay(), field);
		Data.update(context, -4, data.getNotifyData(), field);
		Data.update(context, -7, data.getLine(), field);
	}
	
	public static void getSettingData(Context context, ServiceData data, String field){
		data.setSum(Data.getFlagData(context, 0, field));
		data.setHistory(Data.getFlagData(context, -1, field));
		data.setTempTraffic(Data.getFlagData(context, -2, field));
		data.setNOTIFI_SHOW_FLAG(Data.getFlagData(context, -5, field));
		data.setLine(Data.getFlagData(context, -7, field));
	}
	
	public static void setServiceDataByActivity(Context context, ServiceData data){
		String field = "mobile";
		Data.update(context, 0, data.getSum(), field);
		Data.update(context, -1, data.getHistory(), field);
		Data.update(context, -2, data.getTempTraffic(), field);
		Data.update(context, -5, data.getNOTIFI_SHOW_FLAG(), field);
		Data.update(context, -5, data.getNOTIFI_SHOW_FLAG(), "wifi");
		Data.update(context, -6, data.getNotifyUnite(), field);
		Data.update(context, -7, data.getLine(), field);
	}
	

	public static List<AppInfoItem> getListAppInfo(Context context) {
		// TODO Auto-generated method stub
		Cursor c=context.getContentResolver().query(DBStr.LIST_URI, new String[]{DBStr.UID, 
				 DBStr.LABEL,DBStr.PACKAGE ,DBStr.ICON, DBStr.UPNUM, DBStr.DOWNNUM}, null, null, DBStr.DOWNNUM+" DESC");
		c.moveToFirst();
		List<AppInfoItem> list = new ArrayList<AppInfoItem>();
		AppInfoItem item;
		byte[] bytes = new byte[10240];
		while(c.moveToNext()){
			//索引号根据查询语句的字段顺序而来
			item = new AppInfoItem();
			item.setUid(c.getInt(0));
			item.setName(c.getString(1));
			item.setPackageName(c.getString(2));
			bytes = c.getBlob(3);
			item.setIcon(bytes2Drawable(bytes));
			item.setTxBytes(c.getLong(4));
			item.setRxBytes(c.getLong(5));
			list.add(item);
		}
		c.close();
		return list;
	}
	public static void updateListAppInfo(Context context, List<AppInfoItem> list) {
		// TODO Auto-generated method stub
		values = new ContentValues();
		for(AppInfoItem info:list){
			values.clear();
			values.put(DBStr.UID, info.getUid());
			values.put(DBStr.ICON, drawable2bytes(info.getIcon()));
			values.put(DBStr.PACKAGE, info.getPackageName());
			values.put(DBStr.LABEL, info.getName());
			values.put(DBStr.UPNUM, info.getTxBytes());
			values.put(DBStr.DOWNNUM, info.getRxBytes());
			context.getContentResolver().update(DBStr.LIST_URI, values, DBStr.UID+"=?", new String[]{""+info.getUid()});
		}
		
	}
	/*context.getContentResolver().update(DBStr.LIST_URI, values, DBStr.UID+"=?", new String[]{""+uid});
	Cursor c=context.getContentResolver().query(DBStr.LIST_URI, new String[]{DBStr.UID}, null, null, null);
	*/
	public static Drawable bytes2Drawable(byte[] blob){
		//第二步，调用BitmapFactory的解码方法decodeByteArray把字节数组转换为Bitmap对象
		Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
		//第三步，调用BitmapDrawable构造函数生成一个BitmapDrawable对象，该对象继承Drawable对象，所以在需要处直接使用该对象即可
		BitmapDrawable bd = new BitmapDrawable(bmp);
		return (Drawable)bd;
	}
	public static byte[] drawable2bytes(Drawable icon){
		Bitmap bmp = (((BitmapDrawable)icon).getBitmap());
		//第二步，声明并创建一个输出字节流对象
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		//第三步，调用compress将Bitmap对象压缩为PNG格式，第二个参数为PNG图片质量，第三个参数为接收容器，即输出字节流os
		bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
		return os.toByteArray();
	}
	public static final int NETAVAILABLE = 6;	 
	public static final int NETUNAVAILABLE = 8;
	//public static final String TODAY = "today";
	//public static final String HISTORY = "history";
	//public static final String SUM = "sum";
	//public static final String NOTIFI_SHOW_WAY = "notifi.show.way";
	public static final String NET_CHANGE="android.net.conn.CONNECTIVITY_CHANGE";
	/**	 * 界面向servie发送通知改变	 */
	public static final String ACTI_SET_FLAG = "from.activity.set.flag";
	public static final int AUTO = 0;
	//public static final int ALWAYS = 1;
	public static final int ALWAYS_NOT = 1;	
	public static final int FOR_DATA_NOTIFI = 2;
	public static final String POLY_INFO = "poly_info";
	public static final String POLY_INFO_DAY = "day_info_day";
	public static final String POLY_INFO_NUM = "day_info_arr";	
	
}
