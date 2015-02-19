package org.grameenfoundation.cch.activity;

import java.io.File;
import java.util.ArrayList;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.AboutActivity;
import org.digitalcampus.oppia.activity.HelpActivity;
import org.digitalcampus.oppia.activity.StartUpActivity;
import org.digitalcampus.oppia.application.DbHelper;

import android.view.Menu;
import android.view.MenuItem;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LearningReferencesActivity extends Activity {

	private ListView listView_menu;
	private String[] list;
	private String[] pdflist;
	private File myDirectory;
	private LinearLayout reference_status;
	private LinearLayout reference_list;
	private Button manageButton;
	private ArrayList<String> MyFiles;
	private ArrayList<String> fileLongName;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_learning_center_references);
	    
	    getActionBar().setTitle("Learning Center");
	    getActionBar().setSubtitle("References");
	    listView_menu=(ListView) findViewById(R.id.listView1);
	    reference_status=(LinearLayout) findViewById(R.id.referenceStatus);
	    reference_list=(LinearLayout) findViewById(R.id.referenceList);
	    manageButton=(Button) findViewById(R.id.button_manage);
	    myDirectory  = new File(Environment.getExternalStorageDirectory(), "references");
	    fileLongName=new ArrayList<String>();
	    /*
	    fileLongName.add("Neonatal Care Guidlines");
    	fileLongName.add("Malaria in Pregnancy");
    	fileLongName.add("Maternal and Newborn Care");
    	fileLongName.add("National Safe Motherhood Service Protocol");
    	fileLongName.add("Family Planning Flipchart");
    	fileLongName.add("WHO");
    	fileLongName.add("National Family Planning Protocol");
    	*/
    	for(int i=0;i<GetFiles().size();i++){
    		String name=null;
    		if(GetFiles().get(i).equals("NCG.pdf")){
    			name="Neonatal Care Guidlines";
    		}else if(GetFiles().get(i).equals("MPG.pdf")){
    			name="Malaria in Pregnancy";
    		}else if(GetFiles().get(i).equals("MCG.pdf")){
    			name="Maternal and Newborn Care";
    		}else if(GetFiles().get(i).equals("NSMP.pdf")){
    			name="National Safe Motherhood Service Protocol";
    		}else if(GetFiles().get(i).equals("FPF.pdf")){
    			name="Family Planning Flipchart";
    		}else if(GetFiles().get(i).equals("WHO.pdf")){
    			name="WHO";
    		}else if(GetFiles().get(i).equals("NFPP.pdf")){
    			name="National Family Planning Protocol";
    		}
    		fileLongName.add(name);
    		
    	}
	    if(myDirectory.exists()&&GetFiles().size()>0){
	    		 reference_list.setVisibility(View.VISIBLE);
	    		 listView_menu.setAdapter(new ListAdapter(LearningReferencesActivity.this, GetFiles(),fileLongName));
	    		 reference_status.setVisibility(View.GONE);
	    	 
	    }else {
	    	 myDirectory.mkdirs();
	    	 reference_list.setVisibility(View.GONE);
	    	 reference_status.setVisibility(View.VISIBLE);
	    }
	    listView_menu.setAdapter(new ListAdapter(LearningReferencesActivity.this, GetFiles(),fileLongName));
	    this.registerForContextMenu(listView_menu);
		listView_menu.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String path =myDirectory.getAbsolutePath()+"/"+GetFiles().get(position);
				System.out.println(path);
		       openPdfIntent(path);
				
			}		
		});
		manageButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(LearningReferencesActivity.this,ReferencesDownloadActivity.class);
				startActivity(intent);
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
	      Crouton.makeText(LearningReferencesActivity.this, "No application available to view PDF", Style.ALERT).show();
	    }
	    	 }
	}
	
	
	
	
	public ArrayList<String> GetFiles() {
	    MyFiles = new ArrayList<String>();
	   
	    File[] files = myDirectory.listFiles();
	    if (files.length == 0)
	        return null;
	    else {
	        for (int i=0; i<files.length; i++) 
	            MyFiles.add(files[i].getName());
	    }

	    return MyFiles;
	}
	class ListAdapter extends BaseAdapter{
		Context mContext;
		ArrayList<String> listItems;
		public LayoutInflater minflater;
		ArrayList<String> items;
		public ListAdapter(Context mContext,ArrayList<String> listItems,ArrayList<String> items){
		this.mContext=mContext;
		this.listItems=listItems;
		this.items=items;
		 minflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			return listItems.size();
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
			 text.setText(items.get(position));
			 ImageView image=(ImageView) convertView.findViewById(R.id.imageView1);
			 image.setImageResource(R.drawable.ic_pdf);
			    return convertView;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_references, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_about) {
			startActivity(new Intent(this, AboutActivity.class));
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
		} else if (itemId == R.id.menu_download) {
			startActivity(new Intent(this, ReferencesDownloadActivity.class));
			finish();
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
		} else if (itemId == R.id.menu_help) {
			startActivity(new Intent(this, HelpActivity.class));
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			return true;
		} else if (itemId == R.id.menu_logout) {
			logout();
			return true;
		}
		return true;
	}
	private void logout() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle(R.string.logout);
		builder.setMessage(R.string.logout_confirm);
		builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// wipe activity data
				DbHelper db = new DbHelper(LearningReferencesActivity.this);
				db.onLogout();
				db.close();

				// restart the app
				LearningReferencesActivity.this.startActivity(new Intent(LearningReferencesActivity.this, StartUpActivity.class));
				LearningReferencesActivity.this.finish();
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_left);

			}
		});
		builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				return; // do nothing
			}
		});
		builder.show();
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
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        case R.id.option1:
	        	File filetodelete=new File(myDirectory,GetFiles().get((int)info.id));
	        	System.out.println(GetFiles().get((int)info.id));
	        	filetodelete.delete();
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
}
