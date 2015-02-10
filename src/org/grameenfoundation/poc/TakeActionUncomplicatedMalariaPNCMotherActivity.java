package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TakeActionUncomplicatedMalariaPNCMotherActivity extends BaseActivity {

	private Button button_next;
	 private DbHelper dbh;
		private Long start_time;
		private Long end_time;
		private Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_uncomplicated_malaria_pnc_mother);
	    mContext=TakeActionUncomplicatedMalariaPNCMotherActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Malaria");
	    TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
	    click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "malaria");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    
	    TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
	    click_here_too.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,NutritionCounsellingActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    
	    TextView click_here_three=(TextView) findViewById(R.id.textView_clickHereThree);
	    click_here_three.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,ReturningForCareActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Malaria", start_time.toString(), end_time.toString());
		finish();
	}
}
