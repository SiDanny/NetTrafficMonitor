package sisi.orange.view.calculate;

import java.util.Calendar;
import java.util.List;

import sisi.orange.data.AppInfoItem;
import sisi.orange.data.Data;
import sisi.orange.data.NetTraffic;
import sisi.orange.data.ServiceData;
import sisi.orange.list.TaskManager;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.Formatter;
import android.util.Log;

public abstract class AbstractService extends Service {
	protected ServiceData serviceData = null;
	protected Context context = this;
	protected Broad mBroad ;
	private NetReceiver receiver = null;
	protected String category;
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case Data.NETAVAILABLE:
				Log.v(category,"handler avaiable ");
				handler.removeCallbacks(inqueryRunnable);
				handler.postDelayed(inqueryRunnable, serviceData.getDelay());	
				serviceData.setNetAvaiable(true);
				break;
			case Data.NETUNAVAILABLE:
				Log.v(category,"handler UNavaiable ");
				handler.removeCallbacks(inqueryRunnable);
				serviceData.setNetAvaiable(false);
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	private Runnable inqueryRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			//dealNotif();
			print("inquery ");
			long temp = NetTraffic.getTotalBytes(category);
			if(temp<serviceData.getTempTraffic()){
				serviceData.setHistory(serviceData.getHistory()+serviceData.getTempTraffic());
			}
			serviceData.setTempTraffic((int) temp);
			dealNotifycation();
			Data.updateTempTraffic(AbstractService.this, temp, category);
			Data.update(context, serviceData.getDateDay(), (int) serviceData.getTodayUseData(), category);
			handler.postDelayed(inqueryRunnable, serviceData.getDelay());
		}
	};
	
	public void loadInitService(){
		receiver = new NetReceiver(new NetStateChangeImpl(){

			@Override
			public void ClosingProcess() {
				// TODO Auto-generated method stub				
				handler.sendEmptyMessage(Data.NETUNAVAILABLE);
				System.out.println("net is closing");
				serviceData.setNetAvaiable(false);
				stopSelf();
			}

			@Override
			public void openingProcess() {
				// TODO Auto-generated method stub
				handler.post(inqueryRunnable);
				serviceData.setNetAvaiable(true);
				System.out.println("net is opening");
			}

			@Override
			public void setDataProcess() {
				// TODO Auto-generated method stub
				Data.getSettingData(getApplicationContext(), serviceData, category);
				dealNotifycation();
			}},category);
		IntentFilter nfilter=new IntentFilter();  
		nfilter.addAction(Data.NET_CHANGE);
		nfilter.addAction(Data.ACTI_SET_FLAG);
		registerReceiver(receiver, nfilter);	
		serviceData = Data.getServiceData(context, category);
		mBroad = new Broad(this,(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE),
				serviceData.getNOTIFI_SHOW_FLAG());
		mBroad.AddNotification();		
	}	

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Log.v(category, "onCreate()");
		if (BootReceiver.checkNet(this, category)) {
			loadInitService();
			checkDate();
			handler.sendEmptyMessage(Data.NETAVAILABLE);
			Log.v(category, "onCreate() net is on");
		}
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.i(category,intent.getAction()+"");
		return null;
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		print(" onDestory()");
		
		serviceData.setNetAvaiable(false);
		handler.removeCallbacks(inqueryRunnable);
		unregisterReceiver(receiver);
		print("调用Destory函数并记录缓存");	
		if(mBroad!=null)
			mBroad.cancel();
		Data.setServiceDataByService(this, serviceData, category);
		super.onDestroy();
	}

	public void checkDate() {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		print("checkData");
		if(serviceData.getDateDay()!=day)//日期改变了
		{
			Data.zeroapk(this);
			List<AppInfoItem> list = TaskManager.getInstance(this).getAppInfoList();
			Data.updateListAppInfo(this, list);
			Data.update(context, serviceData.getDateDay(), (int) serviceData.getTodayUseData(), category);
			if (serviceData.getDateDay() > day)//月份
			{
				serviceData.setHistory(0-serviceData.getTempTraffic());
				Data.clearDataOverMonth(this);
			}
			serviceData.setNotifyData(0);	
			serviceData.setLine(serviceData.getTempTraffic()+serviceData.getHistory());
			serviceData.setDateDay(day);
			print("Date Changed");
		 }
		/***
		 * 如果是开机之后
		 */
		long temp = NetTraffic.getTotalBytes(category);
		if(temp<serviceData.getTempTraffic()){
			serviceData.setHistory(serviceData.getHistory()+serviceData.getTempTraffic());
			serviceData.setTempTraffic((int) temp);
		}
		
		Data.setServiceDataByService(context, serviceData, category);
	}

	public void dealNotifycation() {
		// TODO Auto-generated method stub

		//Log.i(category," dealNotifycation()");
		if(mBroad!=null)
			mBroad.notifycation(serviceData.isNetAvaiable(), serviceData.getNOTIFI_SHOW_FLAG(), 
					serviceData.getNotifyData(), serviceData.getMonthUseData(),
					serviceData.getTodayUseData());
	}

	private void print(String str){

		Log.e(category, str
				+formateFileSize(serviceData.getHistory())
				+formateFileSize(serviceData.getTempTraffic())
				+formateFileSize(serviceData.getLine()));
	}
	
	
	//系统函数，字符串转换 long -String (kb)
    private String formateFileSize(long size){
    	return Formatter.formatFileSize(this, size); 
    }
}
