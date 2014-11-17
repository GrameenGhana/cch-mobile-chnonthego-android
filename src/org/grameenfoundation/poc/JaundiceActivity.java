package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class JaundiceActivity extends BaseActivity {

	private Button button_next;
//	protected Context mContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jaundice);
		mContext = JaundiceActivity.this;
		button_next = (Button) findViewById(R.id.button_next);
		button_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,
						JaundiceSectionsActivity.class);
				startActivity(intent);
			}

		});
	}
	
	
	

}
