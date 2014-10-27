package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class FirstVisitActivity extends Activity {
	Context mContext;
	String[] groupItems;
	String[] ChildItemsOne;
	String[] ChildItemsTwo;
	private ExpandableListView expandableListView_firstVisit;
	private Button button_estimate;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_first_visit);
	    mContext=FirstVisitActivity.this;
	    groupItems=new String[]{"Record Personal Information","Take medical history"};
	    ChildItemsOne=new String[]{"Name"," Age","Home address","Occupation","Marital status: Husband or partner","Next of kin: Name, address, telephone number"};
	    ChildItemsTwo=new String[]{" Past obstetric history"," Past contraceptive history","Personal medical and surgical history, including any known allergies to medication","Family medical history","History of present pregnancy"};
	    expandableListView_firstVisit=(ExpandableListView) findViewById(R.id.expandableListView_firstVisit);
	    FirstVisitExpandableListAdapter adapter=new FirstVisitExpandableListAdapter(mContext,groupItems,ChildItemsOne,ChildItemsTwo);
	    expandableListView_firstVisit.setAdapter(adapter);
	    button_estimate=(Button) findViewById(R.id.button_estimateTrimester);
	    button_estimate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,EstimateTrimester.class);
				startActivity(intent);
			}
	    	
	    });
	}

	
	class FirstVisitExpandableListAdapter extends BaseExpandableListAdapter{
		Context mContext;
		String[] groupItems;
		String[] ChildItemsOne;
		String[] ChildItemsTwo;
		 public LayoutInflater minflater;
		
		public FirstVisitExpandableListAdapter(Context c, String[] groupItems,String[] ChildItemsOne,String[] ChildItemsTwo){
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
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			switch(groupPosition){
			case 0:
			
			 text.setGravity(Gravity.CENTER);
			 text.setTextColor(Color.rgb(104,176,54));
			 text.setText(ChildItemsOne[childPosition]);
			 break;
			case 1:
				 text.setGravity(Gravity.CENTER);
				 text.setTextColor(Color.rgb(104,176,54));
				 text.setText(ChildItemsTwo[childPosition]);
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
}
