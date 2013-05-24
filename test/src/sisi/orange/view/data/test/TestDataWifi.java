package sisi.orange.view.data.test;

import junit.framework.TestResult;
import sisi.orange.data.Data;
import sisi.orange.data.NetTraffic;
import sisi.orange.data.ServiceData;
import android.content.ContentValues;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

public class TestDataWifi extends AndroidTestCase{
	private static final String tag = "Data";
	protected String category = "wifi";
	static Bundle b;
	static ContentValues values = new ContentValues();
	
	
	
	@SmallTest
	public void getlistDateTrafficInfo() {
		double[][] list = Data.getListDateTrafficInfo(getContext());
		assert(list[0].length == 31);
		assert(list[0].length == list[1].length);
	}
	
	@SmallTest
	public void testupdateTempTraffic() {
		long tempTraffic = NetTraffic.getTotalBytes(category);
		Data.updateTempTraffic(getContext(), tempTraffic, category);
		assert(tempTraffic == Data.getFlagData(getContext(), -2, category));
	}

	@SmallTest
	public void testsetNotifyUnit(){
		Data.setNotifyUnit(getContext(), Data.AUTO);
		assert(Data.AUTO == Data.getFlagData(getContext(), -5, category));
	}

	
	@SmallTest
	public void testisPreNetAvaiable(){
		assert(Data.AUTO == Data.getFlagData(getContext(), -5, category));
	}
	
	@SmallTest
	public void testsetNetAvaiable(){
		Data.setNetAvaiable(getContext(), false, category);
		assert(false == Data.isPreNetAvaiable(getContext(), category));
	}

	@SmallTest
	public void testgetFlagData(){
		ServiceData data = Data.getServiceData(getContext(), category);
		assert(data.getSum() == Data.getFlagData(getContext(), 0, category));
		assert(data.getHistory() == Data.getFlagData(getContext(), -1, category));
		assert(data.getTempTraffic() == Data.getFlagData(getContext(), -2, category));
		assert(data.getDateDay() == Data.getFlagData(getContext(), -3, category));
		assert(data.getNotifyData() == Data.getFlagData(getContext(), -4, category));
		assert(data.getNOTIFI_SHOW_FLAG() == Data.getFlagData(getContext(), -5, category));
		assert(data.getNotifyUnite() == Data.getFlagData(getContext(), -6, category));
		assert(data.getLine() == Data.getFlagData(getContext(), -7, category));
		assert((data.isNetAvaiable()?1:0) == Data.getFlagData(getContext(), -8, category));
	}


	@SmallTest
	public void testclearDataOverMonth(){
		Data.clearDataOverMonth(getContext());
	}


	@SmallTest
	public void testzeroapk(){
		Data.zeroapk(getContext());
		assert(Data.getListAppInfo(getContext()).size() ==0);
	}

	@Override
	public TestResult run() {
		// TODO Auto-generated method stub
		return super.run();
	}

	@Override
	protected void runTest() throws Throwable {
		// TODO Auto-generated method stub
		super.runTest();
	}	
	
	

}
