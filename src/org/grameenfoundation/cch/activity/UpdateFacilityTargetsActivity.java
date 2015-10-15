package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.utils.UIUtils;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.grameenfoundation.cch.utils.MultiSelectSpinner;
import org.grameenfoundation.cch.utils.MultiSelectSpinner.MultiSpinnerListener;
import org.json.JSONException;
import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateFacilityTargetsActivity extends Activity {

	private TextView target;
	private TextView status;
	private EditText number;
	private RadioGroup radioGroup_group;
	private RadioGroup radioGroup_justification;
	private MultiSelectSpinner group_spinner;
	private TextView groups;
	private MaterialSpinner justification;
	private DbHelper db;
	private long id;
	private String target_number;
	private String name;
	private String number_achieved_from_previous;
	private Button button_update;
	String selected_group_members;
	private ArrayList<String> groupmembernames;
	private TableRow other_option;
	private EditText editText_otherOption;
	private String justification_text;
	private long start_time;
	private long end_time;
	private String category;
	private String dueDate;
	private String startDate;
	private Button button_select;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_update_facility_targets);
	    db=new DbHelper(this);
	    getActionBar().setTitle("Planner");
		getActionBar().setSubtitle("Update Facility Targets");
	    target=(TextView) findViewById(R.id.textView_target);
	    status=(TextView) findViewById(R.id.textView_status);
	    groups=(TextView) findViewById(R.id.textView2);
	    number=(EditText) findViewById(R.id.editText_number);
	    radioGroup_group=(RadioGroup) findViewById(R.id.radioGroup_groups);
	    radioGroup_justification=(RadioGroup) findViewById(R.id.radioGroup_justification);
	    other_option=(TableRow) findViewById(R.id.other_option);
		editText_otherOption=(EditText) findViewById(R.id.editText_otherOption);
		other_option.setVisibility(View.GONE);
	    //groupmembernames=new ArrayList<String>();
		start_time=System.currentTimeMillis();
		// groupmembernames=db.getAllGroupMembers();
        //group_spinner = (MultiSelectSpinner) findViewById(R.id.spinner1);
       // group_spinner.setVisibility(View.GONE);
		 /*
        group_spinner.setItems(groupmembernames, "Select group members", -1, new MultiSpinnerListener() {
			
			@Override
			public void onItemsSelected(boolean[] selected) {
				
				for(int i=0; i<selected.length; i++) {
					if(selected[i]) {
						Log.i("TAG", i + " : "+ groupmembernames.get(i));
					}
				}
			}
		});*/
        button_update=(Button) findViewById(R.id.button_submit);
        justification=(MaterialSpinner) findViewById(R.id.spinner_justification);
        String[] justifications=getResources().getStringArray(R.array.Justification);
        ArrayAdapter<String> justification_adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,justifications);
        justification.setAdapter(justification_adapter);
       
		 Bundle extras = getIntent().getExtras(); 
	     if (extras != null) {
	    	 try{
	          id= extras.getLong("id");
	          target_number=extras.getString("target_number");
	          name=extras.getString("target_type");
	          number_achieved_from_previous=extras.getString("target_achieved");
	          category= extras.getString("category");
	          dueDate=extras.getString("due");
	          startDate=extras.getString("start");
	          
	    	 }catch(Exception e){
	    		 e.printStackTrace();
	    	 }
	        }
	     // set initial selection
	        target.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
			String first="<font color='#53AB20'>What is the progress towards this target:  </font>";
			String next="<font color='#520000'>"+name+"</font>";
			target.setText(Html.fromHtml(first+next));			
			String one="<font color='#53AB20'>So far, your facility has completed </font>";
			String two="<font color='#520000'>"+number_achieved_from_previous+" </font>";
			String three="<font color='#53AB20'>"+" out of "+"</font>";
			String four="<font color='#520000'>"+target_number+"</font>";
			status.setText(Html.fromHtml(one+two+three+four));
			radioGroup_justification.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId==R.id.radio_no2) {
					justification.setVisibility(View.GONE);
				}else {
					justification.setVisibility(View.VISIBLE);
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
						}
					});
				}
			}
		});
			button_select=(Button) findViewById(R.id.button_select);
			button_select.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(UpdateFacilityTargetsActivity.this, GroupParticipantsSelectActivity.class);
					startActivityForResult(i, 1);
					
				}
			});
        /*
        radioGroup_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId==R.id.radio_no) {
					group_spinner.setVisibility(View.GONE);
				}else {
					group_spinner.setVisibility(View.VISIBLE);
				}
			}
		});
        */
        button_update.setOnClickListener(new OnClickListener() {
        	JSONObject json = new JSONObject();
			@Override
			public void onClick(View v) {
				if(!checkValidation()){
	      			Toast.makeText(UpdateFacilityTargetsActivity.this, "Provide data for required fields!", Toast.LENGTH_LONG).show();
				}else{
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
							UpdateFacilityTargetsActivity.this);
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
								private long end_time;

								public void onClick(DialogInterface dialog,int id_negative) {
										int event_new_number_achieved=Integer.valueOf(number.getText().toString());
										int event_number_achieved_for_entry=event_new_number_achieved+Integer.valueOf(number_achieved_from_previous);
										int event_number_remaining_for_entry=Integer.valueOf(target_number)-event_number_achieved_for_entry;
										if(event_number_achieved_for_entry==Integer.valueOf(target_number)){
											db.editFacilityTargetUpdate(groups.getText().toString(),
													String.valueOf(event_number_achieved_for_entry), 
													String.valueOf(event_number_remaining_for_entry), 
													MobileLearning.CCH_TARGET_STATUS_UPDATED,db.getDateTime(),id);
											 try {
												 json.put("target_id", id);
												 json.put("target_type", name);
												 json.put("category", category);
												 json.put("target_number", number);
												 json.put("start_date", startDate);
												 json.put("due_date", dueDate);
												 json.put("achieved_number", event_number_achieved_for_entry);
												 json.put("last_updated", db.getDateTime());
												 json.put("justification", justification_text); 
												 if(!groups.getText().toString().equals("No participants selected")){
													 json.put("group_members", groups.getText().toString());
												 }else{
													 json.put("group_members", "no group");
												 }
												 json.put("ver", db.getVersionNumber(getApplicationContext()));
												 json.put("battery", db.getBatteryStatus(getApplicationContext()));
											     json.put("device", db.getDeviceName());
											     json.put("imei", db.getDeviceImei(getApplicationContext()));
												 
											} catch (JSONException e) {
												e.printStackTrace();
											}
											 end_time=System.currentTimeMillis();
											 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
											 
											 UIUtils.showAlert(UpdateFacilityTargetsActivity.this, "Congratulations!", "Congratulations, you have achieved your target. Visit the achievement center for details.");
										}else if(event_number_achieved_for_entry<Integer.valueOf(target_number)){
											db.editFacilityTargetUpdate(groups.getText().toString(),
													String.valueOf(event_number_achieved_for_entry), 
													String.valueOf(event_number_remaining_for_entry), 
													MobileLearning.CCH_TARGET_STATUS_NEW,db.getDateTime(),id);
											 try {
												 json.put("target_id", id);
												 json.put("target_type", name);
												 json.put("category", category);
												 json.put("target_number", number);
												 json.put("start_date", startDate);
												 json.put("due_date", dueDate);
												 json.put("achieved_number", event_number_achieved_for_entry);
												 json.put("last_updated", db.getDateTime());
												 json.put("justification", justification_text); 
												 if(!groups.getText().toString().equals("No participants selected")){
													 json.put("group_members", groups.getText().toString());
												 }else{
													 json.put("group_members", "no group");
												 }
												 json.put("ver", db.getVersionNumber(getApplicationContext()));
												 json.put("battery", db.getBatteryStatus(getApplicationContext()));
											     json.put("device", db.getDeviceName());
											     json.put("imei", db.getDeviceImei(getApplicationContext()));
												 
											} catch (JSONException e) {
												e.printStackTrace();
											}
											 end_time=System.currentTimeMillis();
											 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
											UIUtils.showAlert(UpdateFacilityTargetsActivity.this, "Message", "Target successfully Updated");
										}else if(event_number_achieved_for_entry>Integer.valueOf(target_number)){
											db.editFacilityTargetUpdate(groups.getText().toString(),
													String.valueOf(event_number_achieved_for_entry), 
													String.valueOf(event_number_remaining_for_entry), 
													MobileLearning.CCH_TARGET_STATUS_UPDATED,db.getDateTime(),id);
											 try {
												 json.put("target_id", id);
												 json.put("target_type", name);
												 json.put("category", category);
												 json.put("target_number", number);
												 json.put("start_date", startDate);
												 json.put("due_date", dueDate);
												 json.put("achieved_number", event_number_achieved_for_entry);
												 json.put("last_updated", db.getDateTime());
												 json.put("justification", justification_text); 
												 if(!groups.getText().toString().equals("No participants selected")){
													 json.put("group_members", groups.getText().toString());
												 }else{
													 json.put("group_members", "no group");
												 }
												 json.put("ver", db.getVersionNumber(getApplicationContext()));
												 json.put("battery", db.getBatteryStatus(getApplicationContext()));
											     json.put("device", db.getDeviceName());
											     json.put("imei", db.getDeviceImei(getApplicationContext()));
												 
											} catch (JSONException e) {
												e.printStackTrace();
											}
											 end_time=System.currentTimeMillis();
											 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
											UIUtils.showAlert(UpdateFacilityTargetsActivity.this, "Congratulations!", "Congratulations, you have achieved your target. Visit the achievement center for details.");
										}
								}
								});
						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
					}
				}
        });
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == 1) {
	        if(resultCode == Activity.RESULT_OK){
	            String result=data.getStringExtra("groups");
	            groups.setText(result);
	        }
	        if (resultCode == Activity.RESULT_CANCELED) {
	          groups.setText("No participants selected");
	        }
	    }
	}
	private boolean checkValidation() {
        boolean ret = true;
        if (justification.isShown()&&!Validation.hasSelection(justification))ret = false;
      //  if (group_spinner.isShown()&&!Validation.hasMultiSelection(group_spinner))ret = false;
        if(!Validation.hasTextEditText(number))ret = false;
        if(other_option.isShown()&&!Validation.hasTextEditText(editText_otherOption))ret = false;
        return ret;
    }
}
