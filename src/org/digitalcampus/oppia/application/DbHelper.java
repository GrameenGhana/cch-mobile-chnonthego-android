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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.model.Activity;
import org.digitalcampus.oppia.model.ActivitySchedule;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.model.TrackerLog;
import org.digitalcampus.oppia.model.User;
import org.digitalcampus.oppia.task.Payload;
import org.grameenfoundation.calendar.CalendarEvents.MyEvent;
import org.grameenfoundation.cch.model.CCHTrackerLog;
import org.grameenfoundation.cch.model.CourseAchievments;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.FacilityTargets;
import org.grameenfoundation.cch.model.MyCalendarEvents;
import org.grameenfoundation.cch.model.POCSections;
import org.grameenfoundation.cch.model.RoutineActivity;
import org.grameenfoundation.cch.model.Survey;
import org.grameenfoundation.cch.utils.CCHTimeUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.provider.CalendarContract.Events;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;

@SuppressLint("SimpleDateFormat")
public class DbHelper extends SQLiteOpenHelper {

	static final String TAG = DbHelper.class.getSimpleName();
	static final String DB_NAME = "mobilelearning.db";
	static final int DB_VERSION = 19;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
	private SharedPreferences prefs;
	private Context ctx;

	private static final String COURSE_TABLE = "Module";
	private static final String COURSES_TABLE = "courses";
	private static final String COURSE_C_ID = BaseColumns._ID;
	private static final String COURSE_C_VERSIONID = "versionid";
	private static final String COURSE_C_TITLE = "title";
	private static final String COURSE_C_SHORTNAME = "shortname";
	private static final String COURSE_C_LOCATION = "location";
	private static final String COURSE_C_SCHEDULE = "schedule";
	private static final String COURSE_C_IMAGE = "imagelink";
	private static final String COURSE_C_LANGS = "langs";
	private static final String COURSE_C_DATE = "date";
	private static final String COURSE_C_GROUP= "course_group";
	private String battery_percentage;
	private CCHTimeUtil timeUtil;
	
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
	private static final String QUIZRESULTS_C_TITLE = "title";
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
	private static final String CCH_USER_DISTRICT = "district";
	private static final String CCH_USER_SCORING = "scoring_enabled";
	private static final String CCH_USER_GROUP_MEMBERS="group_members";
	private static final String CCH_USER_USERNAME="username";
	private static final String CCH_USER_FACILITY="facility";
	private static final String CCH_USER_SUBDISTRICT="subdistrict";
	private static final String CCH_USER_ZONE="zone";
	
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
	private static final String CCH_SW_ROUTINE_TODO_ORDER  = "doorder";
	private static final String CCH_SW_ROUTINE_TODO_TIMEDONE  = "timedone";

	
	
	//CCH: Events Table
		public static final String EVENTS_SET_TABLE="event_set";
		public static final String COL_EVENT_SET_NAME="event_name";
		public static final String COL_EVENT_SET_DETAIL="event_detail";
		public static final String COL_EVENT_PERIOD="event_period";
		public static final String COL_EVENT_NUMBER="event_number";
		public static final String COL_EVENT_DURATION="duration";
		public static final String COL_START_DATE="start_date";
		public static final String COL_EVENT_DUE_DATE="due_date";
		public static final String COL_SYNC_STATUS ="sync_status";
		
		//CCH: Coverage Table
		public static final String COVERAGE_SET_TABLE="coverage_set";
		public static final String COL_COVERAGE_SET_CATEGORY_NAME="category_name";
		public static final String COL_COVERAGE_SET_CATEGORY_DETAIL="category_detail";
		public static final String COL_COVERAGE_SET_PERIOD="coverage_period";
		public static final String COL_COVERAGE_NUMBER="category_number";
		public static final String COL_COVERAGE_DUE_DATE="due_date";
		public static final String COL_COVERAGE_DURATION="duration";
		
		//CCH: Learning Table
		public static final String LEARNING_TABLE="learning";
		public static final String COL_LEARNING_CATEGORY="learning_category";
		public static final String COL_LEARNING_DESCRIPTION="learning_description";
		public static final String COL_LEARNING_TOPIC="learning_topic";
		public static final String COL_LEARNING_PERIOD="learning_period";
		public static final String COL_LEARNING_DUE_DATE="due_date";
		public static final String COL_LEARNING_DURATION="duration";
		
		//CCH: Other Table
		public static final String OTHER_TABLE="other";
		public static final String COL_OTHER_CATEGORY="other_category";
		public static final String COL_OTHER_NUMBER="other_number";
		public static final String COL_OTHER_PERIOD="other_period";
		public static final String COL_OTHER_DUE_DATE="due_date";
		public static final String COL_OTHER_DURATION="duration";
		public static final String COL_OTHER_DETAILS="other_detail";//personal or not
		
		private static final String TEXT_TYPE = " TEXT";
		private static final String INT_TYPE = " integer";
		private static final String COMMA_SEP = ",";
		
		//CCH: Update Justification Table
		public static final String JUSTIFICATION_TABLE="update_justification";
		public static final String COL_TYPE="type";
		public static final String COL_TYPE_DETAIL="type_detail";
		public static final String COL_NUMBER_ENTERED="number_entered";
		public static final String COL_NUMBER_ACHIEVED="number_achieved";
		public static final String COL_NUMBER_REMAINING="number_remaining";
		public static final String COL_JUSTIFICATION="justification";
		public static final String COL_TARGET_ID="target_id";
		public static final String COL_COMMENT="comment";
		
		public static final String CALENDAR_EVENTS_TABLE="calendar_events";
		public static final String COL_USERID="user_id";
		public static final String COL_EVENTTYPE="event_type";
		public static final String COL_DESCRIPTION="description";
		public static final String COL_LOCATION="location";
		public static final String COL_CATEGORY="category";
		public static final String COL_COMMENTS="comments";
		public static final String COL_STARTDATE="start_date";
		public static final String COL_ENDDATE="end_date";
		public static final String COL_REMINDER="reminder";
		public static final String COL_EDITED="edited";
		public static final String COL_STATUS="status";
		public static final String COL_DELETED="deleted";
		public static final String COL_EVENT_ID="event_id";
		
		
		//CCH Targets Table
		public static final String TARGET_TABLE="targets";
		public static final String CCH_TARGET_TYPE="target_type";
		public static final String CCH_OLD_ID="old_id";
		public static final String CCH_TARGET_NAME="target_name";
		public static final String CCH_TARGET_DETAIL="target_detail";
		public static final String CCH_TARGET_GROUP="target_group";
		public static final String CCH_TARGET_CATEGORY="target_category";
		public static final String CCH_TARGET_NO="target_number";
		public static final String CCH_TARGET_NO_ACHIEVED="target_no_achieved";
		public static final String CCH_TARGET_NO_REMAINING="target_no_remaining";
		public static final String CCH_START_DATE="start_date";
		public static final String CCH_DUE_DATE="due_date";
		public static final String CCH_REMINDER="reminder";
		public static final String CCH_STATUS="target_status";
		public static final String CCH_LAST_UPDATED="last_updated";
		
		//CCH Survey Table
				public static final String SURVEY_TABLE="survey_responses";
				public static final String CCH_SURVEY_USER_ID="user_id";
				public static final String CCH_USER_ROLE="user_role";
				public static final String CCH_RESPONSES="responses";
				public static final String CCH_DATE_TAKEN="date_taken";
				public static final String CCH_REMINDER_DATE="reminder_date";
				public static final String CCH_NEXT_REMINDER_DATE="next_reminder_date";
				public static final String CCH_REMINDER_FREQUENCY="reminder_frequency";
				public static final String CCH_REMINDER_FREQUENCY_VALUE="reminder_frequency_value";
				public static final String CCH_SURVEY_STATUS="survey_status";
		
				//CCH Target Achievements Table
				public static final String TARGET_ACHIEVEMENTS_TABLE="target_achievements";
				public static final String CCH_TARGET_ID="target_id";
				
				//CCH Course Achievements Table
				public static final String COURSE_ACHIEVEMENTS_TABLE="course_achievements";
				public static final String CCH_COURSE="course";
				public static final String CCH_COURSE_RECORD_ID="record_id";
				public static final String CCH_COURSE_SECTION="section";
				public static final String CCH_COURSE_SCORE="score";
				public static final String CCH_COURSE_MAX_SCORE="max_score";
				public static final String CCH_COURSE_QUIZ_TYPE="quiz_type";
				public static final String CCH_COURSE_QUIZ_TITLE="quiz_title";
				public static final String CCH_COURSE_PERCENTAGE_COMPLETED="percompleted";
				public static final String CCH_COURSE_DATE_TAKEN="date_taken";
				public static final String CCH_COURSE_TITLE="title";
				public static final String CCH_COURSE_ATTEMPTS="attempts";
				public static final String CCH_COURSE_KSA_STATUS="ksa_status";
				
				
				//CCH POC Sections Table
				public static final String POC_SECTIONS_TABLE="poc_sections";
				public static final String CCH_SECTION_NAME="section_name";
				public static final String CCH_SECTION_SHORTNAME="section_shortname";
				public static final String CCH_SECTION_URL="section_url";
				public static final String CCH_SUB_SECTION="sub_section";
				public static final String CCH_DOWNLOAD_URL="download_url";
		
		//CCH 
				private static final String USER_GROUP_MEMBERS_TABLE = "group_members";
	public static final String COL_LAST_UPDATED="last_updated";
	//CCH Targets Table
			public static final String FACILITY_TARGET_UPDATE_TABLE="facility_targets_update";
			public static final String FACILITY_TARGET_TABLE="facility_targets";
			public static final String CCH_TARGET_GROUP_MEMBERS="group_members";
			public static final String CCH_TARGET_MONTH="target_month";
			public static final String CCH_TARGET_OVERALL="overall_target";
	// Constructor
	public DbHelper(Context ctx) { //
		super(ctx, DB_NAME, null, DB_VERSION);
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		String batteryPercentage = null;
		this.battery_percentage=batteryPercentage;
		this.ctx = ctx;
		timeUtil=new CCHTimeUtil();
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
		//runSWReset(db);
		
		/*CHN Target tracking changes*/
		//createEventsSetTable(db);
		//createCoverageSetTable(db);
		//createLearningTable(db);
		//createOtherTable(db);
		createCalendarEventsTable(db);
		createJustificationTable(db);
		createSurveyTable(db);
		createTargetsTable(db);
		createFacilityTargetsTable(db);
		createFacilityTargetsUpdateTable(db);
		createGroupMembersTable(db);
		createPocSectionTable(db);
	}

	
	public void createPocSectionTable(SQLiteDatabase db){
		String m_sql = "create table if not exists " + POC_SECTIONS_TABLE + " (" 
				+ BaseColumns._ID + " integer primary key autoincrement, "
				+ CCH_SECTION_NAME + INT_TYPE + COMMA_SEP 
				+ CCH_SECTION_SHORTNAME + TEXT_TYPE + COMMA_SEP 
				+ CCH_SECTION_URL + TEXT_TYPE + COMMA_SEP
				+ CCH_DOWNLOAD_URL + TEXT_TYPE + COMMA_SEP
				+ CCH_SUB_SECTION + " text)";
		db.execSQL(m_sql);
	}
	
	public void createSurveyTable(SQLiteDatabase db){
		String m_sql = "create table if not exists " + SURVEY_TABLE + " (" 
				+ BaseColumns._ID + " integer primary key autoincrement, "
				+ CCH_SURVEY_USER_ID + INT_TYPE + COMMA_SEP 
				+ CCH_USER_ROLE + TEXT_TYPE + COMMA_SEP 
				+ CCH_RESPONSES + TEXT_TYPE + COMMA_SEP
				+ CCH_REMINDER_DATE + TEXT_TYPE + COMMA_SEP
				+ CCH_NEXT_REMINDER_DATE + TEXT_TYPE + COMMA_SEP
				+ CCH_REMINDER_FREQUENCY + TEXT_TYPE + COMMA_SEP
				+ CCH_REMINDER_FREQUENCY_VALUE + TEXT_TYPE + COMMA_SEP
				+ CCH_SURVEY_STATUS + TEXT_TYPE + COMMA_SEP
				+ CCH_DATE_TAKEN + " text)";
		db.execSQL(m_sql);
	}
	public void createGroupMembersTable(SQLiteDatabase db){
		String m_sql = "create table if not exists " + USER_GROUP_MEMBERS_TABLE + " (" 
				+ BaseColumns._ID + " integer primary key autoincrement, "
				+ CCH_USER_USERNAME + INT_TYPE + COMMA_SEP 
				+ CCH_USER_FIRSTNAME + TEXT_TYPE + COMMA_SEP 
				+ CCH_USER_LASTNAME + TEXT_TYPE + COMMA_SEP 
				+ CCH_USER_DISTRICT + TEXT_TYPE + COMMA_SEP 
				+ CCH_USER_SUBDISTRICT + TEXT_TYPE + COMMA_SEP 
				+ CCH_USER_FACILITY + TEXT_TYPE + COMMA_SEP 
				+ CCH_USER_ZONE +
				 " text)";
		db.execSQL(m_sql);
	}
	public void createTargetsTable(SQLiteDatabase db){
		String m_sql = "create table if not exists " + TARGET_TABLE + " (" 
				+ BaseColumns._ID + " integer primary key autoincrement, "
				+ CCH_OLD_ID + INT_TYPE + COMMA_SEP 
				+ CCH_TARGET_TYPE + TEXT_TYPE + COMMA_SEP 
				+ CCH_TARGET_NAME + TEXT_TYPE + COMMA_SEP
				+ CCH_TARGET_DETAIL + TEXT_TYPE + COMMA_SEP
				+ CCH_TARGET_CATEGORY + TEXT_TYPE + COMMA_SEP
				+ CCH_TARGET_NO + TEXT_TYPE + COMMA_SEP
				+ CCH_TARGET_NO_ACHIEVED+ TEXT_TYPE + COMMA_SEP
				+ CCH_TARGET_NO_REMAINING+ TEXT_TYPE + COMMA_SEP
				+ CCH_START_DATE+ TEXT_TYPE + COMMA_SEP
				+ CCH_DUE_DATE+ TEXT_TYPE + COMMA_SEP
				+ CCH_REMINDER+ TEXT_TYPE + COMMA_SEP
				+ CCH_STATUS+ TEXT_TYPE + COMMA_SEP
				+ CCH_LAST_UPDATED + " text)";
		db.execSQL(m_sql);
	}
	
	public void createFacilityTargetsTable(SQLiteDatabase db){
		String m_sql = "create table if not exists " + FACILITY_TARGET_TABLE + " (" 
				+ BaseColumns._ID + " integer primary key autoincrement, "
				+ CCH_TARGET_ID + INT_TYPE + COMMA_SEP 
				+ CCH_TARGET_TYPE + TEXT_TYPE + COMMA_SEP 
				+ CCH_TARGET_NAME + TEXT_TYPE + COMMA_SEP 
				+ CCH_TARGET_NO + TEXT_TYPE + COMMA_SEP
				+ CCH_TARGET_NO_ACHIEVED+ TEXT_TYPE + COMMA_SEP //target actual
				+ CCH_TARGET_GROUP+ TEXT_TYPE + COMMA_SEP
				+ CCH_TARGET_MONTH + " text)";
		db.execSQL(m_sql);
	}
	public void createFacilityTargetsUpdateTable(SQLiteDatabase db){
		String m_sql = "create table if not exists " + FACILITY_TARGET_UPDATE_TABLE + " (" 
				+ BaseColumns._ID + " integer primary key autoincrement, "
				+ CCH_TARGET_ID + INT_TYPE + COMMA_SEP 
				+ CCH_TARGET_TYPE + TEXT_TYPE + COMMA_SEP 
				+ CCH_TARGET_DETAIL + TEXT_TYPE + COMMA_SEP 
				+ CCH_TARGET_CATEGORY + TEXT_TYPE + COMMA_SEP 
				+ CCH_TARGET_NAME + TEXT_TYPE + COMMA_SEP 
				+ CCH_TARGET_NO + TEXT_TYPE + COMMA_SEP
				+ CCH_TARGET_NO_ACHIEVED+ TEXT_TYPE + COMMA_SEP
				+ CCH_TARGET_NO_REMAINING+ TEXT_TYPE + COMMA_SEP
				+ CCH_TARGET_OVERALL+ TEXT_TYPE + COMMA_SEP
				+ CCH_STATUS+ TEXT_TYPE + COMMA_SEP
				+ COL_COMMENT+ TEXT_TYPE + COMMA_SEP
				+ CCH_USER_FACILITY+ TEXT_TYPE + COMMA_SEP
				+ CCH_USER_ZONE+ TEXT_TYPE + COMMA_SEP
				+ COL_JUSTIFICATION+ TEXT_TYPE + COMMA_SEP
				+ CCH_TARGET_GROUP_MEMBERS+ TEXT_TYPE + COMMA_SEP
				+ CCH_TARGET_MONTH+ TEXT_TYPE + COMMA_SEP
				+ CCH_LAST_UPDATED + " text)";
		db.execSQL(m_sql);
	}
	public void createCourseTable(SQLiteDatabase db){
		String m_sql = "create table " + COURSE_TABLE + " (" + COURSE_C_ID + " integer primary key autoincrement, "
				+ COURSE_C_VERSIONID + " int, " + COURSE_C_TITLE + " text, " + COURSE_C_LOCATION + " text, "
				+ COURSE_C_SHORTNAME + " text," + COURSE_C_SCHEDULE + " int,"
				+ COURSE_C_IMAGE + " text,"
				+ COURSE_C_LANGS + " text,"
				+ COURSE_C_GROUP + " text,"
				+ COURSE_C_DATE + " text)";
		db.execSQL(m_sql);
	}
	
	/*public void createCourseGroupTable(SQLiteDatabase db){
		String m_sql = "create table if not exists" + COURSE_GROUP_TABLE + 
				" (" + COURSE_C_ID + " integer primary key autoincrement, "
				+ COURSE_C_VERSIONID + " int, "
				+ COURSE_C_TITLE + " text, " 
				+ COURSE_C_SHORTNAME + " text,"
				+ COURSE_C_GROUP + " text,"
				+ COURSE_C_DATE + " text)";
		db.execSQL(m_sql);
	}*/
	
	
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
	
	public void createCalendarEventsTable(SQLiteDatabase db){
		//	table create statement for events set table 
			final String CALENDAR_EVENTS_CREATE_TABLE =
				    "CREATE TABLE if not exists " + CALENDAR_EVENTS_TABLE + " (" +
				    		BaseColumns._ID + " INTEGER PRIMARY KEY," +
				    		COL_EVENTTYPE + TEXT_TYPE + COMMA_SEP +
				    		COL_EVENT_ID + TEXT_TYPE + COMMA_SEP +
				    		COL_USERID + TEXT_TYPE + COMMA_SEP +
				    		COL_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
				    		COL_LOCATION + TEXT_TYPE + COMMA_SEP +
				    		COL_CATEGORY + TEXT_TYPE + COMMA_SEP +
				    	    COL_COMMENTS + TEXT_TYPE + COMMA_SEP +
				    	    COL_JUSTIFICATION + TEXT_TYPE + COMMA_SEP +
				    	    COL_REMINDER + TEXT_TYPE + COMMA_SEP +
				    	    COL_STARTDATE + TEXT_TYPE + COMMA_SEP +
				    	    COL_ENDDATE + TEXT_TYPE + COMMA_SEP +
				    	    COL_STATUS + TEXT_TYPE + COMMA_SEP +
				    		COL_EDITED + TEXT_TYPE + COMMA_SEP +
				    		COL_DELETED+TEXT_TYPE+
				    " )";
			db.execSQL(CALENDAR_EVENTS_CREATE_TABLE);
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
							QUIZRESULTS_C_TITLE + " text, " +  
							QUIZRESULTS_C_SENT + " integer default 0, "+
							QUIZRESULTS_C_COURSEID + " integer)";
		db.execSQL(m_sql);
	}
	public void createJustificationTable(SQLiteDatabase db){
	
			final String JUSTIFICATION_CREATE_TABLE =
				    "CREATE TABLE " + JUSTIFICATION_TABLE + " (" +
				    		BaseColumns._ID + " INTEGER PRIMARY KEY," +
				    		COL_TYPE + TEXT_TYPE + COMMA_SEP +
				    		COL_TYPE_DETAIL + TEXT_TYPE + COMMA_SEP +
				    		COL_JUSTIFICATION + TEXT_TYPE + COMMA_SEP+
				    		COL_NUMBER_ENTERED + TEXT_TYPE + COMMA_SEP+
				    		COL_NUMBER_ACHIEVED + TEXT_TYPE + COMMA_SEP+
				    		COL_NUMBER_REMAINING + TEXT_TYPE + COMMA_SEP+
				    		COL_TARGET_ID + TEXT_TYPE + COMMA_SEP+
				    		COL_COMMENT + TEXT_TYPE + COMMA_SEP+
				    		COL_LAST_UPDATED+ TEXT_TYPE + COMMA_SEP +// new column
				    		COL_SYNC_STATUS+ TEXT_TYPE+
				    " )";
			db.execSQL(JUSTIFICATION_CREATE_TABLE);
	}
	
	public void createTargetAchievementsTable(SQLiteDatabase db){
		
		final String TARGET_ACHIEVEMENT_CREATE_TABLE =
			    "CREATE TABLE IF NOT EXISTS " + TARGET_ACHIEVEMENTS_TABLE + " (" +
			    		BaseColumns._ID + " INTEGER PRIMARY KEY," +
			    		CCH_TARGET_ID + TEXT_TYPE + COMMA_SEP +
			    		CCH_TARGET_TYPE + TEXT_TYPE + COMMA_SEP +
			    		CCH_TARGET_CATEGORY + TEXT_TYPE + COMMA_SEP+
			    		CCH_TARGET_NO + TEXT_TYPE + COMMA_SEP+
			    		CCH_TARGET_NO_ACHIEVED + TEXT_TYPE + COMMA_SEP+
			    		CCH_STATUS + TEXT_TYPE + COMMA_SEP+
			    		CCH_LAST_UPDATED + TEXT_TYPE + COMMA_SEP+
			    		CCH_START_DATE + TEXT_TYPE + COMMA_SEP+
			    		CCH_DUE_DATE + TEXT_TYPE +			    		
			    " )";
		db.execSQL(TARGET_ACHIEVEMENT_CREATE_TABLE);
}
public void createCourseAchievementsTable(SQLiteDatabase db){
		
		final String COURSE_ACHIEVEMENT_CREATE_TABLE =
			    "CREATE TABLE IF NOT EXISTS " + COURSE_ACHIEVEMENTS_TABLE + " (" +
			    		BaseColumns._ID + " INTEGER PRIMARY KEY," +
			    		CCH_COURSE+ TEXT_TYPE + COMMA_SEP +
			    		CCH_COURSE_RECORD_ID+ TEXT_TYPE + COMMA_SEP +
			    		CCH_COURSE_SECTION + TEXT_TYPE + COMMA_SEP +
			    		CCH_COURSE_SCORE + TEXT_TYPE + COMMA_SEP+
			    		CCH_COURSE_MAX_SCORE + TEXT_TYPE + COMMA_SEP+
			    		CCH_COURSE_QUIZ_TYPE + TEXT_TYPE + COMMA_SEP+
			    		CCH_COURSE_QUIZ_TITLE + TEXT_TYPE + COMMA_SEP+
			    		CCH_COURSE_DATE_TAKEN + TEXT_TYPE+	    		
			    " )";
		db.execSQL(COURSE_ACHIEVEMENT_CREATE_TABLE);
}
public void createCourses(SQLiteDatabase db){
	
	final String COURSE_ACHIEVEMENT_CREATE_TABLE =
		    "CREATE TABLE IF NOT EXISTS " + COURSES_TABLE + " (" +
		    		BaseColumns._ID + " INTEGER PRIMARY KEY," +
		    		CCH_COURSE+ TEXT_TYPE + COMMA_SEP +
		    		CCH_COURSE_RECORD_ID+ TEXT_TYPE + COMMA_SEP +
		    		CCH_COURSE_TITLE + TEXT_TYPE + COMMA_SEP +
		    		CCH_COURSE_SCORE + TEXT_TYPE + COMMA_SEP+
		    		CCH_COURSE_PERCENTAGE_COMPLETED + TEXT_TYPE + COMMA_SEP+
		    		CCH_COURSE_ATTEMPTS + TEXT_TYPE + COMMA_SEP+
		    		CCH_COURSE_QUIZ_TITLE + TEXT_TYPE + COMMA_SEP+
		    		CCH_COURSE_KSA_STATUS + TEXT_TYPE + COMMA_SEP+
		    		CCH_COURSE_DATE_TAKEN + TEXT_TYPE+	    		
		    " )";
	db.execSQL(COURSE_ACHIEVEMENT_CREATE_TABLE);
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
			createFacilityTargetsUpdateTable(db);
			//createEventsSetTable(db);
			//createCoverageSetTable(db);
			//createLearningTable(db);
			//createOtherTable(db);
			//createJustificationTable(db);
			//createStayingWellTable(db);
			createTargetsTable(db);
			//createCalendarEventsTable(db);
			//runSWReset(db);

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
		if(oldVersion==19){
			createTargetsTable(db);
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
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COURSE_C_VERSIONID, course.getVersionId());
		values.put(COURSE_C_TITLE, course.getTitleJSONString());
		values.put(COURSE_C_LOCATION, course.getLocation());
		values.put(COURSE_C_SHORTNAME, course.getShortname());
		values.put(COURSE_C_LANGS, course.getLangsJSONString());
		values.put(COURSE_C_IMAGE, course.getImageFile());
		values.put(COURSE_C_GROUP, course.getCourseGroup());
		values.put(COURSE_C_DATE, getDate());

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
	
	
	
	public boolean alterTables(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(QUIZRESULTS_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+QUIZRESULTS_TABLE+")",null);
				c.moveToFirst();
				if(c.getCount()==6){
					System.out.println("Columns up to date!");
			
				}else{
					String sql = "ALTER TABLE " + QUIZRESULTS_TABLE + " ADD COLUMN " + QUIZRESULTS_C_TITLE  + " TEXT NULL;";
					db.execSQL(sql);
				}
				c.close();
				
		}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	
	public boolean alterUserTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(CCH_USER_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+CCH_USER_TABLE+")",null);
				c.moveToFirst();
				//String staffId=c.getString(c.getColumnIndex(CCH_STAFF_ID));
				if(c.getCount()==9){
					String sql = "ALTER TABLE " + CCH_USER_TABLE + " ADD COLUMN " + CCH_USER_ROLE  + " TEXT NULL;";
					db.execSQL(sql);
				}
					c.close();
					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean alterUserFaciityTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(CCH_USER_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+CCH_USER_TABLE+")",null);
				c.moveToFirst();
				//String staffId=c.getString(c.getColumnIndex(CCH_STAFF_ID));
				if(c.getCount()==11){
					String sql = "ALTER TABLE " + CCH_USER_TABLE + " ADD COLUMN " + CCH_USER_FACILITY  + " TEXT NULL;";
					db.execSQL(sql);
				}
					c.close();
					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean alterUserTableForZone(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(CCH_USER_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+CCH_USER_TABLE+")",null);
				c.moveToFirst();
				//String staffId=c.getString(c.getColumnIndex(CCH_STAFF_ID));
				if(c.getCount()==12){
					String sql = "ALTER TABLE " + CCH_USER_TABLE + " ADD COLUMN " + CCH_USER_SUBDISTRICT + " TEXT NULL;" ;
					db.execSQL(sql);
				}
					c.close();
					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean alterUserTableForSubdistrict(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(CCH_USER_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+CCH_USER_TABLE+")",null);
				c.moveToFirst();
				//String staffId=c.getString(c.getColumnIndex(CCH_STAFF_ID));
				if(c.getCount()==13){
					String sql = "ALTER TABLE "+
								CCH_USER_TABLE+ " ADD COLUMN "+ CCH_USER_ZONE+ " TEXT NULL; ALTER TABLE " ;
					db.execSQL(sql);
				}
					c.close();
					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	
	public boolean alterUserTableDistrict(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(CCH_USER_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+CCH_USER_TABLE+")",null);
				c.moveToFirst();
				//String staffId=c.getString(c.getColumnIndex(CCH_STAFF_ID));
				if(c.getCount()==10){
					String sql = "ALTER TABLE " + CCH_USER_TABLE + " ADD COLUMN " + CCH_USER_DISTRICT  + " TEXT NULL;";
					db.execSQL(sql);
				}
					c.close();
					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean updateUserData(String user_role, String user_district,String facility,String subdistrict,String zone){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(CCH_USER_TABLE)){
			try{
				String strQuery = "Update "+CCH_USER_TABLE+" set "+CCH_USER_ROLE+" ='"+user_role+"'"
								  +","+CCH_USER_DISTRICT+" ='"+user_district+"'"
								  +","+CCH_USER_FACILITY+" ='"+facility+"'"
								   +","+CCH_USER_SUBDISTRICT+" ='"+subdistrict+"'"
								    +","+CCH_USER_ZONE+" ='"+zone+"'"
								 ;
				db.execSQL(strQuery);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	
	public boolean updateSurveyData(String legal,String profile,String response,String plan,String datetime){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(CCH_SW_TABLE)){
			try{
				String strQuery = "Update "+CCH_SW_TABLE+" set "+CCH_SW_LEGAL_STATUS+" ='"+legal+"'"
											+","+CCH_SW_PROFILE_STATUS+" ='"+profile+"'"
											+","+CCH_SW_PROFILE_RESPONSES+" ='"+response+"' "
											+","+CCH_SW_MONTH_PLAN+" ='"+plan+"' "
											+","+CCH_SW_MONTH_PLAN_LASTUPDATE+" ='"+datetime+"'";
				db.execSQL(strQuery);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean alterStayingWellTables(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(CCH_SW_TABLE)){
			try{
				Cursor c = db.rawQuery("Select "+CCH_SW_STAFF_ID +" from "+CCH_SW_TABLE,null);
				c.moveToFirst();
				String staffId=c.getString(c.getColumnIndex(CCH_SW_STAFF_ID));
				if(staffId.equalsIgnoreCase("David")){
				}else{
					String strQuery = "Update "+CCH_SW_TABLE+" set "+CCH_SW_STAFF_ID+" ="+"'David'";
					db.execSQL(strQuery);
					
				}
		c.close();
		
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean alterEventTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(EVENTS_SET_TABLE)){
			try{
		Cursor c = db.rawQuery("PRAGMA table_info("+EVENTS_SET_TABLE+")",null);
		c.moveToFirst();
		if(c.getCount()==12){
			System.out.println("Columns up to date!");
			
		}else{
			String sql = "ALTER TABLE " + EVENTS_SET_TABLE + " ADD COLUMN " + COL_EVENT_SET_DETAIL  + " TEXT NULL;";
			db.execSQL(sql);
		}
		c.close();
		
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean alterOtherTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(OTHER_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+OTHER_TABLE+")",null);
				c.moveToFirst();
				if(c.getCount()==12){
			
				}else{
					String sql = "ALTER TABLE " + OTHER_TABLE + " ADD COLUMN " + COL_OTHER_DETAILS  + " TEXT NULL;";
					db.execSQL(sql);
				}
				c.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean alterCourseTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(COURSE_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+COURSE_TABLE+")",null);
				c.moveToFirst();
				if(c.getCount()==8){
					String sql = "ALTER TABLE " + COURSE_TABLE + " ADD COLUMN " + COURSE_C_DATE  + " TEXT NULL;";
					db.execSQL(sql);
			}
				c.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean alterFacilityTargetTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(FACILITY_TARGET_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+FACILITY_TARGET_TABLE+")",null);
				c.moveToFirst();
				if(c.getCount()==13){
					String sql = "ALTER TABLE " + FACILITY_TARGET_TABLE + " ADD COLUMN " + CCH_TARGET_CATEGORY  + " TEXT NULL;";
					db.execSQL(sql);
			}
				c.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	
	public boolean alterFacilityTargetDetailTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(FACILITY_TARGET_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+FACILITY_TARGET_TABLE+")",null);
				c.moveToFirst();
				if(c.getCount()==14){
					String sql = "ALTER TABLE " + FACILITY_TARGET_TABLE + " ADD COLUMN " + CCH_TARGET_DETAIL  + " TEXT NULL;";
					db.execSQL(sql);
			}
				c.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean alterFacilityTargetGroupTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(FACILITY_TARGET_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+FACILITY_TARGET_TABLE+")",null);
				c.moveToFirst();
				if(c.getCount()==15){
					String sql = "ALTER TABLE " + FACILITY_TARGET_TABLE + " ADD COLUMN " + CCH_TARGET_NAME  + " TEXT NULL;";
					db.execSQL(sql);
			}
				c.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean alterFacilityTargetOverall(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(FACILITY_TARGET_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+FACILITY_TARGET_TABLE+")",null);
				c.moveToFirst();
				if(c.getCount()==16){
					String sql = "ALTER TABLE " + FACILITY_TARGET_TABLE + " ADD COLUMN " + CCH_TARGET_OVERALL  + " TEXT NULL;";
					db.execSQL(sql);
			}
				c.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean alterPOCSection(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(POC_SECTIONS_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+POC_SECTIONS_TABLE+")",null);
				c.moveToFirst();
				if(c.getCount()==5){
					String sql = "ALTER TABLE " + POC_SECTIONS_TABLE + " ADD COLUMN " + CCH_DOWNLOAD_URL  + " TEXT NULL;";
					db.execSQL(sql);
			}
				c.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean alterCourseTableGroup(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(COURSE_TABLE)){
			try{
				Cursor c = db.rawQuery("PRAGMA table_info("+COURSE_TABLE+")",null);
				c.moveToFirst();
				if(c.getCount()==9){
					String sql = "ALTER TABLE " + COURSE_TABLE + " ADD COLUMN " + COURSE_C_GROUP  + " TEXT NULL;";
					db.execSQL(sql);
				}else{
				}
				c.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
	}
	public boolean deleteGroupsTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery("PRAGMA table_info("+USER_GROUP_MEMBERS_TABLE+")",null);
		c.moveToFirst();
		if(c.getCount()==4){
			String strQuery = "Drop table if exists "+USER_GROUP_MEMBERS_TABLE;
			db.execSQL(strQuery);	
		}else{
		}
		c.close();
			
		return true;
		
	}
	public boolean deleteFacilityTargetsTable(){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery("PRAGMA table_info("+FACILITY_TARGET_TABLE+")",null);
		c.moveToFirst();
		if(c.getCount()>10){
			String strQuery = "Drop table if exists "+FACILITY_TARGET_TABLE;
			db.execSQL(strQuery);	
		}else{
		}
		c.close();
		return true;
		
	}
	public boolean deleteTables(String table){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(table)){
			try{
				String strQuery = "Drop table if exists "+table;
				Log.d("SQLITE","Deleting table: " +table);
				db.execSQL(strQuery);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	
	public boolean updateDateDefault(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(COURSE_TABLE)){
			try{
				String strQuery = "Update "+COURSE_TABLE+" set "+COURSE_C_DATE+" = '"+getDate()+"' where "+ COURSE_C_DATE+" IS NULL";
				db.execSQL(strQuery);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	
	public boolean updateEventDetailDefault(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(EVENTS_SET_TABLE)){
			try{
				String strQuery = "Update "+EVENTS_SET_TABLE+" set "+COL_EVENT_SET_DETAIL+" ="+"'Event'"+" where "+ COL_EVENT_SET_DETAIL+ " IS NULL";
				db.execSQL(strQuery);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
		
	}
	public boolean updateOtherDetailDefault(){
		SQLiteDatabase db = this.getWritableDatabase();
		if(doesTableExists(OTHER_TABLE)){
			try{
				String strQuery = "Update "+OTHER_TABLE+" set "+COL_OTHER_DETAILS+" ="+"'not_personal'"+" where "+ COL_OTHER_DETAILS+ " IS NULL";
				db.execSQL(strQuery);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public long insertTarget(int old_id,String target_type,String target_name, String target_detail, String target_category, int target_no, int target_no_achieved, int target_remaining,String start_date,String due_date,String reminder,String status,String last_updated){
		SQLiteDatabase db = this.getWritableDatabase();
		createTargetsTable(db);
		ContentValues values = new ContentValues();
		values.put(CCH_OLD_ID,old_id);
		values.put(CCH_TARGET_TYPE,target_type);
		values.put(CCH_TARGET_NAME,target_name);
		values.put(CCH_TARGET_DETAIL,target_detail);
		values.put(CCH_TARGET_CATEGORY,target_category);
		values.put(CCH_TARGET_NO,target_no);
		values.put(CCH_TARGET_NO_ACHIEVED,target_no_achieved);
		values.put(CCH_TARGET_NO_REMAINING,target_remaining);
		values.put(CCH_START_DATE,start_date);
		values.put(CCH_DUE_DATE,due_date);
		values.put(CCH_REMINDER,reminder);
		values.put(CCH_STATUS,status);
		values.put(CCH_LAST_UPDATED,last_updated);
		
		long newRowId;
		newRowId = db.insert(
				TARGET_TABLE, null, values);
		
		return newRowId;
	}
	public long insertPocSection(String section_name,String section_shortname, String section_url,
			String sub_section,String download_url){
				SQLiteDatabase db = this.getWritableDatabase();
					createPocSectionTable(db);
					ContentValues values = new ContentValues();
					values.put(CCH_SECTION_NAME,section_name);
					values.put(CCH_SECTION_SHORTNAME,section_shortname);
					values.put(CCH_SECTION_URL,section_url);
					values.put(CCH_SUB_SECTION,sub_section);
					values.put(CCH_DOWNLOAD_URL,download_url);
					long newRowId;
					newRowId = db.update(POC_SECTIONS_TABLE, values, CCH_SECTION_SHORTNAME + "='" + section_shortname+"'", null);
					if(newRowId==0){
						newRowId = db.insert(POC_SECTIONS_TABLE, null, values);
					}
					return newRowId;
	}
	public long insertUserGroupMembers(String username,String firstname, String lastname,
											String district,String subdistrict,String facility,String zone){
		SQLiteDatabase db = this.getWritableDatabase();
		createGroupMembersTable(db);
		ContentValues values = new ContentValues();
		values.put(CCH_USER_USERNAME,username);
		values.put(CCH_USER_FIRSTNAME,firstname);
		values.put(CCH_USER_LASTNAME,lastname);
		values.put(CCH_USER_DISTRICT,district);
		values.put(CCH_USER_SUBDISTRICT,subdistrict);
		values.put(CCH_USER_FACILITY,facility);
		values.put(CCH_USER_ZONE,zone);
		long newRowId;
		newRowId = db.update(USER_GROUP_MEMBERS_TABLE, values, CCH_USER_USERNAME + "='" + username+"'", null);
		 if(newRowId==0){
			 newRowId = db.insert(USER_GROUP_MEMBERS_TABLE, null, values);
		 }
		return newRowId;
	}
	public long insertFacilityTarget(String target_id,String target_type,String target_name,String target_month,
									String target_group,String target,String target_actual){
		long newRowId = 0;
		try{
		SQLiteDatabase db = this.getWritableDatabase();
		createFacilityTargetsTable(db);
		ContentValues values = new ContentValues();
		values.put(CCH_TARGET_ID,target_id);
		values.put(CCH_TARGET_TYPE,target_type);
		values.put(CCH_TARGET_GROUP,target_group);
		values.put(CCH_TARGET_NAME,target_name);
		values.put(CCH_TARGET_NO,target);
		values.put(CCH_TARGET_NO_ACHIEVED,target_actual);
		values.put(CCH_TARGET_MONTH, target_month);
		
		ContentValues update_values = new ContentValues();
		update_values.put(CCH_TARGET_ID,target_id);
		update_values.put(CCH_TARGET_TYPE,target_type);
		update_values.put(CCH_TARGET_GROUP,target_group);
		update_values.put(CCH_TARGET_NAME,target_name);
		update_values.put(CCH_TARGET_NO,target);
		update_values.put(CCH_TARGET_NO_ACHIEVED,target_actual);
		update_values.put(CCH_TARGET_MONTH, target_month);
		
		
		
		newRowId = db.update(FACILITY_TARGET_TABLE, update_values, CCH_TARGET_NAME + "='" +
															target_name +"' and "+
															CCH_TARGET_TYPE+"='"+target_type+"' and "+
															CCH_TARGET_MONTH+"='"+target_month+"'"
													, null);
		 if(newRowId==0){
			 newRowId = db.insert(FACILITY_TARGET_TABLE, null, values);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return newRowId;
	}
	
	public long insertFacilityTargetUpdate(int target_id,String target_type,String target_category,String target_details,
			String target_group,String target_overall,String target_no, String target_no_achieved, 
			String target_remaining,String status,String last_updated,String group_members,
			String target_month,String comments,String justification,String facility,String zone){
					long newRowId = 0;
					try{
						SQLiteDatabase db = this.getWritableDatabase();
						createFacilityTargetsUpdateTable(db);
						ContentValues values = new ContentValues();
						values.put(CCH_TARGET_ID,target_id);
						values.put(CCH_TARGET_TYPE,target_type);
						values.put(CCH_TARGET_CATEGORY,target_category);
						values.put(CCH_TARGET_DETAIL,target_details);
						values.put(CCH_TARGET_NAME,target_group);
						values.put(CCH_TARGET_NO,target_no);
						values.put(CCH_TARGET_OVERALL,target_overall);
						values.put(CCH_TARGET_NO_ACHIEVED,target_no_achieved);
						values.put(CCH_TARGET_NO_REMAINING,target_remaining);
						values.put(CCH_USER_FACILITY,facility);
						values.put(CCH_USER_ZONE,zone);
						values.put(CCH_STATUS,status);
						values.put(COL_COMMENT,comments);
						values.put(COL_JUSTIFICATION,justification);
						values.put(CCH_LAST_UPDATED,last_updated);
						values.put(CCH_TARGET_GROUP_MEMBERS, group_members);
						values.put(CCH_TARGET_MONTH, target_month);
						newRowId = db.insert(FACILITY_TARGET_UPDATE_TABLE, null, values);
					}catch(Exception e){
						e.printStackTrace();
					}
					return newRowId;
	}
	public long insertOrUpdateFacilityTargetUpdate(int target_id,String target_type,String target_category,
			String target_details,
			String target_group,String target_overall,String target_no, String target_no_achieved, 
			String target_remaining,String status,String last_updated,String group_members,
			String target_month,String comments,String justification,String facility,String zone){
					long newRowId = 0;
					try{
						SQLiteDatabase db = this.getWritableDatabase();
						createFacilityTargetsUpdateTable(db);
						ContentValues values = new ContentValues();
						values.put(CCH_TARGET_ID,target_id);
						values.put(CCH_TARGET_TYPE,target_type);
						values.put(CCH_TARGET_CATEGORY,target_category);
						values.put(CCH_TARGET_DETAIL,target_details);
						values.put(CCH_TARGET_NAME,target_group);
						values.put(CCH_TARGET_NO,target_no);
						values.put(CCH_TARGET_OVERALL,target_overall);
						values.put(CCH_TARGET_NO_ACHIEVED,target_no_achieved);
						values.put(CCH_TARGET_NO_REMAINING,target_remaining);
						values.put(CCH_USER_FACILITY,facility);
						values.put(CCH_USER_ZONE,zone);
						values.put(CCH_STATUS,status);
						values.put(COL_COMMENT,comments);
						values.put(COL_JUSTIFICATION,justification);
						values.put(CCH_LAST_UPDATED,last_updated);
						values.put(CCH_TARGET_GROUP_MEMBERS, group_members);
						values.put(CCH_TARGET_MONTH, target_month);
						
						ContentValues update_values = new ContentValues();
						update_values.put(CCH_TARGET_ID,target_id);
						update_values.put(CCH_TARGET_TYPE,target_type);
						update_values.put(CCH_TARGET_DETAIL,target_details);
						update_values.put(CCH_TARGET_CATEGORY,target_category);
						update_values.put(CCH_TARGET_NAME,target_group);
						update_values.put(CCH_TARGET_NO_ACHIEVED,target_no_achieved);
						update_values.put(COL_COMMENT,comments);
						update_values.put(COL_JUSTIFICATION,justification);
						newRowId = db.update(FACILITY_TARGET_UPDATE_TABLE, update_values, CCH_TARGET_ID + "=" +
																	target_id +" and "+
																	CCH_LAST_UPDATED+"='"+last_updated+"'"+" and "+
																	CCH_TARGET_TYPE+"='"+target_type+"'"
																	, null);
						 if(newRowId==0){
							 newRowId = db.insert(FACILITY_TARGET_UPDATE_TABLE, null, values);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					return newRowId;
	}
	public long insertSurvey(String user_id,String user_role, String responses,String reminder_date,String next_reminder_date,String reminderFrequency,String reminderFrequencyValue,String survey_status, String date_taken){
		SQLiteDatabase db = this.getWritableDatabase();
		createSurveyTable(db);
		ContentValues values = new ContentValues();
		values.put(CCH_SURVEY_USER_ID,user_id);
		values.put(CCH_USER_ROLE,user_role);
		values.put(CCH_RESPONSES,responses);
		values.put(CCH_REMINDER_DATE,reminder_date);
		values.put(CCH_NEXT_REMINDER_DATE,next_reminder_date);
		values.put(CCH_REMINDER_FREQUENCY,reminderFrequency);
		values.put(CCH_REMINDER_FREQUENCY_VALUE,reminderFrequencyValue);
		values.put(CCH_SURVEY_STATUS,survey_status);
		values.put(CCH_DATE_TAKEN,date_taken);
		
		long newRowId;
		newRowId = db.insert(
				SURVEY_TABLE, null, values);
			
		return newRowId;
	}
	public long insertCalendarEvent(long event_id, String event_type,
									String user_id, String description, 
									String category,String location,
									String comments,String justification,
									String reminder,String start_date,
									String end_date,String status,
									String edited,String deleted){
		SQLiteDatabase db = this.getWritableDatabase();
		createCalendarEventsTable(db);
		ContentValues values = new ContentValues();
		values.put(COL_EVENT_ID,event_id);
		values.put(COL_EVENTTYPE,event_type);
		values.put(COL_USERID,user_id);
		values.put(COL_DESCRIPTION,description);
		values.put(COL_LOCATION,location);
		values.put(COL_CATEGORY,category);
		values.put(COL_COMMENTS,comments);
		values.put(COL_JUSTIFICATION,justification);
		values.put(COL_REMINDER,reminder);
		values.put(COL_STARTDATE,start_date);
		values.put(COL_ENDDATE,end_date);
		values.put(COL_STATUS,status);
		values.put(COL_EDITED,edited);
		values.put(COL_DELETED,deleted);
		
		ContentValues update_values = new ContentValues();
		update_values.put(COL_EVENT_ID,event_id);
		update_values.put(COL_EVENTTYPE,event_type);
		update_values.put(COL_USERID,user_id);
		update_values.put(COL_DESCRIPTION,description);
		update_values.put(COL_LOCATION,location);
		update_values.put(COL_CATEGORY,category);
		update_values.put(COL_REMINDER,reminder);
		update_values.put(COL_STARTDATE,start_date);
		update_values.put(COL_ENDDATE,end_date);
		//update_values.put(COL_STATUS,status);
		update_values.put(COL_EDITED,edited);
		update_values.put(COL_DELETED,deleted);
		
		long newRowId;
		//String difficulty=event_type.replaceAll("'","\'");
		newRowId = db.update(CALENDAR_EVENTS_TABLE, update_values, COL_EVENT_ID + "=" + event_id +" and "+COL_EVENTTYPE+"="+DatabaseUtils.sqlEscapeString(event_type)+"", null);
		 if(newRowId==0){
			 newRowId = db.insert(CALENDAR_EVENTS_TABLE, null, values);
		 }
		return newRowId;
	}
	public long targetCarryForward(String target_id, String target_type,
									String target_category,
									String target_number,
									String old_target_id,String old_target_type){
				SQLiteDatabase db = this.getWritableDatabase();
					ContentValues update_values = new ContentValues();
					update_values.put(CCH_TARGET_ID,target_id);
					update_values.put(CCH_TARGET_TYPE,target_type);
					update_values.put(CCH_TARGET_CATEGORY,target_category);
					//update_values.put(CCH_TARGET_NO_REMAINING,target_number_remaining);
					update_values.put(CCH_TARGET_NO,target_number);
					
					ContentValues update_carried_forward = new ContentValues();
					update_carried_forward.put(CCH_TARGET_ID,old_target_id);
					update_carried_forward.put(CCH_TARGET_TYPE,old_target_type);
					update_carried_forward.put(CCH_TARGET_NO_REMAINING,0);
					
						long newRowId = db.update(FACILITY_TARGET_TABLE, update_values, 
													BaseColumns._ID + "=" + target_id +" and "
													+CCH_TARGET_TYPE+"='"+target_type+"'", null);
						if(newRowId!=0){
							db.update(FACILITY_TARGET_TABLE, update_carried_forward, 
									BaseColumns._ID + "=" + old_target_id +" and "
									+CCH_TARGET_TYPE+"='"+old_target_type+"'", null);
						}
						return newRowId;
}
	public long targetSynced(String target_id, String target_type,String number_achieved,String target_detail,String target_month){
			SQLiteDatabase db = this.getWritableDatabase();
				ContentValues update_values = new ContentValues();
				update_values.put(CCH_TARGET_NO_ACHIEVED,number_achieved);

long newRowId = db.update(FACILITY_TARGET_TABLE, update_values, 
							CCH_TARGET_DETAIL + "='" + target_detail +"' and "
							+CCH_TARGET_MONTH + "='" + target_month +"' and "
							+CCH_TARGET_TYPE+"='"+target_type+"'", null);
return newRowId;
}
	
	public long insertJustification(String type,String type_detail,String justification,String comment,String number,String achieved_number,String number_remaining,long id, String sync_status){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COL_TYPE,type);
		values.put(COL_TYPE_DETAIL,type_detail);
		values.put(COL_JUSTIFICATION,justification);
		values.put(COL_NUMBER_ENTERED,number);
		values.put(COL_NUMBER_ACHIEVED,achieved_number);
		values.put(COL_NUMBER_REMAINING,number_remaining);
		values.put(COL_TARGET_ID,id);
		values.put(COL_COMMENT,comment);
		values.put(COL_SYNC_STATUS,sync_status);
		values.put(COL_LAST_UPDATED,getDateTime());
		
		long newRowId;
		newRowId = db.insert(
				JUSTIFICATION_TABLE, null, values);
		
		return newRowId;
	}
	
	public long insertTargetAchievement(String target_id,String target_type,String target_category,String target_number,
										String target_achieved,String target_status,String last_updated,String target_start_date,String target_due_date){
		SQLiteDatabase db = this.getWritableDatabase();
		createTargetAchievementsTable(db);
		ContentValues values = new ContentValues();
		values.put(CCH_TARGET_ID,target_id);
		values.put(CCH_TARGET_TYPE,target_type);
		values.put(CCH_TARGET_CATEGORY,target_category);
		values.put(CCH_TARGET_NO,target_number);
		values.put(CCH_TARGET_NO_ACHIEVED,target_achieved);
		values.put(CCH_STATUS,target_status);
		values.put(CCH_LAST_UPDATED,last_updated);
		values.put(CCH_START_DATE,target_start_date);
		values.put(CCH_DUE_DATE,target_due_date);
		
		long newRowId;
		newRowId = db.update(TARGET_ACHIEVEMENTS_TABLE, values, CCH_TARGET_ID + "=" + target_id +" and "+CCH_TARGET_TYPE+"='"+target_type+"'", null);
		 if(newRowId==0){
			 newRowId = db.insert(TARGET_ACHIEVEMENTS_TABLE, null, values);
		 }
		return newRowId;
	}
	
	public long insertCourseAchievement(String course,String record_id,String section,String score,String max_score,
										String quiz_type,String quiz_title,String date_taken){
			SQLiteDatabase db = this.getWritableDatabase();
			createCourseAchievementsTable(db);
			ContentValues values = new ContentValues();
			values.put(CCH_COURSE,course);
			values.put(CCH_COURSE_RECORD_ID,record_id);
			values.put(CCH_COURSE_SECTION,section);
			values.put(CCH_COURSE_SCORE,score);
			values.put(CCH_COURSE_MAX_SCORE,max_score);
			values.put(CCH_COURSE_QUIZ_TITLE, quiz_title);
			values.put(CCH_COURSE_QUIZ_TYPE, quiz_type);
			values.put(CCH_COURSE_DATE_TAKEN,date_taken);

		long newRowId;
		newRowId = db.update(
				COURSE_ACHIEVEMENTS_TABLE,values,  CCH_COURSE_RECORD_ID + "=" + record_id , null);
		if(newRowId==0){
		newRowId = db.insert(
				COURSE_ACHIEVEMENTS_TABLE, null, values);
		}

		return newRowId;
	}
	
	public long insertCourseForAchievements(String course,String record_id,String title,String score,String attempts,
			String percentage_complete,String ksa_status,String date_taken){
			SQLiteDatabase db = this.getWritableDatabase();
			createCourses(db);
			ContentValues values = new ContentValues();
			values.put(CCH_COURSE,course);
			values.put(CCH_COURSE_RECORD_ID,record_id);
			values.put(CCH_COURSE_TITLE,title);
			values.put(CCH_COURSE_SCORE,score);
			values.put(CCH_COURSE_ATTEMPTS,attempts);
			values.put(CCH_COURSE_PERCENTAGE_COMPLETED, percentage_complete);
			values.put(CCH_COURSE_KSA_STATUS,ksa_status);
			values.put(CCH_COURSE_DATE_TAKEN,date_taken);

			long newRowId;
			newRowId = db.update(
					COURSES_TABLE,values,  CCH_COURSE_RECORD_ID + "=" + record_id , null);
			if(newRowId==0){
				newRowId = db.insert(
						COURSES_TABLE, null, values);
			}

			return newRowId;
	}
	public long refreshCourse(Course course){
		SQLiteDatabase db = this.getWritableDatabase();
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
		SQLiteDatabase db = this.getReadableDatabase();
		String s = COURSE_C_SHORTNAME + "=?";
		String[] args = new String[] { shortname };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, null);
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
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COURSE_C_SCHEDULE, scheduleVersion);
		db.update(COURSE_TABLE, values, COURSE_C_ID + "=" + modId, null);
		
	}
	
	public void insertActivities(ArrayList<Activity> acts) {
		SQLiteDatabase db = this.getWritableDatabase();
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
		SQLiteDatabase db = this.getWritableDatabase();
		for (ActivitySchedule as : actsched) {
			ContentValues values = new ContentValues();
			values.put(ACTIVITY_C_STARTDATE, as.getStartTimeString());
			values.put(ACTIVITY_C_ENDDATE, as.getEndTimeString());
			db.update(ACTIVITY_TABLE, values, ACTIVITY_C_ACTIVITYDIGEST + "='" + as.getDigest() + "'", null);
		}
		
	}
	
	public void insertTrackers(ArrayList<TrackerLog> trackers, long modId) {
		// acts.listIterator();
		SQLiteDatabase db = this.getWritableDatabase();
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
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ACTIVITY_C_STARTDATE,"");
		values.put(ACTIVITY_C_ENDDATE,"");
		db.update(ACTIVITY_TABLE, values, ACTIVITY_C_COURSEID + "=" + modId, null);
		
	}
	
	public ArrayList<Course> getCourses() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Course> courses = new ArrayList<Course>();
		String order = COURSE_C_TITLE + " ASC";
		Cursor c = db.query(COURSE_TABLE, null, null, null, null, null, order);
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
			course.setCourseGroup(c.getString(c.getColumnIndex(COURSE_C_GROUP)));
			courses.add(course);
			
			c.moveToNext();
		}
		c.close();
		
		return courses;
	}

	public ArrayList<Course> getCoursesWithGroups(String courseGroup) {
		SQLiteDatabase db = this.getReadableDatabase();
		Course course = new Course();
		ArrayList<Course> courses = new ArrayList<Course>();
		String order = COURSE_C_TITLE + " ASC";
		String s = COURSE_C_GROUP + "=?";
		String[] args = new String[] { courseGroup };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, order);
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			course = new Course();
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
	public ArrayList<Course> getCoursesWithNoGroups() {
		SQLiteDatabase db = this.getReadableDatabase();
		Course course = new Course();
		ArrayList<Course> courses = new ArrayList<Course>();
		String sql = "select * from module where course_group is null";
		String order = COURSE_C_TITLE + " ASC";
		String s = COURSE_C_GROUP + "=?";
		String[] args = new String[] { "is null" };
		Cursor c = db.rawQuery(sql,null);
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			course = new Course();
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
	public ArrayList<Course> getCourseGroupNames() {
		SQLiteDatabase db = this.getReadableDatabase();
		Course course = new Course();
		ArrayList<Course> courses = new ArrayList<Course>();
		String sql = "Select "+COURSE_C_GROUP+ " from "+COURSE_TABLE+" group by "+COURSE_C_GROUP;
		Cursor c = db.rawQuery(sql,null);
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			course = new Course();
			course.setCourseGroup(c.getString(c.getColumnIndex(COURSE_C_GROUP)));
			courses.add(course);
			c.moveToNext();
		}
		c.close();
		
		return courses;
	}
	
	public Course getCourses(String shortname) {
		SQLiteDatabase db = this.getReadableDatabase();
		//Course courses = new Course();
		String order = COURSE_C_TITLE + " ASC";
		String s = COURSE_C_SHORTNAME + "=?";
		String[] args = new String[] { shortname };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, order);
		c.moveToFirst();
		Course course = null ;
		while (c.isAfterLast() == false) {
			course = new Course();
			course.setModId(c.getInt(c.getColumnIndex(COURSE_C_ID)));
			course.setLocation(c.getString(c.getColumnIndex(COURSE_C_LOCATION)));
			course.setProgress(this.getCourseProgress(course.getModId()));
			course.setVersionId(c.getDouble(c.getColumnIndex(COURSE_C_VERSIONID)));
			course.setTitlesFromJSONString(c.getString(c.getColumnIndex(COURSE_C_TITLE)));
			course.setImageFile(c.getString(c.getColumnIndex(COURSE_C_IMAGE)));
			course.setLangsFromJSONString(c.getString(c.getColumnIndex(COURSE_C_LANGS)));
			course.setShortname(c.getString(c.getColumnIndex(COURSE_C_SHORTNAME)));
		//	courses.add(course);
			c.moveToNext();
		}
		c.close();
		
		return course;
	}
	public ArrayList<Course> getCoursesForAchievements(int month,int year) {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Course> courses = new ArrayList<Course>();
		String order = COURSE_C_TITLE + " ASC";
		Cursor c = db.query(COURSE_TABLE, null, null, null, null, null, order);
		c.moveToFirst();
		
		while (c.isAfterLast() == false) {
			Course course = new Course();
			String due_date=c.getString(c.getColumnIndex(COURSE_C_DATE));
			
			int due_date_month = 0;
			int due_date_year = 0;
			if(due_date!=null){
				String[] due_date_split=due_date.split("-");
		if(due_date_split.length>2){
				due_date_month=Integer.parseInt(due_date_split[1]);
				due_date_year=Integer.parseInt(due_date_split[2]);
			}else{
				due_date_month=04;
				due_date_year=2015;
			}
			}
			//if(month==due_date_month&&year==due_date_year){
			course.setModId(c.getInt(c.getColumnIndex(COURSE_C_ID)));
			course.setLocation(c.getString(c.getColumnIndex(COURSE_C_LOCATION)));
			course.setProgress(this.getCourseProgress(course.getModId()));
			course.setVersionId(c.getDouble(c.getColumnIndex(COURSE_C_VERSIONID)));
			course.setTitlesFromJSONString(c.getString(c.getColumnIndex(COURSE_C_TITLE)));
			course.setImageFile(c.getString(c.getColumnIndex(COURSE_C_IMAGE)));
			course.setLangsFromJSONString(c.getString(c.getColumnIndex(COURSE_C_LANGS)));
			course.setShortname(c.getString(c.getColumnIndex(COURSE_C_SHORTNAME)));
			courses.add(course);
		//	}
			c.moveToNext();
		}
		c.close();
		
		return courses;
	}
	public ArrayList<CourseAchievments> getCoursesForAchievements() {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<CourseAchievments> courses = new ArrayList<CourseAchievments>();
		String order = CCH_COURSE_TITLE + " ASC";
		Cursor c = db.query(COURSES_TABLE, null, null, null, null, null, order);
		c.moveToFirst();
		
		while (c.isAfterLast() == false) {
			CourseAchievments course = new CourseAchievments();
			//if(month==due_date_month&&year==due_date_year){
			course.setCourseName(c.getString(c.getColumnIndex(CCH_COURSE)));
			course.setId(c.getString(c.getColumnIndex(BaseColumns._ID)));
			course.setRecordId(c.getString(c.getColumnIndex(CCH_COURSE_RECORD_ID)));
			course.setCourseAttempts(c.getString(c.getColumnIndex(CCH_COURSE_ATTEMPTS)));
			course.setPercentage(c.getString(c.getColumnIndex(CCH_COURSE_PERCENTAGE_COMPLETED)));
			course.setCourseKSAStatus(c.getString(c.getColumnIndex(CCH_COURSE_KSA_STATUS)));
			course.setCourseTitle(c.getString(c.getColumnIndex(CCH_COURSE_TITLE)));
			course.setDateTaken(c.getString(c.getColumnIndex(CCH_COURSE_DATE_TAKEN)));
			course.setScore(c.getString(c.getColumnIndex(CCH_COURSE_SCORE)));
			courses.add(course);
		//	}
			c.moveToNext();
		}
		c.close();
		
		return courses;
	}
	
	public Course getCourse(long modId) {
		SQLiteDatabase db = this.getWritableDatabase();
		Course m = null;
		String s = COURSE_C_ID + "=?";
		String[] args = new String[] { String.valueOf(modId) };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, null);
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
	
	public ArrayList<Course> getCourseList(long modId) {
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Course> courses = new ArrayList<Course>();
		String s = COURSE_C_ID + "=?";
		String[] args = new String[] { String.valueOf(modId) };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, null);
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
	public String getCourseLocation(String short_name) {
		SQLiteDatabase db = this.getWritableDatabase();
		String m = null;
		String s = COURSE_C_SHORTNAME + "=?";
		String[] args = new String[] { short_name };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, null);
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			m=c.getString(c.getColumnIndex(COURSE_C_LOCATION));
			c.moveToNext();
		}
		c.close();
		
		return m;
	}
	
	public String getCourseImage(String short_name) {
		SQLiteDatabase db = this.getWritableDatabase();
		String m = null;
		String s = COURSE_C_SHORTNAME + "=?";
		String[] args = new String[] { short_name };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, null);
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			m=c.getString(c.getColumnIndex(COURSE_C_IMAGE));
			c.moveToNext();
		}
		c.close();
		
		return m;
	}
	public String getCourseTitle(String short_name) {
		SQLiteDatabase db = this.getWritableDatabase();
		String m = null;
		String s = COURSE_C_SHORTNAME + "=?";
		String[] args = new String[] { short_name };
		Cursor c = db.query(COURSE_TABLE, null, s, args, null, null, null);
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			m=c.getString(c.getColumnIndex(COURSE_C_TITLE));
			c.moveToNext();
		}
		c.close();
		
		return m;
	}
	public void insertLog(int modId, String digest, String data, boolean completed){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TRACKER_LOG_C_COURSEID, modId);
		values.put(TRACKER_LOG_C_ACTIVITYDIGEST, digest);
		values.put(TRACKER_LOG_C_DATA, data);
		values.put(TRACKER_LOG_C_COMPLETED, completed);
		db.insertOrThrow(TRACKER_LOG_TABLE, null, values);
		
	}
	
	public float getCourseProgress(int modId){
		SQLiteDatabase db = this.getReadableDatabase();
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
		
		//This was necessary to fix a division by zero error
		float progress = 0;
		if(noActs==0){
			progress=0;
		}else if(noActs>0){
			progress=noComplete*100/noActs;
		}
		
		return progress;
	}
	
	public String getCourseProgressCompleted(int month,int year){
		SQLiteDatabase db = this.getReadableDatabase();
		Double percentage_completed;
		String percentage = null;
		String sql = "SELECT a."+ ACTIVITY_C_ID + ", " +
				" a."+ACTIVITY_C_COURSEID+", "+
				" m."+COURSE_C_DATE+", "+
				"l."+ TRACKER_LOG_C_ACTIVITYDIGEST + 
				" as d FROM "+ACTIVITY_TABLE + " a " +
				", "+COURSE_TABLE+" m "+
				" LEFT OUTER JOIN (SELECT DISTINCT " +TRACKER_LOG_C_ACTIVITYDIGEST +" FROM " + TRACKER_LOG_TABLE + 
									" WHERE " + TRACKER_LOG_C_COMPLETED + "=1 ) l " +
									" ON a."+ ACTIVITY_C_ACTIVITYDIGEST +" = l."+TRACKER_LOG_C_ACTIVITYDIGEST+
									" WHERE a."+ACTIVITY_C_COURSEID+" = m."+
										COURSE_C_ID;
		Cursor c = db.rawQuery(sql,null);
		int noActs = c.getCount();
		int noComplete = 0;
		c.moveToFirst();
		if(c.getCount()>0){
			String due_date=c.getString(c.getColumnIndex(COURSE_C_DATE));
			
			int due_date_month = 0;
			int due_date_year = 0;
			if(due_date!=null){
				String[] due_date_split=due_date.split("-");
		if(due_date_split.length>2){
				due_date_month=Integer.parseInt(due_date_split[1]);
				due_date_year=Integer.parseInt(due_date_split[2]);
			}else{
				due_date_month=04;
				due_date_year=2015;
			}
			}
		while (c.isAfterLast() == false) {
			if(c.getString(c.getColumnIndex("d"))!=null){
				noComplete++;
		}
			c.moveToNext();
		}
		if(noActs==0){
			percentage= "0";
		}else{
		percentage_completed=((double)noComplete/noActs)*100;
		percentage=String.format("%.0f", percentage_completed);
		}
		}else{
			percentage ="0";
		}
		c.close();
		
		return percentage;
	}
	public String getCourseProgressCompletedAchievements(){
		SQLiteDatabase db = this.getReadableDatabase();
		Double percentage_completed;
		String percentage = null;
		String sql = "Select module.title, activity.modid,trackerlog.modid from module,activity,trackerlog where trackerlog.completed=1 and activity.modid=trackerlog.modid ";
		Cursor c = db.rawQuery(sql,null);
		//int noActs = c.getCount();
		int noComplete = c.getCount();
		c.moveToFirst();
		/*
		while (c.isAfterLast() == false) {
			if(c.getString(c.getColumnIndex("d")) != null){
				noComplete++;
			}
			c.moveToNext();
		}*/
		if(noComplete==0){
			percentage= "0";
		}else{
		//percentage_completed=((double)noComplete/noActs)*100;
		//percentage=String.format("%.0f", percentage_completed);
			percentage=String.valueOf(noComplete);
		}
		c.close();
		
		return percentage;
	}
	
	public String getCourseDate(){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "Select date from module";
		String course_date=null;
		Cursor c = db.rawQuery(sql,null);
		c.moveToFirst();
		
		while (c.isAfterLast() == false) {
			if(c.getString(c.getColumnIndex("date"))!=null){
			course_date=c.getString(c.getColumnIndex("date"));
			}
			c.moveToNext();
		}
		
		c.close();
		
		return course_date;
	}
	public String getCourseProgressUnCompleted(){
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT a."+ ACTIVITY_C_ID + ", " +
				"l."+ TRACKER_LOG_C_ACTIVITYDIGEST + 
				" as d FROM "+ACTIVITY_TABLE + " a " +
				" LEFT OUTER JOIN (SELECT DISTINCT " +TRACKER_LOG_C_ACTIVITYDIGEST +" FROM " + TRACKER_LOG_TABLE + 
									" WHERE " + TRACKER_LOG_C_COMPLETED + "=0 ) l " +
									" ON a."+ ACTIVITY_C_ACTIVITYDIGEST +" = l."+TRACKER_LOG_C_ACTIVITYDIGEST;
		Cursor c = db.rawQuery(sql,null);
		int noActs = c.getCount();
		int noUncomplete = 0;
		c.moveToFirst();
		while (c.isAfterLast() == false) {
			if(c.getString(c.getColumnIndex("d")) != null){
				noUncomplete++;
			}
			c.moveToNext();
		}
		c.close();
		Double  percentage_completed=((double)noUncomplete/noActs)*100;
		String percentage=String.format("%.0f", percentage_completed);
		c.close();
		
		return percentage;
	}
	
	
	public float getSectionProgress(int modId, int sectionId){
		SQLiteDatabase db = this.getReadableDatabase();
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
		SQLiteDatabase db = this.getWritableDatabase();
		// delete quiz results
		this.deleteQuizResults(modId);
		
		String s = TRACKER_LOG_C_COURSEID + "=?";
		String[] args = new String[] { String.valueOf(modId) };
		
		return db.delete(TRACKER_LOG_TABLE, s, args);
		
	}
	
	public void deleteCourse(int modId){
		// delete log
		SQLiteDatabase db = this.getWritableDatabase();
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
		SQLiteDatabase db = this.getReadableDatabase();
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
		SQLiteDatabase db = this.getWritableDatabase();
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
		SQLiteDatabase db = this.getWritableDatabase();
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
		SQLiteDatabase db = this.getReadableDatabase();
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
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TRACKER_LOG_C_SUBMITTED, 1);
		
		return db.update(TRACKER_LOG_TABLE, values, TRACKER_LOG_C_ID + "=" + rowId, null);
	}
	
	public long insertQuizResult(String data, String title,int modId){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(QUIZRESULTS_C_DATA, data);
		values.put(QUIZRESULTS_C_TITLE, title);
		values.put(QUIZRESULTS_C_COURSEID, modId);
		return db.insertOrThrow(QUIZRESULTS_TABLE, null, values);
	}
	
	public Payload getUnsentQuizResults(){
		SQLiteDatabase db = this.getWritableDatabase();
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
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(QUIZRESULTS_C_SENT, 1);
		
		return db.update(QUIZRESULTS_TABLE, values, QUIZRESULTS_C_ID + "=" + rowId, null);
	}
	
	public void deleteQuizResults(int modId){
		SQLiteDatabase db = this.getWritableDatabase();
		// delete any quiz attempts
		String s = QUIZRESULTS_C_COURSEID + "=?";
		String[] args = new String[] { String.valueOf(modId) };
		db.delete(QUIZRESULTS_TABLE, s, args);
	}
	
	public boolean activityAttempted(long modId, String digest){
		SQLiteDatabase db = this.getWritableDatabase();
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
		SQLiteDatabase db = this.getWritableDatabase();
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
	public ArrayList<POCSections> getPocSections(String sub_section)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<POCSections> list=new ArrayList<POCSections>();	
		
		if(doesTableExists(POC_SECTIONS_TABLE)){
		String strQuery="select * from "+POC_SECTIONS_TABLE	
				+" where "+CCH_SUB_SECTION
				+" = '"+sub_section+"'";
		
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				POCSections sections = new POCSections();
					
					try{
						sections.setSectionId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						sections.setSectionName(c.getString(c.getColumnIndex(CCH_SECTION_NAME)));
						sections.setSectionShortname(c.getString(c.getColumnIndex(CCH_SECTION_SHORTNAME)));
						sections.setSectionUrl(c.getString(c.getColumnIndex(CCH_SECTION_URL)));
						sections.setSubSection(c.getString(c.getColumnIndex(CCH_SUB_SECTION)));
						   list.add(sections);
					
				   c.moveToNext();
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			c.close();
			
		}else{
			createPocSectionTable(db);
		}
			return list;	
	}
	public ArrayList<POCSections> getPocSection(String shortname)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<POCSections> list=new ArrayList<POCSections>();	
		
		if(doesTableExists(POC_SECTIONS_TABLE)){
		String strQuery="select * from "+POC_SECTIONS_TABLE	
				+" where "+CCH_SECTION_SHORTNAME
				+" = '"+shortname+"'";
		
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				POCSections sections = new POCSections();
					
					try{
						sections.setSectionId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						sections.setSectionName(c.getString(c.getColumnIndex(CCH_SECTION_NAME)));
						sections.setSectionShortname(c.getString(c.getColumnIndex(CCH_SECTION_SHORTNAME)));
						sections.setSectionUrl(c.getString(c.getColumnIndex(CCH_SECTION_URL)));
						sections.setSubSection(c.getString(c.getColumnIndex(CCH_SUB_SECTION)));
						   list.add(sections);
					
				   c.moveToNext();
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			c.close();
			
		}else{
			createPocSectionTable(db);
		}
			return list;	
	}
	public ArrayList<Activity> getActivitiesDue(int max){
		
		ArrayList<Activity> activities = new ArrayList<Activity>();
		SQLiteDatabase db = this.getReadableDatabase();
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
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<String> quizzes = new ArrayList<String>();
		String s = QUIZRESULTS_C_COURSEID + "=? ";
		String[] args = new String[] { String.valueOf(courseid) };
		Cursor c = db.query(QUIZRESULTS_TABLE, null, s, args, null, null, null);
		c.moveToFirst();
		int quiznum = 1;
		HashMap<String, Integer> qt = new HashMap<String, Integer>();

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
		SQLiteDatabase db = this.getWritableDatabase();
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
	
	public ArrayList<CourseAchievments> getQuizResultsForAchievements(int courseid)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<CourseAchievments> quizzes = new ArrayList<CourseAchievments>();
		String s = QUIZRESULTS_C_COURSEID + "=? ";
		String[] args = new String[] { String.valueOf(courseid) };
		Cursor c = db.query(QUIZRESULTS_TABLE, null, s, args, null, null, null);
		c.moveToFirst();
		int quiznum = 1;
		HashMap<String, Integer> qt = new HashMap<String, Integer>();

		while (c.isAfterLast() == false) {
			CourseAchievments results=new CourseAchievments();
			String data = c.getString(c.getColumnIndex(QUIZRESULTS_C_DATA));
			String data2 = c.getString(c.getColumnIndex(QUIZRESULTS_C_TITLE));
			try {
				JSONObject j=null;
				JSONObject j2=null;
					j = new JSONObject(data);
				if(data2!=null){
					j2 = new JSONObject(data2);
				}
				String t = "Quiz ";
				if (qt.containsKey(j.getString("quiz_id"))) {
					t += qt.get(j.getString("quiz_id")) + " retake";
					if(j2!=null){
					results.setType(j2.getString("title")+" retake");
					}else{
						results.setType(" ");
					}
				} else {
					t += quiznum;
					qt.put(j.getString("quiz_id"), quiznum);
				}
				if(j2!=null){
				results.setCourseName(j2.getString("course"));
				results.setCourseSection(j2.getString("section"));
				results.setType(j2.getString("title"));
				}else{
					results.setCourseName(" ");
					results.setCourseSection(" ");
					results.setType(" ");
				}
				results.setScore(j.getString("score") + "/" + j.getString("maxscore"));
				Double percentage=((double)Integer.valueOf(j.getString("score"))/Integer.valueOf(j.getString("maxscore")))*100;
				String percentage_value=String.format("%.0f", percentage);
				results.setPercentage(percentage_value);
				results.setDateTaken(c.getString(c.getColumnIndex(QUIZRESULTS_C_DATETIME)));
				//quizzes.add(t + ": "+ j.getString("score") + "/" + j.getString("maxscore"));
				quiznum++;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			quizzes.add(results);
			c.moveToNext();
		}
		c.close();
		
		return quizzes;
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
		SQLiteDatabase db = this.getWritableDatabase();
		String userid = prefs.getString(ctx.getString(R.string.prefs_username), "noid"); 
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
		SQLiteDatabase db = this.getReadableDatabase();
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
		SQLiteDatabase db = this.getWritableDatabase();
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
		
		// Insert initial information 
		//if (! hasRowsSW(CCH_SW_TABLE)) 	{	
			String userid = "David"; //prefs.getString(ctx.getString(R.string.prefs_username), "noid"); 
			ContentValues values = new ContentValues();
			values.put(CCH_SW_STAFF_ID, userid);
			values.put(CCH_SW_LEGAL_STATUS, "");
			values.put(CCH_SW_PROFILE_STATUS, "");
			values.put(CCH_SW_PROFILE_RESPONSES, "");
			values.put(CCH_SW_MONTH_PLAN, "");
			values.put(CCH_SW_MONTH_PLAN_LASTUPDATE, "");
			db.insertOrThrow(CCH_SW_TABLE, null, values);
		//}
		
		l_sql = "create table if not exists " + CCH_SW_ROUTINE_TABLE + " (" + 
				CCH_SW_ROUTINE_ID + " integer primary key autoincrement, " + 
				CCH_SW_ROUTINE_TOD + " text, " + 
				CCH_SW_ROUTINE_PROFILE + " text, " + 
				CCH_SW_ROUTINE_PLAN + " text, " + 
				CCH_SW_ROUTINE_ACTION + " text default '', " + 
				CCH_SW_ROUTINE_ORDER + " integer default 0)" ; 
		db.execSQL(l_sql);
		//if (! hasRowsSW(CCH_SW_ROUTINE_TABLE)) 	{	
			insertDefaultRoutineValues(db);
		//}
		
		l_sql = "create table if not exists " + CCH_SW_ROUTINE_TODO_TABLE + " (" + 
				CCH_SW_ROUTINE_TODO_ID + " integer primary key, " + 
				CCH_SW_ROUTINE_TODO_STAFF_ID + " text, " + 
				CCH_SW_ROUTINE_TODO_PROFILE + " text, " + 
				CCH_SW_ROUTINE_TODO_PLAN + " text, " + 
				CCH_SW_ROUTINE_TODO_YEAR + " int, " + 
				CCH_SW_ROUTINE_TODO_MONTH + " int, " + 
				CCH_SW_ROUTINE_TODO_DAY + " int, " + 
				CCH_SW_ROUTINE_TODO_TOD + " text default '', " + 
				CCH_SW_ROUTINE_TODO_TIMEDONE + " text default '', " + 
				CCH_SW_ROUTINE_TODO_ORDER + " integer default 0)" ; 
		db.execSQL(l_sql);
		
	}
	
	public void runSWReset()
	{
		SQLiteDatabase db = this.getWritableDatabase();		 
		Log.v(TAG,"Resetting The table");

		if (! isTableExists("sw_fix",db)) { // reset information
			// Reset all routines
			String sql1 = "DELETE FROM "+ CCH_SW_ROUTINE_TODO_TABLE + " WHERE 1=1";
			db.execSQL(sql1);
			
			// Reset plan and profile
			Cursor cursor = db.rawQuery("select sw_legal from stayingwell", null);
			cursor.moveToFirst();
			if(cursor!=null) {
				if(cursor.getString(cursor.getColumnIndex(CCH_SW_LEGAL_STATUS)).equalsIgnoreCase("agreed")) {
					updateSWInfo(CCH_SW_LEGAL_STATUS, "agreed", db);
				}else{
					updateSWInfo(CCH_SW_LEGAL_STATUS, "", db);
				
				}
			}
			updateSWInfo(CCH_SW_STAFF_ID, "David", db);
			updateSWInfo(CCH_SW_PROFILE_STATUS, "", db);
			updateSWInfo(CCH_SW_PROFILE_RESPONSES, "", db);
			updateSWInfo(CCH_SW_MONTH_PLAN, "",db);
			updateSWInfo(CCH_SW_MONTH_PLAN_LASTUPDATE, "",db);
			
			// Indicate that SW info has been reset
			String l_sql = "create table if not exists sw_fix (ch integer default 0)";
			db.execSQL(l_sql);

			ContentValues v = new ContentValues();
			v.put("ch", 1);
			db.insertOrThrow("sw_fix", null, v);
			cursor.close();
		}
	}
	
	public boolean isTableExists(String tableName,SQLiteDatabase mDatabase) {
		try {		
			Cursor cursor = mDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
			if(cursor!=null) {
				if(cursor.getCount()>0) {
	               cursor.close();
	               return true;
				}
	            cursor.close();
			}
			return false;
		} catch (SQLiteException e) {
			Log.v(TAG,e.getMessage());
		}
		
		return false;
	}
	
	public boolean doesTableExists(String tableName) {
		try {		
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
			if(cursor!=null) {
				if(cursor.getCount()>0) {
	               cursor.close();
	               return true;
				}
	            cursor.close();
			}
			return false;
		} catch (SQLiteException e) {
			Log.v(TAG,e.getMessage());
		}
		
		return false;
	}
	
	public String getSWInfo(String field) 
	{			
		String userid = "David"; //prefs.getString(ctx.getString(R.string.prefs_username), "noid");      

		SQLiteDatabase db = this.getReadableDatabase();		 
		Cursor cursor = db.query(CCH_SW_TABLE, new String[] { field }, CCH_SW_STAFF_ID + "=?",
			            new String[] { String.valueOf(userid) }, null, null, null, null); 	        
		
		if (cursor == null || cursor.getCount()==0) {
			return null;	    	
		}
		
		cursor.moveToFirst();
		
		String info = cursor.getString(0);
		
		cursor.close();
					    
		// return value
		return info;
	}
	
	public void updateSWInfo(String field, String value,SQLiteDatabase db ) {
        ContentValues values = new ContentValues();
        values.put(field, value); 
        Log.e("CCH","Updating "+field+" with "+value);
		String userid = "David";//prefs.getString(ctx.getString(R.string.prefs_username), "noid");      
        db.update(CCH_SW_TABLE, values, CCH_SW_STAFF_ID + "='"+userid+"'", null);
	}

	public void updateSWInfo(String field, String value) {
		SQLiteDatabase db = this.getWritableDatabase(); 
        ContentValues values = new ContentValues();
        values.put(field, value); 
        Log.e("CCH","Updating "+field+" with "+value);
		String userid = "David";//prefs.getString(ctx.getString(R.string.prefs_username), "noid");      
        db.update(CCH_SW_TABLE, values, CCH_SW_STAFF_ID + "='"+userid+"'", null);
                     
	}
	
	public ArrayList<RoutineActivity> getSWRoutineActivities()
	{
    	String profile = getSWInfo(DbHelper.CCH_SW_PROFILE_STATUS);
    	String plan = getSWInfo(DbHelper.CCH_SW_MONTH_PLAN);
    	ArrayList<RoutineActivity> todos = null;
    	
    	if (profile!=null && plan!=null)
    	{
    		Calendar c  = Calendar.getInstance();
    		String year = String.valueOf(c.get(Calendar.YEAR));
    		String month = String.valueOf(c.get(Calendar.MONTH));
    		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
    		String timeofday = getTime();
    		todos = getSWRoutineActivities(year, month, day, profile, plan, timeofday);
    	}
    	
    	return todos;
	}
	
	private ArrayList<RoutineActivity> getSWRoutineActivities(String year, String month, String day, String profile, String plan, String timeofday) 
	{			
		    SQLiteDatabase db = this.getReadableDatabase(); 

			String userid = "David"; //prefs.getString(ctx.getString(R.string.prefs_username), "noid");      
			ArrayList<RoutineActivity> list = new ArrayList<RoutineActivity>();
			
			String strQuery=  " SELECT " + CCH_SW_ROUTINE_ACTION + ", " + CCH_SW_ROUTINE_ORDER		         
		             		+ "   FROM " + CCH_SW_ROUTINE_TABLE
		             		+ "  WHERE " + CCH_SW_ROUTINE_PROFILE + "= '"+profile+"' "
		             		+ "    AND " + CCH_SW_ROUTINE_PLAN + "= '"+plan+"' "
		             		+ "    AND " + CCH_SW_ROUTINE_TOD + "= '"+timeofday+"' ";	
			 			
			Cursor c = db.rawQuery(strQuery, null);
		
			c.moveToFirst();
			
			while (c.isAfterLast()==false) {
				   String action = c.getString(c.getColumnIndex(CCH_SW_ROUTINE_ACTION));
				   String order = c.getString(c.getColumnIndex(CCH_SW_ROUTINE_ORDER));
				  
				   RoutineActivity ra = new RoutineActivity();
				   ra.setUserid(userid);
				   ra.setYear(year);
				   ra.setMonth(month);
				   ra.setDay(day);
				   ra.setTimeofday(timeofday);
				   ra.setProfile(profile);
				   ra.setPlan(plan);
				   ra.setOrder(order);
				   ra.setAction(action);
				   
				   if (this.getSWRoutineDoneActivity(year, month, day, profile, plan, timeofday, order) == null)
				   {
					   ra.setDone(false);
				   } else {
					   ra.setDone(true);
				   }
				   list.add(ra);
				   c.moveToNext();						
			}
			c.close();
			
			return list;	
	}
	
	public String getSWRoutineDoneActivity(String year, String month, String day, String profile, String plan, String timeofday, String order) 
	{	
	        SQLiteDatabase db = this.getReadableDatabase(); 

			String userid = "David"; //prefs.getString(ctx.getString(R.string.prefs_username), "noid");      

			String strQuery=  " SELECT " + CCH_SW_ROUTINE_TODO_ID 		         
             		+ "   FROM " + CCH_SW_ROUTINE_TODO_TABLE
             		+ "   WHERE " + CCH_SW_ROUTINE_TODO_STAFF_ID + "= '"+userid+"' "
             		+ "    AND " + CCH_SW_ROUTINE_TODO_YEAR + "= '"+year+"' "
             		+ "    AND " + CCH_SW_ROUTINE_TODO_MONTH + "= '"+month+"' "
             		+ "    AND " + CCH_SW_ROUTINE_TODO_DAY + "= '"+day+"' "
             		+ "    AND " + CCH_SW_ROUTINE_TODO_PROFILE + "= '"+profile+"' "
             	    + "    AND " + CCH_SW_ROUTINE_TODO_PLAN + "= '"+plan+"' "
             	    + "    AND " + CCH_SW_ROUTINE_TODO_ORDER + "= '"+order+"' "
             		+ "    AND " + CCH_SW_ROUTINE_TODO_TOD + "= '"+timeofday+"' ";	
	 			
			Cursor c = db.rawQuery(strQuery, null);
			
			if (c == null || c.getCount()==0) {
				c.close();
				return null;	    	
			}
			
			c.moveToFirst();
			String i = c.getString(0);
			c.close();
			
			return i;
	}
	
	public void insertSWRoutineDoneActivity(String uuid)
	{
		SQLiteDatabase db = this.getWritableDatabase();		 

		String userid = "David"; //prefs.getString(ctx.getString(R.string.prefs_username), "noid");      
    	String[] data = uuid.split("-");
		Long endtime = System.currentTimeMillis();	

		ContentValues values = new ContentValues();
		values.put(CCH_SW_ROUTINE_TODO_STAFF_ID, userid);
		values.put(CCH_SW_ROUTINE_TODO_YEAR, Integer.valueOf(data[1]));
		values.put(CCH_SW_ROUTINE_TODO_MONTH, Integer.valueOf(data[2]));
		values.put(CCH_SW_ROUTINE_TODO_DAY, Integer.valueOf(data[3]));
		values.put(CCH_SW_ROUTINE_TODO_TOD,	data[4]);
		values.put(CCH_SW_ROUTINE_TODO_PROFILE,data[5]);
		values.put(CCH_SW_ROUTINE_TODO_PLAN,data[6]);
		values.put(CCH_SW_ROUTINE_TODO_ORDER, Integer.valueOf(data[7])); 
		values.put(CCH_SW_ROUTINE_TODO_TIMEDONE, endtime.toString());
		db.insertOrThrow(CCH_SW_ROUTINE_TODO_TABLE, null, values);
		
		String v = values.toString();
		v = v.replace("=", "':'");
		v = v.replace(" ","', '");
		v = "{'type':'activity', '"+v+"'}";
		JSONObject d=new JSONObject();
	    try {
	    	d.put("type", "activity");
	    	d.put("values", v);
	    	d.put("ver",getVersionNumber(ctx));
	    	d.put("battery",getBatteryStatus(ctx));
	    	d.put("device", getDeviceName());
			d.put("imei", getDeviceImei(ctx));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.insertCCHLog("Staying Well", d.toString(), endtime.toString(), endtime.toString());
		
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
        	 // Closing database connection
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
        db.update(CCH_USER_TABLE, values, CCH_STAFF_ID + "= '"+u.getUsername()+"'", null);
        // Closing database connection        
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
        db.update(CCH_USER_TABLE, values, CCH_STAFF_ID + "= '" + staff_id+"'", null);
        // Closing database connection        
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
	


public long findItemCount(String table, String searchedBy,
			String searchValue) {
		
		return findItemCount(table, searchedBy, searchValue, COL_EVENT_DUE_DATE);
	}

	public long findItemCount(String table, String searchedBy,
			String searchValue,String dueDateCol) {
		SQLiteDatabase db = this.getReadableDatabase();
		if(doesTableExists(TARGET_TABLE)){
			String strQuery = "select count(" + BaseColumns._ID + ")as cnt from "
				+ table + " where " + searchedBy + " = '" + searchValue + "' "
				+ " and "+CCH_STATUS+ " = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"' ";
		try {
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			return c.getLong(0);
		} catch (Exception e) {

		}
		}
		return 0l;
	}
	
	public long findTargetCount(String table, String searchedBy,
			String searchValue,String dueDateCol,String targetType) {
		SQLiteDatabase db = this.getReadableDatabase();
		if(doesTableExists(TARGET_TABLE)){
		String strQuery = "select count(" + BaseColumns._ID + ")as cnt from "
				+ table + " where " + searchedBy + " = '" + searchValue + "' "
				+ " and "+CCH_STATUS+ " = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"' "
				+ " and "+CCH_TARGET_TYPE+ " = '"+targetType+"' ";
		try {
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			return c.getLong(0);
		} catch (Exception e) {

		}
		}
		return 0l;
	}

	public long getCount(String duration,String target_type) {

		return findTargetCount(TARGET_TABLE, CCH_REMINDER, duration,CCH_DUE_DATE,target_type);
	}

	public long getCoverageCount(String duration) {

		return findItemCount(COVERAGE_SET_TABLE, COL_COVERAGE_SET_PERIOD,
				duration,COL_COVERAGE_DUE_DATE);
	}

	public long getLearningCount(String duration) {

		return findItemCount(LEARNING_TABLE, COL_LEARNING_PERIOD, duration,COL_LEARNING_DUE_DATE);
	}
	
	public long getOtherCount(String duration) {

		return findItemCount(OTHER_TABLE, COL_OTHER_PERIOD, duration,COL_OTHER_DUE_DATE);
	}
	
	
	public long findItemIdCount(String table, String searchedBy,
			String searchValue) {
		
		return findItemCount(table, searchedBy, searchValue, COL_EVENT_DUE_DATE);
	}

	public long findItemIdCount(String table, String searchedBy,
			String searchValue,String startDateCol) {
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	 
		 SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "dd-MM-yyyy", Locale.getDefault());
		 long starDateAsTimestamp = 0;
		long today = 0;
		Date date1 = null;
		Date date2=null;
		SQLiteDatabase db = this.getReadableDatabase();
		if(doesTableExists(TARGET_TABLE)){
		String strQuery = "select count(" + BaseColumns._ID + ")as cnt, "+CCH_START_DATE+" from "
				+ table + " where " + searchedBy + " = '" + searchValue + "' "
						+ " and "+COL_SYNC_STATUS+ " = '"+"new_record"+"' ";
		
		try {
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			int total_targets = 0;
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(COL_START_DATE)));
				date2= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
				starDateAsTimestamp=date1.getTime();
				today=date2.getTime();
				if(starDateAsTimestamp<=today){
					event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
					list.add(event_targets);
					total_targets=list.size();
				}
				   c.moveToNext();						
			}
			c.close();
			
			return total_targets;
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		return 0l;
	}

	public long getIdCount(String duration) {

		return findItemIdCount(TARGET_TABLE, CCH_REMINDER, duration,CCH_START_DATE);
	}


	
	private void insertDefaultRoutineValues(SQLiteDatabase db) 
	{
		String insertQuery = "INSERT INTO '"+CCH_SW_ROUTINE_TABLE+"' " 
				+ "SELECT '1' AS '"+CCH_SW_ROUTINE_ID+"', 'afternoon' AS '"+CCH_SW_ROUTINE_TOD+"', 'mary' AS '"+CCH_SW_ROUTINE_PROFILE+"', 'appearance' AS '"+CCH_SW_ROUTINE_PLAN+"', 'Sip on <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/homemade lemonade.html\">lemonade</a> throughout the day to stay hydrated ' AS '"+CCH_SW_ROUTINE_ACTION+"', '1' AS '"+CCH_SW_ROUTINE_ORDER+"' "
				+ "UNION SELECT '2', 'afternoon', 'mary', 'appearance', 'Eat a healthy <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lunch.html\">lunch</a>', '2' "
				+ "UNION SELECT '3', 'afternoon', 'mary', 'appearance', 'Take a break: <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/afternoon stretch.html\">Stretch</a> every 1 hour', '3' "
				+ "UNION SELECT '4', 'evening', 'mary', 'appearance', 'Sit under a shade in the evening sun, shade of a neem tree is ideal', '1' "
				+ "UNION SELECT '5', 'evening', 'mary', 'appearance', 'Close your eyes, focus on your breath and take deep breaths in and out for 10 minutes', '2' "
				+ "UNION SELECT '6', 'evening', 'mary', 'appearance', 'Once you come back into your home, apply a <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/facial mask.html\">facial mask</a> ', '3' "
				+ "UNION SELECT '7', 'evening', 'mary', 'appearance', 'Eat a light <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/dinner.html\">dinner</a>, 2 hours before you go to sleep to allow food to digest properly ', '4' "
				+ "UNION SELECT '8', 'morning', 'mary', 'appearance', 'Sip on <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> when you wake up in the morning', '1' "
				+ "UNION SELECT '9', 'morning', 'mary', 'appearance', 'Brush your teeth, and <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/tongue scraping.html\">scrape your tongue</a> to remove any coating', '2' "
				+ "UNION SELECT '10', 'morning', 'mary', 'appearance', '<a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/hair treatment.html\">Nourish hair</a> with coconut oil', '3' "
				+ "UNION SELECT '11', 'morning', 'mary', 'appearance', 'Once a week, <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/massage body.html\">massage your body</a> with coconut oil ', '4' "
				+ "UNION SELECT '12', 'morning', 'mary', 'appearance', 'Use <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/neem water.html\">neem water</a> to wash off the oil from your body and hair ', '5' "
				+ "UNION SELECT '13', 'afternoon', 'mary', 'hurt', 'Before you eat lunch: Give thanks to your body, your digestive system', '1' "
				+ "UNION SELECT '14', 'afternoon', 'mary', 'hurt', 'Sip on  1 cup <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lemon water.html\">lemon water</a> 2 hours after lunch', '2' "
				+ "UNION SELECT '15', 'evening', 'mary', 'hurt', 'Write details about the incident', '1' "
				+ "UNION SELECT '16', 'evening', 'mary', 'hurt', 'Write how you feel about it', '2' "
				+ "UNION SELECT '17', 'evening', 'mary', 'hurt', 'After you are finished, tear the letter and allow the hurt to go away with it', '3' "
				+ "UNION SELECT '18', 'evening', 'mary', 'hurt', 'Before sleeping, <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/wash feet.html\">wash your feet</a> with room temperature water', '4' "
				+ "UNION SELECT '19', 'evening', 'mary', 'hurt', 'Lie down on your bed, close your eyes and take 10 slow deep breaths. Focus on your breathing while you do this', '5' "
				+ "UNION SELECT '20', 'morning', 'mary', 'hurt', 'Upon waking up, say a prayer to be thankful for the new day.', '1' "
				+ "UNION SELECT '21', 'morning', 'mary', 'hurt', 'Eat a healthy <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a>, before you leave home ', '2' "
				+ "UNION SELECT '22', 'afternoon', 'mary', 'immunity', 'Eat when hungry, avoid skipping meals', '1' "
				+ "UNION SELECT '23', 'afternoon', 'mary', 'immunity', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lemon water.html\">lemon water</a> 1 hour before lunch', '2' "
				+ "UNION SELECT '24', 'afternoon', 'mary', 'immunity', 'Eat <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lunch.html\">lunch</a> about the same time each day, if possible ', '3' "
				+ "UNION SELECT '25', 'evening', 'mary', 'immunity', 'Relax after work by listening to soothing music', '1' "
				+ "UNION SELECT '26', 'evening', 'mary', 'immunity', 'Write down 1 new thing each day that makes you happy', '2' "
				+ "UNION SELECT '27', 'evening', 'mary', 'immunity', 'Eat a fresh cooked <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/dinner.html\">dinner</a>, 3 hours before sleep to allow food to be digested properly', '3' "
				+ "UNION SELECT '28', 'evening', 'mary', 'immunity', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink hot water.html\">hot water</a> 30 minutes before bed', '4' "
				+ "UNION SELECT '29', 'morning', 'mary', 'immunity', 'Upon waking up, find a quiet corner and take 10 deep breaths', '1' "
				+ "UNION SELECT '30', 'morning', 'mary', 'immunity', 'Boil 1 cup of water with 3-4 neem leaves, and sip as a tea', '2' "
				+ "UNION SELECT '31', 'morning', 'mary', 'immunity', 'Massage your  <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/stomach.html\">stomach and intestines for 3 minutes</a>', '3' "
				+ "UNION SELECT '32', 'morning', 'mary', 'immunity', 'Eat an easy to digest <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a>, before you leave home ', '4' "
				+ "UNION SELECT '33', 'morning', 'mary', 'immunity', 'Try not to suppress your urges to urinate or clear bowels', '5' "
				+ "UNION SELECT '34', 'afternoon', 'mary', 'strength', 'Eat <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lunch.html\">lunch</a> at about the same time each day, around 1pm if possible', '1' "
				+ "UNION SELECT '35', 'afternoon', 'mary', 'strength', 'Sip on <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lemon water.html\">lemon water</a> 1 hour after lunch', '2' "
				+ "UNION SELECT '36', 'afternoon', 'mary', 'strength', 'Sit in <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/folded leg squat.html\">folded leg squat</a> for 3 minutes after lunch, if possible ', '3' "
				+ "UNION SELECT '37', 'evening', 'mary', 'strength', 'After work exercise: 20 minute fast walking or running, 20 bicep curls, 10 pushups', '1' "
				+ "UNION SELECT '38', 'evening', 'mary', 'strength', 'Eat a light <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/dinner.html\">dinner</a>', '2' "
				+ "UNION SELECT '39', 'evening', 'mary', 'strength', 'Drink a glass of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> 1 hour after eating dinner', '3' "
				+ "UNION SELECT '40', 'morning', 'mary', 'strength', 'Upon waking up, drink <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> to clear you bowels.', '1' "
				+ "UNION SELECT '41', 'morning', 'mary', 'strength', 'Awaken by <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/morning stretch.html\">stretching</a> your body', '2' "
				+ "UNION SELECT '42', 'morning', 'mary', 'strength', 'Do 10 <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/squats.html\">squats</a> or 10 pushups', '3' "
				+ "UNION SELECT '43', 'morning', 'mary', 'strength', 'Eat a good <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a>, before you leave home ', '4' "
				+ "UNION SELECT '44', 'afternoon', 'mary', 'time', 'Before the day begins, make a commitment to yourself to avoid taking worries or stresses from work back home', '1' "
				+ "UNION SELECT '45', 'afternoon', 'mary', 'time', 'Take a 5 minute breaks every 2 hours at work; close your eyes, breath slowly and feel your heartbeat', '2' "
				+ "UNION SELECT '46', 'afternoon', 'mary', 'time', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lemon water.html\">lemon water</a> 1 hour after lunch', '3' "
				+ "UNION SELECT '47', 'evening', 'mary', 'time', 'Write down 1 positive quality about yourself', '1' "
				+ "UNION SELECT '48', 'evening', 'mary', 'time', 'Write down details of how this quality has helped you and people around you', '2' "
				+ "UNION SELECT '49', 'evening', 'mary', 'time', 'Share details of what you have written with a friend or family member', '3' "
				+ "UNION SELECT '50', 'morning', 'mary', 'time', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> in the morning after brushing teeth', '1' "
				+ "UNION SELECT '51', 'morning', 'mary', 'time', 'Sit outside in the shade, the shade of a tree if possible, in the early morning sun', '2' "
				+ "UNION SELECT '52', 'morning', 'mary', 'time', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/energizing tea.html\">energizing tea</a>', '3' "
				+ "UNION SELECT '53', 'morning', 'mary', 'time', 'Take 10 deep breaths, relax body with each exhalation', '4' "
				+ "UNION SELECT '54', 'morning', 'mary', 'time', 'Eat a healthy <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a> before you leave home', '5' "
				+ "UNION SELECT '55', 'afternoon', 'mary', 'worry', 'Avoid skipping meals, especially when hungry', '1' "
				+ "UNION SELECT '56', 'afternoon', 'mary', 'worry', 'Sip <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/homemade lemonade.html\">lemonade</a> throughout the day to stay hydrated ', '2' "
				+ "UNION SELECT '57', 'afternoon', 'mary', 'worry', 'Take a break to <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/afternoon stretch.html\">stretch</a> every hour', '3' "
				+ "UNION SELECT '58', 'evening', 'mary', 'worry', 'Find a quiet place to sit down, and take 10 slow deep breaths in and out', '1' "
				+ "UNION SELECT '59', 'evening', 'mary', 'worry', 'Eat light, fresh cooked <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/dinner.html\">dinner</a> ', '2' "
				+ "UNION SELECT '60', 'evening', 'mary', 'worry', 'Eat dinner 2-3 hours before bed to allow the body to digest the food fully', '3' "
				+ "UNION SELECT '61', 'evening', 'mary', 'worry', 'Before sleeping, <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/wash feet.html\">wash your feet</a> with room temperature water', '4' "
				+ "UNION SELECT '62', 'morning', 'mary', 'worry', 'Wake up before sunrise', '1' "
				+ "UNION SELECT '63', 'morning', 'mary', 'worry', 'Upon waking up, sit up on the bed, keep your eyes closed and take 10 slow, deep breaths in and out. Focus on your breathing. ', '2' "
				+ "UNION SELECT '64', 'morning', 'mary', 'worry', 'Splash lukewarm or room temperature water on your face after you are up', '3' "
				+ "UNION SELECT '65', 'morning', 'mary', 'worry', 'Take a 15 minute walk outside in the early morning sun, if possible', '4' "
				+ "UNION SELECT '66', 'morning', 'mary', 'worry', 'Eat <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a> in the morning before your leave home', '5' "
				+ "UNION SELECT '67', 'afternoon', 'michael', 'appearance', 'Sip on <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/homemade lemonade.html\">lemonade</a> throughout the day to stay hydrated ', '1' "
				+ "UNION SELECT '68', 'afternoon', 'michael', 'appearance', 'Eat a healthy <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lunch.html\">lunch</a>', '2' "
				+ "UNION SELECT '69', 'afternoon', 'michael', 'appearance', 'Take a break: <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/afternoon stretch.html\">Stretch</a> every 1 hour', '3' "
				+ "UNION SELECT '70', 'evening', 'michael', 'appearance', 'Sit under a shade in the evening sun, shade of a neem tree is ideal', '1' "
				+ "UNION SELECT '71', 'evening', 'michael', 'appearance', 'Close your eyes, focus on your breath and take deep breaths in and out for 10 minutes', '2' "
				+ "UNION SELECT '72', 'evening', 'michael', 'appearance', 'Once you come back into your home, apply a <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/facial mask.html\">facial mask</a> ', '3' "
				+ "UNION SELECT '73', 'evening', 'michael', 'appearance', 'Eat a light <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/dinner.html\">dinner</a>, 2 hours before you go to sleep to allow food to digest properly ', '4' "
				+ "UNION SELECT '74', 'morning', 'michael', 'appearance', 'Sip on <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> when you wake up in the morning', '1' "
				+ "UNION SELECT '75', 'morning', 'michael', 'appearance', 'Brush your teeth, and <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/tongue scraping.html\">scrape your tongue</a> to remove any coating', '2' "
				+ "UNION SELECT '76', 'morning', 'michael', 'appearance', '<a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/hair treatment.html\">Nourish hair</a> with coconut oil', '3' "
				+ "UNION SELECT '77', 'morning', 'michael', 'appearance', 'Once a week, <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/massage body.html\">massage your body</a> with coconut oil ', '4' "
				+ "UNION SELECT '78', 'morning', 'michael', 'appearance', 'Use <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/neem water.html\">neem water</a> to wash off the oil from your body and hair ', '5' "
				+ "UNION SELECT '79', 'afternoon', 'michael', 'hurt', 'Before you eat lunch: Give thanks to your body, your digestive system', '1' "
				+ "UNION SELECT '80', 'afternoon', 'michael', 'hurt', 'Sip on  1 cup <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lemon water.html\">lemon water</a> 2 hours after lunch', '2' "
				+ "UNION SELECT '81', 'evening', 'michael', 'hurt', 'Write details about the incident', '1' "
				+ "UNION SELECT '82', 'evening', 'michael', 'hurt', 'Write how you feel about it', '2' "
				+ "UNION SELECT '83', 'evening', 'michael', 'hurt', 'After you are finished, tear the letter and allow the hurt to go away with it', '3' "
				+ "UNION SELECT '84', 'evening', 'michael', 'hurt', 'Before sleeping, <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/wash feet.html\">wash your feet</a> with room temperature water', '4' "
				+ "UNION SELECT '85', 'evening', 'michael', 'hurt', 'Lie down on your bed, close your eyes and take 10 slow deep breaths. Focus on your breathing while you do this', '5' "
				+ "UNION SELECT '86', 'morning', 'michael', 'hurt', 'Upon waking up, say a prayer to be thankful for the new day.', '1' "
				+ "UNION SELECT '87', 'morning', 'michael', 'hurt', 'Eat a healthy <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a>, before you leave home ', '2' "
				+ "UNION SELECT '88', 'afternoon', 'michael', 'immunity', 'Eat when hungry, avoid skipping meals', '1' "
				+ "UNION SELECT '89', 'afternoon', 'michael', 'immunity', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lemon water.html\">lemon water</a> 1 hour before lunch', '2' "
				+ "UNION SELECT '90', 'afternoon', 'michael', 'immunity', 'Eat <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lunch.html\">lunch</a> about the same time each day, if possible ', '3' "
				+ "UNION SELECT '91', 'evening', 'michael', 'immunity', 'Relax after work by listening to soothing music', '1' "
				+ "UNION SELECT '92', 'evening', 'michael', 'immunity', 'Write down 1 new thing each day that makes you happy', '2' "
				+ "UNION SELECT '93', 'evening', 'michael', 'immunity', 'Eat a fresh cooked <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/dinner.html\">dinner</a>, 3 hours before sleep to allow food to be digested properly', '3' "
				+ "UNION SELECT '94', 'evening', 'michael', 'immunity', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink hot water.html\">hot water</a> 30 minutes before bed', '4' "
				+ "UNION SELECT '95', 'morning', 'michael', 'immunity', 'Upon waking up, find a quiet corner and take 10 deep breaths', '1' "
				+ "UNION SELECT '96', 'morning', 'michael', 'immunity', 'Boil 1 cup of water with 3-4 neem leaves, and sip as a tea', '2' "
				+ "UNION SELECT '97', 'morning', 'michael', 'immunity', 'Massage your  <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/stomach.html\">stomach and intestines for 3 minutes</a>', '3' "
				+ "UNION SELECT '98', 'morning', 'michael', 'immunity', 'Eat an easy to digest <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a>, before you leave home ', '4' "
				+ "UNION SELECT '99', 'morning', 'michael', 'immunity', 'Try not to suppress your urges to urinate or clear bowels', '5' "
				+ "UNION SELECT '100', 'afternoon', 'michael', 'strength', 'Eat <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lunch.html\">lunch</a> at about the same time each day, around 1pm if possible', '1' "
				+ "UNION SELECT '101', 'afternoon', 'michael', 'strength', 'Sip on <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lemon water.html\">lemon water</a> 1 hour after lunch', '2' "
				+ "UNION SELECT '102', 'afternoon', 'michael', 'strength', 'Sit in <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/folded leg squat.html\">folded leg squat</a> for 3 minutes after lunch, if possible ', '3' "
				+ "UNION SELECT '103', 'evening', 'michael', 'strength', 'After work exercise: 20 minute fast walking or running, 20 bicep curls, 10 pushups', '1' "
				+ "UNION SELECT '104', 'evening', 'michael', 'strength', 'Eat a light <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/dinner.html\">dinner</a>', '2' "
				+ "UNION SELECT '105', 'evening', 'michael', 'strength', 'Drink a glass of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> 1 hour after eating dinner', '3' "
				+ "UNION SELECT '106', 'morning', 'michael', 'strength', 'Upon waking up, drink <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> to clear you bowels.', '1' "
				+ "UNION SELECT '107', 'morning', 'michael', 'strength', 'Awaken by <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/morning stretch.html\">stretching</a> your body', '2' "
				+ "UNION SELECT '108', 'morning', 'michael', 'strength', 'Do 10 <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/squats.html\">squats</a> or 10 pushups', '3' "
				+ "UNION SELECT '109', 'morning', 'michael', 'strength', 'Eat a good <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a>, before you leave home ', '4' "
				+ "UNION SELECT '110', 'afternoon', 'michael', 'time', 'Before the day begins, make a commitment to yourself to avoid taking worries or stresses from work back home', '1' "
				+ "UNION SELECT '111', 'afternoon', 'michael', 'time', 'Take a 5 minute breaks every 2 hours at work; close your eyes, breath slowly and feel your heartbeat', '2' "
				+ "UNION SELECT '112', 'afternoon', 'michael', 'time', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lemon water.html\">lemon water</a> 1 hour after lunch', '3' "
				+ "UNION SELECT '113', 'evening', 'michael', 'time', 'Do 1 fun activity in the evening that you enjoy doing: example a hobby, singing, dancing', '1' "
				+ "UNION SELECT '114', 'evening', 'michael', 'time', 'Ask a friend or family member to join you in this activity', '2' "
				+ "UNION SELECT '115', 'morning', 'michael', 'time', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> in the morning after brushing teeth', '1' "
				+ "UNION SELECT '116', 'morning', 'michael', 'time', 'Sit outside in the shade, the shade of a tree if possible, in the early morning sun', '2' "
				+ "UNION SELECT '117', 'morning', 'michael', 'time', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/energizing tea.html\">energizing tea</a>', '3' "
				+ "UNION SELECT '118', 'morning', 'michael', 'time', 'Take 10 deep breaths, relax body with each exhalation', '4' "
				+ "UNION SELECT '119', 'morning', 'michael', 'time', 'Eat a healthy <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a> before you leave home', '5' "
				+ "UNION SELECT '120', 'afternoon', 'michael', 'worry', 'Avoid skipping meals, especially when hungry', '1' "
				+ "UNION SELECT '121', 'afternoon', 'michael', 'worry', 'Sip <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/homemade lemonade.html\">lemonade</a> throughout the day to stay hydrated ', '2' "
				+ "UNION SELECT '122', 'afternoon', 'michael', 'worry', 'Take a break to <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/afternoon stretch.html\">stretch</a> every hour', '3' "
				+ "UNION SELECT '123', 'evening', 'michael', 'worry', 'Find a quiet place to sit down, and take 10 slow deep breaths in and out', '1' "
				+ "UNION SELECT '124', 'evening', 'michael', 'worry', 'Eat light, fresh cooked <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/dinner.html\">dinner</a> ', '2' "
				+ "UNION SELECT '125', 'evening', 'michael', 'worry', 'Eat dinner 2-3 hours before bed to allow the body to digest the food fully', '3' "
				+ "UNION SELECT '126', 'evening', 'michael', 'worry', 'Before sleeping, <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/wash feet.html\">wash your feet</a> with room temperature water', '4' "
				+ "UNION SELECT '127', 'morning', 'michael', 'worry', 'Wake up before sunrise', '1' "
				+ "UNION SELECT '128', 'morning', 'michael', 'worry', 'Upon waking up, sit up on the bed, keep your eyes closed and take 10 slow, deep breaths in and out. Focus on your breathing. ', '2' "
				+ "UNION SELECT '129', 'morning', 'michael', 'worry', 'Splash lukewarm or room temperature water on your face after you are up', '3' "
				+ "UNION SELECT '130', 'morning', 'michael', 'worry', 'Take a 15 minute walk outside in the early morning sun, if possible', '4' "
				+ "UNION SELECT '131', 'morning', 'michael', 'worry', 'Eat <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a> in the morning before your leave home', '5' "
				+ "UNION SELECT '132', 'afternoon', 'naana', 'appearance', 'Sip on <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/homemade lemonade.html\">lemonade</a> throughout the day to stay hydrated ', '1' "
				+ "UNION SELECT '133', 'afternoon', 'naana', 'appearance', 'Eat a healthy <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lunch.html\">lunch</a>', '2' "
				+ "UNION SELECT '134', 'afternoon', 'naana', 'appearance', 'Take a break: <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/afternoon stretch.html\">Stretch</a> every 1 hour', '3' "
				+ "UNION SELECT '135', 'evening', 'naana', 'appearance', 'Sit under a shade in the evening sun, shade of a neem tree is ideal', '1' "
				+ "UNION SELECT '136', 'evening', 'naana', 'appearance', 'Close your eyes, focus on your breath and take deep breaths in and out for 10 minutes', '2' "
				+ "UNION SELECT '137', 'evening', 'naana', 'appearance', 'Once you come back into your home, apply a <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/facial mask.html\">facial mask</a> ', '3' "
				+ "UNION SELECT '138', 'evening', 'naana', 'appearance', 'Eat a light <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/dinner.html\">dinner</a>, 2 hours before you go to sleep to allow food to digest properly ', '4' "
				+ "UNION SELECT '139', 'morning', 'naana', 'appearance', 'Sip on <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> when you wake up in the morning', '1' "
				+ "UNION SELECT '140', 'morning', 'naana', 'appearance', 'Brush your teeth, and <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/tongue scraping.html\">scrape your tongue</a> to remove any coating', '2' "
				+ "UNION SELECT '141', 'morning', 'naana', 'appearance', '<a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/hair treatment.html\">Nourish hair</a> with coconut oil', '3' "
				+ "UNION SELECT '142', 'morning', 'naana', 'appearance', 'Once a week, <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/massage body.html\">massage your body</a> with coconut oil ', '4' "
				+ "UNION SELECT '143', 'morning', 'naana', 'appearance', 'Use <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/neem water.html\">neem water</a> to wash off the oil from your body and hair ', '5' "
				+ "UNION SELECT '144', 'afternoon', 'naana', 'hurt', 'Before you eat lunch: Give thanks to your body, your digestive system', '1' "
				+ "UNION SELECT '145', 'afternoon', 'naana', 'hurt', 'Sip on  1 cup <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lemon water.html\">lemon water</a> 2 hours after lunch', '2' "
				+ "UNION SELECT '146', 'evening', 'naana', 'hurt', 'Write details about the incident', '1' "
				+ "UNION SELECT '147', 'evening', 'naana', 'hurt', 'Write how you feel about it', '2' "
				+ "UNION SELECT '148', 'evening', 'naana', 'hurt', 'After you are finished, tear the letter and allow the hurt to go away with it', '3' "
				+ "UNION SELECT '149', 'evening', 'naana', 'hurt', 'Before sleeping, <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/wash feet.html\">wash your feet</a> with room temperature water', '4' "
				+ "UNION SELECT '150', 'evening', 'naana', 'hurt', 'Lie down on your bed, close your eyes and take 10 slow deep breaths. Focus on your breathing while you do this', '5' "
				+ "UNION SELECT '151', 'morning', 'naana', 'hurt', 'Upon waking up, say a prayer to be thankful for the new day.', '1' "
				+ "UNION SELECT '152', 'morning', 'naana', 'hurt', 'Eat a healthy <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a>, before you leave home ', '2' "
				+ "UNION SELECT '153', 'afternoon', 'naana', 'immunity', 'Eat when hungry, avoid skipping meals', '1' "
				+ "UNION SELECT '154', 'afternoon', 'naana', 'immunity', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lemon water.html\">lemon water</a> 1 hour before lunch', '2' "
				+ "UNION SELECT '155', 'afternoon', 'naana', 'immunity', 'Eat <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lunch.html\">lunch</a> about the same time each day, if possible ', '3' "
				+ "UNION SELECT '156', 'evening', 'naana', 'immunity', 'Relax after work by listening to soothing music', '1' "
				+ "UNION SELECT '157', 'evening', 'naana', 'immunity', 'Write down 1 new thing each day that makes you happy', '2' "
				+ "UNION SELECT '158', 'evening', 'naana', 'immunity', 'Eat a fresh cooked <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/dinner.html\">dinner</a>, 3 hours before sleep to allow food to be digested properly', '3' "
				+ "UNION SELECT '159', 'evening', 'naana', 'immunity', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink hot water.html\">hot water</a> 30 minutes before bed', '4' "
				+ "UNION SELECT '160', 'morning', 'naana', 'immunity', 'Upon waking up, find a quiet corner and take 10 deep breaths', '1' "
				+ "UNION SELECT '161', 'morning', 'naana', 'immunity', 'Boil 1 cup of water with 3-4 neem leaves, and sip as a tea', '2' "
				+ "UNION SELECT '162', 'morning', 'naana', 'immunity', 'Massage your  <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/stomach.html\">stomach and intestines for 3 minutes</a>', '3' "
				+ "UNION SELECT '163', 'morning', 'naana', 'immunity', 'Eat an easy to digest <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a>, before you leave home ', '4' "
				+ "UNION SELECT '164', 'morning', 'naana', 'immunity', 'Try not to suppress your urges to urinate or clear bowels', '5' "
				+ "UNION SELECT '165', 'afternoon', 'naana', 'strength', 'Eat <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lunch.html\">lunch</a> at about the same time each day, around 1pm if possible', '1' "
				+ "UNION SELECT '166', 'afternoon', 'naana', 'strength', 'Sip on <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lemon water.html\">lemon water</a> 1 hour after lunch', '2' "
				+ "UNION SELECT '167', 'afternoon', 'naana', 'strength', 'Sit in <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/folded leg squat.html\">folded leg squat</a> for 3 minutes after lunch, if possible ', '3' "
				+ "UNION SELECT '168', 'evening', 'naana', 'strength', 'After work exercise: 20 minute fast walking or running, 20 bicep curls, 10 pushups', '1' "
				+ "UNION SELECT '169', 'evening', 'naana', 'strength', 'Eat a light <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/dinner.html\">dinner</a>', '2' "
				+ "UNION SELECT '170', 'evening', 'naana', 'strength', 'Drink a glass of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> 1 hour after eating dinner', '3' "
				+ "UNION SELECT '171', 'morning', 'naana', 'strength', 'Upon waking up, drink <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> to clear you bowels.', '1' "
				+ "UNION SELECT '172', 'morning', 'naana', 'strength', 'Awaken by <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/morning stretch.html\">stretching</a> your body', '2' "
				+ "UNION SELECT '173', 'morning', 'naana', 'strength', 'Do 10 <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/squats.html\">squats</a> or 10 pushups', '3' "
				+ "UNION SELECT '174', 'morning', 'naana', 'strength', 'Eat a good <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a>, before you leave home ', '4' "
				+ "UNION SELECT '175', 'afternoon', 'naana', 'time', 'Before the day begins, make a commitment to yourself to avoid taking worries or stresses from work back home', '1' "
				+ "UNION SELECT '176', 'afternoon', 'naana', 'time', 'Take a 5 minute breaks every 2 hours at work; close your eyes, breath slowly and feel your heartbeat', '2' "
				+ "UNION SELECT '177', 'afternoon', 'naana', 'time', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/lemon water.html\">lemon water</a> 1 hour after lunch', '3' "
				+ "UNION SELECT '178', 'evening', 'naana', 'time', 'Write down 1 positive quality about yourself', '1' "
				+ "UNION SELECT '179', 'evening', 'naana', 'time', 'Write down details of how this quality has helped you and people around you', '2' "
				+ "UNION SELECT '180', 'evening', 'naana', 'time', 'Share details of what you have written with a friend or family member', '3' "
				+ "UNION SELECT '181', 'morning', 'naana', 'time', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/drink warm water.html\">warm water</a> in the morning after brushing teeth', '1' "
				+ "UNION SELECT '182', 'morning', 'naana', 'time', 'Sit outside in the shade, the shade of a tree if possible, in the early morning sun', '2' "
				+ "UNION SELECT '183', 'morning', 'naana', 'time', 'Drink 1 cup of <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/energizing tea.html\">energizing tea</a>', '3' "
				+ "UNION SELECT '184', 'morning', 'naana', 'time', 'Take 10 deep breaths, relax body with each exhalation', '4' "
				+ "UNION SELECT '185', 'morning', 'naana', 'time', 'Eat a healthy <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a> before you leave home', '5' "
				+ "UNION SELECT '186', 'afternoon', 'naana', 'worry', 'Avoid skipping meals, especially when hungry', '1' "
				+ "UNION SELECT '187', 'afternoon', 'naana', 'worry', 'Sip <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/homemade lemonade.html\">lemonade</a> throughout the day to stay hydrated ', '2' "
				+ "UNION SELECT '188', 'afternoon', 'naana', 'worry', 'Take a break to <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/exercise/afternoon stretch.html\">stretch</a> every hour', '3' "
				+ "UNION SELECT '189', 'evening', 'naana', 'worry', 'Find a quiet place to sit down, and take 10 slow deep breaths in and out', '1' "
				+ "UNION SELECT '190', 'evening', 'naana', 'worry', 'Eat light, fresh cooked <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/dinner.html\">dinner</a> ', '2' "
				+ "UNION SELECT '191', 'evening', 'naana', 'worry', 'Eat dinner 2-3 hours before bed to allow the body to digest the food fully', '3' "
				+ "UNION SELECT '192', 'evening', 'naana', 'worry', 'Before sleeping, <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/personalcare/wash feet.html\">wash your feet</a> with room temperature water', '4' "
				+ "UNION SELECT '193', 'morning', 'naana', 'worry', 'Wake up before sunrise', '1' "
				+ "UNION SELECT '194', 'morning', 'naana', 'worry', 'Upon waking up, sit up on the bed, keep your eyes closed and take 10 slow, deep breaths in and out. Focus on your breathing. ', '2' "
				+ "UNION SELECT '195', 'morning', 'naana', 'worry', 'Splash lukewarm or room temperature water on your face after you are up', '3' "
				+ "UNION SELECT '196', 'morning', 'naana', 'worry', 'Take a 15 minute walk outside in the early morning sun, if possible', '4' "
				+ "UNION SELECT '197', 'morning', 'naana', 'worry', 'Eat <a href=\"#\" class=\"modal-view\" data-view=\"gliving/content/worksheets/diet/breakfast.html\">breakfast</a> in the morning before your leave home', '5' ";



		db.execSQL(insertQuery);
	}
	
	public ArrayList<EventTargets> getAllTargetsForUpdate(String period,String status) 
	{	
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	 
		SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
			long starDateAsTimestamp = 0;
			long today = 0;
			Date date1 = null;
			Date date2=null;	
			if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
							+" where "+CCH_REMINDER
							+ " = '"+period+"'"
							+ " and "+CCH_STATUS
							+ " = '"+status+"'";	
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp=date1.getTime();
					today=date2.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if(starDateAsTimestamp<=today){
				event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
				event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
				event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
				event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
				event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
				event_targets.setEventTargetOldId(c.getString(c.getColumnIndex(CCH_OLD_ID)));
				event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
				event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
				event_targets.setEventTargetPeriod(c.getString(c.getColumnIndex(CCH_REMINDER)));
				event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
				event_targets.setEventTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
				   list.add(event_targets);
				}
				   c.moveToNext();						
			}
			c.close();
			
			}
			return list;	
	}
	public ArrayList<User> getAllGroupMembers() 
	{	
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<User> list=new ArrayList<User>();	 
			if(doesTableExists(USER_GROUP_MEMBERS_TABLE)){
		String strQuery="select * from "+USER_GROUP_MEMBERS_TABLE
						+" ORDER BY "+ CCH_USER_FIRSTNAME+
						" COLLATE NOCASE;" 
							;	
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			while (c.isAfterLast()==false) {
				User u=new User();
				u.setFirstname(c.getString(c.getColumnIndex(CCH_USER_FIRSTNAME)));
				u.setLastname(c.getString(c.getColumnIndex(CCH_USER_LASTNAME)));
				u.setStaffId(c.getString(c.getColumnIndex(CCH_USER_USERNAME)));
				//String name=c.getString(c.getColumnIndex(CCH_USER_FIRSTNAME))+" "+c.getString(c.getColumnIndex(CCH_USER_LASTNAME));
				list.add(u);
				   c.moveToNext();						
			}
			c.close();
			
			}
			return list;	
	}
	public long updateTarget(String status, int number_achieved,int number_remaining,long id){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(TARGET_TABLE)){
        String updateQuery = "Update "+TARGET_TABLE+" set "+
        					CCH_STATUS+" = '"+ status +"'"+
        					","+CCH_TARGET_NO_ACHIEVED+" = '"+ number_achieved +"'"+
        					","+CCH_TARGET_NO_REMAINING+" = '"+ number_remaining +"'"+
        					","+CCH_LAST_UPDATED+" = '"+ getDateTime() +"'"+
        					" where "+BaseColumns._ID+" = "+id;
        db.execSQL(updateQuery);
		}
	return 1;
	
}
	public long updateSurveyReminder(String reminderFrequency,String frequencyValue,int id){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(SURVEY_TABLE)){
        String updateQuery = "Update "+SURVEY_TABLE+" set "+
        					CCH_REMINDER_FREQUENCY+" = '"+ reminderFrequency +"'"+
        					","+CCH_REMINDER_FREQUENCY_VALUE+" = '"+ frequencyValue +"'"+
        					" where "+BaseColumns._ID+" = "+id;
        System.out.println(updateQuery);
        db.execSQL(updateQuery);
		}
	return 1;
	
}	
	public long updateSurveyReminderValue(String reminderFrequencyValue,int id){
	SQLiteDatabase db = this.getWritableDatabase(); 
	if(doesTableExists(SURVEY_TABLE)){
    String updateQuery = "Update "+SURVEY_TABLE+" set "+
    					CCH_REMINDER_FREQUENCY_VALUE+" = '"+ reminderFrequencyValue +"'"+
    					" where "+BaseColumns._ID+" = "+id;
    System.out.println(updateQuery);
    db.execSQL(updateQuery);
	}
return 1;

}
	public long updateSurveyData(String responses,String user_id,String user_role,String survey_status,String date_taken,int id){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(SURVEY_TABLE)){
	    String updateQuery = "Update "+SURVEY_TABLE+" set "+
	    					CCH_RESPONSES+" = '"+ responses +"'"+
	    					","+CCH_SURVEY_USER_ID+" = '"+ user_id +"'"+
	    					","+CCH_USER_ROLE+" = '"+ user_role +"'"+
	    					","+CCH_SURVEY_STATUS+" = '"+ survey_status +"'"+
	    					","+CCH_DATE_TAKEN+" = '"+ date_taken +"'"+
	    					" where "+BaseColumns._ID+" = "+id;
	    System.out.println(updateQuery);
	    db.execSQL(updateQuery);
		}
	return 1;

	}
	public long updateCourseGroup(String courseShortname, String courseGroup){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(COURSE_TABLE)){
        String updateQuery = "Update "+COURSE_TABLE+" set "+
        					COURSE_C_GROUP+" = '"+ courseGroup +"'"+
        					" where "+COURSE_C_SHORTNAME+" = '"+courseShortname +"'";
        //System.out.println(updateQuery);
        db.execSQL(updateQuery);
		}
	return 1;
	
}
	
	public boolean deleteTarget(long id){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(TARGET_TABLE)){
		 String deleteQuery="Delete from "+TARGET_TABLE+" where "+
				 			BaseColumns._ID+" = "+ id;
	      db.execSQL(deleteQuery);
		}
		return true;
		
	}
	
	public boolean deletePOC(){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(POC_SECTIONS_TABLE)){
		 String deleteQuery="Delete from "+POC_SECTIONS_TABLE;
	      db.execSQL(deleteQuery);
		}
		return true;
		
	}
	public boolean deleteCalendarEvent(long id){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(CALENDAR_EVENTS_TABLE)){
		 String deleteQuery="Delete from "+CALENDAR_EVENTS_TABLE+" where "+
				 			COL_EVENT_ID+" = "+ id;
	      db.execSQL(deleteQuery);
		}
		return true;
		
	}
	public boolean deleteSurveyReminder(String reminder){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(SURVEY_TABLE)){
		 String deleteQuery="Delete from "+SURVEY_TABLE+" where "+
				 			CCH_REMINDER_DATE+" = '"+ reminder+"'";
	      db.execSQL(deleteQuery);
		}
		return true;
		
	}
	public boolean editTarget(String target_name,String target_detail,String target_category,String target_number,String start_date, String due_date,String reminder,long id){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(TARGET_TABLE)){
	        String updateQuery = "Update "+TARGET_TABLE+" set "+
	        						CCH_TARGET_NAME+" = '"+ target_name +"'"+
	        					","+CCH_TARGET_DETAIL+" = '"+ target_detail +"'"+
	        					","+CCH_TARGET_CATEGORY+" = '"+ target_category +"'"+
	        					","+CCH_TARGET_NO+" = '"+target_number+"'"+
	        					","+CCH_START_DATE+" = '"+start_date+"'"+
	        					","+CCH_DUE_DATE+" = '"+due_date+"'"+
	        					","+CCH_REMINDER+" = '"+reminder+"'"+
	        					" where "+BaseColumns._ID+" = "+id;
	        db.execSQL(updateQuery);
		}
		return true;
		
	}
	public boolean editCalendarEvent(long event_id, String event_type,
											String description, 
											String category,String location,
											String comments,String justification,
											String reminder,String start_date,
											String end_date
											){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(CALENDAR_EVENTS_TABLE)){
	        String updateQuery = "Update "+CALENDAR_EVENTS_TABLE+" set "+
	        						COL_EVENTTYPE+" = '"+ event_type +"'"+
	        					","+COL_DESCRIPTION+" = '"+ description +"'"+
	        					","+COL_CATEGORY+" = '"+ category +"'"+
	        					","+COL_LOCATION+" = '"+location+"'"+
	        					","+COL_COMMENTS+" = '"+comments+"'"+
	        					","+COL_JUSTIFICATION+" = '"+justification+"'"+
	        					","+COL_REMINDER+" = '"+reminder+"'"+
	        					","+COL_STARTDATE+" = '"+start_date+"'"+
	        					","+COL_ENDDATE+" = '"+end_date+"'"+
	        					" where "+COL_EVENT_ID+" = "+event_id;
	        db.execSQL(updateQuery);
		}
		return true;
		
	}
	public boolean updateCalendarEvent(long event_id,
			String comments,
			String justification,
			String status
			){
					SQLiteDatabase db = this.getWritableDatabase(); 
						if(doesTableExists(CALENDAR_EVENTS_TABLE)){
							String updateQuery = "Update "+CALENDAR_EVENTS_TABLE+" set "+
									COL_COMMENTS+" = '"+comments+"'"+
									","+COL_JUSTIFICATION+" = '"+justification+"'"+
									","+COL_STATUS+" = '"+status+"'"+
									" where "+COL_EVENT_ID+" = "+event_id;
							db.execSQL(updateQuery);
						}
return true;

}
	public long editSurvey(String userid,String userrole,String response,String datetaken,String status,long id){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(SURVEY_TABLE)){
	        String updateQuery = "Update "+SURVEY_TABLE+" set "+
	        						CCH_SURVEY_USER_ID+" = '"+ userid +"'"+
	        					","+CCH_USER_ROLE+" = '"+ userrole +"'"+
	        					","+CCH_RESPONSES+" = '"+ response +"'"+
	        					","+CCH_DATE_TAKEN+" = '"+datetaken+"'"+
	        					","+CCH_SURVEY_STATUS+" = '"+status+"'"+
	        					" where "+BaseColumns._ID+" = "+id;
	        db.execSQL(updateQuery);
		}
		return 1	;
		
	}
	
	public long editFacilityTargetReminders(String reminder,long id){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(FACILITY_TARGET_TABLE)){
	        String updateQuery = "Update "+FACILITY_TARGET_TABLE+" set "+
	        						CCH_REMINDER+" = '"+ reminder +"'"+
	        					" where "+BaseColumns._ID+" = "+id;
	        db.execSQL(updateQuery);
		}
		return 1	;
		
	}
	
	public long editFacilityTargetUpdate(String group_members,String amount_achieved,String amount_remining,String status,String lastUpdated,long id){
		SQLiteDatabase db = this.getWritableDatabase(); 
		if(doesTableExists(FACILITY_TARGET_TABLE)){
	        String updateQuery = "Update "+FACILITY_TARGET_TABLE+" set "+
	        						CCH_TARGET_GROUP_MEMBERS+" = '"+ group_members +"'"+
	        						","+CCH_TARGET_NO_ACHIEVED+" = '"+ amount_achieved +"'"+
		        					","+CCH_TARGET_NO_REMAINING+" = '"+ amount_remining +"'"+
		        					","+CCH_STATUS+" = '"+ status +"'"+
		        					","+CCH_LAST_UPDATED+" = '"+ lastUpdated +"'"+
	        					" where "+BaseColumns._ID+" = "+id;
	        db.execSQL(updateQuery);
		}
		return 1	;
		
	}
	
	
	public ArrayList<EventTargets> getListOfDailyTargetsToUpdate(String target_type)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long today = 0;
		int total_targets = 0;
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
						+" where "+CCH_REMINDER
						+" = '"+"Daily"+"'"
						+" and "+CCH_STATUS
						+" = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"'"
						+" and "+CCH_TARGET_TYPE
						+" = '"+target_type+"'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
		
			
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
				String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					today = date3.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(starDateAsTimestamp<=today&& dueDateAsTimestamp>=today){
					
					try{
						event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
						event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
						event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
						event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
						event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						event_targets.setEventTargetOldId(c.getString(c.getColumnIndex(CCH_OLD_ID)));
						event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
						event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
						event_targets.setEventTargetPeriod(c.getString(c.getColumnIndex(CCH_REMINDER)));
						event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
						event_targets.setEventTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
						event_targets.setEventTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
						event_targets.setEventTargetCategory(c.getString(c.getColumnIndex(CCH_TARGET_CATEGORY)));
						   list.add(event_targets);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return list;	
	}
	
	public ArrayList<EventTargets> getAllTargetsToView(String period,String status,String targetType) 
	{	
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	 
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
							+" where "+CCH_REMINDER
							+ " = '"+period+"'"
							+ " and "+CCH_STATUS
							+ " = '"+status+"'"
							+ " and "+CCH_TARGET_TYPE
							+ " = '"+targetType+"'";	
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				
				event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
				event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
				event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
				event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
				event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
				event_targets.setEventTargetOldId(c.getString(c.getColumnIndex(CCH_OLD_ID)));
				event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
				event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
				event_targets.setEventTargetPeriod(c.getString(c.getColumnIndex(CCH_REMINDER)));
				event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
				event_targets.setEventTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
				event_targets.setEventTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
				event_targets.setEventTargetCategory(c.getString(c.getColumnIndex(CCH_TARGET_CATEGORY)));
				   list.add(event_targets);
				   c.moveToNext();						
			}
			c.close();
			
		}
			return list;	
	}
	public ArrayList<String> getNumberAchieved(long id,String reminder,String status){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<String> list=new ArrayList<String>();
		if(doesTableExists(TARGET_TABLE)){
		 String strQuery="select * from "+TARGET_TABLE
	             	+" where "+CCH_REMINDER
	             	+ " = '"+reminder+"'"
	             	+ " and "+CCH_STATUS
		              + " = '"+status+"'"
		              + " and "+BaseColumns._ID
		              + " = "+id;	
		Cursor c=db.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			list.add(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
			c.moveToNext();						
		}
		c.close();
		
		}
		return list;
	}
	public ArrayList<EventTargets> getAllTargetsForUpdate(String period,String status,String target_type) 
	{	
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	 
		SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
			long starDateAsTimestamp = 0;
			long today = 0;
			Date date1 = null;
			Date date2=null;	
			if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
							+" where "+CCH_REMINDER
							+ " = '"+period+"'"
							+ " and "+CCH_STATUS
							+ " = '"+status+"'"
							+ " and "+CCH_TARGET_TYPE
							+ " = '"+target_type+"'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp=date1.getTime();
					today=date2.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if(starDateAsTimestamp<=today){
				event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
				event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
				event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
				event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
				event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
				event_targets.setEventTargetOldId(c.getString(c.getColumnIndex(CCH_OLD_ID)));
				event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
				event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
				event_targets.setEventTargetPeriod(c.getString(c.getColumnIndex(CCH_REMINDER)));
				event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
				event_targets.setEventTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
				event_targets.setEventTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
				event_targets.setEventTargetCategory(c.getString(c.getColumnIndex(CCH_TARGET_CATEGORY)));
				   list.add(event_targets);
				}
				   c.moveToNext();						
			}
			c.close();
			
			}
			return list;	
	}
	
	
	public int getTargetsForAchievements(String target_type,int month,int year)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		int total_targets = 0;
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long selecteddate = 0;
		DateTime stdate = null;
		DateTime dddate = null;
		DateTime sddate;
		if(doesTableExists(TARGET_ACHIEVEMENTS_TABLE)){
		String strQuery="select * from "+TARGET_ACHIEVEMENTS_TABLE
						+" where "+CCH_TARGET_CATEGORY
						+" like '%"+target_type+"%'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
				String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
				try {
					if(!start_date.equals("")&&!due_date.equals("")){
						DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
						 stdate = formatter.parseDateTime(start_date);
						 dddate = formatter.parseDateTime(due_date);
						// sddate = formatter.parseDateTime("01"+"-0"+String.valueOf(month)+"-"+String.valueOf(year));
						}
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse("01"+"-0"+String.valueOf(month)+"-"+String.valueOf(year));
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					selecteddate = date3.getTime();
				
				//if(starDateAsTimestamp>=selecteddate||dueDateAsTimestamp>=selecteddate){
				if((stdate.getMonthOfYear()<= month&&stdate.getYear()==year)&&(dddate.getMonthOfYear()>=month&&dddate.getYear()==year)){
					try{
					event_targets.setEventTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
					list.add(event_targets);
					total_targets=list.size();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return total_targets;	
	}
	
	public ArrayList<CourseAchievments> getQuizzesForAchievements(String course_name,int month,int year)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<CourseAchievments> list=new ArrayList<CourseAchievments>();	
		if(doesTableExists(COURSE_ACHIEVEMENTS_TABLE)){
		String strQuery="select * from "+COURSE_ACHIEVEMENTS_TABLE
							+" where "+CCH_COURSE
							+" like '%"+course_name+"%'";
						;
						System.out.println(strQuery);
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			
			while (c.isAfterLast()==false) {
				CourseAchievments courses = new CourseAchievments();
				try{
				String date_taken=c.getString(c.getColumnIndex(CCH_DATE_TAKEN));
				DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
				DateTime dt = formatter.parseDateTime(date_taken);
				String[] due_date_split=date_taken.split("-");
				if(due_date_split.length>2){
						int date_month=Integer.parseInt(due_date_split[1]);
						int date_year=Integer.parseInt(due_date_split[0]);
						
					if(month==dt.getMonthOfYear()&&year==dt.getYear()){	
						courses.setCourseName(c.getString(c.getColumnIndex(CCH_COURSE)));
						courses.setCourseSection(c.getString(c.getColumnIndex(CCH_COURSE_SECTION)));
						courses.setCourseTitle(c.getString(c.getColumnIndex(CCH_COURSE_QUIZ_TITLE)));
						courses.setScore(c.getString(c.getColumnIndex(CCH_COURSE_SCORE)));
						courses.setType(c.getString(c.getColumnIndex(CCH_COURSE_QUIZ_TYPE)));
						courses.setMaxScore(c.getString(c.getColumnIndex(CCH_COURSE_MAX_SCORE)));
						courses.setDateTaken(c.getString(c.getColumnIndex(CCH_COURSE_DATE_TAKEN)));
						String score=c.getString(c.getColumnIndex(CCH_COURSE_SCORE));
						String max_score=c.getString(c.getColumnIndex(CCH_COURSE_MAX_SCORE));
						courses.setScore(c.getString(c.getColumnIndex(CCH_COURSE_SCORE))+ "/" + c.getString(c.getColumnIndex(CCH_COURSE_MAX_SCORE)));
						Double percentage=((double)Double.parseDouble(score)/(double)Double.parseDouble(max_score))*100;
						String percentage_value=String.format("%.0f", percentage);
						courses.setPercentage(percentage_value);
						list.add(courses);
					
					}
					 c.moveToNext();		 
			}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			c.close();
		}
			return list;	
	}
	public ArrayList<CourseAchievments> getQuizzesForAchievements(String status)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<CourseAchievments> list=new ArrayList<CourseAchievments>();	
		if(doesTableExists(COURSES_TABLE)){
		String strQuery="select * from "+COURSES_TABLE
							+" where "+CCH_COURSE_KSA_STATUS
							+" = '"+status+"'";
						;
						System.out.println(strQuery);
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			
			while (c.isAfterLast()==false) {
				CourseAchievments courses = new CourseAchievments();
				try{
						courses.setCourseName(c.getString(c.getColumnIndex(CCH_COURSE)));
						courses.setCourseTitle(c.getString(c.getColumnIndex(CCH_COURSE_TITLE)));
						courses.setScore(c.getString(c.getColumnIndex(CCH_COURSE_SCORE)));
						courses.setDateTaken(c.getString(c.getColumnIndex(CCH_COURSE_DATE_TAKEN)));
						courses.setPercentage(c.getString(c.getColumnIndex(CCH_COURSE_PERCENTAGE_COMPLETED)));
						courses.setCourseAttempts(c.getString(c.getColumnIndex(CCH_COURSE_ATTEMPTS)));
						list.add(courses);
					 c.moveToNext();		 
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			c.close();
		}
			return list;	
	}
	public int getTargetsBasedOnStatus(String status,String target_type,int month,int year)
	{	SQLiteDatabase db = this.getReadableDatabase();
	ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
	int total_targets = 0;
	Date date1 = null;
	Date date2 = null;
	Date date3= null;
	DateTime stdate = null;
	DateTime dddate = null;
	DateTime sddate;
	long starDateAsTimestamp = 0;
	long dueDateAsTimestamp = 0;
	long selecteddate = 0;
	if(doesTableExists(TARGET_ACHIEVEMENTS_TABLE)){
	String strQuery="select * from "+TARGET_ACHIEVEMENTS_TABLE
					+" where "+CCH_STATUS
					+ " = '"+status+"'"
					+" and "+CCH_TARGET_CATEGORY
					+" like '%"+target_type+"%'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();	
			while (c.isAfterLast()==false) {
			EventTargets event_targets = new EventTargets();
			String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
			String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
			try {
				if(!start_date.equals("")&&!due_date.equals("")){
				DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
				 stdate = formatter.parseDateTime(start_date);
				 dddate = formatter.parseDateTime(due_date);
				 //sddate = formatter.parseDateTime("01"+"-0"+String.valueOf(month)+"-"+String.valueOf(year));
				}
				date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
				date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
				date3= new SimpleDateFormat("dd-MM-yyyy").parse("01"+"-0"+String.valueOf(month)+"-"+String.valueOf(year));
				starDateAsTimestamp = date1.getTime();
				dueDateAsTimestamp = date2.getTime();
				selecteddate = date3.getTime();
				if((stdate.getMonthOfYear()<= month&&stdate.getYear()==year)&&(dddate.getMonthOfYear()>=month&&dddate.getYear()==year)){
					try{
					event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
					list.add(event_targets);
					total_targets=list.size();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
				   c.moveToNext();		  				
			}
			c.close();
			
	}
			return total_targets;	
	}
	
	
	/* For Achievement Center*/
	public ArrayList<EventTargets> getListOfTargetsForAchievements(String status,String target_type,int month,int year)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	 
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		DateTime stdate = null;
		DateTime dddate = null;
		DateTime sddate;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long selecteddate = 0;
		if(doesTableExists(TARGET_ACHIEVEMENTS_TABLE)){
		String strQuery="select * from "+TARGET_ACHIEVEMENTS_TABLE
						+" where "+CCH_STATUS
						+ " = '"+status+"'"
						+ " and "+CCH_TARGET_CATEGORY
						+" like '%"+target_type+"%'";	
		System.out.println(strQuery);
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
				String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
				String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
				try {
					if(!start_date.equals("")&&!due_date.equals("")){
						DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
						 stdate = formatter.parseDateTime(start_date);
						 dddate = formatter.parseDateTime(due_date);
						 //sddate = formatter.parseDateTime("01"+"-0"+String.valueOf(month)+"-"+String.valueOf(year));
						}
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse("01"+"-0"+String.valueOf(month)+"-"+String.valueOf(year));
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					selecteddate = date3.getTime();
				
				//if(starDateAsTimestamp>=selecteddate||dueDateAsTimestamp>=selecteddate){
				if((stdate.getMonthOfYear()<= month&&stdate.getYear()==year)&&(dddate.getMonthOfYear()>=month&&dddate.getYear()==year)){
					event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
					event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
					event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
					event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
					event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
					event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
					event_targets.setEventTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
					event_targets.setEventTargetCategory(c.getString(c.getColumnIndex(CCH_TARGET_CATEGORY)));
				
				   list.add(event_targets);
				}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return list;	
	}
	
	public ArrayList<FacilityTargets> getListOfFacilityTargetsForAchievements(String status,String month,int year)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<FacilityTargets> list=new ArrayList<FacilityTargets>();	 
		if(doesTableExists(FACILITY_TARGET_TABLE)){
		String strQuery="select * from "+FACILITY_TARGET_TABLE
						+" where "+CCH_STATUS
						+ " = '"+status+"'"
						+ " and "+CCH_TARGET_MONTH
						+ " = '"+month+"'";	
		System.out.println(strQuery);
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				FacilityTargets facility_targets = new FacilityTargets();
				try {
				
				//if(starDateAsTimestamp>=selecteddate||dueDateAsTimestamp>=selecteddate){
				//if((stdate.getMonthOfYear()<= month&&stdate.getYear()==year)&&(dddate.getMonthOfYear()>=month&&dddate.getYear()==year)){
					facility_targets.setTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
					facility_targets.setTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
					facility_targets.setTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
					facility_targets.setTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
					facility_targets.setTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
					facility_targets.setTargetOldId(c.getString(c.getColumnIndex(CCH_TARGET_ID)));
					facility_targets.setTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					facility_targets.setTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
					facility_targets.setTargetReminder(c.getString(c.getColumnIndex(CCH_REMINDER)));
					facility_targets.setTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
					facility_targets.setTargetGroupMembers(c.getString(c.getColumnIndex(CCH_TARGET_GROUP_MEMBERS)));
					facility_targets.setTargetMonth(c.getString(c.getColumnIndex(CCH_TARGET_MONTH)));
					facility_targets.setTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
					facility_targets.setTargetCategory(c.getString(c.getColumnIndex(CCH_TARGET_CATEGORY)));
				   list.add(facility_targets);
				//}
				} catch (Exception e) {
					e.printStackTrace();
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return list;	
	}

	public int getDailyTargetsToUpdate()
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long today = 0;
		int total_targets = 0;
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
						+" where "+CCH_REMINDER
						+" = '"+"Daily"+"'"
						+" and "+CCH_STATUS
						+" = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
				String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					today = date3.getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(starDateAsTimestamp<=today&& dueDateAsTimestamp>=today){
					
					try{
					event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
					list.add(event_targets);
					total_targets=list.size();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return total_targets;	
	}
	
	public int getWeeklyTargetsToUpdate()
	{	SQLiteDatabase db = this.getReadableDatabase();
		DateTime today=new DateTime();
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long now = 0;
		int total_targets = 0;
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
						+" where "+CCH_REMINDER
						+" = '"+"Weekly"+"'"
						+" and "+CCH_STATUS
						+" = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
				String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					now = date3.getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(starDateAsTimestamp<=now&& dueDateAsTimestamp>=now&&timeUtil.checkTodayFriday(today)=="Friday"){
					try{
						event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
						list.add(event_targets);
						total_targets=list.size();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return total_targets;	
	}
	public int getMonthlyTargetsToUpdate()
	{	SQLiteDatabase db = this.getReadableDatabase();
		DateTime today=new DateTime();
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long now = 0;
		int total_targets = 0;
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
						+" where "+CCH_REMINDER
						+" = '"+"Monthly"+"'"
						+" and "+CCH_STATUS
						+" = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
				String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					now = date3.getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(starDateAsTimestamp<=now&& dueDateAsTimestamp>=now&&timeUtil.checkTodayFriday(today)=="Friday"&&getDate().equals(timeUtil.getLastFridayofMonth(0))){
					try{
					event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
					list.add(event_targets);
					total_targets=list.size();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return total_targets;	
	}
	public int getQuarterlylyTargetsToUpdate()
	{	SQLiteDatabase db = this.getReadableDatabase();
		DateTime today=new DateTime();
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long now = 0;
		int total_targets = 0;
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
						+" where "+CCH_REMINDER
						+" = '"+"Quarterly"+"'"
						+" and "+CCH_STATUS
						+" = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
				String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					now = date3.getTime();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(starDateAsTimestamp<=now&&dueDateAsTimestamp>=now&&timeUtil.checkTodayFriday(today)=="Friday"&&getDate().equals(timeUtil.getLastFridayofMonth(0))&&timeUtil.checkIfMonthIsQuarter()==true){
					try{
					event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
					list.add(event_targets);
					total_targets=list.size();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return total_targets;	
	}
	public int getMidYearTargetsToUpdate()
	{	SQLiteDatabase db = this.getReadableDatabase();
		DateTime today=new DateTime();
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long now = 0;
		int total_targets = 0;
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
						+" where "+CCH_REMINDER
						+" = '"+"Mid-year"+"'"
						+" and "+CCH_STATUS
						+" = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
				String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					now = date3.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if(starDateAsTimestamp<=now&& dueDateAsTimestamp>=now&&timeUtil.checkTodayFriday(today)=="Friday"&&getDate().equals(timeUtil.getLastFridayofMonth(0))&&timeUtil.checkIfMonthIsMidYear()==true){
					try{
					event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
					list.add(event_targets);
					total_targets=list.size();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return total_targets;	
	}
	
	public int getAnnualTargetsToUpdate()
	{	SQLiteDatabase db = this.getReadableDatabase();
		DateTime today=new DateTime();
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long now = 0;
		int total_targets = 0;
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
						+" where "+CCH_REMINDER
						+" = '"+"Annually"+"'"
						+" and "+CCH_STATUS
						+" = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					now = date3.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if(starDateAsTimestamp<=now&& dueDateAsTimestamp>=now&&timeUtil.checkTodayFriday(today)=="Friday"&&getDate().equals(timeUtil.getLastFridayofMonth(0))&&timeUtil.checkIfMonthIsEndOfYear()==true){
					try{
					event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
					list.add(event_targets);
					total_targets=list.size();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return total_targets;	
	}
	
	public ArrayList<FacilityTargets> getTargetsForMonth(String month)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<FacilityTargets> list=new ArrayList<FacilityTargets>();	
		if(doesTableExists(FACILITY_TARGET_TABLE)){
		String strQuery="select * from "+FACILITY_TARGET_TABLE
						+" where "+CCH_TARGET_MONTH
						+" = '"+month+"'"						
						;
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				FacilityTargets facility_targets = new FacilityTargets();
					
					try{
						facility_targets.setTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
						facility_targets.setTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
						facility_targets.setTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
						facility_targets.setTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						facility_targets.setTargetOldId(c.getString(c.getColumnIndex(CCH_TARGET_ID)));
						facility_targets.setTargetGroup(c.getString(c.getColumnIndex(CCH_TARGET_GROUP)));
						facility_targets.setTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
						facility_targets.setTargetMonth(c.getString(c.getColumnIndex(CCH_TARGET_MONTH)));
						   list.add(facility_targets);
					
				   c.moveToNext();
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			c.close();
			
		}
			return list;	
	}
	public ArrayList<FacilityTargets> getTargetsById(int id)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<FacilityTargets> list=new ArrayList<FacilityTargets>();	
		if(doesTableExists(FACILITY_TARGET_TABLE)){
		String strQuery="select * from "+FACILITY_TARGET_TABLE
						+" where "+BaseColumns._ID
						+" = '"+id+"'"						
						;
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				FacilityTargets facility_targets = new FacilityTargets();
					
					try{
						if(c.getString(c.getColumnIndex(CCH_REMINDER)).equals("Not set")){
						facility_targets.setTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
						facility_targets.setTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
						facility_targets.setTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
						facility_targets.setTargetNumberRemaining(c.getString(c.getColumnIndex(CCH_TARGET_NO_REMAINING)));
						facility_targets.setTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
						facility_targets.setTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						facility_targets.setTargetOldId(c.getString(c.getColumnIndex(CCH_TARGET_ID)));
						facility_targets.setTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
						facility_targets.setTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
						facility_targets.setTargetReminder(c.getString(c.getColumnIndex(CCH_REMINDER)));
						facility_targets.setTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
						facility_targets.setTargetGroup(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
						facility_targets.setTargetOverall(c.getString(c.getColumnIndex(CCH_TARGET_OVERALL)));
						facility_targets.setTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
						facility_targets.setTargetGroupMembers(c.getString(c.getColumnIndex(CCH_TARGET_GROUP_MEMBERS)));
						facility_targets.setTargetMonth(c.getString(c.getColumnIndex(CCH_TARGET_MONTH)));
						   list.add(facility_targets);
					
					}
				   c.moveToNext();
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			c.close();
			
		}
			return list;	
	}
	public ArrayList<FacilityTargets> getTargetsForMonthSynced(String month)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<FacilityTargets> list=new ArrayList<FacilityTargets>();	
		if(doesTableExists(FACILITY_TARGET_TABLE)){
		String strQuery="select sum("+CCH_TARGET_NO_ACHIEVED+") as target_no_achieved, target_month, target_id,target_type,target_detail,target_month from "+FACILITY_TARGET_UPDATE_TABLE
						+" where "+CCH_TARGET_MONTH
						+" = '"+month+"'"
						+" GROUP BY "+ CCH_TARGET_ID
						;
		System.out.println(strQuery);
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				FacilityTargets facility_targets = new FacilityTargets();
					try{
						facility_targets.setTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
						facility_targets.setTargetOldId(c.getString(c.getColumnIndex(CCH_TARGET_ID)));
						facility_targets.setTargetMonth(c.getString(c.getColumnIndex(CCH_TARGET_MONTH)));
						facility_targets.setTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
						facility_targets.setTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
						facility_targets.setTargetMonth(c.getString(c.getColumnIndex(CCH_TARGET_MONTH)));
						   list.add(facility_targets);
					
				   c.moveToNext();
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			c.close();
			
		}
			return list;	
	}
	public ArrayList<MyCalendarEvents> getCalendarEvents()
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<MyCalendarEvents> list=new ArrayList<MyCalendarEvents>();	
		
		if(doesTableExists(CALENDAR_EVENTS_TABLE)){
		String strQuery="select * from "+CALENDAR_EVENTS_TABLE;
		
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				MyCalendarEvents events = new MyCalendarEvents();
					
					try{
						events.setEventType(c.getString(c.getColumnIndex(COL_EVENTTYPE)));
						events.setEventLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
						events.setEventId(c.getString(c.getColumnIndex(COL_EVENT_ID)));
						events.setEventStatus(c.getString(c.getColumnIndex(COL_STATUS)));
						events.setEventDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));
						events.setEventComment(c.getString(c.getColumnIndex(COL_COMMENTS)));
						events.setEventEndDate(c.getString(c.getColumnIndex(COL_ENDDATE)));
						events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
						   list.add(events);
					
				   c.moveToNext();
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			c.close();
			
		}else{
			createCalendarEventsTable(db);
		}
			return list;	
	}
	public ArrayList<MyCalendarEvents> getCalendarEventsForUpdate()
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<MyCalendarEvents> list=new ArrayList<MyCalendarEvents>();	
		
		if(doesTableExists(CALENDAR_EVENTS_TABLE)){
		String strQuery="select * from "+CALENDAR_EVENTS_TABLE
						+" ORDER BY "+ COL_START_DATE+ " DESC";
		
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			while (c.isAfterLast()==false) {
				MyCalendarEvents events = new MyCalendarEvents();
				String dformat ="MMM-dd hh:mm a";
		 	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
		 	       Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
		 	    	String d =formatter.format(calendar.getTime());
		 	    	formatter.format(calendar.getTime());
					try{
						events.setEventType(c.getString(c.getColumnIndex(COL_EVENTTYPE)));
						events.setEventLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
						events.setEventId(c.getString(c.getColumnIndex(COL_EVENT_ID)));
						events.setEventStatus(c.getString(c.getColumnIndex(COL_STATUS)));
						events.setEventDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));
						events.setEventCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
						events.setEventComment(c.getString(c.getColumnIndex(COL_COMMENTS)));
						events.setEventEndDate(c.getString(c.getColumnIndex(COL_ENDDATE)));
						events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
						events.setEventTime(d);
						   list.add(events);
					
				   c.moveToNext();
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			c.close();
			
		}else{
			createCalendarEventsTable(db);
		}
			return list;	
	}
	public ArrayList<FacilityTargets> getTargetsForMonthView(String month)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<FacilityTargets> list=new ArrayList<FacilityTargets>();	
		if(doesTableExists(FACILITY_TARGET_TABLE)){
		String strQuery="select * from "+FACILITY_TARGET_TABLE
						+" where "+CCH_TARGET_MONTH
						+" = '"+month+"'"
						+" order by case "+CCH_REMINDER
						+" when 'Not set' then 0"
						+ " when 'Daily' then 1"
						+ " when 'Weekly' then 2"
						+ " when 'Monthly' then 3"
						+ " end"						
						;
		System.out.println(strQuery);
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
		
			
			while (c.isAfterLast()==false) {
				FacilityTargets facility_targets = new FacilityTargets();
					
					try{
					//if(!c.getString(c.getColumnIndex(CCH_REMINDER)).equals("Not set")){
							facility_targets.setTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
							facility_targets.setTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
							facility_targets.setTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
							facility_targets.setTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
							facility_targets.setTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
							facility_targets.setTargetOldId(c.getString(c.getColumnIndex(CCH_TARGET_ID)));
							facility_targets.setTargetNumberRemaining(c.getString(c.getColumnIndex(CCH_TARGET_NO_REMAINING)));
							facility_targets.setTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
							facility_targets.setTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
							facility_targets.setTargetReminder(c.getString(c.getColumnIndex(CCH_REMINDER)));
							facility_targets.setTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
							facility_targets.setTargetGroup(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
							facility_targets.setTargetOverall(c.getString(c.getColumnIndex(CCH_TARGET_OVERALL)));
							facility_targets.setTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
							facility_targets.setTargetGroupMembers(c.getString(c.getColumnIndex(CCH_TARGET_GROUP_MEMBERS)));
							facility_targets.setTargetMonth(c.getString(c.getColumnIndex(CCH_TARGET_MONTH)));
						   list.add(facility_targets);
					
					//}
				   c.moveToNext();
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			c.close();
			
		}
			return list;	
	}
	
	public ArrayList<FacilityTargets> getTargetsForMonthView(String month,String category)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<FacilityTargets> list=new ArrayList<FacilityTargets>();	
		if(doesTableExists(FACILITY_TARGET_TABLE)){
		String strQuery="select * from "+FACILITY_TARGET_TABLE
						+" where "+CCH_TARGET_MONTH
						+" = '"+month+"'"
						+" and "+CCH_TARGET_CATEGORY
						+" = '"+category+"'"
						+" order by case "+CCH_REMINDER
						+" when 'Not set' then 0"
						+ " when 'Daily' then 1"
						+ " when 'Weekly' then 2"
						+ " when 'Monthly' then 3"
						+ " end"						
						;
		System.out.println(strQuery);
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
		
			
			while (c.isAfterLast()==false) {
				FacilityTargets facility_targets = new FacilityTargets();
					
					try{
					//if(!c.getString(c.getColumnIndex(CCH_REMINDER)).equals("Not set")){
							facility_targets.setTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
							facility_targets.setTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
							facility_targets.setTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
							facility_targets.setTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
							facility_targets.setTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
							facility_targets.setTargetOldId(c.getString(c.getColumnIndex(CCH_TARGET_ID)));
							facility_targets.setTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
							facility_targets.setTargetNumberRemaining(c.getString(c.getColumnIndex(CCH_TARGET_NO_REMAINING)));
							facility_targets.setTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
							facility_targets.setTargetReminder(c.getString(c.getColumnIndex(CCH_REMINDER)));
							facility_targets.setTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
							facility_targets.setTargetGroup(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
							facility_targets.setTargetOverall(c.getString(c.getColumnIndex(CCH_TARGET_OVERALL)));
							facility_targets.setTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
							facility_targets.setTargetGroupMembers(c.getString(c.getColumnIndex(CCH_TARGET_GROUP_MEMBERS)));
							facility_targets.setTargetMonth(c.getString(c.getColumnIndex(CCH_TARGET_MONTH)));
						   list.add(facility_targets);
					
					//}
				   c.moveToNext();
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			c.close();
			
		}
			return list;	
	}
	public ArrayList<FacilityTargets> getTargetsForMonthViewAgeGroups(String month,String targey_type)
	{	SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<FacilityTargets> list=new ArrayList<FacilityTargets>();	
		if(doesTableExists(FACILITY_TARGET_TABLE)){
		String strQuery="select * from "+FACILITY_TARGET_TABLE
						+" where "+CCH_TARGET_MONTH	
						+" = '"+month+"'"
						+" and "+CCH_TARGET_TYPE
						+" = '"+targey_type+"'"				
						;
		System.out.println(strQuery);
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
		
			
			while (c.isAfterLast()==false) {
				FacilityTargets facility_targets = new FacilityTargets();
					
					try{
							facility_targets.setTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
							facility_targets.setTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
							facility_targets.setTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
							facility_targets.setTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
							facility_targets.setTargetOldId(c.getString(c.getColumnIndex(CCH_TARGET_ID)));
							facility_targets.setTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
							facility_targets.setTargetGroup(c.getString(c.getColumnIndex(CCH_TARGET_GROUP)));
							facility_targets.setTargetMonth(c.getString(c.getColumnIndex(CCH_TARGET_MONTH)));
						   list.add(facility_targets);
					
				   c.moveToNext();
					}catch(Exception e){
						e.printStackTrace();
					}
			}
			c.close();
			
		}
			return list;	
	}
	public ArrayList<EventTargets> getListOfWeeklyTargetsToUpdate(String target_type)
	{	SQLiteDatabase db = this.getReadableDatabase();
		DateTime today=new DateTime();
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long now = 0;
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
						+" where "+CCH_REMINDER
						+" = '"+"Weekly"+"'"
						+" and "+CCH_STATUS
						+" = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"'"
						+" and "+CCH_TARGET_TYPE
						+" = '"+target_type+"'";
						
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			int total_targets = 0;
			
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
				String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					now = date3.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if(starDateAsTimestamp<=now&& dueDateAsTimestamp>=now&&timeUtil.checkTodayFriday(today)=="Friday"){
					try{
						event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
						event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
						event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
						event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
						event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						event_targets.setEventTargetOldId(c.getString(c.getColumnIndex(CCH_OLD_ID)));
						event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
						event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
						event_targets.setEventTargetPeriod(c.getString(c.getColumnIndex(CCH_REMINDER)));
						event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
						event_targets.setEventTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
						event_targets.setEventTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
						event_targets.setEventTargetCategory(c.getString(c.getColumnIndex(CCH_TARGET_CATEGORY)));
						   list.add(event_targets);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return list;	
	}
	public  ArrayList<EventTargets> getListOfMonthlyTargetsToUpdate(String target_type)
	{	SQLiteDatabase db = this.getReadableDatabase();
		DateTime today=new DateTime();
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long now = 0;
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
						+" where "+CCH_REMINDER
						+" = '"+"Monthly"+"'"
						+" and "+CCH_STATUS
						+" = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"'"
						+" and "+CCH_TARGET_TYPE
						+" = '"+target_type+"'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
				String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					now = date3.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if(starDateAsTimestamp<=now&& dueDateAsTimestamp>=now&&timeUtil.checkTodayFriday(today)=="Friday"&&getDate().equals(timeUtil.getLastFridayofMonth(0))){
					try{
						event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
						event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
						event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
						event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
						event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						event_targets.setEventTargetOldId(c.getString(c.getColumnIndex(CCH_OLD_ID)));
						event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
						event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
						event_targets.setEventTargetPeriod(c.getString(c.getColumnIndex(CCH_REMINDER)));
						event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
						event_targets.setEventTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
						event_targets.setEventTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
						event_targets.setEventTargetCategory(c.getString(c.getColumnIndex(CCH_TARGET_CATEGORY)));
						   list.add(event_targets);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return list;	
	}
	public ArrayList<EventTargets> getListOfQuarterlylyTargetsToUpdate(String target_type)
	{	SQLiteDatabase db = this.getReadableDatabase();
		DateTime today=new DateTime();
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long now = 0;
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
						+" where "+CCH_REMINDER
						+" = '"+"Quarterly"+"'"
						+" and "+CCH_STATUS
						+" = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"'"
						+" and "+CCH_TARGET_TYPE
						+" = '"+target_type+"'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
				String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					now = date3.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if(starDateAsTimestamp<=now&&dueDateAsTimestamp>=now&&timeUtil.checkTodayFriday(today)=="Friday"&&getDate().equals(timeUtil.getLastFridayofMonth(0))&&timeUtil.checkIfMonthIsQuarter()==true){
					try{
						event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
						event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
						event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
						event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
						event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						event_targets.setEventTargetOldId(c.getString(c.getColumnIndex(CCH_OLD_ID)));
						event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
						event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
						event_targets.setEventTargetPeriod(c.getString(c.getColumnIndex(CCH_REMINDER)));
						event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
						event_targets.setEventTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
						event_targets.setEventTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
						event_targets.setEventTargetCategory(c.getString(c.getColumnIndex(CCH_TARGET_CATEGORY)));
						   list.add(event_targets);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return list;	
	}
	public ArrayList<EventTargets> getListOfMidYearTargetsToUpdate(String target_type)
	{	SQLiteDatabase db = this.getReadableDatabase();
		DateTime today=new DateTime();
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long now = 0;
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
						+" where "+CCH_REMINDER
						+" = '"+"Mid-year"+"'"
						+" and "+CCH_STATUS
						+" = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"'"
						+" and "+CCH_TARGET_TYPE
						+" = '"+target_type+"'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				String start_date=c.getString(c.getColumnIndex(CCH_START_DATE));
				String due_date=c.getString(c.getColumnIndex(CCH_DUE_DATE));
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					now = date3.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if(starDateAsTimestamp<=now&& dueDateAsTimestamp>=now&&timeUtil.checkTodayFriday(today)=="Friday"&&getDate().equals(timeUtil.getLastFridayofMonth(0))&&timeUtil.checkIfMonthIsMidYear()==true){
					try{
						event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
						event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
						event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
						event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
						event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						event_targets.setEventTargetOldId(c.getString(c.getColumnIndex(CCH_OLD_ID)));
						event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
						event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
						event_targets.setEventTargetPeriod(c.getString(c.getColumnIndex(CCH_REMINDER)));
						event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
						event_targets.setEventTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
						event_targets.setEventTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
						event_targets.setEventTargetCategory(c.getString(c.getColumnIndex(CCH_TARGET_CATEGORY)));
						   list.add(event_targets);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return list;	
	}
	
	public ArrayList<EventTargets> getListOfAnnualTargetsToUpdate(String target_type)
	{	SQLiteDatabase db = this.getReadableDatabase();
		DateTime today=new DateTime();
		Date date1 = null;
		Date date2 = null;
		Date date3= null;
		long starDateAsTimestamp = 0;
		long dueDateAsTimestamp = 0;
		long now = 0;
		ArrayList<EventTargets> list=new ArrayList<EventTargets>();	
		if(doesTableExists(TARGET_TABLE)){
		String strQuery="select * from "+TARGET_TABLE
						+" where "+CCH_REMINDER
						+" = '"+"Annually"+"'"
						+" and "+CCH_STATUS
						+" = '"+MobileLearning.CCH_TARGET_STATUS_NEW+"'"
						+" and "+CCH_TARGET_TYPE
						+" = '"+target_type +"'";
			Cursor c = db.rawQuery(strQuery, null);
			c.moveToFirst();
			
			while (c.isAfterLast()==false) {
				EventTargets event_targets = new EventTargets();
				try {
					date1 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_START_DATE)));
					date2 = new SimpleDateFormat("dd-MM-yyyy").parse(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
					date3= new SimpleDateFormat("dd-MM-yyyy").parse(getDate());
					starDateAsTimestamp = date1.getTime();
					dueDateAsTimestamp = date2.getTime();
					now = date3.getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if(starDateAsTimestamp<=now&& dueDateAsTimestamp>=now&&timeUtil.checkTodayFriday(today)=="Friday"&&getDate().equals(timeUtil.getLastFridayofMonth(0))&&timeUtil.checkIfMonthIsEndOfYear()==true){
					try{
						event_targets.setEventTargetName(c.getString(c.getColumnIndex(CCH_TARGET_NAME)));
						event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(CCH_TARGET_NO)));
						event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(CCH_TARGET_NO_ACHIEVED)));
						event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(CCH_START_DATE)));
						event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						event_targets.setEventTargetOldId(c.getString(c.getColumnIndex(CCH_OLD_ID)));
						event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(CCH_DUE_DATE)));
						event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(CCH_LAST_UPDATED)));
						event_targets.setEventTargetPeriod(c.getString(c.getColumnIndex(CCH_REMINDER)));
						event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(CCH_STATUS)));
						event_targets.setEventTargetDetail(c.getString(c.getColumnIndex(CCH_TARGET_DETAIL)));
						event_targets.setEventTargetType(c.getString(c.getColumnIndex(CCH_TARGET_TYPE)));
						event_targets.setEventTargetCategory(c.getString(c.getColumnIndex(CCH_TARGET_CATEGORY)));
						   list.add(event_targets);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				   c.moveToNext();		  				
			}
			c.close();
			
		}
			return list;	
	}
	
	public ArrayList<User> getUserFirstName(String staff_id){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<User> list=new ArrayList<User>();
		if(doesTableExists(CCH_USER_TABLE)){
		 String strQuery="select * from "+CCH_USER_TABLE
	             +" where "+CCH_STAFF_ID
	             + " = '"+staff_id+"'";	
		Cursor c=db.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			try{
			User u=new User();
			u.setFirstname(c.getString(c.getColumnIndex(CCH_USER_FIRSTNAME)));
			u.setLastname(c.getString(c.getColumnIndex(CCH_USER_LASTNAME)));
			u.setUserDistrict(c.getString(c.getColumnIndex(CCH_USER_DISTRICT)));
			u.setUserSubsistrict(c.getString(c.getColumnIndex(CCH_USER_SUBDISTRICT)));
			u.setUserZone(c.getString(c.getColumnIndex(CCH_USER_ZONE)));
			u.setUserFacility(c.getString(c.getColumnIndex(CCH_USER_FACILITY)));
			list.add(u);
			c.moveToNext();		
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		c.close();
		
		}
		return list;
	}
	public ArrayList<User> getUserFullName(){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<User> list=new ArrayList<User>();
		if(doesTableExists(CCH_USER_TABLE)){
		 String strQuery="select * from "+CCH_USER_TABLE;	
		Cursor c=db.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			try{
			User u=new User();
			u.setFirstname(c.getString(c.getColumnIndex(CCH_USER_FIRSTNAME)));
			u.setLastname(c.getString(c.getColumnIndex(CCH_USER_LASTNAME)));
			u.setStaffId(c.getString(c.getColumnIndex(CCH_STAFF_ID)));
			u.setUserDistrict(c.getString(c.getColumnIndex(CCH_USER_DISTRICT)));
			u.setUserSubsistrict(c.getString(c.getColumnIndex(CCH_USER_SUBDISTRICT)));
			u.setUserZone(c.getString(c.getColumnIndex(CCH_USER_ZONE)));
			u.setUserFacility(c.getString(c.getColumnIndex(CCH_USER_FACILITY)));
			list.add(u);
			c.moveToNext();		
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		c.close();
		
		}
		return list;
	}
	public ArrayList<Survey> getSurveyDetails(String reminder_date){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Survey> list=new ArrayList<Survey>();
		if(doesTableExists(SURVEY_TABLE)){
		 String strQuery="select * from "+SURVEY_TABLE;
	            
		Cursor c=db.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			try{
				Survey u=new Survey();
				if(c.getString(c.getColumnIndex(CCH_REMINDER_DATE)).contains(reminder_date)||c.getString(c.getColumnIndex(CCH_NEXT_REMINDER_DATE)).contains(reminder_date)){
				u.setSurveyDateTaken(c.getString(c.getColumnIndex(CCH_DATE_TAKEN)));
				u.setSurveyStatus(c.getString(c.getColumnIndex(CCH_SURVEY_STATUS)));
				u.setSurveyId(c.getString(c.getColumnIndex(BaseColumns._ID)));
				u.setSurveyReminderDate(c.getString(c.getColumnIndex(CCH_REMINDER_DATE)));
				u.setSurveyNextReminderDate(c.getString(c.getColumnIndex(CCH_NEXT_REMINDER_DATE)));
				list.add(u);
				//System.out.println(list.get(0).getSurveyReminderDate());
				}
				c.moveToNext();		
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		c.close();
		
		}else{
			createSurveyTable(db);
		}
		return list;
	}
	public ArrayList<Survey> getSurvey(){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Survey> list=new ArrayList<Survey>();
		if(doesTableExists(SURVEY_TABLE)){
		 String strQuery="select survey_status from "+SURVEY_TABLE;
	            
		Cursor c=db.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			try{
				Survey u=new Survey();
				u.setSurveyStatus(c.getString(c.getColumnIndex(CCH_SURVEY_STATUS)));
				list.add(u);
				//System.out.println(list.get(0).getSurveyReminderDate());
				c.moveToNext();		
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		c.close();
		
		}else{
			createSurveyTable(db);
		}
		return list;
	}
	
	public int getCourseGroups(){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Course> list=new ArrayList<Course>();
		int total=0;
		if(doesTableExists(COURSE_TABLE)){
		 String strQuery="select * from "+COURSE_TABLE
				 			+" where "+ COURSE_C_GROUP+" = "+ "''";
				 			
		Cursor c=db.rawQuery(strQuery, null);
		c.moveToFirst();
	
		while (c.isAfterLast()==false){
			try{
				Course course=new Course();
				course.setModId(Integer.parseInt(c.getString(c.getColumnIndex(COURSE_C_ID))));
				list.add(course);
				c.moveToNext();		
				total=list.size();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		c.close();
		}
	
		return total;
	}
	public ArrayList<Survey> getSurveys(){
		SQLiteDatabase db = this.getReadableDatabase();
		ArrayList<Survey> list=new ArrayList<Survey>();
		if(doesTableExists(SURVEY_TABLE)){
		String strQuery="select * from "+SURVEY_TABLE;
	            
		Cursor c=db.rawQuery(strQuery, null);
		c.moveToFirst();
		while (c.isAfterLast()==false){
			try{
				Survey u=new Survey();
				u.setSurveyDateTaken(c.getString(c.getColumnIndex(CCH_DATE_TAKEN)));
				u.setSurveyStatus(c.getString(c.getColumnIndex(CCH_SURVEY_STATUS)));
				u.setSurveyId(c.getString(c.getColumnIndex(BaseColumns._ID)));
				u.setSurveyReminderDate(c.getString(c.getColumnIndex(CCH_REMINDER_DATE)));
				u.setSurveyNextReminderDate(c.getString(c.getColumnIndex(CCH_NEXT_REMINDER_DATE)));
				u.setSurveyReminderFrequency(c.getString(c.getColumnIndex(CCH_REMINDER_FREQUENCY)));
				u.setSurveyReminderFrequencyValue(c.getString(c.getColumnIndex(CCH_REMINDER_FREQUENCY_VALUE)));
				list.add(u);
				c.moveToNext();		
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		c.close();
		
		}else{
			createSurveyTable(db);
		}
		return list;
	}
		
		public String getDateTime() {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
	        Date date = new Date();
	        return dateFormat.format(date);
	}
		
		public String getDate() {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "dd-MM-yyyy", Locale.getDefault());
	        Date date = new Date();
	        return dateFormat.format(date);
		}
		
		
		   public String getTime()
		    {
		    	 Time time = new Time();
				 time.setToNow();
				    
				 if (time.hour < 12)
				 {
					 return "morning";
				 } 
				 else if (time.hour >= 12 && time.hour <= 17)
				 {
				     return "afternoon";
				 }
				 else if (time.hour > 17 && time.hour < 23)
				 {
				     return "evening";
				 } 
				 else 
				 {
				      return "";
				 }
		    }
			
		   public String getBatteryStatus(Context c)
		    {
			   String battery;
			   Intent batteryIntent = c.getApplicationContext().registerReceiver(null,
	                    new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			   				int rawlevel = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
			   				int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
			   				int level = -1;
			   				if (rawlevel >= 0 && scale > 0) {
			   					level = (rawlevel *100) / scale;
			   				}
			   		battery=String.valueOf(level+"%");
				return battery;
			  }
			 
			  
		   
		   public String getVersionNumber(Context context)
		   
		    {
			   String versionNumber = null;
			   try{
				   String PACKAGE_NAME=context.getPackageName();
				   PackageInfo info = context.getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
				   versionNumber = info.versionName;
			   }catch(NameNotFoundException e){
				   e.printStackTrace();
			   }
			   return versionNumber;
		    }
		   public String getDeviceName() {
			    String manufacturer = Build.MANUFACTURER;
			    String model = Build.MODEL;
			    if (model.startsWith(manufacturer)) {
			        return capitalize(model);
			    } else {
			        return capitalize(manufacturer) + " " + model;
			    }
			}

		   	public boolean isDateBefore(String date){
		   		boolean isDateBefore = false;
		   		DateTimeFormatter formatter = DateTimeFormat.forPattern( "dd-MM-yyyy" );
		   		LocalDate comparedDate = formatter.parseLocalDate( date );
		   		LocalDate today = new LocalDate();
		   		
		   		if(comparedDate.isBefore(today)){
		   			isDateBefore=true;
		   		}else{
		   			isDateBefore=false;
		   		}
		   		return isDateBefore;
		   	}
		   	
		   	public boolean isDateAfter(String date){
		   		boolean isDateBefore = false;
		   		DateTimeFormatter formatter = DateTimeFormat.forPattern( "dd-MM-yyyy" );
		   		LocalDate comparedDate = formatter.parseLocalDate( date );
		   		LocalDate today = new LocalDate();
		   		
		   		if(comparedDate.isAfter(today)){
		   			isDateBefore=true;
		   		}else{
		   			isDateBefore=false;
		   		}
		   		return isDateBefore;
		   	}
			private String capitalize(String s) {
			    if (s == null || s.length() == 0) {
			        return "";
			    }
			    char first = s.charAt(0);
			    if (Character.isUpperCase(first)) {
			        return s;
			    } else {
			        return Character.toUpperCase(first) + s.substring(1);
			    }
			} 	
			
			public String getDeviceImei(Context context){
				String device_id = null;
				TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				device_id = tm.getDeviceId();
				return device_id;
			}
			
			public boolean isOnline(Context context) {
			    ConnectivityManager cm =
			        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo netInfo = cm.getActiveNetworkInfo();
			    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			        return true;
			    }
			    return false;
			}
// Transforming target setting			
			public ArrayList<EventTargets> getAllEventTargets() 
			{	
				SQLiteDatabase db = this.getReadableDatabase();
				ArrayList<EventTargets> list=new ArrayList<EventTargets>();	 
				String strQuery="select * from "+EVENTS_SET_TABLE;	
					Cursor c = db.rawQuery(strQuery, null);
					c.moveToFirst();
					
					while (c.isAfterLast()==false) {
						EventTargets event_targets = new EventTargets();
						event_targets.setEventTargetName(c.getString(c.getColumnIndex(COL_EVENT_SET_NAME)));
						event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(COL_EVENT_NUMBER)));
						event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(COL_NUMBER_ACHIEVED)));
						event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
						event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(COL_EVENT_DUE_DATE)));
						event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(COL_LAST_UPDATED)));
						event_targets.setEventTargetPeriod(c.getString(c.getColumnIndex(COL_EVENT_PERIOD)));
						event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
						event_targets.setEventTargetDetail(c.getString(c.getColumnIndex(COL_EVENT_SET_DETAIL)));
						event_targets.setEventTargetNumberRemaining(c.getString(c.getColumnIndex(COL_NUMBER_REMAINING)));
						   list.add(event_targets);
						   c.moveToNext();						
					}
					c.close();
					
					return list;	
			}
			
			
			public ArrayList<EventTargets> getAllCoverageTargets() 
			{	
				SQLiteDatabase db = this.getReadableDatabase();
				ArrayList<EventTargets> list=new ArrayList<EventTargets>();	 
				String strQuery="select * from "+COVERAGE_SET_TABLE;	
					Cursor c = db.rawQuery(strQuery, null);
					c.moveToFirst();
					
					while (c.isAfterLast()==false) {
						EventTargets event_targets = new EventTargets();
						event_targets.setEventTargetName(c.getString(c.getColumnIndex(COL_COVERAGE_SET_CATEGORY_NAME)));
						event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(COL_COVERAGE_NUMBER)));
						event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(COL_NUMBER_ACHIEVED)));
						event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
						event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(COL_COVERAGE_DUE_DATE)));
						event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(COL_LAST_UPDATED)));
						event_targets.setEventTargetPeriod(c.getString(c.getColumnIndex(COL_COVERAGE_SET_PERIOD)));
						event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
						event_targets.setEventTargetDetail(c.getString(c.getColumnIndex(COL_COVERAGE_SET_CATEGORY_DETAIL)));
						event_targets.setEventTargetNumberRemaining(c.getString(c.getColumnIndex(COL_NUMBER_REMAINING)));
						   list.add(event_targets);
						   c.moveToNext();						
					}
					c.close();
					
					return list;	
			}
			
			public ArrayList<EventTargets> getAllOtherTargets() 
			{		SQLiteDatabase db = this.getReadableDatabase();
				ArrayList<EventTargets> list=new ArrayList<EventTargets>();	 
				String strQuery="select * from "+OTHER_TABLE;	
					Cursor c = db.rawQuery(strQuery, null);
					c.moveToFirst();
					
					while (c.isAfterLast()==false) {
						EventTargets event_targets = new EventTargets();
						event_targets.setEventTargetName(c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
						event_targets.setEventTargetNumber(c.getString(c.getColumnIndex(COL_OTHER_NUMBER)));
						event_targets.setEventTargetNumberAchieved(c.getString(c.getColumnIndex(COL_NUMBER_ACHIEVED)));
						event_targets.setEventTargetStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
						event_targets.setEventTargetId(c.getString(c.getColumnIndex(BaseColumns._ID)));
						event_targets.setEventTargetEndDate(c.getString(c.getColumnIndex(COL_OTHER_DUE_DATE)));
						event_targets.setEventTargetLastUpdated(c.getString(c.getColumnIndex(COL_LAST_UPDATED)));
						event_targets.setEventTargetPeriod(c.getString(c.getColumnIndex(COL_OTHER_PERIOD)));
						event_targets.setEventTargetStatus(c.getString(c.getColumnIndex(COL_SYNC_STATUS)));
						event_targets.setEventTargetPersonalCategory(c.getString(c.getColumnIndex(COL_OTHER_DETAILS)));
						event_targets.setEventTargetNumberRemaining(c.getString(c.getColumnIndex(COL_NUMBER_REMAINING)));
						   list.add(event_targets);
						   c.moveToNext();						
					}
					c.close();
					
					return list;	
			}
			public ArrayList<MyCalendarEvents> getTodaysEvents() {
			SQLiteDatabase db = this.getReadableDatabase();
			ArrayList<MyCalendarEvents> list=new ArrayList<MyCalendarEvents>();	
			
			if(doesTableExists(CALENDAR_EVENTS_TABLE)){
			String strQuery="select * from "+CALENDAR_EVENTS_TABLE;
			String dformat ="MMM-dd hh:mm a";
 	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
 	       Calendar calendar = Calendar.getInstance();
				Cursor c = db.rawQuery(strQuery, null);
				c.moveToFirst();
				while (c.isAfterLast()==false) {
					MyCalendarEvents events = new MyCalendarEvents();
					 calendar.setTimeInMillis(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
			 	    	String d =formatter.format(calendar.getTime());
			 	    	formatter.format(calendar.getTime());
			 	    	LocalDate now = new LocalDate();
			 	    	LocalDate previous = new LocalDate(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
					// if ((now.getDayOfMonth()==previous.getDayOfMonth())
						//	 &&(now.getMonthOfYear()==previous.getMonthOfYear())
							// &&(now.getYear()==previous.getYear()));
			 	    	if(now.toString("dd/MM/YYYY").equals(previous.toString("dd/MM/YYYY"))){
						try{
							events.setEventType(c.getString(c.getColumnIndex(COL_EVENTTYPE)));
							events.setEventCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
							events.setEventLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
							events.setEventId(c.getString(c.getColumnIndex(COL_EVENT_ID)));
							events.setEventStatus(c.getString(c.getColumnIndex(COL_STATUS)));
							events.setEventDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));
							events.setEventComment(c.getString(c.getColumnIndex(COL_COMMENTS)));
							events.setEventJustification(c.getString(c.getColumnIndex(COL_JUSTIFICATION)));
							events.setEventEndDate(c.getString(c.getColumnIndex(COL_ENDDATE)));
							events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
							events.setEventTime(d);
							   list.add(events);
						}catch(Exception e){
							e.printStackTrace();
						}
		        		}
					   c.moveToNext();
				}
				c.close();
				
			}else{
				createCalendarEventsTable(db);
			}
				
		       return list;
		   	}
			
			public ArrayList<MyCalendarEvents> getPastLastMonthEvents() {
				SQLiteDatabase db = this.getReadableDatabase();
				ArrayList<MyCalendarEvents> list=new ArrayList<MyCalendarEvents>();	
				
				if(doesTableExists(CALENDAR_EVENTS_TABLE)){
				String strQuery="select * from "+CALENDAR_EVENTS_TABLE;
				String dformat ="MMM-dd hh:mm a";
	 	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
	 	       Calendar calendar = Calendar.getInstance();
					Cursor c = db.rawQuery(strQuery, null);
					c.moveToFirst();
					while (c.isAfterLast()==false) {
						MyCalendarEvents events = new MyCalendarEvents();
						calendar.setTimeInMillis(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
			 	    	String d =formatter.format(calendar.getTime());
			 	    	formatter.format(calendar.getTime());
			 	    	LocalDate previous = new LocalDate(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
			 	    	LocalDate lastmonth = new LocalDate().minusMonths(1);
						
						if((previous.getMonthOfYear()==lastmonth.getMonthOfYear())
								&&(previous.getYear()==lastmonth.getYear())){
							
						 //if (isPastLastMonth(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE)))).equals("true"));
			        	  // {
							try{
								events.setEventType(c.getString(c.getColumnIndex(COL_EVENTTYPE)));
								events.setEventLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
								events.setEventId(c.getString(c.getColumnIndex(COL_EVENT_ID)));
								events.setEventStatus(c.getString(c.getColumnIndex(COL_STATUS)));
								events.setEventCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
								events.setEventDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));
								events.setEventComment(c.getString(c.getColumnIndex(COL_COMMENTS)));
								events.setEventJustification(c.getString(c.getColumnIndex(COL_JUSTIFICATION)));
								events.setEventEndDate(c.getString(c.getColumnIndex(COL_ENDDATE)));
								events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
								events.setEventTime(d);
								   list.add(events);
							}catch(Exception e){
								e.printStackTrace();
							}
			        		}
						   c.moveToNext();
					}
					c.close();
					
				}else{
					createCalendarEventsTable(db);
				}
					
			       return list;
			   	}
			public ArrayList<MyCalendarEvents> getPastLastMonthEvents(String status) {
				SQLiteDatabase db = this.getReadableDatabase();
				ArrayList<MyCalendarEvents> list=new ArrayList<MyCalendarEvents>();	
				
				if(doesTableExists(CALENDAR_EVENTS_TABLE)){
				String strQuery="select * from "+CALENDAR_EVENTS_TABLE
								+" where "+COL_STATUS+
								" ='"+status+"'";
				String dformat ="MMM-dd hh:mm a";
	 	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
	 	       Calendar calendar = Calendar.getInstance();
					Cursor c = db.rawQuery(strQuery, null);
					c.moveToFirst();
					while (c.isAfterLast()==false) {
						MyCalendarEvents events = new MyCalendarEvents();
						calendar.setTimeInMillis(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
			 	    	String d =formatter.format(calendar.getTime());
			 	    	formatter.format(calendar.getTime());
			 	    	LocalDate previous = new LocalDate(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
			 	    	LocalDate lastmonth = new LocalDate().minusMonths(1);
						
						if((previous.getMonthOfYear()==lastmonth.getMonthOfYear())
								&&(previous.getYear()==lastmonth.getYear())){
							
						 //if (isPastLastMonth(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE)))).equals("true"));
			        	  // {
							try{
								events.setEventType(c.getString(c.getColumnIndex(COL_EVENTTYPE)));
								events.setEventLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
								events.setEventId(c.getString(c.getColumnIndex(COL_EVENT_ID)));
								events.setEventStatus(c.getString(c.getColumnIndex(COL_STATUS)));
								events.setEventDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));
								events.setEventComment(c.getString(c.getColumnIndex(COL_COMMENTS)));
								events.setEventCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
								events.setEventJustification(c.getString(c.getColumnIndex(COL_JUSTIFICATION)));
								events.setEventEndDate(c.getString(c.getColumnIndex(COL_ENDDATE)));
								events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
								events.setEventTime(d);
								   list.add(events);
							}catch(Exception e){
								e.printStackTrace();
							}
			        		}
						   c.moveToNext();
					}
					c.close();
					
				}else{
					createCalendarEventsTable(db);
				}
					
			       return list;
			   	}
			public ArrayList<MyCalendarEvents> getPastThisMonthEvents() {
				SQLiteDatabase db = this.getReadableDatabase();
				ArrayList<MyCalendarEvents> list=new ArrayList<MyCalendarEvents>();	
				
				if(doesTableExists(CALENDAR_EVENTS_TABLE)){
				String strQuery="select * from "+CALENDAR_EVENTS_TABLE;
				String dformat ="MMM-dd hh:mm a";
	 	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
	 	       Calendar calendar = Calendar.getInstance();
					Cursor c = db.rawQuery(strQuery, null);
					c.moveToFirst();
					while (c.isAfterLast()==false) {
						MyCalendarEvents events = new MyCalendarEvents();
						events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
						 calendar.setTimeInMillis(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
				 	    	String d =formatter.format(calendar.getTime());
				 	    	formatter.format(calendar.getTime());
				 	    	LocalDate previous = new LocalDate(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
				 	    	LocalDate now = new LocalDate().minusDays(2);
				 	    	DateTime today=new DateTime();
				 	    	if(previous.getDayOfMonth()<=now.getDayOfMonth()
									&&previous.getMonthOfYear()==now.getMonthOfYear()
									&&previous.getYear()==now.getYear()){
					
							try{
								
								events.setEventType(c.getString(c.getColumnIndex(COL_EVENTTYPE)));
								events.setEventLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
								events.setEventId(c.getString(c.getColumnIndex(COL_EVENT_ID)));
								events.setEventStatus(c.getString(c.getColumnIndex(COL_STATUS)));
								events.setEventDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));
								events.setEventJustification(c.getString(c.getColumnIndex(COL_JUSTIFICATION)));
								events.setEventComment(c.getString(c.getColumnIndex(COL_COMMENTS)));
								events.setEventCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
								events.setEventEndDate(c.getString(c.getColumnIndex(COL_ENDDATE)));
								events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
								events.setEventTime(d);
								   list.add(events);
						   
							}catch(Exception e){
								e.printStackTrace();
							}
			        		}
						   c.moveToNext();
			        	   
					}
					c.close();
					
				}else{
					createCalendarEventsTable(db);
				}
					
			       return list;
			   	}
			
			public ArrayList<MyCalendarEvents> getPastThisMonthEvents(String status) {
				SQLiteDatabase db = this.getReadableDatabase();
				ArrayList<MyCalendarEvents> list=new ArrayList<MyCalendarEvents>();	
				
				if(doesTableExists(CALENDAR_EVENTS_TABLE)){
					String strQuery="select * from "+CALENDAR_EVENTS_TABLE
							+" where "+COL_STATUS+
							" ='"+status+"'";
				String dformat ="MMM-dd hh:mm a";
	 	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
	 	       Calendar calendar = Calendar.getInstance();
					Cursor c = db.rawQuery(strQuery, null);
					c.moveToFirst();
					while (c.isAfterLast()==false) {
						MyCalendarEvents events = new MyCalendarEvents();
						events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
						 calendar.setTimeInMillis(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
				 	    	String d =formatter.format(calendar.getTime());
				 	    	formatter.format(calendar.getTime());
				 	    	LocalDate previous = new LocalDate(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
				 	    	LocalDate now = new LocalDate().minusDays(2);
				 	    	DateTime today=new DateTime();
							if(previous.getDayOfMonth()<=now.getDayOfMonth()
									&&previous.getMonthOfYear()==now.getMonthOfYear()
									&&previous.getYear()==now.getYear()){
					
							try{
								System.out.println("Previous: "+previous.getDayOfMonth()+"/"+previous.getMonthOfYear());
								System.out.println("Now: "+now.getDayOfMonth()+"/"+today.getMonthOfYear());
								events.setEventType(c.getString(c.getColumnIndex(COL_EVENTTYPE)));
								events.setEventLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
								events.setEventId(c.getString(c.getColumnIndex(COL_EVENT_ID)));
								events.setEventStatus(c.getString(c.getColumnIndex(COL_STATUS)));
								events.setEventDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));
								events.setEventCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
								events.setEventJustification(c.getString(c.getColumnIndex(COL_JUSTIFICATION)));
								events.setEventComment(c.getString(c.getColumnIndex(COL_COMMENTS)));
								events.setEventEndDate(c.getString(c.getColumnIndex(COL_ENDDATE)));
								events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
								events.setEventTime(d);
								   list.add(events);
						   
							}catch(Exception e){
								e.printStackTrace();
							}
			        		}
						   c.moveToNext();
			        	   
					}
					c.close();
					
				}else{
					createCalendarEventsTable(db);
				}
					
			       return list;
			   	}
			public ArrayList<MyCalendarEvents> getYesterdaysEvents() {
				SQLiteDatabase db = this.getReadableDatabase();
				ArrayList<MyCalendarEvents> list=new ArrayList<MyCalendarEvents>();	
				
				if(doesTableExists(CALENDAR_EVENTS_TABLE)){
				String strQuery="select * from "+CALENDAR_EVENTS_TABLE;
				String dformat ="MMM-dd hh:mm a";
	 	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
	 	       Calendar calendar = Calendar.getInstance();
					Cursor c = db.rawQuery(strQuery, null);
					c.moveToFirst();
					while (c.isAfterLast()==false) {
						MyCalendarEvents events = new MyCalendarEvents();
						 calendar.setTimeInMillis(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
				 	    	String d =formatter.format(calendar.getTime());
				 	    	formatter.format(calendar.getTime());
			    			LocalDate previous = new LocalDate(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
			    			LocalDate now = new LocalDate().minusDays(1);
		    				try{
			    			if((now.getDayOfMonth()==previous.getDayOfMonth())
			    					&&(now.getMonthOfYear()==previous.getMonthOfYear()
			    					&&(now.getYear()==previous.getYear()))){
								events.setEventType(c.getString(c.getColumnIndex(COL_EVENTTYPE)));
								events.setEventLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
								events.setEventId(c.getString(c.getColumnIndex(COL_EVENT_ID)));
								events.setEventStatus(c.getString(c.getColumnIndex(COL_STATUS)));
								events.setEventDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));
								events.setEventComment(c.getString(c.getColumnIndex(COL_COMMENTS)));
								events.setEventCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
								events.setEventJustification(c.getString(c.getColumnIndex(COL_JUSTIFICATION)));
								events.setEventEndDate(c.getString(c.getColumnIndex(COL_ENDDATE)));
								events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
								events.setEventTime(d);
								  list.add(events);
					        	   
			    				}  
							}catch(Exception e){
								e.printStackTrace();
							}
			    			
						   c.moveToNext();
			        	   
					}
					c.close();
					
				}else{
					createCalendarEventsTable(db);
				}
					
			       return list;
			   	}
			public ArrayList<MyCalendarEvents> getYesterdaysEvents(String status) {
				SQLiteDatabase db = this.getReadableDatabase();
				ArrayList<MyCalendarEvents> list=new ArrayList<MyCalendarEvents>();	
				
				if(doesTableExists(CALENDAR_EVENTS_TABLE)){
					String strQuery="select * from "+CALENDAR_EVENTS_TABLE
							+" where "+COL_STATUS+
							" ='"+status+"'";
				String dformat ="MMM-dd hh:mm a";
	 	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
	 	       Calendar calendar = Calendar.getInstance();
					Cursor c = db.rawQuery(strQuery, null);
					c.moveToFirst();
					while (c.isAfterLast()==false) {
						MyCalendarEvents events = new MyCalendarEvents();
						 calendar.setTimeInMillis(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
				 	    	String d =formatter.format(calendar.getTime());
				 	    	formatter.format(calendar.getTime());
			    			LocalDate previous = new LocalDate(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
			    			LocalDate now = new LocalDate().minusDays(1);
		    				try{
			    			if((now.getDayOfMonth()==previous.getDayOfMonth())
			    					&&(now.getMonthOfYear()==previous.getMonthOfYear()
			    					&&(now.getYear()==previous.getYear()))){
								events.setEventType(c.getString(c.getColumnIndex(COL_EVENTTYPE)));
								events.setEventLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
								events.setEventId(c.getString(c.getColumnIndex(COL_EVENT_ID)));
								events.setEventStatus(c.getString(c.getColumnIndex(COL_STATUS)));
								events.setEventDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));
								events.setEventComment(c.getString(c.getColumnIndex(COL_COMMENTS)));
								events.setEventCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
								events.setEventJustification(c.getString(c.getColumnIndex(COL_JUSTIFICATION)));
								events.setEventEndDate(c.getString(c.getColumnIndex(COL_ENDDATE)));
								events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
								events.setEventTime(d);
								  list.add(events);
					        	   
			    				}  
							}catch(Exception e){
								e.printStackTrace();
							}
			    			
						   c.moveToNext();
			        	   
					}
					c.close();
					
				}else{
					createCalendarEventsTable(db);
				}
					
			       return list;
			   	}
			public ArrayList<MyCalendarEvents> getTomorrowEvents() {
				SQLiteDatabase db = this.getReadableDatabase();
				ArrayList<MyCalendarEvents> list=new ArrayList<MyCalendarEvents>();	
				
				if(doesTableExists(CALENDAR_EVENTS_TABLE)){
				String strQuery="select * from "+CALENDAR_EVENTS_TABLE;
				String dformat ="MMM-dd hh:mm a";
	 	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
	 	       Calendar calendar = Calendar.getInstance();
					Cursor c = db.rawQuery(strQuery, null);
					c.moveToFirst();
					while (c.isAfterLast()==false) {
						MyCalendarEvents events = new MyCalendarEvents();
						calendar.setTimeInMillis(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
				 	    String d =formatter.format(calendar.getTime());
				 	    formatter.format(calendar.getTime());
				 	   LocalDate previous = new LocalDate(Long.parseLong(c.getString(c.getColumnIndex(COL_START_DATE))));
				 	  LocalDate now = new LocalDate().plusDays(1);
						if((now.getDayOfMonth()==previous.getDayOfMonth())
								&&(now.getMonthOfYear()==previous.getMonthOfYear())
								&&(now.getYear()==previous.getYear())){
							
							try{
								events.setEventType(c.getString(c.getColumnIndex(COL_EVENTTYPE)));
								events.setEventLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
								events.setEventId(c.getString(c.getColumnIndex(COL_EVENT_ID)));
								events.setEventStatus(c.getString(c.getColumnIndex(COL_STATUS)));
								events.setEventDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));
								events.setEventComment(c.getString(c.getColumnIndex(COL_COMMENTS)));
								events.setEventCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
								events.setEventJustification(c.getString(c.getColumnIndex(COL_JUSTIFICATION)));
								events.setEventEndDate(c.getString(c.getColumnIndex(COL_ENDDATE)));
								events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
								events.setEventTime(d);
								   list.add(events);
							}catch(Exception e){
								e.printStackTrace();
							}
			        		}
						   c.moveToNext();
			        	   
					}
					c.close();
					
				}else{
					createCalendarEventsTable(db);
				}
			       return list;
			   	}
			public ArrayList<MyCalendarEvents> getFutureEvents() {
				SQLiteDatabase db = this.getReadableDatabase();
				ArrayList<MyCalendarEvents> list=new ArrayList<MyCalendarEvents>();	
				if(doesTableExists(CALENDAR_EVENTS_TABLE)){
				String strQuery="select * from "+CALENDAR_EVENTS_TABLE;
				String dformat ="MMM-dd hh:mm a";
	 	       SimpleDateFormat formatter = new SimpleDateFormat(dformat);
	 	       Calendar calendar = Calendar.getInstance();
					Cursor c = db.rawQuery(strQuery, null);
					c.moveToFirst();
					while (c.isAfterLast()==false) {
						MyCalendarEvents events = new MyCalendarEvents();
						calendar.setTimeInMillis(Long.valueOf(c.getString(c.getColumnIndex(COL_START_DATE))));
				 	    String d =formatter.format(calendar.getTime());
				 	    formatter.format(calendar.getTime());
				 	   LocalDate previous = new LocalDate(Long.valueOf(c.getString(c.getColumnIndex(COL_START_DATE))));
				 	  LocalDate now = new LocalDate().plusDays(2);
						if((previous.getMonthOfYear()>now.getMonthOfYear())
								&&(previous.getYear()>=now.getYear())){
					
							try{
								events.setEventType(c.getString(c.getColumnIndex(COL_EVENTTYPE)));
								events.setEventLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
								events.setEventId(c.getString(c.getColumnIndex(COL_EVENT_ID)));
								events.setEventStatus(c.getString(c.getColumnIndex(COL_STATUS)));
								events.setEventDescription(c.getString(c.getColumnIndex(COL_DESCRIPTION)));
								events.setEventCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
								events.setEventComment(c.getString(c.getColumnIndex(COL_COMMENTS)));
								events.setEventJustification(c.getString(c.getColumnIndex(COL_JUSTIFICATION)));
								events.setEventEndDate(c.getString(c.getColumnIndex(COL_ENDDATE)));
								events.setEventStartDate(c.getString(c.getColumnIndex(COL_START_DATE)));
								events.setEventTime(d);
								   list.add(events);
							}catch(Exception e){
								e.printStackTrace();
							}
			        		}
						   c.moveToNext();
			        	   
					}
					c.close();
					
				}else{
					createCalendarEventsTable(db);
				}
					
			       return list;
			   	}
			public boolean isOnline() {
				 boolean haveConnectedWifi = false;
				    boolean haveConnectedMobile = false;

				    ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
				    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
				    for (NetworkInfo ni : netInfo) {
				        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				            if (ni.isConnected())
				                haveConnectedWifi = true;
				        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				            if (ni.isConnected())
				                haveConnectedMobile = true;
				    }
				    return haveConnectedWifi || haveConnectedMobile;
			}
			public boolean isToday(long startDate)
	    	{
	    			long milliSeconds = startDate;
	    	    	String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date(System.currentTimeMillis()));
	    	        return (DateFormat.format("MM/dd/yyyy", new Date(milliSeconds))
	    	       				.toString().equals(today)) ? true : false;
	    	}
	    	
	    	public String isTomorrow(long startDate)
	    	{
	    		String result;
	    		long milliSeconds = startDate;
				DateTime previous = new DateTime(milliSeconds);
				DateTime now = new DateTime();
				if(now.plusDays(1)==previous){
					result="true";
    			}else {
    				result= "false";
    			}
				//System.out.println("Tommorrow: "+result);
				return result;
	    	}
	    	public String isYesterday(long startDate)
	    	{
	    		String result;
	    			long milliSeconds = startDate;
	    			DateTime previous = new DateTime(milliSeconds);
	    			DateTime now = new DateTime();
	    			if((now.minusDays(1).getDayOfMonth()==previous.getDayOfMonth())){
	    				result= "true";
	    			}else {
	    				result= "false";
	    			}
	    			//System.out.println("Yesterday: "+result);
	    			return result;
	    	}
	    	public String isFuture(long startDate){
	    		String result;
	    		long milliSeconds = startDate;
				DateTime previous = new DateTime(milliSeconds);
				DateTime now = new DateTime();
				if(now.plusDays(2).getMillis()>=previous.getMillis()){
					result= "true";
    			}else {
    				result= "false";
    			}
				//System.out.println("Future: "+result);
				return result;
	    	}
	    	public String isPastThisMonth(long startDate) {
	    		String result;
	    		long milliSeconds = startDate;
				DateTime previous = new DateTime(milliSeconds);
				DateTime now = new DateTime();
				if((now.minusDays(2).getMillis()>=previous.getMillis())&&(previous.getMonthOfYear()==now.getMonthOfYear())){
					result= "true";
    			}else {
    				result= "false";
    			}
				//System.out.println("Past this month: "+result);
				return result;
	    		}
	    	public String isPastLastMonth(long startDate) {
	    		String result;
	    		long milliSeconds = startDate;
				DateTime previous = new DateTime(milliSeconds);
				DateTime lastmonth = new DateTime().minusMonths(1);
				if(previous.getMonthOfYear()==lastmonth.getMonthOfYear()){
					result= "true";
    			}else {
    				result= "false";
    			}
				//System.out.println("Past last month: "+result);
				return result;
	    		}
	    	public String getPreviousMonth(String month){
	    		String previous_month=null;
	    		switch (month) {
				case "January":
					previous_month="";
					break;
				case "February":
					previous_month="January";
					break;
				case "March":
					previous_month="February";
					break;
				case "April":
					previous_month="March";
					break;
				case "May":
					previous_month="April";
					break;
				case "June":
					previous_month="May";
					break;
				case "July":
					previous_month="June";
					break;
				case "August":
					previous_month="July";
					break;
				case "September":
					previous_month="August";
					break;
				case "October":
					previous_month="September";
					break;
				case "November":
					previous_month="October";
					break;
				case "December":
					previous_month="November";
					break;
				default:
					break;
				}
	    				return previous_month;
	    	}
}
