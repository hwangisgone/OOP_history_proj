/*
	Date class is used to represent a date with 3 attributes
		1. day of month, unknown day is represented by Integer.MIN_VALUE
		2. month, unknown month is represented by Integer.MIN_VALUE
		3. year, unknown year is represented by Integer.MIN_VALUE
 */

package crawldata.crawler.crawlm.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Date implements Comparable<Date> {

	private int day;		// day of month
	private int month;		// month
	private int year;		// year
	private static final int UNKNOWN = Integer.MIN_VALUE;


	// constructor: extract a string date into the date
	public Date(String dateString) {
		this.day = UNKNOWN;
		this.month = UNKNOWN;
		this.year = UNKNOWN;
		if (dateString != null) {
			extractDateFromString(dateString);
		}
	}	// close Date


	/* extract string date into day/month/year attributes */
	private void extractDateFromString(String dateString) {
		String year = "";
		String month = "";
		String day = "";
		Pattern pattern;
		Matcher matcher;
		// first case: date contain block: '(2002-03-17)'
		pattern = Pattern.compile("\\D*\\((?<year>\\d*)-(?<month>\\d*)-(?<day>\\d*)\\)\\D*");
		matcher = pattern.matcher(dateString);
		if (matcher.find()) {
			year = matcher.group("year");
			month = matcher.group("month");
			day = matcher.group("day");
			this.year = Integer.parseInt(year.trim());
			this.month = Integer.parseInt(month.trim());
			this.day = Integer.parseInt(day.trim());
			return;
		}	// close case 1
		// second case: date is in format: 17 tháng 3, 2002 or 15 tháng 10 năm 2012
		pattern = Pattern.compile("\\D*(?<date>(?<day>\\d*) tháng (?<month>\\d*)\\D*(?<year>\\d*)).*");
		matcher = pattern.matcher(dateString);
		if (matcher.find()) {
			year = matcher.group("year");
			month = matcher.group("month");
			day = matcher.group("day");
			this.year = Integer.parseInt(year.trim());
			this.month = Integer.parseInt(month.trim());
			this.day = Integer.parseInt(day.trim());
			return;
		}	// close
	}	// close extractDateFromString


	/* only compare the year (as an integer)
		- if year is not available, the comparison is not valid
	 */
	@Override
	public int compareTo(Date other) {
		if (this.year > other.year)
			return 1;
		if (this.year < other.year)
			return -1;
		return 0;
	}	// close


	@Override
	public String toString() {
		if (this.year == UNKNOWN)
			return "unknown";
		return (year + "/" + month + "/" + day);
	}	// close toString

}	// close Date