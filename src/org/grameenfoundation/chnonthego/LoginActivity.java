package org.grameenfoundation.chnonthego;

import java.util.ArrayList;

import org.grameenfoundation.database.CHNDatabaseHandler;
import org.grameenfoundation.utils.TypefaceUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity{

	private Button button_login;
	private Context mContext;
	private CHNDatabaseHandler db;
	private EditText editText_username;
	private EditText editText_password;
	private String username_entered;
	private String password_entered;
	private ArrayList<String> details;
	private Intent intent;
	private String current_time;
	private String current_date;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		 getActionBar().hide();
		 mContext=LoginActivity.this;
		//   TypefaceUtil.overrideFont(mContext, "SERIF", "fonts/Roboto-Thin.ttf");
		 db=new CHNDatabaseHandler(mContext);
		 button_login=(Button) findViewById(R.id.button_login);
		 
		editText_username=(EditText) findViewById(R.id.editText_username);
		editText_password=(EditText) findViewById(R.id.editText_password);
		  Time time = new Time();
		    time.setToNow();
		    current_time= time.hour +":"+time.minute;
		    current_date=time.monthDay +"-" +time.month + "-" +time.year;
		    
	}

	public void verifyLoginDetails(){
		String verified_username = null;
		String verified_password = null;
		String firstname = null;
		
		details=db.verifyLogin(username_entered, password_entered);
		if(details.size()!=0){
			verified_username=details.get(0);
			verified_password=details.get(1);
			firstname=details.get(2);
		}else{
			System.out.print("Unknown values");
		}
	
	if(username_entered.equalsIgnoreCase(verified_username) &&password_entered.equalsIgnoreCase(verified_password)){
		db.insertLoginActivity(current_date, current_time, verified_username, verified_password,"new_record");
		SharedPreferences myPrefs = LoginActivity.this.getSharedPreferences("loginPrefs", MODE_WORLD_READABLE);
		SharedPreferences.Editor prefsEditor = myPrefs.edit();
		prefsEditor.putString("firstname", firstname);
		prefsEditor.putString("username",verified_username);
		prefsEditor.commit();
		intent=new Intent(LoginActivity.this, MainScreenActivity.class);
		startActivity(intent);
		finish();
	}	else if (details.size()==0){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				LoginActivity.this);
 
			// set title
			alertDialogBuilder.setTitle("Sign in");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("Login incorrect. Please try again")
				.setCancelable(false)
				.setIcon(R.drawable.ic_error)
				.setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						LoginActivity.this.finish();
					}
				  })
				.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
			}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void Login(View v){
		switch(v.getId()){
		case R.id.button_login:
			username_entered=editText_username.getText().toString();
			password_entered=editText_password.getText().toString();
			verifyLoginDetails();
			break;
		}
	}
	
}
