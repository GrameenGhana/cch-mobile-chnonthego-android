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
    
    private ArrayList<MyEvent> calEvents = new ArrayList<MyEvent>();

    private int todaysEventsNum = 0;
    private int tomorrowsEventsNum = 0;
    private int futureEventsNum = 0;
   

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
        readCalendarEvent(c);
    }
    
    /** Show a toast from the web page */
    @JavascriptInterface
    public String getNumEventsToday() {	
    	return String.valueOf(todaysEventsNum) ;
    }
    
    @JavascriptInterface
    public String getTodaysEventsSnippet() {
       String evHtml = ""; 
       int evNum = 0;
       
       if (todaysEventsNum==0) {
    	   evHtml = this.eventSnippetItemAsHTML("No planned events today.", evNum); 			  
       } else {
    	   for(MyEvent ev: calEvents){
        	   if (ev.isToday())
        	   {
        		   evHtml = this.eventSnippetItemAsHTML(ev.eventType, evNum);
        		   evNum++;
        	   }
    	   }
       }
       
       return evHtml;
   	}
    
    @JavascriptInterface
    public String getEventsList(String period)
    {
    	String evHtml = "";
    	
    	if (period.toLowerCase().equals("future")) {
    		  if (futureEventsNum==0) { 
    			   evHtml = emptyEventListItemAsHTML();
    		   } else {
    			   for(MyEvent ev: calEvents) {
    		     	   if (ev.isFuture()) {   evHtml += eventListItemAsHTML(ev, false); }
    			   }
    		   }
    	} else if (period.toLowerCase().equals("tomorrow")) {
	  		  if (tomorrowsEventsNum==0) { 
				   evHtml = emptyEventListItemAsHTML();
			   } else {
				   for(MyEvent ev: calEvents) {
			     	   if (ev.isTomorrow()) {   evHtml += eventListItemAsHTML(ev, false); }
				   }
			   }				
    	} else {
    		  if (todaysEventsNum==0) { 
   			   		evHtml = emptyEventListItemAsHTML();
   		   	  } else {
   			        for(MyEvent ev: calEvents){
   		     	        if (ev.isToday()) { evHtml += eventListItemAsHTML(ev, true); }
   			        }
   		      }
    	}
    	
    	return evHtml;
    }
    

 
    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }
    
    /** Staying Well methods **/
    
    @JavascriptInterface
    public String getQuote() {
    	return "<p>Horray!! I am rocking this world!!!</p><footer><cite title=\"dkh\">gf</cite></footer>";
    }
    
    @JavascriptInterface
    public String getTodayInHistory() {
    	return "<h2>Today In History</h2><br><p><b>2011-02-03:</b> Horray!! I am rocking this world!!!</p>";
    }

    /** Private Interfaces **/
    private String eventSnippetItemAsHTML(String event, int evNum)
    {
		if (event.length() >= 26) {   event = event.substring(0,29).concat("..."); }
    	return "<div class=\"tile-content\"><div class=\"padding10\">"+
  		       "		<p id=\"calevent"+evNum+"\" class=\"secondary-text fg-white no-margin\">"+event+"</p>"+
               "</div></div>";
    }
    
    private String eventListItemAsHTML(MyEvent ev, Boolean inclFlag)
    {
    	String d = ev.getDate();
    	String subtitle = ev.eventType + " at " + ev.location;
    	
    	String flag = "";
    	if (inclFlag) {
    		Calendar c = Calendar.getInstance();
    		flag = (ev.startDate <= c.getTimeInMillis()) ? "icon-flag-2 fg-red smaller" : "";
    	}
    	
    	return  "<a class=\"list\" href=\"#\">" 
             +  "  <div class=\"list-content\"> " 
             +  "   <span class=\"list-title\"><span class=\"place-right "+flag+"\"></span>"+ev.eventType+"</span>" 
             +  "   <span class=\"list-subtitle\"><span class=\"place-right\">"+d+"</span>"+subtitle+"</span>" 
             +  "   <span class=\"list-remark\">"+ev.description+"</span>" 
             +  "  </div>"
    	     +  "</a>";    
    }

    private String emptyEventListItemAsHTML()
    {
    	return  "<a class=\"list\" href=\"#\">" 
             +  "  <div class=\"list-content\"> " 
             +  "   <span class=\"list-title\"><span class=\"place-right\"></span>No events planned.</span>" 
             +  "   <span class=\"list-subtitle\"><span class=\"place-right\"></span>No events found on your calendar</span>" 
             +  "   <span class=\"list-remark\">Consider the lessons from the Learning center or the relaxing techniques in the Staying well center.</span>" 
             +  "  </div>"
    	     +  "</a>";    
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

           Calendar c = Calendar.getInstance();
           calEvents.clear();
           
           for (int i = 0; i < CNames.length; i++) {
                CNames[i] = cursor.getString(1);

        	    long start = Long.parseLong(cursor.getString(3));
        	    long end = c.getTimeInMillis();
                
        	    try {
        		   end = Long.parseLong(cursor.getString(4));
        	    } catch(NumberFormatException e) {}
        	   
        	   MyEvent payload = new MyEvent();
        	   payload.eventType = cursor.getString(1);
        	   payload.description = cursor.getString(2);
        	   payload.startDate = start;
        	   payload.endDate = end;
        	   payload.location = cursor.getString(5);
        	   calEvents.add(payload);
        	   
        	   if (payload.isToday())         {  todaysEventsNum++; } 
        	   else if (payload.isTomorrow()) { tomorrowsEventsNum++; } 
        	   else if (payload.isFuture())   { futureEventsNum++; }
        	   
               cursor.moveToNext();
           }         
    }

    private class MyEvent
    {
    	public String eventType;
    	public String location;
    	public String description;
    	public Long startDate;
    	public Long endDate;
    	
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
              
        public String getDate() {
			   long milliSeconds = this.startDate;
               SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
               Calendar calendar = Calendar.getInstance();
               calendar.setTimeInMillis(milliSeconds);
               return formatter.format(calendar.getTime());
        }
        
    }
}

