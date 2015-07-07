package fr.lemeut.loic.musketeeth.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import fr.lemeut.loic.musketeeth.sqlscorelavage.ScoreLavage;
import fr.lemeut.loic.musketeeth.sqlscorelavage.ScoreLavageDataSource;

/**
 * Created by Loic on 06/07/2015.
 *
 * Class AllScoresActivity
 * Permet de visualiser tous les scores avec la date
 */
public class AllScoresActivity extends Activity {
    private ScoreLavageDataSource datasource;
    String score;
    String Ts_dateScore;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Affichage de la BDD dans un listView sur la home de l'application
        datasource = new ScoreLavageDataSource(this);
        datasource.open();
        // Recupere la liste de tous les scores
        List<ScoreLavage> values = datasource.getAllComments();

        // Fabrique des texView pour afficher les scores
        LinearLayout layout = new LinearLayout(this);
        setContentView(layout);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Boucle sur tous les scores
        for (int i = 0; i < values.size(); i++) {
            score = values.get(i).getscore();
            Ts_dateScore = values.get(i).getTs_dateScore();

            // Creation d'un TextView
            TextView tv=new TextView(getApplicationContext());
            tv.setTextColor(Color.BLUE);
            tv.setText(score + " *** " + Ts_dateScore);
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
