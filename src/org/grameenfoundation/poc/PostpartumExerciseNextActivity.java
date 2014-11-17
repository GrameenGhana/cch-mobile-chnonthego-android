package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class PostpartumExerciseNextActivity extends BaseActivity {

	private WebView myWebView;
	private Button button_next;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_postpartum_exercises_next);
	    mContext =PostpartumExerciseNextActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Postpartum Exercises");
	    dbh=new DbHelper(PostpartumExerciseNextActivity.this);
	    start_time=System.currentTimeMillis();
	    myWebView = (WebView) findViewById(R.id.webView1);	    	 
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.loadUrl("file:///android_asset/www/cch/modules/poc/images/headshoulderlift.gif");
		button_next=(Button) findViewById(R.id.button_next);
		button_next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(PostpartumExerciseNextActivity.this,PostpartumExercisesNextTwoActivity.class);
				startActivity(intent);
			}
			
		});
		
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Counselling:  Postpartum Exercises", start_time.toString(), end_time.toString());
		finish();
	}
}
