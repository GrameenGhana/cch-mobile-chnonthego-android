package org.grameenfoundation.cch.popupquestions;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.model.Survey;
import org.joda.time.DateTime;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.WindowManager.LayoutParams;

public class SurveyAlertFragment extends DialogFragment{
	 private DbHelper dbh;
	 ArrayList<Survey> surveys;
	private DateTime today;

	@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	 
	        /** Turn Screen On and Unlock the keypad when this alert dialog is displayed */
	        getActivity().getWindow().addFlags(LayoutParams.FLAG_TURN_SCREEN_ON | LayoutParams.FLAG_DISMISS_KEYGUARD);
	        dbh = new DbHelper(getActivity());
	        surveys=new ArrayList<Survey>();
	        surveys=dbh.getSurveys();
	        today=new DateTime();
	        /** Creating a alert dialog builder */
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	 
	        /** Setting title for the alert dialog */
	        builder.setTitle("Satisfaction survey reminder");
	        builder.setIcon(getResources().getDrawable(R.drawable.app_icon));
	 
	        /** Setting the content for the alert dialog */
	        builder.setMessage("Click next to take the survey");
	        builder.setCancelable(false);
	        /** Defining an OK button event listener */
	        builder.setPositiveButton("Next", new OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                /** Exit application on click OK */
	              Intent intent=new Intent(getActivity(),RunForm.class);
	              startActivity(intent);
	            }
	        });
	        builder.setNegativeButton("Dismiss", new OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	                /** Exit application on click OK */
	            	if(today.getMillis()>=Long.valueOf(surveys.get(0).getSurveyReminderDate())
							&&today.getMillis()<=Long.valueOf(surveys.get(0).getSurveyNextReminderDate())
							&&surveys.get(0).getSurveyStatus().equals("")
							&&!surveys.get(0).getSurveyReminderFrequencyValue().equals("")){
	            		if(surveys.get(0).getSurveyReminderFrequency().equals("1 hour")){
	            		long newReminder=Long.valueOf(surveys.get(0).getSurveyReminderFrequencyValue())+AlarmManager.INTERVAL_HOUR;
	            		dbh.updateSurveyReminderValue(String.valueOf(newReminder), Integer.valueOf(surveys.get(0).getSurveyId()));
	            		}else if(surveys.get(0).getSurveyReminderFrequency().equals("6 hours")){
	            			long newReminder=Long.valueOf(surveys.get(0).getSurveyReminderFrequencyValue())+21600000;
		            		dbh.updateSurveyReminderValue(String.valueOf(newReminder), Integer.valueOf(surveys.get(0).getSurveyId()));	
	            		}else if(surveys.get(0).getSurveyReminderFrequency().equals("1 day")){
	            			long newReminder=Long.valueOf(surveys.get(0).getSurveyReminderFrequencyValue())+AlarmManager.INTERVAL_DAY;
		            		dbh.updateSurveyReminderValue(String.valueOf(newReminder), Integer.valueOf(surveys.get(0).getSurveyId()));
	            		}else if(surveys.get(0).getSurveyReminderFrequency().equals("1 week")){
	            			long newReminder=Long.valueOf(surveys.get(0).getSurveyReminderFrequencyValue())+604800000;
		            		dbh.updateSurveyReminderValue(String.valueOf(newReminder), Integer.valueOf(surveys.get(0).getSurveyId()));
	            		}
	            		  getActivity().finish();
	     	             dialog.dismiss();
	            	}else if(today.getMillis()>=Long.valueOf(surveys.get(1).getSurveyReminderDate())
							&&today.getMillis()<=Long.valueOf(surveys.get(1).getSurveyNextReminderDate())
							&&surveys.get(1).getSurveyStatus().equals("")
							&&!surveys.get(1).getSurveyReminderFrequencyValue().equals("")){
	            		if(surveys.get(1).getSurveyReminderFrequency().equals("1 hour")){
		            		long newReminder=Long.valueOf(surveys.get(1).getSurveyReminderFrequencyValue())+AlarmManager.INTERVAL_HOUR;
		            		dbh.updateSurveyReminderValue(String.valueOf(newReminder), Integer.valueOf(surveys.get(1).getSurveyId()));
		            		}else if(surveys.get(1).getSurveyReminderFrequency().equals("6 hours")){
		            			long newReminder=Long.valueOf(surveys.get(1).getSurveyReminderFrequencyValue())+21600000;
			            		dbh.updateSurveyReminderValue(String.valueOf(newReminder), Integer.valueOf(surveys.get(1).getSurveyId()));	
		            		}else if(surveys.get(1).getSurveyReminderFrequency().equals("1 day")){
		            			long newReminder=Long.valueOf(surveys.get(1).getSurveyReminderFrequencyValue())+AlarmManager.INTERVAL_DAY;
			            		dbh.updateSurveyReminderValue(String.valueOf(newReminder), Integer.valueOf(surveys.get(1).getSurveyId()));
		            		}else if(surveys.get(1).getSurveyReminderFrequency().equals("1 week")){
		            			long newReminder=Long.valueOf(surveys.get(1).getSurveyReminderFrequencyValue())+604800000;
			            		dbh.updateSurveyReminderValue(String.valueOf(newReminder), Integer.valueOf(surveys.get(1).getSurveyId()));
		            		}
	            		  getActivity().finish();
	     	             dialog.dismiss();
	            	}else if(today.getMillis()>=Long.valueOf(surveys.get(2).getSurveyReminderDate())
							&&today.getMillis()<=Long.valueOf(surveys.get(2).getSurveyNextReminderDate())
							&&surveys.get(2).getSurveyStatus().equals("")
							&&!surveys.get(2).getSurveyReminderFrequencyValue().equals("")){
	            		if(surveys.get(2).getSurveyReminderFrequency().equals("1 hour")){
		            		long newReminder=Long.valueOf(surveys.get(2).getSurveyReminderFrequencyValue())+AlarmManager.INTERVAL_HOUR;
		            		dbh.updateSurveyReminderValue(String.valueOf(newReminder), Integer.valueOf(surveys.get(2).getSurveyId()));
		            		}else if(surveys.get(2).getSurveyReminderFrequency().equals("6 hours")){
		            			long newReminder=Long.valueOf(surveys.get(2).getSurveyReminderFrequencyValue())+21600000;
			            		dbh.updateSurveyReminderValue(String.valueOf(newReminder), Integer.valueOf(surveys.get(2).getSurveyId()));	
		            		}else if(surveys.get(2).getSurveyReminderFrequency().equals("1 day")){
		            			long newReminder=Long.valueOf(surveys.get(2).getSurveyReminderFrequencyValue())+AlarmManager.INTERVAL_DAY;
			            		dbh.updateSurveyReminderValue(String.valueOf(newReminder), Integer.valueOf(surveys.get(2).getSurveyId()));
		            		}else if(surveys.get(2).getSurveyReminderFrequency().equals("1 week")){
		            			long newReminder=Long.valueOf(surveys.get(2).getSurveyReminderFrequencyValue())+604800000;
			            		dbh.updateSurveyReminderValue(String.valueOf(newReminder), Integer.valueOf(surveys.get(2).getSurveyId()));
		            		}
	            		  getActivity().finish();
	     	             dialog.dismiss();
	            	}
	           
	            }
	        });
	        /** Creating the alert dialog window */
	        return builder.create();
	    }
	 
	    /** The application should be exit, if the user presses the back button */
	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        getActivity().finish();
	    }
	}