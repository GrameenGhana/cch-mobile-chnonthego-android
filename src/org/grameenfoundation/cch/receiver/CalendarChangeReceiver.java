package org.grameenfoundation.cch.receiver;

import java.util.Calendar;
import java.util.Date;

import org.digitalcampus.oppia.application.DbHelper;
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
import android.provider.CalendarContract.Instances;
import android.text.format.DateUtils;
import android.util.Log;

public class CalendarChangeReceiver extends BroadcastReceiver {

	public static final String TAG = CalendarChangeReceiver.class.getSimpleName();

	@Override
	public void onReceive(Context context, Intent intent) {
		// Log.d(TAG, "Noting change in calendar");
		
		DbHelper dbh = new DbHelper(context);
		/*String _id = null;
    	ContentResolver contentResolver = context.getContentResolver();
    	Cursor cursor = contentResolver.query(Uri.parse("content://com.android.calendar/events"),
    	(new String[] { "_id"}), null, null, null);

    	while (cursor.moveToNext()) {

    	_id = cursor.getString(0);
    	

    	}
    	*/
		// Get latest calendar changes
		final String SELECTION = "("
	            + CalendarContract.Events.DIRTY + "=" + 1 + " OR "
	            + CalendarContract.Events.DELETED + "=" + 1 + ")" + " AND "
	            + CalendarContract.Instances.DTEND + " > "
	            + (Calendar.getInstance().getTimeInMillis() - (5 * 1000));
		
		Cursor cursor1	 = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[] { "title", "description", "dtstart", "dtend", "eventLocation",  
                        				CalendarContract.Events.DIRTY, 
                        				CalendarContract.Events.DELETED  }, SELECTION, null, null);
         /*               				
		Uri.Builder builder = Instances.CONTENT_URI.buildUpon();
		long now = new Date().getTime();
		ContentUris.appendId(builder, now - DateUtils.WEEK_IN_MILLIS);
		ContentUris.appendId(builder, now + DateUtils.WEEK_IN_MILLIS);
	Cursor cursor1 = context.getContentResolver()
            .query(builder.build(),
                        new String[] { Instances.TITLE,Instances.DESCRIPTION, Instances.DTSTART, Instances.DTEND,Instances.EVENT_LOCATION,  
                        				CalendarContract.Events.DIRTY, 
                        				CalendarContract.Events.DELETED  }, SELECTION, null, null);
                        				*/
        cursor1.moveToFirst();
        
        String CNames[] = new String[cursor1.getCount()];

		String module = "Calendar";
        Calendar c = Calendar.getInstance();

        for (int i = 0; i < CNames.length; i++) {

     	     String start = cursor1.getString(2);
     	     String end = String.valueOf(c.getTimeInMillis());
             
     	     try {
     		    end = String.valueOf(cursor1.getString(3));
     	     } catch(NumberFormatException e) {}
     	   
     	     JSONObject json = new JSONObject();
			 try {
				json.put("eventtype", cursor1.getString(0));
				 json.put("description", cursor1.getString(1));
				 json.put("location", cursor1.getString(4));
				 json.put("changed", cursor1.getInt(5));
				 json.put("deleted", cursor1.getInt(6));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			 
			 //Log.v(TAG,"Saving: "+json.toString());
			 
			 dbh.insertCCHLog(module, json.toString(), start, end);	
             cursor1.moveToNext();
        }  	
        
        cursor1.close();
	}
}
