package sisi.orange.list;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sisi.orange.data.AppInfoItem;
import sisi.orange.data.NetTraffic;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.RemoteException;
import android.util.Log;

public class TaskManager {
	private Context context = null;

	protected static final int MSG_BYTES_CHANGED = 0x101;

	List<AppInfoItem> allAppItem;

	public static TaskManager instance;
	
	public static synchronized TaskManager getInstance(Context context)
	{
		if(instance==null)
		{
			instance=new TaskManager(context);
		}
		return instance;
	}
	public TaskManager(Context context) {
		this.context = context;
		Log.d("TaskManager", "TaskManager init");
	}
	
	public void loadAppList()
	{
		Log.d("TaskManager", "TaskManager LoadAppList");
		allAppItem=getAppInfoList();
	}

	public List<AppInfoItem> updateList()
	{
		return updateList(allAppItem);
	}
	private List<AppInfoItem> updateList(List<AppInfoItem> appList) {
		List<AppInfoItem> newappList=new ArrayList<AppInfoItem>();
		for (AppInfoItem item : appList) {
			item.setRxBytes(NetTraffic.getUidRxBytes(item.getUid()));
			item.setTxBytes(NetTraffic.getUidTxBytes(item.getUid()));
			newappList.add(item);
		}
		Collections.sort(newappList);
		return newappList;
	}

	public List<AppInfoItem> getAppInfoList() {

		List<AppInfoItem> appList = new ArrayList<AppInfoItem>();

		final PackageManager packageManager=context.getPackageManager();

		final List<ApplicationInfo> mApps=packageManager.getInstalledApplications(0);
		for (ApplicationInfo info : mApps) {

			if ((context.getPackageManager().checkPermission(
					"android.permission.INTERNET", info.packageName) == 0)
					||((info.flags & ApplicationInfo.FLAG_SYSTEM) != 1)) {

				AppInfoItem item;
				item = getAppInfoItembyUid(info.uid, appList);
				if (item != null) {
					AppInfoItem subitem = new AppInfoItem();
					subitem.setIcon(info.loadIcon(context.getPackageManager()));
					subitem.setName(info.loadLabel(context.getPackageManager())
							.toString());
					subitem.setUid(info.uid);
					subitem.setPackageName(info.packageName);
					subitem.setCustomApp((info.flags & ApplicationInfo.FLAG_SYSTEM) != 1);
					subitem.setRxBytes(NetTraffic.getUidRxBytes(item.getUid()));
					subitem.setTxBytes(NetTraffic.getUidTxBytes(item.getUid()));
					subitem.addSubItems(subitem);
				} else {
					item = new AppInfoItem();
					item.setIcon(info.loadIcon(context.getPackageManager()));
					item.setName(info.loadLabel(context.getPackageManager())
							.toString());
					item.setUid(info.uid);
					item.setPackageName(info.packageName);
					item.setRxBytes(NetTraffic.getUidRxBytes(item.getUid()));
					item.setTxBytes(NetTraffic.getUidTxBytes(item.getUid()));
					appList.add(item);
				}

			}
		}
		return appList;
	}
	
	private AppInfoItem getAppInfoItembyUid(int uid,List<AppInfoItem> applist)
	{
		if(applist!=null)
		{
			for(AppInfoItem app :applist)
			{
				if(app.getUid()==uid)
					return app;
			}
		}
		return null;
	}
	
	private void queryPacakgeSize(String pkgName) {
		if ( pkgName != null){
    		PackageManager pm = context.getPackageManager();  //寰楀埌pm瀵硅薄
    		try {			
    			Method getPackageSizeInfo = pm.getClass().getDeclaredMethod(
						"getPackageSizeInfo", String.class, android.content.pm.IPackageStatsObserver.class);

			    getPackageSizeInfo.invoke(pm, pkgName,new PkgSizeObserver(pkgName));
			} 
        	catch(Exception ex){
        		Log.e("queryPackageSize", "NoSuchMethodException") ;
        		ex.printStackTrace() ;
        	} 
    	}
	}


	private class PkgSizeObserver extends android.content.pm.IPackageStatsObserver.Stub {
		String pkgName;

		public PkgSizeObserver(String Name) {
			pkgName = Name;
		}


		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			// TODO Auto-generated method stub
			if (succeeded) {
				long cachesize = pStats.cacheSize;
				long datasize = pStats.dataSize;
				long codesize = pStats.codeSize;
				long total = cachesize + datasize + codesize;
			}
		}
	}
}

