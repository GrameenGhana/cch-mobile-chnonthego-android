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

import java.io.File;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.task.SubmitQuizTask;
import org.digitalcampus.oppia.task.SubmitTrackerMultipleTask;
import org.grameenfoundation.cch.tasks.StayingWellNotifyTask;
import org.grameenfoundation.cch.tasks.SurveyNotifyTask;
import org.grameenfoundation.cch.tasks.TargetSettingNotifyTask;
import org.grameenfoundation.cch.tasks.UpdateCCHLogTask;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

import org.acra.*;
import org.acra.annotation.*;
import org.acra.sender.HttpSender;
@ReportsCrashes(
	    formUri = "https://florencejones.cloudant.com/acra-chnonthego/_design/acra-storage/_update/report",
	    reportType = HttpSender.Type.JSON,
	    httpMethod = HttpSender.Method.POST,
	    formUriBasicAuthLogin = "despecindesproughtedenou",
	    formUriBasicAuthPassword = "Mhhri81NL6EoTubaDkXGac0J",
	    formKey = "", // This is required for backward compatibility but not used
	    customReportContent = {
	            ReportField.APP_VERSION_CODE,
	            ReportField.APP_VERSION_NAME,
	            ReportField.ANDROID_VERSION,
	            ReportField.PACKAGE_NAME,
	            ReportField.REPORT_ID,
	            ReportField.BUILD,
	            ReportField.STACK_TRACE
	    },
	    mode = ReportingInteractionMode.TOAST,
	    resToastText = R.string.crash_toast_text
	)
public class MobileLearning extends Application {

	public static final String TAG = MobileLearning.class.getSimpleName();

	// local storage vars
	public static final String OPPIAMOBILE_ROOT = Environment
			.getExternalStorageDirectory() + "/digitalcampus/";
	public static final String REFERENCES_ROOT = Environment
			.getExternalStorageDirectory() + "/references/";
	public static final String COURSES_PATH = OPPIAMOBILE_ROOT + "modules/";
	public static final String MEDIA_PATH = OPPIAMOBILE_ROOT + "media/";
	public static final String DOWNLOAD_PATH = OPPIAMOBILE_ROOT + "download/";
	public static final String COURSE_XML = "module.xml";
	public static final String COURSE_SCHEDULE_XML = "schedule.xml";
	public static final String COURSE_TRACKER_XML = "tracker.xml";
	public static final String PRE_INSTALL_COURSES_DIR = "www/preload/courses"; // don't include leading or trailing slash
	public static final String PRE_INSTALL_MEDIA_DIR = "www/preload/media"; // don't include leading or trailing slash

	
	// server path vars - new version
	public static final String OPPIAMOBILE_API = "api/v1/";
	public static final String LOGIN_PATH = OPPIAMOBILE_API + "user/";
	public static final String REGISTER_PATH = OPPIAMOBILE_API + "register/";
	public static final String QUIZ_SUBMIT_PATH = OPPIAMOBILE_API + "quizattempt/";
	public static final String SERVER_COURSES_PATH = OPPIAMOBILE_API + "course/";
	public static final String SERVER_TAG_PATH = OPPIAMOBILE_API + "tag/";
	public static final String TRACKER_PATH = OPPIAMOBILE_API + "tracker/";
	public static final String SERVER_POINTS_PATH = OPPIAMOBILE_API + "points/";
	public static final String SERVER_COURSES_NAME = "courses";
	public static final String CCH_QUOTES_SUBMIT_PATH = "api/v1/quotes";
	public static final String CCH_USER_DETAILS_PATH = "cch/yabr3/api/v1/details/";
	public static final String CCH_USER_ACHIEVEMENTS_PATH = "cch/yabr3/api/v1/achievements/";
	public static final String CCH_COURSE_DETAILS_PATH = "cch/yabr3/courses";
	public static final String CCH_TRACKER_SUBMIT_PATH = "api/v1/tracker";
	public static final String CCH_COURSE_ACHIEVEMENT_PATH = "cmd=2&username=";
	public static final String CCH_REFERENCE_DOWNLOAD_PATH = "references/";
	public static final String CCH_DIAGNOSTIC = "Diagnostic Tool";
	public static final String CCH_REFERENCES = "References";
	public static final String CCH_COUNSELLING = "Counselling";
	
	public static final String CCH_TARGET_TYPE_EVENT = "Event";
	public static final String CCH_TARGET_TYPE_COVERAGE = "Coverage";
	public static final String CCH_TARGET_TYPE_OTHER = "Other";
	public static final String CCH_TARGET_TYPE_LEARNING = "Learning";
	
	public static final String CCH_TARGET_STATUS_NEW = "new_record";
	public static final String CCH_TARGET_STATUS_UPDATED = "updated";
	
	public static final String CCH_TARGET_PERSONAL = "personal";
	public static final String CCH_TARGET_NOT_PERSONAL= "not_personal";

	// general other settings
	public static final String BUGSENSE_API_KEY = "f3a6ec3a";
	public static final int PASSWORD_MIN_LENGTH = 6;
	public static final int PAGE_READ_TIME = 3;
	public static final int RESOURCE_READ_TIME = 3;
	public static final String USER_AGENT = "OppiaMobile Android: ";
	public static final int QUIZ_PASS_THRESHOLD = 100;
	public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");
	public static final DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern("HH:mm:ss");
	public static final int MAX_TRACKER_SUBMIT = 10;
	public static final boolean DEVELOPER_MODE = false;
	
	// only used in case a course doesn't have any lang specified
	public static final String DEFAULT_LANG = "en";
	
	// for tracking if SubmitTrackerMultipleTask is already running
	public SubmitTrackerMultipleTask omSubmitTrackerMultipleTask = null;
	
	// for tracking if SubmitQuizTask is already running
	public SubmitQuizTask omSubmitQuizTask = null;
	
	// for tracking if UpdateCCHLogTask is already running
	public UpdateCCHLogTask omUpdateCCHLogTask = null;
	
	// for tracking if notifier is already running
	public StayingWellNotifyTask omStayingWellNotifyTask = null;
	
	public TargetSettingNotifyTask omTargetSettingNotifyTask = null;
	public SurveyNotifyTask omSurveyNotifyTask = null;
	
	public static boolean createDirs() {
		String cardstatus = Environment.getExternalStorageState();
		if (cardstatus.equals(Environment.MEDIA_REMOVED)
				|| cardstatus.equals(Environment.MEDIA_UNMOUNTABLE)
				|| cardstatus.equals(Environment.MEDIA_UNMOUNTED)
				|| cardstatus.equals(Environment.MEDIA_MOUNTED_READ_ONLY)
				|| cardstatus.equals(Environment.MEDIA_SHARED)) {
			return false;
		}

		String[] dirs = { OPPIAMOBILE_ROOT, COURSES_PATH, MEDIA_PATH, DOWNLOAD_PATH };

		for (String dirName : dirs) {
			File dir = new File(dirName);
			if (!dir.exists()) {
				if (!dir.mkdirs()) {
					return false;
				}
			} else {
				if (!dir.isDirectory()) {
					return false;
				}
			}
		}
		return true;
	}
	 @Override
     public void onCreate() {
         super.onCreate();

         // The following line triggers the initialization of ACRA
         ACRA.init(this);
         
     }
	public static boolean isLoggedIn(Activity act) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(act.getBaseContext());
		String username = prefs.getString(act.getString(R.string.prefs_username), "");
		String apiKey = prefs.getString(act.getString(R.string.prefs_api_key), "");
		if (username.trim().equals("") || apiKey.trim().equals("")) {
			return false;
		} else {
			return true;
		}
	}

}
