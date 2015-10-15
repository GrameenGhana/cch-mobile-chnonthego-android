package org.grameenfoundation.cch.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.grameenfoundation.cch.utils.MultiSelectSpinner;
import org.grameenfoundation.cch.utils.MultiSelectSpinner.MultiSpinnerListener;
import org.grameenfoundation.poc.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.format.Time;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class UpdateActivity extends BaseActivity {

	private RadioGroup update;
	private TextView message;
	private EditText comment;
	private MaterialSpinner justification;
	private Button dialogButton;
	private DbHelper db;
	private Context mContext;
	private long id;
	private String number;
	private String name;
	private String type;
	private String period;
	private String dueDate;
	private LinearLayout linearLayout_comment;
	private LinearLayout linearLayout_justification;
	private LinearLayout linearLayout_achievedNumber;
	private EditText achievedNumber;
	private long start_time;
	private long end_time;
	private TextView status;
	private long update_id;
	private String number_achieved_from_previous;
	private String today;
	private String startDate;
	private TextView start_date;
	private TextView due_date;
	private String topic;
	private LinearLayout linearLayout_question;
	private RadioGroup question;
	private LinearLayout linearLayout_learningJustification;

	private String learning_achieved_number;
	private String justification_text;
	private LinearLayout linearLayout_question2;
	private RadioGroup question2;
	private EditText edittext_learningJustification;
	private String course;
	private long old_id;
	private TableRow other_option;
	private EditText editText_otherOption;
	private RadioGroup radioGroup_group;
	private MultiSelectSpinner group_spinner;
	private ArrayList<String> groupmembernames;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.update_dialog);
		mContext=UpdateActivity.this;
		db=new DbHelper(mContext);
		getActionBar().setTitle("Planner");
		getActionBar().setSubtitle("Update Targets");
		dialogButton = (Button) findViewById(R.id.button_update);
		justification=(MaterialSpinner) findViewById(R.id.spinner_justification);
		linearLayout_comment=(LinearLayout) findViewById(R.id.LinearLayout_comment);
		linearLayout_justification=(LinearLayout) findViewById(R.id.LinearLayout_justification);
		linearLayout_learningJustification=(LinearLayout) findViewById(R.id.linearLayout_learningJustification);
		edittext_learningJustification=(EditText) findViewById(R.id.editText_learningJustification);
		linearLayout_achievedNumber=(LinearLayout) findViewById(R.id.linearLayout_achievedNumber);
		linearLayout_question=(LinearLayout) findViewById(R.id.linearLayout_question);
		linearLayout_question2=(LinearLayout) findViewById(R.id.linearLayout_question2);
		other_option=(TableRow) findViewById(R.id.other_option);
		editText_otherOption=(EditText) findViewById(R.id.editText_otherOption);
		other_option.setVisibility(View.GONE);
		 question=(RadioGroup) findViewById(R.id.radioGroup_learning);
		 question2=(RadioGroup) findViewById(R.id.radioGroup_justify);
		 radioGroup_group=(RadioGroup) findViewById(R.id.radioGroup_groups);
		 groupmembernames=new ArrayList<String>();
		 groupmembernames=db.getAllGroupMembers();
		 Collections.sort(groupmembernames,String.CASE_INSENSITIVE_ORDER);
		 group_spinner = (MultiSelectSpinner) findViewById(R.id.spinner1);
	        //group_spinner.setAdapter(adapter, false, onSelectedListener);
	        group_spinner.setItems(groupmembernames, "Select group members", -1, new MultiSpinnerListener() {
				
				@Override
				public void onItemsSelected(boolean[] selected) {
					
					// your operation with code...
					for(int i=0; i<selected.length; i++) {
						if(selected[i]) {
							Log.i("TAG", i + " : "+ groupmembernames.get(i));
						}
					}
				}
			});
	        
	        radioGroup_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if(checkedId==R.id.radio_yesGroup){
						group_spinner.setVisibility(View.VISIBLE);
					}else{
						group_spinner.setVisibility(View.GONE);
					}
					
				}
			});
		 question2.check(R.id.radio_no2);
		start_time=System.currentTimeMillis();
		String[] items=getResources().getStringArray(R.array.Justification);
		ArrayAdapter<String> adapter2=new ArrayAdapter<String>(UpdateActivity.this, android.R.layout.simple_list_item_1, items);
		justification.setAdapter(adapter2);
		linearLayout_justification.setVisibility(View.GONE);
		question2.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId==R.id.radio_no2) {
					linearLayout_justification.setVisibility(View.GONE);
					justification_text=" ";
				}else if(checkedId==R.id.radio_yes2){
					linearLayout_justification.setVisibility(View.VISIBLE);
					justification.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							if(justification.getSelectedItem().toString().equalsIgnoreCase("Other")){
								other_option.setVisibility(View.VISIBLE);
								justification_text=editText_otherOption.getText().toString();
							}else{
								other_option.setVisibility(View.GONE);
								justification_text=justification.getSelectedItem().toString();
							}
							
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}
					});
				}
			}
		});
		achievedNumber=(EditText) findViewById(R.id.editText_achievedNumber);
		start_time=System.currentTimeMillis();
		comment=(EditText) findViewById(R.id.editText_comment);
		 message=(TextView) findViewById(R.id.textView_message);
		 start_date=(TextView) findViewById(R.id.textView_startDate);
		 due_date=(TextView) findViewById(R.id.textView_dueDate);
		 status=(TextView) findViewById(R.id.textView_status);
		 
		 Bundle extras = getIntent().getExtras(); 
	     if (extras != null) {
	    	 try{
	          id= extras.getLong("id");
	          old_id=extras.getLong("old_id");
	          number=extras.getString("number");
	          name=extras.getString("name");
	          type=extras.getString("type");
	          period=extras.getString("period");
	          dueDate=extras.getString("due_date");
	          startDate=extras.getString("start_date");
	          number_achieved_from_previous=extras.getString("number_achieved");
	    	 }catch(Exception e){
	    		 e.printStackTrace();
	    	 }
	          if(type.equals("learning")){
	        	  topic=extras.getString("learning_topic");
	        	  course=extras.getString("learning_section");
	          }
	        }
		
		if(type.equals("learning")){
			status.setVisibility(View.GONE);
			message.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
			String first="<font color='#53AB20'>Were you able to complete the topic </font>";
			String next="<font color='#520000'>"+topic+"</font>";
			String next_two="<font color='#53AB20'> under the course: </font>";
			String next_three="<font color='#520000'>"+course+"</font>";
			String next_four="<font color='#53AB20'>"+reminderValue(period)+" </font>";
			//message.setText("Were you able to complete the course "+course+" under the topic: "+topic);
			message.setText(Html.fromHtml(first+next+next_two+next_three+next_four));
			linearLayout_justification.setVisibility(View.GONE);
			linearLayout_achievedNumber.setVisibility(View.GONE);
			linearLayout_question2.setVisibility(View.GONE);
			question.setOnCheckedChangeListener(new OnCheckedChangeListener(){
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (checkedId==R.id.radio_no) {
						linearLayout_learningJustification.setVisibility(View.VISIBLE);
						learning_achieved_number="0";
					}else if(checkedId==R.id.radio_yes){
						linearLayout_learningJustification.setVisibility(View.GONE);
						linearLayout_justification.setVisibility(View.GONE);
						learning_achieved_number="1";
					}
					
				}
			});
		
			
		}else if(number.equals("0")&&type.equals("other")){
			status.setVisibility(View.GONE);
			message.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
			String first="<font color='#53AB20'>Were you able to complete the following target:  </font>";
			String next="<font color='#520000'>"+name+"</font>";
			String next_two="<font color='#53AB20'>"+reminderValue(period)+" </font>";
			message.setText(Html.fromHtml(first+next+next_two));
			//message.setText(name);
			linearLayout_justification.setVisibility(View.GONE);
			linearLayout_achievedNumber.setVisibility(View.GONE);
			linearLayout_question2.setVisibility(View.GONE);
			question.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (checkedId==R.id.radio_no) {
						linearLayout_learningJustification.setVisibility(View.VISIBLE);
						learning_achieved_number="0";
					}else if(checkedId==R.id.radio_yes){
						linearLayout_learningJustification.setVisibility(View.GONE);
						linearLayout_justification.setVisibility(View.GONE);
						learning_achieved_number="1";
					}
					
				}
			});
		}else {
			linearLayout_question.setVisibility(View.GONE);
			message.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
			String first="<font color='#53AB20'>Were you able to complete the following target:  </font>";
			String next="<font color='#520000'>"+name+"</font>";
			String next_two="<font color='#53AB20'>"+reminderValue(period)+" </font>";
			message.setText(Html.fromHtml(first+next+next_two));
			//message.setText(name);
			String one="<font color='#53AB20'>So far you have completed </font>";
			String two="<font color='#520000'>"+number_achieved_from_previous+" </font>";
			String three="<font color='#53AB20'>"+" out of "+"</font>";
			String four="<font color='#520000'>"+number+"</font>";
			status.setText(Html.fromHtml(one+two+three+four));
			//status.setText("So far you have completed "+number_achieved_from_previous+" out of "+number);
			
		}
		start_date.setText(startDate);
		due_date.setText(dueDate);
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			if(!checkValidation()){
				Toast.makeText(getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
			}else{
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						UpdateActivity.this);
					alertDialogBuilder.setTitle("Update Verification");
					alertDialogBuilder
						.setMessage("You are about to update this target. Proceed? \n Press cancel to edit details.")
						.setCancelable(false)
						.setIcon(R.drawable.ic_error)
						.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								dialog.cancel();
							}
						  })
						.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id_negative) {
								if(type.equalsIgnoreCase("event")){
						    		String event_comment_text=comment.getText().toString();
						    		String event_number_achieved_text=achievedNumber.getText().toString();
						    		String event_update_status = MobileLearning.CCH_TARGET_STATUS_UPDATED;
						    		int event_new_number_achieved=Integer.valueOf(event_number_achieved_text);
						        	int event_number_achieved_for_entry=event_new_number_achieved+Integer.valueOf(number_achieved_from_previous);
						        	int event_number_remaining_for_entry=Integer.valueOf(number)-event_number_achieved_for_entry;
						        	if(db.insertJustification("event", name, justification_text, event_comment_text,number,String.valueOf(event_number_achieved_text),String.valueOf(event_number_remaining_for_entry),id, MobileLearning.CCH_TARGET_STATUS_NEW) !=0){
						    	JSONObject json = new JSONObject();
								 try {
									 if(old_id!=0){
										 json.put("id", old_id);
									 }else{
										 json.put("id", id);
									 }
									 json.put("target_type", name);
									 json.put("category", "event");
									 json.put("target_number", number);
									 json.put("start_date", startDate);
									 json.put("due_date", dueDate);
									 json.put("achieved_number", event_number_achieved_for_entry);
									 json.put("last_updated", getDateTime());
									 json.put("justification", justification_text); 
									 json.put("ver", db.getVersionNumber(getApplicationContext()));
									 json.put("battery", db.getBatteryStatus(getApplicationContext()));
								     json.put("device", db.getDeviceName());
								     json.put("imei", db.getDeviceImei(getApplicationContext()));
									 
								} catch (JSONException e) {
									e.printStackTrace();
								}
								 end_time=System.currentTimeMillis();
								 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
								 Calendar c = Calendar.getInstance();
							        int month=c.get(Calendar.MONTH)+1;
							        int day=c.get(Calendar.DAY_OF_WEEK);
							        int year=c.get(Calendar.YEAR);
								 if(event_number_achieved_for_entry==Integer.valueOf(number)){
									 db.updateTarget(event_update_status,event_number_achieved_for_entry,event_number_remaining_for_entry, id);
									 System.out.println("Updating event target with: "+event_update_status);
								 }else if(event_number_achieved_for_entry<Integer.valueOf(number)){
									 db.updateTarget(MobileLearning.CCH_TARGET_STATUS_NEW,event_number_achieved_for_entry,event_number_remaining_for_entry,id);
								 }else if(event_number_achieved_for_entry>Integer.valueOf(number)){
									 db.updateTarget(event_update_status,event_number_achieved_for_entry,event_number_remaining_for_entry, id);
									 System.out.println("Updating event target with: "+event_update_status); 
								 }
								 Intent intent=new Intent(Intent.ACTION_MAIN);
								 intent.setClass(UpdateActivity.this, NewEventPlannerActivity.class);
								 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								 startActivity(intent);
								 UpdateActivity.this.finish();
								 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
								 Toast.makeText(getApplicationContext(), "Target updated!",
								         Toast.LENGTH_SHORT).show();
						        	}
								
								}
								if (type.equals("coverage")){
						    		String coverage_comment_text=comment.getText().toString();
						    		String coverage_number_achieved_text=achievedNumber.getText().toString();
						    		String coverage_update_status = MobileLearning.CCH_TARGET_STATUS_UPDATED;
						    		int coverage_new_number_achieved=Integer.valueOf(coverage_number_achieved_text);
						        	int coverage_number_achieved_for_entry=coverage_new_number_achieved+Integer.valueOf(number_achieved_from_previous);
						        	int coverage_number_remaining_for_entry=Integer.valueOf(number)-coverage_number_achieved_for_entry;
						        	if(db.insertJustification("event", name, justification_text, coverage_comment_text,number,String.valueOf(coverage_number_achieved_for_entry),String.valueOf(coverage_number_remaining_for_entry),id,  MobileLearning.CCH_TARGET_STATUS_NEW) !=0){
						    		JSONObject json = new JSONObject();
									 try {
										 if(old_id!=0){
											 json.put("id", old_id);
										 }else{
											 json.put("id", id);
										 }
										 json.put("target_type", name);
										 json.put("category", "coverage");
										 json.put("start_date", startDate);
										 json.put("due_date", dueDate);
										 json.put("target_number", number);
										 json.put("achieved_number", coverage_number_achieved_for_entry);
										 json.put("last_updated", getDateTime());
										 json.put("justification", justification_text);
										 json.put("ver", db.getVersionNumber(getApplicationContext()));
											json.put("battery", db.getBatteryStatus(getApplicationContext()));
									    	json.put("device", db.getDeviceName());
									    	json.put("imei", db.getDeviceImei(getApplicationContext()));
									} catch (JSONException e) {
										e.printStackTrace();
									}
									 end_time=System.currentTimeMillis();
									 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
									 if(coverage_number_achieved_for_entry==Integer.valueOf(number)){
										 db.updateTarget(coverage_update_status,coverage_number_achieved_for_entry,coverage_number_remaining_for_entry, id);
										 }else if(coverage_number_achieved_for_entry<Integer.valueOf(number)){
											 db.updateTarget(MobileLearning.CCH_TARGET_STATUS_NEW,coverage_number_achieved_for_entry,coverage_number_remaining_for_entry,id);
										 }else if(coverage_number_achieved_for_entry>Integer.valueOf(number)){
											 db.updateTarget(coverage_update_status,coverage_number_achieved_for_entry,coverage_number_remaining_for_entry, id);
										 }
									 Intent intent=new Intent(Intent.ACTION_MAIN);
									 intent.setClass(UpdateActivity.this, NewEventPlannerActivity.class);
									 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									 startActivity(intent);
									 UpdateActivity.this.finish();
									 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
									 Toast.makeText(getApplicationContext(), "Target updated!",
									         Toast.LENGTH_SHORT).show();
								}
						    		
								}
								if(type.equalsIgnoreCase("other")){
									String other_number_achieved_text = null;
						    		String other_comment_text=comment.getText().toString();
						    		if(number.equals("0")){
						    			other_number_achieved_text="0";
						    		}else{
						    			other_number_achieved_text=achievedNumber.getText().toString();
						    			
						    		}
						    		String other_update_status = MobileLearning.CCH_TARGET_STATUS_UPDATED;
						    		int other_new_number_achieved=Integer.valueOf(other_number_achieved_text);
						        	int other_number_achieved_for_entry=other_new_number_achieved+Integer.valueOf(number_achieved_from_previous);
						        	int other_number_remaining_for_entry=Integer.valueOf(number)-other_number_achieved_for_entry;
						        	/*
						        	if(other_number_achieved_for_entry>Integer.valueOf(number)){
						        		achievedNumber.requestFocus();
						        		achievedNumber.setError("Your total number achieved cannot be greater than your actual target!");
						        	}else{*/
						        	if(db.insertJustification("event", name, justification_text, other_comment_text,number,String.valueOf(other_number_achieved_for_entry),String.valueOf(other_number_remaining_for_entry),id,  MobileLearning.CCH_TARGET_STATUS_NEW) !=0){
						    		JSONObject json = new JSONObject();
									 try {
										 if(old_id!=0){
											 json.put("id", old_id);
										 }else{
											 json.put("id", id);
										 }
										 json.put("target_type", name);
										 json.put("category", "other");
										 json.put("start_date", startDate);
										 json.put("due_date", dueDate);
										 json.put("target_number", number);
										 json.put("achieved_number", other_number_achieved_for_entry);
										 json.put("last_updated", getDateTime());
										 json.put("justification", justification_text); 
										 json.put("ver", db.getVersionNumber(getApplicationContext()));
											json.put("battery", db.getBatteryStatus(getApplicationContext()));
									    	json.put("device", db.getDeviceName());
									    	json.put("imei", db.getDeviceImei(getApplicationContext()));
									} catch (JSONException e) {
										e.printStackTrace();
									}
									 end_time=System.currentTimeMillis();
									 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
									 
									 if(other_number_achieved_for_entry==Integer.valueOf(number)){
										 	db.updateTarget(other_update_status,other_number_achieved_for_entry,other_number_remaining_for_entry,id);
										 }else if(other_number_achieved_for_entry<Integer.valueOf(number)){
											 db.updateTarget(MobileLearning.CCH_TARGET_STATUS_NEW,other_number_achieved_for_entry,other_number_remaining_for_entry,id);
										 }else if(other_number_achieved_for_entry>Integer.valueOf(number)){
											 db.updateTarget(other_update_status,other_number_achieved_for_entry,other_number_remaining_for_entry,id);
										 }
									 Intent intent=new Intent(Intent.ACTION_MAIN);
									 intent.setClass(UpdateActivity.this, NewEventPlannerActivity.class);
									 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									 startActivity(intent);
									 UpdateActivity.this.finish();
									 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
									 Toast.makeText(getApplicationContext(), "Target updated!",
									         Toast.LENGTH_SHORT).show();
								}   	
								}
								if(type.equalsIgnoreCase("learning")){
									String learning_justification_text=edittext_learningJustification.getText().toString();
						    		String learning_comment_text=comment.getText().toString();
						        	if(db.insertJustification("event", name, learning_justification_text, learning_comment_text,number,"0","0",id, "new_record") !=0){
						    		JSONObject json = new JSONObject();
									 try {
										 if(old_id!=0){
											 json.put("id", old_id);
										 }else{
											 json.put("id", id);
										 }
										 json.put("target_type", name);
										 json.put("category", "learning");
										 json.put("start_date", startDate);
										 json.put("target_number", 1);
										 json.put("due_date", dueDate);
										 json.put("achieved_number", learning_achieved_number);
										 json.put("last_updated", getDateTime());
										 json.put("ver", db.getVersionNumber(getApplicationContext()));
											json.put("battery", db.getBatteryStatus(getApplicationContext()));
									    	json.put("device", db.getDeviceName());
									    	json.put("imei", db.getDeviceImei(getApplicationContext()));
										 if(learning_justification_text.equals(" ")){
										 json.put("justification", " ");
										 }else {
										 json.put("justification", learning_justification_text); 
										 }
									} catch (JSONException e) {
										e.printStackTrace();
									}
									 end_time=System.currentTimeMillis();
									 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
										 db.updateTarget(MobileLearning.CCH_TARGET_STATUS_UPDATED,0,0,id);
										 Intent intent=new Intent(Intent.ACTION_MAIN);
										 intent.setClass(UpdateActivity.this, NewEventPlannerActivity.class);
										 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										 startActivity(intent);
										 UpdateActivity.this.finish();
										 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
									 Toast.makeText(getApplicationContext(), "Target updated!",
									         Toast.LENGTH_SHORT).show();
								}   	
								}
							}
						});
						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
			}
			}
		});
		
	}	
	private boolean checkValidation() {
        boolean ret = true;
 
        if (status.isShown()&&!Validation.hasTextEditText(achievedNumber)) ret = false;
        if (linearLayout_learningJustification.isShown()&&!Validation.hasTextEditText(edittext_learningJustification)) ret = false;
        if (linearLayout_justification.isShown()&&!Validation.hasSelection(justification))ret = false;
        if (other_option.isShown()&&!Validation.hasTextEditText(editText_otherOption))ret = false;
        return ret;
    }
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}
	
	public String reminderValue(String reminder){
		String value="";
		try{
		switch(reminder){
		case "Daily":
			value=" today?";
			break;
		case "Weekly":
			value=" this week?";
			break;
		case "Monthly":
			value=" this month?";
			break;
		case "Quarterly":
			value=" this quarter?";
			break;
		case "Mid-year":
			value=" this half of the year?";
			break;
		case "Annually":
			value=" this year?";
			break;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return value;
	}
}
