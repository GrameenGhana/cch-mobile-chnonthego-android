package org.grameenfoundation.cch.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.task.Payload;
import org.digitalcampus.oppia.utils.HTTPConnectionUtils;
import org.grameenfoundation.cch.model.Quotes;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;

public class UpdateQuotesTask extends AsyncTask<Payload, Object, Payload> {

	public final static String TAG = UpdateQuotesTask.class.getSimpleName();
	private Context ctx;

	public UpdateQuotesTask(Context c) {
		this.ctx = c;
	}

	@Override
	protected Payload doInBackground(Payload... params) {
		Payload payload = params[0];
		for (Object l : payload.getData()) {
			Quotes tl = (Quotes) l;
		
			HTTPConnectionUtils client = new HTTPConnectionUtils(ctx);
			try {

				String url = client.getFullURL(MobileLearning.CCH_QUOTES_SUBMIT_PATH);
				HttpPost httpPost = new HttpPost(url);
				Log.d(TAG, url);
				StringEntity se = new StringEntity(tl.getContent(), "utf8");
				Log.d(TAG, tl.getContent());
				se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				httpPost.setEntity(se);
				httpPost.addHeader(client.getAuthHeader());
				
				// make request
				HttpResponse response = client.execute(httpPost);
				InputStream content = response.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(new InputStreamReader(content), 4096);
				String responseStr = "";
				String s = "";
				
				while ((s = buffer.readLine()) != null) {
					responseStr += s;
				}
				
				Log.d(TAG, responseStr);

				switch (response.getStatusLine().getStatusCode()) {
					case 201: // submitted
						JSONObject jsonResp = new JSONObject(responseStr);
						Log.v("Quoute","Respone :"+responseStr);
						boolean update = jsonResp.getBoolean("update");
						
						if (update)
						{
							DbHelper db = new DbHelper(ctx);
							db.UpdateQuotesTable();
							db.close();
						}
						
						payload.setResult(true);
						break;
						
					default:
						payload.setResult(false);
				}

			} catch (UnsupportedEncodingException e) {
				payload.setResult(false);
				publishProgress(ctx.getString(R.string.error_connection));
			} catch (ClientProtocolException e) {
				payload.setResult(false);
				publishProgress(ctx.getString(R.string.error_connection));
			} catch (IOException e) {
				payload.setResult(false);
				publishProgress(ctx.getString(R.string.error_connection));
			} catch (JSONException e) {
				payload.setResult(false);
				if (!MobileLearning.DEVELOPER_MODE) {
					BugSenseHandler.sendException(e);
				} else {
					e.printStackTrace();
				}
			}

		}

		return payload;
	}

	protected void onProgressUpdate(String... obj) {
		Log.d(TAG, obj[0]);
	}

	@Override
	protected void onPostExecute(Payload p) {
		MobileLearning app = (MobileLearning) ctx.getApplicationContext();
		app.omUpdateQuotesTask = null;
	}

}
