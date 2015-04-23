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
import android.widget.TextView;

public class ClientSeenAtHealthFacilityActivity extends BaseActivity {

	private TextView clickHere;
	private TextView clickHereToo;
	private TextView clickHereThree;
	private TextView clickHereFour;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private JSONObject json;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_seen_at_healthcare_facility);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic: Client Seen at Health Facility");
	    mContext=ClientSeenAtHealthFacilityActivity.this;
	    dbh=new DbHelper(ClientSeenAtHealthFacilityActivity.this);
	    start_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", "ANC Diagnostic: Client Seen at Health Facility");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    clickHere=(TextView) findViewById(R.id.textView_clickHere);
	    clickHereToo=(TextView) findViewById(R.id.textView_clickHereToo);
	    clickHereThree=(TextView) findViewById(R.id.textView_clickHereThree);
	    clickHereFour=(TextView) findViewById(R.id.textView_clickHereFour);
	    
	    clickHere.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ClientSeenAtHealthFacilityActivity.this,ANCCounsellingTopicsGenerlActivity.class);
				intent.putExtra("value", "tt_immunisation");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    
	    clickHereToo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ClientSeenAtHealthFacilityActivity.this,ANCCounsellingTopicsMenuActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	   
	    });
	    
	    clickHereThree.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ClientSeenAtHealthFacilityActivity.this,ANCCounsellingTopicsMenuActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	   
	    });
	    clickHereFour.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ClientSeenAtHealthFacilityActivity.this,ANCCounsellingTopicsMenuActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	   
	    });
	}
	public void onBackPressed()
	{
		end_time=System.currentTimeMillis();
		//dbh.insertCCHLog("Point of Care", "ANC Diagnostic: Client Seen at Health Facility", start_time.toString(), end_time.toString());
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
