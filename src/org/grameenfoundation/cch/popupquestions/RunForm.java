/*
 * XmlGui application.
 * Written by Frank Ableson for IBM Developerworks
 * June 2010
 * Use the code as you wish -- no warranty of fitness, etc, etc.
 */
package org.grameenfoundation.cch.popupquestions;

import android.app.AlertDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.lang.Thread;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.model.User;
import org.grameenfoundation.cch.activity.ReferencesDownloadActivity;
import org.grameenfoundation.cch.model.Survey;
import org.grameenfoundation.poc.BaseActivity;
import org.joda.time.LocalDate;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;


public class RunForm extends BaseActivity {
    /** Called when the activity is first created. */
	String tag = RunForm.class.getName();
	XmlGuiForm theForm;
	ProgressDialog progressDialog;
	Handler progressHandler;
	DbHelper db;
	private ArrayList<User> user_role;
	private SharedPreferences prefs;
	private String name;
	private Long startTime;
	private Long end_time;
	private LocalDate date;
	private String dateExtra;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String formNumber = "";
        db=new DbHelper(this);
        user_role=new ArrayList<User>();
		 prefs = PreferenceManager.getDefaultSharedPreferences(this);
	        name=prefs.getString("first_name", "name");
    	user_role=db.getUserFirstName(name);
    	date=new LocalDate();
    	 startTime = System.currentTimeMillis();
    	  Bundle extras = getIntent().getExtras(); 
    	  if (extras != null) {
              dateExtra= extras.getString("date");
            }
		// only notify if at certain hours and if there are tasks to do...
		if(user_role.get(0).getUserrole().equals("chn")){
			
			formNumber = "1";
		}else if(user_role.get(0).getUserrole().equals("Supervisor")){
			formNumber = "2";
		}
			/*
		}
        Intent startingIntent = getIntent();
        if(startingIntent == null) {
        	Log.e(tag,"No Intent?  We're not supposed to be here...");
        	finish();
        	return;
        }*/
    	//formNumber = startingIntent.getStringExtra("formNumber");
    	Log.i(tag,"Running Form [" + formNumber + "]");
    	/*
    	if (GetFormData(formNumber)) {
    		DisplayForm();
    	}*/
    	try{
    		
    			new GetData().execute(String.valueOf(formNumber));
    	}catch(Exception e){
    		e.printStackTrace();
    		Log.e(tag,"Couldn't parse the Form.");
    		AlertDialog.Builder bd = new AlertDialog.Builder(this);
    		AlertDialog ad = bd.create();
    		ad.setTitle("Error");
    		ad.setMessage("Could not parse the Form data");
    		ad.show();
    	}
    	/*
    	else
    	{
    		Log.e(tag,"Couldn't parse the Form.");
    		AlertDialog.Builder bd = new AlertDialog.Builder(this);
    		AlertDialog ad = bd.create();
    		ad.setTitle("Error");
    		ad.setMessage("Could not parse the Form data");
    		ad.show();
		
    	}*/
    }
	
	
	private boolean DisplayForm()
	{
		
		try
		{
			ScrollView sv = new ScrollView(this);
			
	        final LinearLayout ll = new LinearLayout(this);
	        sv.addView(ll);
	        ll.setOrientation(android.widget.LinearLayout.VERTICAL);
	        ll.setPadding(20, 20, 20, 20);
	        ll.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
	        ll.setDividerDrawable(this.getResources().getDrawable(R.drawable.divider));
	        // walk thru our form elements and dynamically create them, leveraging our mini library of tools.
	        int i;
	     //  System.out.println(theForm.fields.toString());
	        for (i=0;i<theForm.fields.size();i++) {
	        	if (theForm.fields.elementAt(i).getType().equals("text")) {
	        		theForm.fields.elementAt(i).obj = new XmlGuiTextView(this,(theForm.fields.elementAt(i).isRequired() ? "": "") + theForm.fields.elementAt(i).getLabel(),"");
	        		ll.addView((View) theForm.fields.elementAt(i).obj);
	        	}
	        	if (theForm.fields.elementAt(i).getType().equals("Preamble")) {
	        		theForm.fields.elementAt(i).obj = new XmlGuiTextViewPreamble(this,(theForm.fields.elementAt(i).isRequired() ? "": "") + theForm.fields.elementAt(i).getLabel(),"");
	        		ll.addView((View) theForm.fields.elementAt(i).obj);
	        	}
	        	if (theForm.fields.elementAt(i).getType().equals("bullet")) {
	        		theForm.fields.elementAt(i).obj = new XmlGuiTextViewBullet(this,(theForm.fields.elementAt(i).isRequired() ? "" : "") + theForm.fields.elementAt(i).getLabel(),theForm.fields.elementAt(i).getOptions());
	        		//((XmlGuiEditBox)theForm.fields.elementAt(i).obj).makeNumeric();
	        		ll.addView((View) theForm.fields.elementAt(i).obj);
	        	}
	        	if (theForm.fields.elementAt(i).getType().equals("choice")) {
	        		theForm.fields.elementAt(i).obj = new XmlGuiRadioButtonChoice(this,(theForm.fields.elementAt(i).isRequired() ? "" : "") + theForm.fields.elementAt(i).getLabel(),theForm.fields.elementAt(i).getOptions());
	        		ll.addView((View) theForm.fields.elementAt(i).obj);
	        	}
	        }
	        
	        
	        Button btn = new Button(this);
	        btn.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
	        
	        ll.addView(btn);
	        
	        btn.setText("Submit");
	        btn.setOnClickListener(new Button.OnClickListener() {
	        	public void onClick(View v) {
	        		// check if this form is Valid
	        		if (!CheckForm())
	        		{
	        			AlertDialog.Builder bd = new AlertDialog.Builder(ll.getContext());
	            		AlertDialog ad = bd.create();
	            		ad.setTitle("Error");
	            		ad.setMessage("Please enter all required (*) fields");
	            		ad.show();
	            		return;

	        		}
	        		if (theForm.getSubmitTo().equals("loopback")) {
	        			// just display the results to the screen
	        			String formResults = theForm.getFormattedResults();
	        			Log.i(tag,formResults);
	        			AlertDialog.Builder bd = new AlertDialog.Builder(ll.getContext());
	            		AlertDialog ad = bd.create();
	            		ad.setTitle("Results");
	            		ad.setMessage(formResults);
	            		ad.show();
	            		return;
	        			
	        		} else {
	        			if (!SubmitForm()) {
		        			AlertDialog.Builder bd = new AlertDialog.Builder(ll.getContext());
		            		AlertDialog ad = bd.create();
		            		ad.setTitle("Error");
		            		ad.setMessage("Error submitting form");
		            		ad.show();
		            		return;
	        			}
	        		}
	        		
	        	}
	        } );
	        
	        setContentView(sv);
	        setTitle(theForm.getFormName());
	        
	        return true;

		} catch (Exception e) {
			Log.e(tag,"Error Displaying Form");
			e.printStackTrace();
			return false;
		}
	}
	private boolean SubmitForm()
	{
		try {
			boolean ok = true;
            this.progressDialog = ProgressDialog.show(this, theForm.getFormName(), "Saving Form Data", true,false);
            this.progressHandler = new Handler() {

                @Override
                public void handleMessage(Message msg) {
                    // process incoming messages here
                    switch (msg.what) {
                        case 0:
                            // update progress bar
                            progressDialog.setMessage("" + (String) msg.obj);
                            break;
                        case 1:
                        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Close", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
				   
			   });
                            //progressDialog.cancel();
                            finish();
                            break;
                        case 2:
                        	  progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Close", new DialogInterface.OnClickListener(){

                  				@Override
                  				public void onClick(DialogInterface dialog, int which) {
                  					dialog.dismiss();
                  				}
                  				   
                  			   });
                        	  break;
                    }
                    super.handleMessage(msg);
                }

            };

            Thread workthread = new Thread(new TransmitFormData(theForm));

            workthread.start();

			return ok;	
		} catch (Exception e) {
			Log.e(tag,"Error in SubmitForm()::" + e.getMessage());
			e.printStackTrace();
            // tell user we failed....
            Message msg = new Message();
            msg.what = 1;
            this.progressHandler.sendMessage(msg);

			return false;
		}
		
	}
	private boolean CheckForm()
	{
		try {
			int i;
			boolean good = true;
			
			
			for (i=0;i<theForm.fields.size();i++) {
				String fieldValue = (String) theForm.fields.elementAt(i).getData();
				Log.i(tag,theForm.fields.elementAt(i).getName() + " is [" + fieldValue + "]");
				if (theForm.fields.elementAt(i).isRequired()) {
					if (fieldValue == null) {
						good = false;
					} else {
						if (fieldValue.trim().length() == 0) {
							good = false;
						}
					}
						
				}
			}
			return good;
		} catch(Exception e) {
			Log.e(tag,"Error in CheckForm()::" + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	private class GetData extends AsyncTask<String, Void, XmlGuiForm> {
		 private ProgressDialog dialog = 
				   new ProgressDialog(RunForm.this);
		 protected void onPreExecute() {
			   dialog.setMax(100);
			   dialog.setIndeterminate(false);
			   dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			   dialog.setMessage("Retrieving survey... Please wait...");
			   dialog.setCancelable(false);
			  
			   dialog.show();
			  }
		@Override
		protected XmlGuiForm doInBackground(String... params) {
			try {
				Log.i(tag,"ProcessForm");
				//URL url = new URL(getResources().getString(R.string.serverDefaultAddress)+"/testxml/xmlgui" + Integer.parseInt(params[0]) + ".xml");
				AssetManager assetManager = getResources().getAssets();
				//Log.i(tag,url.toString());
				//InputStream is = url.openConnection().getInputStream();
				InputStream is = assetManager.open("surveys/xmlgui"+Integer.parseInt(params[0])+ ".xml");
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = factory.newDocumentBuilder();
				Document dom = db.parse(is);
				Element root = dom.getDocumentElement();
				NodeList forms = root.getElementsByTagName("form");
				if (forms.getLength() < 1) {
					// nothing here??
					Log.e(tag,"No form, let's bail");
					//return false;
				}
				Node form = forms.item(0);
				theForm = new XmlGuiForm();
				
				// process form level
				NamedNodeMap map = form.getAttributes();
				theForm.setFormNumber(map.getNamedItem("id").getNodeValue());
				theForm.setFormName(map.getNamedItem("name").getNodeValue());
				if (map.getNamedItem("submitTo") != null)
					theForm.setSubmitTo(map.getNamedItem("submitTo").getNodeValue());
				else
					theForm.setSubmitTo("loopback");

				// now process the fields
				NodeList fields = root.getElementsByTagName("field");
				for (int i=0;i<fields.getLength();i++) {
					Node fieldNode = fields.item(i);
					NamedNodeMap attr = fieldNode.getAttributes();
					XmlGuiFormField tempField =  new XmlGuiFormField();
					tempField.setName(attr.getNamedItem("name").getNodeValue());
					tempField.setLabel(attr.getNamedItem("label").getNodeValue());
					tempField.setType(attr.getNamedItem("type").getNodeValue());
					if (attr.getNamedItem("required").getNodeValue().equals("Y"))
						tempField.setRequired(true);
					else
						tempField.setRequired(false);
					tempField.setOptions(attr.getNamedItem("options").getNodeValue());
					theForm.getFields().add(tempField);
				}
				
				//Log.i(tag,theForm..toString());
				//return true;
			} catch (Exception e) {
				Log.e(tag,"Error occurred in ProcessForm:" + e.getMessage());
				e.printStackTrace();
			//	return false;
			}
			return theForm;
		}
		@Override
	    protected void onPostExecute(XmlGuiForm result) {
			dialog.dismiss();
			result=theForm;
			DisplayForm();
	    	
	    }
		
	}
	
	
	
	private class TransmitFormData implements Runnable
	{
        XmlGuiForm _form;
        Message msg;
		private long id;
        TransmitFormData(XmlGuiForm form) {
            this._form = form;
        }

        public void run() {

            try { 
            	msg = new Message();
                msg.what = 0;
                msg.obj = ("Submitting...........");
                progressHandler.sendMessage(msg);
                JSONObject json =new JSONObject();
                for (int i=0;i<theForm.fields.size();i++) {
    	        	if (theForm.fields.elementAt(i).getType().equals("choice")) {
    	        		json.put( theForm.fields.elementAt(i).getName(),  theForm.fields.elementAt(i).getResults());
    	        	}
    	        }
               System.out.println(json.toString());
               ArrayList<User> u=new ArrayList<User>();
               ArrayList<Survey> survey=new ArrayList<Survey>();
               
               u=db.getUserFirstName(name);
               if(dateExtra!=""){
            	   survey=db.getSurveyDetails(dateExtra);
            	   id= db.editSurvey(u.get(0).getUsername(), u.get(0).getUserrole(), json.toString(), db.getDate(),"done",Long.parseLong(survey.get(0).getSurveyId()));
            	   end_time=System.currentTimeMillis();
            	   db.insertCCHLog("Survey", json.toString(), startTime.toString(), end_time.toString());
               }else{
            	   survey=db.getSurveyDetails(date.toString("dd-MM-yyyy"));
                   id= db.editSurvey(u.get(0).getUsername(), u.get(0).getUserrole(), json.toString(), db.getDate(),"done",Long.parseLong(survey.get(0).getSurveyId()));
                   end_time=System.currentTimeMillis();
                   db.insertCCHLog("Survey", json.toString(), startTime.toString(), end_time.toString());
               }
                msg = new Message();
                msg.what = 0;
                msg.obj = ("Data Sent");
                progressHandler.sendMessage(msg);


                if (id!=0) {
                    msg = new Message();
                    msg.what = 0;
                    msg.obj = ("Thank you for your feedback!");
                    progressHandler.sendMessage(msg);

                    msg = new Message();
                    msg.what = 1;
                    progressHandler.sendMessage(msg);
                    return;

                }
            } catch (Exception e) {
                Log.d(tag, "Failed to send form data: " + e.getMessage());
                msg = new Message();
                msg.what = 0;
                msg.obj = ("Error Sending Form Data");
                progressHandler.sendMessage(msg);
            }
            msg = new Message();
            msg.what = 2;
            progressHandler.sendMessage(msg);
        }

	}
	public boolean isOnline() {
		 boolean haveConnectedWifi = false;
		    boolean haveConnectedMobile = false;

		    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
} 