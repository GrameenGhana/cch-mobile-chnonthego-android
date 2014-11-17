package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class InfantFeedingMenuActivity extends Activity {

	private ListView listView_infantFeeding;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_infant_feeding_menu);
	    dbh=new DbHelper(InfantFeedingMenuActivity.this);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Infant Feeding");
	    listView_infantFeeding=(ListView) findViewById(R.id.listView_infantFeeding);
	    String[] items={"Importance of Exclusive Breastfeeding","Breast Attachment",
	    				"How Often to Breastfeed","Breastfeeding a Low Birth Weight Baby",
	    				"How to Express Breast Milk","When You Are Separated from Your Baby",
	    				"Feeding the Sick Baby Less than 6 Months of Age",
	    				"Exclusively Breastfeeding When Mother is Taking ARVs",
	    				"Exclusively Breastfeeding When Mother is NOT Taking ARVs",
	    				"Things for Mother to Remember"};
	    ListAdapter adapter=new ListAdapter(InfantFeedingMenuActivity.this,items);
	    listView_infantFeeding.setAdapter(adapter);
	    listView_infantFeeding.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(InfantFeedingMenuActivity.this,ExclusiveBreastFeedingActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(InfantFeedingMenuActivity.this,BreastAttachementActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(InfantFeedingMenuActivity.this,FeedingFrequencyActivity.class);
					startActivity(intent);
					break;
				case 3:
					intent=new Intent(InfantFeedingMenuActivity.this,InfantFeedingNextActivity.class);
					intent.putExtra("value", "low_birth_weight");
					startActivity(intent);
					break;
				case 4:
					intent=new Intent(InfantFeedingMenuActivity.this,ExpressBreastmilkActivity.class);
					startActivity(intent);
					break;
				case 5:
					intent=new Intent(InfantFeedingMenuActivity.this,InfantFeedingNextActivity.class);
					intent.putExtra("value", "separated_from_baby");
					startActivity(intent);
					break;
				case 6:
					intent=new Intent(InfantFeedingMenuActivity.this,InfantFeedingNextActivity.class);
					intent.putExtra("value", "feeding_sick_baby");
					startActivity(intent);
					break;
				case 7:
					intent=new Intent(InfantFeedingMenuActivity.this,InfantFeedingNextActivity.class);
					intent.putExtra("value", "taking_arv");
					startActivity(intent);
					break;
				case 8:
					intent=new Intent(InfantFeedingMenuActivity.this,InfantFeedingNextActivity.class);
					intent.putExtra("value", "not_taking_arv");
					startActivity(intent);
					break;
				case 9:
					intent=new Intent(InfantFeedingMenuActivity.this,InfantFeedingNextActivity.class);
					intent.putExtra("value", "mother_remember");
					startActivity(intent);
					break;
				}
			}
	    	
	    });
	}
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] items;
		 public LayoutInflater minflater;
		
		public ListAdapter(Context c, String[] items){
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
			 text.setGravity(Gravity.LEFT);
			 text.setPadding(10, 0, 0, 0);
			 text.setText(items[position]);
			    return convertView;
		}
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Counselling Infant Feeding", start_time.toString(), end_time.toString());
		finish();
	}
}
