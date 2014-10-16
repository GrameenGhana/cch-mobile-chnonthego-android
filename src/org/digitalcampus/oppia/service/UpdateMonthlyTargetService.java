package org.digitalcampus.oppia.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.UpdateMonthlyTargetsActivity;
import org.digitalcampus.oppia.activity.UpdateWeeklyTargetsActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.service.UpdateTargetsWeeklyService.MyBinder;

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
import android.util.Log;

public class UpdateMonthlyTargetService extends Service {
	public static final String TAG = UpdateTargetsService.class.getSimpleName();
	private final IBinder mBinder = new MyBinder();
	private SharedPreferences prefs;
	private int mId;
	private String current_month;
	private HashMap<String, String> eventUpdateItemsMonthly;
	private HashMap<String, String> coverageUpdateItemsMonthly;
	private HashMap<String, String> otherUpdateItemsMonthly;
	private ArrayList<String> eventId;
	private ArrayList<String> coverageId;
	private ArrayList<String> otherId;
	private DbHelper db;
	
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
		Log.v(TAG, "Starting monthly target update Service");
		eventUpdateItemsMonthly=db.getAllDailyEvents(current_month);
		 coverageUpdateItemsMonthly=db.getAllDailyCoverage(current_month);
		 otherUpdateItemsMonthly=db.getAllDailyOther(current_month);
		 eventId=new ArrayList<String>();
		 eventId.add(eventUpdateItemsMonthly.get("event_id"));
		 coverageId=new ArrayList<String>();
		 coverageId.add(coverageUpdateItemsMonthly.get("coverage_id"));
		 otherId=new ArrayList<String>();
		 otherId.add(otherUpdateItemsMonthly.get("other_id"));
		 int number=eventUpdateItemsMonthly.get("event_id").length();
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.app_icon)
		        .setContentTitle("CHN on the go")
		        .setContentText("You have "+String.valueOf(number)+" monthly targets to update.");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, UpdateMonthlyTargetsActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(UpdateMonthlyTargetsActivity.class);
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
		public UpdateMonthlyTargetService getService() {
			return UpdateMonthlyTargetService.this;
		}
	}
}
