package org.grameenfoundation.poc;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;

import android.app.Activity;
import android.os.Bundle;

public class NutritionANCNextActivity extends Activity {
	private DbHelper dbh;
	private Long start_time;
	private Long end_time; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_nutrition_4star_diet);
	    dbh=new DbHelper(NutritionANCNextActivity.this);
	    start_time=System.currentTimeMillis();
	    getActionBar().setTitle("Point of Care");
	    getActionBar().setSubtitle("ANC Counselling: Nutrition");
	}
	public void onBackPressed()
	{
	    end_time=System.currentTimeMillis();
		dbh.insertCCHLog("Point of Care", "ANC Counselling: Nutrition", start_time.toString(), end_time.toString());
		finish();
	}
}
