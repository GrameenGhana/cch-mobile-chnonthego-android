package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

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

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_seen_at_healthcare_facility);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic: Client Seen at Health Facility");
	    dbh=new DbHelper(ClientSeenAtHealthFacilityActivity.this);
	    start_time=System.currentTimeMillis();
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
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "ANC Diagnostic: Client Seen at Health Facility", start_time.toString(), end_time.toString());
		finish();
	}
}
