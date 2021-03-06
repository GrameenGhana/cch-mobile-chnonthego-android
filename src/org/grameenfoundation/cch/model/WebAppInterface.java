package org.grameenfoundation.cch.model;



import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.model.Course;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.text.format.DateFormat;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class WebAppInterface {
    Context mContext;
    
    private ArrayList<MyEvent> calEvents = new ArrayList<MyEvent>();

    private int todaysEventsNum = 0;
    private int tomorrowsEventsNum = 0;
    private int futureEventsNum = 0;
    private int thismonthEventsNum = 0;
    private int thismonthEventsDone = 0;
    private String previousLocations = "";
    
    private DbHelper dbh;
    

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
        dbh = new DbHelper(c);
        readCalendarEvent(c);
    }
        
    @JavascriptInterface
    public String getUsername() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    	return prefs.getString(mContext.getString(R.string.prefs_display_name),
    						   mContext.getString(R.string.prefs_username));
    }
    
    /******************************* Achievements methods *****/
    
    @JavascriptInterface
    public String numEventsCompleted() {
    	return String.valueOf(thismonthEventsNum);
    }
    
    @JavascriptInterface
    public int numCoursesCompleted() {
    	int[] c = courseNumInfo();
    	return c[1];
    }
    
    @JavascriptInterface
    public String achievementCenterBar() {
    	
    	String bar = "";
    	int numbars = 7;
    	int[] cInfo = courseNumInfo();
    	
    	float event_pc = (thismonthEventsNum>0) ?  ((float) thismonthEventsDone / thismonthEventsNum ) : 0;
    	float course_pc = (cInfo[0]>0) ? ((float) cInfo[1] / cInfo[0])  : 0;
    	int numSuccessbars = Math.round((float) ((float) event_pc * (float)(numbars/2)) + (float) ((float)course_pc * (float)(numbars/2)));

    	int j= 1;
    	for (int i=1; i<=7; i++) {
    		 int v = (j>numSuccessbars) ? 0 : 100; 
    		 bar += this.achievementBar(v, i);
    		 j++;
    	}
    	
    	return bar;
    }
    
    @JavascriptInterface
    public String eventsProgessList()
    {
    	float event_pc = (thismonthEventsNum>0) ?  ((float) thismonthEventsDone / thismonthEventsNum ) : 0;
    	return progressList("Events completed this month", (event_pc * 100));
    }
    
    @JavascriptInterface
    public String coursesProgessList()
    {
    	String list = "";
    	ArrayList<Course> courses = dbh.getCourses();
    	
    	if (courses.size()==0) {
    		return "<b>No Courses installed</b>";
    	}

    	for(Course c:  courses) {
    		float comp = dbh.getCourseProgress(c.getModId());
    		ArrayList<String> quizzes = dbh.getQuizResults(c.getModId());
    		list += progressList(c.getTitle("en"),comp);
    		if (quizzes.size()>0) {
    			list += "<br/>";
    			list += "<div class=\"evtdetails\">" +
                        "<ul class=\"list-unstyled\" style=\"margin-top: 2px;padding-left:20px; font-weight:bold\">";             
    			for(String q: quizzes) {
    				list += "<li>" + q + "</li>";
    			}
    			list += "</ul></div><br/>";
    		}
    	}
    	    	
    	return list;
    }
    
    private int[] courseNumInfo()
    {
    	int courseInfo[] = new int[2];
    	int numCompleted = 0;
    	
    	ArrayList<Course> courses = dbh.getCourses();
    	
    	for(Course c:  courses) {
    		float comp = dbh.getCourseProgress(c.getModId());
    		numCompleted = (comp >= 100) ? numCompleted+1 : numCompleted;
    	}
    	
    	courseInfo[0] = courses.size();
    	courseInfo[1] = numCompleted;
    	
    	return courseInfo;
    }
      
    private String achievementBar(int value, int barnum)
    {
        int height = barnum * 10;
        int margintop = (barnum - 1) * -10;
        
    	return "<div class=\"progress vertical bottom\" style =\"height: "+height+"px; margin-top: "+margintop+"px; margin-left: -15px;\"> " +
               "<div class=\"progress-bar progress-bar-success\" role=\"progressbar\" aria-valuetransitiongoal=\""+value+"\"> "+
               "</div></div> ";
    }
    
    private String progressList(String title, double value)
    {
    	String color = (value < 1) ? "progress-bar-warning" : ""; 
    	color = (value >=50) ? "progress-bar-success" : color;
    			
    	String tc = (value < 1) ? "black" : "white";
    	
    	return "<label>"+title+"</label>" +
               "<div class=\"evtprogress\">" +
               "  <div class=\"progress-bar "+color+"\" role=\"progressbar\" style=\"color:"+tc+" ;\" aria-valuetransitiongoal=\""+value+"\">&nbsp;</div> "+
               "</div><br/>";
    }
    
    
    
    /******************************* Event planner methods *****/

    
    @JavascriptInterface
    public void refreshEvents() {
    	readCalendarEvent(mContext);
    }
    
    @JavascriptInterface
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
    	/*
	    Calendar cal = Calendar.getInstance(); 
	    int sdk = android.os.Build.VERSION.SDK_INT;
	    
    	Intent intent =  new Intent(Intent.ACTION_EDIT);
    	intent.setType("vnd.android.cursor.item/event");	
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
            | Intent.FLAG_ACTIVITY_SINGLE_TOP
            | Intent.FLAG_ACTIVITY_CLEAR_TOP
            | Intent.FLAG_ACTIVITY_NO_HISTORY
            | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        
	    if(sdk < android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {	
	        intent.putExtra("title", evt);
	        intent.putExtra("eventTimezone", TimeZone.getDefault().getID());
	    	intent.putExtra("beginTime", cal.getTimeInMillis());
	    	intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
	    	if (! desc.isEmpty())     {     intent.putExtra("description", desc); }
	    	if (! location.isEmpty()) { 	intent.putExtra("eventLocation", location); }

	    } else {
	        intent.putExtra(Events.TITLE, evt);
	        intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
	        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
	        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis()+60*60*1000);
	        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY , false);

	       	if (! desc.isEmpty())     {     intent.putExtra(Events.DESCRIPTION, desc); }
	    	if (! location.isEmpty()) { 	intent.putExtra(Events.EVENT_LOCATION, location); }
	    }
	    
	    intent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PUBLIC);
	    intent.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
	    mContext.startActivity(intent);
	    */
    }
    
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
        		   evHtml += this.eventSnippetItemAsHTML(ev.eventType, evNum);
        		   evNum++;
        	   }
    	   }
       }
       
       //Log.v("CCH",evHtml);
       
       return evHtml;
   	}
    
    @SuppressLint("DefaultLocale")
	@JavascriptInterface
    public String getEventsList(String period)
    {
    	String evHtml = "";
    	
    	if (period.toLowerCase().equals("future")) {
    		  if (futureEventsNum==0) { 
    			   evHtml = emptyEventListItemAsHTML();
    		   } else {
    			   for(MyEvent ev: calEvents) {
    		     	   if (ev.isFuture()) {   evHtml += eventListItemAsHTML(ev, false, true); }
    			   }
    		   }
    	} else if (period.toLowerCase().equals("tomorrow")) {
	  		  if (tomorrowsEventsNum==0) { 
				   evHtml = emptyEventListItemAsHTML();
			   } else {
				   for(MyEvent ev: calEvents) {
			     	   if (ev.isTomorrow()) {   evHtml += eventListItemAsHTML(ev, false, false); }
				   }
			   }				
    	} else {
    		  if (todaysEventsNum==0) { 
   			   		evHtml = emptyEventListItemAsHTML();
   		   	  } else {
   			        for(MyEvent ev: calEvents){
   		     	        if (ev.isToday()) { evHtml += eventListItemAsHTML(ev, true, false); }
   			        }
   		      }
    	}
    	
    	return evHtml;
    }
    
    @JavascriptInterface
    public String getPreviousLocations() { 
    	return "{\"myLocations\": ["+previousLocations+"]}";
    }
    
    /** Private Interfaces **/
    private String eventSnippetItemAsHTML(String event, int evNum)
    {
		if (event.length() >= 26) {   event = event.substring(0,29).concat("..."); }
    	return "<div class=\"tile-content\"><div class=\"padding10\">"+
  		       "		<p id=\"calevent"+evNum+"\" class=\"secondary-text fg-white no-margin\">"+event+"</p>"+
               "</div></div>";
    }
    
    private String eventListItemAsHTML(MyEvent ev, Boolean inclFlag, Boolean showDay)
    {
    	String dformat = (showDay)? "MMM dd" : "hh:mm a";
    	String d = ev.getDate(dformat);
    	String subtitle = (ev.location.equals("")) ? "No location specified" : ev.eventType + " at " + ev.location;
    	
    	String flag = "";
    	if (inclFlag) {
    		Calendar c = Calendar.getInstance();
    		flag = (ev.startDate <= c.getTimeInMillis()) ? "icon-flag-2 fg-red smaller" : "";
    	}
    	
    	return  "<a class=\"list\" href=\"#\">" 
             +  "  <div class=\"list-content gotoevent\" data-url=\"viewcal/"+ev.eventId+"\"> " 
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
             +  "   <span class=\"list-remark\">How about a lesson from the Learning center?</span>" 
             +  "  </div>"
    	     +  "</a>";    
    }

    
    private void readCalendarEvent(Context context) 
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
    
	    private class MyEvent
	    {
	    	public long eventId;
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

    /******************************* Staying well methods *****/

    
    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
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

  
}

