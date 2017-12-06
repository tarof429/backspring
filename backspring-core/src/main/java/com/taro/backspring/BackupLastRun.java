package com.taro.backspring;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class BackupLastRun {
	public static void main(String[] args) {

		File logFile = new File(System.getProperty("basedir", "."), 
			"logs/wrapper.log");

		BufferedReader br = null;

		if (logFile.exists()) {
			try {
				br = new BufferedReader(new FileReader(logFile));
				
				String line;
				String dateToken = "";
				String timeToken = "";

				while ((line = br.readLine()) != null) {
					if (line.contains("Strategy finished")) {
						//System.out.println("Interesting line found: " + line);
						
						String [] tokens = line.split("\\|");
						String interestingToken = tokens[2];
						interestingToken = interestingToken.trim();
						String [] detailedTokens = interestingToken.split("\\s+");
						// System.out.println(detailedTokens);
						dateToken = detailedTokens[0].trim();
						timeToken = detailedTokens[1].trim();
					 	//System.out.println("date: " + dateToken + " time: " + timeToken);
					}
				}
				System.out.println("Backup last completed: " + dateToken + " at " + timeToken);

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