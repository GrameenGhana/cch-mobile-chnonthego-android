package org.grameenfoundation.adapters;

import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.cch.model.CourseAchievments;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class CourseAchievementsAdapter extends BaseAdapter{
	Context mContext;
	ArrayList<CourseAchievments> course_achievements;
	public LayoutInflater minflater;
	 int[] images;

	
	public CourseAchievementsAdapter(Context c,ArrayList<CourseAchievments> Course_achievements){
		this.mContext=c;
		course_achievements = new ArrayList<CourseAchievments>();
		course_achievements.addAll(Course_achievements);
		 minflater = LayoutInflater.from(mContext);
		 
	}

	@Override
	public int getCount() {
		return course_achievements.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			   convertView = minflater.inflate(R.layout.course_details_listview_single,parent, false);
			  }
			   TextView topic=(TextView) convertView.findViewById(R.id.textView_topic);
			   TextView testType=(TextView) convertView.findViewById(R.id.textView_testType);
			   TextView score=(TextView) convertView.findViewById(R.id.textView_score);
			   TextView percentage=(TextView) convertView.findViewById(R.id.textView_percentage);
			   topic.setText(course_achievements.get(position).getCourseSection());
			   testType.setText(course_achievements.get(position).getType());
			   score.setText(course_achievements.get(position).getScore());
			   percentage.setText(course_achievements.get(position).getPercentage()+"%");
			   Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
		       	      "fonts/Roboto-Thin.ttf");
			   topic.setTypeface(custom_font);
			   testType.setTypeface(custom_font);
			   score.setTypeface(custom_font);
			   percentage.setTypeface(custom_font);
			   
		    return convertView;
	}

	public void add(String value) {
		// TODO Auto-generated method stub
		
	}
	
}