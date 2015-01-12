package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.model.Course;
import org.grameenfoundation.cch.model.CourseAchievments;
import org.grameenfoundation.cch.utils.TextProgressBar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CourseAchievementActivity extends Activity {

	private ListView Listview;
	private Context mContext;
	private DbHelper db;
	private ArrayList<CourseAchievments> courses;
	private long modid;
	private ListAdapter adapter;
	private TextView textView_label;
	private String course_name;
	private TextProgressBar progress;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_course_achievements);
	    Listview = (ListView) findViewById(R.id.listView1);
	    mContext=CourseAchievementActivity.this;
	    db=new DbHelper(CourseAchievementActivity.this);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          modid= extras.getLong("modid");
          course_name=extras.getString("course_name");
        }
        progress=(TextProgressBar) findViewById(R.id.progressBar1);
        progress.setProgress((int)db.getCourseProgress((int)modid));
	    courses = db.getQuizResultsForAchievements((int)modid);
	    System.out.println(courses);
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Achievement Center");
	    getActionBar().setSubtitle("Achievement Details");
	    textView_label=(TextView) findViewById(R.id.textView_label);
	    textView_label.setText(course_name);
	    adapter=new ListAdapter(mContext,courses);
	    Listview.setAdapter(adapter);
	}

	
	class ListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<CourseAchievments> course_achievements;
		public LayoutInflater minflater;
		 int[] images;
	
		
		public ListAdapter(Context c,ArrayList<CourseAchievments> Course_achievements){
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
		
	}
}
