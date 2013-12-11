package com.taro.backspring;

import java.io.File;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "classpath:com/taro/backspring/settings.xml" })
public abstract class AbstractTest extends TestCase {
	@Resource
	protected ConfigurationDAO configDAO;

	protected Configuration config;

	protected File basedir;

	@Before
	public void setUp() throws Exception {
		if (System.getProperty("basedir") == null) {
			System.setProperty("basedir", "target");
		}
		basedir = new File(System.getProperty("basedir"));

		Configuration config = getConfiguration();
		configDAO.save(config);
	}

	@Before
	public void tearDown() {

	}

	public Configuration getConfiguration() {

		config = new Configuration();

		config.setName("My Configuration");
		config.setSourceDirectory("/home/foo");
		config.setTargetDirectory("/backup/foo");
		// config.setSchedule("0/1 * * * * ?");
		config.setBackupStrategyType(BackupStrategyType.MOCK);
		config.setRunNow(true);

		return config;
	}
}
