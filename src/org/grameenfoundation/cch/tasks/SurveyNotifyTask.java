package org.grameenfoundation.cch.tasks;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.model.User;
import org.digitalcampus.oppia.task.Payload;
import org.grameenfoundation.cch.model.Survey;
import org.grameenfoundation.cch.popupquestions.RunForm;
import org.grameenfoundation.cch.utils.CCHTimeUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.format.Time;


public class SurveyNotifyTask extends AsyncTask<Payload, Object, Payload> {

	public final static String TAG = SurveyNotifyTask.class.getSimpleName();

	private Context ctx;
	private DbHelper dbh;

	private LocalDate date;

	CCHTimeUtil timeUtil;
	ArrayList<User> user_role;

	private SharedPreferences prefs;

	private String name;

	private ArrayList<Survey> surveys;

	private List<String> userDistricts;
	public SurveyNotifyTask(Context ctx) {
		this.ctx = ctx;
		this.dbh = new DbHelper(ctx);
		timeUtil=new CCHTimeUtil();
		user_role=new ArrayList<User>();
		surveys=new ArrayList<Survey>();
		 prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
	        name=prefs.getString("first_name", "name");
	}

	@Override
	protected Payload doInBackground(Payload... params) {

		Payload payload = new Payload();
		
		Time time = new Time();
		time.setToNow();
		date=new LocalDate();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
		DateTimeFormatter formatter2 = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm");
		DateTime today=new DateTime();
		try{
			user_role=dbh.getUserFirstName(name);
			surveys=dbh.getSurveys();
			userDistricts = Arrays.asList(ctx.getResources().getStringArray(R.array.Districts));
			if(surveys.size()<=0){
				
			}
		// only notify if at certain hours and if there are tasks to do...
		if((user_role.get(0).getUserrole().equals("chn")
				||user_role.get(0).getUserrole().equals("Sub-District Supervisor")
				||user_role.get(0).getUserrole().equalsIgnoreCase("District Supervisor"))
				&&userDistricts.contains(user_role.get(0).getUserDistrict())){
			DateTime reminder;
			DateTime now = new DateTime();
			if(today.getMillis()>=Long.valueOf(surveys.get(0).getSurveyReminderDate())
					&&today.getMillis()<=Long.valueOf(surveys.get(0).getSurveyNextReminderDate())
					&&surveys.get(0).getSurveyStatus().equals("")
					&&!surveys.get(0).getSurveyReminderFrequencyValue().equals("")){
				reminder=new DateTime(Long.valueOf(surveys.get(0).getSurveyReminderFrequencyValue()));
				if(now.getHourOfDay()==reminder.getHourOfDay()
						&&now.getMinuteOfDay()==reminder.getMinuteOfDay()
						&&now.getDayOfWeek()==reminder.getDayOfWeek()
						&&now.getMonthOfYear()==reminder.getMonthOfYear()
						&&now.getYear()==reminder.getYear()){
					 Intent i = new Intent("org.grameenfoundation.cch.popupquestions.SurveyAlertActivity");
					 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					 ctx.startActivity(i);
				}
					
			
		}/*else if(today.getMillis()>=Long.valueOf(surveys.get(1).getSurveyReminderDate())
				&&today.getMillis()<=Long.valueOf(surveys.get(1).getSurveyNextReminderDate())
				&&surveys.get(1).getSurveyStatus().equals("")
				&&!surveys.get(1).getSurveyReminderFrequencyValue().equals("")){
			reminder=new DateTime(Long.valueOf(surveys.get(1).getSurveyReminderFrequencyValue()));
			if(now.getHourOfDay()==reminder.getHourOfDay()
					&&now.getMinuteOfDay()==reminder.getMinuteOfDay()
					&&now.getDayOfMonth()==reminder.getDayOfMonth()
					&&now.getMonthOfYear()==reminder.getMonthOfYear()
					&&now.getYear()==reminder.getYear())
				{
			System.out.println(String.valueOf(now.getDayOfMonth())+"/"+String.valueOf(now.getMonthOfYear())+"/"+String.valueOf(now.getYear()));
			System.out.println(String.valueOf(reminder.getDayOfMonth())+"/"+String.valueOf(reminder.getMonthOfYear())+"/"+String.valueOf(reminder.getYear()));
			 Intent i = new Intent("org.grameenfoundation.cch.popupquestions.SurveyAlertActivity");
			 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 ctx.startActivity(i);
			}
		}*/else if(today.getMillis()>=Long.valueOf(surveys.get(2).getSurveyReminderDate())
				&&today.getMillis()<=Long.valueOf(surveys.get(2).getSurveyNextReminderDate())
				&&surveys.get(2).getSurveyStatus().equals("")
				&&!surveys.get(2).getSurveyReminderFrequencyValue().equals("")){
			reminder=new DateTime(Long.valueOf(surveys.get(2).getSurveyReminderFrequencyValue()));
			if(now.getHourOfDay()==reminder.getHourOfDay()
					&&now.getMinuteOfDay()==reminder.getMinuteOfDay()
					&&now.getDayOfMonth()==reminder.getDayOfMonth()
					&&now.getMonthOfYear()==reminder.getMonthOfYear()
					&&now.getYear()==reminder.getYear()){
				 Intent i = new Intent("org.grameenfoundation.cch.popupquestions.SurveyAlertActivity");
				 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				 ctx.startActivity(i);
			}
				}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return payload;
	}

	protected void onProgressUpdate(String... obj) {}
	
	@Override
    protected void onPostExecute(Payload p) {
		// reset submit task back to null after completion - so next call can run properly
		MobileLearning app = (MobileLearning) ctx.getApplicationContext();
		app.omSurveyNotifyTask = null;
    }
}
