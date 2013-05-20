package sisi.orange.view.calculate;

import sisi.orange.data.Data;
import sisi.orange.view.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.Formatter;

public class Broad {
	private int notifiUnit  = 524288;
	private Context context;
	private Notification notifi;
	private NotificationManager nManger;
	private PendingIntent pIntent;
	private int notifFlag=0;
	public Broad(Context con,NotificationManager manger,int flag){
		context = con;
	    nManger = manger;
	    notifFlag = flag;
	    notifi = new Notification();
	    int temp = Data.getNotifyUnit(con);
	    if(temp!=0)
	    	notifiUnit = temp;
	}
	
	public void setNotifUnit(int unit){
		notifiUnit = unit;
	}
	
	public void AddNotification(){
	    
	    notifi.icon = R.drawable.noti;
	    notifi.flags|=Notification.FLAG_ONGOING_EVENT; 
	    Intent intent = new Intent(context,MainActivity.class);	    
	    pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);    
	    //设置事件信息，显示在拉开的里面	    	
	}
	/**
	 * @month 本月至当下使用量history+temeTraffic
	 * @current 今日使用量history+temeTraffic-line
	 * @return 据流量进行通知次数改变即返回当前次数，否则返回-1*/
	public long notifycation(boolean netAvaiable, int NOTIFI_SHOW_FLAG,
			long notifiData, long month, long current) {
		// TODO Auto-generated method stub
		//Log.v("Broad", NOTIFI_SHOW_FLAG+" notif "+netAvaiable);
		long notifiDataChange = -1;
		if(notifi==null){
			//Log.v("Broad", "notif is null ");
			return notifiDataChange;
		}		
		notifi.setLatestEventInfo(context, context.getResources().getString(R.string.app_name),
				newNotifiText(month, current), pIntent);
		switch (NOTIFI_SHOW_FLAG) {
		case Data.ALWAYS_NOT:
			nManger.cancel(R.drawable.noti);
			break;
		case Data.AUTO:
			if(netAvaiable){
				notifi.flags = Notification.FLAG_ONGOING_EVENT;
				nManger.notify(R.drawable.noti,notifi);
			}else
				nManger.cancel(R.drawable.noti);
			break;
		case Data.FOR_DATA_NOTIFI:
			if(netAvaiable){
				int temp = (int) ((current)/notifiUnit);			
				if((temp>notifiData))
				{
					notifi.tickerText = newNotifiText(month,current);
					notifi.flags= Notification.FLAG_AUTO_CANCEL;
					notifiData = temp;
					notifiDataChange = notifiData;
					nManger.notify(R.drawable.noti,notifi);
				}
			}
			break;
		default:
			break;
		}
		/*if(notifFlag!=NOTIFI_SHOW_FLAG){
			//Log.v("Broad", "flag change ");
			nManger.cancel(R.drawable.noti);
			notifFlag = NOTIFI_SHOW_FLAG;
		}
		if(NOTIFI_SHOW_FLAG!=Data.ALWAYS){			
			switch (NOTIFI_SHOW_FLAG) {
			case Data.ALWAYS_NOT:
				nManger.cancel(R.drawable.noti);
				break;
			case Data.AUTO:
				if(netAvaiable){
					notifi.flags = Notification.FLAG_ONGOING_EVENT;
					nManger.notify(R.drawable.noti,notifi);
				}else
					nManger.cancel(R.drawable.noti);
				break;
			case Data.FOR_DATA_NOTIFI:
				if(netAvaiable){
					int temp = (int) ((current)/notifiUnit);			
					if((temp>notifiData))
					{
						notifi.tickerText = newNotifiText(month,current);
						notifi.flags= Notification.FLAG_AUTO_CANCEL;
						notifiData = temp;
						notifiDataChange = notifiData;
						nManger.notify(R.drawable.noti,notifi);
					}
				}
				break;
			default:
				break;
			}
			
		}else{
			notifi.flags = Notification.FLAG_ONGOING_EVENT;
			nManger.notify(R.drawable.noti,notifi);
		}*/
	
		return notifiDataChange;
	}

	private CharSequence newNotifiText(long month, long current) {
		// TODO Auto-generated method stub
		StringBuffer temp = new StringBuffer(context.getResources().getString(R.string.today_use));	
		temp.append(formateFileSize(current));
		temp.append(context.getResources().getString(R.string.month_use));
		temp.append(formateFileSize(month));
		return temp;	
	}
	
	 //系统函数，字符串转换 long -String (kb)
    private String formateFileSize(long size){
    	size = size<0?0:size;
    	return Formatter.formatFileSize(context, size); 
    }
	public void cancel() {
		// TODO Auto-generated method stub
		nManger.cancel(R.drawable.noti);
	}
}
