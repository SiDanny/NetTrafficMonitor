package sisi.orange.view.calculate;

import sisi.orange.data.Data;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetReceiver  extends BroadcastReceiver{
	private NetStateChangeImpl impl;
	private String category="";
	public NetReceiver(NetStateChangeImpl impl, String category){
		this.impl = impl;
		this.category = category;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		boolean currentState = BootReceiver.checkNet(context, category);
		boolean preState = Data.isPreNetAvaiable(context, category);
		String action = intent.getAction();
		Log.v("NetReceiver",preState+" 2 "+currentState);
		if(action.equals(Data.NET_CHANGE)){
			if(isOpen2Close(preState, currentState)){
				//�ƶ������ɴ򿪵��ر�	
				//�򻯲��������յ�����������ı��֪ͨ���ض��Ǳ仯
				Log.v(category,"netReceiver UNavaiable ");
				impl.ClosingProcess();

			}else if(isClose2Open(preState, currentState)){
				//wap�������Ϊ��
				Log.v(category,"netReceiver avaiable ");
				impl.openingProcess();
			}
			Data.setNetAvaiable(context, currentState, category);	
		}else if(action.equals(Data.ACTI_SET_FLAG)){				
			impl.setDataProcess();
		}
	}
	
	private boolean isOpen2Close(boolean preState, boolean currentState){
		return preState && !currentState;
	};
	
	private boolean isClose2Open(boolean preState, boolean currentState){
		return !preState && currentState;
	};
	
}
