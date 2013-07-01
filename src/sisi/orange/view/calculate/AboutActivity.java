package sisi.orange.view.calculate;

import sisi.orange.view.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


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
