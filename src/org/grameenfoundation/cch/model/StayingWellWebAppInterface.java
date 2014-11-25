package org.grameenfoundation.cch.model;


import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class StayingWellWebAppInterface {
    
	Context mContext;
    private DbHelper dbh;
    private long thirtydays = 2592000000L;
    

    /** Instantiate the interface and set the context */
    public StayingWellWebAppInterface(Context c) {
        mContext = c;
       dbh = new DbHelper(c);
    }
        
    @JavascriptInterface
    public String getUsername() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    	return prefs.getString(mContext.getString(R.string.prefs_display_name),
    						   mContext.getString(R.string.prefs_username));
    }
    
    @JavascriptInterface
    public String getGreeting() {
    	String username = getUsername();
    	username = (username.isEmpty()) ? username : " "+username;
    	String g = (getTime()=="") ? "Hello" : "Good " + getTime();
    	return g + username;
    }
    
    @JavascriptInterface
    public String getRoutineInfo(String infoType) {
    	
		return "Welcome to Staying Well";
/*
    	// if no profile encourage client to sign up
    	if (getProfileStatus()=="" || getMonthlyPlan()=="") {
    		return "Welcome to Staying Well";
    	}
    	    	
    	Calendar c  = Calendar.getInstance();
    	int year = c.get(Calendar.YEAR);
    	int month = c.get(Calendar.MONTH);
    	int day = c.get(Calendar.DAY_OF_MONTH);
    	String timeofday = getTime();
    	int numactivities = 5;
    	    	
    	String title = "You have <span class=\"highlight\">"+numactivities+"</span> activities this "+timeofday;
    	
    	// Get routine items
    	if (infoType.equals("list"))
    	{
    		return "<h3 id=\"routine_title\">"+title+"</h3>"
            		+ "<li>Sip on <a href=\"#\" class=\"navigation-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> when you wake up in the morning</li>"
            		+ "<li>Brush your teeth, and <a href=\"#\" class=\"navigation-view\" data-view=\"gliving/content/worksheets/personalcare/tongue scraping.html\">scrape your tongue</a> to remove any coating</li>"
            		+ "<li><a href=\"#\" class=\"navigation-view\" data-view=\"gliving/content/worksheets/personalcare/hair treatment.html\">Nourish hair</a> with coconut oil</li>"
            		+ "<li>Once a week, <a href=\"#\" class=\"navigation-view\" data-view=\"gliving/content/worksheets/personalcare/massage body.html\">massage your body</a> with coconut oil </li>"
            		+ "<li>Use <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/neem water.html\">neem water</a> to wash off the oil from your body and hair </li>";
    	} else {
    		return title + ((infoType.equals("label")) ? " <span class=\"charet\"></span>": "");
    	}
  	*/
    }
    
    
    
    @JavascriptInterface
    public String getLegalStatus() {
    	String status = dbh.getSWInfo(DbHelper.CCH_SW_LEGAL_STATUS);
    	return (status==null) ? "" : status;
    }
    
    @JavascriptInterface
    public void setLegalStatus(String status) {
    	dbh.updateSWInfo(DbHelper.CCH_SW_LEGAL_STATUS, status);
    }
    
    @JavascriptInterface
    public String getProfileStatus() {
    	String profile = dbh.getSWInfo(DbHelper.CCH_SW_PROFILE_STATUS);
    	return (profile==null) ? "" : profile;
    }
    
    @JavascriptInterface
    public void setProfileStatus(String status) {
    	String[] responses = status.split(",");
    	
    	int acount = 0;
    	int bcount = 0;
    	int ccount = 0;
    	
    	for(int i=0; i < responses.length; i++) {
    		if (responses[i].contains("A")) { acount++; }
    		if (responses[i].contains("B")) { bcount++; }
    		if (responses[i].contains("C")) { ccount++; }
    	}
    	
    	String profile = "";
    	if (acount >= bcount && acount >= ccount) { profile = "naana"; }
    	else if (bcount > acount && bcount >= ccount) { profile = "mary"; }
    	else if (ccount > acount && ccount > bcount) { profile = "michael"; }

    	dbh.updateSWInfo(DbHelper.CCH_SW_PROFILE_STATUS, profile);
    	dbh.updateSWInfo(DbHelper.CCH_SW_PROFILE_RESPONSES, status);
    }
     
    @JavascriptInterface
    public String getMonthlyPlan() {
    	String plan = dbh.getSWInfo(DbHelper.CCH_SW_MONTH_PLAN);
    	return (plan==null) ? "" : plan;
    }
    
    @JavascriptInterface
    public void setMonthlyPlan(String plan) {
    	Time time = new Time();
		time.setToNow();
    	dbh.updateSWInfo(DbHelper.CCH_SW_MONTH_PLAN, plan);
    	dbh.updateSWInfo(DbHelper.CCH_SW_MONTH_PLAN_LASTUPDATE, String.valueOf(time.toMillis(true)));
    }
    
    @JavascriptInterface
    public String changeMonthlyPlan() {
    	
    	String lastupdate = dbh.getSWInfo(DbHelper.CCH_SW_MONTH_PLAN_LASTUPDATE);
    	if (lastupdate==null || lastupdate.equals("")) {
    			return "false";
    	} else {
    		Long lu = Long.parseLong(lastupdate);
    		
    		Time time = new Time();
    		time.setToNow();
    			
    		if ((time.toMillis(true) - lu) > thirtydays ) {
    				return "true";
    		} else {
    				return "false";
    		}
    	}
    }
     
    @JavascriptInterface
    public String getFileTemplate(String filename)
    {	
    	try {
    		InputStream input = mContext.getAssets().open(filename);
    		byte[] buffer = new byte[input.available()];
    		input.read(buffer);
    		return new String(buffer);
    	} catch (IOException e) {
    		Log.e("CCH","cch: no file found: "+filename);
    		return "";
    	}
    }
    
    
    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_LONG).show();
    }  
    
    private String getTime()
    {
    	 Time time = new Time();
		 time.setToNow();
		    
		 if (time.hour < 12)
		 {
			 return "morning";
		 } 
		 else if (time.hour >= 12 && time.hour <= 17)
		 {
		     return "afternoon";
		 }
		 else if (time.hour > 17 && time.hour < 23)
		 {
		     return "evening";
		 } 
		 else 
		 {
		      return "";
		 }
    }
}