package com.taro.backspring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;

public class BackupLastRun {

	private static final Logger logger = LoggerFactory
			.getLogger(BackupLastRun.class);

	public static void main(String[] args) {

		logger.info("Checking last time backup was run");

		File logFile = new File(System.getProperty("basedir", "."), 
			"logs/wrapper.log");

		BufferedReader br = null;

		if (logFile.exists()) {
			try {
				br = new BufferedReader(new FileReader(logFile));
				
				String line;
				Date parsedDate = null;

				while ((line = br.readLine()) != null) {
					Date tempParsedDate = StrategyFinishedLineConverter.convert(line);
					if (tempParsedDate != null) {
						parsedDate = (Date)tempParsedDate.clone();
					}
				}

				if (parsedDate != null) {
					System.out.println("Backup last completed: " + parsedDate);
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error while reading log file");
				System.exit(0);
			}
			finally {
				if (br != null) {
					try {
						br.close();
					} catch (Exception ignore) {

					}
				}

			}
		}
	}
}