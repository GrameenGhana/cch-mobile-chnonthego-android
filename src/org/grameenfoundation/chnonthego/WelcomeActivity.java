package org.grameenfoundation.chnonthego;

import java.io.File;

import org.grameenfoundation.database.CHNDatabaseHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WelcomeActivity extends Activity implements AnimationListener {

	private Animation togetherAnimation;
	private CHNDatabaseHandler db;
	private Context mContext;


	private Animation drop_one;
	private Animation drop_two;
	private Animation drop_three;
	private TextView text_one;
	private TextView text_two;
	private TextView text_three;
	private TextView text_four;
	private Animation slide_in;
	private int logincheck;
	 private static final int SPLASH_DISPLAY_LENGTH = 3000;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_welcome);
	    mContext=WelcomeActivity.this;
	    //TypefaceUtil.overrideFont(mContext, "SERIF", "fonts/Roboto-Thin.ttf");
	    db=new CHNDatabaseHandler(mContext);
		logincheck=db.getAllLoginActivity();
	   text_one=(TextView) findViewById(R.id.textView_c);
	   text_two=(TextView) findViewById(R.id.textView_h);
	   text_three=(TextView) findViewById(R.id.textView_n);
	   text_four=(TextView) findViewById(R.id.textView_text);
	   
	   togetherAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
               R.anim.together_animation);
	   togetherAnimation.setAnimationListener(this);
	   
	   drop_one=AnimationUtils.loadAnimation(getApplicationContext(),
               R.anim.drop_one);
	   drop_one.setAnimationListener(this);
	   
	   drop_two=AnimationUtils.loadAnimation(getApplicationContext(),
               R.anim.drop_two);
	   drop_two.setAnimationListener(this);
	   
	   drop_three=AnimationUtils.loadAnimation(getApplicationContext(),
               R.anim.drop_three);
	   drop_three.setAnimationListener(this);
	   
	   slide_in=AnimationUtils.loadAnimation(getApplicationContext(),
               R.anim.slide_in);
	   slide_in.setAnimationListener(this);
	   text_one.setAnimation(drop_one);
	   text_two.setAnimation(drop_two);
	   text_three.setAnimation(drop_three);
	   text_four.setAnimation(slide_in);
	   
	   
	   new Handler().postDelayed(new Runnable(){
           @Override
           
           public void run() {
        	   String filePath = getApplicationContext().getFilesDir().getPath()+"/"+"shared_prefs/loginPrefs.xml";
        	   File f = new File(filePath);
        	   if(logincheck<=0){
        		   db.insertUser("Florence", "Jones", "fjones", "admin", "new_record");
        		   
        		   db.insertCoverage("0 - 11 months", "People", "new_record");
        		   db.insertCoverage("12 - 23 months", "People", "new_record");
        		   db.insertCoverage("24 -59 months", "People", "new_record");
        		   db.insertCoverage("Women in fertile age", "People", "new_record");
        		   db.insertCoverage("Expected pregnancy", "People", "new_record");
        		   db.insertCoverage("BCG", "Immunizations", "new_record");
        		   db.insertCoverage("Penta 3", "Immunizations", "new_record");
        		   db.insertCoverage("OPV 3", "Immunizations", "new_record");
        		   db.insertCoverage("Rota 2", "Immunizations", "new_record");
        		   db.insertCoverage("PCV 3", "Immunizations", "new_record");
        		   db.insertCoverage("Measles Rubella (@9mths)", "Immunizations", "new_record");
        		   db.insertCoverage("Yellow fever (@9mths)", "Immunizations", "new_record");
        		   
        		   db.insertEventCategory("Static Child Welfare Clinics", "new_record");
        		   db.insertEventCategory("Child Welfare Clinics", "new_record");
        		   db.insertEventCategory("Outreach Child Welfare Clinics", "new_record");
        		   db.insertEventCategory("Home Visits", "new_record");
             	  Intent mainIntent = new Intent(WelcomeActivity.this,LoginActivity.class);
                   WelcomeActivity.this.startActivity(mainIntent);
                   WelcomeActivity.this.finish();
               
              }else{

              	   Intent mainIntent = new Intent(WelcomeActivity.this,MainScreenActivity.class);
                  WelcomeActivity.this.startActivity(mainIntent);
                  WelcomeActivity.this.finish();
              }
           }																									
       }, SPLASH_DISPLAY_LENGTH);
   
           
	}
   
	

	@Override
	public void onAnimationStart(Animation animation) {
		
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		
	}

}
