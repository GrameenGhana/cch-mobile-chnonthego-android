package org.grameenfoundation.poc;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class JaundiceSectionsActivity extends BaseActivity {

	private ExpandableListView listView_sections;
//	private Context mContext;
	private Button button_no;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    mContext=JaundiceSectionsActivity.this;
	    setContentView(R.layout.activity_jaundice_sections);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Diagnostic: Jaundice");
	    mContext=JaundiceSectionsActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    listView_sections=(ExpandableListView) findViewById(R.id.expandableListView_jaundice);
	    String[] items={"Severe Jaundice","Jaundice"};
	    JaundiceListAdapter adapter=new JaundiceListAdapter(mContext,items,listView_sections);
	    listView_sections.setAdapter(adapter);
	    button_no=(Button) findViewById(R.id.button_no);
	    button_no.setOnClickListener(new OnClickListener(){

			private Intent intent;

			@Override
			public void onClick(View v) {
				intent=new Intent(mContext,TakeActionJaundiceActivity.class); 
				intent.putExtra("category", "no jaundice");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    listView_sections.setOnChildClickListener(new OnChildClickListener(){

			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent;
				switch(groupPosition){
				case 0:
					intent=new Intent(mContext,TakeActionJaundiceActivity.class); 
					intent.putExtra("category", "severe");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				case 1:
					intent=new Intent(mContext,TakeActionJaundiceActivity.class); 
					intent.putExtra("category", "jaundice");
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
					break;
				
				}
				return true;
			}
	    	
	    });
	}
	class JaundiceListAdapter extends BaseExpandableListAdapter {

		 public String[] groupItem;
		 public LayoutInflater minflater;
		 private int count;
		 public int lastExpandedGroupPosition;    
		 private Context mContext;
		 ExpandableListView eventsList;

		 public JaundiceListAdapter(Context mContext,String[] grList,
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
			   CharSequence t1 = "Any jaundice if newborn is < 48 hours old ";
			   SpannableString s1 = new SpannableString(t1);
			   s1.setSpan(new BulletSpan(15), 0, t1.length(), 0);
			   CharSequence t2 = "OR > 14 days old";
			   SpannableString s2 = new SpannableString(t2);
			   s2.setSpan(new BulletSpan(15), 0, t2.length(), 0);
			   CharSequence t3 = "OR has yellow palms and soles at any age  ";
			   SpannableString s3 = new SpannableString(t3);
			   s3.setSpan(new BulletSpan(15), 0, t3.length(), 0);
			   CharSequence t4 = "Click here to take action";
			   SpannableString s4 = new SpannableString(t4);
			   s4.setSpan(new BulletSpan(15), 0, t4.length(), 0);
			   text1.setText(s1);
			   text2.setText(s2);
			   text3.setText(s3);
			   text4.setText(s4);
		   }else if(groupPosition==1){
			   TextView text1=(TextView) convertView.findViewById(R.id.textView2);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView3);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView4);
			   TextView text4=(TextView) convertView.findViewById(R.id.textView1);
			   CharSequence t1 = "Jaundice if baby > 48 hours old and < 14 days old";
			   SpannableString s1 = new SpannableString(t1);
			   s1.setSpan(new BulletSpan(15), 0, t1.length(), 0);
			   CharSequence t2 = "AND palms and soles are not yellow";
			   SpannableString s2 = new SpannableString(t2);
			   s2.setSpan(new BulletSpan(15), 0, t2.length(), 0);
			   CharSequence t4 = "Click here to take action";
			   SpannableString s4 = new SpannableString(t4);
			   s4.setSpan(new BulletSpan(15), 0, t4.length(), 0);
			   text1.setText(s1);
			   text2.setText(s2);
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
		dbh.insertCCHLog("Point of Care", "PNC Diagnostic Jaundice", start_time.toString(), end_time.toString());
		finish();
	}
}
