package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class AskMalariaFeverYesActivity extends Activity {

	private Button button_next;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_ask_fever_yes);
	    button_next=(Button) findViewById(R.id.button_next);
	}

}
