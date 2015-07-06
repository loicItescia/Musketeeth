package fr.lemeut.loic.musketeeth;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import fr.lemeut.loic.musketeeth.sql.ScoreLavage;
import fr.lemeut.loic.musketeeth.sql.ScoreLavageDataSource;

/**
 * Created by Loic on 06/07/2015.
 */
public class ViewScores extends Activity {
    private ScoreLavageDataSource datasource;

    String score;
    String Ts_dateScore;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Affichage de la BDD dans un listView sur la home de l'application
        datasource = new ScoreLavageDataSource(this);
        datasource.open();
        List<ScoreLavage> values = datasource.getAllComments();

        LinearLayout layout = new LinearLayout(this);
        setContentView(layout);
        layout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < values.size(); i++) {
            score = values.get(i).getscore();
            Ts_dateScore = values.get(i).getTs_dateScore();
            TextView tv=new TextView(getApplicationContext());
            tv.setTextColor(Color.BLUE);

            tv.setText(score+ " *** " + Ts_dateScore);
            layout.addView(tv);
        }

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
}
