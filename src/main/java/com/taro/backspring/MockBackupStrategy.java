package com.taro.backspring;

import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component("mockBackupStrategy")
public class MockBackupStrategy extends AbstractBackupStrategy {

	@Override
	public void init(Configuration config) {
	}

	@Override
	public void executeNow() throws BackupException {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					SETTINGS_FILE);

			ConfigurationDAO configDAO = ctx.getBean("configDAO",
					DefaultConfigurationDAO.class);

			Configuration config = configDAO.load();

			sourceDirectory = config.getSourceDirectory();
			targetDirectory = config.getTargetDirectory();

			CommandLine commandLine = CommandLine.parse("ls");
			commandLine.addArgument("-l");

			Filter filter = config.getFilter();
			if (filter != null) {
				List<String> includes = filter.getIncludes();
				List<String> excludes = filter.getExcludes();

				if (excludes != null) {
					for (String exclude : excludes) {
						commandLine.addArgument("--ignore");
						commandLine.addArgument(exclude);
					}
				}
				if (includes != null) {
					for (String include : includes) {
						commandLine.addArgument(include);
					}
				}
			}

			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(0);
			ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
			executor.setWatchdog(watchdog);
			executor.execute(commandLine);
		} catch (Exception e) {
			throw new BackupException(e);
		}
	}
}
