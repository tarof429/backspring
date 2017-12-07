package com.taro.backspring;

import java.util.Date;
import org.junit.Test;
import java.text.ParseException;
import org.junit.Assert;

public class StrategyFinishedLineConverterTest {
	
	@Test
	public void testParsePositive() throws ParseException {
		String dateString = "INFO   | jvm 1    | 2017/07/08 14:21:45 | 2017-07-08 14:21:45,407 [WrapperSimpleAppMain] INFO  com.taro.backspring.RsyncBackupStrategy - RsyncBackupStrategy finished.";

		Date date = StrategyFinishedLineConverter.convert(dateString);

		System.out.println(date);
		Assert.assertNotNull(date);
	}

	@Test
	public void testParseInvalidString() throws ParseException {
		String dateString = "INFO   | jvm 1    | 2017/07/08 14:21:45 | 2017-07-08 14:21:45,407 [WrapperSimpleAppMain] INFO  com.taro.backspring.DefaultBackupManager - Finished running backup";

		Date date = StrategyFinishedLineConverter.convert(dateString);

		Assert.assertNull(date);
	}

	@Test
	public void testParseInvalidString2() throws ParseException {
		String dateString = "INFO   | jvm 1    | 2017/07/23 21:51:09 | 2017-07-23 21:51:09,509 [WrapperSimpleAppMain] INFO  com.taro.backspring.DefaultConfigurationDAO - Loading config file from: ./conf/config.yml";
		Date date = StrategyFinishedLineConverter.convert(dateString);

		Assert.assertNull(date);
	}

	@Test(expected = java.text.ParseException.class)
	public void testParseInvalidString3() throws ParseException {
		String dateString = "INFO   | jvm 1    | a017)12/05 41:35:12 | 2017-12-05 21:35:12,776 [DefaultQuartzScheduler_Worker-9] INFO  com.taro.backspring.RsyncBackupStrategy - RsyncBackupStrategy finished.";
		Date date = StrategyFinishedLineConverter.convert(dateString);

		Assert.assertNull(date);
	}
}