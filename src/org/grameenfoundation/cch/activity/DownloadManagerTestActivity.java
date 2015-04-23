package org.grameenfoundation.cch.activity;

import java.io.File;
import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.MobileLearning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;

public class DownloadManagerTestActivity extends Activity {

	private ListView referenceList;
	 private long enqueue;
	    private DownloadManager dm;
		private File myDirectory;
		private ArrayList<String> fileLongName;
		private String[] files;
		private ListAdapter myAdapter;
	 

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_download);
	    getActionBar().setTitle("Learning Center");
	    getActionBar().setSubtitle("Download References");
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
	    fileLongName.add("Malaria in Pregnancy");
    	fileLongName.add("Neonatal Care Guidlines");
    	fileLongName.add("Maternal and Newborn Care");
    	fileLongName.add("National Safe Motherhood Service Protocol");
    	fileLongName.add("Family Planning Flipchart");
    	fileLongName.add("WHO");
    	fileLongName.add("National Family Planning Protocol");
    	files=new String[]{"MPG.pdf","NCG.pdf","MCG.pdf","NSMP.pdf","FPF.pdf","WHO.pdf","NFPP.pdf"};
    	myAdapter=new ListAdapter(DownloadManagerTestActivity.this,fileLongName,files,myDirectory);
    	referenceList.setAdapter(myAdapter);
	    BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(
                            DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    Query query = new Query();
                    query.setFilterById(enqueue);
                    Cursor c = dm.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c
                                .getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c
                                .getInt(columnIndex)) {
 
                          
                        }
                    }
                }
            }
        };
 
        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}
	class ListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<String> listItems;
		String[] files;
		File directory;
		 public LayoutInflater minflater;
		private TextView txt1;
		
		
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
				 image.setOnClickListener(new OnClickListener(){
					 
						@Override
						public void onClick(View v) {

							 dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
						        Request request = new Request(
						                Uri.parse(getResources().getString(R.string.serverAddress)+MobileLearning.CCH_REFERENCE_DOWNLOAD_PATH+files[position]));
						        enqueue = dm.enqueue(request);
						 
						}
						 
					 });
				
			 }
			    return rowView;
		}
		
	}
}
