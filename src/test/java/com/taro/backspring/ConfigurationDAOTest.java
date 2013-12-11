package com.taro.backspring;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests for the ConfigurationDAO
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ConfigurationDAOTest extends AbstractTest {
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() {
		super.tearDown();
	}

	@Test
	public void testSaveConfiguration() throws Exception {

		Configuration config = getConfiguration();
		configDAO.save(config);

		Configuration config2 = configDAO.load();

		Assert.assertEquals(config, config2);

		try {
			configDAO.save(null);
			Assert.fail();
		} catch (ConfigurationDAOException e) {
			Assert.assertTrue(true);
		}
		Assert.assertEquals(config2, configDAO.load());

	}

	@Test
	public void testRunNow() {
		Configuration config = getConfiguration();
		config.setRunNow(false);
		Assert.assertEquals(false, config.isRunNow());

		config.setRunNow(true);
		Assert.assertEquals(true, config.isRunNow());

	}

	/**
	 * Save, modify, and read a configuration.
	 * 
	 * @throws ConfigurationDAOException
	 */
	@Test
	public void testModifyConfiguration() throws ConfigurationDAOException {
		Configuration config = getConfiguration();
		configDAO.save(config);

		config = configDAO.load();

		config.setSourceDirectory("/another/directory");
		config.setTargetDirectory("/foo/directory");
		config.setSchedule("0/5 * * * * ?");
		config.setBackupStrategyType(BackupStrategyType.RSYNC);

		List<String> excludes = new ArrayList<String>();
		excludes.add("*.c");
		Filter filter = new Filter();
		filter.setExcludes(excludes);
		config.setFilter(filter);

		configDAO.save(config);

		config = configDAO.load();

		Assert.assertEquals("/another/directory", config.getSourceDirectory());
		Assert.assertEquals("/foo/directory", config.getTargetDirectory());
		Assert.assertEquals("0/5 * * * * ?", config.getSchedule());
		Assert.assertEquals(BackupStrategyType.RSYNC,
				config.getBackupStrategyType());

		filter = config.getFilter();
		List<String> foundFilters = filter.getExcludes();
		Assert.assertEquals(1, foundFilters.size());
		Assert.assertEquals("*.c", foundFilters.get(0));
	}

}
