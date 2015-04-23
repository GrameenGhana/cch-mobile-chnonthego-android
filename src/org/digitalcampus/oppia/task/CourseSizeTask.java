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

package org.digitalcampus.oppia.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.listener.InstallCourseListener;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.model.DownloadProgress;
import org.digitalcampus.oppia.utils.HTTPConnectionUtils;
import org.grameenfoundation.cch.activity.EventTargetsDetailActivity;
import org.grameenfoundation.cch.activity.NewEventPlannerActivity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;

public class CourseSizeTask extends AsyncTask<Payload, DownloadProgress, String>{

	public final static String TAG = DownloadCourseTask.class.getSimpleName();
	
	private Context ctx;
	private SharedPreferences prefs;
	private Payload payload;
	private String fileSize;
	private Course dm;
	private DownloadProgress dp;
	private HttpURLConnection c;
	
	public CourseSizeTask(Context ctx) {
		this.ctx = ctx;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
	}
	
	@Override
	protected String doInBackground(Payload... params) {
		payload = params[0];
		
		 dm = (Course) payload.getData().get(0);
		 //dp = new DownloadProgress();
		 
		try { 
			HTTPConnectionUtils client = new HTTPConnectionUtils(ctx);

			String url =  client.createUrlWithCredentials(dm.getDownloadUrl());
			
			Log.d(TAG,"Downloading:" + url);
			
			URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            c.setConnectTimeout(Integer.parseInt(prefs.getString(ctx.getString(R.string.prefs_server_timeout_connection),
							ctx.getString(R.string.prefServerTimeoutConnection))));
            c.setReadTimeout(Integer.parseInt(prefs.getString(ctx.getString(R.string.prefs_server_timeout_response),
							ctx.getString(R.string.prefServerTimeoutResponse))));
			
			int fileLength = c.getContentLength();
			
			long size=fileLength;
    		double kilobytes = 0;
    		double megabyte = 0;
    		kilobytes=(size/1024);
    		 megabyte=(kilobytes/1024);
			String localFileName = dm.getShortname()+"-"+String.format("%.0f",dm.getVersionId())+".zip";
			if(kilobytes>1000){
    			fileSize=String.format("%.2f", megabyte)+"MB";
    		}else{
    			fileSize=String.format("%.2f", kilobytes)+"KB";
    		}
			
		} catch (ClientProtocolException cpe) { 
			if(!MobileLearning.DEVELOPER_MODE){
				BugSenseHandler.sendException(cpe);
			} else {
				cpe.printStackTrace();
			}
			payload.setResult(false);
			payload.setResultResponse(ctx.getString(R.string.error_connection));
		} catch (SocketTimeoutException ste){
			if(!MobileLearning.DEVELOPER_MODE){
				BugSenseHandler.sendException(ste);
			} else {
				ste.printStackTrace();
			}
			payload.setResult(false);
			payload.setResultResponse(ctx.getString(R.string.error_connection));
		} catch (IOException ioe) { 
			if(!MobileLearning.DEVELOPER_MODE){
				BugSenseHandler.sendException(ioe);
			} else {
				ioe.printStackTrace();
			}
			payload.setResult(false);
			payload.setResultResponse(ctx.getString(R.string.error_connection));
		}

		return fileSize;
	}
	

	@Override
	protected void onPostExecute(String results) {
		synchronized (this) {
        }
	}


}
