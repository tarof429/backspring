package com.taro.backspring;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class StrategyFinishedLineConverter {
	public static Date convert(String line) throws ParseException {
		if (line.contains("Strategy finished")) {

			String [] tokens = line.split("\\|");
			String interestingToken = tokens[2];
			interestingToken = interestingToken.trim();
			String [] detailedTokens = interestingToken.split("\\s+");

			String dateToken = detailedTokens[0].trim();
			String timeToken = detailedTokens[1].trim();

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date parsedDate = formatter.parse(dateToken + " " + timeToken);
			return parsedDate;
		} else {
			return null;
		}
	}
}