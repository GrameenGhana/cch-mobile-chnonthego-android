package org.grameenfoundation.cch.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

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
		 cal.set(Calendar.MINUTE, 30);
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

		 cal.set(Calendar.HOUR_OF_DAY, 16);
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
	
	public String checkTodayFriday(DateTime today) {
		today = new DateTime();
		String dayOfWeek = null;
		if(today.getDayOfWeek()==DateTimeConstants.FRIDAY){
			 dayOfWeek="Friday";
		}else{
			dayOfWeek=getAsText(today.getDayOfWeek());
		}
		return dayOfWeek;
		

	}
	

	public static Calendar getLastFriday(Calendar cal,int offset){
	    int dayofweek;//1-Sunday,2-Monday so on....
	    cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+offset);
	    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); //set calendar to last day of month
	    dayofweek=cal.get(Calendar.DAY_OF_WEEK); //get the day of the week for last day of month set above,1-sunday,2-monday etc
	    if(dayofweek<Calendar.FRIDAY)  //Calendar.FRIDAY will return integer value =5 
	      cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-7+Calendar.FRIDAY-dayofweek);
	    else
	      cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+Calendar.FRIDAY-dayofweek); 

	    return cal;
	  }

	  public String  getLastFridayofMonth(int offset) { //offset=0 mean current month
	    final String DATE_FORMAT_NOW = "dd-MM-yyyy";
	    String what_date;
	    Date now=new Date();
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    cal=getLastFriday(cal,offset);
	    String today_date=sdf.format(now);
	    String last_friday=sdf.format(cal.getTime());
	   if(today_date.equals(last_friday)){
		   what_date=last_friday;
	   }else{
		   what_date="Today is not the last Friday";
	   }
	    return what_date;

	  }
	  
	  public boolean checkIfMonthIsQuarter(){
		 int  month=DateTime.now().monthOfYear().get();
		  boolean isQuarter;
		  switch(month){
		  case 3:
			  isQuarter=true;
			  break;
		  case 6:
			  isQuarter=true;
			  break;
		  case 9:
			  isQuarter=true;
			  break;
		  case 12:
			  isQuarter=true;
			  break;
			  default:
				  isQuarter=false;
				  break;
		  }
		  
		  return isQuarter;
	  }
	
	  
	  public boolean checkIfMonthIsMidYear(){
			 int  month=DateTime.now().monthOfYear().get();
			  boolean isQuarter;
			  switch(month){
			  case 6:
				  isQuarter=true;
				  break;
			
				  default:
					  isQuarter=false;
					  break;
			  }
			  
			  return isQuarter;
		  }
	  
	  public boolean checkIfMonthIsEndOfYear(){
			 int  month=DateTime.now().monthOfYear().get();
			  boolean isQuarter;
			  switch(month){
			  case 12:
				  isQuarter=true;
				  break;
			
				  default:
					  isQuarter=false;
					  break;
			  }
			  
			  return isQuarter;
		  }
	public String getAsText(int dayOfWeek){
			String[] dayNames = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
			String name = dayNames[dayOfWeek-1];
		return name;
		
	}

}
