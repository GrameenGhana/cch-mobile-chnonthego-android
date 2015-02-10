package org.grameenfoundation.cch.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.digitalcampus.mobile.learningGF.R;
import org.grameenfoundation.poc.BaseActivity;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LearningReferencesActivity extends BaseActivity {

	private ListView listView_menu;
	private String[] list;
	private String[] pdflist;
	private File myDirectory;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_antenatal_care);
	    
	    getActionBar().setTitle("Learning Center");
	    getActionBar().setSubtitle("References");
	    listView_menu=(ListView) findViewById(R.id.listView_antenatalCare);
	    list = null;
	    copyAssets();
	    AssetManager assetManager = getAssets();
		try {
			list = assetManager.list("www/cch/modules/learning/references");
			  pdflist = new String[list.length]; 
			  for(int i = 0;i<list.length;i++)
			    {
			            pdflist[i] = list[i];
			    }
			  listView_menu.setAdapter(new ListAdapter(this, pdflist));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listView_menu.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String path =myDirectory.getAbsolutePath()+"/"+list[position];
				System.out.println(path);
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
	     //final Intent intent = new Intent(LearningReferencesActivity.this, PDFActivity.class);
	     //intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, path);
	     //startActivity(intent);																				
	    catch (ActivityNotFoundException e) 
	    {
	      e.printStackTrace();
	      Crouton.makeText(LearningReferencesActivity.this, "No application available to view PDF", Style.ALERT).show();
	    }
	    	 }
	}
	
	private void copyAssets() {
	    AssetManager assetManager = getAssets();
	    String[] files = null;
	    try {
	        files = assetManager.list("www/cch/modules/learning/references");
	    } catch (IOException e) {
	        Log.e("tag", "Failed to get asset file list.", e);
	    }
	    for(String filename : files) {
	        InputStream in = null;
	        OutputStream out = null;
	        try {
	          in = assetManager.open(filename);
	        myDirectory  = new File(Environment.getExternalStorageDirectory(), "references");
            if(!myDirectory.exists()){
            	 myDirectory.mkdirs();
            }
	          File outFile = new File(myDirectory.getAbsolutePath(), filename);
	          out = new FileOutputStream(outFile);
	          copyFile(in, out);
	          in.close();
	          in = null;
	          out.flush();
	          out.close();
	          out = null;
            
	        } catch(IOException e) {
	            Log.e("tag", "Failed to copy asset file: " + filename, e);
	        }       
	    }
	}
	private void copyFile(InputStream in, OutputStream out) throws IOException {
	    byte[] buffer = new byte[1024];
	    int read;
	    while((read = in.read(buffer)) != -1){
	      out.write(buffer, 0, read);
	    }
	}
	
	class ListAdapter extends BaseAdapter{
		Context mContext;
		String[] listItems;
		 public LayoutInflater minflater;
		
		public ListAdapter(Context mContext,String[] listItems){
		this.mContext=mContext;
		this.listItems=listItems;
		 minflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return listItems.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
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
			 text.setText(listItems[position]);
			 ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
			 image.setImageResource(R.drawable.ic_pdf);
			    return convertView;
		}
		
	}
}
