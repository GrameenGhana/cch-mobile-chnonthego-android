package org.grameenfoundation.database;

import android.provider.BaseColumns;

public class CHNDataClass {

public CHNDataClass(){
		
	}
	
public static abstract class CHNDatabase implements BaseColumns{
	//table names
	public static final String EVENTS_CATEGORY_TABLE="events_category";
	public static final String EVENTS_SET_TABLE="events_set";
	public static final String COVERAGE_CATEGORY_TABLE="coverage_category";
	public static final String COVERAGE_SET_TABLE="coverage_set";
	public static final String LEARNING_TABLE="learning";
	public static final String LOGIN_ACTIVITY_TABLE_NAME="login_activity";
	public static final String OTHER_TABLE="other";
	public static final String USER_TABLE="users";
	
	//column names for events_category
	public static final String COL_EVENT_NAME="event_name";

	//column names for events_set
	public static final String COL_EVENT_SET_NAME="event_name";
	public static final String COL_EVENT_PERIOD="event_period";
	public static final String COL_EVENT_NUMBER="event_number";
	public static final String COL_EVENT_MONTH="month";
	public static final String COL_SYNC_STATUS ="sync_status";
	
	//column names for coverage_category
	public static final String COL_CATEGORY_NAME="category_name";
	public static final String COL_CATEGORY_DETAIL="category_detail";

	//column names for coverage_set
	public static final String COL_COVERAGE_SET_CATEGORY_NAME="category_name";
	public static final String COL_COVERAGE_SET_CATEGORY_DETAIL="category_detail";
	public static final String COL_COVERAGE_SET_PERIOD="coverage_period";
	public static final String COL_COVERAGE_NUMBER="category_number";
	public static final String COL_COVERAGE_MONTH="month";
	
	
	//column names for users
	public static final String COL_FIRSTNAME="firstname";
	public static final String COL_LASTNAME="lastname";
	public static final String COL_USERNAME="username";
	public static final String COL_PASSWORD="password";
		

	//column names for learning table
	public static final String COL_LEARNING_CATEGORY="learning_category";
	public static final String COL_LEARNING_DESCRIPTION="learning_description";
	public static final String COL_LEARNING_MONTH="month";

	//column names for other table
	public static final String COL_OTHER_CATEGORY="other_category";
	public static final String COL_OTHER_NUMBER="other_number";
	public static final String COL_OTHER_PERIOD="other_period";
	public static final String COL_OTHER_MONTH="month";
	//column names for login activity table
		public static final String 	COL_DATE_LOGGED_IN="date_logged_in";
		public static final String 	COL_TIME_LOGGED_IN="time_logged_in";
		public static final String 	COL_USERNAME_LOGIN_ACTIVITY="username";
		public static final String COL_PASSWORD_LOGIN_ACTIVITY="password";
		public static final String COL_LOGIN_UPDATE_STATUS="update_status";
	}
	
	//table create statement  for user table
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	
	public static final String USER_TABLE_CREATE_TABLE =
	    "CREATE TABLE " + CHNDatabase.USER_TABLE + " (" +
	    		CHNDatabase._ID + " INTEGER PRIMARY KEY," +
	    		CHNDatabase.COL_FIRSTNAME + TEXT_TYPE + COMMA_SEP +
	    		CHNDatabase.COL_LASTNAME+ TEXT_TYPE + COMMA_SEP +
	    		CHNDatabase.COL_USERNAME + TEXT_TYPE + COMMA_SEP +
	    		CHNDatabase.COL_PASSWORD + TEXT_TYPE + COMMA_SEP +
	    		CHNDatabase.COL_SYNC_STATUS+ TEXT_TYPE + 
	    " )";
	//table create statement for events category table
		public static final String EVENT_CATEGORY_CREATE_TABLE =
			    "CREATE TABLE " + CHNDatabase.EVENTS_CATEGORY_TABLE + " (" +
			    		CHNDatabase._ID + " INTEGER PRIMARY KEY," +
			    		CHNDatabase.COL_EVENT_NAME + TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_SYNC_STATUS + TEXT_TYPE+
			    " )";
		
		//	table create statement for events set table 
		public static final String EVENT_SET_CREATE_TABLE =
			    "CREATE TABLE " + CHNDatabase.EVENTS_SET_TABLE + " (" +
			    		CHNDatabase._ID + " INTEGER PRIMARY KEY," +
			    		CHNDatabase.COL_EVENT_SET_NAME + TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_EVENT_PERIOD + TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_SYNC_STATUS + TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_EVENT_MONTH + TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_EVENT_NUMBER+TEXT_TYPE+
			    " )";
		
		//table create statement for events category table
				public static final String COVERAGE_CREATE_TABLE =
					    "CREATE TABLE " + CHNDatabase.COVERAGE_CATEGORY_TABLE + " (" +
					    		CHNDatabase._ID + " INTEGER PRIMARY KEY," +
					    		CHNDatabase.COL_CATEGORY_NAME+ TEXT_TYPE + COMMA_SEP +
					    		CHNDatabase.COL_CATEGORY_DETAIL+ TEXT_TYPE + COMMA_SEP +
					    		CHNDatabase.COL_SYNC_STATUS + TEXT_TYPE+
					    " )";
				
				//	table create statement for events set table 
				public static final String COVERAGE_SET_CREATE_TABLE =
					    "CREATE TABLE " + CHNDatabase.COVERAGE_SET_TABLE + " (" +
					    		CHNDatabase._ID + " INTEGER PRIMARY KEY," +
					    		CHNDatabase.COL_COVERAGE_SET_CATEGORY_NAME + TEXT_TYPE + COMMA_SEP +
					    		CHNDatabase.COL_COVERAGE_SET_CATEGORY_DETAIL + TEXT_TYPE + COMMA_SEP +
					    		CHNDatabase.COL_COVERAGE_SET_PERIOD + TEXT_TYPE + COMMA_SEP +
					    		CHNDatabase.COL_COVERAGE_NUMBER + TEXT_TYPE + COMMA_SEP +
					    		CHNDatabase.COL_COVERAGE_MONTH+ TEXT_TYPE + COMMA_SEP +
					    		CHNDatabase.COL_SYNC_STATUS+TEXT_TYPE+
					    " )";
				
		//	table create statement for learning table
		public static final String LEARNING_CREATE_TABLE =
			    "CREATE TABLE " + CHNDatabase.LEARNING_TABLE + " (" +
			    		CHNDatabase._ID + " INTEGER PRIMARY KEY," +
			    		CHNDatabase.COL_LEARNING_CATEGORY + TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_LEARNING_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_LEARNING_MONTH+ TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_SYNC_STATUS+ TEXT_TYPE+
			    " )";
		
		//	table create statement for other table
		public static final String OTHER_CREATE_TABLE =
			    "CREATE TABLE " + CHNDatabase.OTHER_TABLE + " (" +
			    		CHNDatabase._ID + " INTEGER PRIMARY KEY," +
			    		CHNDatabase.COL_OTHER_CATEGORY + TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_OTHER_NUMBER + TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_OTHER_PERIOD + TEXT_TYPE + COMMA_SEP+
			    		CHNDatabase.COL_OTHER_MONTH + TEXT_TYPE + COMMA_SEP+
			    		CHNDatabase.COL_SYNC_STATUS+ TEXT_TYPE+
			    " )";
		
//		table create statement for login activity table
		public static final String LOGIN_ACTIVITY_CREATE_TABLE =
			    "CREATE TABLE " + CHNDatabase.LOGIN_ACTIVITY_TABLE_NAME + " (" +
			    		CHNDatabase._ID + " INTEGER PRIMARY KEY," +
			    		CHNDatabase.COL_DATE_LOGGED_IN + TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_TIME_LOGGED_IN + TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_USERNAME_LOGIN_ACTIVITY+ TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_PASSWORD_LOGIN_ACTIVITY+ TEXT_TYPE + COMMA_SEP +
			    		CHNDatabase.COL_SYNC_STATUS+ TEXT_TYPE+
			    " )";
}
	