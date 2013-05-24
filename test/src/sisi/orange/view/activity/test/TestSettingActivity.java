package sisi.orange.view.activity.test;

import sisi.orange.menu.SettingActivity;
import sisi.orange.view.R;
import sisi.orange.view.calculate.MainActivity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;

public class TestSettingActivity extends ActivityUnitTestCase<SettingActivity> {

	private Intent mStartIntent;
	private RadioButton mButton;
	private String tag = "ForwardingTest";

	public TestSettingActivity() {
		super(SettingActivity.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Log.e(tag, "setUp()");
		// In setUp, you can create any shared test data, or set up mock
		// components to inject
		// into your Activity. But do not call startActivity() until the actual
		// test methods.
		mStartIntent = new Intent(Intent.ACTION_MAIN);
	}

	/**
	 * The name 'test preconditions' is a convention to signal that if this test
	 * doesn't pass, the test case was not set up properly and it might explain
	 * any and all failures in other tests. This is not guaranteed to run before
	 * other tests, as junit uses reflection to find the tests.
	
	@MediumTest
	public void testPreconditions() {
		startActivity(mStartIntent, null, null);
		mButton = (RadioButton) getActivity().findViewById(R.id.radio_button0);

		assertNotNull(getActivity());
		assertNotNull(mButton);
		Log.e(tag, "testPreconditions");
	} */

	/**
	 * This test demonstrates examining the way that activity calls
	 * startActivity() to launch other activities.
	
	@MediumTest
	public void testSubLaunch() {
		SettingActivity activity = startActivity(mStartIntent, null, null);
		mButton = (RadioButton) activity.findViewById(R.id.radio_button0);

		// This test confirms that when you click the button, the activity
		// attempts to open
		// another activity (by calling startActivity) and close itself (by
		// calling finish()).
		mButton.performClick();
		Log.e(tag, "testSubLaunch()");
		assertNotNull(getStartedActivityIntent());
		assertTrue(isFinishCalled());
	} */

	/**
	 * This test demonstrates ways to exercise the Activity's life cycle.
	 
	@MediumTest
	public void testLifeCycleCreate() {
		SettingActivity activity = startActivity(mStartIntent, null, null);

		// At this point, onCreate() has been called, but nothing else
		// Complete the startup of the activity
		getInstrumentation().callActivityOnStart(activity);
		//getInstrumentation().callActivityOnResume(activity);

		// At this point you could test for various configuration aspects, or
		// you could
		// use a Mock Context to confirm that your activity has made certain
		// calls to the system
		// and set itself up properly.

		//getInstrumentation().callActivityOnPause(activity);

		// At this point you could confirm that the activity has paused
		// properly, as if it is
		// no longer the topmost activity on screen.

		//getInstrumentation().callActivityOnStop(activity);

		// At this point, you could confirm that the activity has shut itself
		// down appropriately,
		// or you could use a Mock Context to confirm that your activity has
		// released any system
		// resources it should no longer be holding.

		// ActivityUnitTestCase.tearDown(), which is always automatically
		// called, will take care
		// of calling onDestroy().
		Log.e(tag, "testLifeCycleCreate()");
	}*/

}
