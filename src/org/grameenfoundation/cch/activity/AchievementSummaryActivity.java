package org.grameenfoundation.cch.activity;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.CourseActivity;
import org.digitalcampus.oppia.activity.CourseIndexActivity;
import org.digitalcampus.oppia.activity.DownloadActivity;
import org.digitalcampus.oppia.activity.TagSelectActivity;
import org.digitalcampus.oppia.adapter.SectionListAdapter;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.digitalcampus.oppia.exception.InvalidXMLException;
import org.digitalcampus.oppia.model.Activity;
import org.digitalcampus.oppia.model.Course;
import org.digitalcampus.oppia.model.Lang;
import org.digitalcampus.oppia.model.Scores;
import org.digitalcampus.oppia.model.Section;
import org.digitalcampus.oppia.utils.CourseXMLReader;
import org.digitalcampus.oppia.utils.UIUtils;
import org.grameenfoundation.calendar.CalendarEvents;
import org.grameenfoundation.cch.model.EventTargets;
import org.grameenfoundation.cch.model.LearningTargets;
import org.grameenfoundation.cch.model.MyCalendarEvents;
import org.grameenfoundation.cch.model.TargetsForAchievements;
import org.grameenfoundation.poc.BaseActivity;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.Legend.LegendPosition;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.XLabels.XLabelPosition;
import com.github.mikephil.charting.utils.YLabels;
import com.github.mikephil.charting.utils.YLabels.YLabelPosition;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AchievementSummaryActivity extends BaseActivity {

	private CalendarEvents c;
	 public ArrayList<MyCalendarEvents> pastEvents;
	 public ArrayList<MyCalendarEvents> futureEvents;
	 public ArrayList<MyCalendarEvents> totalNumberOfEvents;
	 
	 public int completedEventTargets;
	 public int completedCoverageTargets;
	 public int completedLearningTargets;
	 public int completedOtherTargets;
	 
	 public int futureEventTargets;
	 public int futureCoverageTargets;
	 public int futureLearningTargets;
	 public int futureOtherTargets;
	 DbHelper db;
	private TextView textView_eventPercentage;
	private TextView textView_targetsPercentage;
	private TextView textView_coursesPercentage;
	private int totalEventTargets;
	private int totalCoverageTargets;
	private int totalLearningTargets;
	private int totalOtherTargets;
	private LinearLayout linearLayout_graph;
	private BarChart chart;
	private TableRow tableRow_events;
	private TableRow tableRow_targets;
	private TableRow tableRow_courses;
	private int month;
	private int year;
	 ArrayList<Scores> course_scores;
	private int courseUncompletedText;
	private int courseCompleted;
	private Long start_time;
	private Long end_time;
	private int numberFuture;
	private int numberCompleted;
	private int numberTodo;
	private int eventsNumberCompleted;
	private JSONObject data;
	private TextView textView_timePeriod;
	private DateTime  today;
	public static final String TAG = AchievementSummaryActivity.class.getSimpleName();
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_achievements_summary);
	    getActionBar().setTitle("Achievement Center");
	    getActionBar().setSubtitle("Achievement Summary");
	    c= new CalendarEvents(AchievementSummaryActivity.this);
	    db=new DbHelper(AchievementSummaryActivity.this);
	    start_time=System.currentTimeMillis();
        textView_eventPercentage=(TextView) findViewById(R.id.textView_eventPercentage);
        textView_targetsPercentage=(TextView) findViewById(R.id.textView_targetPercentage);
        textView_coursesPercentage=(TextView) findViewById(R.id.textView_coursePercentage);
	    textView_timePeriod=(TextView) findViewById(R.id.textView_timePeriod);
	   today=new DateTime();
	    tableRow_events=(TableRow) findViewById(R.id.tableRow_events);
	    tableRow_targets=(TableRow) findViewById(R.id.tableRow_targets);
	    tableRow_courses=(TableRow) findViewById(R.id.tableRow_courses);
	    tableRow_events.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AchievementSummaryActivity.this, EventsAchievementsActivity.class);
				intent.putExtra("month", month);
				intent.putExtra("year", year);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    
	    tableRow_targets.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AchievementSummaryActivity.this, TargetAchievementDetailActivity.class);
				intent.putExtra("month", month);
				intent.putExtra("year", year);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    
	    tableRow_courses.setOnClickListener(new OnClickListener(){
	    
			
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AchievementSummaryActivity.this,CourseDetailActivity.class);
				intent.putExtra("month", month);
				intent.putExtra("year", year);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_in_right);
			}
	    	
	    });
	    
	    chart = new BarChart(AchievementSummaryActivity.this);
	    linearLayout_graph=(LinearLayout) findViewById(R.id.linearLayout_graph);
	    new GetData().execute();
        
	}
	
	
	private String calculateEventsCompleted(){
		pastEvents=c.readPastCalendarEvents(AchievementSummaryActivity.this, month, year,true);
		totalNumberOfEvents=c.readCalendarEventsTotal(AchievementSummaryActivity.this, month, year);
		eventsNumberCompleted=pastEvents.size();
		int totalNumber=totalNumberOfEvents.size();
		String percentage;
		if(totalNumber>0){
			System.out.println("Number of events completed in: "+String.valueOf(eventsNumberCompleted));
			Double  percentage_completed=((double)eventsNumberCompleted/totalNumber) *100;
			percentage=String.format("%.0f", percentage_completed);
		}else{
			percentage="0";
		}	
		return percentage;
	}
	

	private String calculateEventsTodo(){
		futureEvents=c.readFutureCalendarEvents(AchievementSummaryActivity.this, month, year,true);
		totalNumberOfEvents=c.readCalendarEventsTotal(AchievementSummaryActivity.this, month, year);
		numberTodo=futureEvents.size();
		int totalNumber=totalNumberOfEvents.size();
		String percentage;
		if(totalNumber>0){
			System.out.println("Number of events to do in: "+String.valueOf(numberTodo));
			System.out.println("Number of events total: "+String.valueOf(totalNumber));
		Double percentage_completed=((double)numberTodo/totalNumber) *100;
		percentage=String.format("%.0f", percentage_completed);
		}else{
			percentage="0";
		}
		return percentage;
	}
	
	private String calculateTargetsCompleted(){
		completedEventTargets=db.getTargetsBasedOnStatus(MobileLearning.CCH_TARGET_STATUS_UPDATED,MobileLearning.CCH_TARGET_TYPE_EVENT, month+1, year);
		completedCoverageTargets=db.getTargetsBasedOnStatus(MobileLearning.CCH_TARGET_STATUS_UPDATED,MobileLearning.CCH_TARGET_TYPE_COVERAGE, month+1, year);
		completedLearningTargets=db.getTargetsBasedOnStatus(MobileLearning.CCH_TARGET_STATUS_UPDATED,MobileLearning.CCH_TARGET_TYPE_LEARNING,  month+1, year);
		completedOtherTargets=db.getTargetsBasedOnStatus(MobileLearning.CCH_TARGET_STATUS_UPDATED, MobileLearning.CCH_TARGET_TYPE_OTHER,month+1, year);
		 
	    totalEventTargets=db.getTargetsForAchievements(MobileLearning.CCH_TARGET_TYPE_EVENT,month+1, year);
	    totalCoverageTargets=db.getTargetsForAchievements(MobileLearning.CCH_TARGET_TYPE_COVERAGE,month+1, year);
	    totalLearningTargets=db.getTargetsForAchievements(MobileLearning.CCH_TARGET_TYPE_LEARNING,month+1, year);
	    totalOtherTargets=db.getTargetsForAchievements(MobileLearning.CCH_TARGET_TYPE_OTHER,month+1, year);
		numberCompleted=completedEventTargets+completedCoverageTargets+completedLearningTargets+completedOtherTargets;
		int totalNumber=totalEventTargets+totalCoverageTargets+totalLearningTargets+totalOtherTargets;
		System.out.println("Total Targets:"+String.valueOf(totalNumber));
		String percentage;
		if(totalNumber>0){
		Double  percentage_completed=((double)numberCompleted/totalNumber) *100;
		percentage=String.format("%.0f", percentage_completed);
		}else{
			percentage="0";	
		}
		return percentage;
	}
	
	private String calculateTargetsTodo(){
		
		futureEventTargets=db.getTargetsBasedOnStatus(MobileLearning.CCH_TARGET_STATUS_NEW, MobileLearning.CCH_TARGET_TYPE_EVENT,month+1, year);
		futureCoverageTargets=db.getTargetsBasedOnStatus(MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_COVERAGE, month+1, year);
	    futureLearningTargets=db.getTargetsBasedOnStatus(MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_LEARNING, month+1, year);
	    futureOtherTargets=db.getTargetsBasedOnStatus(MobileLearning.CCH_TARGET_STATUS_NEW,MobileLearning.CCH_TARGET_TYPE_OTHER, month+1, year);
	  
	    totalEventTargets=db.getTargetsForAchievements(MobileLearning.CCH_TARGET_TYPE_EVENT, month+1, year);
	    totalCoverageTargets=db.getTargetsForAchievements(MobileLearning.CCH_TARGET_TYPE_COVERAGE,month+1, year);
	    totalLearningTargets=db.getTargetsForAchievements(MobileLearning.CCH_TARGET_TYPE_LEARNING,month+1, year);
	    totalOtherTargets=db.getTargetsForAchievements(MobileLearning.CCH_TARGET_TYPE_OTHER,month+1, year);
		numberFuture=futureEventTargets+futureCoverageTargets+futureLearningTargets+futureOtherTargets;
		
		int totalNumber=totalEventTargets+totalCoverageTargets+totalLearningTargets+totalOtherTargets;
		String percentage;
		
		if(totalNumber>0){
		Double  percentage_completed=((double)numberFuture/totalNumber) *100;
		percentage=String.format("%.0f", percentage_completed);
		}else{
			percentage="0";
		}
		return percentage;
	}
	private class GetData extends AsyncTask<Object, Void, Object> {
		 DbHelper db=new DbHelper(AchievementSummaryActivity.this);
		private String eventsCompleted;
		private String eventsTodo;
		private String targetsCompleted;
		private String targetTodo;
		 private ProgressDialog dialog = 
				   new ProgressDialog(AchievementSummaryActivity.this);
		 protected void onPreExecute() {
			   dialog.setMessage("Loading data... Please wait...");
			   dialog.show();
			  }

	    @Override
	    protected Object doInBackground(Object... params) {
	    	  Bundle extras = getIntent().getExtras(); 
	          if (extras != null) {
	            month= extras.getInt("month");
	            year=extras.getInt("year");
	          }
	        eventsCompleted=calculateEventsCompleted();
	        eventsTodo=calculateEventsTodo();
	        targetsCompleted=calculateTargetsCompleted();
	        targetTodo=calculateTargetsTodo();
	        courseCompleted=Integer.valueOf(db.getCourseProgressCompleted(month+1,year));
	        courseUncompletedText=100-courseCompleted;
				return null;
	    }

	    @Override
	    protected void onPostExecute(Object result) {
	    	dialog.dismiss();
	    	 if(eventsCompleted!=null && eventsTodo!=null){
	    		 		textView_eventPercentage.setText(String.valueOf(eventsNumberCompleted)+"     /    "+String.valueOf(numberTodo) );
	    	        }
	    	        
	    	        if(targetsCompleted!=null && targetTodo!=null){
	    	        	textView_targetsPercentage.setText(String.valueOf(numberCompleted)+ "      /    "+String.valueOf(numberFuture));
	    	        }
	    	       
	    	        if(courseCompleted>0){
	    	        	courseUncompletedText=100-courseCompleted;
	    	        }else {
	    	        	courseUncompletedText=0;
	    	        }
	    	        System.out.println("This year is: "+String.valueOf(today.getYear()));
	    	        if(year<=today.getYear()){
	    	        	textView_coursesPercentage.setText(String.valueOf(courseCompleted)+"%   /   "+String.valueOf(courseUncompletedText)+"%" );
	    	        }else {
	    	        	textView_coursesPercentage.setText("0"+"%   /   "+"0"+"%" );
	    	        }
	    	        linearLayout_graph.addView(chart);
	    	        textView_timePeriod.setText(getMonth(month)+" "+String.valueOf(year));
	    	        
	    		    chart.setDrawYValues(true);

	    		    chart.setDescription("");
	    	        
	    		    chart.setDrawYValues(true);
	    		    chart.setMaxVisibleValueCount(60);
	    		    chart.set3DEnabled(false);
	    		    chart.setPinchZoom(false);
	    		    chart.setDrawBarShadow(false);
	    	        
	    		    chart.setDrawVerticalGrid(false);
	    		    chart.setDrawHorizontalGrid(false);
	    		    chart.setDrawGridBackground(false);

	    	        XLabels xLabels = chart.getXLabels();
	    	        xLabels.setPosition(XLabelPosition.BOTTOM);
	    	        xLabels.setCenterXLabelText(true);
	    	        xLabels.setSpaceBetweenLabels(0);
	    	        
	    	        YLabels yLabels = chart.getYLabels();
	    	        yLabels.setPosition(YLabelPosition.LEFT);


	    	        chart.setDrawYLabels(false);
	    	        chart.setDrawLegend(false);
	    	        chart.animateY(2500);
	    	        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

	    	        ArrayList<BarEntry> valsComp1 = new ArrayList<BarEntry>();
	    	        ArrayList<BarEntry> valsComp2 = new ArrayList<BarEntry>();
	    	        ArrayList<BarEntry> valsComp3 = new ArrayList<BarEntry>();
	    	        if(calculateEventsCompleted()!=null){
	    	        	BarEntry c1e1 = new BarEntry(Integer.valueOf(calculateEventsCompleted()), 0); 
	    	        	valsComp1.add(c1e1);
	    	        }
	    	        if(calculateTargetsCompleted()!=null){
	    	        	BarEntry c1e2 = new BarEntry(Integer.valueOf(calculateTargetsCompleted()), 1); 
	    	        	valsComp1.add(c1e2);
	    	        }
	    	        if(db.getCourseProgressCompleted(month+1,year)!=null){
	    	        	if(year<=today.getYear()){
	    	        		BarEntry c1e3 = new BarEntry(Integer.valueOf(db.getCourseProgressCompleted(month+1,year)), 2); 
	    	        		valsComp1.add(c1e3);
	    	        	}else{
	    	        		BarEntry c1e3 = new BarEntry(0, 2); 
	    	        		valsComp1.add(c1e3);
	    	        	}
	    	        }
	    	        
	    	        if(calculateEventsTodo()!=null){
	    	        	BarEntry c2e1 = new BarEntry(Integer.valueOf(calculateEventsTodo()), 0); // 0 == quarter 1
	    	        	valsComp2.add(c2e1);
	    	        }
	    	        if(calculateTargetsTodo()!=null){
	    	        		BarEntry c2e2 = new BarEntry(Integer.valueOf(calculateTargetsTodo()), 1); // 1 == quarter 2 ...
	    	        		valsComp2.add(c2e2);
	    	        }
	    	        
	    	        if(db.getCourseProgressCompleted(month+1,year)!=null){
	    	        	if(year<=today.getYear()){
	    	        		BarEntry c2e3 = new BarEntry(courseUncompletedText, 2); // 1 == quarter 2 ...
	    	        		valsComp2.add(c2e3);
	    	        	}else {
	    	        		BarEntry c2e3 = new BarEntry(0, 2); // 1 == quarter 2 ...
	    	        		valsComp2.add(c2e3);
	    	        	}
	    	        }
	    	        
	    	        int[] done_colors={getResources().getColor(R.color.Red)};
	    	        int[] todo_colors={getResources().getColor(R.color.Green)};
	    	        BarDataSet setComp1 = new BarDataSet(valsComp1, "Todo");
	    	        setComp1.setColors(todo_colors);
	    	        BarDataSet setComp2 = new BarDataSet(valsComp2, "Done");
	    	        setComp2.setColors(done_colors);
	    	        
	    	        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
	    	        dataSets.add(setComp1);
	    	        dataSets.add(setComp2);
	    	        
	    	        ArrayList<String> xVals = new ArrayList<String>();
	    	        xVals.add("Events"); xVals.add("Targets"); xVals.add("Courses"); 

	    	        BarData data = new BarData(xVals, dataSets);
	    	        
	    	        chart.setData(data);
	    	        chart.invalidate();
	    }
	}
	
	public String getMonth(int month){
		return new DateFormatSymbols().getMonths()[month];
		
	}
	public void onBackPressed()
	{
		 end_time=System.currentTimeMillis();
		 data=new JSONObject();
		    try {
		    	data.put("page", "Achievements Summary");
		    	data.put("ver", db.getVersionNumber(AchievementSummaryActivity.this));
		    	data.put("battery", db.getBatteryStatus(AchievementSummaryActivity.this));
		    	data.put("device", db.getDeviceName());
				data.put("imei", db.getDeviceImei(AchievementSummaryActivity.this));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		db.insertCCHLog("Achievement Center", data.toString(), start_time.toString(), end_time.toString());
		finish();
	}

}
