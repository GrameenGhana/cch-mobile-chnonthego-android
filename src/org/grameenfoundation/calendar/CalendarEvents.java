package org.grameenfoundation.calendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.PlanEventActivity;
import org.digitalcampus.oppia.application.DbHelper;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Instances;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;


public class CalendarEvents {
	Context mContext;
    private ArrayList<MyEvent> calEvents = new ArrayList<MyEvent>();

    private int todaysEventsNum = 0;
    private int tomorrowsEventsNum = 0;
    private int futureEventsNum = 0;
    private int thismonthEventsNum = 0;
    private int thismonthEventsDone = 0;
    private String previousLocations = "";
	private SharedPreferences prefs;
    private static DbHelper dbh;
    
	public CalendarEvents(Context c){
		this.mContext=c;
		readCalendarEvent(c);
		dbh = new DbHelper(c);
	}
	
	public boolean addRecurringEvent(String evt, String location, String desc,String rrule)
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
		return true;
    }
	public boolean addEvent(String evt, String location, String desc)
    {	
		/*
		long calID = 1;
		TimeZone timeZone = TimeZone.getDefault();
		Calendar cal = Calendar.getInstance();
		ContentResolver cr = mContext.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(Events.DTSTART, dtstart);
		values.put(Events.DTEND,  dtend);
		values.put(Events.TITLE, evt);
		values.put(Events.DESCRIPTION, desc);
		values.put(Events.CALENDAR_ID, calID);
		values.put(Events.AVAILABILITY,  Events.AVAILABILITY_BUSY);
		values.put(Events.EVENT_LOCATION, location);
		values.put(Events.EVENT_TIMEZONE, timeZone.getID());
		values.put(CalendarContract.EXTRA_EVENT_ALL_DAY,  false);
		Uri uri = cr.insert(Events.CONTENT_URI, values);
		//System.out.println(TimeZone.getAvailableIDs().toString());
		// get the event ID that is the last element in the Uri
		
		long eventID = Long.parseLong(uri.getLastPathSegment());
		if(eventID!=0){
			System.out.println(String.valueOf(eventID));
		}
		
		
		//beginTime.set(2012, 0, 19, 7, 30);
		//Calendar endTime = Calendar.getInstance();
		//endTime.set(2012, 0, 19, 8, 30);
		 * */
		
		//prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		Calendar cal = Calendar.getInstance();
		Intent	intent = new Intent(Intent.ACTION_EDIT)
		        .setData(Events.CONTENT_URI)
		        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis())
		        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME,  cal.getTimeInMillis()+60*60*1000)
		        .putExtra(Events.TITLE, evt)
		        .putExtra(Events.DESCRIPTION, desc)
		        .putExtra(Events.EVENT_LOCATION, location)
		        .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
		 		.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY , false);
		
		//long eventID = Long.parseLong(Events.CONTENT_URI.getLastPathSegment());
		//System.out.println(String.valueOf(eventID));
		
		//String user_id = prefs.getString(mContext.getString(R.string.prefs_username), "noid"); 
		//dbh.insertCalendarEvent(eventID,evt, user_id, desc, location, cal.getTimeInMillis(),  cal.getTimeInMillis()+60*60*1000);
		mContext.startActivity(intent);
		
		return true;
		
	
    }
	public boolean editEvent(long event_id,String evt, String location, String desc)
    {	
		ContentResolver cr = mContext.getContentResolver();
		ContentValues values = new ContentValues();
		Uri updateUri = null;
		// The new title for the event
		values.put(Events.TITLE,evt); 
		values.put(Events.DESCRIPTION,desc); 
		values.put(Events.EVENT_LOCATION,desc); 
		updateUri = ContentUris.withAppendedId(Events.CONTENT_URI, event_id);
		int rows =  mContext.getContentResolver().update(updateUri, values, null, null);
		Log.i("Calendar edit", "Rows updated: " + rows);  
		
		return true;
    }
	
	public boolean deleteEvent(long event_id)
    {	
		ContentResolver cr = mContext.getContentResolver();
		ContentValues values = new ContentValues();
		Uri deleteUri = null;
		deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, event_id);
		int rows = mContext.getContentResolver().delete(deleteUri, null, null);
		Log.i("Calendar delete", "Rows deleted: " + rows);  
		return true;
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
	public HashMap<String, String> getTodaysCalendarEvents(){
		HashMap<String,String> list=new HashMap<String,String>();
		
		
		return list;
		
	}
	public ArrayList<String> getTodaysEventsType() {
			ArrayList<String> events =new ArrayList<String>(); 
	       int evNum = 0;
	       
	       //if (todaysEventsNum==0) {
	    	//  System.out.println("No events");	
	    	 // events.add("No planned events for today"); 
	       //} else {
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
	       //}
	       
	       //Log.v("CCH",evHtml);
	       
	       return events;
	   	}
	public ArrayList<String> getTodaysEventsId() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
       
     //  if (todaysEventsNum==0) {
    	//  System.out.println("No events");	
    	  //events.add("No planned events for today"); 
    //   } else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isToday())
        	   {
        		   events.add(String.valueOf(ev.eventId));
        		  // events.add(ev.description);
        		  //events.add(ev.eventType+" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	   }
      // }
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	
	public ArrayList<String> getTodaysEventsDetail() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
       
      // if (todaysEventsNum==0) {
    	//   events.add(" "); 			  
       //} else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isToday())
        	   {
        		  // events.add(ev.eventType);
        		  // events.add(ev.description);
        		  events.add(ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	   }
       //}
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	
	public ArrayList<String> getTodaysEventsDestcription() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
       
      
      // if (todaysEventsNum==0) {
    	//   events.add(" "); 			  
       //} else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isToday())
        	   {
        		  // events.add(ev.eventType);
        		   events.add(ev.description);
        		  //events.add(ev.eventType+" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	 //  }
       }
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	public ArrayList<String> getTodaysEventsTime(Boolean showDay) {
		ArrayList<String> events =new ArrayList<String>(); 
		Long startDate;
       int evNum = 0;
      
       
     
    //   if (todaysEventsNum==0) {
    	//   events.add(" "); 			  
       //} else {
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
       //}
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	
	
	

	public ArrayList<String> getTomorrowEventsType() {
			ArrayList<String> events =new ArrayList<String>(); 
	       int evNum = 0;
	     
	       //if (tomorrowsEventsNum==0) {
	    	 //  events.add("No planned events for tomorrow"); 			  
	       //} else {
	    	   for(MyEvent ev: calEvents){
	    		
	        	   if (ev.isTomorrow())
	        	   {
	        		   events.add(ev.eventType);
	        		  // events.add(ev.description);
	        		  //events.add(ev.eventType+" at "+ev.location);
	        		  // events.add(d);
	        		   evNum++;
	        	   }
	    	 //  }
	       }
	       
	       //Log.v("CCH",evHtml);
	       
	       return events;
	   	}
	public ArrayList<String> getTomorrowEventsId() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
     
       //if (tomorrowsEventsNum==0) {
    	  // events.add("No planned events for tomorrow"); 			  
       //} else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isTomorrow())
        	   {
        		   events.add(String.valueOf(ev.eventId));
        		  // events.add(ev.description);
        		  //events.add(ev.eventType+" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	 //  }
       }
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	
	public ArrayList<String> getTommorowEventsDetail() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
      
      // if (tomorrowsEventsNum==0) {
    	//   events.add(" "); 			  
       //} else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isTomorrow())
        	   {
        		  // events.add(ev.eventType);
        		  // events.add(ev.description);
        		  events.add(" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	   }
       //}
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	
	public ArrayList<String> getTommorowEventsDestcription() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
      
       //if (tomorrowsEventsNum==0) {
    	  // events.add(" "); 			  
       //} else {
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
       //}
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	public ArrayList<String> getTommorowEventsTime(Boolean showDay) {
		ArrayList<String> events =new ArrayList<String>(); 
		//Long startDate = null;
	       int evNum = 0;
	      // if (tomorrowsEventsNum==0) {
	    	//   events.add(" "); 			  
	       //} else {
	    	   for(MyEvent ev: calEvents){
	    		   long milliSeconds = ev.startDate;
	    	       String dformat =(showDay)? "MMM dd" : "hh:mm a";
	    	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
	    	       Calendar calendar = Calendar.getInstance();
	    	       calendar.setTimeInMillis(milliSeconds);
	    	    	String d =formatter.format(calendar.getTime());
	        	   if (ev.isTomorrow())
	        	   {
	        		  // events.add(ev.eventType);
	        		  // events.add(ev.description);
	        		 // events.add(ev.eventType+" at "+ev.location);
	        		  events.add(d);
	        		   evNum++;
	        	   }
	    	   }
	       //}
	       
	       //Log.v("CCH",evHtml);
       
       return events;
   	}
	
	
	public ArrayList<String> getFutureEventsType() {
			ArrayList<String> events =new ArrayList<String>(); 
	       int evNum = 0;
	       
	      // if (futureEventsNum==0) {
	    	//   events.add("No planned future events"); 			  
	       //} else {
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
	       //}
	       
	       //Log.v("CCH",evHtml);
	       
	       return events;
	   	}
	public ArrayList<String> getFutureEventsId() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
     
      // if (tomorrowsEventsNum==0) {
    	//   events.add("No planned events for tomorrow"); 			  
    //   } else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isFuture())
        	   {
        		   events.add(String.valueOf(ev.eventId));
        		  // events.add(ev.description);
        		  //events.add(ev.eventType+" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	   }
      // }
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}	
	public ArrayList<String> getFutureEventsDetail() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
      
     //  if (futureEventsNum==0) {
    	   events.add(" "); 			  
       //} else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isFuture())
        	   {
        		  // events.add(ev.eventType);
        		  // events.add(ev.description);
        		  events.add(" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	   }
       //}
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	
	public ArrayList<String> getFutureEventsDestcription() {
		ArrayList<String> events =new ArrayList<String>(); 
       int evNum = 0;
       
     ///  if (futureEventsNum==0) {
    	//   events.add(" "); 			  
       //} else {
    	   for(MyEvent ev: calEvents){
    		
        	   if (ev.isFuture())
        	   {
        		  // events.add(ev.eventType);
        		   events.add(ev.description);
        		  //events.add(ev.eventType+" at "+ev.location);
        		  // events.add(d);
        		   evNum++;
        	   }
    	 //  }
       }
       
       //Log.v("CCH",evHtml);
       
       return events;
   	}
	public ArrayList<String> getFutureEventsTime(Boolean showDay) {
		ArrayList<String> events =new ArrayList<String>(); 
		//Long startDate = null;
	       int evNum = 0;
	       
	      // if (futureEventsNum==0) {
	    	//   events.add(" "); 			  
	       //} else {
	    	   for(MyEvent ev: calEvents){
	    		   long milliSeconds = ev.startDate;
	    	       String dformat =(showDay)? "MMM dd" : "hh:mm a";
	    	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
	    	       Calendar calendar = Calendar.getInstance();
	    	       calendar.setTimeInMillis(milliSeconds);
	    	    	String d =formatter.format(calendar.getTime());
	        	   if (ev.isFuture())
	        	   {
	        		  // events.add(ev.eventType);
	        		  // events.add(ev.description);
	        		 // events.add(ev.eventType+" at "+ev.location);
	        		  events.add(d);
	        		   evNum++;
	        	   }
	    	   }
	       //}
	       
	       //Log.v("CCH",evHtml);
       
       return events;
   	}
    public void readCalendarEvent(Context context){
    	{
    		/*
    		ArrayList<String> event_Id;
    		ArrayList<String> event_type;
    		ArrayList<String> event_description;
    		ArrayList<String> dtstart;
    		ArrayList<String> dtend;
    		ArrayList<String> event_location;
    		HashMap<String,String> list=new HashMap<String,String>();
    		String user_id = prefs.getString(mContext.getString(R.string.prefs_username), "noid"); 
    		list=dbh.getCalendarEvents(user_id);
    		event_Id=new ArrayList<String>();
    		event_Id.add(list.get("event_id"));
    		event_type=new ArrayList<String>();
    		event_type.add(list.get("event_type"));
    		event_description=new ArrayList<String>();
    		event_description.add(list.get("event_description"));
    		dtstart=new ArrayList<String>();
    		dtstart.add(list.get("event_dtstart"));
    		dtend=new ArrayList<String>();
    		dtend.add(list.get("event_dtend"));
    		event_location=new ArrayList<String>();
    		event_location.add(list.get("event_location"));
    		
    		 calEvents.clear();
             todaysEventsNum = 0;
             tomorrowsEventsNum = 0;
             futureEventsNum = 0;
             thismonthEventsNum = 0;
             thismonthEventsDone = 0;
             previousLocations = "";
             for(int i = 0; i<=list.size();i++){
    		  MyEvent payload = new MyEvent();
         	   payload.eventId = Long.valueOf(event_Id.get(i));
         	   payload.eventType = event_type.get(i);
         	   payload.description = event_description.get(i);
         	   payload.startDate =Long.valueOf(dtstart.get(i));
         	   payload.endDate = Long.valueOf(dtend.get(i));
         	   payload.location = event_location.get(i);
         	   
         	   calEvents.add(payload);
        	   
         	   if (payload.isToday())              { todaysEventsNum++;    } 
         	   else if (payload.isTomorrow())      { tomorrowsEventsNum++; } 
         	   else if (payload.isFuture())        { futureEventsNum++;    }
         	   
         	   if (payload.isThisMonth())     { thismonthEventsNum++; }
         	   if (payload.isThisMonth(true)) { thismonthEventsDone++; }
             }
         	  */ 
    		Uri uri = CalendarContract.Calendars.CONTENT_URI;
    		 long calID = 0;
    		String[] projection = new String[] {
    		       CalendarContract.Calendars._ID
    		       //CalendarContract.Calendars.ACCOUNT_NAME,
    		       //CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
    		       //CalendarContract.Calendars.NAME,
    		       //CalendarContract.Calendars.CALENDAR_COLOR
    		};

    		Cursor calendarCursor =  context.getContentResolver()
                    .query(uri, projection, null, null, null);
    		while(calendarCursor.moveToNext()){
    			
    			 calID=calendarCursor.getLong(0);
    			 System.out.println("Calendar Ids: "+String.valueOf(calID));
    		}
    		
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
