package com.taro.backspring;

import java.io.File;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("tarBackupStrategy")
public class TarBackupStrategy extends AbstractBackupStrategy {
	private Logger logger = LoggerFactory.getLogger(TarBackupStrategy.class);

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	private String command;

	@Override
	public void init(Configuration config) {
	}

	@Override
	public void executeNow() throws BackupException, ConfigurationDAOException {
		// Injection not working as expected, so need to get the application
		// context manually
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				SETTINGS_FILE);

		ConfigurationDAO configDAO = ctx.getBean("configDAO",
				DefaultConfigurationDAO.class);

		Configuration config2 = configDAO.load();
		this.sourceDirectory = config2.getSourceDirectory();
		this.targetDirectory = config2.getTargetDirectory();

		try {
			CommandLine commandLine = CommandLine.parse(command);
			commandLine.addArgument("cf");
			commandLine.addArgument(targetDirectory + "/backup.tar");

			File f = new File(this.sourceDirectory);
			commandLine.addArgument(f.getName());

			commandLine.addArgument("--ignore-failed-read");
			commandLine.addArgument("--ignore-command-error");

			logger.debug("Executing: " + commandLine);

			DefaultExecutor executor = new DefaultExecutor();
			executor.setWorkingDirectory(new File(sourceDirectory + "/.."));
			executor.setExitValue(0);
			ExecuteWatchdog watchdog = new ExecuteWatchdog(60000 * 10);
			executor.setWatchdog(watchdog);
			executor.execute(commandLine);
			logger.debug("TarBackupStrategy finished.");
		} catch (Exception e) {
			throw new BackupException(e);
		}
	}
}
