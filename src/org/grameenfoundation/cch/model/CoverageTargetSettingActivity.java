package org.grameenfoundation.cch.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.cch.activity.EventPlannerOptionsActivity;
import org.grameenfoundation.cch.caldroid.CaldroidFragment;
import org.grameenfoundation.cch.caldroid.CaldroidListener;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class CoverageTargetSettingActivity extends Fragment implements android.widget.CompoundButton.OnCheckedChangeListener{
	
	private View rootView;
	private Context mContext;
	private DbHelper db;
	private TextView dueDateValue;
	private TextView startDateValue;
	private ImageButton datepickerDialog;
	private ImageButton datepickerDialog2;
	private MaterialSpinner spinner_coverageName;
	private DateTime today;
	private RadioGroup category_options;
	private RadioButton category_people;
	private String coverage_category;
	private String[] items3;
	private LinearLayout description;
	private LinearLayout values;
	private EditText target_number;
	private CheckBox cb;
	private MaterialSpinner spinner_coveragePeriod;
	private String due_date;
	private String start_date;
	protected String[] array;
	protected Long start_time;
	private Long end_time;
	private List<EditText> allEds;
	private List<CheckBox> allCb;
	 public CoverageTargetSettingActivity(){

}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 
			rootView=inflater.inflate(R.layout.coverage_targetsetting,null,false);
			 mContext=getActivity().getApplicationContext();
			 db=new DbHelper(getActivity());
			 today=new DateTime();
			 start_time=System.currentTimeMillis();
			 
			 prepareStartUp();
			 datepickerDialog=(ImageButton) rootView.findViewById(R.id.imageButton_dueDate);
			datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", today.getMonthOfYear(), today.getYear());
						dialogCaldroidFragment.show(getFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	dueDateValue.setText(formatter2.format(date));
						    	dueDateValue.setError(null);
						    	due_date=formatter.format(date);
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
					}
					
				});
				datepickerDialog2=(ImageButton) rootView.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", today.getMonthOfYear(), today.getYear());
						dialogCaldroidFragment.show(getFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MMM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	startDateValue.setText(formatter2.format(date));
						    	startDateValue.setError(null);
						    	start_date=formatter.format(date);
						    	Date today=new Date();
					        	if(Validation.isDateAfter(formatter.format(today),formatter.format(date))==true){
					        		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					        				getActivity());
										alertDialogBuilder.setTitle("Date Confirmation");
										alertDialogBuilder
											.setMessage("You selected a start date in the past. Click ok to proceed")
											.setCancelable(false)
											.setIcon(R.drawable.ic_error)
											.setPositiveButton("OK",new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,int id) {
													dialog.cancel();
												}
											  });
										AlertDialog alertDialog = alertDialogBuilder.create();
										alertDialog.show();
					        	}
						        Toast.makeText(getActivity(), formatter2.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
					}
					
				});

				Button dialogButton = (Button) rootView.findViewById(R.id.button_dialogAddCoverage);
				dialogButton.setText("Save");
				dialogButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						String coverage_name=spinner_coverageName.getSelectedItem().toString();
						String coverage_period=spinner_coveragePeriod.getSelectedItem().toString();
						if(!checkValidation()){
					      			Toast.makeText(getActivity().getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
					      	}else{
					      		System.out.println("Check: "+String.valueOf(description.getChildCount()));
					//long id=db.insertCoverageSet(coverage_name, coverage_detail, coverage_period, coverage_number, duration,start_date,due_date,0,Integer.valueOf(coverage_number),"new_record") ;
					      		for(int i=0;i<description.getChildCount();i++){
					      			//if(view instanceof CheckBox){
					      				//cb=(CheckBox)values.getChildAt(i).findViewById(i);
					      				//target_number=(EditText)description.getChildAt(i).findViewById(i);
					      				//
					      			if(allCb.get(i).isChecked()){
					      					//target_number=(EditText)view.findViewById(i);
					      				long id=db.insertTarget(0, 
					      						MobileLearning.CCH_TARGET_TYPE_COVERAGE,
					      						coverage_name,
					      						allCb.get(i).getText().toString(),
					      						coverage_category,
					      						Integer.parseInt(allEds.get(i).getText().toString()), 
					      						0,
					      						Integer.parseInt(allEds.get(i).getText().toString()), 
					      						start_date,
					      						due_date,
					      						coverage_period,
					      						MobileLearning.CCH_TARGET_STATUS_NEW,
					      						"Not yet");
					      				
					      				if(id!=0){
					      					JSONObject json = new JSONObject();
					      					try {
					      						json.put("id", id);
					      						json.put("target_type", allCb.get(i).getText().toString());
					      						json.put("category", "coverage");
					      						json.put("start_date", start_date);
					      						json.put("target_number", allEds.get(i).getText().toString());
					      						json.put("due_date", due_date);
					      						json.put("achieved_number", 0);
					      						json.put("last_updated", db.getDateTime());
					      						json.put("ver", db.getVersionNumber(getActivity().getApplicationContext()));
					      						json.put("battery", db.getBatteryStatus(getActivity().getApplicationContext()));
					      						json.put("device", db.getDeviceName());
					      						json.put("imei", db.getDeviceImei(getActivity().getApplicationContext()));
					      						end_time=System.currentTimeMillis();
					      						db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
					      					} catch (JSONException e) {
					      						e.printStackTrace();
					      					}
					      					Toast.makeText(getActivity().getApplicationContext(), "Coverage target added successfully!",
					      							Toast.LENGTH_LONG).show();
					      				}else{
					      					Toast.makeText(getActivity().getApplicationContext(), "Oops! Something went wrong. Please try again",
					      							Toast.LENGTH_LONG).show();
					      				}
					      				
					      			//}
					      				
					      			}
					      			}
					      			Intent intent2 = new Intent(Intent.ACTION_MAIN);
					      			intent2.setClass(getActivity(), EventPlannerOptionsActivity.class);
					      			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					 	        //  startActivity(intent2);
					 	          //getActivity().finish();	
					 	         //getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
							    	
					      	}
						}
					});
				
		return rootView;	
	 }
	
	public void prepareStartUp(){
		Button cancel=(Button) rootView.findViewById(R.id.button_cancel);
		cancel.setVisibility(View.GONE);
		 dueDateValue=(TextView) rootView.findViewById(R.id.textView_dueDateValue);
		 startDateValue=(TextView) rootView.findViewById(R.id.textView_startDate);
		 spinner_coverageName=(MaterialSpinner) rootView.findViewById(R.id.spinner_dialogCoverageName);
		 category_options=(RadioGroup) rootView.findViewById(R.id.radioGroup_category);
		  category_options.check(R.id.radio_people);
		  category_people=(RadioButton) rootView.findViewById(R.id.radio_people);
		  category_people.setChecked(true);
		  coverage_category="Indicators";
		  items3=new String[]{};
		  items3=getResources().getStringArray(R.array.CoverageIndicatorsName);
		  ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
		  spinner_coverageName.setAdapter(adapter3);
		  description=(LinearLayout) rootView.findViewById(R.id.linearLayout_description);
		  category_options.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.radio_people) {
					coverage_category="Indicators";
					 items3=new String[]{};
					 items3=getResources().getStringArray(R.array.CoverageIndicatorsName);
					ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
					spinner_coverageName.setAdapter(adapter3);
				} else if (checkedId == R.id.radio_immunization) {
					coverage_category="Immunizations";
					items3=new String[]{};
					items3=getResources().getStringArray(R.array.CoverageImmunizationTypes);
					ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items3);
					spinner_coverageName.setAdapter(adapter4);
				}
			}
		});
		  spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener() {

			

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				allEds = new ArrayList<EditText>();
				allCb = new ArrayList<CheckBox>();
				if(spinner_coverageName.getSelectedItem().toString().equals("Family Planning")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageIndicatorsDetailFamilyPlanning);
					
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
					
				}else if (spinner_coverageName.getSelectedItem().toString().equals("Age Groups")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageIndicatorsDetailAgeGroups);
					
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
				}else if (spinner_coverageName.getSelectedItem().toString().equals("School Health")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageIndicatorsSchoolHealth);
					
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
				}else if (spinner_coverageName.getSelectedItem().toString().equals("BCG")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageImmunizationTypeBCG);
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
				}else if (spinner_coverageName.getSelectedItem().toString().equals("Penta")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageImmunizationPenta);
					
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
				}else if (spinner_coverageName.getSelectedItem().toString().equals("OPV")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageImmunizationOPV);
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
				}else if (spinner_coverageName.getSelectedItem().toString().equals("ROTA")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageImmunizationROTA);
					
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
				}else if (spinner_coverageName.getSelectedItem().toString().equals("PCV")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageImmunizationPCV);
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
				}else if (spinner_coverageName.getSelectedItem().toString().equals("Measles Rubella")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageImmunizationMeaslesRubella);
					
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
				}else if (spinner_coverageName.getSelectedItem().toString().equals("Vitamin A")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageImmunizationVitaminA);
					
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
				}else if (spinner_coverageName.getSelectedItem().toString().equals("TT pregnant")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageImmunizationTT);
					
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
				}else if (spinner_coverageName.getSelectedItem().toString().equals("TT non-pregnant")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageImmunizationTT);
					
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
				}else if (spinner_coverageName.getSelectedItem().toString().equals("Yellow Fever")){
					array=new String[]{};
					array=getResources().getStringArray(R.array.CoverageImmunizationYellowFever);
					
					description.removeAllViews();
					for(int i=0;i<array.length;i++){
						values=new LinearLayout(getActivity());
						values.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						target_number=new EditText(getActivity());
						allEds.add(target_number);
						target_number.setId(i);
						target_number.setEms(10);
						target_number.setInputType(InputType.TYPE_CLASS_NUMBER);
						target_number.setVisibility(View.GONE);
						cb=new CheckBox(getActivity());
						allCb.add(cb);
						cb.setId(i);
						cb.setOnCheckedChangeListener(CoverageTargetSettingActivity.this);
						values.setOrientation(LinearLayout.HORIZONTAL);
						cb.setText(array[i]);
						values.addView(cb);
						values.addView(target_number);
						description.addView(values);
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		  String[] items2=getResources().getStringArray(R.array.ReminderFrequency);
			spinner_coveragePeriod=(MaterialSpinner) rootView.findViewById(R.id.spinner_coveragePeriod);
			ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items2);
			spinner_coveragePeriod.setAdapter(adapter2);
			
	}
	
	
	private boolean checkValidation() {
        boolean ret = true;
 
        if (!Validation.hasTextTextView(startDateValue)) ret = false;
        if (!Validation.hasTextTextView(dueDateValue)) ret = false;
        if (!Validation.hasSelection(spinner_coverageName)) ret = false;
        if (!Validation.hasSelection(spinner_coveragePeriod)) ret = false;
        if (Validation.isDateAfter(start_date,due_date,startDateValue)) ret = false;
        for (int i=0;i<description.getChildCount();i++){
        	if(allCb.get(i).isChecked()){
        		if(!Validation.hasTextEditText(allEds.get(i)))ret = false;
        	}
        }
        return ret;
    }
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		for(int i=0;i<array.length;i++){
			if(allCb.get(i).isChecked()){
				allEds.get(i).setVisibility(View.VISIBLE);
			}else{
				allEds.get(i).setVisibility(View.GONE);
			}
		}
	}	
}
