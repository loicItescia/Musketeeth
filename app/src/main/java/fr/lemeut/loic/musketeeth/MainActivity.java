package fr.lemeut.loic.musketeeth;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import fr.lemeut.loic.musketeeth.sql.ScoreLavage;
import fr.lemeut.loic.musketeeth.sql.ScoreLavageDataSource;

public class MainActivity extends Activity {

    Context _context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = this;



        // Bouton start
        Button buttonViewScores = (Button) findViewById(R.id.button6);
        buttonViewScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent goToNextActivty = new Intent(_context.getApplicationContext(), ViewScores.class);
                _context.startActivity(goToNextActivty);
            }
        });



        // Bouton start
        Button buttonStartLavage = (Button) findViewById(R.id.button);
        buttonStartLavage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent goToNextActivty = new Intent(_context.getApplicationContext(), LavageStartActivity.class);
                _context.startActivity(goToNextActivty);
            }
        });

        //la suppression de la notification se fait grâce à son ID
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(1);



}


}