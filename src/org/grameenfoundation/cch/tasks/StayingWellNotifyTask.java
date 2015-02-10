package org.grameenfoundation.cch.tasks;


import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.task.Payload;
import org.grameenfoundation.cch.model.RoutineActivity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.format.Time;


public class StayingWellNotifyTask extends AsyncTask<Payload, Object, Payload> {

	public final static String TAG = StayingWellNotifyTask.class.getSimpleName();

	private Context ctx;
	private DbHelper dbh;
    private int numMessages = 0;
    private String fragmentIdx = "2"; // Staying well routines

    private NotificationManager myNotificationManager;


	public StayingWellNotifyTask(Context ctx) {
		this.ctx = ctx;
		this.dbh = new DbHelper(ctx);
	}

	@Override
	protected Payload doInBackground(Payload... params) {

		Payload payload = new Payload();
		
		Time time = new Time();
		time.setToNow();
		
		ArrayList<RoutineActivity> todos = this.dbh.getSWRoutineActivities();
		
		int todoCount = todos.size();
		for (RoutineActivity ra: todos) {	if (ra.isDone()) { todoCount--; } }
		
		// only notify if at certain hours and if there are tasks to do...
		if ((time.hour == 6 || time.hour==12 || time.hour==17) && todoCount > 0)
		{
			String contMsg = "This is a reminder for your " + this.dbh.getTime()
						 + " routines for this month. Please click here to go to your " 
						 + this.dbh.getTime() + " routines";
		
		    // Invoking the default notification service
			NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this.ctx);	
		 
		    mBuilder.setContentTitle("CCH Routine Reminder");
		    mBuilder.setContentText(contMsg);
		    mBuilder.setTicker("CHN On the Go: Time to do your "+this.dbh.getTime()+" routines!");
		    mBuilder.setSmallIcon(R.drawable.app_icon);
	
		    // Increase notification number every time a new notification arrives 
		    mBuilder.setNumber(++numMessages);
		      
		    // Creates an explicit intent for an Activity in your app 
		    Intent resultIntent = new Intent(this.ctx, MainScreenActivity.class);
		    resultIntent.putExtra("FRAGMENT_IDX", fragmentIdx);
	
		    //This ensures that navigating backward from the Activity leads out of the app to Home page
		    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.ctx);
	
		    // Adds the back stack for the Intent
		    stackBuilder.addParentStack(MainScreenActivity.class);
	
		    // Adds the Intent that starts the Activity to the top of the stack
		    stackBuilder.addNextIntent(resultIntent);
		    PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_ONE_SHOT);
		    mBuilder.setContentIntent(resultPendingIntent);
	
		     myNotificationManager = (NotificationManager) this.ctx.getSystemService(Context.NOTIFICATION_SERVICE);
	
		     // pass the Notification object to the system 
		     myNotificationManager.notify(2, mBuilder.build());
		}
		
		return payload;
	}

	protected void onProgressUpdate(String... obj) {}
	
	@Override
    protected void onPostExecute(Payload p) {
		// reset submit task back to null after completion - so next call can run properly
		MobileLearning app = (MobileLearning) ctx.getApplicationContext();
		app.omStayingWellNotifyTask = null;
    }
}
