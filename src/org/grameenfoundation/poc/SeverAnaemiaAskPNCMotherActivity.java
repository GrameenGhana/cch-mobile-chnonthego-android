package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.poc.JaundiceSectionsActivity.JaundiceListAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

public class SeverAnaemiaAskPNCMotherActivity extends BaseActivity {


	private Button button_no;
//	Context mContext;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private ExpandableListView listView_sections; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_severe_anaemia);
	    mContext=SeverAnaemiaAskPNCMotherActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Mother Diagnostic: Anaemia");
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    listView_sections=(ExpandableListView) findViewById(R.id.expandableListView1);
	    String[] items={"Severe anaemia","Moderate Anaemia "};
	    ListAdapter adapter=new ListAdapter(mContext,items,listView_sections);
	    listView_sections.setAdapter(adapter);
	    listView_sections.setOnChildClickListener(new OnChildClickListener(){

			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent;
				switch(groupPosition){
				case 0:
					intent=new Intent(mContext,TakeActionSevereMalariaPNCMotherActivity.class);
					intent.putExtra("category", "severe anaemia");
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(mContext,TakeActionSevereMalariaPNCMotherActivity.class); 
					intent.putExtra("category", "moderate_anaemia");
					startActivity(intent);
					break;
				
				}
				return true;
			}
	    	
	    });
	    
	    button_no=(Button) findViewById(R.id.button_no);
	    button_no.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext,TakeActionSevereMalariaPNCMotherActivity.class);
				intent.putExtra("category", "no anaemia");
				startActivity(intent);
			}
	    	
	    });
	}
	class ListAdapter extends BaseExpandableListAdapter {

		 public String[] groupItem;
		 public LayoutInflater minflater;
		 private int count;
		 public int lastExpandedGroupPosition;    
		 private Context mContext;
		 ExpandableListView eventsList;

		 public ListAdapter(Context mContext,String[] grList,
				 					ExpandableListView eventsList) {
		  groupItem = grList;
		  this.mContext=mContext;
		  minflater = LayoutInflater.from(mContext);
		  this.eventsList=eventsList;
		 
		 }
		 @Override
		 public long getChildId(int groupPosition, int childPosition) {
			 long id = 0;
				
				
			return id;
		 }

		 @Override
		 public View getChildView(int groupPosition, final int childPosition,
		   boolean isLastChild, View convertView, ViewGroup parent) {
		  
		   if(convertView==null){
			   convertView=minflater.inflate(R.layout.severe_jaundice_description,null);
		   }
		   
		   if(groupPosition==0){
			   TextView text1=(TextView) convertView.findViewById(R.id.textView2);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView3);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView4);
			   TextView text4=(TextView) convertView.findViewById(R.id.textView1);
			   CharSequence t1 = "Pallor";
			   SpannableString s1 = new SpannableString(t1);
			   s1.setSpan(new BulletSpan(15), 0, t1.length(), 0);
			   CharSequence t2 = "Hb <7 g/dL, if possible to measure";
			   SpannableString s2 = new SpannableString(t2);
			   s2.setSpan(new BulletSpan(15), 0, t2.length(), 0);
			   CharSequence t4 = "Click here to take action";
			   SpannableString s4 = new SpannableString(t4);
			   s4.setSpan(new BulletSpan(15), 0, t4.length(), 0);
			   text1.setText(s1);
			   text2.setText(s2);
			   text3.setText(" ");
			   text4.setText(s4);
		   }else if(groupPosition==1){
			   TextView text1=(TextView) convertView.findViewById(R.id.textView2);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView3);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView4);
			   TextView text4=(TextView) convertView.findViewById(R.id.textView1);
			   CharSequence t1 = "Hb 7-11 g/dL, if possible to measure";
			   SpannableString s1 = new SpannableString(t1);
			   s1.setSpan(new BulletSpan(15), 0, t1.length(), 0);
			   CharSequence t4 = "Click here to take action";
			   SpannableString s4 = new SpannableString(t4);
			   s4.setSpan(new BulletSpan(15), 0, t4.length(), 0);
			   text1.setText(s1);
			   text2.setText(" ");
			   text3.setText(" ");
			   text4.setText(s4);
		   }
		  return convertView;
		 }


		 													
		 @Override
		 public Object getGroup(int groupPosition) {
		  return null;
		 }


		 @Override
		 public View getGroupView(int groupPosition, boolean isExpanded,
		   View convertView, ViewGroup parent) {
			 if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.listview_text_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_listViewText);
			 text.setText(groupItem[groupPosition]);
			    return convertView;
		 }

		 
		 																																				
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groupItem.length;
		}


		@Override
		public int getChildrenCount(int groupPosition) {
			return 1;
		
		}


		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}



		@Override
		public String[] getChild(int groupPosition, int childPosition) {
			String[] item = null;

			return item;
				
		}



		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

				public void onGroupExpanded(int groupPosition) {
	    	
	    	if(groupPosition != lastExpandedGroupPosition){
	    	    eventsList.collapseGroup(lastExpandedGroupPosition);
	    }
	    	
	        super.onGroupExpanded(groupPosition);
	     
	        lastExpandedGroupPosition = groupPosition;
	        
	    }
	} 
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Mother Diagnostic: Anaemia", start_time.toString(), end_time.toString());
		finish();
	}
}
