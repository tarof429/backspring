package com.taro.backspring;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class RsyncBackupStrategyTest extends AbstractTest {
	@Resource
	private RsyncBackupStrategy rsyncBackupStrategy;

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
	public void testInvalid() throws Exception {
		Configuration config = configDAO.load();
		config.setBackupStrategyType(BackupStrategyType.RSYNC);
		config.setSourceDirectory("/foo");
		config.setTargetDirectory("/goo");
		config.setFilter(null);
		configDAO.save(config);
		config = configDAO.load();

		Assert.assertNull(config.getFilter());

		Filter filter = new Filter();
		List<String> includes = new ArrayList<String>();
		includes.add("*");
		filter.setIncludes(includes);

		config.setFilter(filter);
		configDAO.save(config);
		config = configDAO.load();

		filter = config.getFilter();

		Assert.assertNotNull(filter);

		Assert.assertEquals(0, filter.getExcludes().size());

		Assert.assertNotNull(filter.getIncludes());

		includes = filter.getIncludes();

		Assert.assertEquals(1, filter.getIncludes().size());

		Assert.assertEquals("*", includes.get(0));

		try {
			rsyncBackupStrategy.executeNow();
		} catch (BackupException be) {
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}

	}

	@Test
	public void testValid() throws Exception {

		File dataDir = new File(basedir, "data");

		dataDir.mkdirs();

		File targetDir = new File(basedir, "backup");

		targetDir.mkdirs();

		FileUtils.cleanDirectory(dataDir);

		FileUtils.cleanDirectory(targetDir);

		File dataFile = new File(dataDir, "hello.txt");

		FileUtils.writeStringToFile(dataFile, "hello world");

		Configuration config = configDAO.load();

		config.setSourceDirectory(dataDir.getAbsolutePath());
		config.setTargetDirectory(targetDir.getAbsolutePath());

		configDAO.save(config);

		rsyncBackupStrategy.executeNow();

		File copiedFile = new File(targetDir, "/rsync-backup/data/hello.txt");

		Assert.assertTrue(copiedFile.exists());

		Assert.assertEquals("hello world",
				FileUtils.readFileToString(copiedFile));

		FileUtils.cleanDirectory(dataDir);

		rsyncBackupStrategy.executeNow();

		Assert.assertFalse(copiedFile.exists());

	}

}
