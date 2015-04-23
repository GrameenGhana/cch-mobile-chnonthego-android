package org.grameenfoundation.schedulers;

import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.service.TrackerService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class EventUpdateStartServiceReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		Intent service = new Intent(context, EventUpdateService.class);
		context.startService(service);
	}

}
