package org.digitalcampus.oppia.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.service.UpdateMonthlyTargetService.CoveragePeriods;
import org.digitalcampus.oppia.service.UpdateMonthlyTargetService.MyBinder;
import org.grameenfoundation.cch.activity.UpdateMonthlyTargetsActivity;

import com.bugsense.trace.BugSenseHandler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.format.Time;
import android.util.Log;

public class UpdateStartWeeklyServiceReceiver extends Service {
	public static final String TAG = UpdateTargetsService.class.getSimpleName();
	private final IBinder mBinder = new MyBinder();
	private SharedPreferences prefs;
	private int mId;
	private HashMap<String, String> eventUpdateItemsMonthly;
	private HashMap<String, String> coverageUpdateItemsMonthly;
	private HashMap<String, String> otherUpdateItemsMonthly;
	private ArrayList<String> eventId;
	private ArrayList<String> coverageId;
	private ArrayList<String> otherId;
	private DbHelper db;
	private ArrayList<String> learningId;
	
	@Override
	public void onCreate() {
		super.onCreate();
		BugSenseHandler.initAndStartSession(this,MobileLearning.BUGSENSE_API_KEY);
		 db=new DbHelper(this);
        
       
	}

	enum Periods{
		Daily,
		Weekly,
		Monthly,
		
		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "Starting monthly target update Service");
		 long numberOfEvents=0l;
		 try {
			 numberOfEvents =db.getCoverageCount(Periods.Weekly.name())+db.getLearningCount(Periods.Weekly.name())+db.getEventCount(Periods.Weekly.name());
		} catch (Exception e) {
			// TODO: handle exception
		}
	System.out.println(numberOfEvents);
	if(numberOfEvents>0){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.app_icon)
		        .setContentTitle("CHN on the go")
		        .setContentText("You have "+String.valueOf(numberOfEvents)+" weekly target(s) to update.");
		Intent resultIntent = new Intent(this, UpdateMonthlyTargetsActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(UpdateMonthlyTargetsActivity.class);
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
		mNotificationManager.notify(mId, mBuilder.build());
	}
		return Service.START_NOT_STICKY;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public class MyBinder extends Binder {
		public UpdateStartWeeklyServiceReceiver getService() {
			return UpdateStartWeeklyServiceReceiver.this;
		}
	}
}
