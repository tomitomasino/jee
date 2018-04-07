package pl.tomasino.jeeapp.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import pl.tomasino.jeeapp.MyTestWatcher;
import pl.tomasino.jeeapp.model.Company;
import pl.tomasino.jeeapp.service.DBManager;

public class DBManagerTest {

	@Rule
    public TestWatcher watchman = new MyTestWatcher();
	
	@Test
	public void getAllFromDBShouldNotBeEmpty() {
		DBManager dbManager = new DBManager();
		List<Company> compList = dbManager.getAllFromDB();
		Assert.assertFalse(compList.isEmpty());
	}

}
