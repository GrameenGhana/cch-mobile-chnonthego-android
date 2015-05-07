package org.digitalcampus.oppia.service;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.grameenfoundation.cch.utils.CCHTimeUtil;
import org.joda.time.DateTime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

public class UpdateStartServiceReceiver extends BroadcastReceiver {
		Time time_now;
		Time compared_time;
		Time week;
		Time end_of_month;
		private Time week_day;
		DateTime dt;
	@Override
	public void onReceive(Context context, Intent intent) {
		time_now=new Time();
		compared_time=new Time();
		week_day=new Time();
		time_now.setToNow();
		dt=new DateTime();
		

		
		compared_time.set(time_now.second, 0, 17, time_now.monthDay, time_now.month, time_now.year);
//		week.set(time_now.second, 0, 17, 5, time_now.month, time_now.year);
//		end_of_month.set(time_now.second, 0, 17, 31, time_now.month, time_now.year);
		week  = CCHTimeUtil.getLastDayOfWeek(new Date());
		end_of_month  = CCHTimeUtil.getLastFidayOfMonth(new Date());
		
		String today= String.valueOf(time_now.hour) +":"+String.valueOf(time_now.minute)+String.valueOf(time_now.second)
					+","+String.valueOf(time_now.monthDay)+"/"+String.valueOf(time_now.month)+"/"+String.valueOf(time_now.year);
		String compared_today= String.valueOf(compared_time.hour) +":"+String.valueOf(compared_time.minute)+String.valueOf(compared_time.second)
				+","+String.valueOf(compared_time.monthDay)+"/"+String.valueOf(compared_time.month)+"/"+String.valueOf(compared_time.year);
		
		
		String compared_week= String.valueOf(week.hour) +":"+String.valueOf(week.minute)+String.valueOf(week.second)
			+","+String.valueOf(week.monthDay)+"/"+String.valueOf(week.month)+"/"+String.valueOf(week.year);
		
		String compared_endofmonth= String.valueOf(end_of_month.hour) +":"+String.valueOf(end_of_month.minute)+String.valueOf(end_of_month.second)
				+","+String.valueOf(end_of_month.monthDay)+"/"+String.valueOf(end_of_month.month)+"/"+String.valueOf(end_of_month.year);
		if(today.equals(compared_today)){
			Intent service = new Intent(context, UpdateTargetsService.class);
			context.startService(service);
		}
		else if(today.equals(compared_week)){
			//Intent service = new Intent(context, UpdateTargetsWeeklyService.class);
			//context.startService(service);	
		}else if(today.equals(compared_endofmonth)){
			//Intent service = new Intent(context, UpdateMonthlyTargetService.class);
			//context.startService(service);
		}
	}

}
