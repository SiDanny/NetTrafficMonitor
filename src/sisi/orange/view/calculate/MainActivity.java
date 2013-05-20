package sisi.orange.view.calculate;

import sisi.orange.joy.Detector;
import sisi.orange.joy.Detector.Handle;
import sisi.orange.list.UidListActivity;
import sisi.orange.menu.MenuMore;
import sisi.orange.menu.SettingActivity;
import sisi.orange.view.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity implements OnCheckedChangeListener{
	/** Called when the activity is first created. */
	private TabHost mHost;
	private RadioGroup radioderGroup;
	private Detector gd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mHost.setCurrentTab(0);
    }
    
    private void initView(){
    	setContentView(R.layout.tabs_down);              
    	//实例化TabHost
    	mHost=this.getTabHost();
    	mHost.setVisibility(View.VISIBLE);
    	DisplayMetrics dis = new DisplayMetrics();
 	    getWindowManager().getDefaultDisplay().getMetrics(dis);
 	    int wid = dis.widthPixels;
 	    int hei = dis.heightPixels;
 	    gd = new Detector(wid,hei);
 	    gd.setListener(new Handle() {
 			
 	    	@Override
 	    	public void hand(int result, int reason) {
 				// TODO Auto-generated method stub
 	    		switch(result){
 				case Detector.UP:
 					break;
 				case Detector.DOWN:
 					break;
 				case Detector.LEFT:
 					int left = mHost.getCurrentTab()+1;
 					if(left>=0)
 						mHost.setCurrentTab(left);
 					break;
 				case Detector.RIGHT:
 					int right = mHost.getCurrentTab()-1;
 					if(right<mHost.getChildCount())
 						mHost.setCurrentTab(right);
 					break;
 				case Detector.VERT:
 					finish();
 					Toast.makeText(MainActivity.this, "流量监控已退出", Toast.LENGTH_SHORT).show();
 					break;
 				case Detector.LONGPRESS:
 					break;
 				default:
 					break;
 				}
 			}
 		});
 	    //添加选项卡
 	    mHost.addTab(mHost.newTabSpec("ONE").setIndicator("ONE")
 	    		.setContent(new Intent(this,TrafficActivity.class)));
 	    mHost.addTab(mHost.newTabSpec("TWO").setIndicator("TWO")
 	    		.setContent(new Intent(this,UidListActivity.class)));
 	    mHost.addTab(mHost.newTabSpec("THREE").setIndicator("THREE")
 	    		.setContent(new Intent(this,SettingActivity.class)));
 	    mHost.addTab(mHost.newTabSpec("FOUR").setIndicator("FOUR")
 	    		.setContent(new Intent(this,MenuMore.class)));
         
 	    radioderGroup = (RadioGroup) findViewById(R.id.main_radio);
 	    radioderGroup.setOnCheckedChangeListener(this);
    }

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		gd.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.v("mainActivity","destory");
		//Data.update(this,-11,Data.getFlagData(this, -11)-1);
		gd.recycle();
		mHost.removeAllViews();
		radioderGroup.removeAllViews();
		super.onDestroy();
	}



	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.radio_button0:
			//mHost.setCurrentTab(0);
			mHost.setCurrentTabByTag("ONE");
			break;
		case R.id.radio_button1:
			mHost.setCurrentTabByTag("TWO");
			break;
		case R.id.radio_button2:
			mHost.setCurrentTabByTag("THREE");
			break;
		case R.id.radio_button3:
			mHost.setCurrentTabByTag("FOUR");
			break;
		default:
			break;
		}		
	}
}