package com.taro.backspring;

import java.io.File;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("rsyncBackupStrategy")
public class RsyncBackupStrategy extends AbstractBackupStrategy {

	private Logger logger = LoggerFactory.getLogger(RsyncBackupStrategy.class);

	private static final String rsyncCmd = "/usr/bin/rsync";

	@Override
	public void init(Configuration config) {
	}

	@Override
	public void executeNow() throws BackupException, ConfigurationDAOException {
		logger.info("Executing RsyncBackupStrategy");

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				SETTINGS_FILE);

		ConfigurationDAO configDAO = ctx.getBean("configDAO",
				DefaultConfigurationDAO.class);

		Configuration config = configDAO.load();
		this.sourceDirectory = config.getSourceDirectory();
		this.targetDirectory = config.getTargetDirectory();

		try {
			CommandLine commandLine = CommandLine.parse(rsyncCmd);
			commandLine.addArgument("-r");

			File f = new File(this.sourceDirectory);
			commandLine.addArgument(f.getName());

			commandLine.addArgument(targetDirectory + "/rsync-backup");

			Filter filter = config.getFilter();

			if (filter != null) {
				List<String> includes = filter.getIncludes();
				List<String> excludes = filter.getExcludes();

				if (includes != null) {
					for (String include : includes) {
						commandLine.addArgument("--include");
						commandLine.addArgument(include);
					}
				}
				if (excludes != null) {
					for (String exclude : excludes) {
						commandLine.addArgument("--exclude");
						commandLine.addArgument(exclude);
					}
				}

				String logging = config.getLogging();

				if (logging.equals("TRUE")) {
					commandLine.addArgument("--verbose");
				}
			}
			commandLine.addArgument("--links");

			// delete files in targetDirectory if it was deleted in
			// sourcecDirectory
			commandLine.addArgument("--delete");

			logger.debug("Executing: " + commandLine);

			DefaultExecutor executor = new DefaultExecutor();
			executor.setWorkingDirectory(new File(sourceDirectory + "/.."));
			executor.setExitValue(0);
			// ExecuteWatchdog watchdog = new ExecuteWatchdog(60000 * 10);
			// executor.setWatchdog(watchdog);
			executor.execute(commandLine);
			logger.info("RsyncBackupStrategy finished.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BackupException(e);
		}
	}

}
