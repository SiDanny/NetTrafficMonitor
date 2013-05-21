package sisi.orange.menu;

//import sisi.orange.data.Data;
import sisi.orange.data.Data;
import sisi.orange.data.ServiceData;
import sisi.orange.view.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity implements OnSeekBarChangeListener{
	private TextView tview;
	private ServiceData dataMobile = new ServiceData();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		Data.getSettingData(this, dataMobile, "mobile");
		setTitle(R.string.app_name);
		
		int num=dataMobile.getNotifyUnite();
		if(num<1){
			num=512000;
			dataMobile.setNotifyUnite(num);
		}
		tview = (TextView) findViewById(R.id.notif_num);
		tview.setText(formateFileSize(num));		
		if(dataMobile.getSum()<10485760){
			dataMobile.setSum(10485760);		
		}
		((EditText)findViewById(R.id.sumEdit)).setHint(
				String.format("%.0f",dataMobile.getSum()/1024.0/1024));
		((EditText)findViewById(R.id.historyEdit)).setHint(String.format(
				"%.2f",dataMobile.getMonthUseData()/1024.0/1024));

		(findViewById(R.id.setBtn)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updatehistory();
			}
		});
		RadioGroup rg = (RadioGroup)findViewById(R.id.notifishow);
		rg.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int id = -1;
				switch (checkedId) {
				case R.id.radio0:
					id = 0;
					break;
				case R.id.radio1:
					id = 1;
					break;
				case R.id.radio2:
					id = 2;
					break;					
				}
				dataMobile.setNOTIFI_SHOW_FLAG(id);				
			}}
		);
		int NOTIFI_FLAG = dataMobile.getNOTIFI_SHOW_FLAG();
		if(NOTIFI_FLAG==-1)
			NOTIFI_FLAG=0;
		RadioButton rb = (RadioButton)findViewById(R.id.radio0+NOTIFI_FLAG);
		rb.setChecked(true);		
		SeekBar bar = (SeekBar) findViewById(R.id.seekBar1);
		bar.setProgress(num/104857-1);
		bar.setOnSeekBarChangeListener(this);						
	}
 
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		int pos = seekBar.getProgress()+1;
		int num = 0;
		if(pos<10)
			num=pos*102400;
		else
			num=pos*104857;
		tview.setText(formateFileSize(num));
		dataMobile.setNotifyUnite(num);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub		
		super.onDestroy();
		tview=null;
		System.gc();
	}

	private void updatehistory() {
		// TODO Auto-generated method stub
		int tempsum = 0;
		int temphis=0;
		try {
			String str = "";
			EditText edt= (EditText)findViewById(R.id.historyEdit);			
			str = edt.getText().toString();
			if(str.length()!=0)
			{
				float temp = Float.parseFloat(str);
				temp = temp>2045?2045:temp;
				temp = temp<0?0:temp;
				temphis = (int) (temp*1048576);
				edt.setText(null);
				edt.setHint(str);
				dataMobile.setHistory(temphis-dataMobile.getTempTraffic());
				dataMobile.setLine(temphis);
				
			}
			edt= (EditText) findViewById(R.id.sumEdit);
			str = edt.getText().toString();
			if(str.length()!=0)
			{
				tempsum = Integer.parseInt(str);
				tempsum = tempsum>2045?2045:tempsum;
				tempsum = tempsum<10?10:tempsum;
				tempsum*=1048576;
				edt.setText(null);
				edt.setHint(str);
				dataMobile.setSum(tempsum);
			}
			Toast.makeText(this, "已存储设置", Toast.LENGTH_SHORT).show();			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			Toast.makeText(this, "输入数字出错,不可过2040", Toast.LENGTH_SHORT).show();
		}finally{
			EditText edt= (EditText) findViewById(R.id.historyEdit);
			edt.setSelected(false);
		}
		Data.setServiceDataByActivity(this, dataMobile);
		sendBroadcast(new Intent(Data.ACTI_SET_FLAG));
	}

	 //系统函数，字符串转换 long -String (kb)
    private String formateFileSize(long size){
    	size = size<0?0:size;
    	return Formatter.formatFileSize(this, size); 
    }
}