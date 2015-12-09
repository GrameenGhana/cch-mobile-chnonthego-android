package org.digitalcampus.oppia.task;

import java.io.File;
import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.StartUpActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.exception.InvalidXMLException;
import org.digitalcampus.oppia.listener.UpgradeListener;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.utils.CourseScheduleXMLReader;
import org.digitalcampus.oppia.utils.CourseTrackerXMLReader;
import org.digitalcampus.oppia.utils.CourseXMLReader;
import org.digitalcampus.oppia.utils.FileUtils;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.tasks.CourseDetailsTask;
import org.grameenfoundation.cch.utils.CCHTimeUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class UpgradeManagerTask extends AsyncTask<Payload, String, Payload> {
	
	public static final String TAG = UpgradeManagerTask.class.getSimpleName();
	private Context ctx;
	private SharedPreferences prefs;
	private UpgradeListener mUpgradeListener;
	private DbHelper db;
	private String versionName;
	private CCHTimeUtil timeUtil;
	
	public UpgradeManagerTask(Context ctx){
		this.ctx = ctx;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		db=new DbHelper(ctx);
		timeUtil=new CCHTimeUtil();
	}
	
	@Override
	protected Payload doInBackground(Payload... params) {
		Payload payload = params[0];
		
		payload.setResult(false);
		if(!prefs.getBoolean("upgradeV17",false)){
			upgradeV17();
			Editor editor = prefs.edit();
			editor.putBoolean("upgradeV17", true);
			editor.commit();
			publishProgress("Upgraded to v17");
			payload.setResult(true);
		}
		
		if(!prefs.getBoolean("upgradeV20",false)){
			upgradeV20();
			Editor editor = prefs.edit();
			editor.putBoolean("upgradeV20", true);
			editor.commit();
			publishProgress("Upgraded to v20");
			payload.setResult(true);
		}
		
		if(!prefs.getBoolean("upgradeV29",false)){
			Editor editor = prefs.edit();
			editor.putBoolean("upgradeV29", true);
			editor.commit();
			publishProgress("Upgraded to v29");
			payload.setResult(true);
		}
			SWReset();
			ReloadingTargets();
			 SurveyInitialize();
			 ReinstallingCourses();
		return payload;
	}
	
	/* rescans all the installed courses and reinstalls them, to ensure that 
	 * the new titles etc are picked up
	 */
	protected void upgradeV17(){
		File dir = new File(MobileLearning.COURSES_PATH);
		String[] children = dir.list();
		if (children != null) {
			for (int i = 0; i < children.length; i++) {
				Log.d(TAG,"checking "+ children[i]);
				publishProgress("checking: " + children[i]);
				String courseXMLPath = "";
				String courseScheduleXMLPath = "";
				String courseTrackerXMLPath = "";
				// check that it's unzipped etc correctly
				try {
					courseXMLPath = dir + "/" + children[i] + "/" + MobileLearning.COURSE_XML;
					courseScheduleXMLPath = dir + "/" + children[i] + "/" + MobileLearning.COURSE_SCHEDULE_XML;
					courseTrackerXMLPath = dir + "/" + children[i] + "/" + MobileLearning.COURSE_TRACKER_XML;
				} catch (ArrayIndexOutOfBoundsException aioobe){
					FileUtils.cleanUp(dir, MobileLearning.DOWNLOAD_PATH + children[i]);
					break;
				}
				
				// check a module.xml file exists and is a readable XML file
				CourseXMLReader cxr;
				CourseScheduleXMLReader csxr;
				CourseTrackerXMLReader ctxr;
				try {
					cxr = new CourseXMLReader(courseXMLPath);
					csxr = new CourseScheduleXMLReader(courseScheduleXMLPath);
					ctxr = new CourseTrackerXMLReader(courseTrackerXMLPath);
				} catch (InvalidXMLException e) {
					e.printStackTrace();
					break;
				}
				
				//HashMap<String, String> hm = mxr.getMeta();
				Course c = new Course();
				c.setVersionId(cxr.getVersionId());
				c.setTitles(cxr.getTitles());
				c.setLocation(MobileLearning.COURSES_PATH + children[i]);
				c.setShortname(children[i]);
				c.setImageFile(MobileLearning.COURSES_PATH + children[i] + "/" + cxr.getCourseImage());
				c.setLangs(cxr.getLangs());
				
				
				DbHelper db = new DbHelper(ctx);
				long modId = db.refreshCourse(c);
			
				if (modId != -1) {
					db.insertActivities(cxr.getActivities(modId));
					db.insertTrackers(ctxr.getTrackers(),modId);
				} 
				
				// add schedule
				// put this here so even if the course content isn't updated the schedule will be
				db.insertSchedule(csxr.getSchedule());
				db.updateScheduleVersion(modId, csxr.getScheduleVersion());
				
				db.close();
			}
		}
	}
	
	/* switch to using demo.oppia-mobile.org
	 */
	protected void upgradeV20(){
		Editor editor = prefs.edit();
		editor.putString(ctx.getString(R.string.prefs_server), ctx.getString(R.string.prefServerDefault));
		editor.commit();
	}
	protected void SWReset(){
		String PACKAGE_NAME=ctx.getPackageName();
		try{
		PackageInfo info = ctx.getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
		SQLiteDatabase db2=db.getWritableDatabase();
	      int currentVersion = info.versionCode;
	      this.versionName = info.versionName;
	      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
	      int lastVersion = prefs.getInt("version_code", 0);
	      if (currentVersion > lastVersion) {
	    	  publishProgress("Resetting Staying well");
	       // prefs.edit().putInt("version_code", currentVersion).commit();
	        db.alterStayingWellTables();
	        db.runSWReset();
	   }
		}catch(NameNotFoundException e){
			e.printStackTrace();
		}
		
	}
	
	protected void ReinstallingCourses(){
		String PACKAGE_NAME=ctx.getPackageName();
	
		try{
			ArrayList<Course> installedCourses=new ArrayList<Course>();
			installedCourses=db.getCourses();
		PackageInfo info = ctx.getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
	      int currentVersion = info.versionCode;
	      this.versionName = info.versionName;
	      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
	      int lastVersion = prefs.getInt("version_code", 0);
	      System.out.println("CurrentVersion"+String.valueOf(currentVersion));
	      System.out.println("LastVersion "+String.valueOf(lastVersion));
	      if (currentVersion > lastVersion) {
	    	  publishProgress("Reinstalling Courses");
	        prefs.edit().putInt("version_code", currentVersion).commit();
	        if(installedCourses.size()==0){
	    	File dir = new File(MobileLearning.COURSES_PATH);
			String[] children = dir.list();
			if (children != null) {
				for (int i = 0; i < children.length; i++) {
					Log.d(TAG,"checking "+ children[i]);
					publishProgress("checking: " + children[i]);
					String courseXMLPath = "";
					String courseScheduleXMLPath = "";
					String courseTrackerXMLPath = "";
					// check that it's unzipped etc correctly
					try {
						courseXMLPath = dir + "/" + children[i] + "/" + MobileLearning.COURSE_XML;
						courseScheduleXMLPath = dir + "/" + children[i] + "/" + MobileLearning.COURSE_SCHEDULE_XML;
						courseTrackerXMLPath = dir + "/" + children[i] + "/" + MobileLearning.COURSE_TRACKER_XML;
					} catch (ArrayIndexOutOfBoundsException aioobe){
						FileUtils.cleanUp(dir, MobileLearning.DOWNLOAD_PATH + children[i]);
						break;
					}
					
					// check a module.xml file exists and is a readable XML file
					CourseXMLReader cxr;
					CourseScheduleXMLReader csxr;
					CourseTrackerXMLReader ctxr;
					try {
						cxr = new CourseXMLReader(courseXMLPath);
						csxr = new CourseScheduleXMLReader(courseScheduleXMLPath);
						ctxr = new CourseTrackerXMLReader(courseTrackerXMLPath);
					} catch (InvalidXMLException e) {
						e.printStackTrace();
						break;
					}
					
					//HashMap<String, String> hm = mxr.getMeta();
					Course c = new Course();
					c.setVersionId(cxr.getVersionId());
					c.setTitles(cxr.getTitles());
					c.setLocation(MobileLearning.COURSES_PATH + children[i]);
					c.setShortname(children[i]);
					c.setImageFile(MobileLearning.COURSES_PATH + children[i] + "/" + cxr.getCourseImage());
					c.setLangs(cxr.getLangs());
					
					
					DbHelper db = new DbHelper(ctx);
					long modId = db.addOrUpdateCourse(c);
				
					if (modId != -1) {
						db.insertActivities(cxr.getActivities(modId));
						db.insertTrackers(ctxr.getTrackers(),modId);
					} 
					
					// add schedule
					// put this here so even if the course content isn't updated the schedule will be
					db.insertSchedule(csxr.getSchedule());
					db.updateScheduleVersion(modId, csxr.getScheduleVersion());
					
					db.close();
				}
				CourseDetailsTask courseDetails = new CourseDetailsTask(ctx);
				courseDetails.execute(new String[] { ctx.getResources().getString(R.string.serverDefaultAddress)+"/"+MobileLearning.CCH_COURSE_DETAILS_PATH});
			}
	        }
	   }
		}catch(NameNotFoundException e){
			e.printStackTrace();
		}
		
	}
	protected void SurveyInitialize(){
		try{
			 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		      int fix_number = prefs.getInt("survey_init_new", 0);
		      if (fix_number==0) {
		    	  publishProgress("Initializing survey");
		        prefs.edit().putInt("survey_init_new", 1).commit();
		        Time now=new Time();
		        now.setToNow();
		    	  DateTime firstDate=new DateTime(2015,07,01,now.hour,now.minute);
		    	  DateTime secondDate=new DateTime(2015,11,1,now.hour,now.minute);
		    	  DateTime thirdDate=new DateTime(2016,03,1,now.hour,now.minute);
		    	  DateTime firstDateReminder=new DateTime(2015,07,31,now.hour,now.minute);
		    	  DateTime secondDateReminder=new DateTime(2015,11,30,now.hour,now.minute);
		    	  DateTime thirdDateReminder=new DateTime(2016,03,31,now.hour,now.minute);
		    	  db.deleteTables(db.SURVEY_TABLE);
		        db.insertSurvey("", "", "",String.valueOf(firstDate.getMillis()),String.valueOf(firstDateReminder.getMillis()),"","","","");
		        db.insertSurvey("", "", "", String.valueOf(secondDate.getMillis()),String.valueOf(secondDateReminder.getMillis()),"", "","","");
		        db.insertSurvey("", "", "", String.valueOf(thirdDate.getMillis()),String.valueOf(thirdDateReminder.getMillis()),"","","","");
		      }
		      
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	protected void ReloadingTargets(){
		ArrayList<EventTargets> event_targets=new ArrayList<EventTargets>();
		ArrayList<EventTargets> coverage_targets=new ArrayList<EventTargets>();
		ArrayList<EventTargets> other_targets=new ArrayList<EventTargets>();
		
		try{
			if(db.doesTableExists(db.OTHER_TABLE)){
				other_targets=db.getAllOtherTargets();
			}
		}catch(Exception e){
			//Toast.makeText(ctx, "Could not process data", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		try{
	      SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
	      int fix_number = prefs.getInt("target_fix", 0);
	      if (fix_number==0) {
	    	  publishProgress("Resetting Staying well");
	        prefs.edit().putInt("target_fix", 1).commit();
	       for(int i=0;i<other_targets.size();i++){
	    	   db.insertTarget(Integer.parseInt(other_targets.get(i).getEventTargetId()),
	    			   			MobileLearning.CCH_TARGET_TYPE_OTHER, 
	    			   			other_targets.get(i).getEventTargetName(),
	    			   			" ", 
	    			   			other_targets.get(i).getEventTargetPersonalCategory(),
	    			   			Integer.parseInt(other_targets.get(i).getEventTargetNumber()),
	    			   			Integer.parseInt(other_targets.get(i).getEventTargetNumberAchieved()),
	    			   			Integer.parseInt(other_targets.get(i).getEventTargetNumberRemaining()), 
	    			   			other_targets.get(i).getEventTargetStartDate(),
	    			   			other_targets.get(i).getEventTargetEndDate(),
	    			   			other_targets.get(i).getEventTargetPeriod(),
	    			   			other_targets.get(i).getEventTargetStatus(),
	    			   			event_targets.get(i).getEventTargetLastUpdated());
	       }
	       db.deleteTables(db.OTHER_TABLE);
	       System.out.println("Fixing TargetSetting");
	   }
		}catch(Exception e){
			//Toast.makeText(ctx, "Oops something went wrong", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		
	}
	@Override
	protected void onProgressUpdate(String... obj) {
		synchronized (this) {
            if (mUpgradeListener != null) {
                // update progress and total
            	mUpgradeListener.upgradeProgressUpdate(obj[0]);
            }
        }
	}

	@Override
	protected void onPostExecute(Payload p) {
		synchronized (this) {
            if (mUpgradeListener != null) {
            	mUpgradeListener.upgradeComplete(p);
            }
        }
	}

	public void setUpgradeListener(UpgradeListener srl) {
        synchronized (this) {
        	mUpgradeListener = srl;
        }
    }

}
