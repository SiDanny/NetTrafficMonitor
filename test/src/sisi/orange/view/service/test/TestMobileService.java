package sisi.orange.view.service.test;

import sisi.orange.view.calculate.MobileService;
import android.content.Intent;
import android.os.IBinder;
import android.test.ServiceTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

public class TestMobileService extends ServiceTestCase<MobileService>{

	private String tag = "TestMobileService";

	public TestMobileService() {
		super(MobileService.class);
		// TODO Auto-generated constructor stub
	}


    @Override
    protected void setUp() throws Exception {
    	Log.e(tag  , "setUp()");
        super.setUp();
    }

    @SmallTest
    public void testPreconditions() {
    	Log.e(tag , "testPreconditions()");
    }
    
    /**
     * Test basic startup/shutdown of Service
     
    @SmallTest
    public void testStartable() {
    	Log.e(tag , "testStartable()");
        Intent startIntent = new Intent();
        startIntent.setClass(getContext(), MobileService.class);
        startService(startIntent); 
    }*/
    
    /**
     * Test binding to service
    
    @MediumTest
    public void testBindable() {
    	Log.e(tag , "testBindable()");
        Intent startIntent = new Intent();
        startIntent.setClass(getContext(), MobileService.class);
        IBinder service = bindService(startIntent); 
    } */

}
