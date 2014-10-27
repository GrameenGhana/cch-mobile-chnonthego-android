package org.grameenfoundation.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Instances;
import android.text.format.DateFormat;
import android.text.format.DateUtils;


public class CalendarEvents {
	Context mContext;
    private ArrayList<MyEvent> calEvents = new ArrayList<MyEvent>();

    private int todaysEventsNum = 0;
    private int tomorrowsEventsNum = 0;
    private int futureEventsNum = 0;
    private int thismonthEventsNum = 0;
    private int thismonthEventsDone = 0;
    private String previousLocations = "";
    
	public CalendarEvents(Context c){
		this.mContext=c;
		readCalendarEvent(c);
	}
	
	public void addRecurringEvent(String evt, String location, String desc,String rrule)
    {			
		Calendar cal = Calendar.getInstance();
		//beginTime.set(2012, 0, 19, 7, 30);
		//Calendar endTime = Calendar.getInstance();
		//endTime.set(2012, 0, 19, 8, 30);
		Intent	intent = new Intent(Intent.ACTION_EDIT)
		        .setData(Events.CONTENT_URI)
		        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
		        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,  cal.getTimeInMillis()+60*60*1000)
		        .putExtra(Events.TITLE, evt)
		        .putExtra(Events.DESCRIPTION, desc)
		        .putExtra(Events.EVENT_LOCATION, location)
		        .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
		 		.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY , false);
		Bundle b=new Bundle();
		b.putString(Events.RRULE,rrule);
		intent.putExtras(b);
		
		//System.out.println(rrule);
		mContext.startActivity(intent);
    }
	public void addEvent(String evt, String location, String desc)
    {	
		long calID = 3;
		
		Calendar cal = Calendar.getInstance();
		ContentResolver cr = mContext.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(Events.DTSTART, cal.getTimeInMillis());
		values.put(Events.DTEND,  cal.getTimeInMillis()+60*60*1000);
		values.put(Events.TITLE, evt);
		values.put(Events.DESCRIPTION, desc);
		values.put(Events.CALENDAR_ID, calID);
		values.put(Events.AVAILABILITY,  Events.AVAILABILITY_BUSY);
		values.put(Events.EVENT_LOCATION, location);
		values.put(Events.EVENT_TIMEZONE, "Casablanca");
		values.put(CalendarContract.EXTRA_EVENT_ALL_DAY,  false);
		Uri uri = cr.insert(Events.CONTENT_URI, values);
		System.out.println(TimeZone.getAvailableIDs());
		// get the event ID that is the last element in the Uri
		long eventID = Long.parseLong(uri.getLastPathSegment());
		
		//beginTime.set(2012, 0, 19, 7, 30);
		//Calendar endTime = Calendar.getInstance();
		//endTime.set(2012, 0, 19, 8, 30);
		/*
		Intent	intent = new Intent(Intent.ACTION_EDIT)
		        .setData(Events.CONTENT_URI)
		        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
		        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,  cal.getTimeInMillis()+60*60*1000)
		        .putExtra(Events.TITLE, evt)
		        .putExtra(Events.DESCRIPTION, desc)
		        .putExtra(Events.EVENT_LOCATION, location)
		        .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
		 		.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY , false);
		
		mContext.startActivity(intent);
		*/
    }
    
	public class MyEvent
    {
    	public long eventId;
    	public String eventType;
    	public String location;
    	public String description;
    	public Long startDate;
    	public Long endDate;
    	
    	public MyEvent(){
    		
    	}
    	public boolean isToday()
    	{
    			long milliSeconds = this.startDate;
    	    	String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
    	       				.toString().equals(today)) ? true : false;
    	}
    	
    	public boolean isTomorrow()
    	{
    			long milliSeconds = this.startDate;
    	    	Calendar c = Calendar.getInstance();
    	    	c.add(Calendar.DATE, 1);
    	    	String tomorrow = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
    	       				.toString().equals(tomorrow)) ? true : false;
    	}
    	    
    	public boolean isFuture()
    	{
    			long milliSeconds = this.startDate;
    	    	Calendar c = Calendar.getInstance();
    	    	c.add(Calendar.DATE, 2);
    	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
    	}
    	
    	public boolean isThisMonth() { return isThisMonth(false); }
    	public boolean isThisMonth(boolean completed)
    	{
    		boolean resp = false;
    		
    		long milliSeconds = this.startDate;
	    	Calendar c = Calendar.getInstance();
	    	String today = new SimpleDateFormat("MM/yyyy").format(new Date(c.getTimeInMillis()));
	        
	    	// is it this month?
	    	resp =  (DateFormat.format("MM/yyyy", new Date(milliSeconds))
	       				.toString().equals(today)) ? true : false;
 
	    	if (resp && completed)
	    	{
	    		resp =  (milliSeconds < c.getTimeInMillis()) ? true : false;
	    	}
	    	
    		return resp;
    	}
              
        public String getDate(String format) {
			   long milliSeconds = this.startDate;
               SimpleDateFormat formatter = new SimpleDateFormat(format);
               Calendar calendar = Calendar.getInstance();
               calendar.setTimeInMillis(milliSeconds);
               return formatter.format(calendar.getTime());
        }
        
    }
	
	public String getNumEventsToday() {	
    	return String.valueOf(todaysEventsNum) ;
    }
	
	public ArrayList<String> getTodaysEventsType() {
			ArrayList<String> events =new ArrayList<String>(); 
	       int evNum = 0;
	       
	       if (todaysEventsNum==0) {
	    	  System.out.println("No events");	
	    	  events.add("No planned events for today"); 
	       } else {
	    	   for(MyEvent ev: calEvents){
	    		
	        	   if (ev.isToday())
	        	   {
	        		   events.add(ev.eventType);
	        		  // events.add(ev.description);
	        		  //events.add(ev.eventType+" at "+ev.location);
	        		  // events.add(d);
	        		   evNum++;
	        	   }
	    	   }
	       }
	       
	       //Log.v("CCH",evHtml);
	       
	       return events;
	   	}
		
	public ArrayList<String> getTodaysEventsDetail() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
       
       if (todaysEventsNum==0) {
    	   events.add(" "); 			  
       } else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isToday())
        	   {
        		  // events.add(ev.eventType);
        		  // events.add(ev.description);
        		  events.add(ev.eventType+" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	   }
       }
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	
	public ArrayList<String> getTodaysEventsDestcription() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
       
      
       if (todaysEventsNum==0) {
    	   events.add(" "); 			  
       } else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isToday())
        	   {
        		  // events.add(ev.eventType);
        		   events.add(ev.description);
        		  //events.add(ev.eventType+" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	   }
       }
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	public ArrayList<String> getTodaysEventsTime(Boolean showDay) {
		ArrayList<String> events =new ArrayList<String>(); 
		Long startDate;
       int evNum = 0;
      
       
     
       if (todaysEventsNum==0) {
    	   events.add(" "); 			  
       } else {
    	   for(MyEvent ev: calEvents){
    		   long milliSeconds = ev.startDate;
    	       String dformat =(showDay)? "MMM dd" : "hh:mm a";
    	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
    	       Calendar calendar = Calendar.getInstance();
    	       calendar.setTimeInMillis(milliSeconds);
    	    	String d =formatter.format(calendar.getTime());
        	   if (ev.isToday())
        	   {
        		  // events.add(ev.eventType);
        		  // events.add(ev.description);
        		 // events.add(ev.eventType+" at "+ev.location);
        		  events.add(d);
        		   evNum++;
        	   }
    	   }
       }
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	
	
	

	public ArrayList<String> getTomorrowEventsType() {
			ArrayList<String> events =new ArrayList<String>(); 
	       int evNum = 0;
	     
	       if (tomorrowsEventsNum==0) {
	    	   events.add("No planned events for tomorrow"); 			  
	       } else {
	    	   for(MyEvent ev: calEvents){
	    		
	        	   if (ev.isTomorrow())
	        	   {
	        		   events.add(ev.eventType);
	        		  // events.add(ev.description);
	        		  //events.add(ev.eventType+" at "+ev.location);
	        		  // events.add(d);
	        		   evNum++;
	        	   }
	    	   }
	       }
	       
	       //Log.v("CCH",evHtml);
	       
	       return events;
	   	}
		
	public ArrayList<String> getTommorowEventsDetail() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
      
       if (tomorrowsEventsNum==0) {
    	   events.add(" "); 			  
       } else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isTomorrow())
        	   {
        		  // events.add(ev.eventType);
        		  // events.add(ev.description);
        		  events.add(ev.eventType+" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	   }
       }
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	
	public ArrayList<String> getTommorowEventsDestcription() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
      
       if (tomorrowsEventsNum==0) {
    	   events.add(" "); 			  
       } else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isTomorrow())
        	   {
        		  // events.add(ev.eventType);
        		   events.add(ev.description);
        		  //events.add(ev.eventType+" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	   }
       }
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	public ArrayList<String> getTommorowEventsTime(Boolean showDay) {
		ArrayList<String> events =new ArrayList<String>(); 
		//Long startDate = null;
	       int evNum = 0;
	       if (tomorrowsEventsNum==0) {
	    	   events.add(" "); 			  
	       } else {
	    	   for(MyEvent ev: calEvents){
	    		   long milliSeconds = ev.startDate;
	    	       String dformat =(showDay)? "MMM dd" : "hh:mm a";
	    	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
	    	       Calendar calendar = Calendar.getInstance();
	    	       calendar.setTimeInMillis(milliSeconds);
	    	    	String d =formatter.format(calendar.getTime());
	        	   if (ev.isToday())
	        	   {
	        		  // events.add(ev.eventType);
	        		  // events.add(ev.description);
	        		 // events.add(ev.eventType+" at "+ev.location);
	        		  events.add(d);
	        		   evNum++;
	        	   }
	    	   }
	       }
	       
	       //Log.v("CCH",evHtml);
       
       return events;
   	}
	
	
	public ArrayList<String> getFutureEventsType() {
			ArrayList<String> events =new ArrayList<String>(); 
	       int evNum = 0;
	       
	       if (futureEventsNum==0) {
	    	   events.add("No planned future events"); 			  
	       } else {
	    	   for(MyEvent ev: calEvents){
	    		
	        	   if (ev.isFuture())
	        	   {
	        		   events.add(ev.eventType);
	        		  // events.add(ev.description);
	        		  //events.add(ev.eventType+" at "+ev.location);
	        		  // events.add(d);
	        		   evNum++;
	        	   }
	    	   }
	       }
	       
	       //Log.v("CCH",evHtml);
	       
	       return events;
	   	}
		
	public ArrayList<String> getFutureEventsDetail() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
      
       if (futureEventsNum==0) {
    	   events.add(" "); 			  
       } else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isFuture())
        	   {
        		  // events.add(ev.eventType);
        		  // events.add(ev.description);
        		  events.add(ev.eventType+" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	   }
       }
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	
	public ArrayList<String> getFutureEventsDestcription() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
       
       if (futureEventsNum==0) {
    	   events.add(" "); 			  
       } else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isFuture())
        	   {
        		  // events.add(ev.eventType);
        		   events.add(ev.description);
        		  //events.add(ev.eventType+" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	   }
       }
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	public ArrayList<String> getFutureEventsTime(Boolean showDay) {
		ArrayList<String> events =new ArrayList<String>(); 
		//Long startDate = null;
	       int evNum = 0;
	       
	       if (futureEventsNum==0) {
	    	   events.add(" "); 			  
	       } else {
	    	   for(MyEvent ev: calEvents){
	    		   long milliSeconds = ev.startDate;
	    	       String dformat =(showDay)? "MMM dd" : "hh:mm a";
	    	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
	    	       Calendar calendar = Calendar.getInstance();
	    	       calendar.setTimeInMillis(milliSeconds);
	    	    	String d =formatter.format(calendar.getTime());
	        	   if (ev.isToday())
	        	   {
	        		  // events.add(ev.eventType);
	        		  // events.add(ev.description);
	        		 // events.add(ev.eventType+" at "+ev.location);
	        		  events.add(d);
	        		   evNum++;
	        	   }
	    	   }
	       }
	       
	       //Log.v("CCH",evHtml);
       
       return events;
   	}
    public void readCalendarEvent(Context context){
    	{
            Cursor cursor = context.getContentResolver()
                    .query(
                            Uri.parse("content://com.android.calendar/events"),
                            new String[] { "calendar_id", "title", "description",
                                    "dtstart", "dtend", "eventLocation", "_id as max_id" }, null, null, "dtstart");
            cursor.moveToFirst();
            
            // fetching calendars name
            String CNames[] = new String[cursor.getCount()];

            calEvents.clear();
            todaysEventsNum = 0;
            tomorrowsEventsNum = 0;
            futureEventsNum = 0;
            thismonthEventsNum = 0;
            thismonthEventsDone = 0;
            previousLocations = "";

            Calendar c = Calendar.getInstance();
            for (int i = 0; i < CNames.length; i++) {
                 CNames[i] = cursor.getString(1);

         	    long start = Long.parseLong(cursor.getString(3));
         	    long end = c.getTimeInMillis();
                 
         	    try {
         		   end = Long.parseLong(cursor.getString(4));
         	    } catch(NumberFormatException e) {}
         	   
         	   MyEvent payload = new MyEvent();
         	   payload.eventId = cursor.getLong(cursor.getColumnIndex("max_id"));
         	   payload.eventType = cursor.getString(1);
         	   payload.description = cursor.getString(2);
         	   payload.startDate = start;
         	   payload.endDate = end;
         	   payload.location = cursor.getString(5);
     	       addToPreviousLocations(cursor.getString(5));
         	   calEvents.add(payload);
        	   
         	   if (payload.isToday())              { todaysEventsNum++;    } 
         	   else if (payload.isTomorrow())      { tomorrowsEventsNum++; } 
         	   else if (payload.isFuture())        { futureEventsNum++;    }
         	   
         	   if (payload.isThisMonth())     { thismonthEventsNum++; }
         	   if (payload.isThisMonth(true)) { thismonthEventsDone++; }
         	   
                cursor.moveToNext();
            }  
            
            cursor.close();
     }
    	
    }
    private void addToPreviousLocations(String s)
    {
    	try {
    	   if ((! s.isEmpty()) && s.length() < 20) 
    	   {
    		   s = s.replace(",","");
    		   s = s.replace("'", "");
    		   s = s.toLowerCase(Locale.UK).trim();
    		   if (! previousLocations.contains(s)) {
        		   if (!  this.previousLocations.equals("")) { this.previousLocations += ","; }
    			   this.previousLocations += "\""+ s + "\"";
    		   }
    	   }
    	} catch (NullPointerException e) {}
    }

   
    

}
