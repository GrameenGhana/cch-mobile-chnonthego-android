package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class LowBirthWeightNextThreeActivity extends Activity {

	private ListView listView;
	private Button button_no;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_low_birth_weight_next_three);
	    dbh=new DbHelper(LowBirthWeightNextThreeActivity.this);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Breastfeeding Low Birth Weight Baby");
	   listView=(ListView) findViewById(R.id.listView_lowBirthWeightMenu);
	   String[] items={"Not well attached to the breast","Not suckling effectively",
			   			"Breastfeeds < 8 times in 24 hours ","Receive other foods or drinks ",
			   			"Not breastfeeding at all ","Low weight for age ",
			   			"Thrush (Ulcers or white patch in mouth ) "};
	   ListAdapter adapter=new ListAdapter(LowBirthWeightNextThreeActivity.this,items);
	   listView.setAdapter(adapter);
	   listView.setOnItemClickListener(new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent;
			switch(position){
			case 0:
				intent=new Intent(LowBirthWeightNextThreeActivity.this,TakeActionFeedingProblemsActivity.class);
				intent.putExtra("value", "not_attached");
				startActivity(intent);
				break;
			case 1:
				intent=new Intent(LowBirthWeightNextThreeActivity.this,TakeActionFeedingProblemsActivity.class);
				intent.putExtra("value", "not_suckling");
				startActivity(intent);
				break;
			case 2:
				intent=new Intent(LowBirthWeightNextThreeActivity.this,TakeActionFeedingProblemsActivity.class);
				intent.putExtra("value", "breastfeeding");
				startActivity(intent);
				break;
			case 3:
				intent=new Intent(LowBirthWeightNextThreeActivity.this,TakeActionFeedingProblemsActivity.class);
				intent.putExtra("value", "receive_food");
				startActivity(intent);
				break;
			case 4:
				intent=new Intent(LowBirthWeightNextThreeActivity.this,TakeActionFeedingProblemsActivity.class);
				intent.putExtra("value", "not_breastfeeding");
				startActivity(intent);
				break;
			case 5:
				intent=new Intent(LowBirthWeightNextThreeActivity.this,TakeActionFeedingProblemsActivity.class);
				intent.putExtra("value", "low_weight_for_age");
				startActivity(intent);
				break;
			case 6:
				intent=new Intent(LowBirthWeightNextThreeActivity.this,TakeActionFeedingProblemsActivity.class);
				intent.putExtra("value", "thrush");
				startActivity(intent);
				break;
			}
		}
		   
	   });
	   button_no=(Button) findViewById(R.id.button_no);
	   button_no.setOnClickListener(new OnClickListener(){

		@Override
		public void onClick(View v) {
		Intent intent=new Intent(LowBirthWeightNextThreeActivity.this,TakeActionFeedingProblemsActivity.class);
		intent.putExtra("value", "no_feeding_problems");
			startActivity(intent);
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
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setText(listItems[position]);
			    return convertView;
		}
		
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Counselling Breastfeeding Low Birth Weight Baby", start_time.toString(), end_time.toString());
		finish();
	}
}
