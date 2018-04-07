package pl.tomasino.jeeapp;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class MyTestWatcher extends TestWatcher {

	@Override
	protected void succeeded(Description description) {
		System.out.println("============================================================================================");
		System.out.println("Test " + description.getDisplayName() + " passed");
		System.out.println("============================================================================================");
	}

	@Override
	protected void failed(Throwable e, Description description) {
		System.out.println("============================================================================================");
		System.out.println("Test " + description.getDisplayName() + " failed");
		System.out.println("============================================================================================");
	}
}
