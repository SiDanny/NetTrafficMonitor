package sisi.orange.view.calculate;

import sisi.orange.view.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
/**
 * wifi下
 * 78行 waps推送广告，miidi的100分限制
 * destory处扣两分
 */

public class AboutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about); 
		TextView tv = (TextView) findViewById(R.id.print);
		CharSequence version=getResources().getString(R.string.version);
		tv.append(version);
	}
}
