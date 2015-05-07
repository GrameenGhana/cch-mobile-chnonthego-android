package org.grameenfoundation.cch.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LastFriday {

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

  public static String  getLastFridayofMonth(int offset) { //offset=0 mean current month
    final String DATE_FORMAT_NOW = "dd-MMM-yyyy";
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    cal=getLastFriday(cal,offset);
    return sdf.format(cal.getTime()); 

  }

  public static void main(String[] args) {
    System.out.println(getLastFridayofMonth(0)); //0 = current month
    System.out.println(getLastFridayofMonth(1));//1=next month
    System.out.println(getLastFridayofMonth(2));//2=month after next month
  }

}