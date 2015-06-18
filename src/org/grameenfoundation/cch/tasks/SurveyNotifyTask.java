package org.grameenfoundation.cch.tasks;


import java.util.ArrayList;

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

import android.app.NotificationManager;
import android.app.PendingIntent;
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
	ArrayList<Survey> surveyDateTaken;

	private SharedPreferences prefs;

	private String name;

	private ArrayList<Survey> surveys;
	public SurveyNotifyTask(Context ctx) {
		this.ctx = ctx;
		this.dbh = new DbHelper(ctx);
		timeUtil=new CCHTimeUtil();
		user_role=new ArrayList<User>();
		surveyDateTaken=new ArrayList<Survey>();
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
		try{
			user_role=dbh.getUserFirstName(name);
			surveys=dbh.getSurveys();
			if(surveys.size()<=0){
				
			}
			surveyDateTaken=dbh.getSurveyDetails(date.toString("dd-MM-yyyy"));
			LocalDate reminderDate=formatter2.parseLocalDate(surveyDateTaken.get(0).getSurveyReminderDate());
		// only notify if at certain hours and if there are tasks to do...
		if(user_role.get(0).getUserrole().equals("chn")||user_role.get(0).getUserrole().equals("Supervisor")){
			if(surveyDateTaken.get(0).getSurveyNextReminderDate().equals("")){
				if(reminderDate.toString("dd-MM-yyyy HH:mm").contains(date.toString("dd-MM-yyyy"))&&surveyDateTaken.get(0).getSurveyStatus().equals("")){
					String contMsg = "Complete this survey!";
					// Invoking the default notification service
					NotificationCompat.Builder mBuilder =
							new NotificationCompat.Builder(this.ctx)
									.setSmallIcon(R.drawable.app_icon)
									.setContentTitle("Survey Notification")
									.setContentText(contMsg)
									.setTicker(contMsg);
									Intent resultIntent = new Intent(this.ctx, RunForm.class);
									TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.ctx);
									stackBuilder.addParentStack(RunForm.class);
									stackBuilder.addNextIntent(resultIntent);
									PendingIntent resultPendingIntent =
											stackBuilder.getPendingIntent(
													0,
													PendingIntent.FLAG_UPDATE_CURRENT
													);
									mBuilder.setContentIntent(resultPendingIntent);
									NotificationManager mNotificationManager =
											(NotificationManager) ctx.getSystemService(ctx.NOTIFICATION_SERVICE);
									mBuilder.setAutoCancel(true);

									mNotificationManager.notify(2, mBuilder.build());
		 
				}
			
		}else{
			DateTime today=new DateTime();
			today=formatter2.parseDateTime(today.toString("dd-MM-yyyy HH:mm"));
			if(today.toString("dd-MM-yyyy HH:mm").equals(surveyDateTaken.get(0).getSurveyNextReminderDate())&&surveyDateTaken.get(0).getSurveyStatus().equals("")){
				String contMsg = "Complete this survey!";
				// Invoking the default notification service
				System.out.println(" Survey Notify Today "+today.toString("dd-MM-yyyy HH:mm"));
				System.out.println(" Survey Notify Today "+surveyDateTaken.get(0).getSurveyNextReminderDate());
				NotificationCompat.Builder mBuilder =
						new NotificationCompat.Builder(this.ctx)
								.setSmallIcon(R.drawable.app_icon)
								.setContentTitle("Survey Notification")
								.setContentText(contMsg)
								.setTicker(contMsg);
								Intent resultIntent = new Intent(this.ctx, RunForm.class);
								TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.ctx);
								stackBuilder.addParentStack(RunForm.class);
								stackBuilder.addNextIntent(resultIntent);
								PendingIntent resultPendingIntent =
										stackBuilder.getPendingIntent(
												0,
												PendingIntent.FLAG_UPDATE_CURRENT
												);
								mBuilder.setContentIntent(resultPendingIntent);
								NotificationManager mNotificationManager =
										(NotificationManager) ctx.getSystemService(ctx.NOTIFICATION_SERVICE);
								mBuilder.setAutoCancel(true);

								mNotificationManager.notify(2, mBuilder.build());
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
