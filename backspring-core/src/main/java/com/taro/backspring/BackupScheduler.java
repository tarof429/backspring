package com.taro.backspring;

import java.text.ParseException;

public interface BackupScheduler {
	void executeScheduledBackup(Configuration config) throws BackupException,
			ParseException;

	void stop();
}
