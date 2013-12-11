package com.taro.backspring;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class TarBackupStrategyTest extends AbstractTest {
	@Resource
	private TarBackupStrategy tarBackupStrategy;

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
	public void testCommand() throws Exception {
		Assert.assertEquals("/bin/test", tarBackupStrategy.getCommand());

	}

	@Test
	public void testInvalid() throws ConfigurationDAOException {
		Configuration config = configDAO.load();
		config.setSourceDirectory("/foo");
		config.setTargetDirectory("/goo");
		configDAO.save(config);

		try {
			tarBackupStrategy.executeNow();
		} catch (BackupException be) {
			Assert.assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	/*
	 * @Test public void testValid() { Configuration config = configDAO.load();
	 * config.setSourceDirectory("/home/taro/foo");
	 * config.setTargetDirectory("/media/backup"); configDAO.save(config);
	 * 
	 * try { tarBackupStrategy.executeNow(); } catch (BackupException be) {
	 * Assert.assertTrue(true); } catch (Exception e) { Assert.fail(); } }
	 */
}
