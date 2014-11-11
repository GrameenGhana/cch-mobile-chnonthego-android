package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SevereDiseasesNextActivity extends Activity {

	private ListView listView_severDiseaseSymptoms;
	private Context mContext;
	private Button button_no;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_severe_disease_next);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic");
	    mContext=SevereDiseasesNextActivity.this;
	    listView_severDiseaseSymptoms=(ListView) findViewById(R.id.listView_severDiseaseSymptoms);
	    String[] items={"Not breathing (apnea) or Slow breathing < 20 bpm","Fast breathing (≥ 60 bpm)",
	    				"Chest in-drawing",
	    				"Grunting","Convulsing now or convulsed in the last hour",
	    				"Cyanosis or pallor","Low body temperature (< 35.5 C)",
	    				"Fever (> 37.5 C)","Feeding difficulty, not feeding well, or not able to feed",
	    				"Mild Hypothermia (35.5 – 36.5 C) ",
	    				"Umbilicus Infection or Skin Pustules","Eye Infection "};
	    SymptomsListAdapter adapter=new SymptomsListAdapter(mContext,items);
	    listView_severDiseaseSymptoms.setAdapter(adapter);
	    button_no=(Button) findViewById(R.id.button_no);
	    button_no.setOnClickListener(new OnClickListener(){

			private Intent intent;

			@Override
			public void onClick(View v) {
				intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
				intent.putExtra("category", "no symptoms");
				startActivity(intent);
				
			}
	    	
	    });
	    listView_severDiseaseSymptoms.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
					intent.putExtra("category", "difficulty");
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
					intent.putExtra("category", "fast_breathing");
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
					intent.putExtra("category", "fast_breathing");
					startActivity(intent);
					break;
				case 3:
					intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
					intent.putExtra("category", "fast_breathing");
					startActivity(intent);
					break;
				case 4:
					intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
					intent.putExtra("category", "convulsion");
					startActivity(intent);
					break;
				case 5:
					intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
					intent.putExtra("category", "cyanosis");
					startActivity(intent);
					break;
				case 6:
					intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
					intent.putExtra("category", "low");
					startActivity(intent);
					break;
				case 7:
					intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
					intent.putExtra("category", "fever");
					startActivity(intent);
					break;
				case 8:
					intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
					intent.putExtra("category", "feeding");
					startActivity(intent);
					break;
				case 9:
					intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
					intent.putExtra("category", "hypothermia");
					startActivity(intent);
					break;
				case 10:
					intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
					intent.putExtra("category", "umbilicus");
					startActivity(intent);
					break;
				case 11:
					intent=new Intent(mContext, TakeActionSeverDiseasesActivity.class);
					intent.putExtra("category", "eye");
					startActivity(intent);
					break;
				
				}
				
			}
	    	
	    });
	}
	class SymptomsListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public SymptomsListAdapter(Context mContext,String[] listItems){
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
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setText(listItems[position]);
			    return convertView;
		}
		
	}

}
