package org.grameenfoundation.cch.tasks;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class POCContentLoaderTask extends AsyncTask<String, String, String> {
	private Context ctx;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	private DbHelper dbh;
	 private ProgressDialog mProgressDialog;
	private JSONArray contentArray;
	 
	
	public POCContentLoaderTask(Context ctx) {
		this.ctx = ctx;
		this.dbh = new DbHelper(ctx);
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mProgressDialog = new ProgressDialog(ctx);
		mProgressDialog.setMessage("Downloading content..");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}
	 protected String doInBackground(String ... urls) {
         String response = "";
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
		protected void onProgressUpdate(String... progress) {
			 Log.d("ANDRO_ASYNC",progress[0]);
			 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			//dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
			/*dbh.insertPocSection("Child Emergencies/ Danger Signs", "child_emergencies", MobileLearning.POC_ROOT+"child_emergencies", "CWC Diagnostic","");
			dbh.insertPocSection("Registration and Vital Statistics", "registration_statistics", MobileLearning.POC_ROOT+"registration_statistics", "CWC Diagnostic","");
			dbh.insertPocSection("Assesment of the Child: Main Symptoms:Cough or Difficulty Breathing", "child_assessment_cough_difficult_breathing", MobileLearning.POC_ROOT+"child_assessment_cough_difficult_breathing", "CWC Diagnostic","");
			dbh.insertPocSection("Immunization and Vitamin A Status", "immunization", MobileLearning.POC_ROOT+"immunization", "CWC Diagnostic","");
			dbh.insertPocSection("Feeding Assessment & Counselling", "feeding_assessment", MobileLearning.POC_ROOT+"feeding_assessment", "CWC Diagnostic","");
			dbh.insertPocSection("Assess Other Health Problems", "Assess Other Health Problems", MobileLearning.POC_ROOT+"Assess Other Health Problems", "CWC Diagnostic","");
			dbh.insertPocSection("HIV Infection", "hiv_infection", MobileLearning.POC_ROOT+"hiv_infection", "CWC Diagnostic","");
			dbh.insertPocSection("Pre-referral Treatment", "prereferral_treatment", MobileLearning.POC_ROOT+"prereferral_treatment", "CWC Diagnostic","");
			dbh.insertPocSection("Treatment of the Sick Child", "child_treatment", MobileLearning.POC_ROOT+"child_treatment", "CWC Diagnostic","");
			dbh.insertPocSection("Assessment of Child: Main Symptoms: Diarrhoea", "child_assessment_diarrhoea", MobileLearning.POC_ROOT+"child_assessment_diarrhoea", "CWC Diagnostic","");
			dbh.insertPocSection("Assesment of the Child: Main Symptoms: Fever", "child_assessment_diarrhoea_fever", MobileLearning.POC_ROOT+"child_assessment_diarrhoea_fever", "CWC Diagnostic","");
			dbh.insertPocSection("Assesment of the Child: Main Symptoms: Ear Problem", "ear_problem", MobileLearning.POC_ROOT+"ear_problem", "CWC Diagnostic","");*/
			System.out.println(unused);
			mProgressDialog.setMessage("Content successfully downloaded!");
			dbh.deletePOC();
			JSONObject jObj;
			try {
				jObj = new JSONObject();
				contentArray =new JSONArray(unused);
				for(int i=0;i<contentArray.length();i++){
					jObj=contentArray.getJSONObject(i);
					dbh.insertPocSection(jObj.getString("section_name"), 
										jObj.getString("section_shortname"), 
										MobileLearning.POC_ROOT+jObj.getString("section_shortname"),
										jObj.getString("sub_section"),
										jObj.getString("file_url"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			mProgressDialog.dismiss();
		}
	/*
	@Override
    protected void onPostExecute(String p) {
		dbh.insertPocSection("Child Emergencies/ Danger Signs", "child_emergencies", MobileLearning.POC_ROOT+"child_emergencies", "CWC Diagnostic");
		dbh.insertPocSection("Registration and Vital Statistics", "registration_statistics", MobileLearning.POC_ROOT+"registration_statistics", "CWC Diagnostic");
		dbh.insertPocSection("Assesment of the Child: Main Symptoms:Cough or Difficulty Breathing", "child_assessment_cough_difficult_breathing", MobileLearning.POC_ROOT+"child_assessment_cough_difficult_breathing", "CWC Diagnostic");
		dbh.insertPocSection("Immunization and Vitamin A Status", "immunization", MobileLearning.POC_ROOT+"immunization", "CWC Diagnostic");
		dbh.insertPocSection("Feeding Assessment & Counselling", "feeding_assessment", MobileLearning.POC_ROOT+"feeding_assessment", "CWC Diagnostic");
		dbh.insertPocSection("Assess Other Health Problems", "Assess Other Health Problems", MobileLearning.POC_ROOT+"Assess Other Health Problems", "CWC Diagnostic");
		dbh.insertPocSection("HIV Infection", "hiv_infection", MobileLearning.POC_ROOT+"hiv_infection", "CWC Diagnostic");
		dbh.insertPocSection("Pre-referral Treatment", "prereferral_treatment", MobileLearning.POC_ROOT+"prereferral_treatment", "CWC Diagnostic");
		dbh.insertPocSection("Treatment of the Sick Child", "child_treatment", MobileLearning.POC_ROOT+"child_treatment", "CWC Diagnostic");
		dbh.insertPocSection("Assessment of Child: Main Symptoms: Diarrhoea", "child_assessment_diarrhoea", MobileLearning.POC_ROOT+"child_assessment_diarrhoea", "CWC Diagnostic");
		dbh.insertPocSection("Assesment of the Child: Main Symptoms: Fever", "child_assessment_diarrhoea_fever", MobileLearning.POC_ROOT+"child_assessment_diarrhoea_fever", "CWC Diagnostic");
	}
*/

		
}
