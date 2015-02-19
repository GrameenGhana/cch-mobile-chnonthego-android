package org.grameenfoundation.adapters;

import java.util.ArrayList;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.model.CourseChild;
import org.digitalcampus.oppia.model.Lang;
import org.digitalcampus.oppia.model.Scores;
import org.grameenfoundation.cch.model.CourseAchievments;
import org.grameenfoundation.cch.model.CourseGroups;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.TargetsForAchievements;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class CoursesAchievementAdapter extends BaseExpandableListAdapter{
	private Context mContext;
	// public Activity activity;
	// private final SparseArray<CourseGroups> groups;
	//  public LayoutInflater inflater;
	
	 private final ArrayList<CourseAchievments> results;
	private LayoutInflater minflater;
	private ExpandableListView list;
	private final ArrayList<Course> courseList;
	private SharedPreferences prefs;
	private JSONObject json;
	
	 public CoursesAchievementAdapter(Context c,ArrayList<CourseAchievments> Results,ArrayList<Course> CourseList) {
		 	mContext = c;
		 	results = new ArrayList<CourseAchievments>();
		 	courseList = new ArrayList<Course>();
		 	results.addAll(Results);
		 	courseList.addAll(CourseList);
		 	prefs = PreferenceManager.getDefaultSharedPreferences(c);
		 	minflater=LayoutInflater.from(mContext);

	 }
	@Override
	public int getGroupCount() {
		  return courseList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
	        return results.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}
	@Override
	  public void onGroupCollapsed(int groupPosition) {
	    super.onGroupCollapsed(groupPosition);
	  }

	  @Override
	  public void onGroupExpanded(int groupPosition) {
	    super.onGroupExpanded(groupPosition);
	  }

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			   convertView = minflater.inflate(R.layout.listview_single,parent, false);
			  }
			   TextView category=(TextView) convertView.findViewById(R.id.textView_textSingle);
			   category.setText(courseList.get(groupPosition).getTitle(prefs.getString(mContext.getString(R.string.prefs_language), Locale
				.getDefault().getLanguage())));
			   
			  return convertView;
	}

	@SuppressLint("NewApi")
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			   convertView = minflater.inflate(R.layout.course_details_listview_single,parent, false);
			  }
		TextView topic=(TextView) convertView.findViewById(R.id.textView_topic);
		   TextView testType=(TextView) convertView.findViewById(R.id.textView_testType);
		   TextView score=(TextView) convertView.findViewById(R.id.textView_score);
		   TextView percentage=(TextView) convertView.findViewById(R.id.textView_percentage);
		   topic.setText(results.get(childPosition).getCourseSection());
		   testType.setText(results.get(childPosition).getType());
		   score.setText(results.get(childPosition).getScore());
		   percentage.setText(results.get(childPosition).getPercentage()+"%");
			  return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
