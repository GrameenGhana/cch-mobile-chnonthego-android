package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.model.Scores;
import org.grameenfoundation.cch.model.CourseAchievments;
import org.json.JSONException;
import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CourseDetailActivity extends Activity {

	private ExpandableListView expandableListview;
	private Context mContext;
	private DbHelper db;
	private TextView textView_number;
	private TextView textView_label;
	private ListAdapter adapter;
	ArrayList<CourseAchievments> ksa_eligible;
	ArrayList<CourseAchievments> ksa_passed;
	ArrayList<CourseAchievments> in_progress;
	private SharedPreferences prefs;
	private LinearLayout linearLayout;
	private int month;
	private int year;
	private Long start_time;
	private Long end_time;
	private JSONObject data;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_course_achievements);
	    expandableListview = (ExpandableListView) findViewById(R.id.expandableListView1);
	    mContext=CourseDetailActivity.this;
	    db=new DbHelper(CourseDetailActivity.this);
	    start_time=System.currentTimeMillis();
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          month= extras.getInt("month");
          year=extras.getInt("year");
        }
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Achievement Center");
	    getActionBar().setSubtitle("Achievement Details");
	    textView_label=(TextView) findViewById(R.id.textView_label);
	    textView_label.setText("Courses");
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    ksa_eligible = db.getQuizzesForAchievements("ksa_eligible");
	    ksa_passed = db.getQuizzesForAchievements("ksa_passed");
	    in_progress = db.getQuizzesForAchievements("in_progress");
	    int all_courses=ksa_eligible.size()+ksa_passed.size()+in_progress.size();
	    textView_number.setText(" ("+String.valueOf(all_courses)+")");
	    linearLayout=(LinearLayout) findViewById(R.id.Linearlayout_progress);
	    linearLayout.setVisibility(View.GONE);
	   String[] group={"Passed NMC Certification Test ("+ksa_passed.size()+")",
			   			"Pending NMC Certification ("+ksa_eligible.size()+")",
			   			"In progress ("+in_progress.size()+")"};
	    adapter=new ListAdapter(mContext,group,ksa_passed,ksa_eligible,in_progress);
	    expandableListview.setAdapter(adapter);
	    expandableListview.setOnChildClickListener(new OnChildClickListener(){

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent=new Intent(mContext,CourseAchievementActivity.class);
				intent.putExtra("modid", id);
				intent.putExtra("month", month);
				intent.putExtra("year", year);
				return false;
			
	    }
	    });
	}
	class ListAdapter extends BaseExpandableListAdapter{
		Context mContext;
		ArrayList<CourseAchievments> ksa_eligible;
		ArrayList<CourseAchievments> ksa_passed;
		ArrayList<CourseAchievments> in_progress;
		public LayoutInflater minflater;
		 int[] images;
		 String[] groupItems;
	
		
		public ListAdapter(Context c,String[] group,ArrayList<CourseAchievments> ksa_passed,
				ArrayList<CourseAchievments> ksa_eligible,ArrayList<CourseAchievments> in_progress){
			this.mContext=c;
			this.ksa_eligible = new ArrayList<CourseAchievments>();
			this.ksa_passed = new ArrayList<CourseAchievments>();
			this.in_progress = new ArrayList<CourseAchievments>();
			groupItems=group;
			this.ksa_eligible.addAll(ksa_eligible);
			this.ksa_passed.addAll(ksa_passed);
			this.in_progress.addAll(in_progress);
			 minflater = LayoutInflater.from(mContext);
			 prefs = PreferenceManager.getDefaultSharedPreferences(c);
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return groupItems.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
				int count=0;
			switch(groupPosition){
			case 0:
				count=ksa_passed.size();
				break;
			case 1:
				count=ksa_eligible.size();
				break;
			case 2:
				count=in_progress.size();
				break;
			}
			return count;
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
			Long id = null;
			switch(groupPosition){
			case 0:
				id=Long.parseLong(ksa_passed.get(childPosition).getId());
			
				break;
			case 1:
				id=Long.parseLong(ksa_eligible.get(childPosition).getId());
				break;
			case 2:
				id=Long.parseLong(in_progress.get(childPosition).getId());
				break;
			}
			return id;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.other_listview_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_otherCategory);
			 ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
			 text.setText(groupItems[groupPosition]);
			 image.setImageResource(R.drawable.ic_courses);


			    return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.course_achievements_detail,parent, false);
			    }
			if(groupPosition==0){
				TextView title=(TextView) convertView.findViewById(R.id.textView_courseTitle);
				 TextView score=(TextView) convertView.findViewById(R.id.textView_finalExam);
				 TextView completion=(TextView) convertView.findViewById(R.id.textView_completion);
				 ProgressBar progress=(ProgressBar) convertView.findViewById(R.id.progressBar_completion);
				 TextView date=(TextView) convertView.findViewById(R.id.textView_date);
				 title.setText(ksa_passed.get(childPosition).getCourseTitle());
				 score.setText("Final exam score: "+ksa_passed.get(childPosition).getScore()+";"+"Attempts:"+ksa_passed.get(childPosition).getCourseAttempts());
				 completion.setText(ksa_passed.get(childPosition).getPercentage()+"% complete");
				 progress.setProgress(Integer.valueOf(ksa_passed.get(childPosition).getPercentage()));
				 date.setText("Last seen: "+ksa_passed.get(childPosition).getDateTaken());
			}else if(groupPosition==1){
				 TextView title=(TextView) convertView.findViewById(R.id.textView_courseTitle);
				 TextView score=(TextView) convertView.findViewById(R.id.textView_finalExam);
				 TextView completion=(TextView) convertView.findViewById(R.id.textView_completion);
				 ProgressBar progress=(ProgressBar) convertView.findViewById(R.id.progressBar_completion);
				 TextView date=(TextView) convertView.findViewById(R.id.textView_date);
				 title.setText(ksa_eligible.get(childPosition).getCourseTitle());
				 score.setText("Final exam score: "+ksa_eligible.get(childPosition).getScore()+";"+"Attempts:"+ksa_eligible.get(childPosition).getCourseAttempts());
				 completion.setText(ksa_eligible.get(childPosition).getPercentage()+"% complete");
				 progress.setProgress(Integer.valueOf(ksa_eligible.get(childPosition).getPercentage()));
				 date.setText("Last seen: "+ksa_eligible.get(childPosition).getDateTaken());
			}else if(groupPosition==2){
				TextView title=(TextView) convertView.findViewById(R.id.textView_courseTitle);
				 TextView score=(TextView) convertView.findViewById(R.id.textView_finalExam);
				 TextView completion=(TextView) convertView.findViewById(R.id.textView_completion);
				 ProgressBar progress=(ProgressBar) convertView.findViewById(R.id.progressBar_completion);
				 TextView date=(TextView) convertView.findViewById(R.id.textView_date);
				 title.setText(in_progress.get(childPosition).getCourseTitle());
				 score.setText("Final exam score: "+in_progress.get(childPosition).getScore()+";"+"Attempts:"+in_progress.get(childPosition).getCourseAttempts());
				 completion.setText(in_progress.get(childPosition).getPercentage()+"% complete");
				 progress.setProgress(Integer.valueOf(in_progress.get(childPosition).getPercentage()));
				 date.setText("Last seen: "+in_progress.get(childPosition).getDateTaken());
			}


			    return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		 data=new JSONObject();
		    try {
		    	data.put("page", "Course Achievements Details");
		    	data.put("ver", db.getVersionNumber(CourseDetailActivity.this));
		    	data.put("battery", db.getBatteryStatus(CourseDetailActivity.this));
		    	data.put("device", db.getDeviceName());
				data.put("imei", db.getDeviceImei(CourseDetailActivity.this));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		db.insertCCHLog("Achievement Center", data.toString(), start_time.toString(), end_time.toString());
		finish();
	}
}
