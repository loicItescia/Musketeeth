package fr.lemeut.loic.musketeeth.classes;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import fr.lemeut.loic.musketeeth.R;
import fr.lemeut.loic.musketeeth.activity.MainActivity;

/**
 * Created by Loic on 15/07/2015.
 */
public class NotificationMusketeeth {
    private String notificationDesc;
    private int idNotif;
    private String notificationTitle;
    private Context _context;

    public NotificationMusketeeth(int idNotif, String notificationDesc, String notificationTitle, Context _context) {
        this.notificationDesc = notificationDesc;
        this.idNotif = idNotif;
        this.notificationTitle = notificationTitle;
        this._context = _context;
    }


    /*
     * Creation d'une notification pour indiquer un nouveau badge
     */

    public void createNotification() {
        //Recuperation du notification Manager
        final NotificationManager notificationManager = (NotificationManager) _context.getSystemService(Context.NOTIFICATION_SERVICE);

        //Creation de la notification avec specification de l'icone de la notification et le texte qui apparait a la creation de la notification
        final android.app.Notification notification = new android.app.Notification(R.mipmap.ic_musketeeth, notificationTitle, System.currentTimeMillis());

        //Definition de la redirection au moment du clic sur la notification. Dans notre cas la notification redirige vers notre application
        final PendingIntent pendingIntent = PendingIntent.getActivity(_context, 0, new Intent(_context, MainActivity.class), 0);

        //Notification & Vibration
        notification.setLatestEventInfo(_context, notificationTitle, notificationDesc, pendingIntent);
        notification.vibrate = new long[]{0, 200, 100, 200, 100, 200};
        notificationManager.notify(idNotif, notification);
    }
}
