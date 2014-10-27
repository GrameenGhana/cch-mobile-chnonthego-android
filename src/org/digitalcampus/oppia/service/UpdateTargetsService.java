package org.digitalcampus.oppia.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.UpdateTargetActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.service.TrackerService.MyBinder;
import org.digitalcampus.oppia.task.APIRequestTask;
import org.digitalcampus.oppia.task.Payload;
import org.digitalcampus.oppia.task.SubmitQuizTask;
import org.digitalcampus.oppia.task.SubmitTrackerMultipleTask;
import org.grameenfoundation.cch.tasks.UpdateCCHLogTask;

import com.bugsense.trace.BugSenseHandler;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class UpdateTargetsService extends Service {

	public static final String TAG = UpdateTargetsService.class.getSimpleName();
	private final IBinder mBinder = new MyBinder();
	private SharedPreferences prefs;
	private int mId;
	Time time_now;
	Time compared_time;
	Time week;
	Time end_of_month;
	private String current_month;
	private DbHelper db;
	private HashMap<String, String> eventUpdateItemsDaily;
	private HashMap<String, String> coverageUpdateItemsDaily;
	private HashMap<String, String> otherUpdateItemsDaily;
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
		Log.v(TAG, "Starting target update Service");
		//if(time_now.equals(compared_time)){
		 eventUpdateItemsDaily=db.getAllDailyEvents(current_month);
		 coverageUpdateItemsDaily=db.getAllDailyCoverage(current_month);
		 otherUpdateItemsDaily=db.getAllDailyOther(current_month);
		 eventId=new ArrayList<String>();
		 eventId.add(eventUpdateItemsDaily.get("event_id"));
		 coverageId=new ArrayList<String>();
		 coverageId.add(coverageUpdateItemsDaily.get("coverage_id"));
		 otherId=new ArrayList<String>();
		 otherId.add(otherUpdateItemsDaily.get("other_id"));
		 int number=eventId.size();
		 
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.app_icon)
		        .setContentTitle("CHN on the go")
		        .setContentText("You have "+String.valueOf(number) +" daily targets to update.");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, UpdateTargetActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(UpdateTargetActivity.class);
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
		// mId allows you to update the notification later on.
		mBuilder.setAutoCancel(true);
		mNotificationManager.notify(mId, mBuilder.build());
		//}
		return Service.START_NOT_STICKY;
		
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public class MyBinder extends Binder {
		public UpdateTargetsService getService() {
			return UpdateTargetsService.this;
		}
	}
}
