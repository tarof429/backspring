package com.taro.backspring;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class MainTest extends AbstractTest {

	@Resource
	private BackupManager backupManager;

	@Test
	public void testMain() throws Exception {

		new Thread(new Runnable() {
			public void run() {
				try {
					Main main = new Main();
					main.execute();
				} catch (Exception e) {

				}
			}
		}).start();

		try {
			Thread.sleep(3000);

		} finally {
			backupManager.stopScheduledBackup();
			Thread.currentThread().interrupt();
		}

	}

}
