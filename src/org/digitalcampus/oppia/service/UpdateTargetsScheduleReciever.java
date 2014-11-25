package org.digitalcampus.oppia.service;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;

public class UpdateTargetsScheduleReciever extends BroadcastReceiver {
	public static final String TAG = UpdateTargetsScheduleReciever.class.getSimpleName();
	Time time_now;
	Time compared_time;
	Time week;
	Time end_of_month;
	// Restart service every 1 hour
	private static final long REPEAT_TIME = 60000;
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "running onReceive update targets service");
		
		AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, UpdateStartServiceReceiver.class);		
		PendingIntent pending = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar updateTime = Calendar.getInstance();
	    updateTime.setTimeZone(TimeZone.getTimeZone("GMT"));
	    updateTime.set(Calendar.HOUR_OF_DAY, 17);
	    updateTime.set(Calendar.MINUTE, 0);
	    updateTime.set(Calendar.SECOND, 0);
		service.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pending);
	
	}

}
