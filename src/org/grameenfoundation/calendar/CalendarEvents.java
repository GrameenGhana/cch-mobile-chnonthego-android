package org.grameenfoundation.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
   
	public CalendarEvents(Context c){
		this.mContext=c;
		readCalendarEvent(c);
	}
	
	public void addEvent(String evt, String location, String desc)
    {			
		Calendar cal = Calendar.getInstance();
		//beginTime.set(2012, 0, 19, 7, 30);
		//Calendar endTime = Calendar.getInstance();
		//endTime.set(2012, 0, 19, 8, 30);
		Intent intent = new Intent(Intent.ACTION_INSERT)
		        .setData(Events.CONTENT_URI)
		        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
		        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,  cal.getTimeInMillis()+60*60*1000)
		        .putExtra(Events.TITLE, evt)
		        .putExtra(Events.DESCRIPTION, desc)
		        .putExtra(Events.EVENT_LOCATION, location)
		        .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
		 		.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY , false);
		       
		mContext.startActivity(intent);
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
    	/*
    	String _id = null;
    	/*
    	   Cursor cursor = context.getContentResolver()
                   .query(
                           Uri.parse("content://com.android.calendar/events"),
                           new String[] { "calendar_id", "title", "description",
                                   "dtstart", "dtend", "eventLocation", "_id as max_id" }, null, null, "dtstart");
                                   
    	ContentResolver contentResolver = context.getContentResolver();
    	final Cursor cursor = contentResolver.query(Uri.parse("content://com.android.calendar/events"),
    	(new String[] { "_id"}), null, null, null);

    	while (cursor.moveToNext()) {

    	_id = cursor.getString(0);
    	

    	}
    	Uri.Builder builder = Instances.CONTENT_URI.buildUpon();
    		long now = new Date().getTime();
    		ContentUris.appendId(builder, now - DateUtils.WEEK_IN_MILLIS);
    		ContentUris.appendId(builder, now + DateUtils.WEEK_IN_MILLIS);
		Cursor cursor2 = context.getContentResolver()
                .query(builder.build(),
                        new String[] { Instances.CALENDAR_ID,Instances.TITLE, Instances.DESCRIPTION, Instances.BEGIN,Instances.END,  
                        				Instances.EVENT_LOCATION, 
                        				"event_id as max_id"  }, null, null, Instances.BEGIN);
        cursor2.moveToFirst();
        
       
           
           // fetching calendars name
    	String _id = null;
    	ContentResolver contentResolver = context.getContentResolver();
    	Cursor cursor = contentResolver.query(Uri.parse("content://com.android.calendar/events"),
    	(new String[] { "_id"}), null, null, null);

    	while (cursor.moveToNext()) {

    	_id = cursor.getString(0);
    	

    	}
    	String[] projection = new String[] {Instances.CALENDAR_ID,Instances.TITLE, Instances.DESCRIPTION, Instances.BEGIN,Instances.END,  
				Instances.EVENT_LOCATION, 
				"event_id as max_id" };
    	Uri.Builder builder = Instances.CONTENT_URI.buildUpon();
		long now = new Date().getTime();
		ContentUris.appendId(builder, now - DateUtils.WEEK_IN_MILLIS);
		ContentUris.appendId(builder, now + DateUtils.WEEK_IN_MILLIS);
    	String selection = "calendar_id = " + _id;
    	String path = "instances/when/" + (now - DateUtils.WEEK_IN_MILLIS) + "/" + (now + DateUtils.WEEK_IN_MILLIS);
    	String sortOrder = "begin DESC";
    	Cursor cursor2 = context.getContentResolver()
                .query(builder.build(),
                        new String[] { Instances.CALENDAR_ID,Instances.TITLE, Instances.DESCRIPTION, Instances.BEGIN,Instances.END,  
                        				Instances.EVENT_LOCATION, 
                        				"event_id as max_id"  }, selection, null,sortOrder);
                        				*/
    	Cursor cursor2 = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[] { "calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation", "_id as max_id" }, null, null, "dtstart");
        cursor2.moveToFirst();
           String CNames[] = new String[cursor2.getCount()];

           calEvents.clear();
           todaysEventsNum = 0;
           tomorrowsEventsNum = 0;
           futureEventsNum = 0;
           thismonthEventsNum = 0;
           thismonthEventsDone = 0;

           Calendar c = Calendar.getInstance();
           for (int i = 0; i < CNames.length; i++) {
                CNames[i] = cursor2.getString(1);

        	    long start = Long.parseLong(cursor2.getString(3));
        	    long end = c.getTimeInMillis();
                
        	    try {
        		   end = Long.parseLong(cursor2.getString(4));
        	    } catch(NumberFormatException e) {}
        	   
        	   MyEvent payload = new MyEvent();
        	   payload.eventId = cursor2.getLong(cursor2.getColumnIndex("max_id"));
        	   payload.eventType = cursor2.getString(1);
        	   payload.description = cursor2.getString(2);
        	   payload.startDate = start;
        	   payload.endDate = end;
        	   payload.location = cursor2.getString(5);
        	   calEvents.add(payload);
       	   
        	   if (payload.isToday())              { todaysEventsNum++;    } 
        	   else if (payload.isTomorrow())      { tomorrowsEventsNum++; } 
        	   else if (payload.isFuture())        { futureEventsNum++;    }
        	   
        	   if (payload.isThisMonth())     { thismonthEventsNum++; }
        	   if (payload.isThisMonth(true)) { thismonthEventsDone++; }
        	   
               cursor2.moveToNext();
           }  
           
           cursor2.close();
    }
    

   
    

}
