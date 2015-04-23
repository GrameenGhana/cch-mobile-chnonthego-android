package org.grameenfoundation.schedulers;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.activity.EventUpdateActivity;
import org.grameenfoundation.cch.activity.UpdateMonthlyTargetsActivity;
import org.grameenfoundation.cch.model.MyCalendarEvents;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class EventUpdateService extends Service {
	 
	private CalendarEvents c;
	private Context mContext;
	private ArrayList<MyCalendarEvents> TodayCalendarEvents;
	private int mId;
	public EventUpdateService(){
		
	}
	public EventUpdateService(Context c){
		this.mContext=c;
		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		c= new CalendarEvents(this);
	    TodayCalendarEvents=new ArrayList<MyCalendarEvents>();
	    try{
	    	TodayCalendarEvents=c.readEventsToUpdate(this, false);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    if(TodayCalendarEvents.size()>0){
	    	// Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
	    	  //  vibrator.vibrate(2000);
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.app_icon_notify)
		        .setContentTitle("CHN on the go")
		        .setContentText("You have "+String.valueOf(TodayCalendarEvents.size())+" event(s) to update.");
		Intent resultIntent = new Intent(this, EventUpdateActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(EventUpdateActivity.class);
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
		return null;	
	}
	
	
	
	

}
