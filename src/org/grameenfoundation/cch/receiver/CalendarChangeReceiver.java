package org.grameenfoundation.cch.receiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.activity.ReferencesDownloadActivity;
import org.json.JSONException;
import org.json.JSONObject;









import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Instances;
import android.text.format.DateUtils;
import android.util.Log;

public class CalendarChangeReceiver extends BroadcastReceiver {

	public static final String TAG = CalendarChangeReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		 Log.d(TAG, "Noting change in calendar");
		
		DbHelper dbh = new DbHelper(context);
		
		// Get latest calendar changes
		final String SELECTION = "("
	            + CalendarContract.Events.DIRTY + "=" + 1 + " OR "
	            + CalendarContract.Events.DELETED + "=" + 1 + ")" + " AND "
	            + CalendarContract.Events.DTEND + " > "
	            + (Calendar.getInstance().getTimeInMillis() - (5 * 1000));
		
		Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[] { "title", "description", "dtstart", "dtend", "eventLocation",  
                        				CalendarContract.Events.DIRTY, 
                        				CalendarContract.Events.DELETED,
                        				Events._ID,
                        				Events.AVAILABILITY}, SELECTION, null, null);
        cursor.moveToFirst();
        
        String CNames[] = new String[cursor.getCount()];

		String module = "Calendar";
        Calendar c = Calendar.getInstance();

        for (int i = 0; i < CNames.length; i++) {

     	     String start = cursor.getString(2);
     	     String end = String.valueOf(c.getTimeInMillis());
             
     	     try {
     		    end = String.valueOf(cursor.getString(3));
     	     } catch(NumberFormatException e) {}
     	   
     	     JSONObject json = new JSONObject();
			 try {
				 json.put("eventtype", cursor.getString(0));
				 json.put("description", cursor.getString(1));
				 json.put("location", cursor.getString(4));
				 json.put("changed", cursor.getInt(5));
				 json.put("deleted", cursor.getInt(6));
				 json.put("eventid", cursor.getString(7));
				 json.put("ver", dbh.getVersionNumber(context));
				 json.put("battery", dbh.getBatteryStatus(context));
				 json.put("device", dbh.getDeviceName());
				 json.put("imei", dbh.getDeviceImei(context));
				 if(Integer.valueOf(cursor.getString(8))==Events.AVAILABILITY_BUSY){
					 json.put("category","personal");
				 }else if(Integer.valueOf(cursor.getString(8))==Events.AVAILABILITY_FREE){
					 json.put("category","not_personal");
				 }
			} catch (JSONException e) {
				e.printStackTrace();
			}
			 
			 Log.v(TAG,"Saving: "+json.toString());
			 
			 dbh.insertCCHLog(module, json.toString(), start, end);	
             cursor.moveToNext();
        }  	
        
        cursor.close();
	}
}