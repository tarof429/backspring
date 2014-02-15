package com.taro.backspring;

import java.text.ParseException;

public interface BackupManager {
	void runScheduledBackup() throws BackupException, ParseException,
			ConfigurationDAOException;

	void stopScheduledBackup();

	void backup() throws BackupException, ConfigurationDAOException;

	void restore();
}
