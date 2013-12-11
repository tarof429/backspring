package com.taro.backspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	private BackupManager backupManager;

	private Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		Main main = new Main();
		main.execute();
	}

	public void execute() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				AbstractBackupStrategy.SETTINGS_FILE);

		backupManager = ctx
				.getBean("backupManager", DefaultBackupManager.class);

		try {
			backupManager.runScheduledBackup();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			backupManager.stopScheduledBackup();
			Thread.currentThread().interrupt();
		}
	}
}
