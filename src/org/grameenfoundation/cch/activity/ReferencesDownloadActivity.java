package org.grameenfoundation.cch.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;
import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.poc.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

import com.bugsense.trace.BugSenseHandler;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class ReferencesDownloadActivity extends BaseActivity{

	private ListView referenceList;
	private ArrayList<String>  fileLongName;
	private String[] files;
	private boolean isComplete;
	private File myDirectory;
	private SharedPreferences prefs;
	private Long start_time;
	private Long end_time;
	private DbHelper dbh;
	private JSONObject data;
	private TextView txt1;
	/** Called when the activity is first created. */
	private ListAdapter myAdapter;
	private ArrayList<String> fileSizeList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_download);
	    getActionBar().setTitle("Learning Center");
	    getActionBar().setSubtitle("Download References");
	    referenceList=(ListView) findViewById(R.id.tag_list);
	    dbh=new DbHelper(ReferencesDownloadActivity.this);
	    start_time=System.currentTimeMillis();
		prefs = PreferenceManager.getDefaultSharedPreferences(ReferencesDownloadActivity.this);
	    myDirectory  = new File(Environment.getExternalStorageDirectory(), "references");
	    if(!myDirectory.exists()){
	    	 myDirectory.mkdirs();
	    }else{
	    	 File[] checkFiles = myDirectory.listFiles();
	    	 for(File f:checkFiles){
	    		 if(f.getName().equals("FPFlipchart.pdf")){
	    			 f.delete();
	    		 }else if(f.getName().equals("National Safe Motherhood Service Protocol.pdf")){
	    			 f.delete();
	    		 }
    		 }
	    }
	    fileLongName=new ArrayList<String>();
	    fileLongName.add("Malaria in Pregnancy");
    	fileLongName.add("Neonatal Care Guidlines");
    	fileLongName.add("Maternal and Newborn Care");
    	fileLongName.add("National Safe Motherhood Service Protocol");
    	fileLongName.add("Family Planning Flipchart");
    	fileLongName.add("WHO");
    	fileLongName.add("National Family Planning Protocol");
    	files=new String[]{"MPG.pdf","NCG.pdf","MCG.pdf","NSMP.pdf","FPF.pdf","WHO.pdf","NFPP.pdf"};
    	myAdapter=new ListAdapter(ReferencesDownloadActivity.this,fileLongName,files,myDirectory);
    	referenceList.setAdapter(myAdapter);
    	/*
    	fileSizeList=new ArrayList<String>();
    	try {
    		
    		for(int i=0;i<files.length;i++){
    			fileSizeList.add(new GetFileSizes().execute(String.valueOf(i)).get());
    		}
		} catch (Exception e) {
		
			e.printStackTrace();
		}
    */
    	 this.registerForContextMenu(referenceList);
    	 referenceList.setOnItemClickListener(new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			String path =myDirectory.getAbsolutePath()+"/"+files[position];
			openPdfIntent(path);
			
		}		
	});
	}
	
	private void openPdfIntent(String path) 
	{
	    	 File file=new File(path);
	    	 if(file.exists()){
	    	 Uri uri  = Uri.fromFile(file);
	    	 Intent intentUrl = new Intent(Intent.ACTION_VIEW);
	    	 intentUrl.setDataAndType(uri, "application/pdf");
	    	 intentUrl.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

	 	    try {
	    	 startActivity(intentUrl);
	 	    }
	    catch (ActivityNotFoundException e) 
	    {
	      e.printStackTrace();
	      Crouton.makeText(ReferencesDownloadActivity.this, "No application available to view PDF", Style.ALERT).show();
	    }
	    	 }else{
    		 Crouton.makeText(ReferencesDownloadActivity.this, "Download the file!", Style.ALERT).show();
	    	 }
	}
	
	private class GetData extends AsyncTask<String, Void, String> {
		 private ProgressDialog dialog = 
				   new ProgressDialog(ReferencesDownloadActivity.this);
		private int progress;
		private int lenghtOfFile;
		 protected void onPreExecute() {
			   dialog.setMax(100);
			   dialog.setIndeterminate(false);
			   dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			   dialog.setMessage("Downloading files... Please wait...");
			   dialog.setCancelable(false);
			   dialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Close", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent intent=new Intent(Intent.ACTION_MAIN);
					intent.setClass(ReferencesDownloadActivity.this,ReferencesDownloadActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					finish();
					startActivity(intent);
					// overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
				}
				   
			   });
			   dialog.show();
			  }

	    @Override
	    protected String doInBackground(final String... params) {
	    	
	    	  myDirectory  = new File(Environment.getExternalStorageDirectory(), "references");
	            if(!myDirectory.exists()){
	            	 myDirectory.mkdirs();
	            }
	            try {
	            	
	            	URL u = new URL(getResources().getString(R.string.serverAddress)+MobileLearning.CCH_REFERENCE_DOWNLOAD_PATH+files[Integer.parseInt(params[0])]);
	                final HttpURLConnection c = (HttpURLConnection) u.openConnection();
	                c.setRequestMethod("GET");
	                c.setDoOutput(true);
	                c.connect();
	               
	                lenghtOfFile = c.getContentLength();
	                long size=lenghtOfFile;
	                String filesize;
  	        		double kilobytes = 0;
  	        		double megabyte = 0;
    		    	 kilobytes=(size/1024);
	        		 megabyte=(kilobytes/1024);
	        		 if(kilobytes>1000){
	        			 filesize= String.format("%.2f", megabyte)+"MB";
	        		 }else{
	        			 filesize=String.format("%.2f", kilobytes)+"KB" ;
	        		 }
	        		
	                    	runOnUiThread(new Runnable() {
	   	        		     @Override
	   	        		     public void run() {
	   	        		    	long size=lenghtOfFile;
	   	      	        		double kilobytes = 0;
	   	      	        		double megabyte = 0;
	   	        		    	 kilobytes=(size/1024);
	   	    	        		 megabyte=(kilobytes/1024);
	   	    	    			if(kilobytes>1000){
	   	    	    				dialog.setMessage("This file is "+String.format("%.2f", megabyte)+"MB");
	   	    	        		}else{
	   	    	        			dialog.setMessage("This file is "+String.format("%.2f", kilobytes)+"KB" );
	   	    	        		}

	   	        		    }
	   	        		});
	   	        		
	   	                File 	file = new File(myDirectory, files[Integer.parseInt(params[0])]);
	   	     
							file.createNewFile();
				            FileOutputStream f = new FileOutputStream(file);
		   	                InputStream in = c.getInputStream();

		   	                byte[] buffer = new byte[1024];
		   	                int len1 = 0;
		   	                long total = 0;
		   	                while ((len1 = in.read(buffer)) > 0) {
		   	                	 total += len1;
		   	                	   
		   	                	 progress=(int)((total*100)/lenghtOfFile);
		   	                	 onProgressUpdate(""+(int)((total*100)/lenghtOfFile));
		   	                	 if(progress > 0){
		   	                		 onProgressUpdate(""+progress);
		   	                     }
		   	                    f.write(buffer, 0, len1);
		   	                     
		   	                }
		   	                f.close();
	            }catch (Exception e) { 
	            	e.printStackTrace();
   	    			if(!MobileLearning.DEVELOPER_MODE){
   	    				BugSenseHandler.sendException(e);
   	    			} else {
   	    				e.printStackTrace();
   	    			}
   	    			dialog.setMessage(getString(R.string.error_connection));
   	    		
   	    		}  
	            
				return null;
	    }
	    protected void onProgressUpdate(String... progress) {
	        dialog.setProgress(Integer.parseInt(progress[0]));
	      
	   }
	    @Override
	    protected void onPostExecute(String result) {
	    	dialog.setMessage("Download Complete!");
	    	if(progress==lenghtOfFile){
	    		isComplete=true;
	    	}else{
	    		isComplete=false;
	    	}
	    }
	}
	
	private class GetFileSizes extends AsyncTask<String, String, String> {
		 private ProgressDialog dialog ;
		private int progress;
		private int lenghtOfFile;
		private String fileSize;
		@Override
		protected void onPreExecute(){
			Log.d("References", " pre execute async");
			dialog=ProgressDialog.show(ReferencesDownloadActivity.this,"Loading files..."," Please wait...",false);
			//dialog.setMessage("Loading files... Please wait...");
			//dialog.setCancelable(false);
			//dialog.show();
		}
	    @Override
	    protected String doInBackground(String... params) {
	    
	            try {
	            	
	            	URL u = new URL(getResources().getString(R.string.serverAddress)+MobileLearning.CCH_REFERENCE_DOWNLOAD_PATH+files[Integer.parseInt(params[0])]);
	                HttpURLConnection c = (HttpURLConnection) u.openConnection();
	                c.setRequestMethod("GET");
	                c.setDoOutput(true);
	                c.connect();
	               
	                lenghtOfFile = c.getContentLength();
	                double bytes = lenghtOfFile;
	               
	              
	            }catch (ClientProtocolException cpe) { 
	    			if(!MobileLearning.DEVELOPER_MODE){
	    				BugSenseHandler.sendException(cpe);
	    			} else {
	    				cpe.printStackTrace();
	    			}
	    			dialog.setMessage(getString(R.string.error_connection));
	    		} catch (SocketTimeoutException ste){
	    			if(!MobileLearning.DEVELOPER_MODE){
	    				BugSenseHandler.sendException(ste);
	    			} else {
	    				ste.printStackTrace();
	    			}
	    			dialog.setMessage(getString(R.string.error_connection));
	    		} catch (IOException ioe) { 
	    			if(!MobileLearning.DEVELOPER_MODE){
	    				BugSenseHandler.sendException(ioe);
	    			} else {
	    				ioe.printStackTrace();
	    			}
	    			dialog.setMessage(getString(R.string.error_connection));
	    		}
	            double kilobytes = (lenghtOfFile / 1024);	
				double megabyte = (kilobytes / 1024);
				if(kilobytes>1000){
					fileSize=String.valueOf(String.format("%.2f", megabyte))+"MB";
				}else{
					fileSize=String.valueOf(String.format("%.2f", kilobytes))+"KB";
				}
				return fileSize;
	    }
	   
	    @Override
	    protected void onPostExecute(String result) {
	    	dialog.dismiss();
	    	
	    }
	}
	class ListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<String> listItems;
		String[] files;
		File directory;
		 public LayoutInflater minflater;
		
		
		public ListAdapter(Context mContext,ArrayList<String> listItems,String[] files,File directory){
		this.mContext=mContext;
		this.listItems=listItems;
		this.files=files;
		this.directory=directory;
		 minflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return files.length;
		}

		@Override
		public Object getItem(int position) {
			return files[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    View rowView = inflater.inflate(R.layout.references_download_listview_single, parent, false);
			
			 TextView text=(TextView) rowView.findViewById(R.id.textView_referenceName);
			 text.setText(listItems.get(position));
			 final LinearLayout values=(LinearLayout) rowView.findViewById(R.id.Linearlayout_values);
			 final ImageButton image=(ImageButton) rowView.findViewById(R.id.imageButton_download);
			 //final TextView txt1 = new TextView(mContext);
			 txt1 =(TextView) rowView.findViewById(R.id.textView1);
			
			 File file = new File(directory, files[position]);
			 if(file.exists()){
				 
				 	double bytes = file.length();
					double kilobytes = (bytes / 1024);	
					double megabyte = (kilobytes / 1024);
					if(kilobytes>1000){
						txt1.setText(String.valueOf(String.format("%.2f", megabyte))+"MB");
					}else{
						txt1.setText(String.valueOf(String.format("%.2f", kilobytes))+"KB");
					}
					image.setVisibility(View.GONE);
			 }else{		
				 image.setImageResource(R.drawable.ic_download);
				 image.setEnabled(true);
				 //new GetFileSizes().execute(String.valueOf(position));
				 image.setOnClickListener(new OnClickListener(){
					 
						@Override
						public void onClick(View v) {
							if(dbh.isOnline(ReferencesDownloadActivity.this)){
								try {
									AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
											ReferencesDownloadActivity.this);
										alertDialogBuilder.setTitle("File size");
											alertDialogBuilder
											.setMessage("This file is "+new GetFileSizes().execute(String.valueOf(position)).get()+".\n Proceed with download?")
												.setCancelable(false)
												.setIcon(R.drawable.ic_error)
												.setPositiveButton("No",new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog,int id) {
														dialog.cancel();
													}
												  })
												.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog,int id) {
														new GetData().execute(String.valueOf(position));
														}
												});
												
										AlertDialog alertDialog = alertDialogBuilder.create();
										alertDialog.show();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}else if(!dbh.isOnline(ReferencesDownloadActivity.this)){
								AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
										ReferencesDownloadActivity.this);
									alertDialogBuilder.setTitle("Connectivity Error");
										alertDialogBuilder
										.setMessage(getResources().getString(R.string.error_connection_required))
											.setCancelable(false)
											.setIcon(R.drawable.ic_error)
											.setPositiveButton("OK",new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,int id) {
													dialog.cancel();
												}
											  });
											
									AlertDialog alertDialog = alertDialogBuilder.create();
									alertDialog.show();
							}
					
								
						}
						 
					 });
				
			 }
			    return rowView;
		}
		
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        case R.id.option1:
	        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						ReferencesDownloadActivity.this);
					alertDialogBuilder.setTitle("Delete Confirmation");
					alertDialogBuilder
						.setMessage("You are about to delete this file. Proceed?")
						.setCancelable(false)
						.setIcon(R.drawable.ic_error)
						.setPositiveButton("No",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								dialog.cancel();
							}
						  })
						.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								File filetodelete=new File(myDirectory,files[((int)info.position)]);
					        	filetodelete.delete();
					        	Intent intent=new Intent(Intent.ACTION_MAIN);
								intent.setClass(ReferencesDownloadActivity.this,ReferencesDownloadActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
								finish();
								startActivity(intent);
							
			}
    	});
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
	        	
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		 data=new JSONObject();
		    try {
		    	data.put("page", "Reference Download");
		    	data.put("ver", dbh.getVersionNumber(ReferencesDownloadActivity.this));
		    	data.put("battery", dbh.getBatteryStatus(ReferencesDownloadActivity.this));
		    	data.put("device", dbh.getDeviceName());
				data.put("imei", dbh.getDeviceImei(ReferencesDownloadActivity.this));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		dbh.insertCCHLog("Learning Center", data.toString(), start_time.toString(), end_time.toString());
		finish();
	}


}
