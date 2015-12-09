package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.model.User;
import org.digitalcampus.oppia.utils.UIUtils;
import org.grameenfoundation.cch.model.FacilityTargets;
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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
	private ArrayList<FacilityTargets> facilityTargetDetails;
	private TableRow other_option;
	private EditText editText_otherOption;
	private String justification_text;
	private long start_time;
	private long end_time;
	private String category;
	private String dueDate;
	private String startDate;
	private Button button_select;
	private SharedPreferences prefs;
	private String staff_id;
	private ArrayList<User> userdetails;
	private String target_group;
	private String target_detail;
	private String target_month;
	private String target_overall;
	private EditText comments;
	JSONObject facilityname;
	private JSONObject group_members;
	private String group_ids;
	
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
	    comments=(EditText) findViewById(R.id.editText_comments);
	    prefs = PreferenceManager.getDefaultSharedPreferences(UpdateFacilityTargetsActivity.this);
	    staff_id=prefs.getString("first_name", "name");
	    try{
	    	userdetails=new ArrayList<User>();
	    	userdetails=db.getUserFirstName(staff_id);
	    	facilityname=new JSONObject(userdetails.get(0).getUserFacility());
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    number=(EditText) findViewById(R.id.editText_number);
	    radioGroup_group=(RadioGroup) findViewById(R.id.radioGroup_groups);
	    radioGroup_justification=(RadioGroup) findViewById(R.id.radioGroup_justification);
	    other_option=(TableRow) findViewById(R.id.other_option);
		editText_otherOption=(EditText) findViewById(R.id.editText_otherOption);
		other_option.setVisibility(View.GONE);
		start_time=System.currentTimeMillis();
        button_update=(Button) findViewById(R.id.button_submit);
        facilityTargetDetails=new ArrayList<FacilityTargets>();
        justification=(MaterialSpinner) findViewById(R.id.spinner_justification);
        String[] justifications=getResources().getStringArray(R.array.Justification);
        ArrayAdapter<String> justification_adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,justifications);
        justification.setAdapter(justification_adapter);
       
		 Bundle extras = getIntent().getExtras(); 
	     if (extras != null) {
	    	 try{
	          id= extras.getLong("id");
	          facilityTargetDetails= db.getTargetsById((int)id);
	          target_number=extras.getString("target_number");
	          name=extras.getString("target_type");
	          number_achieved_from_previous=extras.getString("target_achieved");
	          category= extras.getString("category");
	          dueDate=extras.getString("due");
	          startDate=extras.getString("start");
	          target_group=extras.getString("target_group");
	          target_detail=extras.getString("target_detail");
	          target_month=extras.getString("month");
	          target_overall=extras.getString("target_overall");
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
									 end_time=System.currentTimeMillis();
										int event_new_number_achieved=Integer.valueOf(number.getText().toString());
										int event_number_achieved_for_entry=event_new_number_achieved+Integer.valueOf(number_achieved_from_previous);
										int event_number_remaining_for_entry=Integer.valueOf(target_number)-event_number_achieved_for_entry;
											db.editFacilityTargetUpdate(groups.getText().toString(),
													String.valueOf(event_number_achieved_for_entry), 
													String.valueOf(event_number_remaining_for_entry), 
													returnUpdateStatus(event_number_achieved_for_entry,Integer.valueOf(target_number)),
													db.getDateTime(),id);
											 try {
												db.insertFacilityTargetUpdate((int)id, 
															facilityTargetDetails.get(0).getTargetType(),
															facilityTargetDetails.get(0).getTargetCategory(),
															facilityTargetDetails.get(0).getTargetDetail(),
															facilityTargetDetails.get(0).getTargetGroup(),
															facilityTargetDetails.get(0).getTargetOverall(),
															facilityTargetDetails.get(0).getTargetNumber(),
															number.getText().toString(), 
															String.valueOf(event_number_remaining_for_entry),
															returnUpdateStatus(event_number_achieved_for_entry,Integer.valueOf(target_number)),
															String.valueOf(end_time), 
															groups.getText().toString(),
															returnMonth(facilityTargetDetails.get(0).getTargetMonth()),
															comments.getText().toString(), 
															justification_text,
															facilityname.getString("name").toString(),
															userdetails.get(0).getUserZone());
											
												 json.put("target_id", id);
												 json.put("target_type", facilityTargetDetails.get(0).getTargetType());
												 json.put("target_category", facilityTargetDetails.get(0).getTargetCategory());
												 json.put("target_detail", facilityTargetDetails.get(0).getTargetDetail());
												 json.put("target_number", facilityTargetDetails.get(0).getTargetNumber());
												 json.put("target_month", returnMonth(facilityTargetDetails.get(0).getTargetMonth()));
												 json.put("target_group", facilityTargetDetails.get(0).getTargetGroup());
												 json.put("achieved_number", number.getText().toString());
												 json.put("last_updated",String.valueOf(end_time));
												 json.put("facility",facilityname.getString("name").toString());
												 json.put("zone",userdetails.get(0).getUserZone());
												 json.put("justification", justification_text); 
												 json.put("comments", comments.getText().toString()); 
												 if(!groups.getText().toString().equals("No participants selected")){
													 json.put("group_members", group_ids);
												 }else{
													 json.put("group_members", "no group");
												 }
												 json.put("ver", db.getVersionNumber(getApplicationContext()));
												 json.put("battery", db.getBatteryStatus(getApplicationContext()));
											     json.put("device", db.getDeviceName());
											     json.put("imei", db.getDeviceImei(getApplicationContext()));
												 
											} catch (Exception e) {
												e.printStackTrace();
											}
											 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
											 UIUtils.showAlert(UpdateFacilityTargetsActivity.this, "Message", "Congratulations, you have achieved your target. Visit the achievement center for details.");
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
	            group_ids=data.getStringExtra("group_ids");
	            
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
	public String returnMonth(String month){
		String monthInt = null;
		switch (month) {
		case "January":
			monthInt="1";
			break;
		case "February":
			monthInt="2";
			break;
		case "March":
			monthInt="3";
			break;
		case "April":
			monthInt="4";
			break;
		case "May":
			monthInt="5";
			break;
		case "June":
			monthInt="6";
			break;
		case "July":
			monthInt="7";
			break;
		case "August":
			monthInt="8";
			break;
		case "September":
			monthInt="9";
			break;
		case "October":
			monthInt="10";
			break;
		case "November":
			monthInt="11";
			break;
		case "December":
			monthInt="12";
			break;
		default:
			break;
		}
		return monthInt;
	}
	public String returnUpdateStatus(int achieved_number,int target_number){
		String status = null;
		if(achieved_number==target_number){
			status=MobileLearning.CCH_TARGET_STATUS_UPDATED;
		}else if(achieved_number>target_number){
			status=MobileLearning.CCH_TARGET_STATUS_UPDATED;
		}else if(achieved_number<target_number){
			status=MobileLearning.CCH_TARGET_STATUS_NEW;
		}
		
		return status;
	}
}
