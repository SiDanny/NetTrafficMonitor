package sisi.orange.view.test;

import sisi.orange.view.NetTrafficApplication;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

public class NetTrafficMonitorTests  extends ApplicationTestCase<NetTrafficApplication> {

	public NetTrafficMonitorTests() {
		super(NetTrafficApplication.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * The name 'test preconditions' is a convention to signal that if this test
	 * doesn't pass, the test case was not set up properly and it might explain
	 * any and all failures in other tests. This is not guaranteed to run before
	 * other tests, as junit uses reflection to find the tests.
	 */
	@SmallTest
	public void testPreconditions() {
	}

	/**
	 * Test basic startup/shutdown of Application
	 */
	@MediumTest
	public void testSimpleCreate() {
		createApplication();
	}

}
