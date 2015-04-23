package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.poc.PostnatalCareCounsellingTopicsActivity.ListAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ANCCounsellingTopicsMenuActivity extends BaseActivity {

	private ListView listView_counselling;
	private Context mContext;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private JSONObject json; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_postnatal_care_counselling);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Counselling");
	    mContext=ANCCounsellingTopicsMenuActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    json=new JSONObject();
	    try {
			json.put("page", "ANC Counselling");
			json.put("section", MobileLearning.CCH_COUNSELLING);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    listView_counselling=(ListView) findViewById(R.id.listView_counsellingTopics);
	    String[] items={"Birth preparedness & complication readiness",//0
	    				"Drug & substance abuse",                     //1
	    				"Encouraging PNC & maternal, newborn care",   //2
	    				"Establishing rapport",                       //3
	    				"HIV care",                                   //4
	    				"Malaria prevention during pregnancy",        //5
	    				"Nutrition during pregnancy",                 //6
	    				"Personal hygiene practices",                 //7
	    				"Pregnancy danger signs",                     //8
	    				"Rest & exercise",                            //9
	    				"Safe preparation of food",                   //10
	    				"STI prevention",                             //11
	    				"Supplementation needed during pregnancy",    //12
	    				"Tetanus Toxoid Immunisation",                //13
	    				"What to expect during labour ",              //14
	    				"When to return for ANC"};                    //15
	    ListAdapter adapter=new ListAdapter(mContext,items);
	    listView_counselling.setAdapter(adapter);
	    listView_counselling.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "birth_preparedness");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 1:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "drug_abuse");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 2:
					intent=new Intent(mContext,EncouragingPNCMenuActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 3:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "establishing_rapport");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 4:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "hiv_care");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 5:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "malaria_prevention");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 6:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "nutrition");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 7:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "personal_hygiene");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 8:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "pregnancy_danger_signs");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 9:
					intent=new Intent(mContext,PostpartumExercisesActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 10:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "safe_food_preparation");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 11	:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "sti_prevention");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 12	:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "supplementation_during_pregnancy");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 13:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "tt_immunisation");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 14:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "what_expect_during_labour");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 15:
					intent=new Intent(mContext,ANCCounsellingTopicsGenerlActivity.class);
					intent.putExtra("value", "when_to_return_anc");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				
				}
			}
	    	
	    });
	}
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public ListAdapter(Context mContext,String[] listItems){
		this.mContext=mContext;
		this.listItems=listItems;
		 minflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return listItems.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if( convertView == null ){
				  convertView = minflater.inflate(R.layout.other_listview_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_otherCategory);
			 text.setText(listItems[position]);
			    return convertView;
		}
		
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		//dbh.insertCCHLog("Point of Care", "ANC Counselling", start_time.toString(), end_time.toString());
	    dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
