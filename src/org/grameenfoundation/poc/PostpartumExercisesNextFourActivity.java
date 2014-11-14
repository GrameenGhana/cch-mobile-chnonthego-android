package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class PostpartumExercisesNextFourActivity extends Activity {

	private DbHelper dbh;
	private Long start_time;
	private Long end_time;  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_postpartum_exercises_next_four);
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("PNC Counselling: Postpartum Exercises");
	    dbh=new DbHelper(PostpartumExercisesNextFourActivity.this);
	    start_time=System.currentTimeMillis();
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
	    System.out.println("Start: " +start_time.toString()+"  "+"End: "+end_time.toString());
		dbh.insertCCHLog("Point of Care", "PNC Counselling:  Postpartum Exercises", start_time.toString(), end_time.toString());
		finish();
	}
}
