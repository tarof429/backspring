package com.taro.backspring;

import java.text.ParseException;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BackupSchedulerTest extends AbstractTest {
	@Resource
	private BackupScheduler backupScheduler;

	@Test
	public void testInvalidSchedule() {
		String[] schedules = { "zzzzz", "* 0 U ] c" };

		for (String schedule : schedules) {
			try {

				config.setSchedule(schedule);

				backupScheduler.executeScheduledBackup(config);
			} catch (ParseException e) {
				Assert.assertTrue(true);
			} catch (BackupException e) {
				Assert.assertTrue(false);
			}
		}
	}

	@Test
	public void testValidSchedule() {
		try {
			String[] schedules = { "* * * * *", "* ? * ? 0/5", "0 1 * * *" };

			for (String schedule : schedules) {
				config.setSchedule(schedule);

				backupScheduler.executeScheduledBackup(config);
			}
		} catch (ParseException e) {
			Assert.assertTrue(true);
		} catch (BackupException e) {
			Assert.assertTrue(false);
		}
	}

}
