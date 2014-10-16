package org.digitalcampus.oppia.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateTargetActivity extends Activity{

	private ListView listView_eventsUpdate;
	private ListView listView_coverageUpdate;
	private ListView listView_otherUpdate;
	private ListView listView_learningUpdate;
	private DbHelper db;
	private Context mContext;
	ArrayList<String> eventType;
	ArrayList<String> eventId;
	ArrayList<String> eventNumber;
	
	ArrayList<String> coverageType;
	ArrayList<String> coverageId;
	ArrayList<String> coverageNumber;
	
	ArrayList<String> otherType;
	ArrayList<String> otherId;
	ArrayList<String> otherNumber;
	private eventsUpdateListAdapter eventUpdateAdapter;
	private String current_month;
	private LinearLayout linearLayout_eventsUpdate;
	private LinearLayout linearLayout_coverageUpdate;
	private LinearLayout linearLayout_otherUpdate;
	private LinearLayout linearLayout_learningUpdate;
	private HashMap<String, String> eventUpdateItemsDaily;
	private HashMap<String, String> coverageUpdateItemsDaily;
	private HashMap<String, String> otherUpdateItemsDaily;
	private HashMap<String, String> learningUpdateItemsDaily;
	private coverageUpdateListAdapter coverageUpdateAdapter;
	private otherUpdateListAdapter otherUpdateAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_update_targets);
	    mContext=UpdateTargetActivity.this;
	    listView_eventsUpdate=(ListView) findViewById(R.id.listView1);
	    listView_coverageUpdate=(ListView) findViewById(R.id.listView2);
	    listView_otherUpdate=(ListView) findViewById(R.id.listView3);
	    listView_learningUpdate=(ListView) findViewById(R.id.listView4);
	    linearLayout_eventsUpdate=(LinearLayout) findViewById(R.id.LinearLayout_eventUpdate);
	    linearLayout_coverageUpdate=(LinearLayout) findViewById(R.id.LinearLayout_coverageUpdate);
	    linearLayout_otherUpdate=(LinearLayout) findViewById(R.id.LinearLayout_otherUpdate);
	    linearLayout_learningUpdate=(LinearLayout) findViewById(R.id.LinearLayout_learningUpdate);
	    db=new DbHelper(mContext);
	    Calendar c = Calendar.getInstance();
        int month=c.get(Calendar.MONTH)+1;
        switch(month){
        case 1:
	        	current_month="January";
	        	break;
        case 2:
        	current_month="February";
        	break;
        case 3:
        	current_month="March";
        	break;
        case 4:
        	current_month="April";
        	break;
        case 5:
        	current_month="May";
        	break;
        case 6:
        	current_month="June";
        	break;
        case 7:
        	current_month="July";
        	break;
        case 8:
        	current_month="August";
        	break;
        case 9:
        	current_month="September";
        	break;
        case 10:
        	current_month="October";
        	break;
        case 11:
        	current_month="November";
        	break;
        case 12:
        	current_month="December";
        	break;
        }
	    //retrieve daily event targets that need to be updated
	    eventUpdateItemsDaily=db.getAllDailyEvents(current_month);
	   
	    if(eventUpdateItemsDaily.isEmpty()){
	    	linearLayout_eventsUpdate.setVisibility(View.GONE);
	   
	    }else {
	    	eventId=new ArrayList<String>();
	    	eventNumber=new ArrayList<String>();
	    	eventType=new ArrayList<String>();
	 	    eventId.add(eventUpdateItemsDaily.get("event_id"));
	 	    eventNumber.add(eventUpdateItemsDaily.get("event_number"));
	 	   eventType.add(eventUpdateItemsDaily.get("event_name"));
	    	eventUpdateAdapter=new eventsUpdateListAdapter(mContext, eventType, eventId, eventNumber);
	 	    listView_eventsUpdate.setAdapter(eventUpdateAdapter);
	    }
	    //retrieve monthly coverage targets that need to be updated
	    coverageUpdateItemsDaily=db.getAllDailyCoverage(current_month);
	   
		if(coverageUpdateItemsDaily.isEmpty()){
			linearLayout_coverageUpdate.setVisibility(View.GONE);	
		}else {
			coverageId=new ArrayList<String>();
			coverageNumber=new ArrayList<String>();
	    	coverageType=new ArrayList<String>();
			 coverageType.add(coverageUpdateItemsDaily.get("coverage_name"));
				coverageId.add(coverageUpdateItemsDaily.get("coverage_id"));
				coverageNumber.add(coverageUpdateItemsDaily.get("coverage_number"));
			coverageUpdateAdapter=new coverageUpdateListAdapter(mContext, coverageType, coverageId, coverageNumber);
			listView_coverageUpdate.setAdapter(coverageUpdateAdapter);
		}
		 //retrieve monthly other targets that need to be updated
	    otherUpdateItemsDaily=db.getAllDailyOther(current_month);
	   
	    if(otherUpdateItemsDaily.isEmpty()){
	    	linearLayout_otherUpdate.setVisibility(View.GONE);
	    }else{
	    	otherId=new ArrayList<String>();
	    	otherNumber=new ArrayList<String>();
			otherType=new ArrayList<String>();
	    	 otherType.add(otherUpdateItemsDaily.get("other_name"));
	 	    otherId.add(otherUpdateItemsDaily.get("other_id"));
	 	    otherNumber.add(otherUpdateItemsDaily.get("other_number"));
	    	otherUpdateAdapter=new otherUpdateListAdapter(mContext, otherType, otherId, otherNumber);
			listView_otherUpdate.setAdapter(otherUpdateAdapter);	
	    }
	    listView_eventsUpdate.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, final long id) {
				final Dialog dialog = new Dialog(mContext);
				dialog.setContentView(R.layout.update_dialog);
				dialog.setTitle("Update target");
				Button dialogButton = (Button) dialog.findViewById(R.id.button_update);
				final EditText justification=(EditText) dialog.findViewById(R.id.editText_dialogJustification);
				final EditText comment=(EditText) dialog.findViewById(R.id.editText_comment);
				final TextView message=(TextView) dialog.findViewById(R.id.textView_message);
				String[] values=eventUpdateAdapter.getItem(position);
				message.setText("Were you able to achieve your target of "+values[1]+" "+values[0] +"?");
				RadioGroup update=(RadioGroup) dialog.findViewById(R.id.radioGroup_updateDialog);
				update.setOnCheckedChangeListener(new OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						LinearLayout linearLayout_comment=(LinearLayout) dialog.findViewById(R.id.LinearLayout_comment);
						LinearLayout linearLayout_justification=(LinearLayout) dialog.findViewById(R.id.LinearLayout_justification);
						switch(checkedId){
						case R.id.radio_repeatYes:
							linearLayout_comment.setVisibility(View.VISIBLE);
							//linearLayout_justification.setVisibility(View.GONE);
							break;
						case R.id.radio_repeatNo:
							linearLayout_justification.setVisibility(View.VISIBLE);
							//linearLayout_comment.setVisibility(View.VISIBLE);
							break;
						}
					}
				});
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					            	String[] items=eventUpdateAdapter.getItem(position);
					            	String justification_text=justification.getText().toString();
					            	String comment_text=comment.getText().toString();
				                	if(db.insertJustification(items[0], items[1], justification_text, comment_text, "new_record") !=0){
				                	long last_id=db.insertJustification(items[0], items[1], justification_text, comment_text, "new_record");;
				                	JSONObject json = new JSONObject();
									 try {
										json.put("id", last_id);
										 json.put("event_type", items[0]);
										 json.put("event_number", items[1]);
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
									 db.updateEventTarget("updated", id);
									 Toast.makeText(getApplicationContext(), "Target updated!",
							         Toast.LENGTH_SHORT).show();
										runOnUiThread(new Runnable() {
								            @Override
								            public void run() {
							            	eventUpdateItemsDaily=db.getAllDailyEvents(current_month);
							            	if(eventUpdateItemsDaily.isEmpty()){
							            		linearLayout_eventsUpdate.setVisibility(View.GONE);
							            	}else{
								        	    eventType.add(eventUpdateItemsDaily.get("event_name"));
								        	    eventId.add(eventUpdateItemsDaily.get("event_id"));
								        	    eventNumber.add(eventUpdateItemsDaily.get("event_number"));
								        	    eventUpdateAdapter=new eventsUpdateListAdapter(mContext, eventType, eventId, eventNumber);
							        	 	    listView_eventsUpdate.setAdapter(eventUpdateAdapter);
								            }
								            }
								        });
				                	}
					    }
					
				});
	 				dialog.show();
			}
	    });
	    
	    listView_coverageUpdate.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, final long id) {
				final Dialog dialog = new Dialog(mContext);
				dialog.setContentView(R.layout.update_dialog);
				dialog.setTitle("Update target");
				Button dialogButton = (Button) dialog.findViewById(R.id.button_update);
				final EditText justification=(EditText) dialog.findViewById(R.id.editText_dialogJustification);
				final EditText comment=(EditText) dialog.findViewById(R.id.editText_comment);
				final LinearLayout linearLayout_justification=(LinearLayout) dialog.findViewById(R.id.LinearLayout_justification);
				final LinearLayout linearLayout_comment=(LinearLayout) dialog.findViewById(R.id.LinearLayout_comment);
				final TextView message=(TextView) dialog.findViewById(R.id.textView_message);
				String[] values=coverageUpdateAdapter.getItem(position);
				message.setText("Were you able to achieve your target of "+values[1]+" "+values[0] +"?");
				final RadioGroup update=(RadioGroup) dialog.findViewById(R.id.radioGroup_updateDialog);
				update.setOnCheckedChangeListener(new OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch(checkedId){
						case R.id.radio_repeatYes:
							linearLayout_comment.setVisibility(View.VISIBLE);
							linearLayout_justification.setVisibility(View.GONE);
							break;
						case R.id.radio_repeatNo:
							linearLayout_justification.setVisibility(View.VISIBLE);
							linearLayout_comment.setVisibility(View.VISIBLE);
							break;
						}
					}
				});
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					            	String[] items=coverageUpdateAdapter.getItem(position);
					            	String justification_text=justification.getText().toString();
					            	String comment_text=comment.getText().toString();
				                	db.insertJustification(items[0], items[1], justification_text, comment_text, "new_record");
				                	long last_id=db.insertJustification(items[0], items[1], justification_text, comment_text, "new_record");;
				                	JSONObject json = new JSONObject();
									 try {
										json.put("id", last_id);
										 json.put("coverage_type", items[0]);
										 json.put("number_number", items[1]);
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
									 db.updateCoverageTarget("updated", id);
									 Toast.makeText(getApplicationContext(), "Target updated!",
							         Toast.LENGTH_SHORT).show();
										runOnUiThread(new Runnable() {
								            @Override
								            public void run() {
								            	coverageUpdateItemsDaily=db.getAllDailyCoverage(current_month);
								            	if(coverageUpdateItemsDaily.isEmpty()){
								            		linearLayout_coverageUpdate.setVisibility(View.GONE);
								            	}else{
								        	    coverageType.add(eventUpdateItemsDaily.get("coverage_name"));
								        	    coverageId.add(eventUpdateItemsDaily.get("coverage_id"));
								        	    coverageNumber.add(eventUpdateItemsDaily.get("coverage_number"));
								        	    coverageUpdateAdapter=new coverageUpdateListAdapter(mContext, coverageType, coverageId, coverageNumber);
							        	 	    listView_coverageUpdate.setAdapter(coverageUpdateAdapter);
								            }
								            }
								        });
					    }
					
				});
	 				dialog.show();
			}
	    });
	    
	    listView_otherUpdate.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, final long id) {
				final Dialog dialog = new Dialog(mContext);
				dialog.setContentView(R.layout.update_dialog);
				dialog.setTitle("Update target");
				Button dialogButton = (Button) dialog.findViewById(R.id.button_update);
				final EditText justification=(EditText) dialog.findViewById(R.id.editText_dialogJustification);
				final EditText comment=(EditText) dialog.findViewById(R.id.editText_comment);
				final LinearLayout linearLayout_justification=(LinearLayout) dialog.findViewById(R.id.LinearLayout_justification);
				final LinearLayout linearLayout_comment=(LinearLayout) dialog.findViewById(R.id.LinearLayout_comment);
				final TextView message=(TextView) dialog.findViewById(R.id.textView_message);
				String[] values=otherUpdateAdapter.getItem(position);
				message.setText("Were you able to achieve your target of "+values[1]+" "+values[0] +"?");
				final RadioGroup update=(RadioGroup) dialog.findViewById(R.id.radioGroup_updateDialog);
				update.setOnCheckedChangeListener(new OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch(checkedId){
						case R.id.radio_repeatYes:
							linearLayout_comment.setVisibility(View.VISIBLE);
							linearLayout_justification.setVisibility(View.GONE);
							break;
						case R.id.radio_repeatNo:
							linearLayout_justification.setVisibility(View.VISIBLE);
							linearLayout_comment.setVisibility(View.VISIBLE);
							break;
						}
					}
				});
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					            	String[] items=otherUpdateAdapter.getItem(position);
					            	String justification_text=justification.getText().toString();
					            	String comment_text=comment.getText().toString();
				                	db.insertJustification(items[0], items[1], justification_text, comment_text, "new_record");
				                	long last_id=db.insertJustification(items[0], items[1], justification_text, comment_text, "new_record");;
				                	JSONObject json = new JSONObject();
									 try {
										json.put("id", last_id);
										 json.put("coverage_type", items[0]);
										 json.put("number_number", items[1]);
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
									 db.updateOtherTarget("updated", id);
									 Toast.makeText(getApplicationContext(), "Target updated!",
							         Toast.LENGTH_SHORT).show();
										runOnUiThread(new Runnable() {
								            @Override
								            public void run() {
								            	otherUpdateItemsDaily=db.getAllDailyOther(current_month);
								            	if(otherUpdateItemsDaily.isEmpty()){
								            		linearLayout_otherUpdate.setVisibility(View.GONE);
								            	}else{
								            		 otherType.add(otherUpdateItemsDaily.get("other_name"));
								         	 	    otherId.add(otherUpdateItemsDaily.get("other_id"));
								         	 	    otherNumber.add(otherUpdateItemsDaily.get("other_number"));
								         	    	otherUpdateAdapter=new otherUpdateListAdapter(mContext, otherType, otherId, otherNumber);
								         			listView_otherUpdate.setAdapter(otherUpdateAdapter);	
								            }
								            }
								        });
					    }
					
				});
	 				dialog.show();
			}
	    });
	}  
	class eventsUpdateListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<String> eventType;
		ArrayList<String> eventId;
		ArrayList<String> eventNumber;
		public eventsUpdateListAdapter(Context c, ArrayList<String> eventType,ArrayList<String> eventId,ArrayList<String> eventNumber){
			this.mContext=c;
			this.eventType=eventType;
			this.eventNumber=eventNumber;
			this.eventId=eventId;
		}
	

	@Override
	public int getCount() {
		return eventType.size();
	}

	@Override
	public String[] getItem(int position) {
		String[] item;
		item=new String[]{eventType.get(position),eventNumber.get(position)};
		return item;
	}

	@Override
	public long getItemId(int position) {
		long id;
		id=Long.valueOf(eventId.get(position));
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View list = null;
		if (convertView == null) {	 
       	 LayoutInflater inflater = (LayoutInflater) mContext
    		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	  list = new View(mContext);
    	  list = inflater.inflate(R.layout.event_listview_single, null);
      	
        } else {
      	  list = (View) convertView;  
        }
		TextView textView2 = (TextView) list.findViewById(R.id.textView_eventCategory);
        textView2.setText(eventType.get(position));
        
        TextView textView3 = (TextView) list.findViewById(R.id.textView_eventNumber);
        textView3.setText(eventNumber.get(position));
		    return list;
	}
	
	}
	
	class coverageUpdateListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<String> coverageType;
		ArrayList<String> coverageId;
		ArrayList<String> coverageNumber;
		 public LayoutInflater minflater;
		public coverageUpdateListAdapter(Context c, ArrayList<String> coverageType,ArrayList<String> coverageId,ArrayList<String> coverageNumber){
			this.mContext=c;
			this.coverageType=coverageType;
			this.coverageNumber=coverageNumber;
			this.coverageId=coverageId;
		}
	

	@Override
	public int getCount() {
		return coverageType.size();
	}

	@Override
	public String[] getItem(int position) {
		String[] item;
		item=new String[]{coverageType.get(position),coverageNumber.get(position)};
		return item;
	}

	@Override
	public long getItemId(int position) {
		long id;
		id=Long.valueOf(coverageId.get(position));
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View list = null;
		if (convertView == null) {	 
       	 LayoutInflater inflater = (LayoutInflater) mContext
    		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	  list = new View(mContext);
    	  list = inflater.inflate(R.layout.event_listview_single, null);
      	
        } else {
      	  list = (View) convertView;  
        }
		 TextView textView2 = (TextView) list.findViewById(R.id.textView_eventCategory);
         textView2.setText(coverageType.get(position));
         
         TextView textView3 = (TextView) list.findViewById(R.id.textView_eventNumber);
         textView3.setText(coverageNumber.get(position));
		    return list;
	}
	
	}
	class otherUpdateListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<String> otherType;
		ArrayList<String> otherId;
		ArrayList<String> otherNumber;
		 public LayoutInflater minflater;
		public otherUpdateListAdapter(Context c, ArrayList<String> otherType,
									ArrayList<String> otherId,
									ArrayList<String> otherNumber){
			this.mContext=c;
			this.otherType=otherType;
			this.otherNumber=otherNumber;
			this.otherId=otherId;
		}
	

	@Override
	public int getCount() {
		return otherType.size();
	}

	@Override
	public String[] getItem(int position) {
		String[] item;
		item=new String[]{otherType.get(position),otherNumber.get(position)};
		return item;
	}

	@Override
	public long getItemId(int position) {
		long id;
		id=Long.valueOf(otherId.get(position));
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View list = null;
		if (convertView == null) {	 
       	 LayoutInflater inflater = (LayoutInflater) mContext
    		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	  list = new View(mContext);
    	  list = inflater.inflate(R.layout.event_listview_single, null);
      	
        } else {
      	  list = (View) convertView;  
        }
		 TextView textView2 = (TextView) list.findViewById(R.id.textView_eventCategory);
         textView2.setText(otherType.get(position));
         
         TextView textView3 = (TextView) list.findViewById(R.id.textView_eventNumber);
         textView3.setText(otherNumber.get(position));
		    return list;
	}
	
	}
	
	
	class learningUpdateListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<String> learningType;
		ArrayList<String> learningId;
		ArrayList<String> learningNumber;
		 public LayoutInflater minflater;
		public learningUpdateListAdapter(Context c, ArrayList<String> learningType,
									ArrayList<String> learningId,
									ArrayList<String> learningNumber){
			this.mContext=c;
			this.learningType=learningType;
			this.learningNumber=learningNumber;
			this.learningId=learningId;
		}
	

	@Override
	public int getCount() {
		return learningType.size();
	}

	@Override
	public String[] getItem(int position) {
		String[] item;
		item=new String[]{learningType.get(position),learningNumber.get(position)};
		return item;
	}

	@Override
	public long getItemId(int position) {
		long id;
		id=Long.valueOf(learningId.get(position));
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if( convertView == null ){
		      
			  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
		    }
		 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
		 text.setText(learningType.get(position));
		    return convertView;
	}
	
	}
	
	
}
