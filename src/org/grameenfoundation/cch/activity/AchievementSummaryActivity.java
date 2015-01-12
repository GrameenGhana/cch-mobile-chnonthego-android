package org.grameenfoundation.cch.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Callable;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.activity.CourseActivity;
import org.digitalcampus.oppia.activity.CourseIndexActivity;
import org.digitalcampus.oppia.adapter.SectionListAdapter;
import org.digitalcampus.oppia.application.DbHelper;
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
import android.content.DialogInterface;
import android.content.Intent;
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
	 
	 public ArrayList<TargetsForAchievements> completedEventTargets;
	 public ArrayList<TargetsForAchievements> completedCoverageTargets;
	 public ArrayList<TargetsForAchievements> completedLearningTargets;
	 public ArrayList<TargetsForAchievements> completedOtherTargets;
	 
	 public ArrayList<TargetsForAchievements> futureEventTargets;
	 public ArrayList<TargetsForAchievements> futureCoverageTargets;
	 public ArrayList<TargetsForAchievements> futureLearningTargets;
	 public ArrayList<TargetsForAchievements> futureOtherTargets;
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
	private Course course;
	private CourseXMLReader mxr;
	private AlertDialog aDialog;
	private Activity baselineActivity;
	 ArrayList<Scores> course_scores;
	 ArrayList<Course> courses;
	public static final String TAG = AchievementSummaryActivity.class.getSimpleName();
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_achievements_summary);
	    getActionBar().setTitle("Achievement Center");
	    getActionBar().setSubtitle("Achievement Summary");
	    c= new CalendarEvents(AchievementSummaryActivity.this);
	    db=new DbHelper(AchievementSummaryActivity.this);
	    Bundle extras = getIntent().getExtras(); 
        if (extras != null) {
          month= extras.getInt("month");
          year=extras.getInt("year");
        }
        courses=db.getCourses();
	    pastEvents=c.readPastCalendarEvents(AchievementSummaryActivity.this, 11, 2014);
	    futureEvents=c.readFutureCalendarEvents(AchievementSummaryActivity.this, 11, 2014);
	    totalNumberOfEvents=c.readCalendarEventsTotal(AchievementSummaryActivity.this, 11, 2014);
	    
	    completedEventTargets=db.getAllEventTargetsCompletedForAchievements("updated", 12, 2014);
	    completedCoverageTargets=db.getAllCoverageTargetsCompletedForAchievements("updated", 12, 2014);
	    completedLearningTargets=db.getAllLearningTargetsCompletedForAchievements("updated", 12, 2014);
	    completedOtherTargets=db.getAllOtherTargetsCompletedForAchievements("updated", 12, 2014);
	    
	    
	    futureEventTargets=db.getAllEventTargetsCompletedForAchievements("new_record", 12, 2014);
	    futureCoverageTargets=db.getAllCoverageTargetsCompletedForAchievements("new_record", 12, 2014);
	    futureLearningTargets=db.getAllLearningTargetsCompletedForAchievements("new_record", 12, 2014);
	    futureOtherTargets=db.getAllOtherTargetsCompletedForAchievements("new_record", 12, 2014);
	    
	    totalEventTargets=db.getAllEventTargetsForAchievements( 12, 2014);
	    totalCoverageTargets=db.getAllCoverageTargetsForAchievements( 12, 2014);
	    totalLearningTargets=db.getAllLearningTargetsForAchievements(12, 2014);
	    totalOtherTargets=db.getAllOtherTargetsForAchievements( 12, 2014);
	    
	    
	    textView_eventPercentage=(TextView) findViewById(R.id.textView_eventPercentage);
	    textView_eventPercentage.setText(calculateEventsCompleted()+"%   /   "+calculateEventsTodo()+"%" );
	    textView_targetsPercentage=(TextView) findViewById(R.id.textView_targetPercentage);
	    textView_targetsPercentage.setText(calculateTargetsCompleted()+ "%   /   "+calculateTargetsTodo()+"%" );
	    textView_coursesPercentage=(TextView) findViewById(R.id.textView_coursePercentage);
	    int courseUncompletedText=	100-Integer.valueOf(db.getCourseProgressCompleted());
	    textView_coursesPercentage.setText(db.getCourseProgressCompleted()+"%   /   "+String.valueOf(courseUncompletedText)+"%" );
	    
	    tableRow_events=(TableRow) findViewById(R.id.tableRow_events);
	    tableRow_targets=(TableRow) findViewById(R.id.tableRow_targets);
	    tableRow_courses=(TableRow) findViewById(R.id.tableRow_courses);
	    tableRow_events.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AchievementSummaryActivity.this, EventsAchievementsActivity.class);
				startActivity(intent);
			}
	    	
	    });
	    
	    tableRow_targets.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AchievementSummaryActivity.this, TargetAchievementsActivity.class);
				startActivity(intent);
				
			}
	    	
	    });
	    
	    tableRow_courses.setOnClickListener(new OnClickListener(){
	    
			
			
			@Override
			public void onClick(View v) {
				System.out.println("Clicked");
				Intent intent=new Intent(AchievementSummaryActivity.this,CourseDetailActivity.class);
				startActivity(intent);
			}
	    	
	    });
	    chart = new BarChart(AchievementSummaryActivity.this);
	    linearLayout_graph=(LinearLayout) findViewById(R.id.linearLayout_graph);
	    linearLayout_graph.addView(chart);
	    
        
	    chart.setDrawYValues(true);

	    chart.setDescription("");
        
	    chart.setDrawYValues(true);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
	    chart.setMaxVisibleValueCount(60);

        // disable 3D
	    chart.set3DEnabled(false);
        // scaling can now only be done on x- and y-axis separately
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

        
        // add a nice and smooth animation
        chart.animateY(2500);
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        ArrayList<BarEntry> valsComp1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> valsComp2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> valsComp3 = new ArrayList<BarEntry>();
        BarEntry c1e1 = new BarEntry(Integer.valueOf(calculateEventsCompleted()), 0); 
        valsComp1.add(c1e1);
        BarEntry c1e2 = new BarEntry(Integer.valueOf(calculateTargetsCompleted()), 1); 
        valsComp1.add(c1e2);
        BarEntry c1e3 = new BarEntry(Integer.valueOf(db.getCourseProgressCompleted()), 2); 
        valsComp1.add(c1e3);
       

        BarEntry c2e1 = new BarEntry(Integer.valueOf(calculateEventsTodo()), 0); // 0 == quarter 1
        valsComp2.add(c2e1);
        BarEntry c2e2 = new BarEntry(Integer.valueOf(calculateTargetsTodo()), 1); // 1 == quarter 2 ...
        valsComp2.add(c2e2);
        int courseUncompleted=	100-Integer.valueOf(db.getCourseProgressCompleted());
        BarEntry c2e3 = new BarEntry(courseUncompleted, 2); // 1 == quarter 2 ...
        valsComp2.add(c2e3);
        
        
        BarDataSet setComp1 = new BarDataSet(valsComp1, " ");
        setComp1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        BarDataSet setComp2 = new BarDataSet(valsComp2, " ");
        setComp2.setColors(ColorTemplate.JOYFUL_COLORS);
        //BarDataSet setComp3 = new BarDataSet(valsComp2, " ");
        //setComp3.setColors(ColorTemplate.VORDIPLOM_COLORS);
        
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);
        
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Events"); xVals.add("Targets"); xVals.add("Courses"); 

        BarData data = new BarData(xVals, dataSets);
        
        chart.setData(data);
        chart.invalidate();
	}
	
	
	private String calculateEventsCompleted(){
		int numberCompleted=pastEvents.size();
		int totalNumber=totalNumberOfEvents.size();
		String percentage;
		if(totalNumber>0){
		Double  percentage_completed=((double)numberCompleted/totalNumber) *100;
		percentage=String.format("%.0f", percentage_completed);
		}else{
			percentage="0";
		}
		return percentage;
	}
	

	private String calculateEventsTodo(){
		int numberTodo=futureEvents.size();
		int totalNumber=totalNumberOfEvents.size();
		String percentage;
		if(totalNumber>0){
		Double percentage_completed=((double)numberTodo/totalNumber) *100;
		percentage=String.format("%.0f", percentage_completed);
		}else{
			percentage="0";
		}
		return percentage;
	}
	
	private String calculateTargetsCompleted(){
		int numberCompleted=completedEventTargets.size()+completedCoverageTargets.size()+completedLearningTargets.size()+completedOtherTargets.size();
		int totalNumber=totalEventTargets+totalCoverageTargets+totalLearningTargets+totalOtherTargets;
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
		int numberCompleted=futureEventTargets.size()+futureCoverageTargets.size()+futureLearningTargets.size()+futureOtherTargets.size();
		int totalNumber=totalEventTargets+totalCoverageTargets+totalLearningTargets+totalOtherTargets;
		String percentage;
		if(totalNumber>0){
		Double  percentage_completed=((double)numberCompleted/totalNumber) *100;
		percentage=String.format("%.0f", percentage_completed);
		}else{
			percentage="0";
		}
		return percentage;
	}
	
}
