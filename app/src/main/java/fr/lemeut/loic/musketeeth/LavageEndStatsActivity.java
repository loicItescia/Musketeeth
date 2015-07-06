package fr.lemeut.loic.musketeeth;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.w3c.dom.Comment;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import fr.lemeut.loic.musketeeth.sql.ScoreLavage;
import  fr.lemeut.loic.musketeeth.sql.ScoreLavageDataSource;

/**
 * Created by Loic on 03/07/2015.
 */
public class LavageEndStatsActivity extends Activity {
    private TextView viewTempsLavage, viewSCORE_DEVANT_VERTICAL, viewSCORE_DESSUS_BAS_HORIZONTALE, viewSCORE_DESSOUS_HAUT_HORIZONTALE,
            viewSCORE_DERRIERE_HAUT, viewSCORE_DERRIERE_BAS, viewSCORE_NOTHING, viewScoreFinal;
    private Button buttonShare, buttonDeleteBDD;
    String messageTempsLavage = "";
    private float TempsLavage = 0;
    private String SCORE_DEVANT_VERTICAL;
    private String SCORE_DESSUS_BAS_HORIZONTALE;
    private String SCORE_DESSOUS_HAUT_HORIZONTALE;
    private String SCORE_DERRIERE_HAUT;
    private String SCORE_DERRIERE_BAS;
    private String SCORE_NOTHING;
    private ScoreLavageDataSource datasource;
    private ScoreLavage comment;
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lavage_end_stats);
        _context = this;
        Bundle myIntent = getIntent().getExtras(); // gets the previously created intent
        GestionScore scoreFinal = null;

        // Gestion des TextView
        gestionTextView();
        gestionButton();

        // Recuperation des donnees de l'Intent
        if (myIntent != null) {
            scoreFinal = new GestionScore(myIntent);
            messageTempsLavage = Float.toString((float) myIntent.get("tempsLavage"));
            SCORE_DEVANT_VERTICAL = Float.toString((float) myIntent.get("SCORE_DEVANT_VERTICAL") / 1000);
            SCORE_DESSUS_BAS_HORIZONTALE = Float.toString((float) myIntent.get("SCORE_DESSUS_BAS_HORIZONTALE") / 1000);
            SCORE_DESSOUS_HAUT_HORIZONTALE = Float.toString((float) myIntent.get("SCORE_DESSOUS_HAUT_HORIZONTALE") / 1000);
            SCORE_DERRIERE_HAUT = Float.toString((float) myIntent.get("SCORE_DERRIERE_HAUT") / 1000);
            SCORE_DERRIERE_BAS = Float.toString((float) myIntent.get("SCORE_DERRIERE_BAS") / 1000);
            SCORE_NOTHING = Float.toString((float) myIntent.get("SCORE_NOTHING") / 1000);

        } else {
            messageTempsLavage = "ERR";
        }

        // Affichage des score a l'ecran
        viewTempsLavage.setText(messageTempsLavage + " s");
        viewSCORE_DEVANT_VERTICAL.setText(SCORE_DEVANT_VERTICAL + " s");
        viewSCORE_DESSUS_BAS_HORIZONTALE.setText(SCORE_DESSUS_BAS_HORIZONTALE + " s");
        viewSCORE_DESSOUS_HAUT_HORIZONTALE.setText(SCORE_DESSOUS_HAUT_HORIZONTALE + " s");
        viewSCORE_DERRIERE_HAUT.setText(SCORE_DERRIERE_HAUT + " s");
        viewSCORE_DERRIERE_BAS.setText(SCORE_DERRIERE_BAS + " s");
        viewSCORE_NOTHING.setText(SCORE_NOTHING + " s");
        viewScoreFinal.setText(Integer.toString(scoreFinal.getScoreFinal()));


        // Sauvegarde du score en BDD
        datasource = new ScoreLavageDataSource(this);
        datasource.open();
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int Month = cal.get(Calendar.MONTH) + 1;
        int Year = cal.get(Calendar.YEAR);
        String dayOfMonthStr = String.valueOf(dayOfMonth);
        String MonthStr = String.valueOf(Month);
        String YearStr = String.valueOf(Year);
        comment = datasource.createComment(Integer.toString(scoreFinal.getScoreFinal()), dayOfMonthStr + "-" + MonthStr + "-" + YearStr);

        // Notificaction
        Toast.makeText(getBaseContext(), "Ajout d'une notification", Toast.LENGTH_SHORT).show();
        createNotification();

        // Vider la BDD
        buttonDeleteBDD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                List<ScoreLavage> values = datasource.getAllComments();
                int size = values.size();
                for (ScoreLavage i : values) {
                    datasource.deleteComment(i);
                }
            }
        });

        // Partager sur Twitter
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Loic a perdu au POng !!!");
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Loic a perdu au POng !!!");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.twitter.android");
                startActivity(sendIntent);
            }
        });
    }

    private void gestionButton() {
        buttonShare = (Button) findViewById(R.id.button3);
        buttonDeleteBDD = (Button) findViewById(R.id.button5);
    }

    private void gestionTextView() {
        viewTempsLavage = (TextView) findViewById(R.id.textView7);
        viewSCORE_DEVANT_VERTICAL = (TextView) findViewById(R.id.textView15);
        viewSCORE_DESSUS_BAS_HORIZONTALE = (TextView) findViewById(R.id.textView16);
        viewSCORE_DESSOUS_HAUT_HORIZONTALE = (TextView) findViewById(R.id.textView17);
        viewSCORE_DERRIERE_HAUT = (TextView) findViewById(R.id.textView18);
        viewSCORE_DERRIERE_BAS = (TextView) findViewById(R.id.textView19);
        viewSCORE_NOTHING = (TextView) findViewById(R.id.textView20);
        viewScoreFinal = (TextView) findViewById(R.id.textView21);
    }


    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    private final void createNotification() {
        //R�cup�ration du titre et description de la notification
        final String notificationTitle = "Titre notif";
        final String notificationDesc = "Description notif";

        //R�cup�ration du notification Manager
        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Cr�ation de la notification avec sp�cification de l'ic�ne de la notification et le texte qui apparait � la cr�ation de la notification
        final Notification notification = new Notification(R.mipmap.ic_launcher, notificationTitle, System.currentTimeMillis());

        //D�finition de la redirection au moment du clic sur la notification. Dans notre cas la notification redirige vers notre application
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        //Notification & Vibration
        notification.setLatestEventInfo(this, notificationTitle, notificationDesc, pendingIntent);
        notification.vibrate = new long[]{0, 200, 100, 200, 100, 200};
        notificationManager.notify(1, notification);
    }

}
