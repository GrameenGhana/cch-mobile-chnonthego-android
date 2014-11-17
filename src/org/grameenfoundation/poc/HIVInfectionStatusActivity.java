package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HIVInfectionStatusActivity extends Activity {

	private Button button_negative;
	private Button button_positive;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_hiv_status);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: HIV Infection");
	    dbh=new DbHelper(HIVInfectionStatusActivity.this);
	    start_time=System.currentTimeMillis();
	   button_negative=(Button) findViewById(R.id.button_negative);
	   button_negative.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent intent=new Intent(HIVInfectionStatusActivity.this,HIVInfectionAskActivity.class);
			intent.putExtra("value", "negative");
			startActivity(intent);
		}
		   
	   });
	   button_positive=(Button) findViewById(R.id.button_positive);
	   button_positive.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(HIVInfectionStatusActivity.this,HIVInfectionAskActivity.class);
				intent.putExtra("value", "positive");
				startActivity(intent);
			}
			   
		   });
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic HIV Infection", start_time.toString(), end_time.toString());
		finish();
	}
}
