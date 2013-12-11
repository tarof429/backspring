package com.taro.backspring;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class BackupStrategyFactoryTest extends AbstractTest {
	@Resource
	private BackupStrategyFactory backupStrategyFactory;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() {
		super.tearDown();
	}

	@Test
	public void testGetBackupStrategy() {

		for (BackupStrategyType strategy : BackupStrategyType.values()) {
			config.setBackupStrategyType(strategy);
			BackupStrategy backupStrategy = backupStrategyFactory
					.getBackupStrategy(config);
			Assert.assertEquals(strategy, config.getBackupStrategyType());
		}
	}
}
