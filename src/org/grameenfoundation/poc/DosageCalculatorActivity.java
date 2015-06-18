package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DosageCalculatorActivity extends BaseActivity {

	private EditText editText_weight;
	private EditText editText_trimester;
	private TextView textView_one;
	private TextView textView_two;
	private TextView textView_three;
	private TextView textView_four;
	private Button button_calculate;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private JSONObject json; 

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_dosage_calculator);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("Dosage Calculator");
	    mContext=DosageCalculatorActivity.this;
	    editText_weight=(EditText) findViewById(R.id.editText_weight);
	    editText_trimester=(EditText) findViewById(R.id.editText_trimester);
	    start_time=System.currentTimeMillis();
	    dbh=new DbHelper(DosageCalculatorActivity.this);
	    json=new JSONObject();
	    try {
			json.put("page", "Dosage Calculator");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    textView_one=(TextView) findViewById(R.id.textView_one);
	    textView_two=(TextView) findViewById(R.id.textView_two);
	    textView_three=(TextView) findViewById(R.id.textView_three);
	    textView_four=(TextView) findViewById(R.id.textView_four);
	    
	    button_calculate=(Button) findViewById(R.id.button_calculate);
	    button_calculate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(editText_weight.getText().toString().equals("")){
					editText_weight.requestFocus();
					editText_weight.setError("Enter a weight value!");
				}else if(editText_trimester.getText().toString().equals("")){
					editText_trimester.requestFocus();
					editText_trimester.setError("Enter a trimester value (1 to 3)!");
				}else{
			calculate();
				}	
			}
	    	
	    });
	}

	
	public double oralQuinineDosage(double weight,int trimester){
		int base = 0;
		if(weight>60){
			weight=60;
		} 
		switch(trimester){
		case 1:
			base=10;
			break;
		case 2:
			base=4;
			break;
		case 3:
			base=4;
			break;
		default:
			base = 4;
			break;
		}
		return base*weight;
	}
	
	public double artesunateDosage(double weight,int trimester){
		int base = 0;
		switch(trimester){
		case 1:
			base=0;
			break;
		default: 
			base =4; 
		break;
		}
		return base *weight;
		
	}
	
	public double amodiaquineDosage(double weight,int trimester){
		 int base = 0;
		 switch(trimester){
		 case 1:
			 base = 0;
			 break;
		 default: 
				base=10; 
			break;
		 }
		
		return base * weight;
	}
	
	public void calculate(){
		String weight_text=editText_weight.getText().toString();
		String trimester_text=editText_trimester.getText().toString();
		double weight=Double.parseDouble(weight_text);
		int trimester=Integer.valueOf(trimester_text);
		int dosage;
		if(trimester>3||trimester<1){
			editText_trimester.requestFocus();
			editText_trimester.setError("Please enter a valid trimester (1 to 3)");
			textView_three.setVisibility(View.GONE);
			textView_four.setVisibility(View.GONE);
			textView_one.setVisibility(View.GONE);
			textView_two.setVisibility(View.GONE);
		}
		else if(trimester>1){
			//dosage=artesunateDosage(weight,trimester);
			textView_three.setVisibility(View.VISIBLE);
			textView_four.setVisibility(View.VISIBLE);
			textView_one.setVisibility(View.VISIBLE);
			textView_two.setVisibility(View.VISIBLE);
			textView_one.setText("Oral Quinine: 600 mg every 8 hours for 7days \n OR");
			textView_two.setText("Artesunate+Amodiaquine: "+String.valueOf(artesunateDosage(weight,trimester))+" mg artesunate "+String.valueOf(amodiaquineDosage(weight,trimester)+" mg amodiaquine per day for 3 days \n OR"));
			textView_three.setText("Artesunate+Amodiaquine: "+String.valueOf(artesunateDosage(weight,trimester)/2)+" mg artesunate "+String.valueOf(amodiaquineDosage(weight,trimester)/2)+" mg amodiaquine twice a day for 3 days \n OR");
			textView_four.setText("Artemether Lumefantrine: Arimether 20 mg, Lumfantrine: 120 mg lumfantrine with fatty meal \n Day 1: 4 tablets stat +  4 tablets in 8 hours \n "
					+ "Day 2: 4 tablets twice daily \n Day 3: 4 tablets twice daily");
		}else {
			textView_one.setText("Oral Quinine: 600 mg every 8 hours for 7days \n OR");
			textView_two.setText("Oral Quinine: "+String.valueOf(oralQuinineDosage(weight,trimester))+" mg of quinine plus 300 mg of Clindamycin three times daily for 3 days");
			textView_three.setVisibility(View.GONE);
			textView_four.setVisibility(View.GONE);
			textView_one.setVisibility(View.VISIBLE);
			textView_two.setVisibility(View.VISIBLE);
		}
	}
	
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		//dbh.insertCCHLog("Point of Care", "Dosage Calculator", start_time.toString(), end_time.toString());
	    dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
