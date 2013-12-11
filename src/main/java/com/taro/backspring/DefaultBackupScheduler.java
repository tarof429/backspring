package com.taro.backspring;

import java.text.ParseException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("backupScheduler")
public class DefaultBackupScheduler implements BackupScheduler {
	private BlockingQueue<RunState> runStateQueue = new LinkedBlockingQueue<RunState>();

	private Logger logger = LoggerFactory
			.getLogger(DefaultBackupScheduler.class);

	@Resource
	private BackupStrategyFactory backupStrategyFactory;

	@Override
	public void executeScheduledBackup(Configuration config)
			throws BackupException, ParseException {

		Scheduler scheduler = null;
		try {
			BackupStrategy backupStrategy = backupStrategyFactory
					.getBackupStrategy(config);

			String schedule = config.getSchedule();

			SchedulerFactory schedulerFactory = new StdSchedulerFactory();

			// Retrieve a scheduler from schedule factory
			scheduler = schedulerFactory.getScheduler();

			// Initiate JobDetail with job name, job group, and executable job
			// class
			JobDetail jobDetail = new JobDetail("jobDetail", "jobDetailGroup",
					backupStrategy.getClass());

			// Initiate CronTrigger with its name and group name CronTrigger
			CronTrigger cronTrigger = new CronTrigger("cronTrigger",
					"triggerGroup2");

			// setup CronExpression CronExpression
			CronExpression cexp = new CronExpression(schedule);

			// Assign the CronExpression to CronTrigger
			cronTrigger.setCronExpression(cexp);

			// schedule a job with JobDetail and Trigger
			scheduler.scheduleJob(jobDetail, cronTrigger);

			// start the scheduler
			logger.debug("Starting scheduled backup");

			scheduler.start();

			RunState runState = runStateQueue.take();
			if (runState == RunState.SHUTDOWN) {
				logger.debug("Stopping scheduled backup");
				scheduler.shutdown(true);

			}
		} catch (ParseException p) {
			throw p;
		} catch (Exception e) {
			throw new BackupException(e);
		} finally {
			try {
				if (!scheduler.isShutdown()) {
					scheduler.shutdown(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void stop() {
		try {
			logger.debug("Stopping schedueld backup");
			runStateQueue.put(RunState.SHUTDOWN);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
