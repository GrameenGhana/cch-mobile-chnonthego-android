package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.model.User;
import org.digitalcampus.oppia.utils.UIUtils;
import org.grameenfoundation.adapters.FacilityTargetBulkUpdateAdapter;
import org.grameenfoundation.cch.model.FacilityTargets;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.grameenfoundation.cch.utils.MultiSelectSpinner;
import org.grameenfoundation.cch.utils.MultiSelectSpinner.MultiSpinnerListener;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class FacilityTargetBulkUpdateActivity extends Activity implements android.widget.CompoundButton.OnCheckedChangeListener {

	private MaterialSpinner justification;
	//private ListView targets_listview;
	private Button button_update;
	private DbHelper db;
	private ArrayList<FacilityTargets> facilityTargets;
	private DateTime currentDate;
	//private FacilityTargetBulkUpdateAdapter adapter;
	private RadioGroup radioGroup_justification;
	private CheckBox cb;
	/** Called when the activity is first created. */
	private LinearLayout parentView;
	private List<EditText> allEds;
	private List<CheckBox> allCb;
	private LinearLayout childViewOne;
	private LinearLayout childViewTwo;
	private EditText number_achieved;
	private TextView target_achieved;
	private double percentage;
	private String percentage_achieved;
	private RadioGroup radioGroup_group;
	private ArrayList<String> groupmembernames;
	private ArrayList<User> userdetails;
	private TableRow other_option;
	private EditText editText_otherOption;
	private LinearLayout linearLayout_comment;
	private String justification_text;
	private String category;
	private long start_time;
	private long end_time;
	private Button button_select;
	private TextView groups;
	private SharedPreferences prefs;
	private String staff_id;
	private JSONObject facilityname;
	private EditText comments;
	private String group_ids;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_target_bulk_update);
	    getActionBar().setTitle("Planner");
		getActionBar().setSubtitle("Update Facility Targets");
		start_time=System.currentTimeMillis();
	    db=new DbHelper(FacilityTargetBulkUpdateActivity.this);
	    groups=(TextView) findViewById(R.id.textView_group);
	    prefs = PreferenceManager.getDefaultSharedPreferences(FacilityTargetBulkUpdateActivity.this);
	    staff_id=prefs.getString("first_name", "name");
	    try{
	    	userdetails=new ArrayList<User>();
	    	userdetails=db.getUserFirstName(staff_id);
	    	facilityname=new JSONObject(userdetails.get(0).getUserFacility());
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    justification=(MaterialSpinner) findViewById(R.id.spinner_justification);
	    button_update=(Button) findViewById(R.id.button_submit);
	    radioGroup_justification=(RadioGroup) findViewById(R.id.radioGroup_justification);
	    String[] items=getResources().getStringArray(R.array.Justification);
	    other_option=(TableRow) findViewById(R.id.other_option);
		linearLayout_comment=(LinearLayout) findViewById(R.id.LinearLayout_comment);
		comments=(EditText) findViewById(R.id.editText_comment);
		editText_otherOption=(EditText) findViewById(R.id.editText_otherOption);
		other_option.setVisibility(View.GONE);
		ArrayAdapter<String> adapter2=new ArrayAdapter<String>(FacilityTargetBulkUpdateActivity.this, android.R.layout.simple_list_item_1, items);
		justification.setAdapter(adapter2);
		allEds = new ArrayList<EditText>();
		allCb = new ArrayList<CheckBox>();
	    currentDate=new DateTime();
	    facilityTargets=new ArrayList<FacilityTargets>();
		prepareStartUp();
		button_select=(Button) findViewById(R.id.button_select);
		button_select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(FacilityTargetBulkUpdateActivity.this, GroupParticipantsSelectActivity.class);
				startActivityForResult(i, 1);
				
			}
		});
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
	    	
	    	button_update.setOnClickListener(new OnClickListener() {	
	    		JSONObject json = new JSONObject();
				@Override
				public void onClick(View v) {
					if(!checkValidation()){
		      			Toast.makeText(FacilityTargetBulkUpdateActivity.this, "Provide data for required fields!", Toast.LENGTH_LONG).show();
		      	}else{
		      		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
		      				FacilityTargetBulkUpdateActivity.this);
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
					for (int i=0;i<parentView.getChildCount();i++){
						if(allCb.get(i).isChecked()){
							 end_time=System.currentTimeMillis();
							int event_new_number_achieved=Integer.valueOf(allEds.get(i).getText().toString());
				        	int event_number_achieved_for_entry=event_new_number_achieved+Integer.valueOf(facilityTargets.get(i).getTargetNumberAchieved());
				        	int event_number_remaining_for_entry=Integer.valueOf(facilityTargets.get(i).getTargetNumber())-event_number_achieved_for_entry;
								db.editFacilityTargetUpdate(groups.getText().toString(), 
										String.valueOf(event_number_achieved_for_entry), 
									String.valueOf(event_number_remaining_for_entry), 
									MobileLearning.CCH_TARGET_STATUS_UPDATED, db.getDateTime(), 
									Long.parseLong(facilityTargets.get(i).getTargetId()));
								 try {
									db.insertFacilityTargetUpdate(Integer.parseInt(facilityTargets.get(i).getTargetId()), 
												facilityTargets.get(i).getTargetType(),
												category, facilityTargets.get(i).getTargetDetail(),
												facilityTargets.get(i).getTargetGroup(),
												facilityTargets.get(i).getTargetOverall(),
												facilityTargets.get(i).getTargetNumber(),
												allEds.get(i).getText().toString(), 
												String.valueOf(event_number_remaining_for_entry),
												returnUpdateStatus(event_number_achieved_for_entry,
												Integer.valueOf(facilityTargets.get(i).getTargetNumber())),
												String.valueOf(end_time), 
												groups.getText().toString(),
												returnMonth(facilityTargets.get(i).getTargetMonth()),
												comments.getText().toString(), 
												justification_text,
												facilityname.getString("name").toString(),
												userdetails.get(0).getUserZone());
									 json.put("target_id", facilityTargets.get(i).getTargetId());
									 //if(facilityTargets.get(i).getTargetDetail().equals("")){
										 json.put("target_type", facilityTargets.get(i).getTargetType());
									// }else{
										// json.put("target_type", facilityTargets.get(i).getTargetDetail());
									 //}
									 json.put("target_category", facilityTargets.get(i).getTargetCategory());
									 json.put("target_number", facilityTargets.get(i).getTargetNumber());
									 json.put("target_month", returnMonth(facilityTargets.get(i).getTargetMonth()));
									 json.put("target_group", facilityTargets.get(i).getTargetGroup());
									 json.put("target_detail", facilityTargets.get(i).getTargetDetail());
									 json.put("achieved_number", allEds.get(i).getText().toString());
									 json.put("last_updated",String.valueOf(end_time));
									 json.put("facility",facilityname.getString("name").toString());
									 json.put("zone",userdetails.get(0).getUserZone());
									 json.put("justification", justification_text); 
									 json.put("comments", comments.getText().toString()); 
									 if(!groups.getText().toString().equals("No participants selected")){
											 json.put("group_members",group_ids);
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
						}
						
					}
				 UIUtils.showAlert(FacilityTargetBulkUpdateActivity.this, "Message", "Target successfully updated! Visit the achievement center to see progress");
					prepareStartUp();
				}
					});
						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
		      	}
		
				}
			});
	}
	public void prepareStartUp(){
		 Bundle extras = getIntent().getExtras(); 
	     if (extras != null) {
	    	 try{
	          category= extras.getString("category");
	    	 }catch(Exception e){
	    		 e.printStackTrace();
	    	 }
	        }
	     parentView=(LinearLayout) findViewById(R.id.parent_view);
		 parentView.removeAllViews();
		facilityTargets=db.getTargetsForMonthViewAgeGroups(currentDate.toString("MMMM"),category);
		if(facilityTargets.size()<=0){
			TextView message=new TextView(FacilityTargetBulkUpdateActivity.this);
			message.setText("Wohoo! All targets updated!");
			parentView.addView(message);
		}
	    
		for(int i=0;i<facilityTargets.size();i++){
			 int number_achieved_today=Integer.valueOf(facilityTargets.get(i).getTargetNumberAchieved());
			 percentage= ((double)number_achieved_today/Integer.valueOf(facilityTargets.get(i).getTargetNumber()))*100;	
			 percentage_achieved=String.format("%.0f", percentage);
			childViewOne=new LinearLayout(FacilityTargetBulkUpdateActivity.this);
		    childViewOne.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			childViewOne.setOrientation(LinearLayout.HORIZONTAL);
			
			childViewTwo=new LinearLayout(FacilityTargetBulkUpdateActivity.this);
			childViewTwo.setLayoutParams(new LayoutParams(300, LayoutParams.WRAP_CONTENT));
			childViewTwo.setOrientation(LinearLayout.VERTICAL);
			number_achieved=new EditText(FacilityTargetBulkUpdateActivity.this);
			target_achieved=new TextView(FacilityTargetBulkUpdateActivity.this);
			target_achieved.setPadding(20, 0, 0, 0);
			allEds.add(number_achieved);
			number_achieved.setId(i);
			number_achieved.setWidth(100);;
			number_achieved.setInputType(InputType.TYPE_CLASS_NUMBER);
			cb=new CheckBox(FacilityTargetBulkUpdateActivity.this);
			cb.setTextColor(getResources().getColor(R.color.TextColorWine));
			cb.setOnCheckedChangeListener(this);
			number_achieved.setVisibility(View.GONE);
			
			allCb.add(cb);
			cb.setId(i);
			 if(facilityTargets.get(i).getTargetDetail().equals("")){
				 cb.setText(facilityTargets.get(i).getTargetType());
			 }else{
				 cb.setText(facilityTargets.get(i).getTargetDetail());
			 }
			target_achieved.setText(facilityTargets.get(i).getTargetNumberAchieved()+"/"+facilityTargets.get(i).getTargetNumber()+" ("+percentage_achieved+") %");
			childViewTwo.addView(cb);
			childViewTwo.addView(target_achieved);
			childViewOne.addView(childViewTwo);
			childViewOne.addView(number_achieved);
			
			parentView.setDividerDrawable(getResources().getDrawable(R.drawable.divider));
			parentView.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			parentView.addView(childViewOne);
		}
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
        for (int i=0;i<parentView.getChildCount();i++){
        	if(allCb.get(i).isChecked()){
        		if(!Validation.hasTextEditText(allEds.get(i)))ret = false;
        	}
        }
        return ret;
    }
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		for (int i=0;i<facilityTargets.size();i++){
			if(allCb.get(i).isChecked()){
				allEds.get(i).setVisibility(View.VISIBLE);
			}else {
				allEds.get(i).setVisibility(View.GONE);
			}
		}
		
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
