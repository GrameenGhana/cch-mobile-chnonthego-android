package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.AboutActivity;
import org.digitalcampus.oppia.activity.HelpActivity;
import org.digitalcampus.oppia.activity.MainScreenActivity;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

public class AskBleedingActivity extends Activity {

	private ListView listView_bleeding;
	private Context mContext;
	String[] groupItems;
	String[] ChildItemsOne;
	String[] ChildItemsTwo;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_acuteemergencies_bleeding);
	    mContext=AskBleedingActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Diagnostic: Excessive Bleeding");
	    listView_bleeding=(ListView) findViewById(R.id.listView_excessiveBleeding);
	    String[] items={"Heavy(more than 2 pad changes in 24hours)","Light(fewer than 2 pad changes in 24hours)"};
	    ListAdapter adapter=new ListAdapter(mContext,items);
	    listView_bleeding.setAdapter(adapter);
	    listView_bleeding.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,TakeActionBleedingActivity.class);
					intent.putExtra("category", "heavy");
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(mContext,TakeActionBleedingActivity.class);
					intent.putExtra("category", "light");
					startActivity(intent);
					break;
				}
				
			}
	    	
	    });
	    /*
	    expandableListView_bleeding=(ExpandableListView) findViewById(R.id.expandableListView_acuteEmergencyBleeding);
	    groupItems= new String[]{"Light(fewer than 2 pad changes in 24hours)","Heavy(more than 2 pad changes in 24hours)"};
	    ChildItemsOne=new String[]{"Encourage fluid intake"," Refer to the nearest health centre or hospital immediately",
	    							"Call provider and arrange transport",
	    							"Record personal info in maternal health book. Fill out referral form",
	    							"Accompany client and have a family member accompany client",
	    							"Follow up with client after discharge from health centre or hospital"};
	    ChildItemsTwo=new String[]{"Place client in a lying-down position",
	    							"Encourage fluid intake",
	    							"Give 2 tablets of paracetamol for pain",
	    							"DO NOT PERFORM A VAGINAL EXAM",
	    							"Refer to the nearest health centre or hospital immediately",
	    							"Call provider and arrange transport",
	    							"Record personal info in maternal health book. Fill out referral form",
	    							"Accompany client and have a family member accompany client",
	    							"Follow up with client after discharge from health centre or hospital"};
	    BleedingExpandableListAdapter adapter=new BleedingExpandableListAdapter(mContext, groupItems, ChildItemsOne, ChildItemsTwo);
	    expandableListView_bleeding.setAdapter(adapter);
	    */
	    
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
			 text.setPadding(10, 0, 0, 0);
			 text.setText(items[position]);
			    return convertView;
		}
	}
	/*
	class BleedingExpandableListAdapter extends BaseExpandableListAdapter{
		Context mContext;
		String[] groupItems;
		String[] ChildItemsOne;
		String[] ChildItemsTwo;
		 public LayoutInflater minflater;
		
		public BleedingExpandableListAdapter(Context c, String[] groupItems,String[] ChildItemsOne,String[] ChildItemsTwo){
			this.mContext=c;
			this.groupItems=groupItems;
			this.ChildItemsOne=ChildItemsOne;
			this.ChildItemsTwo=ChildItemsTwo;
			 minflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getGroupCount() {
			return groupItems.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			int count = 0;
			switch(groupPosition){
			case 0:
				count=ChildItemsOne.length;
				break;
			case 1:
				count=ChildItemsTwo.length;
				break;
			}
			return count;
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setGravity(Gravity.LEFT);
			 text.setPadding(10, 0, 0, 0);
			 text.setText(groupItems[groupPosition]);
			    return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			switch(groupPosition){
			case 0:
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setGravity(Gravity.CENTER);
			 text.setTextColor(Color.rgb(104,176,54));
			 text.setText(ChildItemsOne[childPosition]);
			 break;
			case 1:
				TextView text2=(TextView) convertView.findViewById(R.id.textView_listViewText);
				 text2.setGravity(Gravity.CENTER);
				 text2.setTextColor(Color.rgb(104,176,54));
				 text2.setText(ChildItemsTwo[childPosition]);
				break;
			}
			    return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
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
	 	          goHome.setClass(mContext, MainScreenActivity.class);
	 	          goHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(goHome);
	 	          finish();
	 	         
	            return true;
	        case R.id.action_help:
	        	intent = new Intent(Intent.ACTION_MAIN);
	        	intent.setClass(mContext, HelpActivity.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(intent);
	 	          finish();
	 	         
	            return true;
	        case R.id.action_about:
	        	intent = new Intent(Intent.ACTION_MAIN);
	        	intent.setClass(mContext, AboutActivity.class);
	        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 	          startActivity(intent);
	 	          finish();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "ANC Diagnostic Excessive Bleeding", start_time.toString(), end_time.toString());
		finish();
	}
}
