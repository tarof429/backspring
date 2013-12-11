package com.taro.backspring;

import java.text.ParseException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("backupManager")
public class DefaultBackupManager implements BackupManager {
	private Logger logger = LoggerFactory.getLogger(DefaultBackupManager.class);

	@Resource
	private ConfigurationDAO configDAO;

	@Resource
	private BackupStrategyFactory backupStrategyFactory;

	@Resource
	private BackupScheduler backupScheduler;

	@Override
	public void backup() throws BackupException, ConfigurationDAOException {
		Configuration config = configDAO.load();
		BackupStrategy backupStrategy = backupStrategyFactory
				.getBackupStrategy(config);
		backupStrategy.executeNow();
	}

	@Override
	public void runScheduledBackup() throws BackupException, ParseException,
			ConfigurationDAOException {
		logger.info("Starting scheduled backup");
		Configuration config = configDAO.load();

		if (config.isRunNow()) {
			BackupStrategy backupStrategy = backupStrategyFactory
					.getBackupStrategy(config);
			backupStrategy.executeNow();
		} else {
			backupScheduler.executeScheduledBackup(config);
		}
		logger.info("Finished running backup");

	}

	@Override
	public void restore() {
	}

	@Override
	public void stopScheduledBackup() {
		logger.info("Stopping scheduled backup");
		backupScheduler.stop();
	}
}
