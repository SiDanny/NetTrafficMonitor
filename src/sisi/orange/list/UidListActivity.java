package sisi.orange.list;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sisi.orange.data.AppInfoItem;
import sisi.orange.data.AppListAdapter;
import sisi.orange.data.Data;
import sisi.orange.view.R;
import sisi.orange.view.ScrollLayout;
import sisi.orange.view.ScrollLayout.SnapListener;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class UidListActivity extends Activity implements
		OnCheckedChangeListener, OnItemClickListener {
	private static final String SCHEME = "package";
	/** * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.1及之前版本) */
	private static final String APP_PKG_NAME_21 = "com.android.settings.ApplicationPkgName";
	/** * 调用系统InstalledAppDetails界面所需的Extra名称(用于Android 2.2) */
	private static final String APP_PKG_NAME_22 = "pkg";
	/** * InstalledAppDetails所在包名 */
	private static final String APP_DETAILS_PACKAGE_NAME = "com.android.settings";
	/** InstalledAppDetails类名 */
	private static final String APP_DETAILS_CLASS_NAME = "com.android.settings.InstalledAppDetails";

	private static final String tag = "UidActivity";
	List<AppInfoItem> listAppInfo;
	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_up);
	
		listAppInfo = Data.getListAppInfo(this);
		AppListAdapter oAdapter = new AppListAdapter(this);
		if (listAppInfo != null && listAppInfo.size() != 0) {
			oAdapter.setList(listAppInfo);
		}
		ListView listView = (ListView) findViewById(R.id.netinfoList);
		listView.setAdapter(oAdapter);
		listView.setOnItemClickListener(this);
		
		loadViewFirst();
		loadViewThirdAndFour();
	}

	private void loadViewThirdAndFour() {
		// TODO Auto-generated method stub
		ListView listview = (ListView) (findViewById(R.id.scroll)
				.findViewById(R.id.listview2));
		if (listview.getAdapter() != null)
			return;
		String info = PhoneInfo.getPhoneInfo(this).toString();
		int index = 0;
		int start = 0;
		ArrayList<String> list = new ArrayList<String>();
		while ((index = info.indexOf("\n", start + 1)) != -1) {
			list.add(info.substring(start, index));
			start = index;
		}

		listview.setClickable(false);
		listview.setAdapter(new ArrayAdapter<String>(this,
				R.layout.hardware_item, (List<String>) list));
		TextView tv = (TextView) findViewById(R.id.youmo);
		Random r = new Random(System.currentTimeMillis());

		String[] youmo = new String[getResources()
				.getStringArray(R.array.youmo).length];
		youmo = getResources().getStringArray(R.array.youmo);
		if (youmo != null) {
			int i = Math.abs(r.nextInt()) % (youmo.length);
			tv.setText(youmo[i]);
		}
	}

	private void loadViewFirst() {
		// TODO Auto-generated method stub
		ScrollLayout scroll = (ScrollLayout) findViewById(R.id.scroll);
		scroll.setSnapListener(new SnapListener() {

			@Override
			public void dealSnap(int mCurScreen) {
				// TODO Auto-generated method stub
				Log.v(tag, "scroll is " + mCurScreen);

				RadioButton ag = (RadioButton) findViewById(R.id.list_radio_button1
						+ mCurScreen);
				ag.setChecked(true);
			}
		});
		scroll.setToScreen(0);
		RadioGroup rg = (RadioGroup) findViewById(R.id.list_main_radio);
		rg.setOnCheckedChangeListener(this);
	}

	// 系统函数，字符串转换 long -String (kb)
	/*
	 * private String formateFileSize(long size){ return
	 * Formatter.formatFileSize(this, size); }
	 */

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		ScrollLayout sLayout = (ScrollLayout) findViewById(R.id.scroll);
		sLayout.setToScreen(checkedId - R.id.list_radio_button1);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		String pkg = "";
		pkg = listAppInfo.get(arg2).getPackageName();

		showInstalledAppDetails(pkg);
	}
	
	public void showInstalledAppDetails(String packageName) {
		Intent intent = new Intent();
		final int apiLevel = Build.VERSION.SDK_INT;
		if (apiLevel >= 9) { // 2.3（ApiLevel 9）以上，使用SDK提供的接口
			intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
			Uri uri = Uri.fromParts(SCHEME, packageName, null);
			intent.setData(uri);
		} else { // 2.3以下，使用非公开的接口（查看InstalledAppDetails源码）
			// 2.2和2.1中，InstalledAppDetails使用的APP_PKG_NAME不同。
			final String appPkgName = (apiLevel == 8 ? APP_PKG_NAME_22
					: APP_PKG_NAME_21);
			intent.setAction(Intent.ACTION_VIEW);
			intent.setClassName(APP_DETAILS_PACKAGE_NAME,
					APP_DETAILS_CLASS_NAME);
			intent.putExtra(appPkgName, packageName);
		}
		this.startActivity(intent);
	}

}
