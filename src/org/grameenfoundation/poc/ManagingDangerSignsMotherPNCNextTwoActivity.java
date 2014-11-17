package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ManagingDangerSignsMotherPNCNextTwoActivity extends BaseActivity {

	private Button button_no;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private ListView listView_dangerSigns;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext= ManagingDangerSignsMotherPNCNextTwoActivity.this;
	    setContentView(R.layout.activity_management_danger_signs_next2);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Managing Danger Signs");
	    dbh=new DbHelper(ManagingDangerSignsMotherPNCNextTwoActivity.this);
	    start_time=System.currentTimeMillis();
	    listView_dangerSigns=(ListView) findViewById(R.id.listView_dangerSigns);
	    String[] items={"Difficulty breathing or cyanosis",
	    				"Shock",
	    				"Heavy bleeding",
	    				"Convulsing (now or recently), Unconscious",
	    				"Severe headache/blurred vision",
	    				"Diastolic BP ≥ 90 mmHg",
	    				"Severe abdominal pain ",
	    				"Persistent vomiting",
	    				"Pain in calf with or without swelling",
	    				"Painful or tender wound",
	    				"Pain on urination/dribbling urine",
	    				"Pallor",
	    				"Abnormal behaviour/depression"};
	    ListAdapter adapter=new ListAdapter(ManagingDangerSignsMotherPNCNextTwoActivity.this,items);
	    listView_dangerSigns.setAdapter(adapter);
	    listView_dangerSigns.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "difficulty_breathing");
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "shock");
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "heavy_bleeding");
					startActivity(intent);
					break;
				case 3:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "convulsing");
					startActivity(intent);
					break;
				case 4:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "severe_headache");
					startActivity(intent);
					break;
				case 5:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "diastolic");
					startActivity(intent);
					break;
				case 6:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "severe_abdominal");
					startActivity(intent);
					break;
				case 7:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "persistent_vomiting");
					startActivity(intent);
					break;
				case 8:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "pain_in_calf");
					startActivity(intent);
					break;
				case 9:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "painful_or_tender_wound");
					startActivity(intent);
					break;
				case 10:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "pain_on_urination");
					startActivity(intent);
					break;
				case 11:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "pallor");
					startActivity(intent);
					break;
				case 12:
					intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,TakeActionManagingDangerSignsMotherPNCActivity.class);
					intent.putExtra("value", "abnormal_behaviour");
					startActivity(intent);
					break;
				}
				
			}
	    	
	    });
	    button_no=(Button) findViewById(R.id.button_no);
	    button_no.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ManagingDangerSignsMotherPNCNextTwoActivity.this,MalariaMotherPNCActivity.class);
				startActivity(intent);
			}
	    	
	    });
	}
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] items;
		 public LayoutInflater minflater;
		public ListAdapter(Context c,String[] items){
			this.mContext=c;
			this.items=items;
			minflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return items.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setText(items[position]);
			    return convertView;
		}
		
	}
	
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Managing Danger Signs", start_time.toString(), end_time.toString());
		finish();
	}
}