package fr.lemeut.loic.musketeeth.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServiceAlarm extends Service {

	private PendingIntent pendingIntent;
	private AlarmManager manager;

	public ServiceAlarm() {
	}
	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
	@Override
	public void onCreate() {
		 /* Retrieve a PendingIntent that will perform a broadcast */
		Intent alarmIntent = new Intent(this, AlarmReceiver.class);
		pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

		//Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// Perform your long running operations here.
		//Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

		// Start les alarms
		startAlarm(startOnMorning());
		startAlarm(startOnNight());
	}


	public Calendar startOnMorning(){
		/* Initialisation de l'alarm */
		manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /* Set the alarm to start at 10:30 AM */
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 0);

		return calendar;
	}

	public Calendar startOnNight(){
		/* Initialisation de l'alarm */
		manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        /* Set the alarm to start at 10:30 AM */
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, 20);
		calendar.set(Calendar.MINUTE, 0);

		return calendar;
	}

	public void startAlarm(Calendar calendar){
        /* Repeating on every 20 minutes interval */
		manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), manager.INTERVAL_DAY, pendingIntent);
	}

	@Override
	public void onDestroy() {
		//Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	}


}