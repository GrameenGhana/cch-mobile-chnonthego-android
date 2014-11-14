package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class TakeActionMaternalEmergenciesActivity extends Activity {

	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private String take_action_category;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Maternal Emergencies");
	    dbh=new DbHelper(TakeActionMaternalEmergenciesActivity.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        if(take_action_category.equalsIgnoreCase("difficulty_breathing")){
        setContentView(R.layout.activity_mng_danger_sign_cyanosis);															
        }else if(take_action_category.equalsIgnoreCase("shock")){
            setContentView(R.layout.activity_mng_danger_sign_shock);// Same as the ANC content															
         }else if(take_action_category.equalsIgnoreCase("heavy_bleeding")){
             setContentView(R.layout.activity_mng_danger_sign_heavy_bleeding);// Same as the ANC content															
             }else if(take_action_category.equalsIgnoreCase("convulsion")){
                 setContentView(R.layout.activity_convulsion_pnc);														
                 }
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic:  Maternal Emergencies", start_time.toString(), end_time.toString());
		finish();
	}
}
