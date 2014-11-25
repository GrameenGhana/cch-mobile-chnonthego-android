package org.grameenfoundation.cch.utils;

import java.util.Calendar;
import java.util.Date;

import android.text.format.Time;

public class CCHTimeUtil {

	public static Time getLastDayOfWeek(Date today) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);

		if (cal.get(Calendar.DAY_OF_WEEK) > Calendar.FRIDAY) {
			cal.add(Calendar.DATE, 7 - cal.get(Calendar.DAY_OF_WEEK)
					+ Calendar.FRIDAY);
		} else {
			cal.add(Calendar.DATE,
					Calendar.FRIDAY - cal.get(Calendar.DAY_OF_WEEK));
		}
		 cal.set(Calendar.HOUR_OF_DAY, 17);
		 cal.set(Calendar.MINUTE, 00);
		 cal.set(Calendar.SECOND, 00);
		return calendarToTime(cal);

	}

	public static Time getLastFidayOfMonth(Date today) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1);
		cal.add(Calendar.DATE, -1);
		 if (cal.get(Calendar.DAY_OF_WEEK) > Calendar.FRIDAY) {
				cal.add(Calendar.DATE, cal.get(Calendar.DAY_OF_WEEK) - Calendar.FRIDAY);
			} else {
				cal.add(Calendar.DATE,
						-(7 + cal.get(Calendar.DAY_OF_WEEK) - Calendar.FRIDAY));
			}

		 cal.set(Calendar.HOUR_OF_DAY, 17);
		 cal.set(Calendar.MINUTE, 00);
		 cal.set(Calendar.SECOND, 00);
		
		return calendarToTime(cal);

	}
	
	public static Time calendarToTime(Calendar cal)
	{
		
		Time t = new Time();
		t.set(cal.getTimeInMillis());
		return  t;
	}

}
