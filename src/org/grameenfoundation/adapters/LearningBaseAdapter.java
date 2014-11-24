package org.grameenfoundation.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.digitalcampus.mobile.learningGF.R;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class LearningBaseAdapter extends BaseAdapter {
	 private final ArrayList<String> learningCategory;
	 private final ArrayList<String> learningDescription;
	 private final ArrayList<String> learningDueDate;
	 private final ArrayList<String> learningStatus;
	 private final ArrayList<String> learningTopic;
	 private final ArrayList<String> learningId;
	 
	 public LayoutInflater minflater;
	 private Context mContext;

	 public LearningBaseAdapter(Context mContext,ArrayList<String> learningCategory ,
				ArrayList<String> learningDescription,
				ArrayList<String> learningDueDate,
				ArrayList<String> learningStatus,
				ArrayList<String> learningTopic,
				ArrayList<String>  learningId) {
		 		this.mContext = mContext;
		 		this.learningCategory = learningCategory;
		 		this.learningDescription=learningDescription;
		 		this.learningTopic=learningTopic;
		 		this.learningDueDate=learningDueDate;
		 		this.learningStatus=learningStatus;
		 		this.learningId=learningId;
		 		minflater = LayoutInflater.from(mContext);
	 }
	 
	@Override
	public int getCount() {
		return learningCategory.size();
	}
	@Override
	public String[] getItem(int position) {
		String[] items={learningCategory.get(position),
						learningDescription.get(position),
						learningTopic.get(position),
						learningDueDate.get(position),
						learningStatus.get(position),
						learningId.get(position)};
		
		return items;
	}
	@Override
	public long getItemId(int position) {
		long id=0;
		//id=Integer.valueOf(learningId.get(position));
		return id;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View list = null;
		if(convertView==null){
			  LayoutInflater inflater = (LayoutInflater) mContext
      		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      	  list = new View(mContext);
      	  list = inflater.inflate(R.layout.learning_listview_single, null);
     
        } else {
      	  list = (View) convertView;
        }
	 
	   TextView text=(TextView) list.findViewById(R.id.textView_learningCategory);
	   TextView text2=(TextView) list.findViewById(R.id.textView_learningCourse);
	   TextView text3=(TextView) list.findViewById(R.id.textView_learningTopic);
	   TextView text4=(TextView) list.findViewById(R.id.textView_dueDate);
	   ImageView image=(ImageView) list.findViewById(R.id.imageView1);
	   text.setText(learningCategory.get(position));
	   text2.setText(learningDescription.get(position));
	   text3.setText(learningTopic.get(position));
	   text4.setText(learningDueDate.get(position));
	   /*
	   if(!learningStatus.isEmpty()){
	   if(learningStatus!=null&&learningStatus.get(position).equalsIgnoreCase("updated")){
		   image.setImageResource(R.drawable.ic_achieved);
	   }else if(learningStatus!=null&&learningStatus.get(position).equalsIgnoreCase("new_record")){
		   image.setImageResource(R.drawable.ic_loading);
	   }else if(learningStatus!=null&&learningStatus.get(position).equalsIgnoreCase("not_achieved")){
		   image.setImageResource(R.drawable.ic_not_achieved);
	   }
	   }
	   */
		return list;
	
	}
	
}
