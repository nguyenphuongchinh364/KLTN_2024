package vn.shopttcn.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DateUtil {
	public DateUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String formatDate(Timestamp date) {
		String[] array = date.toString().split("\\s")[0].split("-");
		StringBuilder sb = new StringBuilder();
		sb.append(array[2]).append("/").append(array[1]).append("/").append(array[0]);
		return sb.toString();
	}

	public static String convertDate(String date) {
		String[] arrDate = date.split("\\/");
		StringBuilder sb = new StringBuilder();
		sb.append(arrDate[2]).append("-").append(arrDate[1]).append("-").append(arrDate[0]);
		return sb.toString();
	}

	public static String getDateCurrent() {
		String date = new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
		return convertDate(date);
	}

	public static int getDay(String date) {
		return Integer.parseInt(date.split("\\/")[0]);
	}

	public static int getMonth(String date) {
		return Integer.parseInt(date.split("\\/")[1]);
	}

	public static int getYear(String date) {
		return Integer.parseInt(date.split("\\/")[2]);
	}

	public static List<String> getDayInWeek() {
		List<String> list = new ArrayList<String>();
		String date = new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
		int thu = findThuByDate(getDay(date), getMonth(date), getYear(date));
		switch (thu) {
		case 2:
			list.add(date);
			list = DateUtil.getListDayAfter(list, date, 6);
			break;
		case 3:
			list = DateUtil.getListDayBefore(list, date, 1);
			list.add(date);
			list = DateUtil.getListDayAfter(list, date, 5);
			break;
		case 4:
			list = DateUtil.getListDayBefore(list, date, 2);
			Collections.reverse(list);
			list.add(date);
			list = DateUtil.getListDayAfter(list, date, 4);
			break;
		case 5:
			list = DateUtil.getListDayBefore(list, date, 3);
			Collections.reverse(list);
			list.add(date);
			list = DateUtil.getListDayAfter(list, date, 3);
			break;
		case 6:
			list = DateUtil.getListDayBefore(list, date, 4);
			Collections.reverse(list);
			list.add(date);
			list = DateUtil.getListDayAfter(list, date, 2);
			break;
		case 7:
			list = DateUtil.getListDayBefore(list, date, 5);
			Collections.reverse(list);
			list.add(date);
			list = DateUtil.getListDayAfter(list, date, 1);
			break;
		case 8:
			list = DateUtil.getListDayBefore(list, date, 6);
			Collections.reverse(list);
			list.add(date);
			break;
		}
		return list;
	}

	public static List<String> getListDayBefore(List<String> list, String date, int n) {
		if (n == 0) {
			return list;
		}
		String prevDate = findDateBefore(getDay(date), getMonth(date), getYear(date));
		list.add(prevDate);
		return getListDayBefore(list, prevDate, n - 1);
	}

	public static List<String> getListDayAfter(List<String> list, String date, int n) {
		if (n == 0) {
			return list;
		}
		String nextDate = findDateAfter(getDay(date), getMonth(date), getYear(date));
		list.add(nextDate);
		return getListDayAfter(list, nextDate, n - 1);
	}

	public static String findDateBefore(int day, int month, int year) {
		StringBuilder sb = new StringBuilder();
		day--;
		if (day == 0) {
			month--;
			if (month == 0) {
				month = 12;
				year--;
			}
			day = timSoNgayTrongThang(month, year);
		}
		sb.append(formatDate(day)).append("/").append(formatDate(month)).append("/").append(year);
		return sb.toString();
	}

	public static String findDateAfter(int day, int month, int year) {
		StringBuilder sb = new StringBuilder();
		day++;
		if (day > timSoNgayTrongThang(month, year)) {
			day = 1;
			month++;
			if (month > 12) {
				month = 1;
				year++;
			}
		}
		sb.append(formatDate(day)).append("/").append(formatDate(month)).append("/").append(year);
		return sb.toString();
	}

	public static int timSoNgayTrongThang(int month, int year) {
		int day = 0;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			day = 30;
			break;
		case 2:
			if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
				day = 29;
			} else {
				day = 28;
			}
			break;
		}
		return day;
	}

	public static int findThuByDate(int day, int month, int year) {
		int jmd = (day + ((153 * (month + 12 * ((14 - month) / 12) - 3) + 2) / 5)
				+ (365 * (year + 4800 - ((14 - month) / 12))) + ((year + 4800 - ((14 - month) / 12)) / 4)
				- ((year + 4800 - ((14 - month) / 12)) / 100) + ((year + 4800 - ((14 - month) / 12)) / 400) - 32045)
				% 7;
		switch (jmd) {
		case 0:
			return 2;
		case 1:
			return 3;
		case 2:
			return 4;
		case 3:
			return 5;
		case 4:
			return 6;
		case 5:
			return 7;
		case 6:
			return 8;
		default:
			break;
		}
		return 0;
	}

	public static String formatDate(int n) {
		String kq = Integer.toString(n);
		if (kq.length() == 1) {
			kq = "0" + kq;
		}
		return kq;
	}

	// lấy 3 năm liên tiếp về trc từ năm hiện tại
	public static List<Integer> getListYearPresent() {
		List<Integer> list = new ArrayList<Integer>();
		int year = DateUtil.getYear(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
		list.add(year - 2);
		list.add(year - 1);
		list.add(year);
		return list;
	}

	// lấy ngày cuối cùng của 1 tháng (28,29,30,31)
	public static String getDateEnd(int month, int year) {
		int soNgay = timSoNgayTrongThang(month, year);
		StringBuilder sb = new StringBuilder();
		sb.append(year).append("-").append(formatDate(month)).append("-").append(soNgay);
		return sb.toString();
	}

	public static List<String> getListDateEnd(int year) {
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= 12; i++) {
			list.add(getDateEnd(i, year));
		}
		return list;
	}

	// create date format: yyyy-MM-dd
	public static String setDateByDMY(int day, int month, int year) {
		StringBuilder sb = new StringBuilder();
		sb.append(year).append("-").append(formatDate(month)).append("-").append(formatDate(day));
		return sb.toString();
	}

	// check thời gian đã cách nhau 1 phút chưa => true: tăng views
	public static boolean checkDateTime(String date) {
		String datePresent = getDateTime();
		int dayDate = getDay2(date);
		int monthDate = getMonth2(date);
		int yearDate = getYear2(date);
		int hourDate = getHour(date);
		int minuteDate = getMinute(date);
		int secondDate = getSecond(date);
		int dayDatePresent = getDay2(datePresent);
		int monthDatePresent = getMonth2(datePresent);
		int yearDatePresent = getYear2(datePresent);
		int hourDatePresent = getHour(datePresent);
		int minuteDatePresent = getMinute(datePresent);
		int secondDatePresent = getSecond(datePresent);
		if (yearDatePresent == yearDate) {
			if (monthDatePresent == monthDate) {
				if (dayDatePresent == dayDate) {
					if (hourDatePresent == hourDate) {
						if (minuteDatePresent == minuteDate) {
							return false;
						} else {
							if (minuteDatePresent - minuteDate == 1) {
								if (secondDatePresent < secondDate) {
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	public static String getDateTime() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
	}

	public static int getDay2(String date) {
		return Integer.parseInt(date.split("\\s")[0].split("\\/")[0]);
	}

	public static int getMonth2(String date) {
		return Integer.parseInt(date.split("\\s")[0].split("\\/")[1]);
	}

	public static int getYear2(String date) {
		return Integer.parseInt(date.split("\\s")[0].split("\\/")[2]);
	}

	public static int getHour(String date) {
		return Integer.parseInt(date.split("\\s")[1].split("\\:")[0]);
	}

	public static int getMinute(String date) {
		return Integer.parseInt(date.split("\\s")[1].split("\\:")[1]);
	}

	public static int getSecond(String date) {
		return Integer.parseInt(date.split("\\s")[1].split("\\:")[2]);
	}

}
