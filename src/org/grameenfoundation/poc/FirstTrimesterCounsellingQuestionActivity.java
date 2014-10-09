package org.grameenfoundation.poc;


import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstTrimesterCounsellingQuestionActivity extends Activity implements OnClickListener{
	
	Context mContext;
	private Button button_questionYes;
	private Button button_questionNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_first_trimester_question);
	    mContext=FirstTrimesterCounsellingQuestionActivity.this;
	    
	    button_questionYes=(Button) findViewById(R.id.button_firstTrimesterQuestionYes);
	    button_questionYes.setOnClickListener(this);
	    
	    button_questionNo=(Button) findViewById(R.id.button_firstTrimesterQuestionNo);
	    button_questionNo.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent;
		switch(v.getId()){
		case R.id.button_firstTrimesterQuestionNo:
			intent=new Intent(mContext, FirstTrimesterCounsellingExaminePatientActivity.class);
			startActivity(intent);
			break;
		}
		
	}

}
