package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.mobile.learningGF.R.id;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TreatingDiarrhoeaActivity extends BaseActivity {
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private TextView amount;
	private EditText editText_weight;
	private Button button_calculate;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_treating_diarrhoea);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Treating Diarrhoea");
	    dbh=new DbHelper(TreatingDiarrhoeaActivity.this);
	    start_time=System.currentTimeMillis();
	    amount=(TextView) findViewById(R.id.textView_amount);
	    editText_weight=(EditText) findViewById(R.id.editText_weight);
	    button_calculate=(Button) findViewById(R.id.button_calculate);
	    button_calculate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				 int ors_amount_given=Integer.valueOf(editText_weight.getText().toString())*75;
				 amount.setText("ORS amount= "+String.valueOf(ors_amount_given));
				
			}
	    	
	    });
	   
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Counselling: Treating Diarrhoea" , start_time.toString(), end_time.toString());
		finish();
	}
}
