package fr.lemeut.loic.musketeeth;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

/**
 * Created by Loic on 03/07/2015.
 */
public class LavageEndStatsActivity extends Activity {
    private TextView viewTempsLavage, viewSCORE_DEVANT_VERTICAL, viewSCORE_DESSUS_BAS_HORIZONTALE,  viewSCORE_DESSOUS_HAUT_HORIZONTALE,
            viewSCORE_DERRIERE_HAUT, viewSCORE_DERRIERE_BAS, viewSCORE_NOTHING;
    private Button buttonShare;
    String messageTempsLavage = "";
    private float TempsLavage = 0;
    private String SCORE_DEVANT_VERTICAL;
    private String SCORE_DESSUS_BAS_HORIZONTALE;
    private String SCORE_DESSOUS_HAUT_HORIZONTALE;
    private String SCORE_DERRIERE_HAUT;
    private String SCORE_DERRIERE_BAS;
    private String SCORE_NOTHING;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lavage_end_stats);
        Bundle myIntent = getIntent().getExtras(); // gets the previously created intent

        // Gestion des TextView
        gestionTextView();
        gestionButton();


        if(myIntent != null){
            messageTempsLavage = Float.toString((float)myIntent.get("tempsLavage"));
            SCORE_DEVANT_VERTICAL =  Float.toString((float)myIntent.get("SCORE_DEVANT_VERTICAL")/1000);
            SCORE_DESSUS_BAS_HORIZONTALE =  Float.toString((float)myIntent.get("SCORE_DESSUS_BAS_HORIZONTALE")/1000);
            SCORE_DESSOUS_HAUT_HORIZONTALE =  Float.toString((float)myIntent.get("SCORE_DESSOUS_HAUT_HORIZONTALE")/1000);
            SCORE_DERRIERE_HAUT =  Float.toString((float)myIntent.get("SCORE_DERRIERE_HAUT")/1000);
            SCORE_DERRIERE_BAS =  Float.toString((float)myIntent.get("SCORE_DERRIERE_BAS")/1000);
            SCORE_NOTHING = Float.toString((float)myIntent.get("SCORE_NOTHING")/1000);

        }else{
            messageTempsLavage = "ERR";
        }

        viewTempsLavage.setText(messageTempsLavage+" s");
        viewSCORE_DEVANT_VERTICAL.setText(SCORE_DEVANT_VERTICAL+" s");
        viewSCORE_DESSUS_BAS_HORIZONTALE.setText(SCORE_DESSUS_BAS_HORIZONTALE+" s");
        viewSCORE_DESSOUS_HAUT_HORIZONTALE.setText(SCORE_DESSOUS_HAUT_HORIZONTALE+" s");
        viewSCORE_DERRIERE_HAUT.setText(SCORE_DERRIERE_HAUT+" s");
        viewSCORE_DERRIERE_BAS.setText(SCORE_DERRIERE_BAS+" s");
        viewSCORE_NOTHING.setText(SCORE_NOTHING+" s");



    /*
     * Partager sur Twitter
     */
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
    }

    private void gestionTextView() {
        viewTempsLavage  = (TextView) findViewById(R.id.textView7);
        viewSCORE_DEVANT_VERTICAL  = (TextView) findViewById(R.id.textView15);
        viewSCORE_DESSUS_BAS_HORIZONTALE  = (TextView) findViewById(R.id.textView16);
        viewSCORE_DESSOUS_HAUT_HORIZONTALE  = (TextView) findViewById(R.id.textView17);
        viewSCORE_DERRIERE_HAUT  = (TextView) findViewById(R.id.textView18);
        viewSCORE_DERRIERE_BAS  = (TextView) findViewById(R.id.textView19);
        viewSCORE_NOTHING  = (TextView) findViewById(R.id.textView20);
    }
}