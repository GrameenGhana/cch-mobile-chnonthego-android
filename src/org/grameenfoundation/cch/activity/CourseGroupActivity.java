package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.AboutActivity;
import org.digitalcampus.oppia.activity.AppActivity;
import org.digitalcampus.oppia.activity.CourseIndexActivity;
import org.digitalcampus.oppia.activity.HelpActivity;
import org.digitalcampus.oppia.activity.OppiaMobileActivity;
import org.digitalcampus.oppia.activity.PrefsActivity;
import org.digitalcampus.oppia.activity.StartUpActivity;
import org.digitalcampus.oppia.activity.TagSelectActivity;
import org.digitalcampus.oppia.adapter.CourseListAdapter;
import org.digitalcampus.oppia.adapter.NewCourseListAdapter;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.model.Lang;
import org.digitalcampus.oppia.utils.UIUtils;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CourseGroupActivity extends AppActivity {

	public static final String TAG = CourseGroupActivity.class.getSimpleName();
	private SharedPreferences prefs;
	private ArrayList<Course> courses;
	private Course tempCourse;
	private DbHelper db;
	private int[] imageIds;
	private ArrayList<Course> coursesGroups;
	private Button download;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
		getActionBar().setDisplayShowHomeEnabled(true);
	    getActionBar().setTitle("Learning Center");
	    getActionBar().setSubtitle("Learning Modules");
	    download=(Button) this.findViewById(R.id.button_download);
	    try{
	    	db = new DbHelper(this);
	    	coursesGroups = db.getCourseGroupNames();
	    	courses=db.getCourses();
	    	db.close();
	    
	    populateImages();
	    LinearLayout llLoading = (LinearLayout) this.findViewById(R.id.loading_courses);
		llLoading.setVisibility(View.GONE);
		LinearLayout llNone = (LinearLayout) this.findViewById(R.id.no_courses);
		if (coursesGroups.size() > 0) {
			llNone.setVisibility(View.GONE);
		} else {
			llNone.setVisibility(View.VISIBLE);
			download.setVisibility(View.GONE);
			Button manageBtn = (Button) this.findViewById(R.id.manage_courses_btn);
			manageBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					startActivity(new Intent(CourseGroupActivity.this, TagSelectActivity.class));
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
			});
		}
		
		download.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(CourseGroupActivity.this, TagSelectActivity.class));
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
			
		});
	    if(coursesGroups.size()>0){
	    NewCourseListAdapter mla = new NewCourseListAdapter(this, coursesGroups,imageIds);
		ListView listView = (ListView) findViewById(R.id.course_list);
		listView.setAdapter(mla);
		registerForContextMenu(listView);

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try{
				Course m = (Course) view.getTag();
				Intent i = new Intent(CourseGroupActivity.this, OppiaMobileActivity.class);
				Bundle tb = new Bundle();
				tb.putSerializable(Course.TAG, m);
				i.putExtras(tb);
				i.putExtra("courseGroup", coursesGroups.get(position).getCourseGroup());
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}catch(Exception e){
					e.printStackTrace();
					Crouton.makeText(CourseGroupActivity.this, "There is no group for this course!Please reload the application", Style.ALERT).show();
				}
			}
		});
	    }else{
	    	ArrayList<String> c=new ArrayList<String>();
			for(int i=0;i<courses.size();i++){
				c.add(courses.get(i).getTitle(prefs.getString(this.getString(R.string.prefs_language), Locale.getDefault().getLanguage())));
				Collections.sort(c);
			}
	    	 CourseListAdapter mla = new CourseListAdapter(this, courses,c);
	 		ListView listView = (ListView) findViewById(R.id.course_list);
	 		listView.setAdapter(mla);
	 		registerForContextMenu(listView);

	 		listView.setOnItemClickListener(new OnItemClickListener() {

	 			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	 				Course m = (Course) view.getTag();
	 				Intent i = new Intent(CourseGroupActivity.this, CourseIndexActivity.class);
	 				Bundle tb = new Bundle();
	 				tb.putSerializable(Course.TAG, m);
	 				i.putExtras(tb);
	 				//i.putExtra("courseGroup", coursesGroups.get(position).getCourseGroup());
	 				startActivity(i);
	 				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
	 			}
	 		});
	    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	public void populateImages(){
		 if(coursesGroups.size()==2){
			 imageIds=new int[]{R.drawable.ic_family_planning,R.drawable.ic_mnch};
		 }else {
		 imageIds=new int[coursesGroups.size()];  
		 
		 for(int i=0;i<coursesGroups.size();i++){
			 imageIds[i]=R.drawable.ic_family_planning;
		 }
		 }
	   }
	@Override
	public void onRestart() {
		super.onStart();		
		 if(coursesGroups.size()>0){
		    	populateImages();
		    NewCourseListAdapter mla = new NewCourseListAdapter(this, coursesGroups,imageIds);
			ListView listView = (ListView) findViewById(R.id.course_list);
			listView.setAdapter(mla);
			registerForContextMenu(listView);

			listView.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Course m = (Course) view.getTag();
					Intent i = new Intent(CourseGroupActivity.this, OppiaMobileActivity.class);
					Bundle tb = new Bundle();
					tb.putSerializable(Course.TAG, m);
					i.putExtras(tb);
					i.putExtra("courseGroup", coursesGroups.get(position).getCourseGroup());
					startActivity(i);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
			});
		    }else{
		    	ArrayList<String> c=new ArrayList<String>();
				for(int i=0;i<courses.size();i++){
					c.add(courses.get(i).getTitle(prefs.getString(this.getString(R.string.prefs_language), Locale.getDefault().getLanguage())));
					Collections.sort(c);
				}
		    	 CourseListAdapter mla = new CourseListAdapter(this, courses,c);
		 		ListView listView = (ListView) findViewById(R.id.course_list);
		 		listView.setAdapter(mla);
		 		mla.notifyDataSetChanged();
		 		registerForContextMenu(listView);

		 		listView.setOnItemClickListener(new OnItemClickListener() {

		 			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		 				Course m = (Course) view.getTag();
		 				Intent i = new Intent(CourseGroupActivity.this, CourseIndexActivity.class);
		 				Bundle tb = new Bundle();
		 				tb.putSerializable(Course.TAG, m);
		 				i.putExtras(tb);
		 				//i.putExtra("courseGroup", coursesGroups.get(position).getCourseGroup());
		 				startActivity(i);
		 				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
		 			}
		 		});
		    }
				
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		UIUtils.showUserData(menu,this);
	    return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_about) {
			startActivity(new Intent(this, AboutActivity.class));
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
		} else if (itemId == R.id.menu_download) {
			startActivity(new Intent(this, TagSelectActivity.class));
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
		} else if (itemId == R.id.menu_settings) {
			Intent i = new Intent(this, PrefsActivity.class);
			Bundle tb = new Bundle();
			ArrayList<Lang> langs = new ArrayList<Lang>();
			for(Course m: courses){
				langs.addAll(m.getLangs());
			}
			tb.putSerializable("langs", langs);
			i.putExtras(tb);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
		} else if (itemId == R.id.menu_help) {
			startActivity(new Intent(this, HelpActivity.class));
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
		} else if (itemId == R.id.menu_logout) {
			logout();
			return true;
		}
		return true;
	}
	private void logout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle(R.string.logout);
		builder.setMessage(R.string.logout_confirm);
		builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// wipe activity data
				DbHelper db = new DbHelper(CourseGroupActivity.this);
				db.onLogout();
				db.close();

				// restart the app
				CourseGroupActivity.this.startActivity(new Intent(CourseGroupActivity.this, StartUpActivity.class));
				CourseGroupActivity.this.finish();
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);

			}
		});
		builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return; // do nothing
			}
		});
		builder.show();
	}
	

}
