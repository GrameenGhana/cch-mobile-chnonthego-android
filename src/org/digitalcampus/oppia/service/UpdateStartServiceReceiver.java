package org.digitalcampus.oppia.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

public class UpdateStartServiceReceiver extends BroadcastReceiver {
		Time time_now;
		Time compared_time;
		Time week;
		Time end_of_month;
	@Override
	public void onReceive(Context context, Intent intent) {
		time_now.setToNow();
		compared_time.set(0, 0, 17, Time.MONTH_DAY, Time.MONTH, Time.YEAR);
		week.set(0, 0, 17, Time.FRIDAY, Time.MONTH, Time.YEAR);
		end_of_month.set(0, 0, 17, 31, Time.MONTH, Time.YEAR);
		if(time_now.equals(compared_time)){
		Intent service = new Intent(context, UpdateTargetsService.class);
		context.startService(service);
		}else if(time_now.equals(week)){
			Intent service = new Intent(context, UpdateTargetsWeeklyService.class);
			context.startService(service);	
		}else if(time_now.equals(end_of_month)){
			Intent service = new Intent(context, UpdateMonthlyTargetService.class);
			context.startService(service);
		}
	}

}
