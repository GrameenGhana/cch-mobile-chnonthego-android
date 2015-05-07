package org.digitalcampus.oppia.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.service.TrackerService.MyBinder;
import org.digitalcampus.oppia.task.APIRequestTask;
import org.digitalcampus.oppia.task.Payload;
import org.digitalcampus.oppia.task.SubmitQuizTask;
import org.digitalcampus.oppia.task.SubmitTrackerMultipleTask;
import org.grameenfoundation.cch.activity.UpdateTargetActivity;
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
	private DbHelper db;
	private long eventId;
	private long coverageId;
	private long otherId;
	private long learningId;
	
	@Override
	public void onCreate() {
		super.onCreate();
		BugSenseHandler.initAndStartSession(this,MobileLearning.BUGSENSE_API_KEY);
		 db=new DbHelper(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "Starting target update Service");
		 Time time = new Time();
		    time.setToNow();
		    eventId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_EVENT);
			 coverageId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_COVERAGE);
			 otherId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_OTHER);
			 learningId=db.getCount("Daily",MobileLearning.CCH_TARGET_TYPE_LEARNING);
			 int number=(int)eventId;
			 int number2=(int)coverageId;
			 int number3=(int)otherId;
			 int number4=(int)learningId;
			 final int counter;
			
			counter=number+number2+number3+number4;
		System.out.println(counter);
		if(counter>0){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.app_icon)
		        .setContentTitle("CHN on the go")
		        .setContentText("You have "+String.valueOf(counter) +" target(s) to update.");
		Intent resultIntent = new Intent(this, UpdateTargetActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(UpdateTargetActivity.class);
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
		//}
		}
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
