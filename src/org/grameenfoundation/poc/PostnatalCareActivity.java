package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PostnatalCareActivity extends Activity{

	Context mContext;
	private ListView listView_postnatal;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_postnatal_care_menu);
	    mContext=PostnatalCareActivity.this;
	    listView_postnatal=(ListView) findViewById(R.id.listView_postnatalCare);
	    String[] items={"Diagnostic Tool","Counselling"};
	    int[] images={R.drawable.ic_diagnostic,R.drawable.ic_counselling};
	    PostnatalCareListAdapter adapter=new PostnatalCareListAdapter(mContext,items,images);
	    listView_postnatal.setAdapter(adapter);
	    listView_postnatal.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent;
				switch(position){
				case 0:
					intent=new Intent(mContext,PostnatalCareSectionActivity.class);
					startActivity(intent);
					break;
				}
				
			}
	    	
	    });
	    /*
	    expandablelistView_postnatal=(ExpandableListView) findViewById(R.id.expandableListView_postnatalCare);
	    String[] groupitems={"Diagnostic Tool","Counselling"};
	    String[] ChildItemsOne={"Newborn Emergency","Records & History","Very Severe Disease & Local Bacterial Infections",
	    						"Jaundice","Other Serious Conditions, Birth Injury & Abnormalities",
	    						"Diarrhoea","HIV Infection ","Feeding Problems, Low Weight for Age"};
	    int[] images={R.drawable.ic_diagnostic,R.drawable.ic_counselling};
	    String[] ChildItemsTwo={"PNC Visit 1","PNC Visit 2","PNC Visit 3"};
	    PostnatalCareListAdapter adapter=new PostnatalCareListAdapter(mContext,groupitems,ChildItemsOne,ChildItemsTwo,images);
	    expandablelistView_postnatal.setAdapter(adapter);
	    expandablelistView_postnatal.setOnChildClickListener(new OnChildClickListener(){

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent;
				if(groupPosition==0){
					switch(childPosition){
					case 0:
						intent=new Intent(mContext,NewbornEmergenciesActivity.class);
						startActivity(intent);
						break;
				}
				}
				return true;
			}
	    	
	    });
	    */
	}
	class PostnatalCareListAdapter extends BaseAdapter{
		Context mContext;
		String[] items;
		 public LayoutInflater minflater;
		 int[] images;
		
		public PostnatalCareListAdapter(Context c, String[] items,int[] images){
			this.mContext=c;
			this.items=items;
			 minflater = LayoutInflater.from(mContext);
			 this.images=images;
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
			      
				  convertView = minflater.inflate(R.layout.expandable_group_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_groupCategory);
			 text.setGravity(Gravity.LEFT);
			 text.setPadding(10, 0, 0, 0);
			 text.setText(items[position]);
			    return convertView;
		}
	}
	/*
	class PostnatalCareListAdapter extends BaseExpandableListAdapter{
		Context mContext;
		String[] groupItems;
		String[] ChildItemsOne;
		String[] ChildItemsTwo;
		 public LayoutInflater minflater;
		 int[] images;
		
		public PostnatalCareListAdapter(Context c, String[] groupItems,String[] ChildItemsOne,String[] ChildItemsTwo,int[] images){
			this.mContext=c;
			this.groupItems=groupItems;
			this.ChildItemsOne=ChildItemsOne;
			this.ChildItemsTwo=ChildItemsTwo;
			 minflater = LayoutInflater.from(mContext);
			 this.images=images;
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
			      
				  convertView = minflater.inflate(R.layout.expandable_group_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_groupCategory);
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
}
