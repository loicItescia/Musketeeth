package fr.lemeut.loic.musketeeth.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import fr.lemeut.loic.musketeeth.R;

/**
 * Created by Loic on 03/07/2015.
 */
public class LavageStartActivity extends Activity implements SensorEventListener {

    // Context
    private Context _context;

    // Variables de l'accelerometre
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 600;
    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    // Variables Chronometrer
    private Chronometer chronoLavage;
    private float tempsLavage = 0;
    private int elapsed = 0;
    private int intervalleAnalysePos = 100;

    // Variables score lavage
    public float SCORE_DEVANT_VERTICAL = 0;
    public float SCORE_DESSUS_BAS_HORIZONTALE = 0;
    public float SCORE_DESSOUS_HAUT_HORIZONTALE = 0;
    public float SCORE_DERRIERE_HAUT = 0;
    public float SCORE_DERRIERE_BAS = 0;
    public float SCORE_NOTHING = 0;

    // Variables des TextView
    TextView viewX, viewY, viewZ, viewPos, viewSpeed, viewSpeedString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Application du context + la vue
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lavage);
        _context = this;

        // Initialisation du Chronometre
        chronoLavage = (Chronometer) findViewById(R.id.chronometer);
        chronoLavage.start();
        chronoLavage.setBase(SystemClock.elapsedRealtime() - elapsed);

        // Initialisation des listener de l'accelerometre
        senSensorManager = (SensorManager) getSystemService(this.getApplicationContext().SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        // Initialisation des TextView
        gestionTextView();

        // Initialisation du bouton "Arreter le lavage"
        Button buttonStopLavage = (Button) findViewById(R.id.button2);
        buttonStopLavage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Stop le timer
                chronoLavage.stop();
                // Recupere la valeur du timer
                tempsLavage = SystemClock.elapsedRealtime() -  chronoLavage.getBase();
                // Lancement de l'activite pour les stats
                Intent goToNextActivty = new Intent(_context.getApplicationContext(), LavageEndStatsActivity.class);
                goToNextActivty.putExtra("tempsLavage", tempsLavage/1000);
                goToNextActivty.putExtra("SCORE_DEVANT_VERTICAL", SCORE_DEVANT_VERTICAL);
                goToNextActivty.putExtra("SCORE_DESSUS_BAS_HORIZONTALE", SCORE_DESSUS_BAS_HORIZONTALE);
                goToNextActivty.putExtra("SCORE_DESSOUS_HAUT_HORIZONTALE", SCORE_DESSOUS_HAUT_HORIZONTALE);
                goToNextActivty.putExtra("SCORE_DERRIERE_HAUT", SCORE_DERRIERE_HAUT);
                goToNextActivty.putExtra("SCORE_DERRIERE_BAS", SCORE_DERRIERE_BAS);
                goToNextActivty.putExtra("SCORE_NOTHING", SCORE_NOTHING);
                finish(); // Supprime l'activite en cours, pour eviter de revenir dessus avec un back.
                _context.startActivity(goToNextActivty);
            }
        });
    }

    /*
     * Initialisation des TextView
     */
    private void gestionTextView() {
         viewX = (TextView) findViewById(R.id.textView);
         viewY = (TextView) findViewById(R.id.textView2);
         viewZ = (TextView) findViewById(R.id.textView3);
         viewPos = (TextView) findViewById(R.id.textView4);
         viewSpeed = (TextView) findViewById(R.id.textView5);
         viewSpeedString = (TextView) findViewById(R.id.textView6);

    }

    /*
     * Listerner de l'accelerometre
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        String messagePos, messageSpeed = "";

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > intervalleAnalysePos) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ diffTime * 10000;
                Integer speedInt = Math.round(speed);


                // Set des x,y,z
                last_x = x;
                last_y = y;
                last_z = z;

                // Affiche la vitesse
                if(speed>800){
                    messageSpeed = "PARFAIT !!!!";
                }else if(speed>400){
                    messageSpeed = "PAS MAL !";
                }else{
                    messageSpeed = "PLUS VITE";
                }

                // Affiche la position actuelle de la brosse a dents
                if(last_x>6 && (last_y>-6 && last_y<6) && last_z >-3) {
                    messagePos = "DENTS DEVANT VERTICAL";
                    SCORE_DEVANT_VERTICAL += intervalleAnalysePos;
                }else if((last_x>-3 && last_x<3) && last_z <-5 ){
                    messagePos = "DENTS BAS HORIZON";
                    SCORE_DESSUS_BAS_HORIZONTALE += intervalleAnalysePos;
                }else if((last_x>-3 && last_x<3) && last_z > 5 ){
                    messagePos = "DENTS HAUT HORIZON";
                    SCORE_DESSOUS_HAUT_HORIZONTALE += intervalleAnalysePos;
                }else if(last_x>4 && last_y <0 && last_z <0 ){
                    messagePos = "DENTS DERRIERE BAS";
                    SCORE_DERRIERE_BAS += intervalleAnalysePos;
                }else if(last_x<-5 && (last_y>-4 && last_y<4)  && last_z > 4 ){
                    messagePos = "DENTS DERRIERE HAUT";
                    SCORE_DERRIERE_HAUT += intervalleAnalysePos;
                }else{
                    messagePos = "NOTHING";
                    SCORE_NOTHING += intervalleAnalysePos;
                }

                // Set les TextView
                viewX.setText(Float.toString(last_x));
                viewY.setText(Float.toString(last_y));
                viewZ.setText(Float.toString(last_z));
                viewSpeed.setText(speedInt.toString());
                viewPos.setText(messagePos);
                viewSpeedString.setText(messageSpeed);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
