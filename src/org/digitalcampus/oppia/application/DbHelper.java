/* 
 * This file is part of OppiaMobile - http://oppia-mobile.org/
 * 
 * OppiaMobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * OppiaMobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OppiaMobile. If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalcampus.oppia.application;

import java.util.ArrayList;
import java.util.HashMap;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.model.Activity;
import org.digitalcampus.oppia.model.ActivitySchedule;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.model.TrackerLog;
import org.digitalcampus.oppia.model.User;
import org.digitalcampus.oppia.task.Payload;
import org.grameenfoundation.cch.model.CCHTrackerLog;
import org.grameenfoundation.database.CHNDataClass.CHNDatabase;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;

public class DbHelper extends SQLiteOpenHelper {

	static final String TAG = DbHelper.class.getSimpleName();
	static final String DB_NAME = "mobilelearning.db";
	static final int DB_VERSION = 15;

	private SQLiteDatabase db=this.getWritableDatabase();
	private SharedPreferences prefs;
	private Context ctx;
	private SQLiteDatabase read=this.getReadableDatabase();

	
	private static final String COURSE_TABLE = "Module";
	private static final String COURSE_C_ID = BaseColumns._ID;
	private static final String COURSE_C_VERSIONID = "versionid";
	private static final String COURSE_C_TITLE = "title";
	private static final String COURSE_C_SHORTNAME = "shortname";
	private static final String COURSE_C_LOCATION = "location";
	private static final String COURSE_C_SCHEDULE = "schedule";
	private static final String COURSE_C_IMAGE = "imagelink";
	private static final String COURSE_C_LANGS = "langs";
	
	private static final String ACTIVITY_TABLE = "Activity";
	private static final String ACTIVITY_C_ID = BaseColumns._ID;
	private static final String ACTIVITY_C_COURSEID = "modid"; // reference to COURSE_C_ID
	private static final String ACTIVITY_C_SECTIONID = "sectionid";
	private static final String ACTIVITY_C_ACTID = "activityid";
	private static final String ACTIVITY_C_ACTTYPE = "activitytype";
	private static final String ACTIVITY_C_ACTIVITYDIGEST = "digest";
	private static final String ACTIVITY_C_STARTDATE = "startdate";
	private static final String ACTIVITY_C_ENDDATE = "enddate";
	private static final String ACTIVITY_C_TITLE = "title";

	private static final String TRACKER_LOG_TABLE = "TrackerLog";
	private static final String TRACKER_LOG_C_ID = BaseColumns._ID;
	private static final String TRACKER_LOG_C_COURSEID = "modid"; // reference to COURSE_C_ID
	private static final String TRACKER_LOG_C_DATETIME = "logdatetime";
	private static final String TRACKER_LOG_C_ACTIVITYDIGEST = "digest";
	private static final String TRACKER_LOG_C_DATA = "logdata";
	private static final String TRACKER_LOG_C_SUBMITTED = "logsubmitted";
	private static final String TRACKER_LOG_C_INPROGRESS = "loginprogress";
	private static final String TRACKER_LOG_C_COMPLETED = "completed";
	
	private static final String QUIZRESULTS_TABLE = "results";
	private static final String QUIZRESULTS_C_ID = BaseColumns._ID;
	private static final String QUIZRESULTS_C_DATETIME = "resultdatetime";
	private static final String QUIZRESULTS_C_DATA = "content";
	private static final String QUIZRESULTS_C_SENT = "submitted";
	private static final String QUIZRESULTS_C_COURSEID = "moduleid";
	
	// CCH: Tracker table
	private static final String CCH_TRACKER_TABLE = "CCHTrackerLog";
	private static final String CCH_TRACKER_ID = BaseColumns._ID;
	private static final String CCH_TRACKER_USERID = "userid"; // reference to current user id
	private static final String CCH_TRACKER_MODULE = "module"; 
	private static final String CCH_TRACKER_START_DATETIME = "starttime";
	private static final String CCH_TRACKER_END_DATETIME = "endtime";
	private static final String CCH_TRACKER_DATA = "data";
	private static final String CCH_TRACKER_SUBMITTED = "submitted";
	private static final String CCH_TRACKER_INPROGRESS = "inprogress";
	
	// CCH: Login Table 
    private static final String CCH_USER_TABLE = "login";
	private static final String CCH_USER_ID = BaseColumns._ID;
	private static final String CCH_STAFF_ID = "staff_id";
	private static final String CCH_USER_PASSWORD = "password";
	private static final String CCH_USER_APIKEY = "apikey";
	private static final String CCH_USER_FIRSTNAME = "first_name";
	private static final String CCH_USER_LASTNAME = "last_name";
	private static final String CCH_USER_POINTS = "points";
	private static final String CCH_USER_BADGES = "badges";
	private static final String CCH_USER_SCORING = "scoring_enabled";
	
	// CCH: Staying Well table
    private static final String CCH_SW_TABLE = "stayingwell";
	private static final String CCH_SW_ID = BaseColumns._ID;
	private static final String CCH_SW_STAFF_ID = "staff_id";
	public static final String CCH_SW_LEGAL_STATUS = "sw_legal";
	public static final String CCH_SW_PROFILE_STATUS = "sw_profile";
	public static final String CCH_SW_PROFILE_RESPONSES = "sw_profileresponse";
	public static final String CCH_SW_MONTH_PLAN = "sw_monthplan";
	public static final String CCH_SW_MONTH_PLAN_LASTUPDATE = "sw_planupdatetime";
	
	// CCH: Staying Well Routines tables
    private static final String CCH_SW_ROUTINE_TABLE = "stayingwell_routines";
	private static final String CCH_SW_ROUTINE_ID = BaseColumns._ID;
	private static final String CCH_SW_ROUTINE_TOD = "timeofday";
	private static final String CCH_SW_ROUTINE_PROFILE = "profile";
	private static final String CCH_SW_ROUTINE_PLAN = "plan";
	private static final String CCH_SW_ROUTINE_ACTION = "action";
	private static final String CCH_SW_ROUTINE_ORDER  = "doorder";
    private static final String CCH_SW_ROUTINE_TODO_TABLE = "stayingwell_routinetodos";
	private static final String CCH_SW_ROUTINE_TODO_ID = BaseColumns._ID;
	private static final String CCH_SW_ROUTINE_TODO_STAFF_ID = "staff_id";
	private static final String CCH_SW_ROUTINE_TODO_PROFILE  = "profile";
	private static final String CCH_SW_ROUTINE_TODO_PLAN  = "plan";
	private static final String CCH_SW_ROUTINE_TODO_YEAR = "year";
	private static final String CCH_SW_ROUTINE_TODO_MONTH = "month";
	private static final String CCH_SW_ROUTINE_TODO_DAY = "day";
	private static final String CCH_SW_ROUTINE_TODO_TOD = "timeofday";
	private static final String CCH_SW_ROUTINE_TODO_ACTION = "action";
	private static final String CCH_SW_ROUTINE_TODO_ORDER  = "doorder";
	public static final String CCH_SW_ROUTINE_TODO_TIMEDONE  = "timedone";
	public static final String CCH_SW_ROUTINE_TODO_LOGGED  = "logged";

	
	//CCH: Events Table
		public static final String EVENTS_SET_TABLE="events_set";
		public static final String COL_EVENT_SET_NAME="event_name";
		public static final String COL_EVENT_PERIOD="event_period";
		public static final String COL_EVENT_NUMBER="event_number";
		public static final String COL_EVENT_MONTH="month";
		public static final String COL_EVENT_DUE_DATE="due_date";
		public static final String COL_SYNC_STATUS ="sync_status";
		
		//CCH: Coverage Table
		public static final String COVERAGE_SET_TABLE="coverage_set";
		public static final String COL_COVERAGE_SET_CATEGORY_NAME="category_name";
		public static final String COL_COVERAGE_SET_CATEGORY_DETAIL="category_detail";
		public static final String COL_COVERAGE_SET_PERIOD="coverage_period";
		public static final String COL_COVERAGE_NUMBER="category_number";
		public static final String COL_COVERAGE_DUE_DATE="due_date";
		public static final String COL_COVERAGE_MONTH="month";
		
		//CCH: Learning Table
		public static final String LEARNING_TABLE="learning";
		public static final String COL_LEARNING_CATEGORY="learning_category";
		public static final String COL_LEARNING_DESCRIPTION="learning_description";
		public static final String COL_LEARNING_TOPIC="learning_topic";
		public static final String COL_LEARNING_DUE_DATE="due_date";
		public static final String COL_LEARNING_MONTH="month";
		
		//CCH: Other Table
		public static final String OTHER_TABLE="other";
		public static final String COL_OTHER_CATEGORY="other_category";
		public static final String COL_OTHER_NUMBER="other_number";
		public static final String COL_OTHER_PERIOD="other_period";
		public static final String COL_OTHER_DUE_DATE="due_date";
		public static final String COL_OTHER_MONTH="month";
		
		private static final String TEXT_TYPE = " TEXT";
		private static final String COMMA_SEP = ",";
		
		//CCH: Update Justification Table
		public static final String JUSTIFICATION_TABLE="update_justification";
		public static final String COL_TYPE="type";
		public static final String COL_TYPE_DETAIL="type_detail";
		public static final String COL_NUMBER="number";
		public static final String COL_NUMBER_ACHIEVED="number_achieved";
		public static final String COL_JUSTIFICATION="justification";
		public static final String COL_TARGET_ID="target_id";
		public static final String COL_COMMENT="comment";
		
		//CCH: Update Justification Table
		public static final String CALENDAR_EVENTS_TABLE="calendar_events";
		public static final String COL_USERID="user_id";
		public static final String COL_EVENTTYPE="event_type";
		public static final String COL_DESCRIPTION="description";
		public static final String COL_LOCATION="location";
		public static final String COL_DTSTART="dtstart";
		public static final String COL_DTEND="dtend";
		public static final String COL_EVENTID="event_id";
	// Constructor
	public DbHelper(Context ctx) { //
		super(ctx, DB_NAME, null, DB_VERSION);
		db = this.getWritableDatabase();
		read=this.getReadableDatabase();
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		this.ctx = ctx;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createUserTable(db);
		createCourseTable(db);
		createActivityTable(db);
		createLogTable(db);
		createQuizResultsTable(db);
		
		/* CCH additions */
		createCCHTrackerTable(db);
		createStayingWellTable(db);
		
		/*CHN Target tracking changes*/
		createEventsSetTable(db);
		createCoverageSetTable(db);
		createLearningTable(db);
		createOtherTable(db);
		createJustificationTable(db);
		createCalendarEventsTable(db);
	}

	public void createCourseTable(SQLiteDatabase db){
		String m_sql = "create table " + COURSE_TABLE + " (" + COURSE_C_ID + " integer primary key autoincrement, "
				+ COURSE_C_VERSIONID + " int, " + COURSE_C_TITLE + " text, " + COURSE_C_LOCATION + " text, "
				+ COURSE_C_SHORTNAME + " text," + COURSE_C_SCHEDULE + " int,"
				+ COURSE_C_IMAGE + " text,"
				+ COURSE_C_LANGS + " text)";
		db.execSQL(m_sql);
	}
	
	public void createActivityTable(SQLiteDatabase db){
		String a_sql = "create table " + ACTIVITY_TABLE + " (" + 
									ACTIVITY_C_ID + " integer primary key autoincrement, " + 
									ACTIVITY_C_COURSEID + " int, " + 
									ACTIVITY_C_SECTIONID + " int, " + 
									ACTIVITY_C_ACTID + " int, " + 
									ACTIVITY_C_ACTTYPE + " text, " + 
									ACTIVITY_C_STARTDATE + " datetime null, " + 
									ACTIVITY_C_ENDDATE + " datetime null, " + 
									ACTIVITY_C_ACTIVITYDIGEST + " text, "+
									ACTIVITY_C_TITLE + " text)";
		db.execSQL(a_sql);
	}
	
	
	public void createLogTable(SQLiteDatabase db){
		String l_sql = "create table " + TRACKER_LOG_TABLE + " (" + 
				TRACKER_LOG_C_ID + " integer primary key autoincrement, " + 
				TRACKER_LOG_C_COURSEID + " integer, " + 
				TRACKER_LOG_C_DATETIME + " datetime default current_timestamp, " + 
				TRACKER_LOG_C_ACTIVITYDIGEST + " text, " + 
				TRACKER_LOG_C_DATA + " text, " + 
				TRACKER_LOG_C_SUBMITTED + " integer default 0, " + 
				TRACKER_LOG_C_INPROGRESS + " integer default 0, " +
				TRACKER_LOG_C_COMPLETED + " integer default 0)";
		db.execSQL(l_sql);
	}
	public void createQuizResultsTable(SQLiteDatabase db){
		String m_sql = "create table " + QUIZRESULTS_TABLE + " (" + 
							QUIZRESULTS_C_ID + " integer primary key autoincrement, " + 
							QUIZRESULTS_C_DATETIME + " datetime default current_timestamp, " + 
							QUIZRESULTS_C_DATA + " text, " +  
							QUIZRESULTS_C_SENT + " integer default 0, "+
							QUIZRESULTS_C_COURSEID + " integer)";
		db.execSQL(m_sql);
	}
	

	public void createCalendarEventsTable(SQLiteDatabase db){
		//	table create statement for events set table 
			final String CALENDAR_EVENTS_CREATE_TABLE =
				    "CREATE TABLE " + CALENDAR_EVENTS_TABLE + " (" +
				    		BaseColumns._ID + " INTEGER PRIMARY KEY," +
				    		COL_EVENTTYPE + TEXT_TYPE + COMMA_SEP +
				    		COL_EVENTID + TEXT_TYPE + COMMA_SEP +
				    		COL_USERID + TEXT_TYPE + COMMA_SEP +
				    		COL_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
				    		COL_LOCATION + TEXT_TYPE + COMMA_SEP +
				    		COL_DTSTART + TEXT_TYPE + COMMA_SEP +
				    		COL_DTEND+TEXT_TYPE+
				    " )";
			db.execSQL(CALENDAR_EVENTS_CREATE_TABLE);
		}
	public void createEventsSetTable(SQLiteDatabase db){
	//	table create statement for events set table 
		final String EVENT_SET_CREATE_TABLE =
			    "CREATE TABLE " + EVENTS_SET_TABLE + " (" +
			    		BaseColumns._ID + " INTEGER PRIMARY KEY," +
			    		COL_EVENT_SET_NAME + TEXT_TYPE + COMMA_SEP +
			    		COL_EVENT_PERIOD + TEXT_TYPE + COMMA_SEP +
			    		COL_SYNC_STATUS + TEXT_TYPE + COMMA_SEP +
			    		COL_EVENT_MONTH + TEXT_TYPE + COMMA_SEP +
			    		COL_EVENT_DUE_DATE+ TEXT_TYPE +COMMA_SEP+
			    		COL_EVENT_NUMBER+TEXT_TYPE+
			    " )";
		db.execSQL(EVENT_SET_CREATE_TABLE);
	}
	
	public void createCoverageSetTable(SQLiteDatabase db){
//		table create statement for events set table 
		final String COVERAGE_SET_CREATE_TABLE =
			    "CREATE TABLE " + COVERAGE_SET_TABLE + " (" +
			    		BaseColumns._ID + " INTEGER PRIMARY KEY," +
			    		COL_COVERAGE_SET_CATEGORY_NAME + TEXT_TYPE + COMMA_SEP +
			    		COL_COVERAGE_SET_CATEGORY_DETAIL + TEXT_TYPE + COMMA_SEP +
			    		COL_COVERAGE_SET_PERIOD + TEXT_TYPE + COMMA_SEP +
			    		COL_COVERAGE_NUMBER + TEXT_TYPE + COMMA_SEP +
			    		COL_COVERAGE_DUE_DATE+ TEXT_TYPE +COMMA_SEP+
			    		COL_COVERAGE_MONTH+ TEXT_TYPE + COMMA_SEP +
			    		COL_SYNC_STATUS+TEXT_TYPE+
			    " )";
		db.execSQL(COVERAGE_SET_CREATE_TABLE);
	}
	
	public void createLearningTable(SQLiteDatabase db){
//		table create statement for learning table
			final String LEARNING_CREATE_TABLE =
				    "CREATE TABLE " + LEARNING_TABLE + " (" +
				    		BaseColumns._ID + " INTEGER PRIMARY KEY," +
				    		COL_LEARNING_CATEGORY + TEXT_TYPE + COMMA_SEP +
				    		COL_LEARNING_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
				    		COL_LEARNING_MONTH+ TEXT_TYPE + COMMA_SEP +
				    		COL_LEARNING_TOPIC+ TEXT_TYPE + COMMA_SEP +
				    		COL_LEARNING_DUE_DATE+ TEXT_TYPE + COMMA_SEP +
				    		COL_SYNC_STATUS+ TEXT_TYPE+
				    " )";
			db.execSQL(LEARNING_CREATE_TABLE);
	}
	
	public void createOtherTable(SQLiteDatabase db){
//		table create statement for other table
			final String OTHER_CREATE_TABLE =
				    "CREATE TABLE " + OTHER_TABLE + " (" +
				    		BaseColumns._ID + " INTEGER PRIMARY KEY," +
				    		COL_OTHER_CATEGORY + TEXT_TYPE + COMMA_SEP +
				    		COL_OTHER_NUMBER + TEXT_TYPE + COMMA_SEP +
				    		COL_OTHER_PERIOD + TEXT_TYPE + COMMA_SEP+
				    		COL_OTHER_MONTH + TEXT_TYPE + COMMA_SEP+
				    		COL_OTHER_DUE_DATE+ TEXT_TYPE + COMMA_SEP +
				    		COL_SYNC_STATUS+ TEXT_TYPE+
				    " )";
			db.execSQL(OTHER_CREATE_TABLE);
	}
	
	public void createJustificationTable(SQLiteDatabase db){
	
			final String JUSTIFICATION_CREATE_TABLE =
				    "CREATE TABLE " + JUSTIFICATION_TABLE + " (" +
				    		BaseColumns._ID + " INTEGER PRIMARY KEY," +
				    		COL_TYPE + TEXT_TYPE + COMMA_SEP +
				    		COL_TYPE_DETAIL + TEXT_TYPE + COMMA_SEP +
				    		COL_JUSTIFICATION + TEXT_TYPE + COMMA_SEP+
				    		COL_NUMBER + TEXT_TYPE + COMMA_SEP+
				    		COL_NUMBER_ACHIEVED + TEXT_TYPE + COMMA_SEP+
				    		COL_TARGET_ID + TEXT_TYPE + COMMA_SEP+
				    		COL_COMMENT + TEXT_TYPE + COMMA_SEP+
				    		COL_SYNC_STATUS+ TEXT_TYPE+
				    " )";
			db.execSQL(JUSTIFICATION_CREATE_TABLE);
	}
	
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		if(oldVersion < 7){
			db.execSQL("drop table if exists " + COURSE_TABLE);
			db.execSQL("drop table if exists " + ACTIVITY_TABLE);
			db.execSQL("drop table if exists " + TRACKER_LOG_TABLE);
			db.execSQL("drop table if exists " + QUIZRESULTS_TABLE);
			db.execSQL("drop table if exists " + CCH_TRACKER_TABLE);
			db.execSQL("drop table if exists " + EVENTS_SET_TABLE);
			db.execSQL("drop table if exists " + COVERAGE_SET_TABLE);
			db.execSQL("drop table if exists " + LEARNING_TABLE);
			db.execSQL("drop table if exists " + OTHER_TABLE);
			db.execSQL("drop table if exists " + JUSTIFICATION_TABLE);
			db.execSQL("drop table if exists " + CALENDAR_EVENTS_TABLE);
			db.execSQL("drop table if exists " + CCH_SW_TABLE);
			createCourseTable(db);
			createActivityTable(db);
			createLogTable(db);
			createQuizResultsTable(db);
			createCCHTrackerTable(db);
			createUserTable(db);
			createEventsSetTable(db);
			createCoverageSetTable(db);
			createLearningTable(db);
			createOtherTable(db);
			createJustificationTable(db);
			createCalendarEventsTable(db);
			createStayingWellTable(db);
			return;
		}
		
		if(oldVersion <= 7 && newVersion >= 8){
			String sql = "ALTER TABLE " + ACTIVITY_TABLE + " ADD COLUMN " + ACTIVITY_C_STARTDATE + " datetime null;";
			db.execSQL(sql);
			sql = "ALTER TABLE " + ACTIVITY_TABLE + " ADD COLUMN " + ACTIVITY_C_ENDDATE + " datetime null;";
			db.execSQL(sql);
		}
		
		if(oldVersion <= 8 && newVersion >= 9){
			String sql = "ALTER TABLE " + COURSE_TABLE + " ADD COLUMN " + COURSE_C_SCHEDULE + " int null;";
			db.execSQL(sql);
		}
		
		if(oldVersion <= 9 && newVersion >= 10){
			String sql = "ALTER TABLE " + ACTIVITY_TABLE + " ADD COLUMN " + ACTIVITY_C_TITLE  + " text null;";
			db.execSQL(sql);
		}
		
		// This is a fix as previous versioning may not have upgraded db tables correctly
		if(oldVersion <= 10 && newVersion >=11){
			String sql1 = "ALTER TABLE " + ACTIVITY_TABLE + " ADD COLUMN " + ACTIVITY_C_STARTDATE + " datetime null;";
			String sql2 = "ALTER TABLE " + ACTIVITY_TABLE + " ADD COLUMN " + ACTIVITY_C_ENDDATE + " datetime null;";
			String sql3 = "ALTER TABLE " + COURSE_TABLE + " ADD COLUMN " + COURSE_C_SCHEDULE + " int null;";
			String sql4 = "ALTER TABLE " + ACTIVITY_TABLE + " ADD COLUMN " + ACTIVITY_C_TITLE  + " text null;";
			try {
				db.execSQL(sql1);
			} catch (Exception e){
				
			}
			try {
				db.execSQL(sql2);
			} catch (Exception e){
				
			}
			try {
				db.execSQL(sql3);
			} catch (Exception e){
				
			}
			try {
				db.execSQL(sql4);
			} catch (Exception e){
				
			}
		}
		
		if(oldVersion <= 11 && newVersion >= 12){
			String sql = "ALTER TABLE " + COURSE_TABLE + " ADD COLUMN " + COURSE_C_LANGS  + " text null;";
			db.execSQL(sql);
			sql = "ALTER TABLE " + COURSE_TABLE + " ADD COLUMN " + COURSE_C_IMAGE  + " text null;";
			db.execSQL(sql);
		}
		
		if(oldVersion <= 12 && newVersion >= 13){
			String sql = "ALTER TABLE " + TRACKER_LOG_TABLE + " ADD COLUMN " + TRACKER_LOG_C_COMPLETED  + " integer default 0;";
			db.execSQL(sql);
		}
		// skip jump from 13 to 14
		if(oldVersion <= 14 && newVersion >= 15){
			ContentValues values = new ContentValues();
			values.put(TRACKER_LOG_C_COMPLETED,true);
			db.update(TRACKER_LOG_TABLE, values, null, null);
		}
	}

	public void onLogout(){
		/***
		  db.execSQL("DELETE FROM "+ TRACKER_LOG_TABLE);
		  db.execSQL("DELETE FROM "+ QUIZRESULTS_TABLE);
		 ***/	
		// Store current users prefs.
		String userid = prefs.getString(ctx.getString(R.string.prefs_username),"" );
		
		if (! userid.equals(""))
		{
			String firstname = "";
			String lastname ="" ;
			
			if (! prefs.getString(ctx.getString(R.string.prefs_display_name),"").isEmpty()) {
				String displayname = prefs.getString(ctx.getString(R.string.prefs_display_name),"");
				String[] name = displayname.split(" ");
				if (name.length > 0) {
					firstname = name[0];
					lastname = name[1];
				} else {
					firstname = displayname;
				}
			}
			int points =  prefs.getInt(ctx.getString(R.string.prefs_points),0);
			int badges =  prefs.getInt(ctx.getString(R.string.prefs_badges),0);			
			boolean scoring = prefs.getBoolean(ctx.getString(R.string.prefs_scoring_enabled),true);

			updateUser(userid, prefs.getString(ctx.getString(R.string.prefs_api_key),""), firstname, lastname, points, badges, scoring);
		}
		
		
		// Reset preferences
		Editor editor = prefs.edit();
    	editor.putString(ctx.getString(R.string.prefs_username), "");
    	editor.putString(ctx.getString(R.string.prefs_api_key), "");
    	editor.putString(ctx.getString(R.string.prefs_display_name),"");
    	editor.putInt(ctx.getString(R.string.prefs_points), 0);
    	editor.putInt(ctx.getString(R.string.prefs_badges), 0);
    	editor.putBoolean(ctx.getString(R.string.prefs_scoring_enabled), false);
    	editor.commit();
	}
	
	// returns id of the row
	public long addOrUpdateCourse(Course course) {
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COURSE_C_VERSIONID, course.getVersionId());
		values.put(COURSE_C_TITLE, course.getTitleJSONString());
		values.put(COURSE_C_LOCATION, course.getLocation());
		values.put(COURSE_C_SHORTNAME, course.getShortname());
		values.put(COURSE_C_LANGS, course.getLangsJSONString());
		values.put(COURSE_C_IMAGE, course.getImageFile());

		if (!this.isInstalled(course.getShortname())) {
			Log.v(TAG, "Record added");
			return db.insertOrThrow(COURSE_TABLE, null, values);
		} else if(this.toUpdate(course.getShortname(), course.getVersionId())){
			long toUpdate = this.getCourseID(course.getShortname());
			if (toUpdate != 0) {
				db.update(COURSE_TABLE, values, COURSE_C_ID + "=" + toUpdate, null);
				// remove all the old activities
				String s = ACTIVITY_C_COURSEID + "=?";
				String[] args = new String[] { String.valueOf(toUpdate) };
				db.delete(ACTIVITY_TABLE, s, args);
				return toUpdate;
			}
		} 
		return -1;
	}
	public boolean insertCalendarEvent(long event_id, String event_type, String user_id, String description, String location,long dtstart,long dtend){
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COL_EVENTID,event_id);
		values.put(COL_EVENTTYPE,event_type);
		values.put(COL_USERID,user_id);
		values.put(COL_DESCRIPTION,description);
		values.put(COL_LOCATION,location);
		values.put(COL_DTSTART,dtstart);
		values.put(COL_DTEND,dtend);
		
		long newRowId;
		newRowId = db.insert(
				CALENDAR_EVENTS_TABLE, null, values);
		
		return true;
	}
	public HashMap<String,String> getCalendarEvents(String user_id){
		HashMap<String,String> list=new HashMap<String,String>();
		 String strQuery="select "+BaseColumns._ID
	             +","+COL_EVENTTYPE
	             +","+COL_EVENTID
	             +","+COL_DESCRIPTION
	             +","+COL_LOCATION
	             +","+COL_DTSTART
	             +","+COL_DTEND
	             +" from "+CALENDAR_EVENTS_TABLE
	             +" where "+COL_USERID
	             +" = '"+user_id+"'";	
		 
		System.out.println(strQuery);
		Cursor c=read.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			list.put("event_type",c.getString(c.getColumnIndex(COL_EVENTTYPE)));
			list.put("event_id",c.getString(c.getColumnIndex(COL_EVENTTYPE)));
			list.put("event_description", c.getString(c.getColumnIndex(COL_DESCRIPTION)));
			list.put("event_location",  c.getString(c.getColumnIndex(COL_LOCATION)));
			list.put("event_dtstart",  c.getString(c.getColumnIndex(COL_DTSTART)));
			list.put("event_dtend",  c.getString(c.getColumnIndex(COL_DTEND)));
			c.moveToNext();						
		}
		c.close();
		return list;
	}
	public boolean insertEventSet(String event_name, String event_period, String event_number, String month, String due_date,String sync_status){
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COL_EVENT_SET_NAME,event_name);
		values.put(COL_EVENT_PERIOD,event_period);
		values.put(COL_SYNC_STATUS,sync_status);
		values.put(COL_EVENT_NUMBER,event_number);
		values.put(COL_EVENT_DUE_DATE,due_date);
		values.put(COL_EVENT_MONTH,month);
		
		long newRowId;
		newRowId = db.insert(
				EVENTS_SET_TABLE, null, values);
		return true;
	}
	public boolean insertCoverageSet(String category_name, String category_detail,String coverage_period, String coverage_number,String month,String due_date, String sync_status){
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COL_COVERAGE_SET_CATEGORY_NAME,category_name);
		values.put(COL_COVERAGE_SET_CATEGORY_DETAIL,category_detail);
		values.put(COL_COVERAGE_SET_PERIOD,coverage_period);
		values.put(COL_COVERAGE_NUMBER,coverage_number);
		values.put(COL_COVERAGE_DUE_DATE,due_date);
		values.put(COL_COVERAGE_MONTH,month);
		values.put(COL_SYNC_STATUS,sync_status);
		
		long newRowId;
		newRowId = db.insert(
				COVERAGE_SET_TABLE, null, values);
		return true;
	}
	
	public boolean insertLearning(String learning_category, String learning_description,String topic, String month,String due_date,String sync_status){
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COL_LEARNING_CATEGORY,learning_category);
		values.put(COL_LEARNING_DESCRIPTION,learning_description);
		values.put(COL_LEARNING_TOPIC,topic);
		values.put(COL_LEARNING_MONTH,month);
		values.put(COL_LEARNING_DUE_DATE,due_date);
		values.put(COL_SYNC_STATUS,sync_status);
		
		long newRowId;
		newRowId = db.insert(
				LEARNING_TABLE, null, values);
		return true;
	}
	
	public boolean insertOther(String other_category,String other_number,String other_period,String month,String due_date, String sync_status){
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COL_OTHER_CATEGORY,other_category);
		values.put(COL_OTHER_NUMBER,other_number);
		values.put(COL_OTHER_PERIOD,other_period);
		values.put(COL_OTHER_MONTH,month);
		values.put(COL_OTHER_DUE_DATE,due_date);
		values.put(COL_SYNC_STATUS,sync_status);
		
		db.insert(OTHER_TABLE, null, values);
		return true;
	}
	
	public long insertJustification(String type,String type_detail,String justification,String comment,String number,String achieved_number,long id, String sync_status){
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COL_TYPE,type);
		values.put(COL_TYPE_DETAIL,type_detail);
		values.put(COL_JUSTIFICATION,justification);
		values.put(COL_NUMBER,number);
		values.put(COL_NUMBER_ACHIEVED,achieved_number);
		values.put(COL_TARGET_ID,id);
		values.put(COL_COMMENT,comment);
		values.put(COL_SYNC_STATUS,sync_status);
		
		long newRowId;
		newRowId = db.insert(
				JUSTIFICATION_TABLE, null, values);
		return newRowId;
	}
	public long refreshCourse(Course course){
		db = this.getWritableDatabase();
		long modId = this.getCourseID(course.getShortname());
		ContentValues values = new ContentValues();
		values.put(COURSE_C_VERSIONID, course.getVersionId());
		values.put(COURSE_C_TITLE, course.getTitleJSONString());
		values.put(COURSE_C_LOCATION, course.getLocation());	
		values.put(COURSE_C_SHORTNAME, course.getShortname());
		values.put(COURSE_C_LANGS, course.getLangsJSONString());
		values.put(COURSE_C_IMAGE, course.getImageFile());
		db.update(COURSE_TABLE, values, COURSE_C_ID + "=" + modId, null);
		// remove all the old activities
		String s = ACTIVITY_C_COURSEID + "=?";
		String[] args = new String[] { String.valueOf(modId) };
		db.delete(ACTIVITY_TABLE, s, args);
		return modId;
	}
	
	
	public int getCourseID(String shortname){
		read=this.getReadableDatabase();
		String s = COURSE_C_SHORTNAME + "=?";
		String[] args = new String[] { shortname };
		Cursor c = read.query(COURSE_TABLE, null, s, args, null, null, null);
		if(c.getCount() == 0){
			c.close();
			return 0;
		} else {
			c.moveToFirst();
			int modId = c.getInt(c.getColumnIndex(COURSE_C_ID));
			c.close();
			return modId;
		}
	}
	
	public void updateScheduleVersion(long modId, long scheduleVersion){
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COURSE_C_SCHEDULE, scheduleVersion);
		db.update(COURSE_TABLE, values, COURSE_C_ID + "=" + modId, null);
	}
	
	public void insertActivities(ArrayList<Activity> acts) {
		// acts.listIterator();
		db = this.getWritableDatabase();
		for (Activity a : acts) {
			ContentValues values = new ContentValues();
			values.put(ACTIVITY_C_COURSEID, a.getModId());
			values.put(ACTIVITY_C_SECTIONID, a.getSectionId());
			values.put(ACTIVITY_C_ACTID, a.getActId());
			values.put(ACTIVITY_C_ACTTYPE, a.getActType());
			values.put(ACTIVITY_C_ACTIVITYDIGEST, a.getDigest());
			values.put(ACTIVITY_C_TITLE, a.getTitleJSONString());
			db.insertOrThrow(ACTIVITY_TABLE, null, values);
		}
	}

	public void insertSchedule(ArrayList<ActivitySchedule> actsched) {
		// acts.listIterator();
		db = this.getWritableDatabase();
		for (ActivitySchedule as : actsched) {
			ContentValues values = new ContentValues();
			values.put(ACTIVITY_C_STARTDATE, as.getStartTimeString());
			values.put(ACTIVITY_C_ENDDATE, as.getEndTimeString());
			db.update(ACTIVITY_TABLE, values, ACTIVITY_C_ACTIVITYDIGEST + "='" + as.getDigest() + "'", null);
		}
	}
	
	public void insertTrackers(ArrayList<TrackerLog> trackers, long modId) {
		// acts.listIterator();
		db = this.getWritableDatabase();
		for (TrackerLog t : trackers) {
			ContentValues values = new ContentValues();
			values.put(TRACKER_LOG_C_DATETIME, t.getDateTimeString());
			values.put(TRACKER_LOG_C_ACTIVITYDIGEST, t.getDigest());
			values.put(TRACKER_LOG_C_SUBMITTED, t.isSubmitted());
			values.put(TRACKER_LOG_C_COURSEID, modId);
			values.put(TRACKER_LOG_C_COMPLETED, true);
			db.insertOrThrow(TRACKER_LOG_TABLE, null, values);
		}
	}
	
	public void resetSchedule(int modId){
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACTIVITY_C_STARTDATE,"");
		values.put(ACTIVITY_C_ENDDATE,"");
		db.update(ACTIVITY_TABLE, values, ACTIVITY_C_COURSEID + "=" + modId, null);
	}
	
	public ArrayList<Course> getCourses() {
		read=this.getReadableDatabase();
		ArrayList<Course> courses = new ArrayList<Course>();
		String order = COURSE_C_TITLE + " ASC";
		Cursor c = read.query(COURSE_TABLE, null, null, null, null, null, order);
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			Course course = new Course();
			course.setModId(c.getInt(c.getColumnIndex(COURSE_C_ID)));
			course.setLocation(c.getString(c.getColumnIndex(COURSE_C_LOCATION)));
			course.setProgress(this.getCourseProgress(course.getModId()));
			course.setVersionId(c.getDouble(c.getColumnIndex(COURSE_C_VERSIONID)));
			course.setTitlesFromJSONString(c.getString(c.getColumnIndex(COURSE_C_TITLE)));
			course.setImageFile(c.getString(c.getColumnIndex(COURSE_C_IMAGE)));
			course.setLangsFromJSONString(c.getString(c.getColumnIndex(COURSE_C_LANGS)));
			course.setShortname(c.getString(c.getColumnIndex(COURSE_C_SHORTNAME)));
			courses.add(course);
			c.moveToNext();
		}
		c.close();
		return courses;
	}
	
	public Course getCourse(long modId) {
		read=this.getReadableDatabase();
		Course m = null;
		String s = COURSE_C_ID + "=?";
		String[] args = new String[] { String.valueOf(modId) };
		Cursor c = read.query(COURSE_TABLE, null, s, args, null, null, null);
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			m = new Course();
			m.setModId(c.getInt(c.getColumnIndex(COURSE_C_ID)));
			m.setLocation(c.getString(c.getColumnIndex(COURSE_C_LOCATION)));
			m.setProgress(this.getCourseProgress(m.getModId()));
			m.setVersionId(c.getDouble(c.getColumnIndex(COURSE_C_VERSIONID)));
			m.setTitlesFromJSONString(c.getString(c.getColumnIndex(COURSE_C_TITLE)));
			m.setImageFile(c.getString(c.getColumnIndex(COURSE_C_IMAGE)));
			m.setLangsFromJSONString(c.getString(c.getColumnIndex(COURSE_C_LANGS)));
			m.setShortname(c.getString(c.getColumnIndex(COURSE_C_SHORTNAME)));
			c.moveToNext();
		}
		c.close();
		return m;
	}
	
	public void insertLog(int modId, String digest, String data, boolean completed){
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TRACKER_LOG_C_COURSEID, modId);
		values.put(TRACKER_LOG_C_ACTIVITYDIGEST, digest);
		values.put(TRACKER_LOG_C_DATA, data);
		values.put(TRACKER_LOG_C_COMPLETED, completed);
		db.insertOrThrow(TRACKER_LOG_TABLE, null, values);
	}
	
	public float getCourseProgress(int modId){
		db = this.getWritableDatabase();
		String sql = "SELECT a."+ ACTIVITY_C_ID + ", " +
				"l."+ TRACKER_LOG_C_ACTIVITYDIGEST + 
				" as d FROM "+ACTIVITY_TABLE + " a " +
				" LEFT OUTER JOIN (SELECT DISTINCT " +TRACKER_LOG_C_ACTIVITYDIGEST +" FROM " + TRACKER_LOG_TABLE + 
									" WHERE " + TRACKER_LOG_C_COMPLETED + "=1 AND " + TRACKER_LOG_C_COURSEID + "=" + String.valueOf(modId) + ") l " +
									" ON a."+ ACTIVITY_C_ACTIVITYDIGEST +" = l."+TRACKER_LOG_C_ACTIVITYDIGEST + 
				" WHERE a."+ ACTIVITY_C_COURSEID +"=" + String.valueOf(modId);
		Cursor c = db.rawQuery(sql,null);
		int noActs = c.getCount();
		int noComplete = 0;
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			if(c.getString(c.getColumnIndex("d")) != null){
				noComplete++;
			}
			c.moveToNext();
		}
		c.close();
		return noComplete*100/noActs;
	}
	
	public float getSectionProgress(int modId, int sectionId){
		db = this.getWritableDatabase();
		String sql = "SELECT a."+ ACTIVITY_C_ID + ", " +
						"l."+ TRACKER_LOG_C_ACTIVITYDIGEST + 
						" as d FROM "+ACTIVITY_TABLE + " a " +
						" LEFT OUTER JOIN (SELECT DISTINCT " +TRACKER_LOG_C_ACTIVITYDIGEST +" FROM " + TRACKER_LOG_TABLE + " WHERE " + TRACKER_LOG_C_COMPLETED + "=1) l " +
								" ON a."+ ACTIVITY_C_ACTIVITYDIGEST +" = l."+TRACKER_LOG_C_ACTIVITYDIGEST + 
						" WHERE a."+ ACTIVITY_C_COURSEID +"=" + String.valueOf(modId) +
						" AND a."+ ACTIVITY_C_SECTIONID +"=" + String.valueOf(sectionId);
		
		Cursor c = db.rawQuery(sql,null);
		int noActs = c.getCount();
		int noComplete = 0;
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			if(c.getString(c.getColumnIndex("d")) != null){
				noComplete++;
			}
			c.moveToNext();
		}
		c.close();
		if(noActs == 0){
			return 0;
		} else {
			return noComplete*100/noActs;
		}
		
	}
	
	public int resetCourse(int modId){
		db = this.getWritableDatabase();
		// delete quiz results
		this.deleteQuizResults(modId);
		
		String s = TRACKER_LOG_C_COURSEID + "=?";
		String[] args = new String[] { String.valueOf(modId) };
		return db.delete(TRACKER_LOG_TABLE, s, args);
	}
	
	public void deleteCourse(int modId){
		// delete log
		db = this.getWritableDatabase();
		resetCourse(modId);
		
		// delete activities
		String s = ACTIVITY_C_COURSEID + "=?";
		String[] args = new String[] { String.valueOf(modId) };
		db.delete(ACTIVITY_TABLE, s, args);
		
		// delete course
		s = COURSE_C_ID + "=?";
		args = new String[] { String.valueOf(modId) };
		db.delete(COURSE_TABLE, s, args);
		
		// delete any quiz attempts
		this.deleteQuizResults(modId);
	}
	
	public boolean isInstalled(String shortname){
		db = this.getWritableDatabase();
		read=this.getReadableDatabase();
		String s = COURSE_C_SHORTNAME + "=?";
		String[] args = new String[] { shortname };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, null);
		if(c.getCount() == 0){
			c.close();
			return false;
		} else {
			c.close();
			return true;
		}
	}
	
	public boolean toUpdate(String shortname, Double version){
		String s = COURSE_C_SHORTNAME + "=? AND "+ COURSE_C_VERSIONID + "< ?";
		String[] args = new String[] { shortname, String.format("%.0f", version) };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, null);
		if(c.getCount() == 0){
			c.close();
			return false;
		} else {
			c.close();
			return true;
		}
	}
	
	public boolean toUpdateSchedule(String shortname, Double scheduleVersion){
		String s = COURSE_C_SHORTNAME + "=? AND "+ COURSE_C_SCHEDULE + "< ?";
		String[] args = new String[] { shortname, String.format("%.0f", scheduleVersion) };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, null);
		if(c.getCount() == 0){
			c.close();
			return false;
		} else {
			c.close();
			return true;
		}
	}
	
	public Payload getUnsentLog(){
		String s = TRACKER_LOG_C_SUBMITTED + "=? ";
		String[] args = new String[] { "0" };
		Cursor c = db.query(TRACKER_LOG_TABLE, null, s, args, null, null, null);
		c.moveToFirst();

		ArrayList<Object> sl = new ArrayList<Object>();
		while (c.isAfterLast() == false) {
			TrackerLog so = new TrackerLog();
			so.setId(c.getLong(c.getColumnIndex(TRACKER_LOG_C_ID)));
			so.setDigest(c.getString(c.getColumnIndex(TRACKER_LOG_C_ACTIVITYDIGEST)));
			String content ="" ;
			try {
				JSONObject json = new JSONObject();
				json.put("data", c.getString(c.getColumnIndex(TRACKER_LOG_C_DATA)));
				json.put("tracker_date", c.getString(c.getColumnIndex(TRACKER_LOG_C_DATETIME)));
				json.put("digest", c.getString(c.getColumnIndex(TRACKER_LOG_C_ACTIVITYDIGEST)));
				json.put("completed", c.getInt(c.getColumnIndex(TRACKER_LOG_C_COMPLETED)));
				Course m = this.getCourse(c.getLong(c.getColumnIndex(TRACKER_LOG_C_COURSEID)));
				if (m != null){
					json.put("course", m.getShortname());
				}
				content = json.toString();
			} catch (JSONException e) {
				if(!MobileLearning.DEVELOPER_MODE){
					BugSenseHandler.sendException(e);
				} else {
					e.printStackTrace();
				}
			}
			
			so.setContent(content);
			sl.add(so);
			c.moveToNext();
		}
		Payload p = new Payload(sl);
		c.close();
		
		return p;
	}
	
	public int markLogSubmitted(long rowId){
		ContentValues values = new ContentValues();
		values.put(TRACKER_LOG_C_SUBMITTED, 1);
		
		return db.update(TRACKER_LOG_TABLE, values, TRACKER_LOG_C_ID + "=" + rowId, null);
	}
	
	public long insertQuizResult(String data, int modId){
		ContentValues values = new ContentValues();
		values.put(QUIZRESULTS_C_DATA, data);
		values.put(QUIZRESULTS_C_COURSEID, modId);
		return db.insertOrThrow(QUIZRESULTS_TABLE, null, values);
	}
	
	public Payload getUnsentQuizResults(){
		String s = QUIZRESULTS_C_SENT + "=? ";
		String[] args = new String[] { "0" };
		Cursor c = db.query(QUIZRESULTS_TABLE, null, s, args, null, null, null);
		c.moveToFirst();
		ArrayList<Object> sl = new ArrayList<Object>();
		while (c.isAfterLast() == false) {
			TrackerLog so = new TrackerLog();
			so.setId(c.getLong(c.getColumnIndex(QUIZRESULTS_C_ID)));
			so.setContent(c.getString(c.getColumnIndex(QUIZRESULTS_C_DATA)));
			sl.add(so);
			c.moveToNext();
		}
		Payload p = new Payload(sl);
		c.close();
		
		return p;
	}
	
	public int markQuizSubmitted(long rowId){
		ContentValues values = new ContentValues();
		values.put(QUIZRESULTS_C_SENT, 1);
		
		return db.update(QUIZRESULTS_TABLE, values, QUIZRESULTS_C_ID + "=" + rowId, null);
	}
	
	public void deleteQuizResults(int modId){
		// delete any quiz attempts
		String s = QUIZRESULTS_C_COURSEID + "=?";
		String[] args = new String[] { String.valueOf(modId) };
		db.delete(QUIZRESULTS_TABLE, s, args);
	}
	
	public boolean activityAttempted(long modId, String digest){
		String s = TRACKER_LOG_C_ACTIVITYDIGEST + "=? AND " + TRACKER_LOG_C_COURSEID + "=?";
		String[] args = new String[] { digest, String.valueOf(modId) };
		Cursor c = db.query(TRACKER_LOG_TABLE, null, s, args, null, null, null);
		if(c.getCount() == 0){
			c.close();
			return false;
		} else {
			c.close();
			return true;
		}
	}
	
	public boolean activityCompleted(int modId, String digest){
		String s = TRACKER_LOG_C_ACTIVITYDIGEST + "=? AND " + TRACKER_LOG_C_COURSEID + "=? AND " + TRACKER_LOG_C_COMPLETED + "=1";
		String[] args = new String[] { digest, String.valueOf(modId) };
		Cursor c = db.query(TRACKER_LOG_TABLE, null, s, args, null, null, null);
		if(c.getCount() == 0){
			c.close();
			return false;
		} else {
			c.close();
			return true;
		}
	}
	
	public ArrayList<Activity> getActivitiesDue(int max){
		
		ArrayList<Activity> activities = new ArrayList<Activity>();
		DateTime now = new DateTime();
		String nowDateString = MobileLearning.DATETIME_FORMAT.print(now);
		String sql = "SELECT a.* FROM "+ ACTIVITY_TABLE + " a " +
					" INNER JOIN " + COURSE_TABLE + " m ON a."+ ACTIVITY_C_COURSEID + " = m."+COURSE_C_ID +
					" LEFT OUTER JOIN (SELECT * FROM " + TRACKER_LOG_TABLE + " WHERE " + TRACKER_LOG_C_COMPLETED + "=1) tl ON a."+ ACTIVITY_C_ACTIVITYDIGEST + " = tl."+ TRACKER_LOG_C_ACTIVITYDIGEST +
					" WHERE tl." + TRACKER_LOG_C_ID + " IS NULL "+
					" AND a." + ACTIVITY_C_STARTDATE + "<='" + nowDateString + "'" +
					" AND a." + ACTIVITY_C_TITLE + " IS NOT NULL " +
					" ORDER BY a." + ACTIVITY_C_ENDDATE + " ASC" +
					" LIMIT " + max;
							
		
		Cursor c = db.rawQuery(sql,null);
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			Activity a = new Activity();
			if(c.getString(c.getColumnIndex(ACTIVITY_C_TITLE)) != null){
				a.setTitlesFromJSONString(c.getString(c.getColumnIndex(ACTIVITY_C_TITLE)));
				a.setModId(c.getLong(c.getColumnIndex(ACTIVITY_C_COURSEID)));
				a.setDigest(c.getString(c.getColumnIndex(ACTIVITY_C_ACTIVITYDIGEST)));
				activities.add(a);
			}
			c.moveToNext();
		}
		
		if(c.getCount() < max){
			//just add in some extra suggested activities unrelated to the date/time
			String sql2 = "SELECT a.* FROM "+ ACTIVITY_TABLE + " a " +
					" INNER JOIN " + COURSE_TABLE + " m ON a."+ ACTIVITY_C_COURSEID + " = m."+COURSE_C_ID +
					" LEFT OUTER JOIN (SELECT * FROM " + TRACKER_LOG_TABLE + " WHERE " + TRACKER_LOG_C_COMPLETED + "=1) tl ON a."+ ACTIVITY_C_ACTIVITYDIGEST + " = tl."+ TRACKER_LOG_C_ACTIVITYDIGEST +
					" WHERE (tl." + TRACKER_LOG_C_ID + " IS NULL "+
					" OR tl." + TRACKER_LOG_C_COMPLETED + "=0)" +
					" AND a." + ACTIVITY_C_TITLE + " IS NOT NULL " +
					" AND a." + ACTIVITY_C_ID + " NOT IN (SELECT " + ACTIVITY_C_ID + " FROM (" + sql + ") b)" +
					" LIMIT " + (max-c.getCount());
			Cursor c2 = db.rawQuery(sql2,null);
			c2.moveToFirst();
			while (c2.isAfterLast() == false) {
				Activity a = new Activity();
				if(c2.getString(c.getColumnIndex(ACTIVITY_C_TITLE)) != null){
					a.setTitlesFromJSONString(c2.getString(c2.getColumnIndex(ACTIVITY_C_TITLE)));
					a.setModId(c2.getLong(c2.getColumnIndex(ACTIVITY_C_COURSEID)));
					a.setDigest(c2.getString(c2.getColumnIndex(ACTIVITY_C_ACTIVITYDIGEST)));
					activities.add(a);
				}
				c2.moveToNext();
			}
			c2.close();
		}
		c.close();
		return activities;
	}

	/*** CCH: Additions ********************************************************************************/
	public ArrayList<String> getQuizResults(int courseid)
	{
		ArrayList<String> quizzes = new ArrayList<String>();
		String s = QUIZRESULTS_C_COURSEID + "=? ";
		String[] args = new String[] { String.valueOf(courseid) };
		Cursor c = db.query(QUIZRESULTS_TABLE, null, s, args, null, null, null);
		c.moveToFirst();
		int quiznum = 1;
		HashMap qt = new HashMap();

		while (c.isAfterLast() == false) {
			String data = c.getString(c.getColumnIndex(QUIZRESULTS_C_DATA));
			try {
				JSONObject j = new JSONObject(data);
				String t = "Quiz ";
				if (qt.containsKey(j.getString("quiz_id"))) {
					t += qt.get(j.getString("quiz_id")) + " retake";
				} else {
					t += quiznum;
					qt.put(j.getString("quiz_id"), quiznum);
				}
				quizzes.add(t + ": "+ j.getString("score") + "/" + j.getString("maxscore"));
				quiznum++;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			c.moveToNext();
		}
		c.close();
		
		return quizzes;
	}
	
	public String getQuizTitle(int courseid, int quizid)
	{
		String quiztitle = "";
		
		//String s = ACTIVITY_C_COURSEID + "=? AND " + ACTIVITY_C_ACTID + "=?" ;
		String s = ACTIVITY_C_COURSEID + "=?" ;

		//String[] args = new String[] { String.valueOf(courseid), String.valueOf(quizid) };
		String[] args = new String[] { String.valueOf(courseid) };

		Cursor c = db.query(ACTIVITY_TABLE, null, s, args, null, null, null);
		
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			   quiztitle += ", " +  c.getString(c.getColumnIndex(ACTIVITY_C_SECTIONID));
					   							   c.moveToNext();
		}
		
		return (quiztitle.equals("")) ? "Quiz " + quizid : quiztitle; 
	}
	
	/** Tracking table **/
	public void createCCHTrackerTable(SQLiteDatabase db){
		String l_sql = "create table if not exists " + CCH_TRACKER_TABLE + " (" + 
				CCH_TRACKER_ID + " integer primary key autoincrement, " + 
				CCH_TRACKER_USERID + " text, " + 
				CCH_TRACKER_MODULE + " text, " + 
				CCH_TRACKER_START_DATETIME + " string , " + 
				CCH_TRACKER_END_DATETIME + " string , " + 
				CCH_TRACKER_DATA + " text, " + 
				CCH_TRACKER_SUBMITTED + " integer default 0, " + 
				CCH_TRACKER_INPROGRESS + " integer default 0)";
		db.execSQL(l_sql);
	}
	
	public void insertCCHLog(String module, String data, String starttime, String endtime){
		db = this.getWritableDatabase();
		String userid = prefs.getString(ctx.getString(R.string.prefs_username), "noid"); 
		System.out.println(userid);
		ContentValues values = new ContentValues();
		values.put(CCH_TRACKER_USERID, userid);
		values.put(CCH_TRACKER_MODULE, module);
		values.put(CCH_TRACKER_DATA, data);
		values.put(CCH_TRACKER_START_DATETIME, starttime);
		values.put(CCH_TRACKER_END_DATETIME, endtime);
		Log.v("insertCCHLOG", values.toString());
		db.insertOrThrow(CCH_TRACKER_TABLE, null, values);
	}
	
	public Payload getCCHUnsentLog(){
		String s = CCH_TRACKER_SUBMITTED + "=? ";
		String[] args = new String[] { "0" };
		Cursor c = db.query(CCH_TRACKER_TABLE, null, s, args, null, null, null);
		c.moveToFirst();

		ArrayList<Object> sl = new ArrayList<Object>();
		while (c.isAfterLast() == false) {
			CCHTrackerLog so = new CCHTrackerLog();
			so.setId(c.getLong(c.getColumnIndex(CCH_TRACKER_ID)));
			
			String content ="" ;
			
			try {
				JSONObject json = new JSONObject();
				json.put("user_id", c.getString(c.getColumnIndex(CCH_TRACKER_USERID)));
				json.put("data", c.getString(c.getColumnIndex(CCH_TRACKER_DATA)));
				json.put("module", c.getString(c.getColumnIndex(CCH_TRACKER_MODULE)));
				json.put("start_time", c.getString(c.getColumnIndex(CCH_TRACKER_START_DATETIME)));
				json.put("end_time", c.getString(c.getColumnIndex(CCH_TRACKER_END_DATETIME)));
				content = json.toString();
			} catch (JSONException e) {
			    e.printStackTrace();
			}
			
			so.setContent(content);
			sl.add(so);
			c.moveToNext();
		}
		
		Payload p = new Payload(sl);
		c.close();
		
		return p;
	}
	
	public int markCCHLogSubmitted(long rowId){
		ContentValues values = new ContentValues();
		values.put(CCH_TRACKER_SUBMITTED, 1);
		return db.update(CCH_TRACKER_TABLE, values, CCH_TRACKER_ID + "=" + rowId, null);
	}
	
	/** Staying Well table **/
	public void createStayingWellTable(SQLiteDatabase db)
	{
		String l_sql = "create table if not exists " + CCH_SW_TABLE + " (" + 
				CCH_SW_ID + " integer primary key autoincrement, " + 
				CCH_SW_STAFF_ID + " text, " + 
				CCH_SW_LEGAL_STATUS + " text, " + 
				CCH_SW_PROFILE_STATUS + " text default '', " + 
				CCH_SW_PROFILE_RESPONSES + " text default '', " + 
				CCH_SW_MONTH_PLAN + " text default '', " + 
				CCH_SW_MONTH_PLAN_LASTUPDATE + " text default '')" ; 		
		db.execSQL(l_sql);
		
		// Insert initial information TODO: GET USER ID
		String userid = "David"; //prefs.getString(ctx.getString(R.string.prefs_username), "noid"); 
		ContentValues values = new ContentValues();
		values.put(CCH_SW_STAFF_ID, userid);
		values.put(CCH_SW_LEGAL_STATUS, "");
		values.put(CCH_SW_PROFILE_STATUS, "");
		values.put(CCH_SW_PROFILE_RESPONSES, "");
		values.put(CCH_SW_MONTH_PLAN, "");
		values.put(CCH_SW_MONTH_PLAN_LASTUPDATE, "");
		db.insertOrThrow(CCH_SW_TABLE, null, values);
		
		l_sql = "create table if not exists " + CCH_SW_ROUTINE_TABLE + " (" + 
				CCH_SW_ROUTINE_ID + " integer primary key autoincrement, " + 
				CCH_SW_ROUTINE_TOD + " text, " + 
				CCH_SW_ROUTINE_PROFILE + " text, " + 
				CCH_SW_ROUTINE_PLAN + " text, " + 
				CCH_SW_ROUTINE_ACTION + " text default '', " + 
				CCH_SW_ROUTINE_ORDER + " integer default 0)" ; 
		db.execSQL(l_sql);
		
		insertDefaultRoutineValues();
		
		l_sql = "create table if not exists " + CCH_SW_ROUTINE_TODO_TABLE + " (" + 
				CCH_SW_ROUTINE_TODO_ID + " integer primary key autoincrement, " + 
				CCH_SW_ROUTINE_TODO_STAFF_ID + " text, " + 
				CCH_SW_ROUTINE_TODO_PROFILE + " text, " + 
				CCH_SW_ROUTINE_TODO_PLAN + " text, " + 
				CCH_SW_ROUTINE_TODO_YEAR + " int, " + 
				CCH_SW_ROUTINE_TODO_MONTH + " int, " + 
				CCH_SW_ROUTINE_TODO_DAY + " int, " + 
				CCH_SW_ROUTINE_TODO_TOD + " text default '', " + 
				CCH_SW_ROUTINE_TODO_ACTION + " text default '', " + 
				CCH_SW_ROUTINE_TODO_TIMEDONE + " text default '', " + 
				CCH_SW_ROUTINE_TODO_LOGGED + " integer default 0, " + 
				CCH_SW_ROUTINE_TODO_ORDER + " integer default 0)" ; 
		db.execSQL(l_sql);    	
	}
	

	public void updateSWInfo(String field, String value) {
		SQLiteDatabase db = this.getWritableDatabase(); 
        ContentValues values = new ContentValues();
        values.put(field, value); 
        Log.e("CCH","Updating "+field+" with "+value);
		String userid = "David";//prefs.getString(ctx.getString(R.string.prefs_username), "noid");      
        db.update(CCH_SW_TABLE, values, CCH_SW_STAFF_ID + "='"+userid+"'", null);
        db.close(); // Closing database connection      
	}
	
	public void updateSWRoutineTodoInfo(String field, String value, String uuid) {
		SQLiteDatabase db = this.getWritableDatabase(); 
        ContentValues values = new ContentValues();
        values.put(field, value); 
        Log.e("CCH","Updating "+field+" with "+value);
		String userid = "David";//prefs.getString(ctx.getString(R.string.prefs_username), "noid");      
        db.update(CCH_SW_ROUTINE_TODO_TABLE, values, CCH_SW_ROUTINE_TODO_STAFF_ID + "='"+userid+"' AND "+CCH_SW_ROUTINE_TODO_ID+'='+uuid, null);
        db.close(); // Closing database connection      
	}
	
	public String getSWInfo(String field) 
	{			
		String userid = "David"; //prefs.getString(ctx.getString(R.string.prefs_username), "noid");      

		SQLiteDatabase db = this.getReadableDatabase();		 
		Cursor cursor = db.query(CCH_SW_TABLE, new String[] { field }, CCH_SW_STAFF_ID + "=?",
			            new String[] { String.valueOf(userid) }, null, null, null, null); 	        
		
		if (cursor == null || cursor.getCount()==0) {
			Log.e("CCH DB","Value for "+field+" is null or no result");	
			return null;	    	
		}
		
		cursor.moveToFirst();
		
		Log.e("CCH DB","Value for "+field+": "+cursor.getString(0));	
			    
		// return value
		return cursor.getString(0);
	}

	public String getSWRoutineTodoInfo(String field) 
	{			
			String userid = "David"; //prefs.getString(ctx.getString(R.string.prefs_username), "noid");      

			SQLiteDatabase db = this.getReadableDatabase();		 
			Cursor cursor = db.query(CCH_SW_ROUTINE_TODO_TABLE, new String[] { field }, CCH_SW_ROUTINE_TODO_STAFF_ID + "=?",
				            new String[] { String.valueOf(userid) }, null, null, null, null); 	        
			
			if (cursor == null || cursor.getCount()==0) {
				Log.e("CCH DB","Value for "+field+" is null or no result");	
				return null;	    	
			}
			
			cursor.moveToFirst();
			
			Log.e("CCH DB","Value for "+field+": "+cursor.getString(0));	
				    
			// return value
			return cursor.getString(0);
	}
		
	/** User tables **/
	public void createUserTable(SQLiteDatabase db){
		String l_sql = "create table if not exists " + CCH_USER_TABLE + " (" + 
				CCH_USER_ID + " integer primary key autoincrement, " + 
				CCH_STAFF_ID + " text, " + 
				CCH_USER_PASSWORD + " text, " + 
				CCH_USER_APIKEY + " text default '', " + 
				CCH_USER_FIRSTNAME + " text default '', " + 
				CCH_USER_LASTNAME + " text default '', " + 
				CCH_USER_POINTS + " integer default 0, " + 
				CCH_USER_BADGES + " integer default 0, " + 
				CCH_USER_SCORING + " integer default 0)";
		db.execSQL(l_sql);
	}
	
	public void addUser(User u) 
	{	
		if (checkUserExists(u)==null)
		{
			SQLiteDatabase db = this.getWritableDatabase(); 
        	ContentValues values = new ContentValues();
        	values.put(CCH_STAFF_ID, u.getUsername()); // StaffId
        	values.put(CCH_USER_PASSWORD, u.getPassword()); // Password  
            values.put(CCH_USER_APIKEY, u.getApi_key());
            values.put(CCH_USER_FIRSTNAME, u.getFirstname());
            values.put(CCH_USER_LASTNAME, u.getLastname());
            values.put(CCH_USER_POINTS, u.getPoints());
            values.put(CCH_USER_BADGES, u.getBadges());
            values.put(CCH_USER_SCORING, (u.isScoringEnabled() ? 1:0));
        	db.insert(CCH_USER_TABLE, null, values);
        	//db.close(); // Closing database connection
		}
    }
	
	
	
	public void updateUser(User u) 
	{
        SQLiteDatabase db = this.getWritableDatabase(); 
        ContentValues values = new ContentValues();
        values.put(CCH_STAFF_ID, u.getUsername()); // StaffId
        values.put(CCH_USER_PASSWORD, u.getPassword()); // Password 
        values.put(CCH_USER_APIKEY, u.getApi_key());
        values.put(CCH_USER_FIRSTNAME, u.getFirstname());
        values.put(CCH_USER_LASTNAME, u.getLastname());
        values.put(CCH_USER_POINTS, u.getPoints());
        values.put(CCH_USER_BADGES, u.getBadges());
        values.put(CCH_USER_SCORING, (u.isScoringEnabled() ? 1:0));
           
        // Inserting Row        
        db.update(CCH_USER_TABLE, values, CCH_STAFF_ID + "="+u.getUsername()+"'", null);
       // db.close(); // Closing database connection        
    }
	
	public void updateUser(String staff_id, String apikey, String firstname, String lastname, int points, int badges, boolean scoring) 
	{
        SQLiteDatabase db = this.getWritableDatabase(); 
        ContentValues values = new ContentValues();
        values.put(CCH_USER_APIKEY, apikey);
        values.put(CCH_USER_FIRSTNAME, firstname);
        values.put(CCH_USER_LASTNAME, lastname);
        values.put(CCH_USER_POINTS, points);
        values.put(CCH_USER_BADGES, badges);
        values.put(CCH_USER_SCORING, scoring);
           
        // Inserting Row        
        db.update(CCH_USER_TABLE, values, CCH_STAFF_ID + "=" + staff_id, null);
       // db.close(); // Closing database connection        
    }
	
    // check if User exists
	public User checkUserExists(User u) 
	{			
			SQLiteDatabase db = this.getReadableDatabase();		 
		    Cursor cursor = db.query(CCH_USER_TABLE, new String[] { CCH_USER_ID, CCH_STAFF_ID, CCH_USER_PASSWORD, CCH_USER_APIKEY, CCH_USER_FIRSTNAME, CCH_USER_LASTNAME, CCH_USER_POINTS, CCH_USER_BADGES, CCH_USER_SCORING }, CCH_STAFF_ID + "=?",
		            new String[] { String.valueOf(u.getUsername()) }, null, null, null, null); 	        
		   if (cursor == null || cursor.getCount()==0)
			    return null;	    	
		        cursor.moveToFirst();
		    
		   // load preferences
		   u.setApi_key(cursor.getString(3));
		   u.setFirstname(cursor.getString(4));
		   u.setLastname(cursor.getString(5));
		   u.setPoints(cursor.getInt(6));
		   u.setBadges(cursor.getInt(7));   
		   u.setScoringEnabled((cursor.getInt(8)==1 ? true: false));
		        
		   if (!(u.getPassword().equals(cursor.getString(2)))) {
			   u.setPasswordRight(false);	   
		   } 
		   
		   return u;
   }
	
	public boolean updateEventTarget(String sync_status, long id){
		    
	        String updateQuery = "Update "+EVENTS_SET_TABLE+" set "+
	        					COL_SYNC_STATUS+" = '"+ sync_status +"'"+
	        					" where "+BaseColumns._ID+" = "+id;
	        db.execSQL(updateQuery);
		return true;
		
	}
	public boolean updateCoverageTarget(String sync_status, long id){
	    
        String updateQuery = "Update "+COVERAGE_SET_TABLE+" set "+
        					COL_SYNC_STATUS+" = '"+ sync_status +"'"+
        					" where "+BaseColumns._ID+" = "+id;
        db.execSQL(updateQuery);
	return true;
}
	
public boolean updateOtherTarget(String sync_status, long id){
	    
        String updateQuery = "Update "+OTHER_TABLE+" set "+
        					COL_SYNC_STATUS+" = '"+ sync_status +"'"+
        					" where "+BaseColumns._ID+" = "+id;
        db.execSQL(updateQuery);
	return true;	
}

public boolean updateLearningTarget(String sync_status, long id){
    
    String updateQuery = "Update "+LEARNING_TABLE+" set "+
    					COL_SYNC_STATUS+" = '"+ sync_status +"'"+
    					" where "+BaseColumns._ID+" = "+id;
    db.execSQL(updateQuery);
return true;	
}


public HashMap<String,String> getAllOther(String month,String due_date){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_OTHER_CATEGORY
             +","+COL_OTHER_NUMBER
             +","+COL_OTHER_DUE_DATE
             +" from "+OTHER_TABLE
             +" where "+COL_OTHER_DUE_DATE
             + " = '"+due_date+"'"
             +" and "+COL_OTHER_MONTH
             +" = '"+month+"'"
             +" and "+COL_SYNC_STATUS
             + " = '"+"new_record"+"'";	
	 
	System.out.println(strQuery);
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("other_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("other_name", c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
		list.put("other_number",  c.getString(c.getColumnIndex(COL_OTHER_NUMBER)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_OTHER_DUE_DATE)));
		c.moveToNext();						
	}
	c.close();
	return list;
}

public HashMap<String,String> getAllMonthlyOthers(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_OTHER_CATEGORY
             +","+COL_OTHER_NUMBER
             +","+COL_OTHER_DUE_DATE
             +" from "+OTHER_TABLE
             +" where "+COL_OTHER_PERIOD
             + " = '"+"Monthly"+"'"
             +" and "+COL_OTHER_MONTH
             +" = '"+month+"'"
             +" and "+COL_SYNC_STATUS
             + " = '"+"new_record"+"'";	
	 
	System.out.println(strQuery);
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("other_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("other_name", c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
		list.put("other_number",  c.getString(c.getColumnIndex(COL_OTHER_NUMBER)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_OTHER_DUE_DATE)));
		c.moveToNext();						
	}
	c.close();
	return list;
}

public HashMap<String,String> getAllWeeklyOther(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	String strQuery="select "+BaseColumns._ID
             +","+COL_OTHER_CATEGORY
             +","+COL_OTHER_NUMBER
             +","+COL_OTHER_DUE_DATE
             +" from "+OTHER_TABLE
             +" where "+COL_OTHER_PERIOD
             + " = '"+"Weekly"+"'"
             +" and "+COL_OTHER_MONTH
             +" = '"+month+"'"
             +" and "+COL_SYNC_STATUS
             + " = '"+"new_record"+"'";	
	 
	System.out.println(strQuery);
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("other_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("other_name", c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
		list.put("other_number",  c.getString(c.getColumnIndex(COL_OTHER_NUMBER)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_OTHER_DUE_DATE)));
		c.moveToNext();						
	}
	c.close();
	return list;
}
public HashMap<String,String> getAllYearlyOther(String year){
	HashMap<String,String> list=new HashMap<String,String>();
	String strQuery="select "+BaseColumns._ID
             +","+COL_OTHER_CATEGORY
             +","+COL_OTHER_NUMBER
             +","+COL_OTHER_DUE_DATE
             +" from "+OTHER_TABLE
             +" where "+COL_OTHER_PERIOD
             + " = '"+"Yearly"+"'"
             +" = '"+year+"'"
             +" and "+COL_SYNC_STATUS
             + " = '"+"new_record"+"'";	
	 
	System.out.println(strQuery);
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("other_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("other_name", c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
		list.put("other_number",  c.getString(c.getColumnIndex(COL_OTHER_NUMBER)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_OTHER_DUE_DATE)));
		c.moveToNext();						
	}
	c.close();
	return list;
}


public HashMap<String,String> getAllCoverage(String month,String due_date){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_COVERAGE_SET_CATEGORY_NAME
             +","+COL_COVERAGE_NUMBER
             +","+COL_COVERAGE_DUE_DATE
             +" from "+COVERAGE_SET_TABLE
             +" where "+COL_COVERAGE_DUE_DATE
             + " = '"+due_date+"'"
             +" and "+COL_COVERAGE_MONTH
             +" = '"+month+"'"
             +" and "+COL_SYNC_STATUS
             + " = '"+"new_record"+"'";	
	 
	System.out.println(strQuery);
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("coverage_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("coverage_name", c.getString(c.getColumnIndex(COL_COVERAGE_SET_CATEGORY_NAME)));
		list.put("coverage_number",  c.getString(c.getColumnIndex(COL_COVERAGE_NUMBER)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_COVERAGE_DUE_DATE)));
		c.moveToNext();						
	}
	c.close();
	return list;
}

public HashMap<String,String> getAllMonthlyCoverage(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_COVERAGE_SET_CATEGORY_NAME
             +","+COL_COVERAGE_NUMBER
             +","+COL_COVERAGE_DUE_DATE
             +" from "+COVERAGE_SET_TABLE
             +" where "+COL_COVERAGE_SET_PERIOD
             + " = '"+"Monthly"+"'"
             +" and "+COL_COVERAGE_MONTH
             +" = '"+month+"'"
             +" and "+COL_SYNC_STATUS
             + " = '"+"new_record"+"'";	
	 
	System.out.println(strQuery);
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("coverage_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("coverage_name", c.getString(c.getColumnIndex(COL_COVERAGE_SET_CATEGORY_NAME)));
		list.put("coverage_number",  c.getString(c.getColumnIndex(COL_COVERAGE_NUMBER)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_COVERAGE_DUE_DATE)));
		c.moveToNext();						
	}
	c.close();
	return list;
}

public HashMap<String,String> getAllWeeklyCoverage(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_COVERAGE_SET_CATEGORY_NAME
             +","+COL_COVERAGE_NUMBER
             +","+COL_COVERAGE_DUE_DATE
             +" from "+COVERAGE_SET_TABLE
             +" where "+COL_COVERAGE_SET_PERIOD
             + " = '"+"Weekly"+"'"
             +" and "+COL_COVERAGE_MONTH
             +" = '"+month+"'"
             +" and "+COL_SYNC_STATUS
             + " = '"+"new_record"+"'";	
	 
	System.out.println(strQuery);
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("coverage_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("coverage_name", c.getString(c.getColumnIndex(COL_COVERAGE_SET_CATEGORY_NAME)));
		list.put("coverage_number",  c.getString(c.getColumnIndex(COL_COVERAGE_NUMBER)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_COVERAGE_DUE_DATE)));
		c.moveToNext();						
	}
	c.close();
	return list;
}
public HashMap<String,String> getAllYearlyCoverage(){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_COVERAGE_SET_CATEGORY_NAME
             +","+COL_COVERAGE_NUMBER
             +","+COL_COVERAGE_DUE_DATE
             +" from "+COVERAGE_SET_TABLE
             +" where "+COL_COVERAGE_SET_PERIOD
             + " = '"+"Yearly"+"'"
             +" and "+COL_SYNC_STATUS
             + " = '"+"new_record"+"'";	
	 
	System.out.println(strQuery);
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("coverage_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("coverage_name", c.getString(c.getColumnIndex(COL_COVERAGE_SET_CATEGORY_NAME)));
		list.put("coverage_number",  c.getString(c.getColumnIndex(COL_COVERAGE_NUMBER)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_COVERAGE_DUE_DATE)));
		c.moveToNext();						
	}
	c.close();
	return list;
}

public HashMap<String,String> getAllEvents(String month,String due_date){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_EVENT_SET_NAME
             +","+COL_EVENT_NUMBER
             +","+COL_EVENT_DUE_DATE
             +" from "+EVENTS_SET_TABLE
             +" where "+COL_EVENT_DUE_DATE
             + " = '"+due_date+"'"
             +" and "+COL_EVENT_MONTH
             +" = '"+month+"'"
             +" and "+COL_SYNC_STATUS
             + " = '"+"new_record"+"'";	
	 
	System.out.println(strQuery);
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("event_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("event_name", c.getString(c.getColumnIndex(COL_EVENT_SET_NAME)));
		list.put("event_number",  c.getString(c.getColumnIndex(COL_EVENT_NUMBER)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_EVENT_DUE_DATE)));
		c.moveToNext();	
		System.out.println(list);
	}
	c.close();
	return list;
}
public HashMap<String,String> getAllMonthlyEvents(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_EVENT_SET_NAME
             +","+COL_EVENT_NUMBER
             +","+COL_EVENT_DUE_DATE
             +" from "+EVENTS_SET_TABLE
             +" where "+COL_EVENT_PERIOD
             + " = '"+"Monthly"+"'"
             +" and "+COL_EVENT_MONTH
             +" = '"+month+"'"
             +" and "+COL_SYNC_STATUS
             + " = '"+"new_record"+"'";	
	 
	System.out.println(strQuery);
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("event_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("event_name", c.getString(c.getColumnIndex(COL_EVENT_SET_NAME)));
		list.put("event_number",  c.getString(c.getColumnIndex(COL_EVENT_NUMBER)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_EVENT_DUE_DATE)));
		c.moveToNext();	
		System.out.println(list);
	}
	c.close();
	return list;
}

	public HashMap<String,String> getAllWeeklyEvents(String month){
		HashMap<String,String> list=new HashMap<String,String>();
		 String strQuery="select "+BaseColumns._ID
	             +","+COL_EVENT_SET_NAME
	             +","+COL_EVENT_NUMBER
	             +","+COL_EVENT_DUE_DATE
	             +" from "+EVENTS_SET_TABLE
	             +" where "+COL_EVENT_PERIOD
	             + " = '"+"Weekly"+"'"
	             +" and "+COL_EVENT_MONTH
	             +" = '"+month+"'"
	             +" and "+COL_SYNC_STATUS
	             + " = '"+"new_record"+"'";	
		 
		System.out.println("Weekly events "+strQuery);
		Cursor c=read.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			list.put("event_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
			list.put("event_name", c.getString(c.getColumnIndex(COL_EVENT_SET_NAME)));
			list.put("event_number",  c.getString(c.getColumnIndex(COL_EVENT_NUMBER)));
			list.put("due_date",  c.getString(c.getColumnIndex(COL_EVENT_DUE_DATE)));
			c.moveToNext();	
			System.out.println("Weekly events"+list);
		}
		c.close();
		return list;
	}
	
	public HashMap<String,String> getAllYearlyEvents(String month){
		HashMap<String,String> list=new HashMap<String,String>();
		 String strQuery="select "+BaseColumns._ID
	             +","+COL_EVENT_SET_NAME
	             +","+COL_EVENT_NUMBER
	             +","+COL_EVENT_DUE_DATE
	             +" from "+EVENTS_SET_TABLE
	             +" where "+COL_EVENT_PERIOD
	             + " = '"+"Annually"+"'"
	             +" and "+COL_EVENT_MONTH
	             +" = '"+month+"'"
	             +" and "+COL_SYNC_STATUS
	             + " = '"+"new_record"+"'";	
		 
		System.out.println(strQuery);
		Cursor c=read.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			list.put("event_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
			list.put("event_name", c.getString(c.getColumnIndex(COL_EVENT_SET_NAME)));
			list.put("event_number",  c.getString(c.getColumnIndex(COL_EVENT_NUMBER)));
			list.put("due_date",  c.getString(c.getColumnIndex(COL_EVENT_DUE_DATE)));
			c.moveToNext();						
		}
		c.close();
		return list;
	
}


	public boolean editEventCategory(String event_category,String event_number,String event_period, String due_date,long id){
		    
	        String updateQuery = "Update "+EVENTS_SET_TABLE+" set "+
	        					COL_EVENT_SET_NAME+" = '"+ event_category +"'"+
	        					","+COL_EVENT_NUMBER+" = '"+event_number+"'"+
	        					","+COL_EVENT_PERIOD+" = '"+event_period+"'"+
	        					","+COL_EVENT_DUE_DATE+" = '"+due_date+"'"+
	        					" where "+BaseColumns._ID+" = "+id;
	        db.execSQL(updateQuery);
		return true;
		
	}
	
	public boolean deleteEventCategory(long id){
		    
		 String deleteQuery="Delete from "+EVENTS_SET_TABLE+" where "+
				 			BaseColumns._ID+" = "+ id;
		 System.out.println(deleteQuery);
	        db.execSQL(deleteQuery);
		return true;
		
	}
	
	public boolean deleteCoverageCategory(long id){

		 String deleteQuery="Delete from "+COVERAGE_SET_TABLE+" where "+
				 			BaseColumns._ID+" = "+ id;
		 System.out.println(deleteQuery);
	       db.execSQL(deleteQuery);
		return true;
		
	}
	
	public boolean deleteLearningCategory(long id){
	    
		 String deleteQuery="Delete from "+LEARNING_TABLE+" where "+
				 			BaseColumns._ID+" = "+ id;
		 System.out.println(deleteQuery);
	      db.execSQL(deleteQuery);
		return true;
		
	}
	public boolean deleteOtherCategory(long id){
	    
		 String deleteQuery="Delete from "+OTHER_TABLE+" where "+
				 			BaseColumns._ID+" = "+ id;
		 System.out.println(deleteQuery);
	      db.execSQL(deleteQuery);
		return true;
		
	}
	public boolean editCoverage(String coverage_category,String coverage_number,String coverage_period, String due_date,long id){
		    
	        String updateQuery = "Update "+COVERAGE_SET_TABLE+" set "+
	        					COL_COVERAGE_SET_CATEGORY_NAME+" = '"+ coverage_category +"'"+
	        					","+COL_COVERAGE_NUMBER+" = '"+coverage_number+"'"+
	        					","+COL_COVERAGE_SET_PERIOD+" = '"+coverage_period+"'"+
	        					","+COL_COVERAGE_DUE_DATE+" = '"+due_date+"'"+
	        					" where "+BaseColumns._ID+" = "+id;
	        db.execSQL(updateQuery);
		return true;
		
	}
	
	public boolean editLearning(String learning_category,String learning_description,String topic,String due_date, long id){
		    
	        String updateQuery = "Update "+LEARNING_TABLE+" set "+
	        					COL_LEARNING_CATEGORY+" = '"+ learning_category +"'"+
	        					","+COL_LEARNING_DESCRIPTION+" = '"+learning_description+"'"+
	        					","+COL_LEARNING_TOPIC+" = '"+topic+"'"+
	        					","+COL_LEARNING_DUE_DATE+" = '"+due_date+"'"+
	        					" where "+BaseColumns._ID+" = "+id;
	        db.execSQL(updateQuery);
		return true;
	}
	
	public boolean editOther(String other_category,String other_number,String other_period, String due_date,long id){
		    
	        String updateQuery = "Update "+OTHER_TABLE+" set "+
	        					COL_OTHER_CATEGORY+" = '"+ other_category +"'"+
	        					","+COL_OTHER_NUMBER+" = '"+ other_number +"'"+
	        					","+COL_OTHER_PERIOD+" = '"+other_period+"'"+
	        					","+COL_OTHER_DUE_DATE+" = '"+due_date+"'"+
	        					" where "+BaseColumns._ID+" = "+id;
	        db.execSQL(updateQuery);
		return true;	
	}

	private void insertDefaultRoutineValues() {
	
		/*ContentValues values = new ContentValues();
		values.put(CCH_SW_LEGAL_STATUS, "");
		values.put(CCH_SW_PROFILE_STATUS, "");
		values.put(CCH_SW_PROFILE_RESPONSES, "");
		values.put(CCH_SW_MONTH_PLAN, "");
		values.put(CCH_SW_MONTH_PLAN_LASTUPDATE, "");
		db.insertOrThrow(CCH_SW_TABLE, null, values);
		
		l_sql = "create table if not exists " + CCH_SW_ROUTINE_TABLE + " (" + 
				CCH_SW_ROUTINE_ID + " integer primary key autoincrement, " + 
				CCH_SW_ROUTINE_TOD + " text, " + 
				CCH_SW_ROUTINE_PROFILE + " text, " + 
				CCH_SW_ROUTINE_PLAN + " text, " + 
				CCH_SW_ROUTINE_ACTION + " text default '', " + 
				CCH_SW_ROUTINE_ORDER + " integer default 0)" ; 
		db.execSQL(l_sql);
		*/
	}
	

public HashMap<String,String> getAllUnupdatedDailyOther(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_OTHER_CATEGORY
             +","+COL_OTHER_NUMBER
             +","+COL_OTHER_DUE_DATE
             +","+COL_OTHER_PERIOD
             +","+COL_SYNC_STATUS
             +" from "+OTHER_TABLE
             +" where "+COL_OTHER_PERIOD
             + " = '"+"Daily"+"'"
             +" and "+COL_OTHER_MONTH
             +" = '"+month+"'";	
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("other_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("other_name", c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
		list.put("other_number",  c.getString(c.getColumnIndex(COL_OTHER_NUMBER)));
		list.put("other_period",  c.getString(c.getColumnIndex(COL_OTHER_PERIOD)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_OTHER_DUE_DATE)));
		list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
		c.moveToNext();						
	}
	c.close();
	return list;
}

public HashMap<String,String> getAllUnupdatedMonthlyOthers(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_OTHER_CATEGORY
             +","+COL_OTHER_NUMBER
             +","+COL_OTHER_DUE_DATE
             +","+COL_OTHER_PERIOD
             +","+COL_SYNC_STATUS
             +" from "+OTHER_TABLE
             +" where "+COL_OTHER_PERIOD
             + " = '"+"Monthly"+"'"
             +" and "+COL_OTHER_MONTH
             +" = '"+month+"'";	
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("other_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("other_name", c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
		list.put("other_number",  c.getString(c.getColumnIndex(COL_OTHER_NUMBER)));
		list.put("other_period",  c.getString(c.getColumnIndex(COL_OTHER_PERIOD)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_OTHER_DUE_DATE)));
		list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
		c.moveToNext();						
	}
	c.close();
	return list;
}

public HashMap<String,String> getAllUpupdatedWeeklyOther(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	String strQuery="select "+BaseColumns._ID
             +","+COL_OTHER_CATEGORY
             +","+COL_OTHER_NUMBER
             +","+COL_OTHER_DUE_DATE
             +","+COL_OTHER_PERIOD
             +","+COL_SYNC_STATUS
             +" from "+OTHER_TABLE
             +" where "+COL_OTHER_PERIOD
             + " = '"+"Weekly"+"'"
             +" and "+COL_OTHER_MONTH
             +" = '"+month+"'";	
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("other_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("other_name", c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
		list.put("other_number",  c.getString(c.getColumnIndex(COL_OTHER_NUMBER)));
		list.put("other_period",  c.getString(c.getColumnIndex(COL_OTHER_PERIOD)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_OTHER_DUE_DATE)));
		list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
		c.moveToNext();						
	}
	c.close();
	return list;
}
public HashMap<String,String> getAllUnupdatedYearlyOther(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	String strQuery="select "+BaseColumns._ID
             +","+COL_OTHER_CATEGORY
             +","+COL_OTHER_NUMBER
             +","+COL_OTHER_DUE_DATE
             +","+COL_OTHER_PERIOD
             +","+COL_SYNC_STATUS
             +" from "+OTHER_TABLE
             +" where "+COL_OTHER_PERIOD
             + " = '"+"Annually"+"'"
             +" = '"+month+"'";	
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("other_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("other_name", c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
		list.put("other_number",  c.getString(c.getColumnIndex(COL_OTHER_NUMBER)));
		list.put("other_period",  c.getString(c.getColumnIndex(COL_OTHER_PERIOD)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_OTHER_DUE_DATE)));
		list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
		c.moveToNext();						
	}
	c.close();
	return list;
}
public HashMap<String,String> getAllUnupdatedMidYearOther(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	String strQuery="select "+BaseColumns._ID
             +","+COL_OTHER_CATEGORY
             +","+COL_OTHER_NUMBER
             +","+COL_OTHER_DUE_DATE
             +","+COL_OTHER_PERIOD
             +","+COL_SYNC_STATUS
             +" from "+OTHER_TABLE
             +" where "+COL_OTHER_PERIOD
             + " = '"+"Mid-year"+"'"
             +" = '"+month+"'";	
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("other_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("other_name", c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
		list.put("other_number",  c.getString(c.getColumnIndex(COL_OTHER_NUMBER)));
		list.put("other_period",  c.getString(c.getColumnIndex(COL_OTHER_PERIOD)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_OTHER_DUE_DATE)));
		list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
		c.moveToNext();						
	}
	c.close();
	return list;
}


public HashMap<String,String> getAllUnupdatedDailyCoverage(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_COVERAGE_SET_CATEGORY_NAME
             +","+COL_COVERAGE_NUMBER
             +","+COL_COVERAGE_DUE_DATE
             +","+COL_COVERAGE_SET_PERIOD
             +","+COL_SYNC_STATUS
             +" from "+COVERAGE_SET_TABLE
             +" where "+COL_COVERAGE_SET_PERIOD
             + " = '"+"Daily"+"'"
             +" and "+COL_COVERAGE_MONTH
             +" = '"+month+"'";	
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("coverage_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("coverage_name", c.getString(c.getColumnIndex(COL_COVERAGE_SET_CATEGORY_NAME)));
		list.put("coverage_number",  c.getString(c.getColumnIndex(COL_COVERAGE_NUMBER)));
		list.put("coverage_period",  c.getString(c.getColumnIndex(COL_COVERAGE_SET_PERIOD)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_COVERAGE_DUE_DATE)));
		list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
		c.moveToNext();						
	}
	c.close();
	return list;
}

public HashMap<String,String> getAllUnupdatedMonthlyCoverage(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_COVERAGE_SET_CATEGORY_NAME
             +","+COL_COVERAGE_NUMBER
             +","+COL_COVERAGE_DUE_DATE
             +","+COL_COVERAGE_SET_PERIOD
             +","+COL_SYNC_STATUS
             +" from "+COVERAGE_SET_TABLE
             +" where "+COL_COVERAGE_SET_PERIOD
             + " = '"+"Monthly"+"'"
             +" and "+COL_COVERAGE_MONTH
             +" = '"+month+"'";	
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("coverage_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("coverage_name", c.getString(c.getColumnIndex(COL_COVERAGE_SET_CATEGORY_NAME)));
		list.put("coverage_number",  c.getString(c.getColumnIndex(COL_COVERAGE_NUMBER)));
		list.put("coverage_period",  c.getString(c.getColumnIndex(COL_COVERAGE_SET_PERIOD)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_COVERAGE_DUE_DATE)));
		list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
		c.moveToNext();						
	}
	c.close();
	return list;
}

public HashMap<String,String> getAllUnupdatedWeeklyCoverage(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_COVERAGE_SET_CATEGORY_NAME
             +","+COL_COVERAGE_NUMBER
             +","+COL_COVERAGE_DUE_DATE
             +","+COL_COVERAGE_SET_PERIOD
             +","+COL_SYNC_STATUS
             +" from "+COVERAGE_SET_TABLE
             +" where "+COL_COVERAGE_SET_PERIOD
             + " = '"+"Weekly"+"'"
             +" and "+COL_COVERAGE_MONTH
             +" = '"+month+"'";	
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("coverage_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("coverage_name", c.getString(c.getColumnIndex(COL_COVERAGE_SET_CATEGORY_NAME)));
		list.put("coverage_number",  c.getString(c.getColumnIndex(COL_COVERAGE_NUMBER)));
		list.put("coverage_period",  c.getString(c.getColumnIndex(COL_COVERAGE_SET_PERIOD)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_COVERAGE_DUE_DATE)));
		list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
		c.moveToNext();						
	}
	c.close();
	return list;
}
public HashMap<String,String> getAllUnupdatedYearlyCoverage(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_COVERAGE_SET_CATEGORY_NAME
             +","+COL_COVERAGE_NUMBER
             +","+COL_COVERAGE_DUE_DATE
             +","+COL_COVERAGE_SET_PERIOD
             +","+COL_SYNC_STATUS
             +" from "+COVERAGE_SET_TABLE
             +" where "+COL_COVERAGE_SET_PERIOD
             + " = '"+"Annually"+"'"
             +" and "+COL_COVERAGE_MONTH
             +" = '"+month+"'";	
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("coverage_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("coverage_name", c.getString(c.getColumnIndex(COL_COVERAGE_SET_CATEGORY_NAME)));
		list.put("coverage_number",  c.getString(c.getColumnIndex(COL_COVERAGE_NUMBER)));
		list.put("coverage_period",  c.getString(c.getColumnIndex(COL_COVERAGE_SET_PERIOD)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_COVERAGE_DUE_DATE)));
		list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
		c.moveToNext();						
	}
	c.close();
	return list;
}

public HashMap<String,String> getAllUnupdatedMidYearCoverage(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_COVERAGE_SET_CATEGORY_NAME
             +","+COL_COVERAGE_NUMBER
             +","+COL_COVERAGE_DUE_DATE
             +","+COL_COVERAGE_SET_PERIOD
             +","+COL_SYNC_STATUS
             +" from "+COVERAGE_SET_TABLE
             +" where "+COL_COVERAGE_SET_PERIOD
             + " = '"+"Mid-year"+"'"
             +" and "+COL_COVERAGE_MONTH
             +" = '"+month+"'";	
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("coverage_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("coverage_name", c.getString(c.getColumnIndex(COL_COVERAGE_SET_CATEGORY_NAME)));
		list.put("coverage_number",  c.getString(c.getColumnIndex(COL_COVERAGE_NUMBER)));
		list.put("coverage_period",  c.getString(c.getColumnIndex(COL_COVERAGE_SET_PERIOD)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_COVERAGE_DUE_DATE)));
		list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
		c.moveToNext();						
	}
	c.close();
	return list;
}

public HashMap<String,String> getAllUnupdatedLearning(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
             +","+COL_LEARNING_CATEGORY
             +","+COL_LEARNING_DESCRIPTION
             +","+COL_LEARNING_TOPIC
             +","+COL_LEARNING_DUE_DATE
             +","+COL_LEARNING_MONTH
             +","+COL_SYNC_STATUS
             +" from "+LEARNING_TABLE
             +" where "+COL_LEARNING_MONTH
             + " = '"+"month"+"'";	
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("learning_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("learning_category", c.getString(c.getColumnIndex(COL_LEARNING_CATEGORY)));
		list.put("learning_description",  c.getString(c.getColumnIndex(COL_LEARNING_DESCRIPTION)));
		list.put("learning_topic",  c.getString(c.getColumnIndex(COL_LEARNING_TOPIC)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_LEARNING_DUE_DATE)));
		list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
		c.moveToNext();	
		System.out.println(list);
	}
	c.close();
	return list;
}
public HashMap<String,String> getAllUnupdatedMonthlyLearning(String month){
	HashMap<String,String> list=new HashMap<String,String>();
	 String strQuery="select "+BaseColumns._ID
			 +","+COL_LEARNING_CATEGORY
             +","+COL_LEARNING_DESCRIPTION
             +","+COL_LEARNING_DUE_DATE
             +","+COL_LEARNING_MONTH
             +","+COL_SYNC_STATUS
             +" from "+LEARNING_TABLE
             +" where "+COL_LEARNING_MONTH
             + " = '"+"month"+"'";	
	 
	System.out.println(strQuery);
	Cursor c=read.rawQuery(strQuery, null);
	c.moveToFirst();
	while (c.isAfterLast()==false){
		list.put("learning_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
		list.put("learning_category", c.getString(c.getColumnIndex(COL_LEARNING_CATEGORY)));
		list.put("learning_description",  c.getString(c.getColumnIndex(COL_LEARNING_DESCRIPTION)));
		list.put("due_date",  c.getString(c.getColumnIndex(COL_LEARNING_DUE_DATE)));
		list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
		c.moveToNext();	
		System.out.println(list);
	}
	c.close();
	return list;
}

	public HashMap<String,String> getAllUnupdatedWeeklyLearning(String month){
		HashMap<String,String> list=new HashMap<String,String>();
		 String strQuery="select "+BaseColumns._ID
				 +","+COL_LEARNING_CATEGORY
	             +","+COL_LEARNING_DESCRIPTION
	             +","+COL_LEARNING_DUE_DATE
	             +","+COL_LEARNING_MONTH
	             +","+COL_SYNC_STATUS
	             +" from "+LEARNING_TABLE
	             +" where "+COL_LEARNING_MONTH
	             + " = '"+"month"+"'";	
		Cursor c=read.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			list.put("learning_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
			list.put("learning_category", c.getString(c.getColumnIndex(COL_LEARNING_CATEGORY)));
			list.put("learning_description",  c.getString(c.getColumnIndex(COL_LEARNING_DESCRIPTION)));
			list.put("due_date",  c.getString(c.getColumnIndex(COL_LEARNING_DUE_DATE)));
			list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
			c.moveToNext();	
			System.out.println("Weekly events"+list);
		}
		c.close();
		return list;
	}
	
	public HashMap<String,String> getAllUnupdatedYearlyLearning(String month){
		HashMap<String,String> list=new HashMap<String,String>();
		 String strQuery="select "+BaseColumns._ID
				 +","+COL_LEARNING_CATEGORY
	             +","+COL_LEARNING_DESCRIPTION
	             +","+COL_LEARNING_DUE_DATE
	             +","+COL_LEARNING_MONTH
	             +","+COL_SYNC_STATUS
	             +" from "+LEARNING_TABLE
	             +" where "+COL_LEARNING_MONTH
	             + " = '"+"month"+"'";	
		Cursor c=read.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			list.put("learning_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
			list.put("learning_category", c.getString(c.getColumnIndex(COL_LEARNING_CATEGORY)));
			list.put("learning_description",  c.getString(c.getColumnIndex(COL_LEARNING_DESCRIPTION)));
			list.put("due_date",  c.getString(c.getColumnIndex(COL_LEARNING_DUE_DATE)));
			list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
			c.moveToNext();						
		}
		c.close();
		return list;
	
}

	public HashMap<String,String> getAllUnupdatedMidyearLearning(String month){
		HashMap<String,String> list=new HashMap<String,String>();
		 String strQuery="select "+BaseColumns._ID
				 +","+COL_LEARNING_CATEGORY
	             +","+COL_LEARNING_DESCRIPTION
	             +","+COL_LEARNING_DUE_DATE
	             +","+COL_LEARNING_MONTH
	             +","+COL_SYNC_STATUS
	             +" from "+LEARNING_TABLE
	             +" where "+COL_LEARNING_MONTH
	             + " = '"+"month"+"'";	
		Cursor c=read.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			list.put("learning_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
			list.put("learning_category", c.getString(c.getColumnIndex(COL_LEARNING_CATEGORY)));
			list.put("learning_description",  c.getString(c.getColumnIndex(COL_LEARNING_DESCRIPTION)));
			list.put("due_date",  c.getString(c.getColumnIndex(COL_LEARNING_DUE_DATE)));
			list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
			c.moveToNext();						
		}
		c.close();
		return list;
	
}

	
	public HashMap<String,String> getAllUnupdatedDailyEvents(String month){
		HashMap<String,String> list=new HashMap<String,String>();
		 String strQuery="select "+BaseColumns._ID
	             +","+COL_EVENT_SET_NAME
	             +","+COL_EVENT_NUMBER
	             +","+COL_EVENT_DUE_DATE
	             +","+COL_EVENT_PERIOD
	             +","+COL_SYNC_STATUS
	             +" from "+EVENTS_SET_TABLE
	             +" where "+COL_EVENT_PERIOD
	             + " = '"+"Daily"+"'"
	             +" and "+COL_EVENT_MONTH
	             +" = '"+month+"'";	
		Cursor c=read.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			list.put("event_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
			list.put("event_name", c.getString(c.getColumnIndex(COL_EVENT_SET_NAME)));
			list.put("event_number",  c.getString(c.getColumnIndex(COL_EVENT_NUMBER)));
			list.put("event_period",  c.getString(c.getColumnIndex(COL_EVENT_PERIOD)));
			list.put("due_date",  c.getString(c.getColumnIndex(COL_EVENT_DUE_DATE)));
			list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
			c.moveToNext();	
			System.out.println(list);
		}
		c.close();
		return list;
	}
	public HashMap<String,String> getAllUnupdatedMonthlyEvents(String month){
		HashMap<String,String> list=new HashMap<String,String>();
		 String strQuery="select "+BaseColumns._ID
	             +","+COL_EVENT_SET_NAME
	             +","+COL_EVENT_NUMBER
	             +","+COL_EVENT_PERIOD
	             +","+COL_EVENT_DUE_DATE
	             +","+COL_SYNC_STATUS
	             +" from "+EVENTS_SET_TABLE
	             +" where "+COL_EVENT_PERIOD
	             + " = '"+"Monthly"+"'"
	             +" and "+COL_EVENT_MONTH
	             +" = '"+month+"'";	
		Cursor c=read.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			list.put("event_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
			list.put("event_name", c.getString(c.getColumnIndex(COL_EVENT_SET_NAME)));
			list.put("event_number",  c.getString(c.getColumnIndex(COL_EVENT_NUMBER)));
			list.put("event_period",  c.getString(c.getColumnIndex(COL_EVENT_PERIOD)));
			list.put("due_date",  c.getString(c.getColumnIndex(COL_EVENT_DUE_DATE)));
			list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
			c.moveToNext();	
			System.out.println(list);
		}
		c.close();
		return list;
	}

		public HashMap<String,String> getAllUnupdatedWeeklyEvents(String month){
			HashMap<String,String> list=new HashMap<String,String>();
			 String strQuery="select "+BaseColumns._ID
		             +","+COL_EVENT_SET_NAME
		             +","+COL_EVENT_NUMBER
		             +","+COL_EVENT_PERIOD
		             +","+COL_EVENT_DUE_DATE
		             +","+COL_SYNC_STATUS
		             +" from "+EVENTS_SET_TABLE
		             +" where "+COL_EVENT_PERIOD
		             + " = '"+"Weekly"+"'"
		             +" and "+COL_EVENT_MONTH
		             +" = '"+month+"'";	
			Cursor c=read.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.put("event_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
				list.put("event_name", c.getString(c.getColumnIndex(COL_EVENT_SET_NAME)));
				list.put("event_number",  c.getString(c.getColumnIndex(COL_EVENT_NUMBER)));
				list.put("event_period",  c.getString(c.getColumnIndex(COL_EVENT_PERIOD)));
				list.put("due_date",  c.getString(c.getColumnIndex(COL_EVENT_DUE_DATE)));
				list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
				c.moveToNext();	
				
			}
			c.close();
			return list;
		}
		
		public HashMap<String,String> getAllUnupdatedYearlyEvents(String month){
			HashMap<String,String> list=new HashMap<String,String>();
			 String strQuery="select "+BaseColumns._ID
		             +","+COL_EVENT_SET_NAME
		             +","+COL_EVENT_NUMBER
		             +","+COL_EVENT_DUE_DATE
		             +","+COL_EVENT_PERIOD
		             +","+COL_SYNC_STATUS
		             +" from "+EVENTS_SET_TABLE
		             +" where "+COL_EVENT_PERIOD
		             + " = '"+"Annually"+"'"
		             +" and "+COL_EVENT_MONTH
		             +" = '"+month+"'";	
			Cursor c=read.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.put("event_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
				list.put("event_name", c.getString(c.getColumnIndex(COL_EVENT_SET_NAME)));
				list.put("event_number",  c.getString(c.getColumnIndex(COL_EVENT_NUMBER)));
				list.put("event_period",  c.getString(c.getColumnIndex(COL_EVENT_PERIOD)));
				list.put("due_date",  c.getString(c.getColumnIndex(COL_EVENT_DUE_DATE)));
				list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
				c.moveToNext();						
			}
			c.close();
			return list;
		
	}

		public HashMap<String,String> getAllUnupdatedMidyearEvents(String month){
			HashMap<String,String> list=new HashMap<String,String>();
			 String strQuery="select "+BaseColumns._ID
		             +","+COL_EVENT_SET_NAME
		             +","+COL_EVENT_NUMBER
		             +","+COL_EVENT_DUE_DATE
		             +","+COL_EVENT_PERIOD
		             +","+COL_SYNC_STATUS
		             +" from "+EVENTS_SET_TABLE
		             +" where "+COL_EVENT_PERIOD
		             + " = '"+"Mid-year"+"'"
		             +" and "+COL_EVENT_MONTH
		             +" = '"+month+"'";	
			Cursor c=read.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false){
				list.put("event_id",c.getString(c.getColumnIndex(BaseColumns._ID)));
				list.put("event_name", c.getString(c.getColumnIndex(COL_EVENT_SET_NAME)));
				list.put("event_number",  c.getString(c.getColumnIndex(COL_EVENT_NUMBER)));
				list.put("event_period",  c.getString(c.getColumnIndex(COL_EVENT_PERIOD)));
				list.put("due_date",  c.getString(c.getColumnIndex(COL_EVENT_DUE_DATE)));
				list.put("sync_status",  c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
				c.moveToNext();						
			}
			c.close();
			return list;
		
	}

}