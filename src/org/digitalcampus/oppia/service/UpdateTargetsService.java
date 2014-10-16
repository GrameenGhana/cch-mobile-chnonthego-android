package org.digitalcampus.oppia.service;

import java.util.Calendar;

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
	
	@Override
	public void onCreate() {
		super.onCreate();
		BugSenseHandler.initAndStartSession(this,MobileLearning.BUGSENSE_API_KEY);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.v(TAG, "Starting target update Service");
		time_now=new Time();
		time_now.setToNow();
		compared_time=new Time();
		compared_time.set(0, 0, 17, Time.MONTH_DAY, Time.MONTH, Time.YEAR);
		week=new Time();
		week.set(0, 0, 17, Time.FRIDAY, Time.MONTH, Time.YEAR);
		end_of_month=new Time();
		end_of_month.set(0, 0, 17, 31, Time.MONTH, Time.YEAR);
		//if(time_now.equals(compared_time)){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.app_icon)
		        .setContentTitle("CHN on the go")
		        .setContentText("You have targets to update.");
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
