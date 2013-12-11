package com.taro.backspring;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component("backupStrategyFactory")
public class BackupStrategyFactory {
	@Resource
	private ApplicationContext applicationContext;

	public BackupStrategy getBackupStrategy(Configuration config) {
		BackupStrategyType backupStrategyType = config.getBackupStrategyType();

		BackupStrategy backupStrategy = null;

		switch (backupStrategyType) {
		case RSYNC:
			backupStrategy = applicationContext.getBean("rsyncBackupStrategy",
					RsyncBackupStrategy.class);
			break;
		case COPY:
			backupStrategy = applicationContext.getBean("copyBackupStrategy",
					CopyBackupStrategy.class);
			break;
		case TAR:
			backupStrategy = applicationContext.getBean("tarBackupStrategy",
					TarBackupStrategy.class);
			break;
		case MOCK:
			backupStrategy = applicationContext.getBean("mockBackupStrategy",
					MockBackupStrategy.class);
			break;
		default:
			backupStrategy = applicationContext.getBean("mockBackupStrategy",
					MockBackupStrategy.class);

		}
		// backupStrategy.init(config);
		return backupStrategy;

	}
}
