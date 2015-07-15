package fr.lemeut.loic.musketeeth.services;

/**
 * Created by Loic on 15/07/2015.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import fr.lemeut.loic.musketeeth.R;
import fr.lemeut.loic.musketeeth.activity.LavageEndStatsActivity;
import fr.lemeut.loic.musketeeth.activity.MainActivity;
import fr.lemeut.loic.musketeeth.classes.NotificationMusketeeth;

public class AlarmReceiver extends BroadcastReceiver {
    String notifDesc, notifTitre = "";
    int notifId = 0;
    boolean hasNotif = false;
    Context _context;

    @Override
    public void onReceive(Context context, Intent intent) {

         /* Set the alarm to start at 10:30 AM */
        Calendar cal = Calendar.getInstance();
        int heure = cal.get(Calendar.HOUR_OF_DAY);

        LavageEndStatsActivity classNotif = new LavageEndStatsActivity();

        if(heure== 9){
            notifDesc = "Musketeeth te demande de te laver les dents !";
            notifTitre ="Musketeeth";
            notifId = 5;
            hasNotif = true;
        }else if(heure == 20){
            notifDesc = "Musketeeth te demande de te laver les dents !";
            notifTitre ="Musketeeth";
            notifId = 6;
            hasNotif = true;
        }

        if(hasNotif){
            NotificationMusketeeth notificationScore = new NotificationMusketeeth(notifId, notifDesc,  notifTitre, context);
            notificationScore.createNotification();
        }

    }



}