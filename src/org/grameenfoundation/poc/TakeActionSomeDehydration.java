package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TakeActionSomeDehydration extends Activity {

	private Button button_yes;
	private Button button_no;
	private Context mContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_some_dehydration);
		mContext=TakeActionSomeDehydration.this;
		button_yes=(Button) findViewById(R.id.button_yes);
		button_yes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			Intent intent=new Intent(mContext, TakeActionSomeDehydrationNoActivity.class);
			intent.putExtra("category","yes");	
			startActivity(intent);
			}
			
		});
		button_no=(Button) findViewById(R.id.button_no);
		button_no.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			Intent intent=new Intent(mContext, TakeActionSomeDehydrationNoActivity.class);
			intent.putExtra("category","no");	
			startActivity(intent);
			}
			
		});
	}

}
