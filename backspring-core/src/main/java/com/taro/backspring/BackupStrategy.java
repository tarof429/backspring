package com.taro.backspring;

public interface BackupStrategy {
	void init(Configuration config);

	void executeNow() throws BackupException, ConfigurationDAOException;
}
