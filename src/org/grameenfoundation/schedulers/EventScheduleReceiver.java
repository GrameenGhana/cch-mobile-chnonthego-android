package org.grameenfoundation.schedulers;

import java.util.Calendar;
import java.util.TimeZone;

import org.digitalcampus.oppia.service.UpdateStartServiceReceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EventScheduleReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, EventUpdateStartServiceReceiver.class);		
		PendingIntent pending = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
		Calendar updateTime = Calendar.getInstance();
	    updateTime.setTimeZone(TimeZone.getTimeZone("GMT"));
	    updateTime.set(Calendar.HOUR_OF_DAY, 17);
	    updateTime.set(Calendar.MINUTE, 30);
	    updateTime.set(Calendar.SECOND, 0);
		service.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pending);
	}

}
