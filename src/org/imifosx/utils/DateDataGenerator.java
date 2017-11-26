package org.imifosx.utils;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DateDataGenerator implements Constants {
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private Map<String, List<String>> dataMap = new HashMap<String, List<String>>();
	private String loanDateString;
	private String loanMaturityDateString;

	public String getLoanMaturityDate() {
		return DOUBLE_QUOTES + loanMaturityDateString + DOUBLE_QUOTES;
	}

	public String getLoanDate() {
		return DOUBLE_QUOTES + loanDateString + DOUBLE_QUOTES;
	}

	/**
	 * 
	 * @param interval
	 *            - The interval of collection (in days)
	 * @param duration
	 *            - Duration of the loan or total number of collections
	 * @param amount
	 *            - The loan amount
	 * @param joiningDate
	 *            - The date user profile was created in the system
	 * @return
	 */
	public Map<String, List<String>> generateData(int interval, int duration,
			int amount, String joiningDate, String loanDateStr) {
		/*
		 * These are the needed variables that needs to be initialized for
		 * proper values generation
		 */
		ArgumentsContainer container = null;
		Date loanDate = null;

		if (null != loanDateStr && !loanDateStr.isEmpty()) {
			container = new ArgumentsContainer(loanDateStr);
			loanDate = container.generateDateFromGivenValues();
		} else {
			container = new ArgumentsContainer(joiningDate);
			Date joinedDate = container.generateDateFromGivenValues();
			// System.out.println("First:" + joinedDate.toString());
			loanDate = generateRandomDateAfter(joinedDate);
		}
		// System.out.println("Second:" + loanDate.toString());

		populateLoanDateAndCollectionDates(loanDate, interval, duration);

		populateDataForAmount(amount, duration);
		return dataMap;
	}

	private Date generateRandomDateAfter(Date joiningDate) {
		Date curDate = Calendar.getInstance().getTime();
		Date randomDate = new Date(ThreadLocalRandom.current().nextLong(
				joiningDate.getTime(), curDate.getTime()));
		return randomDate;
	}

	/**
	 * First print the same date for 3 times Then increment date by the interval
	 * and print for the duration number of times
	 * 
	 * @param startingDate
	 * @param interval
	 * @param duration
	 */
	private void populateLoanDateAndCollectionDates(final Date startingDate,
			final int interval, final int duration) {
		int iCounter = 0;

		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(startingDate);
		if (localCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			localCalendar.add(Calendar.DATE, 1);
		}

		// System.out.println("Loan Date:");
		Date localDate = localCalendar.getTime();
		loanDateString = convertDateToFormattedString(localDate, null);
		// addDataToMap(startingDate, null);

		// System.out.println("\n\nInstallments:");
		// Print start date 3 times
		addDateToDataMap(startingDate);
		addDateToDataMap(startingDate);
		addDateToDataMap(startingDate);

		// localCalendar.add(Calendar.DATE, 1);

		while (iCounter < duration) {
			localCalendar.add(Calendar.DATE, 1);
			if (localCalendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				localDate = localCalendar.getTime();
				addDateToDataMap(localDate);
				iCounter++;
			}
		}
		localDate = localCalendar.getTime();
		loanMaturityDateString = convertDateToFormattedString(localDate, null);
		System.out.println("\"\"\"" + loanMaturityDateString + "\"\"\"");
	}

	private Time generateRandomTime() {
		final Random random = new Random();
		final int millisInDay = 24 * 60 * 60 * 1000;
		Time time = new Time((long) random.nextInt(millisInDay));
		// System.out.println("Time:" + time);
		return time;
	}

	private String convertDateToFormattedString(Date date, Time time) {
		String dateString = DATE_FORMAT.format(date);
		if (time != null) {
			dateString += " " + time;
		}
		return dateString;
	}

	private void addDateToDataMap(final Date date) {

		String dateStr;
		// Without time
		dateStr = convertDateToFormattedString(date, null);
		addDataToMap(TRANSACTION_DATE, DOUBLE_QUOTES + dateStr + DOUBLE_QUOTES);

		// With time
		dateStr = convertDateToFormattedString(date, generateRandomTime());
		addDataToMap(CREATED_DATE, DOUBLE_QUOTES + dateStr + DOUBLE_QUOTES);

	}

	private void addDataToMap(final String key, String value) {
		List<String> dataList = dataMap.get(key);
		if (dataList == null) {
			dataList = new LinkedList<String>();
		}
		dataList.add(value);
		// Remove the object and re-add
		dataMap.remove(key);
		dataMap.put(key, dataList);

	}

	/**
	 * Print the amount first then zero two times Then divide the amount over
	 * the duration and display the series amount
	 * 
	 * @param amount
	 * @param duration
	 */
	private void populateDataForAmount(final int amount, final int duration) {
		// Appending empty string just to auto-convert the int to String
		addDataToMap(AMOUNT, amount + EMPTY_STRING);
		addDataToMap(OUTSTANDING, amount + EMPTY_STRING);

		addDataToMap(AMOUNT, 0 + EMPTY_STRING);
		addDataToMap(AMOUNT, 0 + EMPTY_STRING);
		addDataToMap(OUTSTANDING, EMPTY_STRING);
		addDataToMap(OUTSTANDING, EMPTY_STRING);

		int amountCollected = 0;
		int installment = amount / duration;
		while (amountCollected < amount) {
			amountCollected += installment;
			addDataToMap(AMOUNT, installment + EMPTY_STRING);
			addDataToMap(OUTSTANDING, (amount - amountCollected) + EMPTY_STRING);
		}
	}

	public static void main(String[] args) {
		String[] dataArray = new String[] { "2017-08-01", "2017-08-02",
				"2017-08-05", "2017-08-07", "2017-08-10", "2017-08-12",
				"2017-08-12", "2017-08-16", "2017-08-17", "2017-08-29",
				"2017-09-07", "2017-09-08", "2017-09-13", "2017-09-13",
				"2017-09-14", "2017-09-14", "2017-09-22", "2017-09-22",
				"2017-09-26", "2017-09-27", "2017-09-28", "2017-10-05",
				"2017-10-12", "2017-10-18", "2017-10-28" };
		DateDataGenerator generator = new DateDataGenerator();
		for (String data : dataArray) {
			generator.generateData(1, 10, 100, null, data);
		}

	}

}
