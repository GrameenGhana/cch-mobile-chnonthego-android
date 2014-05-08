package org.grameenfoundation.cch.model;



import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.DateFormat;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    Context mContext;
    
    private ArrayList<String> nameOfEvent = new ArrayList<String>();
    private ArrayList<Long> startDates = new ArrayList<Long>();
    private ArrayList<Long> endDates = new ArrayList<Long>();
    private ArrayList<String> descriptions = new ArrayList<String>();

    private ArrayList<String> todaysEvents = new ArrayList<String>();
    private ArrayList<String> tomorrowsEvents = new ArrayList<String>();
    private ArrayList<String> futureEvents = new ArrayList<String>();

    

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
        readCalendarEvent(c);
    }
    
    /** Show a toast from the web page */
    @JavascriptInterface
    public String getNumEventsToday() {	
    	return String.valueOf(todaysEvents.size()) ;
    }
    
    @JavascriptInterface
    public ArrayList<String> getTodaysEvents() {
    	return todaysEvents;
   	}
    
    @JavascriptInterface
    public ArrayList<String> getTomorrowsEvents() {
    	return tomorrowsEvents;
   	}
    
    @JavascriptInterface
    public ArrayList<String> getFutureEvents() {
    	return futureEvents;
   	}
    
    
    
    
 
    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
    
    @JavascriptInterface
    public String getQuote() {
    	return "<p>Horray!! I am rocking this world!!!</p><footer><cite title=\"dkh\">gf</cite></footer>";
    }
    
    @JavascriptInterface
    public String getTodayInHistory() {
    	return "<h2>Today In History</h2><br><p><b>2011-02-03:</b> Horray!! I am rocking this world!!!</p>";
    }

    
    private void readCalendarEvent(Context context) 
    {
           Cursor cursor = context.getContentResolver()
                   .query(
                           Uri.parse("content://com.android.calendar/events"),
                           new String[] { "calendar_id", "title", "description",
                                   "dtstart", "dtend", "eventLocation" }, null,
                           null, null);
           cursor.moveToFirst();
           
           // fetching calendars name
           String CNames[] = new String[cursor.getCount()];

           // fetching calendars id
           nameOfEvent.clear();
           startDates.clear();
           endDates.clear();
           descriptions.clear();
           
           Calendar c = Calendar.getInstance();
    
           for (int i = 0; i < CNames.length; i++) {
        	   long start = Long.parseLong(cursor.getString(3));
        	   long end = c.getTimeInMillis(); ;
        	   
        	   try {
        		   end = Long.parseLong(cursor.getString(4));
        	   } catch(NumberFormatException e) {
        	   }
        	   
        	   String payload = cursor.getString(1)+ "||" +
			            cursor.getString(2);
        	   
        	   if (this.isToday(start)) {
        		   todaysEvents.add(payload);
        	   } else if (this.isTomorrow(start)) {
        		   tomorrowsEvents.add(payload);
        	   } else if (this.isFuture(start)) {
        		   futureEvents.add(payload);
        	   }
        	   
        	   nameOfEvent.add(cursor.getString(1));
               startDates.add(start);
               endDates.add(end);
               descriptions.add(cursor.getString(2));
               CNames[i] = cursor.getString(1);
               cursor.moveToNext();
           }         
    }
    
    private boolean isToday(long milliSeconds)
    {
    	String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
       				.toString().equals(today)) ? true : false;
    }

    private boolean isTomorrow(long milliSeconds)
    {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE, 1);
    	String tomorrow = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
       				.toString().equals(tomorrow)) ? true : false;
    }
    
    private boolean isFuture(long milliSeconds)
    {
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE, 2);
        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
    }
    
    private String getDate(long milliSeconds) {
           SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
           Calendar calendar = Calendar.getInstance();
           calendar.setTimeInMillis(milliSeconds);
           return formatter.format(calendar.getTime());
    }
}

