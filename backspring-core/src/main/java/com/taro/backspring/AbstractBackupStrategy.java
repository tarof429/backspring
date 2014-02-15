package com.taro.backspring;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class AbstractBackupStrategy implements BackupStrategy, Job {
	protected String sourceDirectory;

	protected String targetDirectory;

	protected static final String SETTINGS_FILE = "com/taro/backspring/settings.xml";

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			executeNow();
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}
	}
}
