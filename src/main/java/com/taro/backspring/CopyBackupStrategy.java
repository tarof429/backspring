package com.taro.backspring;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component("copyBackupStrategy")
public class CopyBackupStrategy extends AbstractBackupStrategy {

	@Override
	public void init(Configuration config) {

	}

	@Override
	public void executeNow() throws BackupException {
		try {
			CommandLine commandLine = CommandLine.parse("cp");
			commandLine.addArgument("-r");
			commandLine.addArgument(sourceDirectory);
			commandLine.addArgument(targetDirectory);
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(0);
			ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
			executor.setWatchdog(watchdog);
		} catch (Exception e) {
			throw new BackupException(e);
		}
	}

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
