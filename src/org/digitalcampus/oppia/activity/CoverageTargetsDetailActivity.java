package org.digitalcampus.oppia.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.mobile.learningGF.R.color;
import org.digitalcampus.oppia.activity.EventTargetsDetailActivity.DatePickerFragment;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.adapters.CoverageListAdapter;
import org.grameenfoundation.adapters.EventBaseAdapter;
import org.grameenfoundation.cch.utils.TextProgressBar;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class CoverageTargetsDetailActivity extends FragmentActivity {

	private TextView textView_dueDate;
	private TextView textView_period;
	private TextView textView_name;
	private ImageView imageView_status;
	private static String due_date;
	private String status;
	private long coverage_id;
	private TextView textView_number;
	private DbHelper db;
	private RadioGroup category_options;
	private String[] items3;
	private String coverage_name;
	private String coverage_number;
	private String coverage_period;
	private String due_date_extra;
	private String last_updated;
	private Button button_edit;
	private Button button_delete;
	private Button button_update;
	private TextView textView_startDate;
	private String start_date_extra;
	private TextView textView_achieved;
	private String achieved;
	private TextView textView_percentageAchieved;
	private Integer due_date_day;
	private Integer due_date_month;
	private Integer due_date_year;
	private int date_difference;
	private int today_month;
	private int today_day;
	private int today_year;
	private TextView textView_progressCheck;
	private static TextView dueDateValue;
	private static TextView startDateValue;
	private static HashMap<String, String> updateItems;
	private static ArrayList<String> number_entered;
	private static ArrayList<String> number_remaining;
	private static ArrayList<String> number_achieved;
	private static ArrayList<String> update_id;
	private String coverage_detail;
	private TextProgressBar progress_bar;
	private int progress_status;
	static long due_date_to_compare;
	private static String start_date;
	private long end_time;
	private long start_time;
	private TextView day_difference;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_event_target_detail);
	    db=new DbHelper(CoverageTargetsDetailActivity.this);
	    getActionBar().setTitle("Event Planner");
	    getActionBar().setSubtitle("Coverage Target Details");
	    Calendar c = Calendar.getInstance();
        today_month=c.get(Calendar.MONTH)+1;
        today_day=c.get(Calendar.DAY_OF_WEEK);
        today_year=c.get(Calendar.YEAR);
        start_time=System.currentTimeMillis();
      	//today=day+"-"+month+"-"+year;
	    textView_name=(TextView) findViewById(R.id.textView_name);
	    textView_period=(TextView) findViewById(R.id.textView_period);
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    textView_dueDate=(TextView) findViewById(R.id.textView_dueDate);
	    textView_startDate=(TextView) findViewById(R.id.textView_startDate);
	    textView_achieved=(TextView) findViewById(R.id.textView_achieved);
	    textView_percentageAchieved=(TextView) findViewById(R.id.textView_percentageAchieved);
	    textView_progressCheck=(TextView) findViewById(R.id.textView_progressCheck);
	    day_difference=(TextView) findViewById(R.id.textView_dayDifference);
	    progress_bar=(TextProgressBar) findViewById(R.id.progressBar1);
	    imageView_status=(ImageView) findViewById(R.id.imageView_status);
	    button_edit=(Button) findViewById(R.id.button_edit);
	    button_delete=(Button) findViewById(R.id.button_delete);
	  
	    button_update=(Button) findViewById(R.id.button_update);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
        	coverage_name=extras.getString("coverage_name");
			coverage_number=extras.getString("coverage_number");
			coverage_period=extras.getString("coverage_period");
			due_date_extra=extras.getString("due_date");
			start_date_extra=extras.getString("start_date");
			achieved=extras.getString("achieved");
			status=extras.getString("status");
			coverage_id=extras.getLong("coverage_id");
			last_updated=extras.getString("last_updated");
        }
       
        int number_achieved_current=Integer.valueOf(achieved);
        int number_entered_current=Integer.valueOf(coverage_number);
        Double percentage=((double)number_achieved_current/number_entered_current)*100;
        String percentage_achieved=String.format("%.0f", percentage);
        textView_percentageAchieved.setText(percentage_achieved+"%");
        progress_status=(int) percentage.doubleValue();
        progress_bar.setProgress(progress_status);
        progress_bar.setPrefixText(percentage_achieved+"%");
        progress_bar.setPrefixText(" ");
    
        long difference_in_days=differenceIndays(start_date_extra,due_date_extra);
        if (difference_in_days==1){
        	day_difference.setTextColor(color.Red);
        	day_difference.setText("Due in: "+String.valueOf(difference_in_days)+ " day");
        }else if(difference_in_days==0) {
        	day_difference.setTextColor(color.Red);
        	day_difference.setText("Due today!!!!");
        }else {
        	day_difference.setText("Due in: "+String.valueOf(difference_in_days)+ " days");
        }
        textView_name.setText(coverage_name);
        textView_period.setText(coverage_period);
        textView_dueDate.setText(due_date_extra);
        textView_number.setText(coverage_number);
        textView_startDate.setText(start_date_extra);
        textView_achieved.setText(achieved);
        if(achieved==coverage_number){
        	button_edit.setClickable(false);
        	button_delete.setClickable(false);
        	button_update.setClickable(false);
        }
        /*
        if(date_difference==4&&due_date_month==today_month&&due_date_year==today_year&&percentage<50){
        	textView_progressCheck.setText("You are behind. Please work hard to improve!");	
        	imageView_status.setImageResource(R.drawable.sad);
            }else {
            	textView_progressCheck.setText("You are on track. Keep up the good work");	
            	imageView_status.setImageResource(R.drawable.ic_achieved_smile);
            }
            */
        
        if(status.equalsIgnoreCase("updated")){
        	imageView_status.setImageResource(R.drawable.ic_achieved_smile);
        }else if(status.equalsIgnoreCase("new_record")){
        	imageView_status.setImageResource(R.drawable.ic_achieved_waiting);
        }else if(status.equalsIgnoreCase("not_achieved")){
        	imageView_status.setImageResource(R.drawable.ic_not_achieved);
        }
        button_update.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//updateItems=db.getAllForUpdate(coverage_name, coverage_id);
				//number_entered=new ArrayList<String>();
				//number_achieved=new ArrayList<String>();
				//number_remaining=new ArrayList<String>();
				//update_id=new ArrayList<String>();
				  number_achieved=db.getForUpdateCoverageNumberAchieved(coverage_id,coverage_period);
					number_remaining=db.getAllForCoverageNumberRemaining(coverage_period);
					//number_remaining.add(updateItems.get("number_remaining"));
					//update_id.add(updateItems.get("justification_id"));
				Intent intent3=new Intent(CoverageTargetsDetailActivity.this,UpdateActivity.class);
	        	intent3.putExtra("id", coverage_id);
				intent3.putExtra("number",coverage_number);
				intent3.putExtra("name", coverage_name);
				intent3.putExtra("type", "coverage");
				intent3.putExtra("start_date", start_date_extra);
				intent3.putExtra("due_date", due_date_extra);
				intent3.putExtra("period", coverage_period);
				intent3.putExtra("last_updated", last_updated);
				intent3.putExtra("number_achieved", number_achieved.get(0));//0
				intent3.putExtra("number_remaining", number_remaining.get(0));//number entered
				//intent3.putExtra("update_id", update_id.get(0));
	        	startActivity(intent3);
				
			}
        	
        });
        
        button_delete.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						CoverageTargetsDetailActivity.this);
		 
					// set title
					alertDialogBuilder.setTitle("Delete Confirmation");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("You are about to delete this target. Proceed?")
						.setCancelable(false)
						.setIcon(R.drawable.ic_error)
						.setPositiveButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close
								// current activity
								dialog.cancel();
							}
						  })
						.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, just close
								if(db.deleteCoverageCategory(coverage_id)==true){
									  number_achieved=db.getForUpdateCoverageNumberAchieved(coverage_id,coverage_period);
										number_remaining=db.getAllForCoverageNumberRemaining(coverage_period);
									JSONObject json = new JSONObject();
									 try {
										json.put("id", coverage_id);
										json.put("category", "coverage");
										json.put("target_type", coverage_name);
										 json.put("target_number", coverage_number);
										 json.put("start_date", start_date_extra);
										 json.put("due_date", due_date_extra);
										 json.put("achieved_number", achieved);
										 json.put("last_updated", last_updated);
										json.put("deleted", 1);
										json.put("changed", 0);
									} catch (JSONException e) {
										e.printStackTrace();
									}
									 end_time=System.currentTimeMillis();
									 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
					        		Intent intent2 = new Intent(Intent.ACTION_MAIN);
						 	          intent2.setClass(CoverageTargetsDetailActivity.this, NewEventPlannerActivity.class);
						 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						 	          startActivity(intent2);
						 	          finish();	
						 	         Toast.makeText(getApplicationContext().getApplicationContext(), "Deleted successfully!",
									         Toast.LENGTH_LONG).show();
					        	}
							}
						});
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
				
				
			
			}	
        });
        button_edit.setOnClickListener(new OnClickListener(){

			private RadioButton category_people;

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(CoverageTargetsDetailActivity.this);
				dialog.setContentView(R.layout.coverage_add_dialog);
				final Spinner spinner_coverageName=(Spinner) dialog.findViewById(R.id.spinner_dialogCoverageName);
				final Spinner spinner_coverageDetails=(Spinner) dialog.findViewById(R.id.spinner_dialogCoverageDetail);
				dialog.setTitle("Edit Coverage Target");
				 category_options=(RadioGroup) dialog.findViewById(R.id.radioGroup_category);
				  category_options.check(R.id.radio_people);
				  category_people=(RadioButton) dialog.findViewById(R.id.radio_people);
				  category_people.setChecked(true);
				  items3=new String[]{"Family Planning","Age Groups","School Health"};
				  spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

						@Override
						public void onItemSelected(
								AdapterView<?> parent, View view,
								int position, long id) {
						switch(position){
						case 0:
							String[] detail_items1={"New Acceptors","Continuing Acceptors"};
							ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items1);
							spinner_coverageDetails.setAdapter(details_adapter);
							coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
							break;
						case 1:
							String[] detail_items2={"0 to 11 months","12 to 23 months","24 to 59 months"};
							ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items2);
							spinner_coverageDetails.setAdapter(details_adapter2);
							coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
							break;
						case 2:
							String[] detail_items3={"# of schools visited","# of schools with 3+ health talks","# examined ­ Pre­school","# examined - P1" ,"# examined - P3", "# examined - JHS 1"};
							ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items3);
							spinner_coverageDetails.setAdapter(details_adapter3);
							coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
							break;
						}
							
						}

						@Override
						public void onNothingSelected(
								AdapterView<?> parent) {
							// TODO Auto-generated method stub
							
						}
						
					});
					ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, items3);
					spinner_coverageName.setAdapter(adapter3);
				    category_options.setOnCheckedChangeListener(new OnCheckedChangeListener(){
						public void onCheckedChanged(
								RadioGroup buttonView,
								int isChecked) {
							if (isChecked == R.id.radio_people) {
								items3=new String[]{"Family Planning","Age Groups","School Health"};
								ArrayAdapter<String> adapter3=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter3);
								spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

									@Override
									public void onItemSelected(
											AdapterView<?> parent, View view,
											int position, long id) {
									switch(position){
									case 0:
										String[] detail_items1={"New Acceptors","Continuing Acceptors"};
										ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items1);
										spinner_coverageDetails.setAdapter(details_adapter);
										coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										break;
									case 1:
										String[] detail_items2={"0 to 11 months","12 to 23 months","24 to 59 months"};
										ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items2);
										spinner_coverageDetails.setAdapter(details_adapter2);
										coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										break;
									case 2:
										String[] detail_items3={"Number of schools visited","Number of schools with 3+ health talks","Number examined ­ Pre­school, P1, P3, JHS 1"};
										ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items3);
										spinner_coverageDetails.setAdapter(details_adapter3);
										coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
										break;
									}
										
									}

									@Override
									public void onNothingSelected(
											AdapterView<?> parent) {
									}
									
								});
								
							} else if (isChecked == R.id.radio_immunization) {
								items3=new String[]{"BCG"," Penta",
										"OPV","ROTA",
										"PCV","Measles Rubella","Vitamin A","TT pregnant","TT non-pregnant","Yellow Fever"};
								ArrayAdapter<String> adapter4=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, items3);
								spinner_coverageName.setAdapter(adapter4);
								spinner_coverageName.setOnItemSelectedListener(new OnItemSelectedListener(){

									@Override
									public void onItemSelected(
											AdapterView<?> parent, View view,
											int position, long id) {
										switch(position){
										case 0:
											String[] detail_items1={"BCG"};
											ArrayAdapter<String> details_adapter=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items1);
											spinner_coverageDetails.setAdapter(details_adapter);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 1:
											String[] detail_items2={"Penta 1","Penta 2","Penta 3"};
											ArrayAdapter<String> details_adapter2=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items2);
											spinner_coverageDetails.setAdapter(details_adapter2);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 2:
											String[] detail_items3={"OPV 1","OPV 2","OPV 3"};
											ArrayAdapter<String> details_adapter3=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items3);
											spinner_coverageDetails.setAdapter(details_adapter3);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 3:
											String[] detail_items4={"ROTA 1","ROTA 2"};
											ArrayAdapter<String> details_adapter4=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items4);
											spinner_coverageDetails.setAdapter(details_adapter4);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 4:
											String[] detail_itemsPCV={"PCV 1","PCV 2","PCV 3"};
											ArrayAdapter<String> details_adapterPCV=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_itemsPCV);
											spinner_coverageDetails.setAdapter(details_adapterPCV);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 5:
											String[] detail_items5={"Measles Rubella @9mnths","Measles 2"};
											ArrayAdapter<String> details_adapter5=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items5);
											spinner_coverageDetails.setAdapter(details_adapter5);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 6:
											String[] detail_items6={"100,000 IU","200,000 IU","Postpartum"};
											ArrayAdapter<String> details_adapter6=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items6);
											spinner_coverageDetails.setAdapter(details_adapter6);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 7:
											String[] detail_items7={"TT 1","TT 2","TT 3","TT 4","TT 5"};
											ArrayAdapter<String> details_adapter7=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items7);
											spinner_coverageDetails.setAdapter(details_adapter7);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 8:
											String[] detail_items8={"TT 1","TT 2","TT 3","TT 4","TT 5"};
											ArrayAdapter<String> details_adapter8=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items8);
											spinner_coverageDetails.setAdapter(details_adapter8);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										case 9:
											String[] detail_items9={"Yellow Fever"};
											ArrayAdapter<String> details_adapter9=new ArrayAdapter<String>(getApplicationContext().getApplicationContext(), android.R.layout.simple_list_item_1, detail_items9);
											spinner_coverageDetails.setAdapter(details_adapter9);
											coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
											break;
										}
										
									}

									@Override
									public void onNothingSelected(
											AdapterView<?> parent) {
										// TODO Auto-generated method stub
										
									}
									
								});
								
							}
						}
				    });
				    String[] items2={"Daily","Weekly","Monthly","Quarterly","Mid-year","Annually"};
				final Spinner spinner_coveragePeriod=(Spinner) dialog.findViewById(R.id.spinner_coveragePeriod);
				ArrayAdapter<String> spinner_adapter=new ArrayAdapter<String>(CoverageTargetsDetailActivity.this, android.R.layout.simple_list_item_1, items2);
				spinner_coveragePeriod.setAdapter(spinner_adapter);
				
				final EditText editText_coverageNumber=(EditText) dialog.findViewById(R.id.editText_dialogCoverageNumber);
				editText_coverageNumber.setText(coverage_number);
				Button dialogButton = (Button) dialog.findViewById(R.id.button_dialogAddCoverage);
				dialogButton.setText("Save");
				ImageButton datepickerDialog=(ImageButton) dialog.findViewById(R.id.imageButton_dueDate);
				datepickerDialog.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment();
						    newFragment.show(getSupportFragmentManager(), "datePicker");
						
					}
					
				});
				ImageButton datepickerDialog2=(ImageButton) dialog.findViewById(R.id.imageButton_startDate);
				datepickerDialog2.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						 DialogFragment newFragment = new DatePickerFragment2();
						    newFragment.show(getSupportFragmentManager(), "datePicker2");
						
					}
					
				});
				Button cancel=(Button) dialog.findViewById(R.id.button_cancel);
				cancel.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
					dialog.dismiss();
						
					}
					
				});
				
				dueDateValue=(TextView) dialog.findViewById(R.id.textView_dueDateValue);
				startDateValue=(TextView) dialog.findViewById(R.id.textView_startDate);
				dialogButton.setOnClickListener(new OnClickListener() {
					

					@Override
					public void onClick(View v) {
						dialog.dismiss();
						String duration = null;
						if(isToday(due_date_to_compare)){
							duration="Today";
						}else if(isTomorrow(due_date_to_compare)){
							duration="Tomorrow";
						}else if(isThisWeek(due_date_to_compare)){
							duration="This week";
						}else if(isThisMonth(due_date_to_compare)){
							duration="This month";
						}else if(isThisQuarter(due_date_to_compare)){
							duration="This quarter";
						}
						String coverage_name=spinner_coverageName.getSelectedItem().toString();
						String coverage_period=spinner_coveragePeriod.getSelectedItem().toString();
						String coverage_number=editText_coverageNumber.getText().toString();
						coverage_detail=spinner_coverageDetails.getSelectedItem().toString();
					    if(db.editCoverage(coverage_name, coverage_detail,coverage_number, coverage_period,duration,start_date,due_date, coverage_id) ==true){
					    	  number_achieved=db.getForUpdateCoverageNumberAchieved(coverage_id,coverage_period);
					  		number_remaining=db.getAllForCoverageNumberRemaining(coverage_period);
					    	JSONObject json = new JSONObject();
							 try {
								json.put("id", coverage_id);
								json.put("category", "coverage");
								json.put("target_type", coverage_name);
								 json.put("target_number", coverage_number);
								 json.put("start_date", start_date_extra);
								 json.put("due_date", due_date_extra);
								 json.put("achieved_number", achieved);
								 json.put("last_updated", last_updated);
								 json.put("changed", 1);
								 json.put("deleted", 0);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							 end_time=System.currentTimeMillis();
							 db.insertCCHLog("Target Setting", json.toString(), String.valueOf(start_time), String.valueOf(end_time));
					    	Intent intent2 = new Intent(Intent.ACTION_MAIN);
				 	          intent2.setClass(CoverageTargetsDetailActivity.this, NewEventPlannerActivity.class);
				 	          intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 	          startActivity(intent2);
				 	          finish();	
					    	 Toast.makeText(CoverageTargetsDetailActivity.this.getApplicationContext(), "Coverage target edited successfully!",
							         Toast.LENGTH_LONG).show();
					    }else{
					    	Toast.makeText(CoverageTargetsDetailActivity.this.getApplicationContext(), "Oops! Something went wrong. Please try again",
							         Toast.LENGTH_LONG).show();
					    }
					}
				});
	 				dialog.show();
				
			}
        	
        });
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.update_menu_icons, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
	    switch (item.getItemId()) {
	        case R.id.action_home:
	        	Intent goHome = new Intent(Intent.ACTION_MAIN);
	 	          goHome.setClass(CoverageTargetsDetailActivity.this, MainScreenActivity.class);
	 	          goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(goHome);
	 	          finish();
	 	         
	            return true;
	        case R.id.action_edit:
	        	
	 	         
	            return true;
	        case R.id.action_delete:
	        	
	        	
	         	return true;
	        case R.id.action_update:
	        	
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	 public boolean isToday(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
 	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
 	       				.toString().equals(today)) ? true : false;
 	}
 	
 	public boolean isTomorrow(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	Calendar c = Calendar.getInstance();
 	    	c.add(Calendar.DATE, 1);
 	    	String tomorrow = new SimpleDateFormat("MM/dd/yyyy").format(new Date(c.getTimeInMillis()));
 	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
 	       				.toString().equals(tomorrow)) ? true : false;
 	}
 	    
 	public boolean isThisWeek(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	Calendar c = Calendar.getInstance();
 	    	c.add(Calendar.DATE, 7);
 	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
 	}
 	public boolean isThisMonth(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	Calendar c = Calendar.getInstance();
 	    	c.add(Calendar.DATE, 30);
 	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
 	}
 	public boolean isThisQuarter(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	Calendar c = Calendar.getInstance();
 	    	c.add(Calendar.DATE, 90);
 	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
 	}
 	
 	public boolean isMidYear(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	Calendar c = Calendar.getInstance();
 	    	c.add(Calendar.DATE, 120);
 	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
 	}
 	public boolean isThisYear(long dueDate)
 	{
 			long milliSeconds = dueDate;
 	    	Calendar c = Calendar.getInstance();
 	    	c.add(Calendar.DATE, 365);
 	        return (milliSeconds >= c.getTimeInMillis()) ? true : false;
 	}
	public static class DatePickerFragment extends DialogFragment
	implements DatePickerDialog.OnDateSetListener {

@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
//Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);

//Create a new instance of DatePickerDialog and return it
return new DatePickerDialog(getActivity(), this, year, month, day);
}

public void onDateSet(DatePicker view, int year, int month, int day) {
int month_value=month+1;
due_date=day+"-"+month_value+"-"+year;
dueDateValue.setText(due_date);
}
}
	 public static class DatePickerFragment2 extends DialogFragment
		implements DatePickerDialog.OnDateSetListener {



@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH);
	int day = c.get(Calendar.DAY_OF_MONTH);

	// Create a new instance of DatePickerDialog and return it
	return new DatePickerDialog(getActivity(), this, year, month, day);
}

public void onDateSet(DatePicker view, int year, int month, int day) {
int month_value=month+1;
start_date=day+"-"+month_value+"-"+year;
startDateValue.setText(start_date);
}
}
	 public static long differenceIndays(String startDate,String endDate)
	    {
		// String toDateAsString = "05/11/2010";
		 Date start_date = null;
		 Date end_date = null;
		try {
			start_date = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
			end_date = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 long starDateAsTimestamp = start_date.getTime();
		 long endDateTimestamp = end_date.getTime();
		 long diff = endDateTimestamp - starDateAsTimestamp;
		  
		  long diffDays = diff / (24 * 60 * 60 * 1000);
		  return diffDays;
	    }
}
