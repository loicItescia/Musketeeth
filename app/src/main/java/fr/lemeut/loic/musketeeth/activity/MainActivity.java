package fr.lemeut.loic.musketeeth.activity;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.lemeut.loic.musketeeth.R;
import fr.lemeut.loic.musketeeth.sqlbadges.Badges;
import fr.lemeut.loic.musketeeth.sqlbadges.BadgesDataSource;


/*
 * Class MainActivity
 * Home de l'application, permet de lancer le brossage, ou de voir les scores
 */
public class MainActivity extends Activity {
    Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = this;



        // Bouton "Voir les scores"
        Button buttonViewScores = (Button) findViewById(R.id.button6);
        buttonViewScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent goToNextActivty = new Intent(_context.getApplicationContext(), AllScoresActivity.class);
                _context.startActivity(goToNextActivty);
            }
        });



        // Bouton "Start", commencer le brossage
        Button buttonStartLavage = (Button) findViewById(R.id.button);
        buttonStartLavage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent goToNextActivty = new Intent(_context.getApplicationContext(), LavageStartActivity.class);
                _context.startActivity(goToNextActivty);
            }
        });

        // Bouton pour setter les badges (Pour eviter de polluer la base, eviter d'INSERT plusieurs fois chaque badge)
        Button buttonSetBadges = (Button) findViewById(R.id.button7);
        buttonSetBadges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                BadgesDataSource datasource;
                Badges badge;
                datasource = new BadgesDataSource(_context);
                datasource.open();
                /*datasource.createBadge("BADGE 1", 100, 0);
                datasource.createBadge("BADGE 2", 200, 0);
                datasource.createBadge("BADGE 3", 500, 0);
                datasource.createBadge("BADGE 4", 1000, 0);
                datasource.createBadge("BADGE 5", 1050, 0);*/
                datasource.close();
            }
        });

        // La suppression de la notification se fait grace a son ID
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);

    }
}