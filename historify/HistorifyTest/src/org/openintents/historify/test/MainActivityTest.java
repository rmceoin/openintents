package org.openintents.historify.test;

import org.openintents.historify.MainActivity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public class MainActivityTest extends ActivityInstrumentationTestCase2{

	public MainActivityTest() {
		super(MainActivity.class);
	}
	
	public void testSettings(){
		Activity activity = getActivity();
		assertTrue(activity != null); 
	}

}
