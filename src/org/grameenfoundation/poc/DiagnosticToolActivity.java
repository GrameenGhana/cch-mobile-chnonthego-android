package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DiagnosticToolActivity extends Activity implements OnItemClickListener{

	private Context mContext;
	private ListView listView_encounter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_encounter);
	    mContext=DiagnosticToolActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic");
	    listView_encounter=(ListView) findViewById(R.id.listView_encounter);
	    listView_encounter.setOnItemClickListener(this);
	    /*
	    String[] category={"Breast Problems","Complication Readiness & Newborn Danger Signs",
	    					"Family Planning","Home Care for the infant",
	    					"Immunisation Schedule for Infant", "Infant Feeding",
	    					"Kangaroo Mother Care- Keeping Low Birth Weight Baby Warm at Home",
	    					"Keeping Infant Warm & Breastfeeding on the Way to the Hospital",
	    					"Malaria Prevention"};
	   // String[] categoryDetails={"Importance of Exclusive Breastfeeding, Breast Attachement"};
	    DiagnosticToolBaseAdapter adapter=new DiagnosticToolBaseAdapter(mContext,category);
	    listView_diagnosticMenu.setAdapter(adapter);
	    */
	    String[] encounter_location={"Home Visit","Outreach Clinic","CHPS facility","Health Center","Hospital"};
	    ListAdapter adapter=new ListAdapter(mContext, encounter_location);
	    listView_encounter.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent;
		switch(position){
		case 0:
			intent=new Intent(mContext, AcuteEmergenciesActivity.class);
			startActivity(intent);
			break;
		case 1:
			intent=new Intent(mContext, AcuteEmergenciesActivity.class);
			startActivity(intent);
			break;
		case 2:
			intent=new Intent(mContext, AcuteEmergenciesActivity.class);
			startActivity(intent);
			break;
		case 3:
			intent=new Intent(mContext, AcuteEmergenciesActivity.class);
			startActivity(intent);
			break;
		case 4:
			intent=new Intent(mContext, AcuteEmergenciesActivity.class);
			startActivity(intent);
			break;
		}
		
	}
	
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] items;
		 public LayoutInflater minflater;
		public ListAdapter(Context mContext,String[] items){
			this.mContext=mContext;
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

}
