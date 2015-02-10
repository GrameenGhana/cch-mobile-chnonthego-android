package org.grameenfoundation.cch.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.adapters.CourseAchievementsAdapter;
import org.grameenfoundation.cch.model.CourseAchievments;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OverallCourseAchievementsActivity extends Activity {

	private ArrayList<CourseAchievments> courseList;
	private ListView listView;
	private SharedPreferences prefs;
	private String name;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_overall_course_achievements);
	    getActionBar().setTitle("Achievement Center");
	    getActionBar().setSubtitle("Overall Course Achievements");
	    listView=(ListView) findViewById(R.id.listView1);
	    courseList=new ArrayList<CourseAchievments>();
	    prefs = PreferenceManager.getDefaultSharedPreferences(OverallCourseAchievementsActivity.this);
	    name=prefs.getString("first_name", "name");
	    grabURL("http://188.226.189.149/chn/courseActions.php?"+ MobileLearning.CCH_COURSE_ACHIEVEMENT_PATH+"7089");
	}
	 public void grabURL(String url) {
		  new CourseAchievementsTask().execute(url);
		 }

	class CourseAchievementsTask extends AsyncTask<String, Void, String> {
		
		 private ProgressDialog dialog = 
				   new ProgressDialog(OverallCourseAchievementsActivity.this);
		private boolean loadingMore;
		private CourseAchievementsAdapter adapter;

		 
		 protected void onPreExecute() {
			   dialog.setMessage("Getting your data... Please wait...");
			   dialog.setCancelable(false);
			   dialog.show();
			  }
		 protected String doInBackground(String ... urls) {
	         String response = "";
	         loadingMore = true;
	         for (String url : urls) {
	             DefaultHttpClient client = new DefaultHttpClient();
	             HttpGet httpGet = new HttpGet(url);
	             try {
	                 HttpResponse execute = client.execute(httpGet);
	                 InputStream content = execute.getEntity().getContent();

	                 BufferedReader buffer = new BufferedReader(
	                         new InputStreamReader(content));
	                 String s = "";
	                 while ((s = buffer.readLine()) != null) {
	                     response += s;
	                 }

	             } catch (Exception e) {
	                 e.printStackTrace();
	             }
	         }
	         return response;
	     }

	     @Override
	     protected void onPostExecute(String result) {
	    	 dialog.dismiss();
	          try {
				JSONObject jObj = new JSONObject(result);
				JSONArray date = jObj.getJSONArray("quizzes");
				
				for (int i=0;i<date.length();i++){
					CourseAchievments courses= new CourseAchievments();
					String myresult=date.getString(i);
					JSONObject newobj=new JSONObject(myresult);
					courses.setCourseName(newobj.getString("course"));
					courses.setCourseSection(newobj.getString("section_title"));
					courses.setType(newobj.getString("quiz_type")+"-Test Assessment");
					courses.setScore(newobj.getString("score") + "/" + newobj.getString("max_possible_score"));
					courses.setDateTaken(newobj.getString("datetime"));
					Double percentage=((double)Double.valueOf(newobj.getString("score"))/(double)Double.valueOf(newobj.getString("max_possible_score")))*100;
					String percentage_value=String.format("%.0f", percentage);
					courses.setPercentage(percentage_value);
					courseList.add(courses);
					adapter=new CourseAchievementsAdapter(OverallCourseAchievementsActivity.this,courseList);
				    listView.setAdapter(adapter);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     }
	     
	     class CourseAchievementsAdapter extends BaseAdapter{
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
	    				   convertView = minflater.inflate(R.layout.overall_course_details_listview_single,parent, false);
	    				  }
	    				   TextView topic=(TextView) convertView.findViewById(R.id.textView_topic);
	    				   TextView testType=(TextView) convertView.findViewById(R.id.textView_testType);
	    				   TextView score=(TextView) convertView.findViewById(R.id.textView_score);
	    				   TextView course=(TextView) convertView.findViewById(R.id.textView_courseTitle);
	    				   TextView dateTaken=(TextView) convertView.findViewById(R.id.textView_dateTaken);
	    				   TextView percentage=(TextView) convertView.findViewById(R.id.textView_percentage);
	    				   topic.setText(course_achievements.get(position).getCourseSection());
	    				   testType.setText(course_achievements.get(position).getType());
	    				   score.setText(course_achievements.get(position).getScore());
	    				   percentage.setText(course_achievements.get(position).getPercentage()+"%");
	    				   dateTaken.setText(course_achievements.get(position).getDateTaken());
	    				   course.setText(course_achievements.get(position).getCourseName());
	    				   Typeface custom_font = Typeface.createFromAsset(mContext.getAssets(),
	    			       	      "fonts/Roboto-Thin.ttf");
	    				   topic.setTypeface(custom_font);
	    				   testType.setTypeface(custom_font);
	    				   score.setTypeface(custom_font);
	    				   percentage.setTypeface(custom_font);
	    				   course.setTypeface(custom_font);
	    				   dateTaken.setTypeface(custom_font);
	    				   
	    			    return convertView;
	    		}

	    		public void add(String value) {
	    			// TODO Auto-generated method stub
	    			
	    		}
	     }
	}
}
