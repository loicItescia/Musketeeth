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
