package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SoftUterusPNCMotherYesActivity extends BaseActivity {

	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private Button button_yes;
	private Button button_no; 

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext = SoftUterusPNCMotherYesActivity.this;
	    setContentView(R.layout.activity_mother_pnc_soft_uterus_yes);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Soft Uterus");
	    dbh=new DbHelper(SoftUterusPNCMotherYesActivity.this);
	    start_time=System.currentTimeMillis();
	    button_yes=(Button) findViewById(R.id.button_yes);
	    button_yes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SoftUterusPNCMotherYesActivity.this,SoftUterusPNCMotherYesNextActivity.class);
				intent.putExtra("value", "yes");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    
	    button_no=(Button) findViewById(R.id.button_no);
	    button_no.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(SoftUterusPNCMotherYesActivity.this,SoftUterusPNCMotherYesNextActivity.class);
				intent.putExtra("value", "no");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Soft Uterus", start_time.toString(), end_time.toString());
		finish();
	}
}
