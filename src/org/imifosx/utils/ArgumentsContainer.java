package org.imifosx.utils;

import java.util.Calendar;
import java.util.Date;

public class ArgumentsContainer implements Constants {

	private int year;
	private int month;
	private int date;

	public ArgumentsContainer(String dateString) {
		parseDateString(dateString);
	}

	/**
	 * Make sure that the date string is in the format: Eg: "2017-08-01"
	 * This method will populate the global variables that will be used for
	 * further calculation
	 * 
	 * @param dateStr
	 */
	private void parseDateString(String dateStr) {
		if(dateStr.startsWith(DOUBLE_QUOTES)){
			dateStr = dateStr.replaceAll(DOUBLE_QUOTES, EMPTY_STRING);
		}
		String[] dateParts = dateStr.split("-");
		year = Integer.parseInt(dateParts[0]);
		month = Integer.parseInt(dateParts[1]);
		date = Integer.parseInt(dateParts[2]);
	}

	public Date generateDateFromGivenValues() {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, date);
		Date valuesDate = cal.getTime();
		return valuesDate;

	}

}
