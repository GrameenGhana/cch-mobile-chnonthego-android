package org.grameenfoundation.schedulers;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.application.MobileLearning;
import org.grameenfoundation.cch.tasks.FacilityTargetsTask;
import org.grameenfoundation.cch.tasks.UserDetailsProcessTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class UserDetailsUpdateService extends Service {

	private Context mContext;
	private SharedPreferences prefs;
	private String name;
	private DbHelper dbh;
	public UserDetailsUpdateService(){
		
	}
	public UserDetailsUpdateService(Context c){
		this.mContext=c;
		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(isOnline()){
			try{
				 prefs = PreferenceManager.getDefaultSharedPreferences(this);
				 name=prefs.getString("first_name", "name");
					UserDetailsProcessTask usd = new UserDetailsProcessTask(this);
					usd.execute(new String[] { getResources().getString(R.string.serverDefaultAddress)+"/"+MobileLearning.CCH_USER_DETAILS_PATH+name});
					
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return Service.START_NOT_STICKY;
	} 
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isOnline() {
		 boolean haveConnectedWifi = false;
		    boolean haveConnectedMobile = false;

		    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		    for (NetworkInfo ni : netInfo) {
		        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		            if (ni.isConnected())
		                haveConnectedWifi = true;
		        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		            if (ni.isConnected())
		                haveConnectedMobile = true;
		    }
		    return haveConnectedWifi || haveConnectedMobile;
	}
}
