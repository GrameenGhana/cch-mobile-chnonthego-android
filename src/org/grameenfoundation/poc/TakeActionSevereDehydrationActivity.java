package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TakeActionSevereDehydrationActivity extends BaseActivity {
	
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_sever_dehydration);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Diarrheoa");
	    dbh=new DbHelper(TakeActionSevereDehydrationActivity.this);
	    start_time=System.currentTimeMillis();
	    TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
		   click_here.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionSevereDehydrationActivity.this,KeepingBabyWarmAndMalariaActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
			}
			   
		   });
		   
		   TextView click_here_too=(TextView) findViewById(R.id.textView_clickHereToo);
		   click_here_too.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(TakeActionSevereDehydrationActivity.this,TreatingDiarrhoeaActivity.class);
				intent.putExtra("value", "keeping_baby_warm");
				startActivity(intent);
			}
			   
		   });
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic: Diarrheoa", start_time.toString(), end_time.toString());
		finish();
	}
}
