package org.digitalcampus.oppia.activity;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class UpdateActivity extends Activity {

	private RadioGroup update;
	private TextView message;
	private EditText comment;
	private EditText justification;
	private Button dialogButton;
	private DbHelper db;
	private Context mContext;
	private String id;
	private String number;
	private String name;
	private String type;
	private LinearLayout linearLayout_comment;
	private LinearLayout linearLayout_justification;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.update_dialog);
		mContext=UpdateActivity.this;
		dialogButton = (Button) findViewById(R.id.button_update);
		justification=(EditText) findViewById(R.id.editText_dialogJustification);
		linearLayout_comment=(LinearLayout) findViewById(R.id.LinearLayout_comment);
		linearLayout_justification=(LinearLayout) findViewById(R.id.LinearLayout_justification);
		comment=(EditText) findViewById(R.id.editText_comment);
		 message=(TextView) findViewById(R.id.textView_message);
		 db=new DbHelper(mContext);
		 Bundle extras = getIntent().getExtras(); 
	        if (extras != null) {
	          id= extras.getString("id");
	          number=extras.getString("number");
	          name=extras.getString("name");
	          type=extras.getString("type");
	        }
		message.setText("Were you able to achieve your target of "+number+" "+name +"?");
		update=(RadioGroup) findViewById(R.id.radioGroup_updateDialog);
		update.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				if (checkedId == R.id.radio_updateDialogYes) {
					linearLayout_comment.setVisibility(View.VISIBLE);
					linearLayout_justification.setVisibility(View.GONE);
				} else if (checkedId == R.id.radio_updateDialogNo) {
					linearLayout_justification.setVisibility(View.VISIBLE);
					linearLayout_comment.setVisibility(View.VISIBLE);
				}
			}
		});
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			            	
			            	String justification_text=justification.getText().toString();
			            	String comment_text=comment.getText().toString();
		                	if(db.insertJustification(name, number, justification_text, comment_text, "new_record") !=0){
		                	long last_id=db.insertJustification(name, number, justification_text, comment_text, "new_record");
		                	if(type.equals("event")){
		                	JSONObject json = new JSONObject();
							 try {
								json.put("id", last_id);
								 json.put("event_type", name);
								 json.put("event_number", number);
								 if(justification_text.equals(" ")){
								 json.put("justification", "did not justify");
								 }else {
								 json.put("justification", "justified"); 
								 }
							} catch (JSONException e) {
								e.printStackTrace();
							}
							 db.insertCCHLog("Event Planner", json.toString(), " ", " ");
							 System.out.println(json.toString());
							 db.updateEventTarget("updated", Long.valueOf(id));
		                	}else if(type.equals("coverage")){
		                		JSONObject json = new JSONObject();
								 try {
									json.put("id", last_id);
									 json.put("coverage_type", name);
									 json.put("coverage_number", number);
									 if(justification_text.equals(" ")){
									 json.put("justification", "did not justify");
									 }else {
									 json.put("justification", "justified"); 
									 }
								} catch (JSONException e) {
									e.printStackTrace();
								}
								 db.insertCCHLog("Event Planner", json.toString(), " ", " ");
								 System.out.println(json.toString());
								 db.updateCoverageTarget("updated", Long.valueOf(id));
		                	}else if(type.equals("other")){
		                		JSONObject json = new JSONObject();
								 try {
									json.put("id", last_id);
									 json.put("other_type", name);
									 json.put("other_number", number);
									 if(justification_text.equals(" ")){
									 json.put("justification", "did not justify");
									 }else {
									 json.put("justification", "justified"); 
									 }
								} catch (JSONException e) {
									e.printStackTrace();
								}
								 db.insertCCHLog("Event Planner", json.toString(), " ", " ");
								 System.out.println(json.toString());
								 db.updateOtherTarget("updated", Long.valueOf(id));
		                	}
							 Toast.makeText(getApplicationContext(), "Target updated!",
					         Toast.LENGTH_SHORT).show();
								
		                	}
			    }
			
		});
				
	}

}
