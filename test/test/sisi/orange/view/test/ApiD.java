package sisi.orange.view.test;

import sisi.orange.view.calculate.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

public class NetTrafficTest extends ActivityInstrumentationTestCase2<MainActivity> {

    /**
     * Create an {@link ActivityInstrumentationTestCase2} that tests the {@link ApiDemos} activity.
     */
    public NetTrafficTest() {
        super(MainActivity.class);
    }

    /**
     * Verifies that activity under test can be launched.
     */
    public void testActivityTestCaseSetUpProperly() {
        assertNotNull("activity should be launched successfully", getActivity());
    }
}
