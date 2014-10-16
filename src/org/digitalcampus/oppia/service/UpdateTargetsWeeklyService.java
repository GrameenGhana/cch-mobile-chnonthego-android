package org.digitalcampus.oppia.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.UpdateTargetActivity;
import org.digitalcampus.oppia.activity.UpdateWeeklyTargetsActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.service.UpdateTargetsService.MyBinder;

import com.bugsense.trace.BugSenseHandler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.format.Time;
import android.util.Log;

public class UpdateTargetsWeeklyService extends Service {

	public static final String TAG = UpdateTargetsWeeklyService.class.getSimpleName();
	private final IBinder mBinder = new MyBinder();
	private SharedPreferences prefs;
	private int mId;
	private String current_month;
	private DbHelper db;
	Time time_now;
	Time compared_time;
	Time week;
	Time end_of_month;
	private HashMap<String, String> eventUpdateItemsWeekly;
	private HashMap<String, String> coverageUpdateItemsWeekly;
	private HashMap<String, String> otherUpdateItemsWeekly;
	private ArrayList<String> eventId;
	private ArrayList<String> coverageId;
	private ArrayList<String> otherId;
	
	@Override
	public void onCreate() {
		super.onCreate();
		BugSenseHandler.initAndStartSession(this,MobileLearning.BUGSENSE_API_KEY);
		 db=new DbHelper(this);
		    Calendar c = Calendar.getInstance();
	        int month=c.get(Calendar.MONTH)+1;
	        switch(month){
	        case 1:
		        current_month="January";
		        break;
	        case 2:
	        	current_month="February";
	        	break;
	        case 3:
	        	current_month="March";
	        	break;
	        case 4:
	        	current_month="April";
	        	break;
	        case 5:
	        	current_month="May";
	        	break;
	        case 6:
	        	current_month="June";
	        	break;
	        case 7:
	        	current_month="July";
	        	break;
	        case 8:
	        	current_month="August";
	        	break;
	        case 9:
	        	current_month="September";
	        	break;
	        case 10:
	        	current_month="October";
	        	break;
	        case 11:
	        	current_month="November";
	        	break;
	        case 12:
	        	current_month="December";
	        	break;
	        }
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "Starting weekly target update Service");
	
		 eventUpdateItemsWeekly=db.getAllDailyEvents(current_month);
		 coverageUpdateItemsWeekly=db.getAllDailyCoverage(current_month);
		 otherUpdateItemsWeekly=db.getAllDailyOther(current_month);
		 eventId=new ArrayList<String>();
		 eventId.add(eventUpdateItemsWeekly.get("event_id"));
		
		 coverageId=new ArrayList<String>();
		 coverageId.add(coverageUpdateItemsWeekly.get("coverage_id"));
		 otherId=new ArrayList<String>();
		 otherId.add(otherUpdateItemsWeekly.get("other_id"));
		 int number=eventId.size();
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.app_icon)
		        .setContentTitle("CHN on the go")
		        .setContentText("You have "+String.valueOf(number)+" weekly targets to update.");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, UpdateWeeklyTargetsActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(UpdateWeeklyTargetsActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder.setAutoCancel(true);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(mId, mBuilder.build());
		
		return Service.START_NOT_STICKY;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public class MyBinder extends Binder {
		public UpdateTargetsWeeklyService getService() {
			return UpdateTargetsWeeklyService.this;
		}
	}
}