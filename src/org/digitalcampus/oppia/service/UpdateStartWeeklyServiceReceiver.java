package org.digitalcampus.oppia.service;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

public class UpdateStartWeeklyServiceReceiver extends BroadcastReceiver {
		Time time_now;
		Time compared_time;
		Time week;
		Time end_of_month;
		private Time week_day;
	@Override
	public void onReceive(Context context, Intent intent) {

		Intent service = new Intent(context, UpdateTargetsWeeklyService.class);
		context.startService(service);
		
		/*else if(today.equals(compared_week)){
			Intent service = new Intent(context, UpdateTargetsWeeklyService.class);
			context.startService(service);	
		}else if(today.equals(compared_endofmonth)){
			Intent service = new Intent(context, UpdateMonthlyTargetService.class);
			context.startService(service);
		}*/
	}

}
