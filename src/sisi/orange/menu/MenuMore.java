package sisi.orange.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sisi.orange.data.Data;
import sisi.orange.view.R;
import sisi.orange.view.calculate.AboutActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * wifi下或积分不足显示广告 75行miidi推送广告，分数100限制
 */
public class MenuMore extends Activity {
	private Builder b;
	final Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appinfo_list);
		setTitle(R.string.app_name);
		String[] mStrings = new String[5];
		mStrings = getResources().getStringArray(R.array.menuArray);
		String[] explainStrs = new String[5];
		explainStrs = getResources().getStringArray(R.array.menuArray2);
		b = new AlertDialog.Builder(MenuMore.this).setIcon(R.drawable.noti);
		ListView listview = (ListView) findViewById(R.id.listview2);
		ArrayList<Map<String, String>> strArray = new ArrayList<Map<String, String>>();
		for (int i = 0; i != mStrings.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("item", mStrings[i]);
			map.put("explain", explainStrs[i]);
			strArray.add(map);
		}
		listview.setAdapter(new SimpleAdapter(this, strArray,
				R.layout.menuitem, new String[] { "item", "explain" },
				new int[] { R.id.item, R.id.explain }));
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				switch (arg2) {

				case 0:
					b.setTitle(R.string.about)
							.setMessage(R.string.more00)
							.setPositiveButton(android.R.string.ok,
									new OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											Data.clearDataOverMonth(MenuMore.this);

										}
									})
							.setNegativeButton(android.R.string.cancel, null)
							.show();
					break;
				case 1:
					b.setTitle(R.string.about)
							.setMessage(R.string.updateInfo)
							.setPositiveButton(R.string.fov,
									new OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											Uri uri = Uri
													.parse("market://search?q=pname:"
															+ getPackageName());
											Intent it = new Intent(
													Intent.ACTION_VIEW, uri);
											try {
												startActivity(it);
											} catch (ActivityNotFoundException e) {
												// TODO Auto-generated catch
												// block

											}

										}
									});
					b.show();
					break;
				case 2:
					startActivity(new Intent(MenuMore.this, AboutActivity.class));
					break;
				default:
					break;
				}
			}

		});
	}
}