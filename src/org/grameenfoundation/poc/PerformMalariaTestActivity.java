package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PerformMalariaTestActivity extends Activity {

	private Button button_positive;
	private Button button_negative;
	private Button button_testNotDone;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_malaria_test_);
	    button_positive=(Button) findViewById(R.id.button_positive);
	    button_positive.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(PerformMalariaTestActivity.this,AskMalariaComplicatedActivity.class);
				startActivity(intent);
			}
	    	
	    });
	    button_negative=(Button) findViewById(R.id.button_negative);
	    button_testNotDone=(Button) findViewById(R.id.button_testNotDone);
	}

}
