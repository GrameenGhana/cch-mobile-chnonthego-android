package org.grameenfoundation.poc;

import java.io.File;
import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.cch.model.POCSections;
import org.grameenfoundation.cch.tasks.DownloadCWCReferenceTask;
import org.json.JSONException;
import org.json.JSONObject;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CWCReferencesActivity extends BaseActivity implements OnItemClickListener{

//	private Context mContext;
	private ListView listView_encounter;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	private JSONObject json;
	private ArrayList<POCSections> list;
	private String first_file;
	private String[] items;
	private ListAdapter adapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_encounter);
	    mContext=CWCReferencesActivity.this;
	    dbh=new DbHelper(mContext);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("CWC References");
	    json=new JSONObject();
	    try {
			json.put("page", "CWC References");
			json.put("section", MobileLearning.CCH_DIAGNOSTIC);
			json.put("ver", dbh.getVersionNumber(mContext));
			json.put("battery", dbh.getBatteryStatus(mContext));
			json.put("device", dbh.getDeviceName());
			json.put("imei", dbh.getDeviceImei(mContext));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	    listView_encounter=(ListView) findViewById(R.id.listView_encounter);
	    listView_encounter.setOnItemClickListener(this);
	  items=new String[]{"Pre-Referral Treatment","Treatment of the Sick Child"};
	    adapter=new ListAdapter(mContext, items);
	    listView_encounter.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position,
			long id) {
		final String[] selected_items=adapter.getItem(position);
		File folder=new File(MobileLearning.POC_ROOT+File.separator+"references"+File.separator+selected_items[position]+".pdf");
		if(folder.exists()){
			Uri uri  = Uri.fromFile(folder);
	    	 Intent intentUrl = new Intent(Intent.ACTION_VIEW);
	    	 intentUrl.setDataAndType(uri, "application/pdf");
	    	 intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	 	    try {
	 	    		startActivity(intentUrl);
	 	    	}
	 	    catch (ActivityNotFoundException e) 
	 	    {
	 	    	e.printStackTrace();
	 	    	Crouton.makeText(CWCReferencesActivity.this, "No application available to view PDF", Style.ALERT).show();
	 	    }
			
		}else{
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					mContext);
				alertDialogBuilder.setTitle("Download Verification");
				alertDialogBuilder
					.setMessage("Content has not been downloaded for this section. \n Would you like to download this content?")
					.setCancelable(false)
					.setIcon(R.drawable.ic_error)
					.setPositiveButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					  })
					.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id_negative) {
							if(dbh.isOnline()){
								DownloadCWCReferenceTask task=new DownloadCWCReferenceTask(mContext);
								task.execute(selected_items[position]);
							}else{
								Crouton.makeText(CWCReferencesActivity.this, "Check your internet connection", Style.ALERT).show();
							}
						}
					});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
		}
		
	}
	
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] items;
		 public LayoutInflater minflater;
		public ListAdapter(Context mContext,String[] items){
			this.mContext=mContext;
			this.items=items;
			 minflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return items.length;
		}

		@Override
		public String[] getItem(int position) {
			String[] item={"pre_referral_treatments","treatment_of_the_sick_child"};
			
			
			return item;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if( convertView == null ){
			      
				  convertView = minflater.inflate(R.layout.other_listview_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_otherCategory);
			 ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
			 String[] item={"pre_referral_treatments","treatment_of_the_sick_child"};
			 File file=new File(MobileLearning.POC_ROOT+File.separator+"references"+File.separator+item[position]+".pdf");
			 if(file.exists()){
				 image.setImageDrawable(getResources().getDrawable(R.drawable.ic_special_bullet));
			 }else{
				 image.setImageDrawable(getResources().getDrawable(R.drawable.ic_special_bullet_downloaded));
			 }
			 text.setText(items[position]);
			 
			    return convertView;
		}
		
	}
	public void onBackPressed()
	{
		end_time=System.currentTimeMillis();
		//dbh.insertCCHLog("Point of Care", "ANC Diagnostic Tool", start_time.toString(), end_time.toString());
		dbh.insertCCHLog("Point of Care", json.toString(), start_time.toString(), end_time.toString());
		finish();
	}

}
