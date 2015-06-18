package org.grameenfoundation.cch.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.caldroid.CaldroidFragment;
import org.grameenfoundation.cch.caldroid.CaldroidListener;
import org.grameenfoundation.cch.model.Validation;
import org.grameenfoundation.cch.utils.MaterialSpinner;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LearningTargetsDetailActivity extends FragmentActivity {

	
	private ImageView imageView_status;
	private String status;
	private long learning_id;
	private DbHelper db;
	private TextView textView_category;
	private TextView textView_course;
	private TextView textView_topic;
	private TextView textView_dueDate;
	private Button button_edit;
	private Button button_delete;
	private Button button_update;
	private String start_date_extra;
	private TextView textView_startDate;
	private static TextView dueDateValue;
	private static TextView learning_period;
	private static String due_date;
	private static String due_date_extra;
	static String start_date ;
	static long due_date_to_compare;
	private static TextView startDateValue;
	private long end_time;
	private long start_time;
	private String last_updated;
	private String period;
	private TextView day_difference;
	private int today_month;
	private int today_day;
	private int today_year;
	public long learning_old_id;
	private String learning_target_name;
	private String learning_target_detail;
	private String learning_target_category;
	private MaterialSpinner target_name;
	private MaterialSpinner target_category;
	private MaterialSpinner target_detail;
	private MaterialSpinner spinner_period;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_learning_target_detail);
	    db=new DbHelper(LearningTargetsDetailActivity.this);
	    getActionBar().setTitle("Planner");
	    getActionBar().setSubtitle("Target Details");
	    Calendar c = Calendar.getInstance();
        today_month=c.get(Calendar.MONTH)+1;
        today_day=c.get(Calendar.DAY_OF_WEEK);
        today_year=c.get(Calendar.YEAR);
	    start_time=System.currentTimeMillis();
	    textView_category=(TextView) findViewById(R.id.textView_learningCategory);
	    textView_course=(TextView) findViewById(R.id.textView_learningCourse);
	    textView_topic=(TextView) findViewById(R.id.textView_learningTopic);
	    textView_dueDate=(TextView) findViewById(R.id.textView_dueDate);
	    textView_startDate=(TextView) findViewById(R.id.textView_startDate);
	    learning_period=(TextView) findViewById(R.id.textView_period);
	    imageView_status=(ImageView) findViewById(R.id.imageView_status);
	    day_difference=(TextView) findViewById(R.id.textView_dayDifference);
	    button_edit=(Button) findViewById(R.id.button_edit);
	    button_delete=(Button) findViewById(R.id.button_delete);
	    button_update=(Button) findViewById(R.id.button_update);
	    new GetData().execute();
        button_edit.setOnClickListener(new OnClickListener(){

		

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(LearningTargetsDetailActivity.this);
				dialog.setContentView(R.layout.learning_add_dialog);
				dialog.setTitle("Edit Learning Target");
			    target_name=(MaterialSpinner) dialog.findViewById(R.id.spinner_learningHeader);
				String[] items={"Family Planning","Maternal and Child Health"};
				target_category=(MaterialSpinner) dialog.findViewById(R.id.spinner_learningDescription);
				final Button button_show=(Button) dialog.findViewById(R.id.button_show);
				button_show.setVisibility(View.GONE);
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
				target_name.setAdapter(adapter);
				target_detail=(MaterialSpinner) dialog.findViewById(R.id.spinner_learningCourse);
				String[] items2={"Family Planning 101",
						"Family Planning Counselling",
						"Family Planning for people living with HIV",
						"Hormonal Contraceptives",
						"Postpartum Family Planning"};
				ArrayAdapter<String> adapter2=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items2);
				target_detail.setAdapter(adapter2);
				String[] items3=new String[]{"Rationale for voluntary family planning",
				"Family Planning method considerations",
				"Short-acting contraceptive methods",
				"Long-acting contraceptive methods",
				"Special needs"};
		ArrayAdapter<String> adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items3);
		target_category.setAdapter(adapter3);
				target_name.setOnItemSelectedListener(new OnItemSelectedListener(){
					
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if(target_name.getSelectedItem().toString().equalsIgnoreCase("Family Planning")){
							String[] items2={"Family Planning 101",
											"Family Planning Counselling",
											"Family Planning for people living with HIV",
											"Hormonal Contraceptives",
											"Postpartum Family Planning"};
							ArrayAdapter<String> adapter2=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items2);
							target_detail.setAdapter(adapter2);
							String[] items=new String[]{"Rationale for voluntary family planning",
									"Family Planning method considerations",
									"Short-acting contraceptive methods",
									"Long-acting contraceptive methods",
									"Special needs"};
							ArrayAdapter<String> adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_name.getSelectedItem().toString().equalsIgnoreCase("Maternal and Child Health")){
							String[] items2_1={"Essential Newborn care","Antenatal Care",
												"Diarrhoea Disease","Emergency obstetrics",
												"Malaria in Pregnancy",
												"Postpartum Care",
												"Preventing Postpartum Hemorrhage"};
							ArrayAdapter<String> adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items2_1);
							target_detail.setAdapter(adapter3);
							String[] items=new String[]{"Newborn mortality",
									"Care during labor and birth",
									"Newborn care following birth",
									"Newborn care following birth: Later",
									"Infant feeding",
									"Household to hospital continuum"};
							ArrayAdapter<String> adapter4=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter4);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
				spinner_period=(MaterialSpinner) dialog.findViewById(R.id.spinner_period);
				final String[] items_period=getResources().getStringArray(R.array.ReminderFrequency);
				ArrayAdapter<String> adapter_period=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items_period);
				spinner_period.setAdapter(adapter_period);
				
				target_detail.setOnItemSelectedListener(new OnItemSelectedListener(){
					
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						String items[];
						ArrayAdapter<String> adapter3;
						if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Family Planning 101")){
							items=new String[]{"Rationale for voluntary family planning",
									"Family Planning method considerations",
									"Short-acting contraceptive methods",
									"Long-acting contraceptive methods",
									"Special needs"};
							adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Family Planning Counselling")){
							items=new String[]{"Family planning counselling",
							"Family planning counselling skills"};
							adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Family Planning for people living with HIV")){
							items= new String[]{"Family Planning/Reproductive Health",
									"Family planning for people living with HIV",
									"Reproductive Health",
									"Helping Clients Make a Family Planning",
									"Family Planning in PMTCT Services"};
							adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Hormonal Contraceptives")){
							items=new String[]{"Hormonal Contraceptives",
									"Oral contraceptives",
									"Emergency contraceptive pills",
									"Injectable contraceptives",
									"Implants",
									"Benefits and risks of hormonal contraceptives"};
							adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Postpartum Family Planning")){
							items=new String[]{"Rationale for postpartum family planning",
									"Contraceptive method considerations",
									"Service delivery:Clinical Considerations",
									"Service delivery:Integration and linkage"};
							adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}
						else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Essential Newborn care")){
							items=new String[]{"Newborn mortality",
									"Care during labor and birth",
									"Newborn care following birth",
									"Newborn care following birth: Later",
									"Infant feeding",
									"Household to hospital continuum"};
							adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Antenatal Care")){
							items=new String[]{"Significance of Antenatal Care",
									"Goal and Principles of Antenatal Care",
									"Elements of Focused (Goal-directed) Assessment",
									"Screening to Detect, Not Predict, Problems",
									"Preventive Measures",
									"Malaria in Pregnancy",
									"HIV in Pregnancy","Syphilis in Pregnancy",
									"Program Considerations"};
							adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Diarrhoea Disease")){
							items=new String[]{"Etiology and Epidemiology",
									"Clinical Assessment and Classification",
									"Treatment",
									"Prevention"};
							adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Emergency obstetrics")){
							items=new String[]{"Background and Definitions",
									"Basic and Comprehensive EmONC",
									"Implementation of EmONC Services"};
							adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Malaria in Pregnancy")){
							items=new String[]{"Why Is Malaria in Pregnancy (MIP) Important?",
									"MIP: Strategic Framework, Main Interventions",
									"Insecticide-treated Nets, Case Management",
									"Partnerships for MIP, MIP Readiness",
									"Case Study: Frequent Problems/Practical Solutions"};
							adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Postpartum Care")){
							items=new String[]{"Postpartum Care: Overview",
									"Field Realities",
									"Preventing Postpartum Mortality and Morbidity One",
									"Preventing Postpartum Mortality and Morbidity Two",
									"Case Study: Frequent Problems/Practical Solutions"};
							adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}else if(target_detail.getSelectedItem().toString().equalsIgnoreCase("Preventing Postpartum Hemorrhage")){
							items=new String[]{"Postpartum Hemorrhage and Maternal Mortality",
									"Causes of Postpartum Hemorrhage",
									"Prevention of Postpartum Hemorrhage One",
									"Prevention of Postpartum Hemorrhage Two: AMTSL"};	
							adapter3=new ArrayAdapter<String>(LearningTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items);
							target_category.setAdapter(adapter3);
						}
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});
				
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddLearning);
				dialogButton.setText("Save");
				dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
				if(due_date_extra!=null){
					dueDateValue.setText(due_date_extra);
				}else{
					dueDateValue.setText("null");
				}
				startDateValue=(TextView) dialog.findViewById(R.id.textView_startDate);
				if(start_date_extra!=null){
					startDateValue.setText(start_date_extra);
				}else{
					startDateValue.setText("null");
				}
				ImageButton datepickerDialog=(ImageButton) dialog.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", today_month, today_year);
						dialogCaldroidFragment.show(getSupportFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	dueDateValue.setText(formatter.format(date));
						    	due_date=formatter.format(date);
						        Toast.makeText(LearningTargetsDetailActivity.this, formatter.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
					}
					
				});
				ImageButton datepickerDialog2=(ImageButton) dialog.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						final CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", today_month, today_year);
						dialogCaldroidFragment.show(getSupportFragmentManager(),"TAG");
						final CaldroidListener listener = new CaldroidListener() {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						    @Override
						    public void onSelectDate(Date date, View view) {
						    	dialogCaldroidFragment.dismiss();
						    	startDateValue.setText(formatter.format(date));
						    	start_date=formatter.format(date);
						        Toast.makeText(LearningTargetsDetailActivity.this, formatter.format(date),
						                Toast.LENGTH_SHORT).show();
						    }
						};

						dialogCaldroidFragment.setCaldroidListener(listener);
						
					}
					
				});
				
				Button cancel=(Button) dialog.findViewById(R.id.button_cancel);
				cancel.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
					dialog.dismiss();
						
					}
					
				});
				
				dialogButton.setOnClickListener(new OnClickListener() {
					

					@Override
					public void onClick(View v) {
						//dialog.dismiss();
						String duration=" ";
						
						learning_target_name=target_name.getSelectedItem().toString();
						learning_target_detail=target_detail.getSelectedItem().toString();
						learning_target_category=target_category.getSelectedItem().toString();
						period=spinner_period.getSelectedItem().toString();
						if(!checkValidation()){
				      		Toast.makeText(getApplicationContext(), "Provide data for required fields!", Toast.LENGTH_LONG).show();
				      	}else{
					    if(db.editTarget(learning_target_name, learning_target_detail,learning_target_category,"0",startDateValue.getText().toString(),dueDateValue.getText().toString(),period, learning_id) ==true){
					    	JSONObject json = new JSONObject();
							 try {
								 if(learning_old_id!=0){
									 json.put("id", learning_old_id);
								 }else{
									 json.put("id", learning_id);
								 }
								 json.put("target_type", learning_target_category);
								 json.put("target_number", 1);
								 json.put("achieved_number", 0);
								 json.put("category", "learning");
								 json.put("last_updated", last_updated);
								 json.put("start_date", start_date_extra);
								 json.put("due_date", due_date_extra);
								 json.put("changed", 1);
								 json.put("deleted", 0);
								 json.put("ver", db.getVersionNumber(LearningTargetsDetailActivity.this));
									json.put("battery", db.getBatteryStatus(LearningTargetsDetailActivity.this));
							    	json.put("device", db.getDeviceName());
							    	json.put("imei", db.getDeviceImei(LearningTargetsDetailActivity.this));
							} catch (JSONException e) {
								e.printStackTrace();
							}
							 end_time=System.currentTimeMillis();
							 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
					    	Intent intent2 = new Intent(Intent.ACTION_MAIN);
				 	          intent2.setClass(LearningTargetsDetailActivity.this, NewEventPlannerActivity.class);
				 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 	          startActivity(intent2);
				 	          finish();	
				 	         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
					    	 Toast.makeText(LearningTargetsDetailActivity.this.getApplicationContext(), "Learning target edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(LearningTargetsDetailActivity.this.getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
					}
				});
	 				dialog.show();
			}
        });
        button_delete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						LearningTargetsDetailActivity.this);
					alertDialogBuilder.setTitle("Delete Confirmation");
					alertDialogBuilder
						.setMessage("You are about to delete this target. Proceed?")
						.setCancelable(false)
						.setIcon(R.drawable.ic_error)
						.setPositiveButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								dialog.cancel();
							}
						  })
						.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								if(db.deleteTarget(learning_id)==true){
									JSONObject json = new JSONObject();
									 try {
										 if(learning_old_id!=0){
											 json.put("id", learning_old_id);
										 }else{
											 json.put("id", learning_id);
										 }
										 json.put("target_type", learning_target_category);
										 json.put("target_number", 1);
										 json.put("achieved_number", 0);
										 json.put("category", "learning");
										 json.put("last_updated", last_updated);
										 json.put("start_date", start_date_extra);
										 json.put("due_date", due_date_extra);
										 json.put("changed", 0);
										 json.put("deleted", 1	);
										 json.put("ver", db.getVersionNumber(LearningTargetsDetailActivity.this));
											json.put("battery", db.getBatteryStatus(LearningTargetsDetailActivity.this));
									    	json.put("device", db.getDeviceName());
									    	json.put("imei", db.getDeviceImei(LearningTargetsDetailActivity.this));
									} catch (JSONException e) {
										e.printStackTrace();
									}
									 end_time=System.currentTimeMillis();
									 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
					        		Intent intent2 = new Intent(Intent.ACTION_MAIN);
						 	          intent2.setClass(LearningTargetsDetailActivity.this, NewEventPlannerActivity.class);
						 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						 	          startActivity(intent2);
						 	          finish();	
						 	         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
					        	}
						 	         Toast.makeText(getApplicationContext().getApplicationContext(), "Deleted successfully!",
									         Toast.LENGTH_LONG).show();
					        	}
							
						});
						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
				
			
				
			}
        	
        });
        button_update.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent3=new Intent(Intent.ACTION_MAIN);
				intent3.setClass(LearningTargetsDetailActivity.this,UpdateActivity.class);
				intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        	intent3.putExtra("id", learning_id);
				intent3.putExtra("old_id",learning_old_id);
				intent3.putExtra("learning_topic", learning_target_category);
				intent3.putExtra("learning_section", learning_target_detail);
				intent3.putExtra("type", "learning");
				intent3.putExtra("due_date", due_date_extra);
				intent3.putExtra("start_date", start_date_extra);
				intent3.putExtra("last_updated", last_updated);
	        	startActivity(intent3);
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
        	
        });
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.custom_action_bar, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	Intent goHome = new Intent(Intent.ACTION_MAIN);
	 	          goHome.setClass(LearningTargetsDetailActivity.this, MainScreenActivity.class);
	 	          goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(goHome);
	 	          finish();
	 	         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);
	            return true;
	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	 public static long differenceIndays(String startDate,String endDate,String today)
	 {
			// String toDateAsString = "05/11/2010";
			 Date start_date = null;
			 Date end_date = null;
			 Date today_date=null;
			 long starDateAsTimestamp=0;
			long endDateTimestamp=0;
			try {
				today_date= new SimpleDateFormat("dd-MM-yyyy").parse(today);
			if(startDate!=null){
				start_date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
				 starDateAsTimestamp = start_date.getTime();
				}else{
					System.out.println("Enter a valid date!");
				}
			if(endDate!=null){
				  end_date = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
				  endDateTimestamp = end_date.getTime();
				
				}else {
					System.out.println("Enter a valid date!");	
				}
				
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 long todayDateTimestamp = today_date.getTime();
			 long diff = endDateTimestamp - todayDateTimestamp;
			  
			  long diffDays = diff / (24 * 60 * 60 * 1000);
			  if(todayDateTimestamp>endDateTimestamp){
			  return -1;
			  }else{
				return diffDays;
			  }
	    }
	 
	 private String getDateTime() {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "dd-MM-yyyy", Locale.getDefault());
	        Date date = new Date();
	        return dateFormat.format(date);
	}
	 
	 public static boolean isDateAfter(String startDate,String endDate)
	    {
		// String toDateAsString = "05/11/2010";
		 Date start_date = null;
		 Date end_date = null;
		long starDateAsTimestamp = 0;
		long endDateTimestamp = 0;
		try {
		if(startDate!=null){
			start_date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
			starDateAsTimestamp = start_date.getTime();
				
			}else{
				System.out.println("Enter a valid date!");
			}
		if(endDate!=null){
			end_date = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
			endDateTimestamp = end_date.getTime();
				
			}else{
				System.out.println("Enter a valid date!");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 long getRidOfTime = 1000 * 60 * 60 * 24;
		 long startDateAsTimestampWithoutTime = starDateAsTimestamp / getRidOfTime;
		 long endDateTimestampWithoutTime = endDateTimestamp / getRidOfTime;

		 if (startDateAsTimestampWithoutTime > endDateTimestampWithoutTime) {
		    return true;
		 } else {
		    return false;
		 }
	    }
	 
	 private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(LearningTargetsDetailActivity.this);
		private Double percentage;
		private String percentage_achieved;

	    @Override
	    protected Object doInBackground(Object... params) {
	    	Bundle extras = getIntent().getExtras(); 
	        if (extras != null) {
	        	learning_target_name=extras.getString("learning_course");
	        	learning_target_detail=extras.getString("learning_section");
	        	learning_target_category=extras.getString("learning_topic");
				due_date_extra=extras.getString("due_date");
				start_date_extra=extras.getString("start_date");
				status=extras.getString("status");
				learning_id=extras.getLong("learning_id");
				learning_old_id=extras.getLong("old_id");
				last_updated=extras.getString("last_updated");
				period=extras.getString("period");
	        }
	      
	       
				return null;
	        
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	    	
	    	  long difference_in_days=differenceIndays(start_date_extra,due_date_extra,getDateTime());
		        if (difference_in_days==1){
		        	day_difference.setTextColor(Color.rgb(225,170,7));
		        	day_difference.setText("Due in: "+String.valueOf(difference_in_days)+ " day");
		        	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
		        }else if(difference_in_days==0) {
		        	day_difference.setTextColor(Color.RED);
		        	day_difference.setText("Due today!!!!");
		        	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
		        }else if(difference_in_days==-1) {
		        	day_difference.setTextColor(Color.RED);
		        	day_difference.setText("Due date is past");
		        	imageView_status.setImageResource(R.drawable.sad);
		        }else {
		        	day_difference.setText("Due in: "+String.valueOf(difference_in_days)+ " days");
		        	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
		        }
	    	  learning_period.setText(period);
	          textView_category.setText(learning_target_name);
	          textView_course.setText(learning_target_detail);
	          textView_topic.setText(learning_target_category);
	          textView_dueDate.setText(due_date_extra);
	          textView_startDate.setText(start_date_extra);
	        
	    }
	}
	 
	 private boolean checkValidation() {
	        boolean ret = true;
	 
	        if (!Validation.hasTextTextView(startDateValue)) ret = false;
	        if (!Validation.hasTextTextView(dueDateValue)) ret = false;
	        if (!Validation.hasSelection(target_name)) ret=false;
	        if (!Validation.hasSelection(target_category)) ret=false;
	        if (!Validation.hasSelection(target_detail)) ret=false;
	        if (!Validation.hasSelection(spinner_period)) ret=false;
	        if (Validation.isDateAfter(startDateValue.getText().toString(),dueDateValue.getText().toString(),startDateValue)) ret = false;
	        return ret;
	    }	
}
