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
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
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
	ArrayList<String> eventPeriod;
	ArrayList<String> eventDueDate;
	
	ArrayList<String> coverageType;
	ArrayList<String> coverageId;
	ArrayList<String> coverageNumber;
	ArrayList<String> coveragePeriod;
	ArrayList<String> coverageDueDate;
	
	ArrayList<String> otherType;
	ArrayList<String> otherId;
	ArrayList<String> otherNumber;
	ArrayList<String> otherPeriod;
	ArrayList<String> otherDueDate;
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
	String due_date;
	
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
        Time time = new Time();
	    time.setToNow();
	    String today= String.valueOf(time.monthDay)+"-"+String.valueOf(time.month+1)+"-"+String.valueOf(time.year);
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
	    eventUpdateItemsDaily=db.getAllEvents(current_month,today);
	   
	    if(eventUpdateItemsDaily.isEmpty()){
	    	linearLayout_eventsUpdate.setVisibility(View.GONE);
	   
	    }else {
	    	eventId=new ArrayList<String>();
	    	eventNumber=new ArrayList<String>();
	    	eventType=new ArrayList<String>();
	    	eventDueDate=new ArrayList<String>();
	    	eventPeriod=new ArrayList<String>();
	 	    eventId.add(eventUpdateItemsDaily.get("event_id"));
	 	    eventNumber.add(eventUpdateItemsDaily.get("event_number"));
	 	   eventType.add(eventUpdateItemsDaily.get("event_name"));
	 	   eventDueDate.add(eventUpdateItemsDaily.get("due_date"));
	 	   eventPeriod.add(eventUpdateItemsDaily.get("event_period"));
	    	eventUpdateAdapter=new eventsUpdateListAdapter(mContext, eventType, eventId, eventNumber,eventPeriod,eventDueDate);
	 	    listView_eventsUpdate.setAdapter(eventUpdateAdapter);
	    }
	    //retrieve monthly coverage targets that need to be updated
	    coverageUpdateItemsDaily=db.getAllCoverage(current_month,today);
	   
		if(coverageUpdateItemsDaily.isEmpty()){
			linearLayout_coverageUpdate.setVisibility(View.GONE);	
		}else {
			coverageId=new ArrayList<String>();
			coverageNumber=new ArrayList<String>();
	    	coverageType=new ArrayList<String>();
	    	coverageDueDate=new ArrayList<String>();
	    	coveragePeriod=new ArrayList<String>();
			coverageType.add(coverageUpdateItemsDaily.get("coverage_name"));
			coverageId.add(coverageUpdateItemsDaily.get("coverage_id"));
			coverageNumber.add(coverageUpdateItemsDaily.get("coverage_number"));
			coverageDueDate.add(coverageUpdateItemsDaily.get("due_date"));
			coveragePeriod.add(coverageUpdateItemsDaily.get("coverage_period"));
			coverageUpdateAdapter=new coverageUpdateListAdapter(mContext, coverageType, coverageId, coverageNumber,coveragePeriod,coverageDueDate);
			listView_coverageUpdate.setAdapter(coverageUpdateAdapter);
		}
		 //retrieve monthly other targets that need to be updated
	    otherUpdateItemsDaily=db.getAllOther(current_month,today);
	   
	    if(otherUpdateItemsDaily.isEmpty()){
	    	linearLayout_otherUpdate.setVisibility(View.GONE);
	    }else{
	    	otherId=new ArrayList<String>();
	    	otherNumber=new ArrayList<String>();
			otherType=new ArrayList<String>();
			otherDueDate=new ArrayList<String>();
			otherPeriod=new ArrayList<String>();
	    	 otherType.add(otherUpdateItemsDaily.get("other_name"));
	 	    otherId.add(otherUpdateItemsDaily.get("other_id"));
	 	    otherNumber.add(otherUpdateItemsDaily.get("other_number"));
	 	    otherDueDate.add(otherUpdateItemsDaily.get("due_date"));
	 	   otherPeriod.add(otherUpdateItemsDaily.get("other_period"));
	    	otherUpdateAdapter=new otherUpdateListAdapter(mContext, otherType, otherId, otherNumber,otherPeriod,otherDueDate);
			listView_otherUpdate.setAdapter(otherUpdateAdapter);	
	    }
	    listView_eventsUpdate.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, final long id) {
				//String[] values=eventUpdateAdapter.getItem(position);
				String[] items=eventUpdateAdapter.getItem(position);
				Intent intent;
				intent=new Intent(mContext, UpdateActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("number",items[1]);
				intent.putExtra("name", items[0]);
				intent.putExtra("type", "event");
				intent.putExtra("due_date", items[3]);
				intent.putExtra("period", items[2]);
				startActivity(intent);
				/*
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
		        */
			}
	    });
	    
	    listView_coverageUpdate.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, final long id) {
				
				String[] items=coverageUpdateAdapter.getItem(position);
				Intent intent;
				intent=new Intent(mContext, UpdateActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("number",items[1]);
				intent.putExtra("name", items[0]);
				intent.putExtra("type", "coverage");
				intent.putExtra("due_date", items[3]);
				intent.putExtra("period", items[2]);
				startActivity(intent);
				/*
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
					            	
				*/	            	
			}
	    });
	    
	    listView_otherUpdate.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, final long id) {
				String[] items=otherUpdateAdapter.getItem(position);
				Intent intent;
				intent=new Intent(mContext, UpdateActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("number",items[1]);
				intent.putExtra("name", items[0]);
				intent.putExtra("type", "other");
				intent.putExtra("due_date", items[3]);
				intent.putExtra("period", items[2]);
				startActivity(intent);
				/*
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
					            	
					*/            	
			}
	    });
	}  
	class eventsUpdateListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<String> eventType;
		ArrayList<String> eventId;
		ArrayList<String> eventNumber;
		ArrayList<String> eventPeriod;
		ArrayList<String> eventDueDate;
		public eventsUpdateListAdapter(Context c, ArrayList<String> eventType,ArrayList<String> eventId,
										ArrayList<String> eventNumber,ArrayList<String> eventPeriod,
										ArrayList<String> eventDueDate){
			this.mContext=c;
			this.eventType=eventType;
			this.eventNumber=eventNumber;
			this.eventId=eventId;
			this.eventDueDate=eventDueDate;
			this.eventPeriod=eventPeriod;
		}
	

	@Override
	public int getCount() {
		return eventType.size();
	}

	@Override
	public String[] getItem(int position) {
		String[] item;
		item=new String[]{eventType.get(position),eventNumber.get(position),eventPeriod.get(position),eventDueDate.get(position)};
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
        
        TextView text3=(TextView) list.findViewById(R.id.textView_eventPeriod);
        text3.setText(eventPeriod.get(position));
		   TextView text4=(TextView) list.findViewById(R.id.textView_dueDate);
		   text4.setText(eventDueDate.get(position));
		    return list;
	}
	
	}
	
	class coverageUpdateListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<String> coverageType;
		ArrayList<String> coverageId;
		ArrayList<String> coverageNumber;
		ArrayList<String> coveragePeriod;
		ArrayList<String> coverageDueDate;
		 public LayoutInflater minflater;
		public coverageUpdateListAdapter(Context c, ArrayList<String> coverageType,
											ArrayList<String> coverageId,
											ArrayList<String> coverageNumber,
											ArrayList<String> coveragePeriod,
											ArrayList<String> coverageDueDate){
			this.mContext=c;
			this.coverageType=coverageType;
			this.coverageNumber=coverageNumber;
			this.coverageId=coverageId;
			this.coveragePeriod=coveragePeriod;
			this.coverageDueDate=coverageDueDate;
		}
	

	@Override
	public int getCount() {
		return coverageType.size();
	}

	@Override
	public String[] getItem(int position) {
		String[] item;
		item=new String[]{coverageType.get(position),coverageNumber.get(position),coveragePeriod.get(position),coverageDueDate.get(position)};
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
         
         TextView text3=(TextView) list.findViewById(R.id.textView_eventPeriod);
         text3.setText(coveragePeriod.get(position));
		   TextView text4=(TextView) list.findViewById(R.id.textView_dueDate);
		   text4.setText(coverageDueDate.get(position));
		    return list;
	}
	
	}
	class otherUpdateListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<String> otherType;
		ArrayList<String> otherId;
		ArrayList<String> otherNumber;
		ArrayList<String> otherPeriod;
		ArrayList<String> otherDueDate;
		 public LayoutInflater minflater;
		public otherUpdateListAdapter(Context c, ArrayList<String> otherType,
									ArrayList<String> otherId,
									ArrayList<String> otherNumber,
									ArrayList<String> otherPeriod,
									ArrayList<String> otherDueDate){
			this.mContext=c;
			this.otherType=otherType;
			this.otherNumber=otherNumber;
			this.otherId=otherId;
			this.otherPeriod=otherPeriod;
			this.otherDueDate=otherDueDate;
		}
	

	@Override
	public int getCount() {
		return otherType.size();
	}

	@Override
	public String[] getItem(int position) {
		String[] item;
		item=new String[]{otherType.get(position),otherNumber.get(position),otherPeriod.get(position),otherDueDate.get(position)};
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
         
         TextView text3=(TextView) list.findViewById(R.id.textView_eventPeriod);
         text3.setText(otherPeriod.get(position));
		   TextView text4=(TextView) list.findViewById(R.id.textView_dueDate);
		   text4.setText(otherDueDate.get(position));
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
