package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class PostpartumExercisesActivity extends Activity {

	private WebView myWebView;
	private Button button_next;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_postpartum_exercises);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling");
	    myWebView = (WebView) findViewById(R.id.webView1);	    	 
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.loadUrl("file:///android_asset/www/cch/modules/poc/images/pelvicrock.gif");
		button_next=(Button) findViewById(R.id.button_next);
		button_next.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent =new Intent(PostpartumExercisesActivity.this,PostpartumExerciseNextActivity.class);
				startActivity(intent);
			}
			
		});
		
	}

}
