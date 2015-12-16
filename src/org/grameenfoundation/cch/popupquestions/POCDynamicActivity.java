package org.grameenfoundation.cch.popupquestions;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.utils.UIUtils;
import org.grameenfoundation.poc.BaseActivity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout.LayoutParams;

public class POCDynamicActivity extends BaseActivity {

	public String tag;
	public XmlGuiForm theForm;
	private String shortname;
	private String first_file;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Bundle extras = getIntent().getExtras(); 
	    if (extras != null) {
	          shortname= extras.getString("shortname");
	          first_file=extras.getString("link");
	        }
	    new GetData().execute(first_file);
	    
	    
	}
	
	@SuppressLint("NewApi")
	private boolean DisplayForm()
	{
		
		try
		{
			ScrollView sv = new ScrollView(this);
			if(theForm.getFormType().equals("Take Action Page")&&theForm.getFormColor().equals("Red")){
				Drawable img = this.getResources().getDrawable( R.drawable.stroke_bg );
				sv.setBackgroundDrawable(img);
			}else if(theForm.getFormType().equals("Take Action Page")&&theForm.getFormColor().equals("Amber")){
				Drawable img = this.getResources().getDrawable( R.drawable.stroke_bg_curry);
				sv.setBackgroundDrawable(img);
			}else if(theForm.getFormType().equals("Take Action Page")&&theForm.getFormColor().equals("Green")){
				Drawable img = this.getResources().getDrawable( R.drawable.stroke_bg_green);
				sv.setBackgroundDrawable(img);
			}
			
			if(theForm.getFormType().equals("Question Page")){
				 final LinearLayout ll = new LinearLayout(this);
			        sv.addView(ll);
			        ll.setOrientation(android.widget.LinearLayout.VERTICAL);
			        ll.setPadding(20, 20, 20, 20);
			      //  ll.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			        ll.setDividerDrawable(this.getResources().getDrawable(R.drawable.divider));
			        int i;
			        for (i=0;i<theForm.fields.size();i++) {
			        	if (theForm.fields.elementAt(i).getType().equals("question")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiTextViewQuestion(this,theForm.fields.elementAt(i).getName(),"");
			        		ll.addView((View) theForm.fields.elementAt(i).obj);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("answers")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		String[] values={theForm.fields.elementAt(i).getName()};
			        		theForm.fields.elementAt(i).obj = new XmlGuiListView(this,values,"",theForm.fields.elementAt(i).getLink());
			        		ll.addView((View) theForm.fields.elementAt(i).obj);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("button")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiButton(this,theForm.fields.elementAt(i).getName(),theForm.fields.elementAt(i).getColorCode(),theForm.fields.elementAt(i).getLink());
			        		//((XmlGuiEditBox)theForm.fields.elementAt(i).obj).makeNumeric();
			        		ll.addView((View) theForm.fields.elementAt(i).obj);
			        	}
			        }
			        
			}else if(theForm.getFormType().equals("Take Action Page")){
				 final LinearLayout ll = new LinearLayout(this);
			        sv.addView(ll);
			        ll.setOrientation(android.widget.LinearLayout.VERTICAL);
			        ll.setPadding(20, 20, 20, 20);
			      //  ll.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			        //ll.setDividerDrawable(this.getResources().getDrawable(R.drawable.divider));
			        int i;
			        for (i=0;i<theForm.fields.size();i++) {
			        	if (theForm.fields.elementAt(i).getType().equals("first_section_head")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiTakeFirstActionHeader(this,theForm.fields.elementAt(i).getName(),theForm.getFormColor());
			        	
			        		ll.addView((View) theForm.fields.elementAt(i).obj);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("definition")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiDefinition(this,theForm.fields.elementAt(i).getName());
			        		ll.addView((View) theForm.fields.elementAt(i).obj);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("first_actions")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiTextViewChoice(this,theForm.fields.elementAt(i).getName(),theForm.fields.elementAt(i).getLink());
			        		 LinearLayout ll2 = new LinearLayout(this);
			        		 if(theForm.getFormColor().equals("Red")){
			        			 ll2.setBackgroundColor(getResources().getColor(R.color.TakeAction));
			        		 }else if(theForm.getFormColor().equals("Amber")){
			        			 ll2.setBackgroundColor(getResources().getColor(R.color.TakeActionCurry));
			        		 }else if(theForm.getFormColor().equals("Green")){
			        			// ll2.setBackgroundColor(getResources().getColor(R.color.TakeActionGreen));
			        		 }
			        		 if (theForm.fields.elementAt(i).getType().equals("first_actions_sub")&&!theForm.fields.elementAt(i).getName().equals("")) {
					        		theForm.fields.elementAt(i).obj = new XmlGuiSubSection(this,theForm.fields.elementAt(i).getName(),theForm.fields.elementAt(i).getLink());
					        		 LinearLayout ll4 = new LinearLayout(this);
					        		 ll4.setBackground(getResources().getDrawable(R.drawable.custom_border2));
					        		 ll4.setOrientation(android.widget.LinearLayout.VERTICAL);
					        		 ll4.setDividerDrawable(this.getResources().getDrawable(R.drawable.divider));
					        		 ll4.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
					        		 ll4.addView((View) theForm.fields.elementAt(i).obj);
					        		 ll.addView(ll4);
					        	}
			        		 ll2.setOrientation(android.widget.LinearLayout.VERTICAL);
			        		 ll2.setDividerDrawable(this.getResources().getDrawable(R.drawable.divider));
			        		 ll2.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			        		 ll2.addView((View) theForm.fields.elementAt(i).obj);
			        		 ll.addView(ll2);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("transport_section_head")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiTakeTransportActionHeader(this,theForm.fields.elementAt(i).getName(),"");
			        		ll.addView((View) theForm.fields.elementAt(i).obj);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("transport_actions")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiTextViewChoice(this,theForm.fields.elementAt(i).getName(),theForm.fields.elementAt(i).getLink());
			        		 LinearLayout ll3 = new LinearLayout(this);
			        		 ll3.setPadding(0, 20, 0, 40);
			        		 ll3.setOrientation(android.widget.LinearLayout.VERTICAL);
			        		 ll3.setDividerDrawable(this.getResources().getDrawable(R.drawable.divider));
			        		ll3.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			        		ll3.addView((View) theForm.fields.elementAt(i).obj);
			        		 ll.addView(ll3);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("second_actions")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiTextViewChoice(this,theForm.fields.elementAt(i).getName(),theForm.fields.elementAt(i).getLink());
			        		 LinearLayout ll4 = new LinearLayout(this);
			        		 ll4.setPadding(0, 20, 0, 0);
			        		 if(theForm.getFormColor().equals("Red")){
			        			 ll4.setBackgroundColor(getResources().getColor(R.color.TakeAction));
			        		 }else if(theForm.getFormColor().equals("Amber")){
			        			 ll4.setBackgroundColor(getResources().getColor(R.color.TakeActionCurry));
			        		 }else if(theForm.getFormColor().equals("Green")){
			        			// ll4.setBackgroundColor(getResources().getColor(R.color.TakeActionGreen));
			        		 }
			        		 
			        		 ll4.setOrientation(android.widget.LinearLayout.VERTICAL);
			        		 ll4.setDividerDrawable(this.getResources().getDrawable(R.drawable.divider));
			        		 ll4.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			        		 ll4.addView((View) theForm.fields.elementAt(i).obj);
			        		 ll.addView(ll4);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("second_actions_sub")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiSubSection(this,theForm.fields.elementAt(i).getName(),theForm.fields.elementAt(i).getLink());
			        		 LinearLayout ll2 = new LinearLayout(this);
			        		 ll2.setBackground(getResources().getDrawable(R.drawable.custom_border2));
			        		 ll2.setOrientation(android.widget.LinearLayout.VERTICAL);
			        		 ll2.setDividerDrawable(this.getResources().getDrawable(R.drawable.divider));
			        		 ll2.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			        		 ll2.addView((View) theForm.fields.elementAt(i).obj);
			        		 ll.addView(ll2);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("transport_actions_sub")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiSubSection(this,theForm.fields.elementAt(i).getName(),theForm.fields.elementAt(i).getLink());
			        		 LinearLayout ll2 = new LinearLayout(this);
			        		 ll2.setBackground(getResources().getDrawable(R.drawable.custom_border2));
			        		 ll2.setOrientation(android.widget.LinearLayout.VERTICAL);
			        		 ll2.setDividerDrawable(this.getResources().getDrawable(R.drawable.divider));
			        		 ll2.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			        		 ll2.addView((View) theForm.fields.elementAt(i).obj);
			        		 ll.addView(ll2);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("first_actions_sub")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiSubSection(this,theForm.fields.elementAt(i).getName(),theForm.fields.elementAt(i).getLink());
			        		 LinearLayout ll2 = new LinearLayout(this);
			        		 ll2.setOrientation(android.widget.LinearLayout.VERTICAL);
			        		 ll2.setDividerDrawable(this.getResources().getDrawable(R.drawable.divider));
			        		 ll2.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			        		 ll2.addView((View) theForm.fields.elementAt(i).obj);
			        		 ll.addView(ll2);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("image")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiImage(this,theForm.fields.elementAt(i).getName(),"");
			        		 LinearLayout ll5 = new LinearLayout(this);
			        		 ll5.setPadding(0, 20, 0, 0);
			        		 ll5.setOrientation(android.widget.LinearLayout.VERTICAL);
			        		 ll5.addView((View) theForm.fields.elementAt(i).obj);
			        		 ll.addView(ll5);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("button")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiButton(this,theForm.fields.elementAt(i).getName(),theForm.fields.elementAt(i).getColorCode(),theForm.fields.elementAt(i).getLink());
			        		ll.addView((View) theForm.fields.elementAt(i).obj);
			        	}
			        	
			        }
			}else if(theForm.getFormType().equals("Info Page")){
				 final LinearLayout ll = new LinearLayout(this);
			        sv.addView(ll);
			        ll.setOrientation(android.widget.LinearLayout.VERTICAL);
			        ll.setPadding(20, 20, 20, 20);
			        int i;
			        for (i=0;i<theForm.fields.size();i++) {
			        	if (theForm.fields.elementAt(i).getType().equals("header")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiSectionHeader(this,theForm.fields.elementAt(i).getName(),theForm.getFormColor());
			        		ll.addView((View) theForm.fields.elementAt(i).obj);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("section_items")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiTextViewChoice(this,theForm.fields.elementAt(i).getName(),theForm.fields.elementAt(i).getLink());
			        		 LinearLayout ll2 = new LinearLayout(this);
			        		 ll2.setBackground(getResources().getDrawable(R.drawable.custom_border2));
			        		 ll2.setOrientation(android.widget.LinearLayout.VERTICAL);
			        		 ll2.setDividerDrawable(this.getResources().getDrawable(R.drawable.divider));
			        		 ll2.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			        		 ll2.addView((View) theForm.fields.elementAt(i).obj);
			        		 ll.addView(ll2);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("section_items_sub")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiSubSection(this,theForm.fields.elementAt(i).getName(),theForm.fields.elementAt(i).getLink());
			        		 LinearLayout ll2 = new LinearLayout(this);
			        		 ll2.setBackground(getResources().getDrawable(R.drawable.custom_border2));
			        		 ll2.setOrientation(android.widget.LinearLayout.VERTICAL);
			        		 ll2.setDividerDrawable(this.getResources().getDrawable(R.drawable.divider));
			        		 ll2.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
			        		 ll2.addView((View) theForm.fields.elementAt(i).obj);
			        		 ll.addView(ll2);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("button")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiButton(this,theForm.fields.elementAt(i).getName(),theForm.fields.elementAt(i).getColorCode(),theForm.fields.elementAt(i).getLink());
			        		ll.addView((View) theForm.fields.elementAt(i).obj);
			        	}
			        	if (theForm.fields.elementAt(i).getType().equals("image")&&!theForm.fields.elementAt(i).getName().equals("")) {
			        		theForm.fields.elementAt(i).obj = new XmlGuiImage(this,theForm.fields.elementAt(i).getName(),"");
			        		 LinearLayout ll5 = new LinearLayout(this);
			        		 ll5.setPadding(0, 20, 0, 0);
			        		 ll5.setOrientation(android.widget.LinearLayout.VERTICAL);
			        		 ll5.addView((View) theForm.fields.elementAt(i).obj);
			        		 ll.addView(ll5);
			        	}
			        	
			        }
			}
	        // walk through our form elements and dynamically create them, leveraging our mini library of tools.
	        setContentView(sv);
	        setTitle(theForm.getFormTitle());
	        getActionBar().setSubtitle(theForm.getFormSubTitle());
	        
	        return true;

		} catch (Exception e) {
			Log.e(tag,"Error Displaying Form");
			e.printStackTrace();
			return false;
		}
	}
	private class GetData extends AsyncTask<String, Void, XmlGuiForm> {
		 private ProgressDialog dialog = 
				   new ProgressDialog(POCDynamicActivity.this);
		 protected void onPreExecute() {
			   dialog.setMax(100);
			   dialog.setIndeterminate(false);
			   dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			   dialog.setMessage("Retrieving page... Please wait...");
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
				//InputStream is = assetManager.open("surveys/xmlgui"+Integer.parseInt(params[0])+ ".xml");
				//InputStream is = assetManager.open("surveys/xmlgui"+Integer.parseInt(params[0])+ ".xml");
				File xmlFile = new File(MobileLearning.POC_ROOT+params[0]+".xml");
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = factory.newDocumentBuilder();
				Document dom = db.parse(xmlFile);
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
					theForm.setFormType(map.getNamedItem("type_of_page").getNodeValue());
				theForm.setFormName(map.getNamedItem("name").getNodeValue());
				theForm.setFormColor(map.getNamedItem("color_code").getNodeValue());
				theForm.setFormTitle(map.getNamedItem("page_title").getNodeValue());
				theForm.setFormSubTitle(map.getNamedItem("page_subtitle").getNodeValue());
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
					tempField.setLink(attr.getNamedItem("link").getNodeValue());
					tempField.setGroup(attr.getNamedItem("group").getNodeValue());
					tempField.setType(attr.getNamedItem("type").getNodeValue());
					tempField.setColorCode(attr.getNamedItem("color_code").getNodeValue());
					//tempField.setOptions(attr.getNamedItem("options").getNodeValue());
				
					theForm.getFields().add(tempField);
				}
				
				//Log.i(tag,theForm..toString());
				//return true;
			} catch (Exception e) {
				Log.e(tag,"Error occurred in ProcessForm:" + e.getMessage());
				//UIUtils.showAlert(POCDynamicActivity.this, "Alert!", "Content for this page has not been downloaded.\n Ensure content is downloaded and proceed");
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
}
