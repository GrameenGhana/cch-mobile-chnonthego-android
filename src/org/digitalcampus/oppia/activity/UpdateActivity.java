package org.digitalcampus.oppia.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.chnonthego.LoginActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class UpdateActivity extends Activity {

	private RadioGroup update;
	private TextView message;
	private EditText comment;
	private Spinner justification;
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

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.update_dialog);
		mContext=UpdateActivity.this;
		 getActionBar().setTitle("Event Planner");
		    getActionBar().setSubtitle("Update Targets");
		dialogButton = (Button) findViewById(R.id.button_update);
		justification=(Spinner) findViewById(R.id.spinner_justification);
		linearLayout_comment=(LinearLayout) findViewById(R.id.LinearLayout_comment);
		linearLayout_justification=(LinearLayout) findViewById(R.id.LinearLayout_justification);
		linearLayout_learningJustification=(LinearLayout) findViewById(R.id.linearLayout_learningJustification);
		linearLayout_achievedNumber=(LinearLayout) findViewById(R.id.linearLayout_achievedNumber);
		linearLayout_question=(LinearLayout) findViewById(R.id.linearLayout_question);
		 question=(RadioGroup) findViewById(R.id.radioGroup_learning);
		start_time=System.currentTimeMillis();
		
		String[] items={"No transportation","Bad weather","No funds","No vaccines","Conflicting activity","Sick/Leave","No logistics","Other"};
		ArrayAdapter<String> adapter2=new ArrayAdapter<String>(UpdateActivity.this, android.R.layout.simple_list_item_1, items);
		justification.setAdapter(adapter2);
		achievedNumber=(EditText) findViewById(R.id.editText_achievedNumber);
		start_time=System.currentTimeMillis();
		comment=(EditText) findViewById(R.id.editText_comment);
		 message=(TextView) findViewById(R.id.textView_message);
		 start_date=(TextView) findViewById(R.id.textView_startDate);
		 due_date=(TextView) findViewById(R.id.textView_dueDate);
		 status=(TextView) findViewById(R.id.textView_status);
		 db=new DbHelper(mContext);
		 Bundle extras = getIntent().getExtras(); 
	        if (extras != null) {
	          id= extras.getLong("id");
	          System.out.println("Update id: "+String.valueOf(id));
	          number=extras.getString("number");
	          name=extras.getString("name");
	          type=extras.getString("type");
	          period=extras.getString("period");
	          dueDate=extras.getString("due_date");
	          startDate=extras.getString("start_date");
	          number_achieved_from_previous=extras.getString("number_achieved");
	          if(type.equals("learning")){
	        	  topic=extras.getString("learning_topic");
	          }
	        }
		
		if(type.equals("learning")){
			status.setVisibility(View.GONE);
			message.setText(topic);
			linearLayout_justification.setVisibility(View.GONE);
			linearLayout_achievedNumber.setVisibility(View.GONE);
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
		}else{
			linearLayout_question.setVisibility(View.GONE);
			message.setText(name);
			status.setText("So far you have completed "+number_achieved_from_previous+" out of "+number);
			
		}
		start_date.setText(startDate);
		due_date.setText(dueDate);
		Calendar c = Calendar.getInstance();
        int month=c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DAY_OF_WEEK);
        int year=c.get(Calendar.YEAR);
      	today=day+"-"+month+"-"+year;
		/*
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
					linearLayout_achievedNumber.setVisibility(View.VISIBLE);
				}
			}
		});
		*/

		
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						UpdateActivity.this);
		 
					// set title
					alertDialogBuilder.setTitle("Update Verification");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("You are about to update this target. Proceed? \n Press cancel to edit details.")
						.setCancelable(false)
						.setIcon(R.drawable.ic_error)
						.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close
								// current activity
								dialog.cancel();
							}
						  })
						.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id_negative) {
								// if this button is clicked, just close
								// the dialog box and do nothing
								if(type.equalsIgnoreCase("event")){
									String event_justification_text=justification.getSelectedItem().toString();
						    		String event_comment_text=comment.getText().toString();
						    		String event_number_achieved_text=achievedNumber.getText().toString();
						    		String event_update_status = "updated";
						    		int event_new_number_achieved=Integer.valueOf(event_number_achieved_text);
						        	
						        	int event_number_achieved_for_entry=event_new_number_achieved+Integer.valueOf(number_achieved_from_previous);
						        	System.out.println("Number achieved"+String.valueOf(event_number_achieved_for_entry));
						        	int event_number_remaining_for_entry=Integer.valueOf(number)-event_number_achieved_for_entry;
						        	System.out.println("Number remaining"+String.valueOf(event_number_remaining_for_entry));
						        	if(db.insertJustification(name, number, event_justification_text, event_comment_text,number,String.valueOf(event_number_achieved_text),String.valueOf(event_number_remaining_for_entry),id, "new_record") !=0){
						        	//if(db.editJustification(justification_text, String.valueOf(number_achieved_for_entry), String.valueOf(number_remaining_for_entry), comment_text, update_id)==true){
						        	//long last_id=db.insertJustification(name, number, justification_text, comment_text,number,number_achieved,id,"new_record");
						    	JSONObject json = new JSONObject();
								 try {
									json.put("id", id);
									 json.put("target_type", name);
									 json.put("category", "event");
									 json.put("target_number", number);
									 json.put("start_date", startDate);
									 json.put("due_date", dueDate);
									 json.put("achieved_number", event_number_achieved_for_entry);
									 json.put("last_updated", getDateTime());
									 if(event_justification_text.equals(" ")){
									 json.put("justification", " ");
									 }else {
									 json.put("justification", event_justification_text); 
									 }
								} catch (JSONException e) {
									e.printStackTrace();
								}
								 end_time=System.currentTimeMillis();
								 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
								 System.out.println(json.toString());
								 Calendar c = Calendar.getInstance();
							        int month=c.get(Calendar.MONTH)+1;
							        int day=c.get(Calendar.DAY_OF_WEEK);
							        int year=c.get(Calendar.YEAR);
							        String today=day+"-"+month+"-"+year;
								 if(event_number_achieved_for_entry==Integer.valueOf(number)){
								 db.updateEventTarget(event_update_status,event_number_achieved_for_entry,event_number_remaining_for_entry, id);
								 
								 }else if(event_number_achieved_for_entry<Integer.valueOf(number)){
									 System.out.println("Printing id again in update: "+String.valueOf(id));
									 db.updateEventTarget("new_record",event_number_achieved_for_entry,event_number_remaining_for_entry,id);
								 }
								 Intent intent=new Intent(UpdateActivity.this, NewEventPlannerActivity.class);
								 startActivity(intent);
								 finish();
								 Toast.makeText(getApplicationContext(), "Target updated!",
								         Toast.LENGTH_SHORT).show();
						        	}
								
								}
								if (type.equals("coverage")){
									String coverage_justification_text=justification.getSelectedItem().toString();
						    		String coverage_comment_text=comment.getText().toString();
						    		String coverage_number_achieved_text=achievedNumber.getText().toString();
						    		String coverage_update_status = "updated";
						    		int coverage_new_number_achieved=Integer.valueOf(coverage_number_achieved_text);
						        	int coverage_number_achieved_for_entry=coverage_new_number_achieved+Integer.valueOf(number_achieved_from_previous);
						        	System.out.println("Number achieved"+String.valueOf(coverage_number_achieved_text));
						        	int coverage_number_remaining_for_entry=Integer.valueOf(number)-coverage_number_achieved_for_entry;
						        	if(db.insertJustification(name, number, coverage_justification_text, coverage_comment_text,number,String.valueOf(coverage_number_achieved_for_entry),String.valueOf(coverage_number_remaining_for_entry),id, "new_record") !=0){
						    		JSONObject json = new JSONObject();
									 try {
										json.put("id", id);
										 json.put("target_type", name);
										 json.put("category", "coverage");
										 json.put("start_date", startDate);
										 json.put("due_date", dueDate);
										 json.put("target_number", number);
										 json.put("achieved_number", coverage_number_achieved_for_entry);
										 json.put("last_updated", getDateTime());
										 if(coverage_justification_text.equals(" ")){
										 json.put("justification", " ");
										 }else {
										 json.put("justification", coverage_justification_text); 
										 }
									} catch (JSONException e) {
										e.printStackTrace();
									}
									 end_time=System.currentTimeMillis();
									 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
									 System.out.println(json.toString());
									 Calendar c = Calendar.getInstance();
								        int month=c.get(Calendar.MONTH)+1;
								        int day=c.get(Calendar.DAY_OF_WEEK);
								        int year=c.get(Calendar.YEAR);
								        String today=day+"-"+month+"-"+year;
									 if(coverage_number_achieved_for_entry==Integer.valueOf(number)){
										 db.updateCoverageTarget(coverage_update_status,coverage_number_achieved_for_entry,coverage_number_remaining_for_entry, id);
										 }else if(coverage_number_achieved_for_entry<Integer.valueOf(number)){
											 db.updateCoverageTarget("new_record",coverage_number_achieved_for_entry,coverage_number_remaining_for_entry,id);
										 }
									 Intent intent=new Intent(UpdateActivity.this, NewEventPlannerActivity.class);
									 startActivity(intent);
									 finish();
									 Toast.makeText(getApplicationContext(), "Target updated!",
									         Toast.LENGTH_SHORT).show();
								}
								}
								
								if(type.equalsIgnoreCase("other")){
									String other_justification_text=justification.getSelectedItem().toString();
						    		String other_comment_text=comment.getText().toString();
						    		String other_number_achieved_text=achievedNumber.getText().toString();
						    		String other_update_status = "updated";
						    		int other_new_number_achieved=Integer.valueOf(other_number_achieved_text);
						        	int other_number_achieved_for_entry=other_new_number_achieved+Integer.valueOf(number_achieved_from_previous);
						        	System.out.println("Number achieved"+String.valueOf(other_number_achieved_for_entry));
						        	int other_number_remaining_for_entry=Integer.valueOf(number)-other_number_achieved_for_entry;
						        	if(db.insertJustification(name, number, other_justification_text, other_comment_text,number,String.valueOf(other_number_achieved_for_entry),String.valueOf(other_number_remaining_for_entry),id, "new_record") !=0){
						    		JSONObject json = new JSONObject();
									 try {
										json.put("id", id);
										 json.put("target_type", name);
										 json.put("category", "other");
										 json.put("start_date", startDate);
										 json.put("due_date", dueDate);
										 json.put("target_number", number);
										 json.put("achieved_number", other_number_achieved_for_entry);
										 json.put("last_updated", getDateTime());
										 if(other_justification_text.equals(" ")){
										 json.put("justification", " ");
										 }else {
										 json.put("justification", other_justification_text); 
										 }
									} catch (JSONException e) {
										e.printStackTrace();
									}
									 end_time=System.currentTimeMillis();
									 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
									 System.out.println(json.toString());
									 Calendar c = Calendar.getInstance();
								        int month=c.get(Calendar.MONTH)+1;
								        int day=c.get(Calendar.DAY_OF_WEEK);
								        int year=c.get(Calendar.YEAR);
								        String today=day+"-"+month+"-"+year;
									 if(other_number_achieved_for_entry==Integer.valueOf(number)){
										 db.updateOtherTarget(other_update_status,other_number_achieved_for_entry,other_number_remaining_for_entry,id);
										 }else if(other_number_achieved_for_entry<Integer.valueOf(number)){
											 db.updateOtherTarget("new_record",other_number_achieved_for_entry,other_number_remaining_for_entry,id);
										 }
									 Intent intent=new Intent(UpdateActivity.this, NewEventPlannerActivity.class);
									 startActivity(intent);
									 finish();
									 Toast.makeText(getApplicationContext(), "Target updated!",
									         Toast.LENGTH_SHORT).show();
								}   	
						                	
								}
								

								if(type.equalsIgnoreCase("learning")){
									String learning_justification_text=justification.getSelectedItem().toString();
						    		String learning_comment_text=comment.getText().toString();
						    		//String other_number_achieved_text=achievedNumber.getText().toString();
						    		//String other_update_status = "updated";
						    		//int other_new_number_achieved=Integer.valueOf(other_number_achieved_text);
						        	//int other_number_achieved_for_entry=other_new_number_achieved+Integer.valueOf(number_achieved_from_previous);
						        	//System.out.println("Number achieved"+String.valueOf(other_number_achieved_for_entry));
						        	//int other_number_remaining_for_entry=Integer.valueOf(number)-other_number_achieved_for_entry;
						        	if(db.insertJustification(name, number, learning_justification_text, learning_comment_text,number,"0","0",id, "new_record") !=0){
						    		JSONObject json = new JSONObject();
									 try {
										json.put("id", id);
										 json.put("target_type", name);
										 json.put("category", "learning");
										 json.put("start_date", startDate);
										 json.put("target_number", 1);
										 json.put("due_date", dueDate);
										//json.put("other_number", number);
										 json.put("achieved_number", learning_achieved_number);
										 json.put("last_updated", getDateTime());
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
									 System.out.println(json.toString());
									 Calendar c = Calendar.getInstance();
								        int month=c.get(Calendar.MONTH)+1;
								        int day=c.get(Calendar.DAY_OF_WEEK);
								        int year=c.get(Calendar.YEAR);
								        String today=day+"-"+month+"-"+year;
									
										 db.updateLearningTarget("updated",id);
										 Intent intent=new Intent(UpdateActivity.this, NewEventPlannerActivity.class);
										 startActivity(intent);
										 finish();
									 Toast.makeText(getApplicationContext(), "Target updated!",
									         Toast.LENGTH_SHORT).show();
								}   	
						                	
								}
							}
							
						});
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
				
			}
			
		});
				
			
	}	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}
	
}
