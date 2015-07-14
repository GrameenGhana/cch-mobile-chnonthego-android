package org.grameenfoundation.cch.popupquestions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SurveyAlertActivity extends FragmentActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	  try{
		  SurveyAlertFragment alert=new SurveyAlertFragment();
		  alert.show(getSupportFragmentManager(), "Alert");
		  
	  }catch(Exception e){
		  e.printStackTrace();
	  }
	}

}
