package org.grameenfoundation.poc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.grameenfoundation.cch.caldroid.CaldroidFragment;
import org.grameenfoundation.cch.caldroid.CaldroidListener;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EstimateDueDateCalculator extends FragmentActivity {

	private Button button_calculate;
	private TextView textView_selectedDate;
	private String newDate;
	private TextView textView_estimatedDueDate;
	private String estimated_due_date;
	private DbHelper dbh;
	private Long start_time;
	private Long end_time;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_estimate_due_date);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("Due Date Calculator");
	    start_time=System.currentTimeMillis();
	    dbh=new DbHelper(EstimateDueDateCalculator.this);
	    textView_selectedDate=(TextView) findViewById(R.id.textView_selectedDate);
	    textView_estimatedDueDate=(TextView) findViewById(R.id.textView_estimatedDueDate);
	    final CaldroidFragment caldroidFragment  = new CaldroidFragment();
	    Bundle args = new Bundle();
	    Calendar cal = Calendar.getInstance();
	    args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
	    args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
	    caldroidFragment.setArguments(args);

	    FragmentTransaction t = getSupportFragmentManager().beginTransaction();
	    t.replace(R.id.fragment1, caldroidFragment);
	    t.commit();
	    
	    final CaldroidListener listener = new CaldroidListener() {
	    	SimpleDateFormat formatter  = new SimpleDateFormat("dd/MM/yyyy");
			
	        @Override
	        public void onSelectDate(Date date, View view) {
	        	newDate=formatter.format(date);
	        	textView_selectedDate.setText("You selected: "+formatter.format(date));
	        	caldroidFragment.setBackgroundResourceForDate(Color.rgb(83,171,32), date);
	        	caldroidFragment.setTextColorForDate(Color.rgb(255, 255, 255), date);
	        }

	       
	    };

	    caldroidFragment.setCaldroidListener(listener);
	    /*
	    myWebView = (WebView) findViewById(R.id.webView_estimate);	
	    myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
		myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);	 
		myWebView.loadUrl(URL);
		myWebView.setWebViewClient(new WebViewClient(){
				
			@Override
			     public void onReceivedError(WebView view, int errorCod,String description, String failingUrl) {
		            Toast.makeText(view.getContext(), description , Toast.LENGTH_LONG).show();
		         }
			    
			     
	});

	    mPager = (ViewPager)findViewById(R.id.pager3);
	    mPager.setAdapter(mAdapter);
	    */
	    button_calculate=(Button) findViewById(R.id.button_calculate);
	    button_calculate.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				calculateDueDate();			
			}
	    	
	    });
	}
	
	public void calculateDueDate(){
		SimpleDateFormat formatter  = new SimpleDateFormat("dd/MM/yyyy");
		 Calendar cal = Calendar.getInstance();
    	 try {
			cal.setTime(formatter.parse(newDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 cal.add(Calendar.DATE,280);
    	 estimated_due_date=formatter.format(cal.getTime());
    	 textView_estimatedDueDate.setText("Estimated due date is: "+estimated_due_date);
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "Due Date Calculator", start_time.toString(), end_time.toString());
		finish();
	}
}
