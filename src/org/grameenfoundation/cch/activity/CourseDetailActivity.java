package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.exception.InvalidXMLException;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.model.CourseChild;
import org.digitalcampus.oppia.model.Lang;
import org.digitalcampus.oppia.model.Scores;
import org.grameenfoundation.adapters.CoursesAchievementAdapter;
import org.grameenfoundation.adapters.TargetsAchievementAdapter;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.model.CourseAchievments;
import org.grameenfoundation.cch.model.CourseGroups;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.utils.TextProgressBar;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class CourseDetailActivity extends Activity {

	private ListView listView;
	private ListView expandableListview;
	private Context mContext;
	private DbHelper db;
	private TextView textView_number;
	private TextView textView_label;
	private ListAdapter adapter;
	private ArrayList<Course> courses;
	private ArrayList<Scores> course_scores;
	private SharedPreferences prefs;
	private LinearLayout linearLayout;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_course_achievements);
	    expandableListview = (ListView) findViewById(R.id.listView1);
	    mContext=CourseDetailActivity.this;
	    db=new DbHelper(CourseDetailActivity.this);
	    getActionBar().setDisplayShowHomeEnabled(false);
	    getActionBar().setTitle("Achievement Center");
	    getActionBar().setSubtitle("Achievement Details");
	    textView_label=(TextView) findViewById(R.id.textView_label);
	    textView_label.setText("Courses");
	    textView_number=(TextView) findViewById(R.id.textView_number);
	    courses = db.getCourses();
	    textView_number.setText(" ("+courses.size()+")");
	    ArrayList<CourseAchievments> results = new ArrayList<CourseAchievments>();
	    linearLayout=(LinearLayout) findViewById(R.id.Linearlayout_progress);
	    linearLayout.setVisibility(View.GONE);
	  //  results=db.getAllQuizResults();
	   
	    adapter=new ListAdapter(mContext,courses);
	    expandableListview.setAdapter(adapter);
	    expandableListview.setOnItemClickListener(new OnItemClickListener(){

			private ArrayList<CourseAchievments> course_achievements;

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			Intent intent=new Intent(mContext,CourseAchievementActivity.class);
			String course_name=adapter.getItem(position);
			intent.putExtra("modid", id);
			intent.putExtra("course_name", course_name);
			 course_achievements = db.getQuizResultsForAchievements((int)id);
			 if(course_achievements.size()>0){
			startActivity(intent);
			 }else if(course_achievements.size()==0){
				 Crouton.makeText(CourseDetailActivity.this, "You have no details for "+course_name+"!",
						 Style.INFO).show();
			 }
			}
	    	
	    });
	}
	class ListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<Course> courses;
		public LayoutInflater minflater;
		 int[] images;
	
		
		public ListAdapter(Context c,ArrayList<Course> Courses){
			this.mContext=c;
			courses = new ArrayList<Course>();
			courses.addAll(Courses);
			 minflater = LayoutInflater.from(mContext);
			 prefs = PreferenceManager.getDefaultSharedPreferences(c);
		}

		@Override
		public int getCount() {
			return courses.size();
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return courses.get(position).getTitle(prefs.getString(mContext.getString(R.string.prefs_language), Locale
						.getDefault().getLanguage()));
		}

		@Override
		public long getItemId(int position) {
			return courses.get(position).getModId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.course_list_row,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.module_title);
			 ImageView image=(ImageView) convertView.findViewById(R.id.course_image);
			 text.setText(courses.get(position).getTitle(prefs.getString(mContext.getString(R.string.prefs_language), Locale
						.getDefault().getLanguage())));
			 image.setImageResource(R.drawable.ic_courses);
			 ProgressBar progress=(ProgressBar) convertView.findViewById(R.id.course_progress_bar);
			 progress.setVisibility(View.GONE);
			    return convertView;
		}
	}
}
