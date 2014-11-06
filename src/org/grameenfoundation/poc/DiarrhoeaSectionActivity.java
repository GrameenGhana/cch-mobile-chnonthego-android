package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
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

public class DiarrhoeaSectionActivity extends Activity {

	private ExpandableListView listView_diarrhoeaSections;
	Context mContext;
	private Button button_no;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_diarrhoea_sections);
	    mContext=DiarrhoeaSectionActivity.this;
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("Postnatal Care Diagnostic");
    listView_diarrhoeaSections=(ExpandableListView) findViewById(R.id.expandableListView_diarrhoeaSections);
	    String[] items={"Diarrhoea with Severe Dehydration ",
	    				"Diarrhoea with Some Dehydration",
	    				"Diarrhoea with No Dehydration",
	    				"Severe persistent diarrhoea",
	    				"Blood in stool"};
	    DiarrhoeaSectionsListAdapter adapter=new DiarrhoeaSectionsListAdapter(mContext,items,listView_diarrhoeaSections);
	    listView_diarrhoeaSections.setAdapter(adapter);
	    listView_diarrhoeaSections.setOnChildClickListener(new OnChildClickListener(){

		
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent;
				switch(groupPosition){
				case 0:
					intent=new Intent(mContext,TakeActionSevereDehydrationActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent=new Intent(mContext,TakeActionSomeDehydration.class);
					startActivity(intent);
					break;
				case 2:
					intent=new Intent(mContext,TakeActionDiarrhoeaNoDehydrationActivity.class);
					startActivity(intent);
					break;
				case 3:
					intent=new Intent(mContext,TakeActionDiarrhoeaActivity.class);
					intent.putExtra("value", "severe_diarrhoea");
					startActivity(intent);
					break;
				case 4:
					intent=new Intent(mContext,TakeActionDiarrhoeaActivity.class);
					intent.putExtra("value", "blood_stool");
					startActivity(intent);
					break;
				}
			
				return true;
			}
	    	
	    });
	     button_no=(Button) findViewById(R.id.button_no);
	     button_no.setOnClickListener(new OnClickListener(){

			private Intent intent;

			@Override
			public void onClick(View v) {
				intent=new Intent(mContext,TakeActionDiarrhoeaActivity.class);
				intent.putExtra("value", "no_diarrhoea");
				startActivity(intent);
			}
	    	 
	     });
	}
	
	class DiarrhoeaSectionsListAdapter extends BaseExpandableListAdapter {

		 public String[] groupItem;
		 public LayoutInflater minflater;
		 private int count;
		 public int lastExpandedGroupPosition;    
		 private Context mContext;
		 ExpandableListView eventsList;

		 public DiarrhoeaSectionsListAdapter(Context mContext,String[] grList,
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
			   CharSequence t1 = "Movement only when stimulated or no movement ";
			   SpannableString s1 = new SpannableString(t1);
			   s1.setSpan(new BulletSpan(15), 0, t1.length(), 0);
			   CharSequence t2 = "Sunken eyes";
			   SpannableString s2 = new SpannableString(t2);
			   s2.setSpan(new BulletSpan(15), 0, t2.length(), 0);
			   CharSequence t3 = "Skin pinch goes back very slowly ";
			   SpannableString s3 = new SpannableString(t3);
			   s3.setSpan(new BulletSpan(15), 0, t3.length(), 0);
			   text1.setText(s1);
			   text2.setText(s2);
			   text3.setText(s3);
		   }else if(groupPosition==1){
			   TextView text1=(TextView) convertView.findViewById(R.id.textView2);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView3);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView4);
			   CharSequence t1 = "Restless and irritable";
			   SpannableString s1 = new SpannableString(t1);
			   s1.setSpan(new BulletSpan(15), 0, t1.length(), 0);
			   CharSequence t2 = "Sunken eyes";
			   SpannableString s2 = new SpannableString(t2);
			   s2.setSpan(new BulletSpan(15), 0, t2.length(), 0);
			   CharSequence t3 = "Skin pinch goes back slowly";
			   SpannableString s3 = new SpannableString(t3);
			   s3.setSpan(new BulletSpan(15), 0, t3.length(), 0);
			   text1.setText(s1);
			   text2.setText(s2);
			   text3.setText(s3);
		   }else if(groupPosition==2){
			   TextView text1=(TextView) convertView.findViewById(R.id.textView2);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView3);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView4);
			   CharSequence t1 = "Not enough signs to classify as some or severe dehydration ";
			   SpannableString s1 = new SpannableString(t1);
			   s1.setSpan(new BulletSpan(15), 0, t1.length(), 0);
			   text1.setText(s1);
			   text2.setText("");
			   text3.setText("");
		   }
		   else if(groupPosition==3){
			   TextView text1=(TextView) convertView.findViewById(R.id.textView2);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView3);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView4);
			   CharSequence t1 = "Diarrhoea lasting ≥ 14 days ";
			   SpannableString s1 = new SpannableString(t1);
			   s1.setSpan(new BulletSpan(15), 0, t1.length(), 0);
			   text1.setText(s1);
			   text2.setText("");
			   text3.setText("");
		   }else if(groupPosition==4){
			   TextView text1=(TextView) convertView.findViewById(R.id.textView2);
			   TextView text2=(TextView) convertView.findViewById(R.id.textView3);
			   TextView text3=(TextView) convertView.findViewById(R.id.textView4);
			   CharSequence t1 = "Blood in stool";
			   SpannableString s1 = new SpannableString(t1);
			   s1.setSpan(new BulletSpan(15), 0, t1.length(), 0);
			   text1.setText(s1);
			   text2.setText("");
			   text3.setText("");
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
	
	

}
