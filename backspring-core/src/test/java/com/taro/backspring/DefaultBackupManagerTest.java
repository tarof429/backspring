package com.taro.backspring;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultBackupManagerTest extends AbstractTest {
	@Resource
	private BackupManager backupManager;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() {
		super.tearDown();
	}

	@Test
	public void testBackup() throws ConfigurationDAOException {
		try {
			backupManager.backup();
		} catch (BackupException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testRunScheduledBackup() {

		new Thread(new Runnable() {
			public void run() {
				try {
					backupManager.runScheduledBackup();

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// backupManager.stopScheduledBackup();
				}
			}
		}).start();

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		} finally {
			backupManager.stopScheduledBackup();
		}

	}
}
