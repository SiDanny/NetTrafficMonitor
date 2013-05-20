package sisi.orange.view.calculate;

import sisi.orange.data.DBStr;
import sisi.orange.data.Data;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		boolean wifiState = checkNet(context, "wifi");
		boolean mobState = checkNet(context, "mobile");
		if(wifiState){
			Log.i("bootReceiver", "start WifiService");
			context.startService(new Intent(context,WifiService.class));
		}
		if(mobState){
			Log.i("bootReceiver", "start MobileService");
			context.startService(new Intent(context,MobileService.class));
		}
		Log.v("boot",wifiState+" "+mobState);
		Data.update(context,-8, wifiState?1:0, DBStr.WIFI);
		Data.update(context,-8, mobState?1:0, DBStr.MOBILE);
	}
	
	/**
	 * 
	 * @param context 上下文
	 * @param isMoble 检查类型是否是移动蜂窝网络
	 * @return
	 */
	public static boolean checkNet(Context context, String category){
    	NetworkInfo[] state=null;
    	boolean avai = false;
    	ConnectivityManager manger;
		try {
			manger = (ConnectivityManager)context.getSystemService(
					Context.CONNECTIVITY_SERVICE);
			state = manger.getAllNetworkInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			avai  = false;
		}
		if(state!=null)
    	{
    		if(category.equals("mobile")){
    			avai = state[ConnectivityManager.TYPE_MOBILE].isConnected();
    		}else{
    			avai = state[ConnectivityManager.TYPE_WIFI].isConnected();
    		}
    	}
    	return avai;
	}

}
