package org.grameenfoundation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CHNDatabaseHelper extends SQLiteOpenHelper  {

	 public static final int DATABASE_VERSION = 1;
	 public static final String DATABASE_NAME = "CHN.db";  
	 

	 public CHNDatabaseHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	      
	    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CHNDataClass.USER_TABLE_CREATE_TABLE);
		db.execSQL(CHNDataClass.EVENT_SET_CREATE_TABLE);
		db.execSQL(CHNDataClass.EVENT_CATEGORY_CREATE_TABLE);
		db.execSQL(CHNDataClass.COVERAGE_CREATE_TABLE);
		db.execSQL(CHNDataClass.COVERAGE_SET_CREATE_TABLE);
		db.execSQL(CHNDataClass.LEARNING_CREATE_TABLE);
		db.execSQL(CHNDataClass.OTHER_CREATE_TABLE);
		db.execSQL(CHNDataClass.LOGIN_ACTIVITY_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		
		onCreate(db);
		
	}
	
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       onUpgrade(db, oldVersion, newVersion);
   }
}
