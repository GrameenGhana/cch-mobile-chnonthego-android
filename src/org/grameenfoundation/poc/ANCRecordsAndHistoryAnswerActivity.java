package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ANCRecordsAndHistoryAnswerActivity extends BaseActivity {

	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private String take_action_category;
	private TextView textView_clickHere;
	private JSONObject json;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic: Records & History");
	    mContext=ANCRecordsAndHistoryAnswerActivity.this;
	    dbh=new DbHelper(ANCRecordsAndHistoryAnswerActivity.this);
	    start_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", "ANC Diagnostic: Records & History");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
        if (extras != null) {
          take_action_category= extras.getString("value");
        }
        
        if(take_action_category.equals("yes")){
        	setContentView(R.layout.activity_ask_records_yes);
        	Button next=(Button) findViewById(R.id.button_next);
        	next.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(ANCRecordsAndHistoryAnswerActivity.this,ANCRecordsAskYesNextActivity.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
        		
        	});
        }else if(take_action_category.equals("no")){
        	setContentView(R.layout.activity_ask_records_no);
        	textView_clickHere=(TextView) findViewById(R.id.textView_clickHere);
        	textView_clickHere.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(ANCRecordsAndHistoryAnswerActivity.this,EstimateTrimester.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					
				}
        		
        	});
        }
	}
	public void onBackPressed()
	{
		end_time=System.currentTimeMillis();
		//dbh.insertCCHLog("Point of Care", "ANC Diagnostic: Records & History", start_time.toString(), end_time.toString());
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
