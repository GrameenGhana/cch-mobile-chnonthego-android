package org.grameenfoundation.cch.tasks;


import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.task.Payload;
import org.grameenfoundation.cch.activity.UpdateTargetActivity;
import org.grameenfoundation.cch.model.RoutineActivity;
import org.grameenfoundation.cch.popupquestions.RunForm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.format.Time;


public class TargetSettingNotifyTask extends AsyncTask<Payload, Object, Payload> {

	public final static String TAG = TargetSettingNotifyTask.class.getSimpleName();

	private Context ctx;
	private DbHelper dbh;
    private int numMessages = 0;
    private String fragmentIdx = "2"; // Staying well routines

    private NotificationManager myNotificationManager;

	private int targetCount;


	public TargetSettingNotifyTask(Context ctx) {
		this.ctx = ctx;
		this.dbh = new DbHelper(ctx);
	}

	@Override
	protected Payload doInBackground(Payload... params) {

		Payload payload = new Payload();
		
		Time time = new Time();
		time.setToNow();
		try{
			targetCount = dbh.getDailyTargetsToUpdate()+dbh.getWeeklyTargetsToUpdate()+dbh.getMonthlyTargetsToUpdate()+dbh.getMidYearTargetsToUpdate()+dbh.getAnnualTargetsToUpdate()+dbh.getQuarterlylyTargetsToUpdate();
		
		// only notify if at certain hours and if there are tasks to do...
		if ((time.hour == 17) && targetCount > 0)
		{
			String contMsg = "You have "+String.valueOf(targetCount)+" targets to update.";
		
		    // Invoking the default notification service
			NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(this.ctx)
			        .setSmallIcon(R.drawable.app_icon)
			        .setContentTitle("Targets Reminder")
			        .setContentText(contMsg)
			        .setTicker(contMsg);;
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
	
		     // pass the Notification object to the system 
		     myNotificationManager.notify(2, mBuilder.build());
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
		app.omTargetSettingNotifyTask = null;
    }
}
