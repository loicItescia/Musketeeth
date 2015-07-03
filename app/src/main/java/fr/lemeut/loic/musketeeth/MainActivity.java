package fr.lemeut.loic.musketeeth;

import android.app.Activity;
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
import android.widget.ListView;

import java.util.List;

import fr.lemeut.loic.musketeeth.sql.ScoreLavage;
import fr.lemeut.loic.musketeeth.sql.ScoreLavageDataSource;

public class MainActivity extends Activity {

    Context _context;
    private ScoreLavageDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _context = this;

        // Affichage de la BDD dans un listView sur la home de l'application
        datasource = new ScoreLavageDataSource(this);
        datasource.open();
        List<ScoreLavage> values = datasource.getAllComments();
        ListView lv = (ListView) findViewById(R.id.listView);

        ArrayAdapter<ScoreLavage> adapter = new ArrayAdapter<ScoreLavage>(this,android.R.layout.simple_list_item_1, values);
        lv.setAdapter(adapter);

        // Bouton start
        Button buttonStartLavage = (Button) findViewById(R.id.button);
        buttonStartLavage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent goToNextActivty = new Intent(_context.getApplicationContext(), LavageStartActivity.class);
                _context.startActivity(goToNextActivty);
            }
        });


    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
        // Affichage de la BDD dans un listView sur la home de l'application
        datasource = new ScoreLavageDataSource(this);
        datasource.open();
        List<ScoreLavage> values = datasource.getAllComments();
        ListView lv = (ListView) findViewById(R.id.listView);
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }
}