package fr.lemeut.loic.musketeeth.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import fr.lemeut.loic.musketeeth.R;
import fr.lemeut.loic.musketeeth.activity.LavageEndStatsActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BrossageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BrossageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrossageFragment extends Fragment implements SensorEventListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private  View view;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BrossageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BrossageFragment newInstance(String param1, String param2) {
        BrossageFragment fragment = new BrossageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BrossageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_brossage,container, false);
        Button buttonStartLavage = (Button) view.findViewById(R.id.button);


        _context = getActivity();

        // Initialisation du Chronometre
        chronoLavage = (Chronometer) view.findViewById(R.id.chronometer);
        chronoLavage.start();
        chronoLavage.setBase(SystemClock.elapsedRealtime() - elapsed);

        // Initialisation des listener de l'accelerometre
        senSensorManager = (SensorManager) _context.getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        // Initialisation des TextView
        gestionTextView();

        // Initialisation du bouton "Arreter le lavage"
        Button buttonStopLavage = (Button) view.findViewById(R.id.button2);
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
                //finish(); // Supprime l'activite en cours, pour eviter de revenir dessus avec un back.
                _context.startActivity(goToNextActivty);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
     * Initialisation des TextView
     */
    private void gestionTextView() {

        viewX = (TextView) view.findViewById(R.id.textView);
        viewY = (TextView) view.findViewById(R.id.textView2);
        viewZ = (TextView) view.findViewById(R.id.textView3);
        viewPos = (TextView) view.findViewById(R.id.textView4);
        viewSpeed = (TextView) view.findViewById(R.id.textView5);
        viewSpeedString = (TextView) view.findViewById(R.id.textView6);

    }

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

                /*// Affiche la position actuelle de la brosse a dents
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
                viewPos.setText(messagePos);*/
                viewSpeedString.setText(messageSpeed);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
