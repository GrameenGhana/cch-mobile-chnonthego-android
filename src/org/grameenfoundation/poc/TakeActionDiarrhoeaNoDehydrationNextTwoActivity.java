package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class TakeActionDiarrhoeaNoDehydrationNextTwoActivity extends BaseActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic");
	    	setContentView(R.layout.activity_diarrhoea_no_dehydration_next_two);
	    	 TextView click_here=(TextView) findViewById(R.id.textView_clickHere);
			   click_here.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					Intent intent=new Intent(TakeActionDiarrhoeaNoDehydrationNextTwoActivity.this,ReturningForCareActivity.class);
					intent.putExtra("value", "keeping_baby_warm");
					startActivity(intent);
				}
				   
			   });
	}

}
