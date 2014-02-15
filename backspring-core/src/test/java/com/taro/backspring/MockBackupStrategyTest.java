package com.taro.backspring;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class MockBackupStrategyTest extends AbstractTest {
	@Resource
	private MockBackupStrategy mockBackupStrategy;

	@Resource
	private ConfigurationDAO configDAO;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() {
		super.tearDown();
	}

	@Test
	public void testValid() throws ConfigurationDAOException {
		Configuration config = configDAO.load();
		config.setSourceDirectory("/home/taro");
		config.setTargetDirectory("/goo");

		Filter filter = new Filter();

		List<String> excludes = new ArrayList<String>();
		excludes.add(".gvfs");
		filter.setExcludes(excludes);
		config.setFilter(filter);

		configDAO.save(config);

		try {
			mockBackupStrategy.executeNow();
		} catch (BackupException be) {
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
