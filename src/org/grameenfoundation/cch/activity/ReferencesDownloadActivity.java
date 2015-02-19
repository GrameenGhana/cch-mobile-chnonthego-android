package org.grameenfoundation.cch.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.AboutActivity;
import org.digitalcampus.oppia.activity.AppActivity;
import org.digitalcampus.oppia.activity.HelpActivity;
import org.digitalcampus.oppia.activity.OppiaMobileActivity;
import org.digitalcampus.oppia.activity.PrefsActivity;
import org.digitalcampus.oppia.activity.StartUpActivity;
import org.digitalcampus.oppia.activity.TagSelectActivity;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.model.Lang;
import org.digitalcampus.oppia.utils.UIUtils;
import org.grameenfoundation.cch.activity.LearningReferencesActivity.ListAdapter;
import org.grameenfoundation.cch.utils.ReferenceDownloader;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ReferencesDownloadActivity extends AppActivity {

	private ListView referenceList;
	private ArrayList<String>  fileLongName;
	private String[] files;
	private boolean isComplete;
	private File myDirectory;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_download);
	    getActionBar().setTitle("Learning Center");
	    getActionBar().setSubtitle("Download");
	    referenceList=(ListView) findViewById(R.id.tag_list);
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
	    fileLongName.add("Neonatal Care Guidlines");
    	fileLongName.add("Malaria in Pregnancy");
    	fileLongName.add("Maternal and Newborn Care");
    	fileLongName.add("National Safe Motherhood Service Protocol");
    	fileLongName.add("Family Planning Flipchart");
    	fileLongName.add("WHO");
    	fileLongName.add("National Family Planning Protocol");
    	files=new String[]{"NCG.pdf","MPG.pdf","MCG.pdf","NSMP.pdf","FPF.pdf","WHO.pdf","NFPP.pdf"};
    	referenceList.setAdapter(new ListAdapter(this,fileLongName,files,myDirectory));
	}
	private class GetData extends AsyncTask<String, Void, String> {
		 private ProgressDialog dialog = 
				   new ProgressDialog(ReferencesDownloadActivity.this);
		 
		 
		
		 protected void onPreExecute() {
			   dialog.setMax(100);
			   dialog.setIndeterminate(false);
			   dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			   dialog.setMessage("Downloading files... Please wait...");
			   dialog.setCancelable(false);
			   dialog.show();
			  }

	    @Override
	    protected String doInBackground(String... params) {
	    	
	    	  myDirectory  = new File(Environment.getExternalStorageDirectory(), "references");
	            if(!myDirectory.exists()){
	            	 myDirectory.mkdirs();
	            }
	            try {
	            	File file = new File(myDirectory, files[Integer.parseInt(params[0])]);
	            	 try {
	                      file.createNewFile();
	                  } catch (IOException e1) {
	                      e1.printStackTrace();
	                  }
	                FileOutputStream f = new FileOutputStream(file);
	                URL u = new URL("http://188.226.189.149/learning/references/"+files[Integer.parseInt(params[0])]);
	                HttpURLConnection c = (HttpURLConnection) u.openConnection();
	                c.setRequestMethod("GET");
	                c.setDoOutput(true);
	                c.connect();
	                int lenghtOfFile = c.getContentLength();

	                InputStream in = c.getInputStream();

	                byte[] buffer = new byte[1024];
	                int len1 = 0;
	                long total = 0;
	                while ((len1 = in.read(buffer)) > 0) {
	                	 total += len1;
	                	 onProgressUpdate(""+(int)((total*100)/lenghtOfFile));
	                    f.write(buffer, 0, len1);
	                }
	                f.close();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

				return null;
	    }
	    protected void onProgressUpdate(String... progress) {
	    	isComplete=false;
	        dialog.setProgress(Integer.parseInt(progress[0]));
	   }
	    @Override
	    protected void onPostExecute(String result) {
	    	
	    	dialog.dismiss();
	    	isComplete=true;
	    	Intent intent=new Intent(Intent.ACTION_MAIN);
			intent.setClass(ReferencesDownloadActivity.this,LearningReferencesActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
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
			return listItems.size();
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
			if( convertView == null ){
				  convertView = minflater.inflate(R.layout.references_download_listview_single,parent, false);
			    }
			 TextView text=(TextView) convertView.findViewById(R.id.textView_referenceName);
			 text.setText(listItems.get(position));
			 final ImageButton image=(ImageButton) convertView.findViewById(R.id.imageButton_download);
			
			 File file = new File(directory, files[position] );
			 if(file.exists()){
				 image.setImageResource(R.drawable.ic_complete);
				 image.setEnabled(false);
			 }else{
				 image.setImageResource(R.drawable.ic_download);
				 image.setEnabled(true);
			 }
			 image.setOnClickListener(new OnClickListener(){
				 
				@Override
				public void onClick(View v) {
					new GetData().execute(String.valueOf(position));
				if(isComplete==true){
					image.setImageResource(R.drawable.ic_complete);
					image.setEnabled(false);
				}
				
				}
				 
			 });
			    return convertView;
		}
		
	}
	
	
}
